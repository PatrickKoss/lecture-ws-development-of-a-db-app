#!/usr/bin/env node

import { chromium } from "playwright";
import { dirname } from "node:path";
import { mkdir } from "node:fs/promises";

const BASE_URL = process.env.BASE_URL ?? "http://localhost:8000";
const DECK = process.env.DECK ?? "index.html";
const DECK_PATH = DECK.endsWith(".html") ? DECK : `${DECK}.html`;
const OUT_FILE = process.env.OUT_FILE ?? "slides.pdf";
const CSS_DPI = 96;

await mkdir(dirname(OUT_FILE) || ".", { recursive: true });

const browser = await chromium.launch();
const context = await browser.newContext({
  viewport: { width: 1920, height: 1080 },
  deviceScaleFactor: 1,
});
const page = await context.newPage();

page.on("pageerror", (error) => console.error("[page error]", error.message));
page.on("console", (message) => {
  if (message.type() === "error")
    console.error("[console error]", message.text());
});

const url = new URL(DECK_PATH, `${BASE_URL}/`);
url.searchParams.set("print-pdf", "");
console.log(`loading ${url}`);
await page.goto(url.toString(), { waitUntil: "load" });

await page.waitForFunction(
  () =>
    typeof window.Reveal !== "undefined" &&
    window.Reveal.isReady?.() === true &&
    window.Reveal.getTotalSlides() > 0,
  { timeout: 120000 },
);
await page.waitForTimeout(1500);
await page.emulateMedia({ media: "print" });

// Reveal builds print pages at slideSize * (1 + margin), not at the raw
// slide size. Reading them back keeps the paper and the pages identical, so
// Chromium has nothing to crop.
const { width, height } = await page.evaluate(() => {
  const page = document.querySelector(".pdf-page");
  if (!page) throw new Error("print view produced no .pdf-page elements");
  const rect = page.getBoundingClientRect();
  return { width: Math.ceil(rect.width), height: Math.ceil(rect.height) };
});
console.log(`page size ${width}x${height}px`);

await page.pdf({
  path: OUT_FILE,
  width: `${width / CSS_DPI}in`,
  height: `${height / CSS_DPI}in`,
  printBackground: true,
  margin: { top: 0, right: 0, bottom: 0, left: 0 },
  preferCSSPageSize: false,
});

await browser.close();
console.log(`wrote ${OUT_FILE}`);

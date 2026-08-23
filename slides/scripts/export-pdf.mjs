#!/usr/bin/env node

import { chromium } from "playwright";
import { dirname } from "node:path";
import { mkdir } from "node:fs/promises";

const BASE_URL = process.env.BASE_URL ?? "http://localhost:8000";
const DECK = process.env.DECK ?? "index.html";
const DECK_PATH = DECK.endsWith(".html") ? DECK : `${DECK}.html`;
const OUT_FILE = process.env.OUT_FILE ?? "slides.pdf";
const PAGE_WIDTH_IN = 1920 / 96;
const PAGE_HEIGHT_IN = 1080 / 96;

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

await page.pdf({
  path: OUT_FILE,
  width: `${PAGE_WIDTH_IN}in`,
  height: `${PAGE_HEIGHT_IN}in`,
  printBackground: true,
  margin: { top: 0, right: 0, bottom: 0, left: 0 },
  preferCSSPageSize: false,
});

await browser.close();
console.log(`wrote ${OUT_FILE}`);

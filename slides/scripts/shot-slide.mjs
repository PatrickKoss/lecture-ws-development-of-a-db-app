#!/usr/bin/env node

import { chromium } from "playwright";
import { mkdir } from "node:fs/promises";
import { join } from "node:path";

const BASE_URL = process.env.BASE_URL ?? "http://localhost:8000";
const DECK = process.env.DECK ?? "index.html";
const DECK_PATH = DECK.endsWith(".html") ? DECK : `${DECK}.html`;
const OUT_DIR = process.env.OUT_DIR ?? "debug-screenshots";
const VIEWPORT = { width: 1920, height: 1080 };

const raw = (process.env.SLIDE ?? "").trim();
if (!raw) {
  console.error("usage: SLIDE=h/v/f node scripts/shot-slide.mjs");
  console.error("v defaults to 0, f to -1 (no fragment shown)");
  process.exit(1);
}

const parts = raw
  .replace(/^\/+|\/+$/g, "")
  .split("/")
  .map((part) => part.trim());
if (
  parts.length < 1 ||
  parts.length > 3 ||
  parts.some((part) => !/^-?\d+$/.test(part))
) {
  console.error(`invalid SLIDE=${raw}; expected digits separated by '/'`);
  process.exit(1);
}
const [h, v = 0, f = -1] = parts.map(Number);

await mkdir(OUT_DIR, { recursive: true });

const browser = await chromium.launch();
const context = await browser.newContext({
  viewport: VIEWPORT,
  deviceScaleFactor: 1,
});
const page = await context.newPage();

page.on("pageerror", (error) => console.error("[page error]", error.message));
page.on("console", (message) => {
  if (message.type() === "error")
    console.error("[console error]", message.text());
});

const deckUrl = new URL(DECK_PATH, `${BASE_URL}/`).toString();
await page.goto(deckUrl, { waitUntil: "load" });
await page.waitForFunction(
  () =>
    typeof window.Reveal !== "undefined" &&
    window.Reveal.isReady?.() === true &&
    window.Reveal.getTotalSlides() > 0,
  { timeout: 120000 },
);

const landed = await page.evaluate(
  ({ h, v, f }) => {
    window.Reveal.slide(h, v, f);
    const slide = window.Reveal.getCurrentSlide();
    return {
      indices: window.Reveal.getIndices(),
      heading: slide?.querySelector("h1,h2,h3")?.textContent?.trim() ?? "",
    };
  },
  { h, v, f },
);

if (landed.indices.h !== h || (landed.indices.v ?? 0) !== v) {
  console.error(
    `requested h=${h} v=${v}, but Reveal landed on h=${landed.indices.h} v=${landed.indices.v ?? 0}; slide does not exist`,
  );
  await browser.close();
  process.exit(2);
}

await page.waitForTimeout(800);

const file = `slide-h${h}-v${v}-f${f}.png`;
const path = join(OUT_DIR, file);
await page.screenshot({ path, fullPage: false });

await browser.close();
console.log(
  `wrote ${path} from ${DECK_PATH} (h=${h} v=${v} f=${f}${landed.heading ? `, ${landed.heading}` : ""})`,
);

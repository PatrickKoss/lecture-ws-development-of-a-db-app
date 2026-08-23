#!/usr/bin/env node

import { chromium } from "playwright";
import { mkdir, writeFile, rm } from "node:fs/promises";
import { join } from "node:path";

const BASE_URL = process.env.BASE_URL ?? "http://localhost:8000";
const DECK = process.env.DECK ?? "index.html";
const DECK_PATH = DECK.endsWith(".html") ? DECK : `${DECK}.html`;
const OUT_DIR = process.env.OUT_DIR ?? "debug-screenshots";
const MAX_FRAGMENTS_PER_SLIDE = 40;
const VIEWPORT = { width: 1920, height: 1080 };

await rm(OUT_DIR, { recursive: true, force: true });
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

const coords = await page.evaluate(() =>
  window.Reveal.getSlides().map((element) => {
    const { h, v } = window.Reveal.getIndices(element);
    return { h, v: v ?? 0 };
  }),
);
console.log(
  `Capturing ${coords.length} slides from ${DECK_PATH} at ${VIEWPORT.width}x${VIEWPORT.height}`,
);

const shots = [];

for (let i = 0; i < coords.length; i++) {
  const { h, v } = coords[i];
  await page.evaluate(({ h, v }) => window.Reveal.slide(h, v, -1), { h, v });
  await page.waitForTimeout(900);

  const slideInfo = await page.evaluate(() => {
    const slide = window.Reveal.getCurrentSlide();
    const heading = slide?.querySelector("h1,h2,h3")?.textContent?.trim() ?? "";
    return {
      heading,
      id: slide?.id ?? "",
      dataName: slide?.getAttribute("data-name") ?? "",
    };
  });

  for (let f = 0; f < MAX_FRAGMENTS_PER_SLIDE; f++) {
    const file = `slide-${String(i).padStart(2, "0")}-fragment-${String(f).padStart(2, "0")}.png`;
    await page.screenshot({ path: join(OUT_DIR, file), fullPage: false });
    shots.push({
      slide: i,
      h,
      v,
      fragment: f,
      file,
      heading: slideInfo.heading,
      id: slideInfo.id || slideInfo.dataName || null,
    });

    const advanced = await page.evaluate(() => {
      if (!window.Reveal.availableFragments().next) return false;
      window.Reveal.nextFragment();
      return true;
    });
    if (!advanced) break;
    await page.waitForTimeout(800);
  }

  process.stdout.write(
    `  ${i + 1}/${coords.length} h=${h} v=${v} ${slideInfo.heading || "(no heading)"}\n`,
  );
}

await writeFile(
  join(OUT_DIR, "index.json"),
  JSON.stringify(
    {
      baseUrl: BASE_URL,
      deck: DECK_PATH,
      viewport: VIEWPORT,
      capturedAt: new Date().toISOString(),
      shots,
    },
    null,
    2,
  ),
);

await browser.close();
console.log(`\nWrote ${shots.length} screenshots to ${OUT_DIR}/`);

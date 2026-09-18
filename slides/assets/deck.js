async function loadExternals() {
  const placeholders = document.querySelectorAll("[data-external-replace]");

  await Promise.all(
    Array.from(placeholders).map(async (placeholder) => {
      const path = placeholder.getAttribute("data-external-replace");

      try {
        const response = await fetch(path);
        if (!response.ok) {
          throw new Error(`${response.status} ${response.statusText}`);
        }

        const template = document.createElement("template");
        template.innerHTML = (await response.text()).trim();
        const elements = Array.from(template.content.children);
        const slides = elements.filter((element) => element.matches("section"));

        if (slides.length === 0) {
          throw new Error("chapter contains no slides");
        }

        elements
          .filter((element) => element.matches("style, link[rel='stylesheet']"))
          .forEach((stylesheet) => document.head.append(stylesheet));

        placeholder.replaceWith(...slides);
      } catch (error) {
        const message = document.createElement("div");
        message.className = "external-load-error";
        message.textContent = `Failed to load ${path}: ${error.message}`;
        placeholder.replaceChildren(message);
        placeholder.removeAttribute("data-external-replace");
      }
    }),
  );
}

await loadExternals();

/**
 * Reveal's print stylesheet sets `display: block !important` and
 * `padding: 0 !important` on every section, which flattens the flex layouts
 * in theme.css and removes their padding. Author rules cannot override an
 * `!important` declaration from another stylesheet, so instead we read the
 * values each section resolves to with the print sheet disabled, then write
 * them back as inline styles, which do win.
 */
function applyPrintLayout() {
  const sections = document.querySelectorAll(".reveal .pdf-page > section");
  if (sections.length === 0) return false;

  const printSheets = Array.from(document.styleSheets).filter((sheet) => {
    try {
      return Array.from(sheet.cssRules).some((rule) =>
        /reveal-print|print-pdf/.test(rule.selectorText ?? ""),
      );
    } catch {
      return false;
    }
  });

  printSheets.forEach((sheet) => (sheet.disabled = true));
  const layouts = Array.from(sections, (section) => {
    const styles = getComputedStyle(section);
    return { display: styles.display, padding: styles.padding };
  });
  printSheets.forEach((sheet) => (sheet.disabled = false));

  sections.forEach((section, index) => {
    const { display, padding } = layouts[index];
    section.style.setProperty("display", display, "important");
    section.style.setProperty("padding", padding, "important");
  });

  return true;
}

/**
 * `PrintView.activate()` builds the `.pdf-page` wrappers asynchronously and
 * emits no event when it finishes, so watch the slide container until they
 * show up.
 */
function restoreLayoutWhenPrintViewIsBuilt() {
  if (applyPrintLayout()) return;

  const slides = document.querySelector(".reveal .slides");
  const observer = new MutationObserver(() => {
    if (applyPrintLayout()) observer.disconnect();
  });
  observer.observe(slides, { childList: true, subtree: true });
}

Reveal.initialize({
  hash: true,
  transition: "fade",
  backgroundTransition: "none",
  width: 1920,
  height: 1080,
  margin: 0.04,
  slideNumber: "c/t",
  controls: true,
  navigationMode: "linear",
  controlsLayout: "edges",
  plugins: [RevealHighlight],
});

if (new URLSearchParams(window.location.search).has("print-pdf")) {
  Reveal.on("ready", restoreLayoutWhenPrintViewIsBuilt);
}

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

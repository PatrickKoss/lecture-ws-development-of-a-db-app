async function loadExternals() {
  const stacks = document.querySelectorAll("[data-external-replace]");

  await Promise.all(
    Array.from(stacks).map(async (stack) => {
      const path = stack.getAttribute("data-external-replace");

      try {
        const response = await fetch(path);
        if (!response.ok) {
          throw new Error(`${response.status} ${response.statusText}`);
        }

        const template = document.createElement("template");
        template.innerHTML = (await response.text()).trim();
        const slides = template.content.querySelectorAll(":scope > section");

        if (slides.length > 0) {
          stack.replaceChildren(...slides);
        } else {
          stack.replaceChildren(template.content.cloneNode(true));
        }
        stack.removeAttribute("data-external-replace");
      } catch (error) {
        const message = document.createElement("div");
        message.className = "external-load-error";
        message.textContent = `Failed to load ${path}: ${error.message}`;
        stack.replaceChildren(message);
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

export const config = {
  // identifier used for localStorage
  appId: "frontend",

  apiEndpoint: window["env"]["VUE_APP_BACKEND_URL"] || "http://127.0.0.1:8000",
  corsHeader: {
    'Access-Control-Allow-Origin': '*',
  },
};

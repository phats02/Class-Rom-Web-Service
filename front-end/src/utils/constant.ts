export const API_BASE_URL = process.env.REACT_APP_SERVER_HOST;
export const CLIENT_HOST = process.env.REACT_APP_CLIENT_HOST;
export const ACCESS_TOKEN = "accessToken";
export const USER_ID = "userId";

export const OAUTH2_REDIRECT_URI = `${process.env.REACT_APP_CLIENT_HOST}/oauth2/redirect`;

export const GOOGLE_AUTH_URL =
  API_BASE_URL + "/oauth2/authorize/google?redirect_uri=" + OAUTH2_REDIRECT_URI;
export const FACEBOOK_AUTH_URL =
  API_BASE_URL +
  "/oauth2/authorize/facebook?redirect_uri=" +
  OAUTH2_REDIRECT_URI;
export const GITHUB_AUTH_URL =
  API_BASE_URL + "/oauth2/authorize/github?redirect_uri=" + OAUTH2_REDIRECT_URI;

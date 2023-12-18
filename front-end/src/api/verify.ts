import { configuredAxios } from "./axios-config";

const verifyToken = async (token: string) => {
  const res = await configuredAxios.get("/auth/verify?code=" + token);
  return res.data;
};

export const VerifyAPI = {
  verifyToken,
};

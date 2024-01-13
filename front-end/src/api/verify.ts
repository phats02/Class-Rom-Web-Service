import { FailedResponse } from "../types/Response.type";
import { configuredAxios } from "./axios-config";

const verifyToken = async (token: string) => {
  const res = await configuredAxios.get("/auth/activation/" + token);
  return res.data;
};

const verifyForgotPasswordToken = async (
  token: string
): Promise<
  | FailedResponse
  | { code: number; success: true; message: string; email: string }
> => {
  const res = await configuredAxios.get("/auth/forgot-password/" + token);
  return res.data;
};

export const VerifyAPI = {
  verifyToken,
  verifyForgotPasswordToken,
};

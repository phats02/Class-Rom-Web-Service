import { configuredAxios } from "./axios-config";

const login = async (body: { email: string; password: string }) => {
  const res = await configuredAxios.post("/auth/admin/login", body);
  return res.data;
};
export const AdminAuthApi = {
  login,
};

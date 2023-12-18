import { User } from "../redux-toolkit/slice/auth.slice";
import { FailedResponse } from "../types/Response.type";
import { configuredAxios } from "./axios-config";

const fetchUserInfo = async (
  userId: string
): Promise<FailedResponse & { user: User }> => {
  const res = await configuredAxios.get("/users/" + userId);
  return res.data;
};

export const UserApi = {
  fetchUserInfo,
};

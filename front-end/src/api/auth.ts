import axios from "axios";
import { User } from "../redux-toolkit/slice/auth.slice";
import { configuredAxios } from "./axios-config";
import { API_BASE_URL } from "../utils/constant";

type UserLoginPayload = {
  email: string;
  password: string;
};

type LoginResponse = {
  code: number;
  jwt?: string;
  success: boolean;
  message?: string;
  user?: User;
};

const login = async (user: UserLoginPayload): Promise<LoginResponse> => {
  const res = await configuredAxios.post("/auth/login", user);
  return res.data;
};

type UserRegisterPayload = {
  email: string;
  name: string;
  password: string;
};
type RegisterResponse = {
  code: number;
  message: string;
  success: boolean;
};
const register = async (
  user: UserRegisterPayload
): Promise<RegisterResponse> => {
  const res = await configuredAxios.post("/auth/register", user);
  return res.data;
};

type SocialLoginResponse = {
  code: number;
  success: boolean;
  user: User;
  jwt: string;
};
const loginByGoogle = async (
  accessCode: string
): Promise<SocialLoginResponse> => {
  const res = await axios.get(`${API_BASE_URL}/auth/google/token`, {
    headers: {
      access_token: accessCode,
    },
  });
  return res.data;
};
export const AuthApi = { login, register, loginByGoogle };

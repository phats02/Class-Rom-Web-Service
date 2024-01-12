import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { ACCESS_TOKEN, USER_ID } from "../../utils/constant";

enum UserType {
  ADMIN,
  USER,
}

export type User = {
  createAt: string;
  email: string;
  name: string;
  type: UserType;
  _id: string;
  updatedAt: string;
  student: string;
};
export interface UserAuth {
  accessToken: string;
  user: User | null;
}

const initialState: UserAuth = {
  accessToken: "",
  user: null,
};

const UserSlice = createSlice({
  name: "auth",
  initialState: initialState,
  reducers: {
    setToken: (state, action: PayloadAction<{ token: string }>) => {
      const { token } = action.payload;
      state.accessToken = token;
      localStorage.setItem(ACCESS_TOKEN, token);
    },
    logoutUser: (state, _) => {
      state.user = null;
      state.accessToken = "";
      localStorage.setItem(ACCESS_TOKEN, "");
      localStorage.setItem(USER_ID, "");
    },
  },
});

export const authReducer = UserSlice.reducer;
export const { setToken, logoutUser } = UserSlice.actions;

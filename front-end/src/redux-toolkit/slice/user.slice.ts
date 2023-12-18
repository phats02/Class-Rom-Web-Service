import { PayloadAction, createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { USER_ID } from "../../utils/constant";
import { User } from "./auth.slice";
import { UserApi } from "../../api/user";

export interface UserState {
  currentUser: User | null;
  isExpired: boolean;
}

const initialState: UserState = {
  currentUser: null,
  isExpired: false,
};

export const fetchUserInfo = createAsyncThunk(
  "/auth/fetchUserInfo",
  async (userId: string) => {
    const res = await UserApi.fetchUserInfo(userId);
    return res.user;
  }
);

const UserSlice = createSlice({
  name: "user",
  initialState: initialState,
  reducers: {
    setCurrentUser: (state, action: PayloadAction<{ user: User }>) => {
      const { user } = action.payload;
      state.currentUser = user;
      localStorage.setItem(USER_ID, user._id);
    },
  },
  extraReducers: (builder) => {
    builder.addCase(fetchUserInfo.rejected, (state, action) => {
      state.isExpired = true;
    });
    builder.addCase(fetchUserInfo.fulfilled, (state, action) => {
      state.currentUser = action.payload;
    });
  },
});

export const userReducer = UserSlice.reducer;
export const { setCurrentUser } = UserSlice.actions;

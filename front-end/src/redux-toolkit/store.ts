import { gradeReviewReducer } from "./slice/gradeReview.slice";
import { classroomReducer } from "./slice/classroom.slice";
import { userReducer } from "./slice/user.slice";
import { configureStore } from "@reduxjs/toolkit";
import { setupListeners } from "@reduxjs/toolkit/dist/query";
import { useDispatch } from "react-redux";
import { authReducer } from "./slice/auth.slice";
import { notificationSlice } from "./slice/notification.slice";

export const store = configureStore({
  reducer: {
    authReducer,
    userReducer,
    classroomReducer,
    gradeReviewReducer,
    notificationSlice,
  },
});
setupListeners(store.dispatch);

export type RootState = ReturnType<typeof store.getState>;

export type AppDispatch = typeof store.dispatch;

export const useAppDispatch = () => useDispatch<AppDispatch>();

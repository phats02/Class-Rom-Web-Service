import { PayloadAction, createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { NotificationApi } from "../../api/notification";
import { TNotification } from "../../types/others.type";

type TNotificationSlice = {
  listNotification: TNotification[];
};

const initialState: TNotificationSlice = {
  listNotification: [],
};

export const fetchAllNotification = createAsyncThunk(
  "notification/fetchAllNotification",
  async () => {
    const res = await NotificationApi.getAllNotification();
    if (!res.success) throw res.message;
    return res.filterNotification;
  }
);

const NotificationSlice = createSlice({
  name: "notification",
  initialState: initialState,
  reducers: {
    viewedNotification: (state, action: PayloadAction<string>) => {
      const notificationId = action.payload;
      state.listNotification = state.listNotification.map((item) =>
        item._id === notificationId
          ? {
              ...item,
              viewed: true,
            }
          : item
      );
    },
  },
  extraReducers: (builder) => {
    builder.addCase(fetchAllNotification.fulfilled, (state, action) => {
      state.listNotification = action.payload;
    });
  },
});

export const notificationSlice = NotificationSlice.reducer;
export const { viewedNotification } = NotificationSlice.actions;

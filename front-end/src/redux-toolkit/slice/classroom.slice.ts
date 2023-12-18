import { ClassRoom } from "./../../types/Classroom.typs";
import { PayloadAction, createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { ClassRoomApi } from "../../api/classroom";
import _ from "lodash";

type ClassRoomSlice = {
  listClassRoom: ClassRoom[] | null;
  currentClassRoom: ClassRoom | null;
};
const initialState: ClassRoomSlice = {
  listClassRoom: null,
  currentClassRoom: null,
};

export const fetchListClassRoom = createAsyncThunk(
  "classroom/fetchListClassRoom",
  async () => {
    const res = await ClassRoomApi.getAllClass();
    return res.courses;
  }
);
const ClassroomSlice = createSlice({
  name: "classroom",
  initialState: initialState,
  reducers: {
    setListClassRoom: (
      state,
      action: PayloadAction<{ listClassRoom: ClassRoom[] }>
    ) => {
      const { listClassRoom } = action.payload;
      state.listClassRoom = listClassRoom;
    },
    addClass: (state, action: PayloadAction<ClassRoom>) => {
      state.listClassRoom = _.uniqBy(
        [action.payload, ...(state.listClassRoom as any)],
        "_id"
      );
    },
    setCurrentClass: (state, action: PayloadAction<ClassRoom>) => {
      state.currentClassRoom = action.payload;
    },
    deleteClass: (state, action: PayloadAction<String>) => {
      const classId = action.payload;
      state.listClassRoom =
        state.listClassRoom?.filter((item) => item._id !== classId) || null;
    },
  },
  extraReducers: (builder) => {
    builder.addCase(fetchListClassRoom.fulfilled, (state, action) => {
      state.listClassRoom = action.payload;
    });
  },
});

export const classroomReducer = ClassroomSlice.reducer;
export const { setListClassRoom, addClass, setCurrentClass, deleteClass } =
  ClassroomSlice.actions;

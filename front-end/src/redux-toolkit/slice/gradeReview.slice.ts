import { ReviewAPI } from "../../api/classroom";
import {
  Comment,
  GradeReview,
  GradeReviewsStatus,
} from "./../../types/Review.type";
import { PayloadAction, createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import _ from "lodash";

type TGradeReviewSlice = {
  listGradeReview: { assignmentId: string; gradeReview: GradeReview[] }[];
};

const initialState: TGradeReviewSlice = {
  listGradeReview: [],
};

export const fetchGradeReview = createAsyncThunk(
  "gradeReview/fetchListGradeReview",
  async (payload: { classSlug: string; assignmentId: string }) => {
    const { classSlug, assignmentId } = payload;
    const data = await ReviewAPI.getReviewRequest(classSlug, assignmentId);
    if (!data.success) {
      throw data.message;
    }
    return { assignmentId, gradeReview: data.gradeReviews };
  }
);

const GradeReviewSlice = createSlice({
  name: "grade",
  initialState: initialState,
  reducers: {
    finalizeGradeReview: (
      state,
      action: PayloadAction<{
        assignmentId: string;
        reviewId: string;
        approve: boolean;
      }>
    ) => {
      const { assignmentId, reviewId, approve } = action.payload;
      state.listGradeReview = state.listGradeReview.map((item) =>
        item.assignmentId === assignmentId
          ? {
              assignmentId,
              gradeReview: item.gradeReview.map((gradeItem) =>
                gradeItem._id === reviewId
                  ? {
                      ...gradeItem,
                      status: approve
                        ? GradeReviewsStatus.FINALIZED
                        : GradeReviewsStatus.REJECTED,
                    }
                  : gradeItem
              ),
            }
          : item
      );
    },
    addComment: (
      state,
      action: PayloadAction<{
        assignmentId: string;
        reviewId: string;
        comment: Comment;
      }>
    ) => {
      const { assignmentId, reviewId, comment } = action.payload;
      state.listGradeReview = state.listGradeReview.map((item) =>
        item.assignmentId === assignmentId
          ? {
              assignmentId,
              gradeReview: item.gradeReview.map((gradeItem) =>
                gradeItem._id === reviewId
                  ? {
                      ...gradeItem,
                      comments: [...gradeItem.comments, comment],
                    }
                  : gradeItem
              ),
            }
          : item
      );
    },
  },
  extraReducers: (builder) => {
    builder.addCase(fetchGradeReview.fulfilled, (state, action) => {
      state.listGradeReview = _.uniqBy(
        [...state.listGradeReview, action.payload],
        "assignmentId"
      );
    });
  },
});

export const gradeReviewReducer = GradeReviewSlice.reducer;
export const { finalizeGradeReview, addComment } = GradeReviewSlice.actions;

export type Comment = {
  userId: string;
  name: string;
  content: string;
  _id: string;
  createdAt: string;
};

export enum GradeReviewsStatus {
  PENDING,
  FINALIZED,
  REJECTED,
}

export type GradeReview = {
  actualGrade: number;
  assignmentId: string;
  comments: Comment[];
  createdAt: string;
  expectedGrade: number;
  message: string;
  status: GradeReviewsStatus;
  studentId: string;
  updatedAt: string;
  _id: string;
};

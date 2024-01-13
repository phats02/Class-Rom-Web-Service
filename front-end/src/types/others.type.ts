import { ClassRoomRole } from "../api/classroom";

export type Invitation = {
  _id: string;
  courseId: string;
  inviteCode: string;
  type: ClassRoomRole;
  createdAt: string;
  updatedAt: string;
};

export type TNotification = {
  _id: string;
  userId: string;
  sender: string;
  message: string;
  createdAt: string;
  course: string;
  viewed: boolean;
};

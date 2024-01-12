import { ClassRoomRole } from "../api/classroom";

export type Invitation = {
  _id: string;
  courseId: string;
  inviteCode: string;
  type: ClassRoomRole;
  createdAt: string;
  updatedAt: string;
};

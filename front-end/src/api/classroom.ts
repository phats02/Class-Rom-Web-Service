import { ClassRoom } from "../types/Classroom.typs";
import { FailedResponse } from "../types/Response.type";
import { configuredAxios } from "./axios-config";

const getAllClass = async () => {
  const res = await configuredAxios.get("/courses");
  return res.data;
};

type CreateClassRequest = {
  name: string;
  description: string;
};

type CreateClassResponse = FailedResponse & {
  course: ClassRoom;
};
const createClass = async (
  classInfo: CreateClassRequest
): Promise<CreateClassResponse> => {
  const res = await configuredAxios.post("/courses/store", classInfo);
  return res.data;
};

const getCurrentClassInfo = async (
  id: string
): Promise<CreateClassResponse> => {
  const res = await configuredAxios.get("/courses/" + id);
  return res.data;
};

const joinClassWithCode = async (
  classCode: string
): Promise<CreateClassResponse> => {
  const res = await configuredAxios.get("/courses/join/" + classCode);
  return res.data;
};

export enum ClassRoomRole {
  TEACHER,
  STUDENT,
}
type InviteBody = {
  email: string;
  courseId: string;
  type: ClassRoomRole;
};

const inviteUser = async (inviteInfo: InviteBody) => {
  const res = await configuredAxios.post("/courses/invite", inviteInfo);
  return res.data;
};

const deleteClass = async (classId: string): Promise<FailedResponse> => {
  const res = await configuredAxios.delete("/courses/" + classId);
  return res.data;
};
export const ClassRoomApi = {
  getAllClass,
  createClass,
  getCurrentClassInfo,
  joinClassWithCode,
  inviteUser,
  deleteClass,
};

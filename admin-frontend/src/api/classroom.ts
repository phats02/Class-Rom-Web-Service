import { configuredAxios } from "./axios-config";

const getAllClassroom = async () => {
  const res = await configuredAxios.get("/courses/all");
  return res.data;
};

export const ClassRoomAPI = {
  getAllClassroom,
};

import { User } from "../redux-toolkit/slice/auth.slice";

export type ClassRoom = {
  name: string;
  description: string;
  students: User[];
  teachers: User[];
  owner: User;
  joinId: string;
  _id: string;
  updatedAt: string;
};

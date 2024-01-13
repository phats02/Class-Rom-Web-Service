import { FailedResponse } from "../types/Response.type";
import { TNotification } from "../types/others.type";
import { configuredAxios } from "./axios-config";

const getAllNotification = async (): Promise<
  | FailedResponse
  | {
      success: true;
      message: string;
      statusCode: number;
      filterNotification: TNotification[];
    }
> => {
  const res = await configuredAxios.get("/notification");
  return res.data;
};

const viewedNotification = async (
  notificationId: string
): Promise<
  FailedResponse | { code: string; success: true; message: string }
> => {
  const res = await configuredAxios.post(
    "/notification/" + notificationId + "/viewed"
  );
  return res.data;
};

export const NotificationApi = {
  getAllNotification,
  viewedNotification,
};

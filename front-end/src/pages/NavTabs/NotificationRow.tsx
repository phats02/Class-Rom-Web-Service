import React from "react";
import { TNotification } from "../../types/others.type";
import { createUseStyles } from "react-jss";
import { Chip } from "@mui/material";
import { NotificationApi } from "../../api/notification";
import { useAppDispatch } from "../../redux-toolkit/store";
import { viewedNotification } from "../../redux-toolkit/slice/notification.slice";

type Props = {
  item: TNotification;
};

const useStyle = createUseStyles({
  container: {
    display: "flex",
    flexDirection: "column",
    width: "100%",
  },
  viewedStyle: {
    background: "",
  },
});
const NotificationRow = ({ item }: Props) => {
  const classes = useStyle();
  const storeDispatch = useAppDispatch();
  const handleViewed = async () => {
    try {
      await NotificationApi.viewedNotification(item._id);
      storeDispatch(viewedNotification(item._id));
    } catch (err) {}
  };
  return (
    <div className={classes.container} onClick={handleViewed}>
      <div>
        <div style={{ fontSize: 10, color: "grey" }}>
          Class: <span style={{ fontWeight: "bold" }}>{item.course}</span>
        </div>
        <div>{item.message}</div>
      </div>
      <div
        style={{
          display: "flex",
          marginTop: 3,
          marginBottom: 5,
          justifyContent: "space-between",
          alignItems: "flex-end",
        }}
      >
        <div style={{ fontSize: 10, fontWeight: "bold", color: "gray" }}>
          {new Date(item.createdAt).toLocaleDateString()}
        </div>
        <div>
          <Chip
            label={item.viewed ? "Viewed" : "Unseen"}
            color={item.viewed ? "success" : "warning"}
          />
        </div>
      </div>
    </div>
  );
};

export default NotificationRow;

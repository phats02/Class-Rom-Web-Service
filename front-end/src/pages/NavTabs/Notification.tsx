import { Badge, IconButton, Menu, Divider } from "@mui/material";
import React from "react";
import NotificationsNoneIcon from "@mui/icons-material/NotificationsNone";
import { useSelector } from "react-redux";
import { RootState } from "../../redux-toolkit/store";
import MenuItem from "@mui/material/MenuItem";
import Fade from "@mui/material/Fade";
import NotificationRow from "./NotificationRow";
import _ from "lodash";

const Notification = () => {
  const listNotification = useSelector(
    (state: RootState) => state.notificationSlice.listNotification
  );
  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
  const open = Boolean(anchorEl);
  const handleClick = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };
  return (
    <>
      <IconButton onClick={handleClick}>
        <Badge
          badgeContent={listNotification.filter((item) => !item.viewed).length}
          color={"success"}
          style={{ cursor: "pointer" }}
        >
          <NotificationsNoneIcon />
        </Badge>
      </IconButton>
      <Menu
        id="fade-menu"
        MenuListProps={{
          "aria-labelledby": "fade-button",
        }}
        anchorEl={anchorEl}
        open={open}
        onClose={handleClose}
        TransitionComponent={Fade}
        style={{ maxHeight: "50vh", overflowY: "auto" }}
      >
        {_.orderBy(
          listNotification,
          [(obj) => new Date(obj.createdAt)],
          ["asc"]
        ).map((item) => (
          <MenuItem
            key={item._id}
            style={{ display: "flex", flexDirection: "column" }}
          >
            <NotificationRow item={item} />
            <Divider component="div" style={{ width: "100%" }}></Divider>
          </MenuItem>
        ))}
      </Menu>
    </>
  );
};

export default Notification;

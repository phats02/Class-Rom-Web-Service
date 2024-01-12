import React, { useEffect, useState } from "react";
import { createUseStyles } from "react-jss";
import { useSelector } from "react-redux";
import { RootState } from "../../../../../redux-toolkit/store";
import { Button } from "@mui/material";
import { ClassRoomApi } from "../../../../../api/classroom";
import { Invitation } from "../../../../../types/others.type";
import { generateLinkInvite } from "../../../../../utils/common";
import { toast } from "react-toastify";

const useStyle = createUseStyles({
  container: {
    display: "flex",
    flexDirection: "column",
    gap: 10,
  },
  section: {
    border: "0.0625rem solid #dadce0",
    borderRadius: "0.5rem",
    padding: "1rem",
  },
  title: {
    fontSize: "0.875rem",
    fontWeight: 500,
    lineHeight: "1.25rem",
  },
  buttons: {
    display: "flex",
    justifyContent: "flex-end",
    marginTop: 20,
  },
});
const StreamLeftSide = () => {
  const classes = useStyle();

  const currentClassRoom = useSelector(
    (state: RootState) => state.classroomReducer.currentClassRoom
  );
  const [invitation, setInvitation] = useState<Invitation | null>(null);

  useEffect(() => {
    const fetchInvitationData = async () => {
      try {
        const data = await ClassRoomApi.getInvitation(currentClassRoom?._id);
        if (data.success) {
          setInvitation(data.invitation);
        }
      } catch (err) {}
    };
    if (currentClassRoom && !invitation) fetchInvitationData();
  }, [currentClassRoom]);

  function CopyCode() {
    navigator.clipboard.writeText(invitation?.inviteCode as any);
    toast.success("Copy invitation code successfully");
  }
  function CopyLinkInvite() {
    const invitationLink = generateLinkInvite(invitation?.inviteCode as any);
    navigator.clipboard.writeText(invitationLink);
  }
  return (
    <div className={classes.container}>
      <div className={classes.section}>
        <h1 className={classes.title}>
          Class Code:{" "}
          <span
            style={{ fontSize: "1rem", cursor: "pointer" }}
            onClick={CopyCode}
          >
            {invitation?.inviteCode}
          </span>
        </h1>
        <div className={classes.buttons} onClick={CopyLinkInvite}>
          <Button>Copy Link Invite</Button>
        </div>
      </div>
    </div>
  );
};

export default StreamLeftSide;

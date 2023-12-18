import React, { useEffect } from "react";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import { Button } from "@mui/material";
import CardContent from "@mui/material/CardContent";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import Divider from "@mui/material/Divider";
import ListItemAvatar from "@mui/material/ListItemAvatar";
import { useState } from "react";
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import IconButton from "@mui/material/IconButton";
import AddClassmate from "../Common/AddClassmate";
import { useNavigate, useParams } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../redux-toolkit/store";
import { ClassRoomApi, ClassRoomRole } from "../../api/classroom";
import {
  deleteClass,
  setCurrentClass,
} from "../../redux-toolkit/slice/classroom.slice";
import { toast } from "react-toastify";
import { User } from "../../redux-toolkit/slice/auth.slice";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import { generateLinkInvite } from "../../utils/common";
import { createUseStyles } from "react-jss";
import DeleteClassModal from "../Common/DeleteClassModal";
import { LoadingButton } from "@mui/lab";

interface Props {
  role: string;
  isCanAdd: boolean;
  users: User[];
}

const useStyle = createUseStyles({
  buttons: {
    display: "flex",
    justifyContent: "center",
  },
});

const ClassRoom = () => {
  const { classId } = useParams();
  const classes = useStyle();
  const storeDispatch = useDispatch();
  const currentClassRoom = useSelector(
    (state: RootState) => state.classroomReducer.currentClassRoom
  );
  const currentUser = useSelector(
    (state: RootState) => state.userReducer.currentUser
  );
  const isOwner = currentClassRoom?.owner._id === currentUser?._id;
  const navigate = useNavigate();

  const [openAddClassmate, setOpenAddClassmate] = useState<boolean>(false);
  const [addUserRole, setAddUserRole] = useState<ClassRoomRole>(
    ClassRoomRole.STUDENT
  );
  const [openDeleteClassModal, setOpenDeleteClassModal] =
    useState<boolean>(false);
  const [isDeleting, setIsDeleting] = useState<boolean>(false);

  const handleInviteUser = async (email: string) => {
    try {
      const res = await ClassRoomApi.inviteUser({
        courseId: currentClassRoom?._id as any,
        email,
        type: addUserRole,
      });
      if (!res?.success) throw res?.message || "Cannot send your request";

      toast.success("Sended invitation link to " + email);
      setOpenAddClassmate(false);
    } catch (err) {
      toast.warning(err as any);
    }
  };

  const handleDeleteClass = async () => {
    try {
      setOpenDeleteClassModal(false);
      setIsDeleting(true);
      const res = await ClassRoomApi.deleteClass(currentClassRoom?._id as any);
      if (!res?.success) throw res?.message || "Cannot send your request";
      toast.success(`Delete class ${currentClassRoom?.name} successfully`);
      storeDispatch(deleteClass(currentClassRoom?._id as any));
      navigate("/");
    } catch (error) {
      console.log(error);
      toast.warning(error as string);
    } finally {
      setIsDeleting(false);
    }
  };

  function ListItems({ role, isCanAdd, users }: Props) {
    return (
      <>
        <List
          sx={{
            maxWidth: 850,
            bgcolor: "background.paper",
            margin: "auto",
            mt: 6,
          }}
        >
          <Box sx={{ display: "flex", md: 1 }}>
            <Typography variant="h4" sx={{}}>
              {role}
            </Typography>
            {isCanAdd && (
              <IconButton
                onClick={() => {
                  setOpenAddClassmate(true);
                  setAddUserRole(
                    role === "Classmate"
                      ? ClassRoomRole.STUDENT
                      : ClassRoomRole.TEACHER
                  );
                }}
                sx={{ ml: "auto" }}
              >
                <PersonAddIcon />
              </IconButton>
            )}
          </Box>
          <Divider color="black" />
          {users.map((user, index) => (
            <React.Fragment key={user._id}>
              <ListItem alignItems="flex-start">
                <ListItemAvatar>
                  <AccountCircleIcon fontSize={"large"} />
                </ListItemAvatar>
                <Typography
                  variant="body2"
                  color="text.secondary"
                  sx={{ mt: 2 }}
                >
                  {user.name}
                </Typography>
              </ListItem>
              <Divider />
            </React.Fragment>
          ))}
        </List>
      </>
    );
  }

  useEffect(() => {
    const _fetchCurrentClassRoom = async (id: string) => {
      try {
        const res = await ClassRoomApi.getCurrentClassInfo(id);
        if (!res?.success) {
          throw res?.message || "Cannot send your request";
        }
        storeDispatch(setCurrentClass(res.course));
      } catch (error) {
        toast.warning("Your request fail with error:" + error);
      }
    };

    if (currentClassRoom === null || currentClassRoom._id !== classId)
      _fetchCurrentClassRoom(classId as any);
  }, []);

  function CopyCode() {
    navigator.clipboard.writeText(currentClassRoom?.joinId as any);
  }
  function CopyLinkInvite() {
    const invitationLink = generateLinkInvite(currentClassRoom?.joinId as any);
    navigator.clipboard.writeText(invitationLink);
  }

  return (
    <>
      <Box sx={{ display: "flex", maxWidth: "100%" }}>
        <Box
          sx={{
            flex: "15%",
            height: "90vh",
            borderRight: 1,
            borderColor: "grey.300",
          }}
        >
          <CardContent>
            <Typography
              gutterBottom
              variant="h5"
              component="div"
              sx={{ fontWeight: "bold" }}
            >
              {currentClassRoom?.name}
            </Typography>
            <Typography variant="body2" color="text.secondary">
              {currentClassRoom?.description}
            </Typography>
          </CardContent>

          <Box
            sx={{
              maxWidth: 250,
              border: 1,
              borderColor: "grey.300",
              borderRadius: 2,
              mt: 2,
              ml: 2,
            }}
          >
            <Typography
              variant="h6"
              sx={{
                pl: 2,
                fontWeight: "bold",
                backgroundImage: "linear-gradient(to right, #46b5e5, #1e88e5)",
                pt: 1,
                color: "white",
                pb: 1,
              }}
            >
              Invite Code
            </Typography>
            <Typography
              variant="h5"
              sx={{
                textAlign: "center",
                pt: 2,
                fontWeight: "bold",
              }}
            >
              {currentClassRoom?.joinId}
            </Typography>
            <Box sx={{ display: "flex" }}>
              <Button
                onClick={CopyCode}
                size="small"
                color="primary"
                sx={{
                  ml: "auto",
                  mr: 3,
                  mb: 1,
                  mt: 1,
                }}
              >
                copy code
              </Button>
              <Button
                size="small"
                onClick={(e) => {
                  e.preventDefault();
                  e.stopPropagation();
                  CopyLinkInvite();
                }}
              >
                Copy link invite
              </Button>
            </Box>
          </Box>
        </Box>
        <Box sx={{ flex: "70%", mt: 1 }}>
          <ListItems
            role={"Teacher"}
            isCanAdd={isOwner}
            users={currentClassRoom?.teachers || []}
          />
          <ListItems
            role={"Classmate"}
            isCanAdd={true}
            users={currentClassRoom?.students || []}
          />
          <div className={classes.buttons}>
            {isOwner && (
              <LoadingButton
                color={"error"}
                variant="contained"
                onClick={() => {
                  setOpenDeleteClassModal(true);
                }}
                loading={isDeleting}
              >
                Delete class
              </LoadingButton>
            )}
          </div>
        </Box>
      </Box>
      {openAddClassmate && (
        <AddClassmate
          isOpen={openAddClassmate}
          handleClose={() => {
            setOpenAddClassmate(false);
          }}
          handleInviteUser={handleInviteUser}
        />
      )}
      {openDeleteClassModal && (
        <DeleteClassModal
          isOpen={openDeleteClassModal}
          handleClose={() => {
            setOpenDeleteClassModal(false);
          }}
          handleDelete={handleDeleteClass}
        ></DeleteClassModal>
      )}
      s
    </>
  );
};

export default ClassRoom;

import { Button } from "@mui/material";
import { createUseStyles } from "react-jss";
import AddIcon from "@mui/icons-material/Add";
import { useEffect, useState } from "react";
import CreateClassModal from "../Common/CreateClassModal";
import ListClass from "../Common/ListClass";
import { AppDispatch, RootState } from "../../redux-toolkit/store";
import { useDispatch, useSelector } from "react-redux";
import { fetchListClassRoom } from "../../redux-toolkit/slice/classroom.slice";
import GroupAddOutlinedIcon from "@mui/icons-material/GroupAddOutlined";
import JoinClassByCodeModal from "../Common/JoinClassByCodeModal";

const useStyle = createUseStyles({
  container: {
    margin: 16,
  },
  header: {
    display: "flex",
    justifyContent: "space-between",
    width: "80%",
    margin: "0px auto",
  },
  listCard: {
    margin: 20,
    marginTop: 50,
  },
  buttons: {
    display: "flex",
    gap: 10,
  },
});

const Home = () => {
  const classes = useStyle();
  const [openCreateClassDialog, setOpenCreateClass] = useState<boolean>(false);
  const [openJoinClassDialog, setOpenJoinClassDialog] =
    useState<boolean>(false);
  const { listClassRoom } = useSelector(
    (state: RootState) => state.classroomReducer
  );
  const storeDispatch = useDispatch<AppDispatch>();

  useEffect(() => {
    if (listClassRoom === null) storeDispatch(fetchListClassRoom());
  }, []);
  return (
    <div className={classes.container}>
      <div className={classes.header}>
        <h1 style={{ fontWeight: "normal" }}>Classes</h1>
        <div className={classes.buttons}>
          <Button
            variant="contained"
            onClick={() => {
              setOpenJoinClassDialog(true);
            }}
            color={"secondary"}
          >
            <GroupAddOutlinedIcon />
            {"  "}Join class
          </Button>
          <Button
            variant="contained"
            onClick={() => {
              setOpenCreateClass(true);
            }}
          >
            <AddIcon />
            Create Class
          </Button>
        </div>

        {openCreateClassDialog && (
          <CreateClassModal
            isOpen={openCreateClassDialog}
            handleClose={() => {
              setOpenCreateClass(false);
            }}
          />
        )}
        {openJoinClassDialog && (
          <JoinClassByCodeModal
            isOpen={openJoinClassDialog}
            handleClose={() => {
              setOpenJoinClassDialog(false);
            }}
          />
        )}
      </div>
      <div className={classes.listCard}>
        <ListClass></ListClass>
      </div>
    </div>
  );
};

export default Home;

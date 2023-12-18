import { Button, Modal } from "@mui/material";
import React from "react";
import { createUseStyles } from "react-jss";

const useStyle = createUseStyles({
  container: {
    position: "absolute" as "absolute",
    top: "40%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    width: "60vw",
    maxWidth: 500,
    backgroundColor: "white",
    boxShadow: "rgba(0, 0, 0, 0.24) 0px 3px 8px;",
    borderRadius: 4,
    padding: "30px 40px",
    display: "flex",
    flexDirection: "column",
    rowGap: 35,
  },
  buttons: {
    display: "flex",
    justifyContent: "flex-end",
    gap: 10,
  },
});

type Props = {
  isOpen: boolean;
  handleClose: () => void;
  handleDelete: () => void;
};
const DeleteClassModal = ({ isOpen, handleClose, handleDelete }: Props) => {
  const classes = useStyle();
  return (
    <Modal
      open={isOpen}
      onClose={handleClose}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
    >
      <div className={classes.container}>
        <h1
          style={{
            zIndex: 1,
            backgroundColor: "white",
            fontSize: "1.5em",
          }}
        >
          Are you want to delete this class ?
        </h1>
        <div className={classes.buttons}>
          <Button onClick={handleClose} variant={"contained"}>
            Cancel
          </Button>
          <Button
            onClick={handleDelete}
            variant={"contained"}
            color={"warning"}
          >
            Delete
          </Button>
        </div>
      </div>
    </Modal>
  );
};
export default DeleteClassModal;

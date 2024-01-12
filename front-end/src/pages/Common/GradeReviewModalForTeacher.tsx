import { useMemo, useState } from "react";
import {
  Box,
  Button,
  Divider,
  Modal,
  TextField,
  Typography,
} from "@mui/material";
import { createUseStyles } from "react-jss";
import { LoadingButton } from "@mui/lab";
import { ReviewAPI } from "../../api/classroom";
import { useSelector } from "react-redux";
import { RootState, useAppDispatch } from "../../redux-toolkit/store";
import { GradeReview } from "../../types/Review.type";
import { toast } from "react-toastify";
import {
  addComment,
  finalizeGradeReview,
} from "../../redux-toolkit/slice/gradeReview.slice";
import { updateGrade } from "../../redux-toolkit/slice/classroom.slice";
import AddIcon from "@mui/icons-material/Add";
import CommentRow from "./CommentRow";

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
    padding: "16px 24px",
    display: "flex",
    flexDirection: "column",
    rowGap: 16,
  },
  body: {
    border: "solid 3px #c4c4c4",
    padding: 10,
    display: "flex",
    borderRadius: 16,
    flexDirection: "column",
    rowGap: 16,
    marginTop: -31,
    paddingTop: 30,
  },
  buttonsBottom: {
    display: "flex",
    justifyContent: "flex-end",
    gap: 10,
  },
  header: {
    fontWeight: 400,
    fontSize: "1.5rem",
  },
});

type Props = {
  isOpen: boolean;
  gradeReview: GradeReview;
  onClose: () => void;
};

const GradeReviewModalForTeacher = ({
  isOpen,
  gradeReview,
  onClose,
}: Props) => {
  const classes = useStyle();
  const storeDispatch = useAppDispatch();
  const currentClassroom = useSelector(
    (state: RootState) => state.classroomReducer.currentClassRoom
  );

  const assignment = useMemo(() => {
    return currentClassroom.assignments.find(
      (item) => item._id === gradeReview.assignmentId
    );
  }, [currentClassroom]);

  const [message, setMessage] = useState<string>("");
  const handleFinalizeReview = async (isApprove: boolean) => {
    try {
      const data = await ReviewAPI.setFinalizeReview(
        currentClassroom.slug,
        assignment?._id || "",
        gradeReview._id,
        {
          approve: isApprove,
          grade: gradeReview.expectedGrade,
        }
      );
      storeDispatch(
        finalizeGradeReview({
          assignmentId: assignment?._id || "",
          reviewId: gradeReview._id,
          approve: isApprove,
        })
      );
      if (isApprove) {
        storeDispatch(
          updateGrade({
            assignmentId: assignment?._id || "",
            grades: assignment?.grades?.map((item) =>
              item.id === gradeReview.studentId
                ? { ...item, grade: gradeReview.expectedGrade }
                : item
            ) as any,
          })
        );
      }
      toast.success(data.message);
      onClose();
    } catch (err) {}
  };

  const handleAddComment = async () => {
    try {
      const data = await ReviewAPI.addReviewComment(
        currentClassroom.slug,
        assignment?._id || "",
        gradeReview._id,
        {
          content: message,
        }
      );
      if (!data?.success) {
        toast.error(data.message);
        return;
      }
      storeDispatch(
        addComment({
          assignmentId: assignment?._id || "",
          reviewId: gradeReview._id,
          comment: data.comment,
        })
      );
      toast.success(data.message);
      setMessage("");
    } catch (err) {}
  };

  return (
    <Modal open={isOpen} onClose={onClose}>
      <div className={classes.container}>
        <h1
          style={{
            textAlign: "center",
            width: "35%",
            margin: "0px auto",
            zIndex: 1,
            backgroundColor: "white",
            fontSize: "1.5em",
          }}
        >
          Grade Review
        </h1>
        <Box className={classes.body}>
          <Typography variant="h2">{assignment?.name}</Typography>
          <div>Ration: {assignment?.point}</div>
          <div>Current grade: {gradeReview.actualGrade}</div>
          <>
            <Typography>Expected Grade: {gradeReview.expectedGrade}</Typography>
            <Typography>Message: {gradeReview.message}</Typography>
            <div className={classes.header}>Comment</div>
            <Divider style={{ borderBottomWidth: 3 }}></Divider>
            <div
              style={{
                maxHeight: 140,
                overflow: "auto",
                display: "flex",
                flexDirection: "column",
                gap: 10,
              }}
            >
              {gradeReview.comments.map((item) => (
                <CommentRow comment={item} key={item._id} />
              ))}
            </div>

            <TextField
              label={"Type your comment"}
              multiline
              maxRows={4}
              value={message}
              onChange={(e) => {
                setMessage(e.currentTarget.value);
              }}
            />
            <Button
              startIcon={<AddIcon></AddIcon>}
              onClick={handleAddComment}
              style={{ justifyContent: "flex-end" }}
            >
              Add
            </Button>

            <div className={classes.buttonsBottom}>
              <Button onClick={onClose}>Cancel</Button>
              <LoadingButton
                type="submit"
                onClick={() => {
                  handleFinalizeReview(false);
                }}
                variant="contained"
                color={"error"}
              >
                Reject
              </LoadingButton>
              <LoadingButton
                type="submit"
                onClick={() => {
                  handleFinalizeReview(true);
                }}
                variant="contained"
                color={"success"}
              >
                Accept
              </LoadingButton>
            </div>
          </>
        </Box>
      </div>
    </Modal>
  );
};

export default GradeReviewModalForTeacher;

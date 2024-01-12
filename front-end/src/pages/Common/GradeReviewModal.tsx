import { useEffect, useMemo, useState } from "react";
import { Assignment } from "../../types/Classroom.type";
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
import { RootState } from "../../redux-toolkit/store";
import { GradeReview } from "../../types/Review.type";
import { toast } from "react-toastify";
import CommentRow from "./CommentRow";
import AddIcon from "@mui/icons-material/Add";

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
  bodyHeader: {},
  header: {
    fontWeight: 400,
    fontSize: "1.5rem",
  },
});

type Props = {
  isOpen: boolean;
  assignment: Assignment;
  onClose: () => void;
};

const GradeReviewModal = ({ isOpen, assignment, onClose }: Props) => {
  const classes = useStyle();
  const currentClassroom = useSelector(
    (state: RootState) => state.classroomReducer.currentClassRoom
  );
  const currentUser = useSelector(
    (state: RootState) => state.userReducer.currentUser
  );

  const [currentGradeReview, setCurrentGradeReview] = useState<GradeReview>(
    null as unknown as GradeReview
  );

  const [expectedGrade, setExpectedGrade] = useState<string>("");

  const [message, setMessage] = useState<string>("");

  const currentPoint = useMemo(() => {
    const studentId = currentUser?.student;
    if (!studentId) return;
    return assignment.grades.find(
      (item) => item.id === studentId && !item.draft
    )?.grade;
  }, [assignment]);

  const handleCreateReviewRequest = async () => {
    try {
      const data = await ReviewAPI.createReviewRequest(
        currentClassroom.slug,
        assignment._id,
        {
          expectedGrade: expectedGrade as unknown as number,
          message,
        }
      );
      if (!data.success) {
        toast.error("Fetch Grade review failed with error: " + data.message);
        return;
      }
      toast.success("Create Grade review successfully");
      onClose();
    } catch (err) {
      toast.error("Something went wrong when fetching Grade Review !!");
    }
  };
  useEffect(() => {
    const fetchReviewData = async () => {
      try {
        const data = await ReviewAPI.getReviewRequest(
          currentClassroom.slug,
          assignment._id
        );
        if (!data.success) {
          toast.error("Fetch Grade review failed with error: " + data.message);
          return;
        }
        setCurrentGradeReview(
          data.gradeReviews.find(
            (item) => item.studentId === currentUser?.student
          ) as unknown as GradeReview
        );
      } catch (err) {
        toast.error("Something went wrong when fetching Grade Review !!");
      }
    };
    fetchReviewData();
  }, []);

  const handleAddComment = async () => {
    try {
      const data = await ReviewAPI.addReviewComment(
        currentClassroom.slug,
        assignment?._id || "",
        currentGradeReview._id,
        {
          content: message,
        }
      );
      if (!data?.success) {
        toast.error(data.message);
        return;
      }
      setCurrentGradeReview((prev) => ({
        ...prev,
        comments: [...prev.comments, data.comment],
      }));
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
          <Typography variant="h2">{assignment.name}</Typography>
          <div>Ration: {assignment.point}</div>
          <div>Current point: {currentPoint}</div>

          {!currentGradeReview ? (
            <>
              <TextField
                id="class-name"
                label="Expected grade"
                variant="outlined"
                fullWidth
                autoFocus
                inputProps={{ type: "number" }}
                value={expectedGrade}
                onChange={(e) => {
                  setExpectedGrade(e.currentTarget.value);
                }}
              />
              <TextField
                label={"Message"}
                multiline
                maxRows={4}
                value={message}
                onChange={(e) => {
                  setMessage(e.currentTarget.value);
                }}
              />

              <div className={classes.buttonsBottom}>
                <Button onClick={onClose}>Cancel</Button>
                <LoadingButton
                  type="submit"
                  onClick={handleCreateReviewRequest}
                  variant="contained"
                  //   loading={isCreating}
                >
                  Create
                </LoadingButton>
              </div>
            </>
          ) : (
            <>
              <Typography>
                Expected Grade: {currentGradeReview.expectedGrade}
              </Typography>
              <Typography>Message: {currentGradeReview.message}</Typography>
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
                {currentGradeReview.comments.map((item) => (
                  <CommentRow comment={item} key={item._id} />
                ))}
              </div>
              <TextField
                label={"Comment"}
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
                <Button onClick={onClose}>Close</Button>
              </div>
            </>
          )}
        </Box>
      </div>
    </Modal>
  );
};

export default GradeReviewModal;

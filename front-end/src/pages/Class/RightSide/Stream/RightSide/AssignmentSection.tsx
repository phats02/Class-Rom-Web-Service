import { useEffect, useMemo, useState } from "react";
import { createUseStyles } from "react-jss";
import { Assignment } from "../../../../../types/Classroom.type";
import { Divider } from "@mui/material";
import { useSelector } from "react-redux";
import { RootState, useAppDispatch } from "../../../../../redux-toolkit/store";
import GradeReviewModal from "../../../../Common/GradeReviewModal";
import { fetchGradeReview } from "../../../../../redux-toolkit/slice/gradeReview.slice";
import GradeReviewRow from "./GradeReviewRow";

const useStyle = createUseStyles({
  header: {
    fontWeight: 400,
    fontSize: "1.5rem",
  },
});

type Props = {
  assignment: Assignment;
};

const AssignmentSection = ({ assignment }: Props) => {
  const classes = useStyle();
  const storeDispatch = useAppDispatch();
  const currentUser = useSelector(
    (state: RootState) => state.userReducer.currentUser
  );
  const listGradeReview = useSelector(
    (state: RootState) => state.gradeReviewReducer.listGradeReview
  );

  const currentGradeReview = useMemo(
    () =>
      listGradeReview.find((item) => item.assignmentId === assignment._id)
        ?.gradeReview,
    [listGradeReview]
  );
  const currentClassroom = useSelector(
    (state: RootState) => state.classroomReducer.currentClassRoom
  );
  const [openGradeReviewModal, setOpenGradeReviewModal] =
    useState<boolean>(false);

  const currentPoint = useMemo(() => {
    const studentId = currentUser?.student;
    if (!studentId) return;
    return assignment.grades.find(
      (item) => item.id === studentId && !item.draft
    )?.grade;
  }, [assignment]);

  const isTeacher = useMemo(() => {
    return (
      currentClassroom.teachers.find(
        (item) => item._id === currentUser?._id
      ) !== undefined
    );
  }, [currentClassroom, currentUser]);

  useEffect(() => {
    if (!currentGradeReview)
      storeDispatch(
        fetchGradeReview({
          classSlug: currentClassroom.slug,
          assignmentId: assignment._id,
        })
      );
  }, []);

  return (
    <div>
      <div className={classes.header}>{assignment.name}</div>
      <Divider style={{ borderBottomWidth: 3 }}></Divider>
      {isTeacher ? (
        currentGradeReview?.map((item) => (
          <GradeReviewRow item={item} key={item._id} />
        ))
      ) : (
        <>
          {currentPoint ? (
            <span
              onClick={() => {
                setOpenGradeReviewModal(true);
              }}
              style={{ cursor: "pointer" }}
            >
              {currentPoint}
            </span>
          ) : (
            <span style={{ fontStyle: "italic" }}>Do not have point yet !</span>
          )}
        </>
      )}

      {openGradeReviewModal && (
        <GradeReviewModal
          isOpen={openGradeReviewModal}
          assignment={assignment}
          onClose={() => {
            setOpenGradeReviewModal(false);
          }}
        ></GradeReviewModal>
      )}
    </div>
  );
};

export default AssignmentSection;

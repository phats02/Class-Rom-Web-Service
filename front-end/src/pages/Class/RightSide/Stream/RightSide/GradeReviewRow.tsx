import React, { useState } from "react";
import {
  GradeReview,
  GradeReviewsStatus,
} from "../../../../../types/Review.type";
import { createUseStyles } from "react-jss";
import Chip from "@mui/material/Chip";
import GradeReviewModalForTeacher from "../../../../Common/GradeReviewModalForTeacher";
import { gradeReviewReducer } from "../../../../../redux-toolkit/slice/gradeReview.slice";

const useStyle = createUseStyles({
  row: {
    display: "flex",
    width: "100%",
    marginBottom: 10,
    padding: "10px 5px",
    cursor: "pointer",
    justifyContent: "space-between",
    alignItems: "center",
  },
});

type Props = {
  item: GradeReview;
};

const getStyleOfStatus = () => {
  const PendingStyle = {
    backgroundColor: "#F6F7C4",
  };
  const SuccessStyle = {
    backgroundColor: "#A1EEBD",
  };
  const RejectedStyle = {
    backgroundColor: "#FF8F8F",
  };
  const data = {
    [GradeReviewsStatus.PENDING]: PendingStyle,
    [GradeReviewsStatus.FINALIZED]: SuccessStyle,
    [GradeReviewsStatus.REJECTED]: RejectedStyle,
  };
  return data;
};

const getStatusLabel = (status: GradeReviewsStatus) => {
  const data = {
    [GradeReviewsStatus.PENDING]: "Pending",
    [GradeReviewsStatus.FINALIZED]: "Finalized",
    [GradeReviewsStatus.REJECTED]: "Rejected",
  };
  return data[status];
};
const GradeReviewRow = ({ item }: Props) => {
  const classes = useStyle();
  const [isOpenModal, setIsOpenModal] = useState(false);

  return (
    <>
      <div
        className={classes.row}
        style={getStyleOfStatus()[item.status]}
        onClick={() => {
          setIsOpenModal(true);
        }}
      >
        <span>
          From:{" "}
          <span style={{ fontStyle: "italic", fontWeight: "bold" }}>
            {item.studentId}
          </span>
        </span>
        <div style={{ display: "flex", gap: 10 }}>
          <Chip
            label={`Expected grade: ${item.actualGrade} -> ${item.expectedGrade}`}
            variant="outlined"
            color={"primary"}
          />
          <Chip label={getStatusLabel(item.status)} color={"primary"} />
        </div>
      </div>
      {isOpenModal && (
        <GradeReviewModalForTeacher
          gradeReview={item}
          isOpen={isOpenModal}
          onClose={() => {
            setIsOpenModal(false);
          }}
        />
      )}
    </>
  );
};

export default GradeReviewRow;

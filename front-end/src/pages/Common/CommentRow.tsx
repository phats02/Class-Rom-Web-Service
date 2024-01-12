import React from "react";
import { Comment } from "../../types/Review.type";

type Props = {
  comment: Comment;
};

const CommentRow = ({ comment }: Props) => {
  return (
    <div
      style={{
        display: "flex",
        flexWrap: "wrap",
        justifyContent: "space-between",
      }}
    >
      <div style={{ fontWeight: "bold" }}>{comment.name}</div>
      <div style={{ fontSize: 10, fontWeight: "bold", color: "gray" }}>
        {new Date(comment.createdAt).toLocaleDateString()}
      </div>
      <div style={{ width: "100%" }}>{comment.content}</div>
    </div>
  );
};

export default CommentRow;

import {
  Button,
  Card,
  CardActions,
  CardContent,
  Tooltip,
  Typography,
} from "@mui/material";
import React from "react";
import { createUseStyles } from "react-jss";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { RootState } from "../../redux-toolkit/store";
import { CLIENT_HOST } from "../../utils/constant";

const useStyles = createUseStyles({
  header: {
    height: 150,
  },
});

const colorArr = [
  "linear-gradient(-225deg, #7de2fc 0%, #b9b6e5 100%)",
  "linear-gradient(to right, #616161, #9bc5c3)",
  "linear-gradient(to right, #50c9c3, #96deda)",
  "linear-gradient(to right, #215f00, #e4e4d9)",
  "linear-gradient(to right, #c21500, #ffc500)",
];

export const getRandomColor = () => {
  return colorArr[Math.floor(Math.random() * colorArr.length)];
};

type Props = {
  classroom: any;
};
const ClassCard = ({ classroom }: Props) => {
  const classes = useStyles();
  return (
    <Card sx={{ maxWidth: 300, cursor: "pointer" }}>
      <div
        className={classes.header}
        style={{ backgroundImage: getRandomColor() }}
      ></div>
      <CardContent>
        <Typography gutterBottom variant="h5" component="div">
          {classroom.name}
        </Typography>
        <Typography variant="body2" color="text.secondary">
          {classroom.description}
          <br />
          <b>Owner: {classroom.owner.name}</b>
          <br />
          <b>
            Updated at: {new Date(classroom.updatedAt).toLocaleDateString()}
          </b>
        </Typography>
      </CardContent>
    </Card>
  );
};

export default ClassCard;

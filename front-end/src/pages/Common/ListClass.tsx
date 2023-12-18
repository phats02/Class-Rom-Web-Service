import { createUseStyles } from "react-jss";
import ClassCard from "./ClassCard";
import { RootState } from "../../redux-toolkit/store";
import { useSelector } from "react-redux";

const useStyle = createUseStyles({
  container: {
    maxWidth: 1400,
    margin: "0px auto",
    display: "grid",
    gridTemplateColumns: "repeat(auto-fill, 300px)",
    gap: 16,
    justifyContent: "center",
  },
});
const ListClass = () => {
  const classes = useStyle();
  const { listClassRoom } = useSelector(
    (state: RootState) => state.classroomReducer
  );
  return (
    <div className={classes.container}>
      {listClassRoom &&
        listClassRoom.map((item) => (
          <div key={item._id}>
            <ClassCard classroom={item}></ClassCard>
          </div>
        ))}
    </div>
  );
};

export default ListClass;

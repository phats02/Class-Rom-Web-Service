import React, { useEffect, useState } from "react";
import { ClassRoomAPI } from "../../api/classroom";
import { createUseStyles } from "react-jss";
import ClassCard from "../Common/ClassCard";
import { InputLabel, MenuItem, Select, TextField } from "@mui/material";

const useStyle = createUseStyles({
  header: {
    display: "flex",
    justifyContent: "space-between",
    marginBottom: 20,
  },
  container: {
    maxWidth: 1400,
    margin: "0px auto",
    display: "grid",
    gridTemplateColumns: "repeat(auto-fill, 300px)",
    gap: 16,
    justifyContent: "center",
  },
});
export enum FilterUpdatedDate {
  newest = "Newest",
  oldest = "Oldest",
}
const ListClassRoom = () => {
  const classes = useStyle();
  const [queryListClassRoom, setQueryListClassRoom] = useState([]);
  const [listClassRoom, setListClassroom] = useState([]);
  const [searchQuery, setSearchQuery] = useState<string>("");
  const [filterSelect, setFilterSelect] = useState<FilterUpdatedDate>(
    FilterUpdatedDate.newest
  );
  useEffect(() => {
    const fetchListClassRoom = async () => {
      const res = await ClassRoomAPI.getAllClassroom();
      setListClassroom(res.courses);
      setQueryListClassRoom(res.courses);
    };
    fetchListClassRoom();
  }, []);
  const handleOnChangeSearch = (e: any) => {
    setSearchQuery(e.currentTarget.value);
  };

  useEffect(() => {
    setQueryListClassRoom(
      listClassRoom
        .filter((item: any) =>
          item.name.toLowerCase().includes(searchQuery.toLowerCase())
        )
        .sort((a: any, b: any) => {
          const isNewest = b.updatedAt.localeCompare(a.updatedAt);

          return filterSelect === FilterUpdatedDate.newest
            ? isNewest
            : -isNewest;
        })
    );
  }, [searchQuery, filterSelect]);

  return (
    <div>
      <div className={classes.header}>
        <TextField
          onChange={handleOnChangeSearch}
          value={searchQuery}
          placeholder="Filter by name"
        />
        <div>
          <Select
            labelId="demo-simple-select-label"
            id="demo-simple-select"
            value={filterSelect}
            onChange={(e) => {
              setFilterSelect(e.target?.value as FilterUpdatedDate);
            }}
          >
            <MenuItem value={FilterUpdatedDate.newest}>
              {FilterUpdatedDate.newest}
            </MenuItem>
            <MenuItem value={FilterUpdatedDate.oldest}>
              {FilterUpdatedDate.oldest}
            </MenuItem>
          </Select>
        </div>
      </div>

      <div className={classes.container}>
        {queryListClassRoom &&
          queryListClassRoom.map((item: any) => (
            <div key={item._id}>
              <ClassCard classroom={item}></ClassCard>
            </div>
          ))}
      </div>
    </div>
  );
};

export default ListClassRoom;

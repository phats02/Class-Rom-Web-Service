import React from "react";
import { User } from "../../../../redux-toolkit/slice/auth.slice";
import {
  Box,
  Divider,
  IconButton,
  List,
  ListItem,
  ListItemAvatar,
  Typography,
} from "@mui/material";
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";

interface Props {
  role: string;
  isCanAdd: boolean;
  users: User[];
  onAddClick: () => void;
}
function UserInfoRow({ role, isCanAdd, users, onAddClick }: Props) {
  return (
    <>
      <List
        sx={{
          maxWidth: 850,
          bgcolor: "background.paper",
          margin: "auto",
          mt: 1,
        }}
      >
        <Box sx={{ display: "flex", md: 1 }}>
          <Typography variant="h4" sx={{}}>
            {role}
          </Typography>
          {isCanAdd && (
            <IconButton
              onClick={() => {
                onAddClick();
              }}
              sx={{ ml: "auto" }}
            >
              <PersonAddIcon />
            </IconButton>
          )}
        </Box>
        <Divider color="black" />
        {users.map((user, index) => (
          <React.Fragment key={user._id}>
            <ListItem alignItems="flex-start">
              <ListItemAvatar>
                <AccountCircleIcon fontSize={"large"} />
              </ListItemAvatar>
              <Typography variant="body2" color="text.secondary" sx={{ mt: 2 }}>
                {user.name}
              </Typography>
            </ListItem>
            <Divider />
          </React.Fragment>
        ))}
      </List>
    </>
  );
}

export default UserInfoRow;

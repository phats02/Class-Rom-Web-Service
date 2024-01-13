import * as React from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import IconButton from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";
import Menu from "@mui/material/Menu";
import Container from "@mui/material/Container";
import Button from "@mui/material/Button";
import Tooltip from "@mui/material/Tooltip";
import MenuItem from "@mui/material/MenuItem";
import AdbIcon from "@mui/icons-material/Adb";
import { createUseStyles } from "react-jss";
import { Link } from "react-router-dom";
import { User } from "../../redux-toolkit/slice/auth.slice";
import { useAppDispatch } from "../../redux-toolkit/store";
import { fetchAllNotification } from "../../redux-toolkit/slice/notification.slice";
import Notification from "./Notification";
import { io } from "socket.io-client";
import { ACCESS_TOKEN, API_BASE_URL } from "../../utils/constant";

const pages = ["My Class"];

const settingRoute = [
  {
    label: "Profile",
    url: "/profile",
  },
  {
    label: "Logout",
    url: "/logout",
  },
];

type TProps = {
  currentUser: User | null;
};

const useStyle = createUseStyles({
  textAvatar: {
    width: 50,
    height: 50,
    margin: "0px auto",
    verticalAlign: "middle",
    textAlign: "center",
    borderRadius: "50%",
    backgroundImage:
      "linear-gradient(45deg,#46b5e5 1%,#1e88e5 64%,#40baf5 97%)",
    "& > span": {
      lineHeight: "50px",
      color: "#FFF",
      fontSize: "1rem",
    },
  },
});
function NavBars({ currentUser }: TProps) {
  const classes = useStyle();

  const storeDispatch = useAppDispatch();
  const socket = React.useRef<any>(null);

  const [anchorElNav, setAnchorElNav] = React.useState<null | HTMLElement>(
    null
  );
  const [anchorElUser, setAnchorElUser] = React.useState<null | HTMLElement>(
    null
  );

  const handleOpenUserMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorElUser(event.currentTarget);
  };

  const handleCloseNavMenu = () => {
    setAnchorElNav(null);
  };

  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };

  React.useEffect(() => {
    storeDispatch(fetchAllNotification());
    const accessToken = localStorage.getItem(ACCESS_TOKEN);
    if (socket.current !== null) return;
    socket.current = io(API_BASE_URL || "");
    socket.current.emit("authenticate", { token: accessToken });
    socket.current.on("notice", () => {
      console.log("New notification");
      storeDispatch(fetchAllNotification());
    });
    return () => {
      socket.current.disconnect();
      socket.current = null;
    };
  }, []);

  return (
    <AppBar position="static">
      <Container maxWidth="xl">
        <Toolbar disableGutters>
          <AdbIcon sx={{ display: { xs: "none", md: "flex" }, mr: 1 }} />
          <Typography
            variant="h6"
            noWrap
            component="a"
            href="/home"
            sx={{
              mr: 2,
              display: { xs: "none", md: "flex" },
              fontFamily: "monospace",
              fontWeight: 700,
              letterSpacing: ".3rem",
              color: "inherit",
              textDecoration: "none",
            }}
          >
            Grad
          </Typography>

          <Box sx={{ flexGrow: 1, display: { xs: "flex", md: "none" } }}>
            <Menu
              id="menu-appbar"
              anchorEl={anchorElNav}
              anchorOrigin={{
                vertical: "bottom",
                horizontal: "left",
              }}
              keepMounted
              transformOrigin={{
                vertical: "top",
                horizontal: "left",
              }}
              open={Boolean(anchorElNav)}
              onClose={handleCloseNavMenu}
              sx={{
                display: { xs: "block", md: "none" },
              }}
            >
              {pages.map((page) => (
                <MenuItem key={page} onClick={handleCloseNavMenu}>
                  <Typography textAlign="center">{page}</Typography>
                </MenuItem>
              ))}
            </Menu>
          </Box>
          <AdbIcon sx={{ display: { xs: "flex", md: "none" }, mr: 1 }} />
          <Typography
            variant="h5"
            noWrap
            component="a"
            href="#app-bar-with-responsive-menu"
            sx={{
              mr: 2,
              display: { xs: "flex", md: "none" },
              flexGrow: 1,
              fontFamily: "monospace",
              fontWeight: 700,
              letterSpacing: ".3rem",
              color: "inherit",
              textDecoration: "none",
            }}
          >
            LOGO
          </Typography>
          <Box sx={{ flexGrow: 1, display: { xs: "none", md: "flex" } }}>
            {pages.map((page) => (
              <Button
                key={page}
                onClick={handleCloseNavMenu}
                sx={{ my: 2, color: "white", display: "block" }}
              >
                {page}
              </Button>
            ))}
          </Box>

          <div style={{ display: "flex", alignItems: "center", gap: 25 }}>
            <Notification />
            <Tooltip title="Open settings">
              <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                <div className="profile-avatar">
                  <div className={classes.textAvatar}>
                    <span>{currentUser?.name && currentUser?.name[0]}</span>
                  </div>
                </div>
              </IconButton>
            </Tooltip>
            <Menu
              sx={{ mt: "45px" }}
              id="menu-appbar"
              anchorEl={anchorElUser}
              anchorOrigin={{
                vertical: "top",
                horizontal: "right",
              }}
              keepMounted
              transformOrigin={{
                vertical: "top",
                horizontal: "right",
              }}
              open={Boolean(anchorElUser)}
              onClose={handleCloseUserMenu}
            >
              {settingRoute.map((setting) => (
                <MenuItem key={setting.label} onClick={handleCloseUserMenu}>
                  <Link to={setting.url} style={{ textDecoration: "none" }}>
                    <Typography textAlign="center">{setting.label}</Typography>
                  </Link>
                </MenuItem>
              ))}
            </Menu>
          </div>
        </Toolbar>
      </Container>
    </AppBar>
  );
}
export default NavBars;

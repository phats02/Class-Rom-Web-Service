import { Box, Tab, Tabs, Typography } from "@mui/material";
import { useState } from "react";
import SwipeableViews from "react-swipeable-views";
import Setting from "../Setting";
import UserManager from "../UserManager";
import ListClassRoom from "../Classroom";

interface TabPanelProps {
  children?: React.ReactNode;
  dir?: string;
  index: number;
  value: number;
}

function TabPanel(props: TabPanelProps) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`full-width-tabppanel-${index}`}
      aria-labelledby={`full-width-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box sx={{ p: 3 }}>
          <Typography>{children}</Typography>
        </Box>
      )}
    </div>
  );
}

function a11yProps(index: number) {
  return {
    id: `full-width-tab-${index}`,
    "aria-controls": `full-width-tabpanel-${index}`,
  };
}

const Home = () => {
  const [value, setValue] = useState(0);

  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
    setValue(newValue);
  };

  const handleChangeIndex = (index: number) => {
    setValue(index);
  };

  return (
    <Box sx={{ width: "100%" }}>
      <Box sx={{ borderBottom: 1, borderColor: "divider" }}>
        <Tabs
          value={value}
          onChange={handleChange}
          indicatorColor="primary"
          textColor="inherit"
        >
          <Tab label="User Manager" {...a11yProps(0)} />
          <Tab label="ClassRoom Manager" {...a11yProps(1)} />
          <Tab label="Setting" {...a11yProps(2)} />
        </Tabs>
      </Box>
      <SwipeableViews index={value} onChangeIndex={handleChangeIndex}>
        <TabPanel value={value} index={0}>
          <UserManager />
        </TabPanel>
        <TabPanel value={value} index={1}>
          <ListClassRoom />
        </TabPanel>
        <TabPanel value={value} index={2}>
          <Setting></Setting>
        </TabPanel>
      </SwipeableViews>
    </Box>
  );
};

export default Home;

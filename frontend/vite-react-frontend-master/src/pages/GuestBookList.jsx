import * as React from "react";

import Paper from "@mui/material/Paper";

import ListRegisterBtn from "../components/list/ListRegisterBtn";
import Orders from "../components/list/Orders";

const GuestBookList = () => {
  return (
    <Paper sx={{ p: 2, display: "flex", flexDirection: "column" }}>
      <Orders />
      <ListRegisterBtn />
    </Paper>
  );
};

export default GuestBookList;

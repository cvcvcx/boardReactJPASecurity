import * as React from "react";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import DashboardIcon from "@mui/icons-material/Dashboard";
import ShoppingCartIcon from "@mui/icons-material/ShoppingCart";
import PeopleIcon from "@mui/icons-material/People";
import { useNavigate } from "react-router-dom";

export const MenuList = () => {
  const navigate = useNavigate();

  const onClickBoardButton = () => {
    navigate("/");
  };
  const onClickLoginButton = () => {
    navigate("/login");
  };
  const onClickLogoutButton = () => {
    alert("로그아웃");
  };

  const mainListItems = (
    <React.Fragment>
      <ListItemButton onClick={onClickBoardButton}>
        <ListItemIcon>
          <DashboardIcon />
        </ListItemIcon>
        <ListItemText primary="게시물" />
      </ListItemButton>
      <ListItemButton onClick={onClickLoginButton}>
        <ListItemIcon>
          <PeopleIcon />
        </ListItemIcon>
        <ListItemText primary="로그인" />
      </ListItemButton>
      <ListItemButton onClick={onClickLogoutButton}>
        <ListItemIcon>
          <PeopleIcon />
        </ListItemIcon>
        <ListItemText primary="로그아웃" />
      </ListItemButton>
    </React.Fragment>
  );

  return mainListItems;
};

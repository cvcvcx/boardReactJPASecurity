import { Button, Grid } from "@mui/material";
import React from "react";
import { useNavigate } from "react-router-dom";

const ListRegisterBtn = () => {
  const navigate = useNavigate();
  const handleRegisterBtn = () => {
    navigate("/register");
  };
  return (
    <Grid container justifyContent="flex-end">
      <Button
        variant="contained"
        sx={{ mt: 1, maxWidth: 120 }}
        onClick={handleRegisterBtn}>
        제품등록
      </Button>
    </Grid>
  );
};

export default ListRegisterBtn;

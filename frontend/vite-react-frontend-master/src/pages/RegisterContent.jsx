import { Button, Paper, TextField } from "@mui/material";
import axios from "axios";
import React from "react";
import { Controller, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { axiosRequest } from "../util/request/requestService";

const RegisterContent = () => {
  const { control, handleSubmit } = useForm();
  // const apiUrl = import.meta.env.VITE_PRODUCTION_API_URL;
  const navigate = useNavigate();
  const handleOnSubmit = (event) => {
    axiosRequest(
      `/board/register`,
      "post",
      JSON.stringify(event),
      "application/json"
    )
      .then((res) => {
        alert("등록된 게시글 번호" + res.data);
        navigate("/");
      })
      .catch((e) => {
        console.log(e.message);
        alert("에러가 발생하였습니다.");
        navigate("/");
      });
  };
  const handleOnClickGoBackBtn = () => {
    let isCancel = window.confirm(
      "정말 뒤로가시겠습니까? 지금까지 작성한 글이 모두 지워집니다."
    );
    if (isCancel) {
      navigate("/");
    } else {
      return;
    }
  };

  return (
    <Paper sx={{ p: 2, display: "flex", flexDirection: "column" }}>
      <h1>게시글 작성 화면</h1>
      <form onSubmit={handleSubmit(handleOnSubmit)}>
        <h3>제목</h3>
        <Controller
          control={control}
          defaultValue={""}
          rules={{ required: true }}
          name="title"
          render={({ field }) => (
            <TextField
              label="title"
              fullWidth
              {...field}
              inputProps={{ maxLength: 100 }}
            />
          )}
        />

        <h3>내용</h3>
        <Controller
          control={control}
          defaultValue={""}
          rules={{ required: true }}
          name="content"
          render={({ field }) => (
            <TextField
              {...field}
              label="content"
              fullWidth
              multiline
              rows={15}
              inputProps={{ maxLength: 1000 }}
            />
          )}
        />

        <Button type="submit" variant="contained" sx={{ mt: 2, maxWidth: 120 }}>
          등록
        </Button>
        <Button
          variant="contained"
          color="error"
          onClick={handleOnClickGoBackBtn}
          sx={{ mt: 2, ml: 2, maxWidth: 120 }}>
          뒤로가기
        </Button>
      </form>
    </Paper>
  );
};

export default RegisterContent;

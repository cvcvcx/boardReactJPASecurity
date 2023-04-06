import { Button, Paper, TextField } from "@mui/material";
import axios from "axios";
import React from "react";
import { Controller, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";

const RegisterContent = () => {
  const { control, handleSubmit } = useForm();
  const apiUrl = import.meta.env.VITE_PRODUCTION_API_URL;
  const navigate = useNavigate();
  const handleOnSubmit = (event) => {
    console.log(event);
    axios
      .post(`${apiUrl}/board/register`, JSON.stringify(event), {
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((res) => {
        alert("등록된 게시글 번호" + res.data);
        navigate("/list");
      });
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
            <TextField label="title" fullWidth {...field} />
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
            />
          )}
        />

        <h3>작성자</h3>
        <Controller
          control={control}
          defaultValue={""}
          rules={{ required: true }}
          name="writerEmail"
          render={({ field }) => (
            <TextField {...field} label="writerEmail" fullWidth />
          )}
        />

        <Button type="submit" variant="contained" sx={{ mt: 2, maxWidth: 120 }}>
          등록
        </Button>
      </form>
    </Paper>
  );
};

export default RegisterContent;

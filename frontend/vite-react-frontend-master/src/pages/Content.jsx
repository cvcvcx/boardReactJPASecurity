import { Button, Paper, TextField } from "@mui/material";
import { useQuery } from "@tanstack/react-query";
import axios from "axios";
import React, { useEffect, useState } from "react";
import { Controller, useForm } from "react-hook-form";
import { useLocation, useNavigate } from "react-router-dom";
import ReplyForm from "../components/reply/ReplyForm";

const Content = () => {
  const { control, handleSubmit, reset } = useForm();
  // 주석을 추가한다
  const navigate = useNavigate();
  const { state } = useLocation();
  const [content, setContent] = useState({});
  const [modifyMode, setModifyMode] = useState(false);

  const apiUrl = import.meta.env.VITE_PRODUCTION_API_URL;

  let disableModifyTime;

  const queryFetch = useQuery(
    ["readContent"],
    () => axios.get(`${apiUrl}/board/read/?id=${state}`),
    {
      refetchOnWindowFocus: false,
      retry: 0,
      onSuccess: (res) => {
        setContent(() => res.data);
        reset(res.data);
      },
      onError: (e) => {
        console.log(e.message);
      },
    }
  );
  useEffect(() => {
    return () => {
      clearTimeout(disableModifyTime);
    };
  }, []);

  const handleOnClickModify = () => {
    disableModifyTime = setTimeout(() => {
      setModifyMode(true);
    }, 50);
  };

  const handleOnClickSubmit = (data) => {
    axios
      .post(`${apiUrl}/board/modify/?id=${state}`, data, {
        headers: {
          "Content-Type": "application/json;",
        },
      })
      .then((res) => {
        console.log(data);
        alert("수정완료");
        navigate("/list");
      })
      .catch((e) => {
        console.log(e.message);
      });
  };
  const handleOnClickDelete = () => {
    axios
      .post(`${apiUrl}/board/delete/?id=${state}`, {
        headers: {
          "Content-Type": "application/json;",
        },
      })
      .then((res) => {
        console.log(state);
        alert("삭제완료");
        navigate("/list");
      })
      .catch((e) => {
        console.log(e.message);
      });
  };
  const handleOnClickListBtn = () => {
    navigate("/list");
  };

  return (
    <Paper sx={{ p: 2, display: "flex", flexDirection: "column" }}>
      <h1>게시글 조회 화면</h1>
      <form onSubmit={handleSubmit(handleOnClickSubmit)}>
        <h3>제목</h3>
        <Controller
          control={control}
          rules={{ required: true }}
          defaultValue={""}
          name="title"
          render={({ field }) => (
            <TextField
              label="title"
              inputProps={{ readOnly: !modifyMode }}
              fullWidth
              {...field}
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
              inputProps={{ readOnly: !modifyMode }}
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
          name="writerName"
          render={({ field }) => (
            <TextField
              {...field}
              inputProps={{ readOnly: true }}
              label="writer"
              fullWidth
            />
          )}
        />
        <h3>작성일자</h3>
        <Controller
          control={control}
          defaultValue={""}
          rules={{ required: true }}
          name="formattedRegDate"
          render={({ field }) => (
            <TextField
              {...field}
              inputProps={{ readOnly: true }}
              label="작성일자"
              fullWidth
            />
          )}
        />
        <h3>수정일자</h3>
        <Controller
          control={control}
          defaultValue={""}
          rules={{ required: true }}
          name="formattedModDate"
          render={({ field }) => (
            <TextField
              {...field}
              inputProps={{ readOnly: true }}
              label="수정일자"
              fullWidth
            />
          )}
        />

        {modifyMode ? (
          <Button
            variant="contained"
            type="submit"
            sx={{ mt: 2, maxWidth: 120 }}>
            수정완료
          </Button>
        ) : (
          <Button
            onClick={handleOnClickModify}
            variant="contained"
            sx={{ mt: 2, maxWidth: 120 }}>
            수정
          </Button>
        )}
        <Button
          onClick={handleOnClickDelete}
          variant="contained"
          color="error"
          sx={{ mt: 2, ml: 2, maxWidth: 120 }}>
          삭제
        </Button>
        <Button
          onClick={handleOnClickListBtn}
          variant="contained"
          color="success"
          sx={{ mt: 2, ml: 2, maxWidth: 120 }}>
          목록
        </Button>
      </form>
      <ReplyForm bno={content.bno} />
    </Paper>
  );
};

export default Content;

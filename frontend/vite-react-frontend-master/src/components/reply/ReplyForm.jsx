import { Button, TextField } from "@mui/material";
import axios from "axios";
import React, { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import ReplyCard from "./ReplyCard";

const ReplyForm = ({ bno }) => {
  const navigate = useNavigate();
  const {
    register,
    formState: { errors },
    handleSubmit,
  } = useForm();
  const [replies, setReplies] = useState([]);
  const apiUrl = import.meta.env.VITE_PRODUCTION_API_URL;
  useEffect(() => {
    if (bno != null) {
      axios.get(`${apiUrl}/replies/board/${bno}`).then((res) => {
        setReplies(res.data);
      });
    }
  }, [bno, replies.length]);

  const handleReplyOnSubmit = (data) => {
    const postData = { ...data, bno: bno };
    axios
      .post(`${apiUrl}/replies/`, JSON.stringify(postData), {
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
        },
      })
      .then((res) => {
        alert(res.data);
        navigate(0);
      });
  };
  return (
    <div>
      <form onSubmit={handleSubmit(handleReplyOnSubmit)}>
        <h3>댓글</h3>
        <TextField
          rows={3}
          label="comments"
          name="text"
          {...register("text", {
            required: "댓글 내용은 필수입력항목입니다.",
          })}
          fullWidth
          error={errors.text}
          defaultValue=""
          helperText={errors.text?.message}
          multiline
        />
        <TextField
          label="작성자"
          sx={{ mt: 1 }}
          name="replyer"
          defaultValue=""
          {...register("replyer", {
            required: "작성자는 필수입력항목입니다.",
          })}
          error={errors.replyer}
          helperText={errors.replyer?.message}
        />
        <div style={{ display: "flex", justifyContent: "flex-end" }}>
          <Button sx={{ mt: 2 }} variant="contained" type="submit">
            등록
          </Button>
        </div>
      </form>
      {/* 아래 reverse를 통해 최근 등록된 댓글이 가장 위로 올 수 있도록함 */}
      {[...replies].reverse().map((item) => (
        <ReplyCard
          key={item.rno}
          data={{ ...item, bno }}
          setReplies={setReplies}
        />
      ))}
    </div>
  );
};

export default ReplyForm;

import { Button, TextField } from "@mui/material";
import axios from "axios";
import React, { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { axiosRequest } from "../../util/request/requestService";
import ReplyCard from "./ReplyCard";

const ReplyForm = ({ bno }) => {
  const navigate = useNavigate();

  const {
    register,
    formState: { errors },
    handleSubmit,
  } = useForm();
  const [replies, setReplies] = useState([]);
  useEffect(() => {
    if (bno != null) {
      axiosRequest(`/replies/board/${bno}`, "get")
        .then((res) => {
          setReplies(res.data);
        })
        .catch((e) => {
          alert(e.message);
        });
    }
  }, [bno]);

  const handleReplyOnSubmit = (data) => {
    const postData = { ...data, bno: bno };
    axiosRequest(
      `/replies`,
      "post",
      JSON.stringify(postData),
      "application/json; charset=UTF-8"
    )
      .then((res) => {
        alert(res.data);
        navigate(0);
      })
      .catch((e) => {
        alert(e.message);
      });
  };
  return (
    <div>
      {sessionStorage.getItem("userEmail") == null ? (
        <></>
      ) : (
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
          <div style={{ display: "flex", justifyContent: "flex-end" }}>
            <Button sx={{ mt: 2 }} variant="contained" type="submit">
              등록
            </Button>
          </div>
        </form>
      )}

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

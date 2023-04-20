import {
  Button,
  Card,
  CardActions,
  CardContent,
  Modal,
  TextField,
  Typography,
} from "@mui/material";
import { Box } from "@mui/system";
import axios from "axios";
import moment from "moment";
import React, { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { axiosRequest } from "../../util/request/requestService";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 400,
  bgcolor: "background.paper",
  border: "2px solid #000",
  boxShadow: 24,
  p: 4,
};

const ReplyCard = ({ data, setReplies }) => {
  const [isModify, setIsModify] = useState(false);
  const [isMyReply, setIsMyReply] = useState(false);
  const navigate = useNavigate();
  const apiUrl = import.meta.env.VITE_PRODUCTION_API_URL;

  const {
    register,
    formState: { errors },
    handleSubmit,
  } = useForm();
  const userEmail = sessionStorage.getItem("userEmail");
  useEffect(() => {
    if (userEmail == data.replyer) {
      setIsMyReply(true);
    } else {
      setIsMyReply(false);
    }
  }, [userEmail]);
  const handleOnClickReplyModifyBtn = () => {
    setIsModify(true);
  };
  const handleModalClose = () => {
    setIsModify(false);
  };
  const handleOnClickReplyDeleteBtn = () => {
    axiosRequest(`/replies/${data.rno}`, "delete")
      .then((res) => {
        alert("삭제성공!");
        setReplies((prev) => prev.filter((i) => i.rno !== data.rno));
      })
      .catch((e) => {
        if (e.response.status === 500) {
          alert("잘못된 요청입니다");
        } else if (e.response.status === 400) {
          alert("권한이 없습니다.");
        }
      });
  };

  const handleReplyOnSubmit = (formData) => {
    const postData = { ...formData, rno: data.rno, bno: data.bno };
    console.log(postData);
    axiosRequest(
      `/replies/${data.rno}`,
      "put",
      JSON.stringify(postData),
      "application/json; charset=UTF-8"
    ).then((res) => {
      alert(res.data);
      navigate(0);
    });
  };

  return (
    <>
      <Card variant="outlined" sx={{ minWidth: 275, mt: 1 }}>
        <CardContent>
          <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
            {data.rno}
          </Typography>
          <Typography variant="h5" component="div">
            {data.text}
          </Typography>
          <Typography sx={{ mb: 1.5 }} color="text.secondary"></Typography>
          <Typography variant="body2">{data.replyer}</Typography>
          <Typography variant="body2">
            {moment(data.modDate).format("YYYY년 MM월 DD일")}
          </Typography>
        </CardContent>
        {isMyReply ? (
          <CardActions sx={{ display: "flex", justifyContent: "end" }}>
            <Button
              size="small"
              variant="contained"
              color="success"
              onClick={handleOnClickReplyModifyBtn}>
              수정
            </Button>
            <Button
              size="small"
              variant="contained"
              color="error"
              onClick={handleOnClickReplyDeleteBtn}>
              삭제
            </Button>
          </CardActions>
        ) : (
          <></>
        )}
      </Card>
      <Modal open={isModify} onClose={handleModalClose}>
        <Box sx={style}>
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
              defaultValue={data.text}
              helperText={errors.text?.message}
              multiline
            />
            <div style={{ display: "flex", justifyContent: "flex-end" }}>
              <Button sx={{ mt: 2 }} variant="contained" type="submit">
                등록
              </Button>
            </div>
          </form>
        </Box>
      </Modal>
    </>
  );
};

export default ReplyCard;

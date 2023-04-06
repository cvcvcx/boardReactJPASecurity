import {
  Box,
  Button,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  TextField,
} from "@mui/material";
import axios from "axios";
import React from "react";
import { Controller, useForm } from "react-hook-form";

const Search = ({
  size,
  currentPage,
  searchType,
  searchKeyword,
  setList,
  setTotalPage,
  setSearchType,
  setSearchKeyword,
}) => {
  const apiUrl = import.meta.env.VITE_PRODUCTION_API_URL;
  const { control, handleSubmit } = useForm();
  const handleSearchBtnClick = (data) => {
    setSearchKeyword((prev) => data.keyword);
    setSearchType((prev) => data.type);
    axios
      .get(`${apiUrl}/board/list/`, {
        params: {
          size: size,
          page: currentPage,
          type: searchType,
          keyword: searchKeyword,
        },
      })
      .then((result) => {
        setList((prev) => result.data.dtoList);
        setTotalPage((prev) => result.data.totalPage);
      });
  };
  return (
    <form onSubmit={handleSubmit(handleSearchBtnClick)}>
      <Box display="flex" justifyContent="right">
        <FormControl size="small" sx={{ mt: 2, mr: 2 }}>
          <InputLabel id="search-type-select-label">타입</InputLabel>
          <Controller
            name="type"
            control={control}
            defaultValue="t"
            render={({ field }) => (
              <Select
                {...field}
                labelId="search-type-select-label"
                id="search-type-select"
                label="type">
                <MenuItem value={"t"}>제목</MenuItem>
                <MenuItem value={"c"}>내용</MenuItem>
                <MenuItem value={"w"}>작성자</MenuItem>
                <MenuItem value={"tc"}>제목+내용</MenuItem>
                <MenuItem value={"tcw"}>제목+내용+작성자</MenuItem>
              </Select>
            )}
          />
        </FormControl>
        <Controller
          name="keyword"
          control={control}
          defaultValue=""
          render={({ field }) => (
            <TextField
              {...field}
              id="keyword"
              name="keyword"
              type="search"
              size="small"
              label="검색할 내용을 입력하세요"
              variant="standard"
              sx={{ mt: 1 }}
            />
          )}
        />
        <Button
          type="submit"
          variant="outlined"
          sx={{ ml: 2, mt: 2 }}
          size="small">
          검색
        </Button>
      </Box>
    </form>
  );
};

export default Search;

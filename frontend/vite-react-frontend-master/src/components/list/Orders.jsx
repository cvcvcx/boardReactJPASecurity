import {
  Box,
  FormControl,
  Grid,
  InputLabel,
  MenuItem,
  Pagination,
  Select,
} from "@mui/material";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import axios from "axios";
import { Fragment, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Title from "../Title";
import Search from "./Search";

export default function Orders() {
  const [list, setList] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [pageSize, setPageSize] = useState(15);
  const [totalPage, setTotalPage] = useState(1);
  const [searchType, setSearchType] = useState("");
  const [searchKeyword, setSearchKeyword] = useState("");
  const navigate = useNavigate();
  const apiUrl = import.meta.env.VITE_PRODUCTION_API_URL;
  const getGuestBookDateList = () => {
    axios
      .get(
        `${apiUrl}/board/list?page=${currentPage}&size=${pageSize}&type=${searchType}&keyword=${searchKeyword}`
      )
      .then((result) => {
        setList((prev) => result.data.dtoList);
        setTotalPage((prev) => result.data.totalPage);
      });
  };
  //검색항목이나, 크기가 달라졌을때 다시 페이지를 로딩
  useEffect(getGuestBookDateList, [
    currentPage,
    pageSize,
    searchKeyword,
    searchType,
  ]);
  const handlePage = (event, page) => {
    const nowPageInt = parseInt(page);
    setCurrentPage(nowPageInt);
  };
  const handlePageSize = (event) => {
    setPageSize(event.target.value);
  };

  const handleIdClick = (id) => {
    navigate("/read", { state: id });
  };
  return (
    <Fragment>
      <Grid container justifyContent="space-between">
        <Title>제품 리스트</Title>
        <FormControl fullWidth sx={{ maxWidth: "120px" }}>
          <InputLabel id="page-size-label">게시글 수</InputLabel>
          <Select
            labelId="page-size-label"
            id="page-size-select"
            value={pageSize}
            label="page-size"
            onChange={handlePageSize}>
            <MenuItem value={10}>10</MenuItem>
            <MenuItem value={15}>15</MenuItem>
            <MenuItem value={20}>20</MenuItem>
          </Select>
        </FormControl>
      </Grid>
      <Table size="small">
        <TableHead>
          <TableRow>
            <TableCell width="15%">#</TableCell>
            <TableCell width="35%">제목</TableCell>
            <TableCell width="5%"></TableCell>
            <TableCell width="10%">작성자</TableCell>
            <TableCell width="20%" align="right">
              작성일자
            </TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {list.map((item) => (
            <TableRow
              hover
              key={item.bno}
              onClick={() => {
                handleIdClick(item.bno);
              }}>
              <TableCell>{item.bno}</TableCell>
              <TableCell>{item.title}</TableCell>
              <TableCell>
                <b>[{item.replyCount}]</b>
              </TableCell>
              <TableCell>{item.writerName}</TableCell>
              <TableCell align="right">{item.formattedRegDate}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
      <Search
        size={pageSize}
        currentPage={currentPage}
        setList={setList}
        setTotalPage={setTotalPage}
        searchType={searchType}
        setSearchType={setSearchType}
        searchKeyword={searchKeyword}
        setSearchKeyword={setSearchKeyword}
      />
      <Box
        display="flex"
        alignItems="center"
        justifyContent="center"
        sx={{ mt: 2 }}>
        <Pagination
          count={totalPage}
          showFirstButton
          showLastButton
          onChange={handlePage}
        />
      </Box>
    </Fragment>
  );
}

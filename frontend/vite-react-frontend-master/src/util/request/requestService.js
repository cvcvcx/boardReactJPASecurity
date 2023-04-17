import axios from "axios";

export function axiosRequest(path, method, data) {
  return axios({
    method: method,
    url: "http://127.0.0.1:8080/" + path,
    data: {
      ...data,
    },
  });
}

import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allBooks() {
    return HTTP.get(BASE_URL + "/book", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(book) {
    return HTTP.post(BASE_URL + "/book", book, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  edit(book) {
    return HTTP.patch(BASE_URL + "/book", book, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  delete(book) {
    return HTTP.delete(BASE_URL + "/book/" + book, { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
    );
  },
};

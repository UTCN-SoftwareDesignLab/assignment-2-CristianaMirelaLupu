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
    deleteById(id) {
        console.log("id: " + id)
        return HTTP.delete(BASE_URL + "/book/" + id , {headers: authHeader()}).then(
            () => {
                return true;
            }
        );
    },

    pdf(){
        return HTTP.get(BASE_URL + "/book/export/PDF", { headers: authHeader() }).then(
            (response) => {
                return response.data;
            }
        );
    },


    csv(){
        return HTTP.get(BASE_URL + "/book/export/CSV", { headers: authHeader() }).then(
            (response) => {
                return response.data;
            }
        );
    }


};

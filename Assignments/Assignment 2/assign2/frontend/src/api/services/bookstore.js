import authHeader, { BASE_URL, HTTP } from "../http";

export default {
    sell(book) {
        return HTTP.patch(BASE_URL + "/bookstore", book,{headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
    search(description) {
        return HTTP.get(BASE_URL + "/bookstore/" + description,{headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
}
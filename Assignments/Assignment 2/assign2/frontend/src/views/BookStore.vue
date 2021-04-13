<template>
  <v-card>
    <v-card-title>
      Book Store
      <v-spacer></v-spacer>
      <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Search"
          single-line
          hide-details
      ></v-text-field>

    </v-card-title>
    <v-data-table
        :headers="headers"
        :items="books"
        :search="search"
        @click:row="sellBook"
    ></v-data-table>
    <BookstoreDialog
        :opened="dialogVisible"
        :book="selectedBook"
        @refresh="refreshList"
    ></BookstoreDialog>
  </v-card>
</template>

<script>
import api from "../api";
import BookstoreDialog from "../components/BookstoreDialog";
//import router from "@/router";

export default {
  name: "BookStore",
  components: { BookstoreDialog },
  data() {
    return {
      books: [],
      search: "",
      headers: [
        {
          text: "Title",
          align: "start",
          sortable: false,
          value: "title",
        },
        { text: "Author", value: "author" },
        { text: "Genre", value: "genre" },
        { text: "Price", value: "price" },
        { text: "Quantity", value: "quantity" },
      ],
      dialogVisible: false,
      selectedBook: {},
    };
  },
  methods: {
    sellBook(book) {
      this.selectedBook = book;
      this.dialogVisible = true;
    },

    async refreshList() {
      this.dialogVisible = false;
      this.selectedBook = {};
      this.books = await api.books.allBooks();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>

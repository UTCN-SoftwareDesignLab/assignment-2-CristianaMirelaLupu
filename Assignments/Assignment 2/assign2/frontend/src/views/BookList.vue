<template>
  <v-card>
    <v-card-title>
      Books
      <v-spacer></v-spacer>

      <v-btn @click="addBook">Add Book</v-btn>
      <v-btn @click="goToUsers">Go to Users</v-btn>

      <v-btn @click="pdfBook">PDF</v-btn>
      <v-btn @click="csvBook">CSV</v-btn>

    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="books"
      :search="search"
      @click:row="editBook"
    ></v-data-table>
    <BookDialog
      :opened="dialogVisible"
      :book="selectedBook"
      @refresh="refreshList"
    ></BookDialog>
  </v-card>
</template>

<script>
import api from "../api";
import BookDialog from "../components/BookDialog";
import router from "@/router";

export default {
  name: "BookList",
  components: { BookDialog },
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
    editBook(book) {
      this.selectedBook = book;
      this.dialogVisible = true;
    },
    addBook() {
      this.dialogVisible = true;
    },

    goToUsers() {
      router.push("/users");
    },

    pdfBook(){
      api.books.pdf();
    },

    csvBook(){
      api.books.csv();
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

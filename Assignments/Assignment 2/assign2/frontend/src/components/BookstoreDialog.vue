<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{"Sell book" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="book.title" label="Title" />
          <v-text-field v-model="book.author" label="Author" />
          <v-text-field v-model="book.genre" label="Genre" />
          <v-text-field v-model="book.price" label="Price" />
          <v-text-field v-model="book.quantity" label="Quantity" />
          <v-text-field v-model="amount" label="Amount to sell" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{"Sell book" }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "BookstoreDialog",
  props: {
    book:Object,
    amount: Number,
    opened: Boolean,
  },
  methods: {
    persist() {

      if(this.amount <= this.book.quantity) {
        api.bookstore
            .sell({
              id: this.book.id,
              title: this.book.title,
              author: this.book.author,
              genre: this.book.genre,
              price: this.book.price,
              quantity: this.book.quantity,
            }, this.amount)
            .then(() => this.$emit("refresh"));
      }
      else{
        this.$alert("There are not enough books in the stock!")
      .then(() => this.$emit("refresh"));

      }
    },
  },
};
</script>

<style scoped></style>

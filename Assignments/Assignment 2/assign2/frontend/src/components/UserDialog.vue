<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create user" : "Edit user" }}

        </v-toolbar>
        <v-form >
          <v-text-field v-model="user.username" label="Username" />
          <v-text-field v-model="user.email" label="Email" />
          <v-text-field type="password" v-model="user.password" label="Password" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>

          <v-btn v-if="!isNew" @click="deletion">Delete</v-btn>

        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "UserDialog",
  props: {
    user: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      console.log("On click");
      if (this.isNew) {
        console.log("heiiiii");
        api.users
            .create({
              username: this.user.username,
              email: this.user.email,
              password: this.user.password,
              roles: ["EMPLOYEE"]
            })
            .then(() => this.$emit("refresh"));

      } else {
        console.log("here");
        api.users
            .edit({
              id: this.user.id,
              username: this.user.username,
              email: this.user.email,
              password: this.user.password,
            })
      }
    },

  deletion() {
      //console.log("heiiiii");
      //console.log(this.user)
      api.users.deleteById(this.user.id)
          .then((response) => {
            if (response == true)
             this.$emit("refresh")
          }
          );

  }
  },

  computed: {
    isNew: function () {
      console.log(this.user)
      return !this.user.id
    },
  },
};
</script>

<style scoped></style>

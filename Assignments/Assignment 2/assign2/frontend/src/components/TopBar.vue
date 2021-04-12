<template>
  <v-card color="grey lighten-4" flat tile>
    <v-toolbar>
      <v-app-bar-nav-icon></v-app-bar-nav-icon>

      <v-toolbar-title>Welcome</v-toolbar-title>

      <v-spacer></v-spacer>

<!--      <v-tabs id="mobile-tabs-1" v-model="active" grow light>-->
<!--        <v-tabs-bar slot="activators">-->
<!--          <v-tabs-item v-for="i in 3" :key="i" :href="'#mobile-tabs-1-' + i" ripple>-->
<!--            Item {{ i }}-->
<!--          </v-tabs-item>-->
<!--          <v-tabs-slider></v-tabs-slider>-->
<!--        </v-tabs-bar>-->
<!--      </v-tabs>-->

      <v-btn icon @click="logout">
        <v-icon>logout</v-icon>
      </v-btn>

      <v-btn icon @click="logout">
        <v-icon>logout</v-icon>
      </v-btn>

      <v-btn icon @click="logout">
        <v-icon>logout</v-icon>
      </v-btn>

    </v-toolbar>
  </v-card>
</template>

<script>
import router from "../router";

export default {
  name: "TopBar",
  methods: {
    logout() {
      this.$store.dispatch("auth/logout");
      router.push("/");
    },
  },
};
</script>

<style scoped></style>

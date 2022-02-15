<template>
  <div>
    <Post :post="post"/>
    <div class="form-comment" v-if="user">
      <div class="header">Write Comment</div>

      <form @submit.prevent="onAddComment">
        <div class="field">
          <div class="name">
            <label for="text">Text:</label>
          </div>
          <div class="value">
            <textarea id="text" name="text" v-model="text"></textarea>
          </div>
        </div>
        <div class="error">{{ error }}</div>
        <div class="button-field">
          <input type="submit" value="Write">
        </div>
      </form>
    </div>
    <Comment v-for="comment in comments"
             :key="comment.id"
             :comment="comment"/>
  </div>

</template>

<script>
import Post from "./Post";
import Comment from "./Comment";
import axios from "axios";

export default {
  data: function () {
    return {
      text: "",
      error: "",
      comments: null
    }
  },
  computed : {

  },
  methods: {
    onAddComment: function () {
      this.error = "";
      this.$root.$emit("onAddComment", this.post.id, this.text);
    }
  },
  beforeMount() {
    this.$root.$on("onAddCommentValidationError", error => this.error = error);
    this.$root.$on("onAddCommentSuccess", (comments, id) => {
      if (this.post.id === id) {
        this.text = "";
        this.error = "";
        this.comments = comments
      }
    });
    axios.get("/api/1/posts/" + this.post.id + "/comments").then(response => this.comments = response.data)
  },
  name: "PostPage",
  components: {Post, Comment},
  props: ["post", "user"]
}

</script>

<style scoped>

</style>
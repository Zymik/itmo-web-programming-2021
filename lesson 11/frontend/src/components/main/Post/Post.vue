<template>
  <article>
    <div class="title" @click.prevent="toPost">
      {{ post.title }}
    </div>
    <div class="information">By {{ post.user.name }}</div>
    <div class="body">{{ post.text }}</div>
    <div class="footer">
      <div class="left">
        <img src='../../../assets/img/voteup.png' title="Vote Up" alt="Vote Up"/>
        <span class="positive-score">+173</span>
        <img src='../../../assets/img/votedown.png' title="Vote Down" alt="Vote Down"/>
      </div>
      <div class="right">
        <img src='../../../assets/img/date_16x16.png' title="Publish Time" alt="Publish Time"/>
        <img src='../../../assets/img/comments_16x16.png' title="Comments" alt="Comments"/>
        <a href="#" @click.prevent="toPost">{{ commentsCount }}</a>
      </div>
    </div>
  </article>
</template>

<script>
import {toPost} from '../../../functions.js'
import axios from "axios";

export default {
  name: "Post",
  props: ["post"],
  data: function () {
    return {
      commentsCount: null
    }
  },
  methods: {
    toPost: toPost
  }, beforeMount() {
    this.$root.$on("onAddCommentSuccess", (comments, id) => {
      if (id === this.post.id) {
        this.commentsCount = comments.length
      }
    });
    axios.get("/api/1/posts/" + this.post.id + "/comments/count").then(response => this.commentsCount = response.data)
  },
}

</script>

<style scoped>

</style>
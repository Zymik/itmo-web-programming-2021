<template>
  <div>
    <Post :post="post" :user="users[post.userId]" :commentCount="comments.length"/>
    <Comment v-for="comment in comments"
             :key="comment.id"
             :comment="comment"
             :user="users[comment.userId]"/>
  </div>
</template>

<script>
import Post from "./Post";
import Comment from "./Comment";

export default {
  name: "PostPage",
  components: {Post, Comment},
  props: ["post", "comments", "users"],
}

</script>

<style scoped>

</style>
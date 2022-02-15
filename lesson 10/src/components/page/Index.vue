<template>
  <div>
    <Post v-for="post in Object.values(posts).sort((a, b) => b.id - a.id)"
          :key="post.id"
          :post="post"
          :user="users[post.userId]"
          :commentCount="Object.values(comments)
                               .filter(a => a.postId === post.id)
                               .length"
    />
  </div>
</template>

<script>
import Post from "./Post/Post";

export default {
  name: "Index",
  components: {Post},
  props: ["posts", "users", "comments"]
}
</script>

<style scoped>

</style>

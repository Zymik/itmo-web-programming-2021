<template>
  <div>
    <Post v-for="post in Object.values(posts).sort((a, b) => b.id - a.id)"
          :key="post.id"
          :post="post"
          :commentCount="0"
    />
  </div>
</template>

<script>
import Post from "./Post/Post";

export default {
  name: "Index",
  components: {Post},
  props: ["posts"]
}
</script>

<style scoped>

</style>

<template>
  <div class="middle">
    <Sidebar :posts="viewPosts"/>
    <main>
      <Index v-if="page === 'Index'" :posts="posts"/>
      <Enter v-if="page === 'Enter'"/>
      <Register v-if="page === 'Register'"/>
      <Users v-if="page === 'Users'"/>
      <WritePost v-if="page === 'WritePost'"/>
      <PostPage v-if="page === 'PostPage'"
                :post="post"
                :user="user"/>
    </main>
  </div>
</template>

<script>
import Sidebar from "./sidebar/Sidebar";
import Index from "./main/Index";
import Enter from "./main/Enter";
import Register from "./main/Register";
import Users from "./main/Users";
import WritePost from "./main/WritePost";
import PostPage from "./main/Post/PostPage";

export default {
  name: "Middle",
  data: function () {
    return {
      page: "Index",
      post: null
    }
  },
  components: {
    PostPage,
    WritePost,
    Users,
    Register,
    Enter,
    Index,
    Sidebar
  },
  props: ["posts", "user"],
  computed: {
    viewPosts: function () {
      return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
    }
  }, beforeCreate() {
    this.$root.$on("onChangePage", (page) => this.page = page)
    this.$root.$on("toPost", (post) => {
      this.page = "PostPage";
      this.post = post;
    })
  }
}
</script>

<style scoped>

</style>

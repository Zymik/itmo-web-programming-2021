<template>
  <div class="middle">
    <Sidebar :posts="viewPosts"/>
    <main>
      <Index v-if="page === 'Index'" :posts="posts" :users="users" :comments="comments"/>
      <Enter v-if="page === 'Enter'"/>
      <WritePost v-if="page === 'WritePost'"/>
      <EditPost v-if="page === 'EditPost'"/>
      <Register v-if="page === 'Register'"/>
      <Users v-if="page === 'Users'" :users="users"/>
      <PostPage v-if="page === 'PostPage'"
                :post="posts[this.postId]"
                :users="users"
                :comments="Object.values(comments).filter(a => a.postId === this.postId)"/>
    </main>
  </div>
</template>

<script>
import Sidebar from "./sidebar/Sidebar";
import Index from "./page/Index";
import Enter from "./page/Enter";
import WritePost from "./page/WritePost";
import EditPost from "./page/EditPost";
import Register from "./page/Register";
import Users from "./page/Users";
import PostPage from "./page/Post/PostPage";

export default {
  name: "Middle",
  data: function () {
    return {
      page: "Index",
      postId: null
    }
  },
  components: {
    PostPage,
    Users,
    Register,
    WritePost,
    Enter,
    Index,
    Sidebar,
    EditPost
  },
  props: ["posts", "users", "comments"],
  computed: {
    viewPosts: function () {
      return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
    }
  }, beforeCreate() {
    this.$root.$on("onChangePage", (page) => this.page = page)
    this.$root.$on("toPost", (id) => {
      this.page = "PostPage";
      this.postId = id;
    })
  }
}
</script>

<style scoped>

</style>

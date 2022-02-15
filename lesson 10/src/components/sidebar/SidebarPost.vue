<template>
  <section>
    <div class="header">
      {{ post.title }}
    </div>
    <div class="body">
      {{ post.text }}
    </div>
    <div class="footer">
      <a href="#" @click.prevent="toPost">View all</a>
    </div>
  </section>
</template>

<script>

import {toPost} from '../../functions.js'

export default {
  name: "SidebarPost",
  props: ["post"]
  ,methods: {
    toPost: toPost
  }
}
</script>

<style scoped>
section {
  margin-bottom: 1rem;
}
</style>
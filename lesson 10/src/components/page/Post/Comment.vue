<template>
  <div class="comment">
    <div class="header">
      {{ user.name}}
    </div>
    <div class="text">
      {{ comment.text }}
    </div>
    <div class="footer">
      Id {{ comment.id }}
    </div>
  </div>
</template>

<script>
export default {
  name: "Comment.",
  props: ["comment", "user"]
}
</script>

<style scoped>

</style>
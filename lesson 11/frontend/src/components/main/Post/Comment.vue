<template>
  <div class="comment">
    <div class="header">
      {{ comment.user.name}}
    </div>
    <div class="text">
      {{ comment.text }}
    </div>
    <div class="footer">
      Time {{ comment.creationTime.split(".")[0].split("T").join(" ") }}
    </div>
  </div>
</template>

<script>
export default {
  name: "Comment.",
  props: ["comment"]
}
</script>

<style scoped>

</style>
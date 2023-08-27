<template>
  <router-link class="task flex" :to="{ name: 'Task', params: { taskId: task.id } }">
      <div class="left flex"></div>
  </router-link>
</template>

<script>
export default {
    name: "task",
    props: ['task']
}
</script>

<style scoped>

</style>
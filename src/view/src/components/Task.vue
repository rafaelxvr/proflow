<template>
  <router-link class="task flex" :to="{ name: 'Task', params: { taskId: task.id } }">
      <div class="left flex">
          <span class="task-id">{{task.epicName}}#{{task.id}}</span>
          <span class="due-date">{{task.dueDate}}</span>
          <span></span>
      </div>
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
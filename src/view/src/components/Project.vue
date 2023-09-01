<template>
  <router-link class="project flex" :to="{ name: 'Project', params: { projectId: project.id } }">
    <div class="left flex">
      <span class="project-id">{{ project.name }}#{{ project.id }}</span>
      <span class="name">{{ project.description }}</span>
    </div>
    <div class="right flex">
      <div
        class="status-button flex"
        :class="{
          backlog: project.status === 'Backlog',
          ready: project.status === 'Ready For Dev',
          progress: project.status === 'In Progress',
          done: project.status === 'Done'
        }"
      >
        <span v-if="project.status === 'Backlog'">Backlog</span>
        <span v-if="project.status === 'Ready For Dev'">Ready For Dev</span>
        <span v-if="project.status === 'In Progress'">In Progress</span>
        <span v-if="project.status === 'Done'">Done</span>
      </div>
      <div class="icon">
        <img src="@/assets/icon-arrow-right.svg" alt="" />
      </div>
    </div>
  </router-link>
</template>

<script>
export default {
  name: 'project',
  props: ['project']
}
</script>

<style lang="scss" scoped>
.project {
  text-decoration: none;
  cursor: pointer;
  gap: 16px;
  margin-bottom: 16px;
  color: #fff;
  border-radius: 20px;
  padding: 28px 32px;
  background-color: #1e2139;
  align-items: center;

  span {
    font-size: 13px;
  }

  .left {
    align-items: center;
    flex-basis: 60%;
    gap: 16px;

    span {
      flex: 1;
    }

    .project-id {
      text-transform: uppercase;
    }

    .name {
      flex: 1;
      font-size: 16px;
      font-weight: 600;
    }
  }

  .right {
    gap: 16px;
    flex-basis: 40%;
    align-items: center;
  }
}
</style>

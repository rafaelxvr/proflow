<template>
  <router-link class="task flex" :to="{ name: 'Task', params: { taskId: task.id }  }">
      <div class="left flex">
          <span class="task-id">{{task.epicName}}#{{task.id}}</span>
          <span class="due-date">{{task.dueDate}}</span>
      </div>
      <div class="right flex">
          <span class="name">{{task.name}}</span>
          <div class="status-button flex pending" :class="{
            backlog : task.status === 'backlog',
            ready : task.status === 'ready',
            progress : task.status === 'in progress',
            done : task.status === 'done' }"
          >
              <span v-if="task.status === 'backlog'">Backlog</span>
              <span v-if="task.status === 'ready'">Ready For Dev</span>
              <span v-if="task.status === 'in progress'">In Progress</span>
              <span v-if="task.status === 'done'">Done</span>
          </div>
          <div class="icon">
              <img src="@/assets/icon-arrow-right.svg" alt="">
          </div>
      </div>
  </router-link>
</template>

<script>
export default {
    name: "task",
    props: ['task']
}
</script>

<style lang="scss" scoped>
    .task {
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

            .task-id {
                text-transform: uppercase;
            }
        }

        .right {
            gap: 16px;
            flex-basis: 40%;
            align-items: center;

            .name {
                flex: 1;
                font-size: 16px;
                font-weight: 600px;
            }
        }
    }
</style>
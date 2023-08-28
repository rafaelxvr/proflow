<template>
  <router-link class="task flex" :to="{ name: 'Task', params: { taskId: task.id }  }">
      <div class="left flex">
          <span class="task-id">{{task.epicName}}#{{task.id}}</span>
          <span class="name">{{task.name}}</span>
          <span class="due-date">{{formattedStartDate}}</span>
      </div>
      <div class="right flex">
          <span class="due-date">{{formattedDueDate}}</span>
          <div class="status-button flex" :class="{
            backlog : task.status === 'backlog',
            ready : task.status === 'ready' ?? true,
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
    props: ['task'],
    data() {
        return {
            formattedStartDate: null,
            formattedDueDate: null,
            dateOptions: { year: "numeric", month: "short", day: "numeric" }
        }
    },
    created() {
        this.formatDates()
    },
    methods: {
        formatDates() {
            this.formattedStartDate = new Date(this.task.startDate).toLocaleDateString("en-us", this.dateOptions)
            this.formattedDueDate = new Date(this.task.dueDate).toLocaleDateString("en-us", this.dateOptions)
        }
    }
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
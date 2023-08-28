<template>
  <div v-if="currentTask" class="task-view container">
      <router-link class="nav-link flex" :to="{ name: 'Task' }">
          <img src="@/assets/icon-arrow-left.svg" alt=""> Go Back
      </router-link>
      <div class="header flex">
          <div class="left flex">
            <div class="status-button flex" :class="{
            backlog : currentTask.status === 'backlog',
            ready : currentTask.status === 'ready' ?? true,
            progress : currentTask.status === 'in progress',
            done : currentTask.status === 'done' }"
              >
                <span v-if="currentTask.status === 'backlog'">Backlog</span>
                <span v-if="currentTask.status === 'ready'">Ready For Dev</span>
                <span v-if="currentTask.status === 'in progress'">In Progress</span>
                <span v-if="currentTask.status === 'done'">Done</span>
            </div>
          </div>
          <div class="right flex">
            <button @click="toggleEditTask(currentTask.id)" class="dark-purple">Edit</button>
            <button @click="deleteTask(currentTask.id)" class="red">Delete</button>
            <button
              v-if="currentTask.status === 'backlog'"
              @click="updateStatusToReady(currentTask.id)"
              class="green"
            >
              Mark as Ready to Dev
            </button>
            <button
              v-if="currentTask.status !== 'backlog'"
              @click="updateStatusToBacklog(currentTask.id)"
              class="orange"
            >
              Return to Backlog
            </button>

          </div>
      </div>
      <div class="task-details flex flex-column">
          <div class="top flex">
              <div class="left flex">
                <p>{{ currentTask.epicName }}<span>#</span></p>
                <p>{{ currentTask.id }}</p>
              </div>

              <div class="right flex flex-column">
                  <p>{{ currentTask.description }}</p>
              </div>
          </div>
          <div class="middle flex flex-column">
              <h4>Start Date</h4>
              <p>{{ formattedStartDate }}</p>
              <h4>Due Date</h4>
              <p>{{ formattedDueDate }}</p>
              <div class="task-creator flex flex-column">
                  <h4>Task Creator</h4>
                  <p>{{ currentTask.creatorName }}</p>
              </div>
              <div class="task-sector flex flex-column">
                  <h4>Sector</h4>
                  <p>{{ currentTask.sectorName }}</p>
              </div>
          </div>
          <div class="bottom flex flex-column">
              <div class="task-subtasks">
                  <div class="heading flex">
                      <p>Subtask Name</p>
                      <p>Subtask Description</p>
                  </div>
                  <div v-for="(subtask, index) in currentTask.subtaskList" :key="index" class="item flex">
                      <p>{{ subtask.name }}</p>
                      <p>{{ subtask.description }}</p>
                  </div>
              </div>
          </div>
      </div>
  </div>
</template>

<script>
import { mapMutations, mapState } from "vuex";

export default {
    name: "TaskView",
    data() {
        return {
          currentTask: null,
          formattedStartDate: null,
          formattedDueDate: null,
          dateOptions: { year: "numeric", month: "short", day: "numeric" }
        }
    },
    created() {
      this.getCurrentTask()
      this.formatDates()
    },
    methods: {
        ...mapMutations(['SET_CURRENT_TASK', 'TOGGLE_EDIT_TASK', 'TOGGLE_TASK']),
        getCurrentTask() {
            this.SET_CURRENT_TASK(this.$route.params.taskId)
            this.currentTask = this.currentTaskArray[0];
        },
        toggleEditTask() {
          this.TOGGLE_EDIT_TASK();
          this.TOGGLE_TASK();
        },
        formatDates() {
            this.formattedStartDate = new Date(this.currentTask.startDate).toLocaleDateString("en-us", this.dateOptions)
            this.formattedDueDate = new Date(this.currentTask.dueDate).toLocaleDateString("en-us", this.dateOptions)
        }
    },
    computed: {
        ...mapState(['currentTaskArray', 'editTask'])
    },
    watch: {
        editTask() {
            if(!this.editTask) {
                this.currentTask = this.currentTaskArray[0];
            }
        }
    }
}
</script>

<style lang="scss" scoped>
  .task-view {
    .nav-link {
      margin-bottom: 32px;
      align-items: center;
      color: white;
      font-size: 12px;
      img {
        margin-right: 16px;
        width: 7px;
        height: 10px;
      }
    }

    .header, .task-details {
      background-color: #1e2139;
      border-radius: 20px;
    }

    .header {
      align-items: center;
      padding: 24px 32px;
      font-size: 12px;

      .left {
        align-items: center;

        span {
          color: #dfe3fa;
          margin-right: 16px;
        }
      }

      .right {
        flex: 1;
        justify-content: flex-end;

        button {
          color: white;
        }
      }
    }

    .task-details {
      padding: 48px;
      margin-top: 24px;

      .top {
        div {
          color: #dfe3fa;
          flex: 1;
        }

        .left {
          font-size: 12px;
          p:first-child {
            font-size: 24px;
            text-transform: uppercase;
            color: white;
            margin-bottom: 8px;
          }

          p:nth-child(2) {
            font-size: 26px;
          }

          span {
            color: #888eb0;
          }
        }

        .right {
          font-size: 12px;
          align-items: flex-end;
        }
      }

      .middle {
        margin-top: 50px;
        color: #dfe3fa;
        gap: 16px;

        h4 {
          font-size: 12px;
          font-weight: 400;
          margin-bottom: 12px;
        }

        p {
          font-size: 16px
        }

        .task-creator, .task-sector {
          flex: 1;
        }

        .task-sector {
          h4 {
            margin-top: 32px;
          }

          p {
            font-weight: 600;
          }
        }

        .task-creator {
          p {
            font-size: 12px;
          }
        }

      }

      .bottom {
        margin-top: 50px;

        .task-subtasks {
          padding: 32px;
          border-radius: 20px 20px 0 0;
          background-color: #252945;

          .heading {
            color: #dfe3fa;
            font-size: 12px;
            margin-bottom: 32px;

            p:first-child {
              flex: 3;
              text-align: left;
            }

            p {
              flex: 1;
              text-align: right;

            }
          }

          .item {
            margin-bottom: 32px;
            font-size: 13px;
            color: white;

            &:last-child {
              margin-bottom: 0;
            }

            p:first-child {
              flex: 3;
              text-align: left;
            }

            p {
              flex: 1;
              text-align: right;
            }
          }
        }
      }
    }
  }
</style>
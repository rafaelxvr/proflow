<template>
  <div v-if="currentProject" class="project-view container">
    <router-link class="nav-link flex" :to="{ name: 'home' }">
      <img src="@/assets/icon-arrow-left.svg" alt="" /> Go Back
    </router-link>
    <div class="header flex">
      <div class="left flex">
        <div
          class="status-button flex"
          :class="{
            backlog: currentProject.status === 'Backlog',
            ready: currentProject.status === 'Ready For Dev',
            progress: currentProject.status === 'In Progress',
            done: currentProject.status === 'Done'
          }"
        >
          <span v-if="currentProject.status === 'Backlog'">Backlog</span>
          <span v-if="currentProject.status === 'Ready For Dev'">Ready For Dev</span>
          <span v-if="currentProject.status === 'In Progress'">In Progress</span>
          <span v-if="currentProject.status === 'Done'">Done</span>
        </div>
      </div>
      <div class="right flex">
        <div @click="toggleFilterMenu" class="filter flex">
          <span>Filter by Status</span>
          <img src="@/assets/icon-arrow-down.svg" alt="" />
          <ul v-show="filterMenu" class="filter-menu">
            <li @click="filteredTasks">Backlog</li>
            <li @click="filteredTasks">Ready for Dev</li>
            <li @click="filteredTasks">In Progress</li>
            <li @click="filteredTasks">Done</li>
            <li @click="filteredTasks">Clear Filter</li>
          </ul>
        </div>
        <div @click="newTask" class="button flex">
          <div class="inner-button flex">
            <img src="@/assets/icon-plus.svg" alt="" />
          </div>
          <span>New Task</span>
        </div>
        <button @click="toggleEditProject(currentProject.id)" class="dark-purple">Edit</button>
        <button @click="deleteProject(currentProject.id)" class="red">Delete</button>
      </div>
    </div>
    <div class="project-details flex flex-column">
      <div class="top flex">
        <div class="left flex">
          <p>{{ currentProject.name }}<span>#</span></p>
          <p>{{ currentProject.id }}</p>
        </div>

        <div class="right flex flex-column">
          <p>{{ currentProject.description }}</p>
        </div>
      </div>
      <div class="middle flex flex-column">
        <div class="tasks" v-if="taskData.length > 0">
          <Task v-for="(task, index) in filteredData" :task="task" :key="index" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapMutations, mapState, mapActions } from 'vuex'
import Task from '@/components/Task.vue'

export default {
  name: 'ProjectView',
  components: { Task },
  data() {
    return {
      filterMenu: null,
      filteredTask: null,
      currentProject: null
    }
  },
  async created() {
    await this.getCurrentProject()
  },
  methods: {
    ...mapMutations([
      'SET_CURRENT_PROJECT',
      'TOGGLE_EDIT_PROJECT',
      'TOGGLE_PROJECT',
      'TOGGLE_TASK'
    ]),
    ...mapActions(['DELETE_PROJECT', 'GET_TASKS', 'GET_PROJECTS']),
    toggleFilterMenu() {
      this.filterMenu = !this.filterMenu
    },
    filteredTasks(event) {
      if (event.target.innerText === 'Clear Filter') {
        this.filteredTask = null
        return
      }

      this.filteredTask = event.target.innerText
    },
    newTask() {
      this.TOGGLE_TASK()
    },
    async getCurrentProject() {
      this.SET_CURRENT_PROJECT({ id: this.$route.params.projectId })
      this.currentProject = this.currentProjectArray[0]
      await this.GET_TASKS({ payload: this.currentProject })
    },
    toggleEditProject() {
      this.TOGGLE_EDIT_PROJECT()
      this.TOGGLE_PROJECT()
    },
    async deleteProject(id) {
      await this.DELETE_PROJECT(id)
      await this.GET_PROJECTS({ payload: this.currentProject.client })
      this.$router.push({ name: 'home' })
    }
  },
  computed: {
    ...mapState(['currentProjectArray', 'editProject', 'taskData']),
    filteredData() {
      const filteredData = this.taskData.filter((project) => {
        return this.filteredTask === project.status
      })

      return filteredData.length > 0 ? filteredData : this.taskData
    }
  },
  watch: {
    editProject() {
      if (!this.editProject) {
        this.currentProject = this.currentProjectArray[0]
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.project-view {
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

  .header,
  .project-details {
    background-color: #1e2139;
    border-radius: 20px;
  }

  .button {
    padding: 8px 10px;
    background-color: #7c5dfa;
    border-radius: 40px;

    .inner-button {
      margin-right: 8px;
      border-radius: 50%;
      padding: 8px;
      align-items: center;
      justify-content: center;
      background-color: white;

      img {
        width: 10px;
        height: 10px;
      }
    }
  }

  .button,
  .filter {
    align-items: center;
    color: white;

    span {
      font-size: 12px;
    }
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

  .project-details {
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
        font-size: 16px;
      }

      .project-creator,
      .project-sector {
        flex: 1;
      }

      .project-sector {
        h4 {
          margin-top: 32px;
        }

        p {
          font-weight: 600;
        }
      }

      .project-creator {
        p {
          font-size: 12px;
        }
      }
    }

    .bottom {
      margin-top: 50px;

      .project-subprojects {
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

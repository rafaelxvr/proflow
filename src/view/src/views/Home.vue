<template>
  <div class="home container">
    <div class="header flex">
      <div class="left flex flex-column">
        <h1>Projects</h1>
        <div v-if="this.clientsLoaded" class="input flex flex-column">
          <label for="clients">Client</label>
          <select v-model="selectedClientId" @change="selectClient" :key="selectedClientId">
            <option selected>Select a Client</option>
            <option
              v-for="client in this.clientData"
              v-bind:value="client.id"
              :key="client.id"
              :selected="this.currentClient.id === selectedClientId"
            >
              {{ client.name }}
            </option>
          </select>
        </div>
      </div>
      <div class="right flex">
        <div @click="toggleFilterMenu" class="filter flex">
          <span
            >Filter by Status <span v-if="filteredProject">{{ filteredProject }}</span></span
          >
          <img src="@/assets/icon-arrow-down.svg" alt="" />
          <ul v-show="filterMenu" class="filter-menu">
            <li @click="filteredProjects">Backlog</li>
            <li @click="filteredProjects">Ready For Dev</li>
            <li @click="filteredProjects">In Progress</li>
            <li @click="filteredProjects">Done</li>
            <li @click="filteredProjects">Clear Filter</li>
          </ul>
        </div>
        <div @click="newClient" class="button flex">
          <div class="inner-button flex">
            <img src="@/assets/icon-plus.svg" alt="" />
          </div>
          <span>New Client</span>
        </div>
        <div @click="newProject" class="button flex">
          <div class="inner-button flex">
            <img src="@/assets/icon-plus.svg" alt="" />
          </div>
          <span>New Project</span>
        </div>
      </div>
    </div>
    <div class="projects" v-if="this.projectsLoaded">
      <Project v-for="(project, index) in filteredData" :project="project" :key="index" />
    </div>
    <div v-else class="empty flex flex-column">
      <img src="@/assets/illustration-empty.svg" alt="" />
      <h3>There is nothing here.</h3>
      <p>Select a project, create a new task by clicking the New Task button and get started</p>
    </div>
  </div>
</template>

<script>
import Project from '@/components/Project.vue'
import { mapMutations, mapState, mapActions } from 'vuex'
export default {
  name: 'Home',
  data() {
    return {
      filterMenu: null,
      filteredProject: null,
      selectedProjectId: null,
      selectedClientId: null,
      currentProject: {
        id: null,
        name: '',
        description: ''
      },
      currentClient: {
        id: null,
        name: '',
        document: '',
        email: ''
      }
    }
  },
  components: {
    Project
  },
  created() {},
  methods: {
    ...mapMutations([
      'TOGGLE_TASK',
      'TOGGLE_CLIENT',
      'TOGGLE_PROJECT',
      'SET_CURRENT_PROJECT',
      'SET_CURRENT_CLIENT'
    ]),
    ...mapActions(['GET_PROJECTS', 'GET_CLIENTS', 'GET_TASKS']),
    toggleFilterMenu() {
      this.filterMenu = !this.filterMenu
    },
    filteredProjects(event) {
      if (event.target.innerText === 'Clear Filter') {
        this.filteredProject = null
        return
      }

      this.filteredProject = event.target.innerText
    },
    newClient() {
      this.TOGGLE_CLIENT()
    },
    newProject() {
      this.TOGGLE_PROJECT()
    },
    async selectClient() {
      const client = this.clientData.find((client) => client.id === this.selectedClientId)
      this.currentClient = client
      await this.GET_PROJECTS({ payload: client })
      this.SET_CURRENT_CLIENT({ payload: this.currentClient })
    }
  },
  computed: {
    ...mapState(['taskData', 'clientData', 'projectData', 'projectsLoaded', 'clientsLoaded']),
    filteredData() {
      const filteredData = this.projectData.filter((project) => {
        return this.filteredProject === project.status
      })

      return filteredData.length > 0 ? filteredData : this.projectData
    }
  },
  watch: {
    projectsLoaded: {
      immediate: true,
      handler(newProjectsLoaded) {
        if (newProjectsLoaded) {
          this.currentProject = this.projectData
        }
      }
    },
    clientsLoaded: {
      immediate: true,
      handler(newClientsLoaded) {
        if (newClientsLoaded) {
          this.currentClient = this.clientData
        }
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.home {
  color: white;
}

.header {
  margin-bottom: 65px;

  .left,
  .right {
    flex: 1;
  }

  .right {
    justify-content: flex-end;
    align-items: center;

    .button,
    .filter {
      align-items: center;

      span {
        font-size: 12px;
      }
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
  }

  input,
  select,
  textarea {
    width: 100%;
    width: 50%;
    background-color: #1e2139;
    color: white;
    border-radius: 4px;
    padding: 12px 4px;
    border: none;
    resize: none;

    &.focus {
      outline: none;
    }
  }
}

.empty {
  margin-top: 160px;
  align-items: center;

  img {
    width: 214px;
    height: 200px;
  }

  h3 {
    font-size: 20px;
    margin-top: 40px;
  }

  p {
    text-align: center;
    max-width: 224px;
    font-size: 12px;
    font-weight: 300;
    margin-top: 16px;
  }
}
</style>

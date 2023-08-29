<template>
  <div class="home container">
      <div class="header flex">
          <div class="left flex flex-column">
              <h1>Tasks</h1>
              <div v-if="clientsList.length > 0" class="input flex flex-column">
                  <label for="clients">Client</label>
                  <select v-model="selectedClientId" @change="selectClient">
                      <option selected>Select a Client</option>
                      <option
                          v-for="client in clientsList"
                          v-bind:value="client.id"
                          :selected="this.currentClient.id === selectedClientId"
                      >
                          {{ client.name }}
                      </option>
                  </select>
              </div>
              <div v-if="this.projectsLoaded" class="input flex flex-column">
                  <label for="clients">Projects</label>
                  <select v-model="selectedProjectId" @change="selectProject">
                      <option selected>Select a Project</option>
                      <option
                          v-for="project in projectsList"
                          v-bind:value="project.id"
                          :selected="this.currentProject.id === selectedProjectId"
                      >
                          {{ project.name }}
                      </option>
                  </select>
              </div>
          </div>
          <div class="right flex">
              <div @click="toggleFilterMenu" class="filter flex">
                  <span>Filter by Status</span>
                  <img src="@/assets/icon-arrow-down.svg" alt="">
                  <ul v-show="filterMenu" class="filter-menu">
                      <li>Backlog</li>
                      <li>Ready for Dev</li>
                      <li>In Progress</li>
                      <li>Done</li>
                      <li>Clear Filter</li>
                  </ul>
              </div>
              <div @click="newClient" class="button flex">
                  <div class="inner-button flex">
                      <img src="@/assets/icon-plus.svg" alt="">
                  </div>
                  <span>New Client</span>
              </div>
              <div @click="newProject" class="button flex">
                  <div class="inner-button flex">
                      <img src="@/assets/icon-plus.svg" alt="">
                  </div>
                  <span>New Project</span>
              </div>
              <div @click="newTask" class="button flex">
                  <div class="inner-button flex">
                      <img src="@/assets/icon-plus.svg" alt="">
                  </div>
                  <span>New Task</span>
              </div>
          </div>
      </div>
      <div class="tasks" v-if="taskData.length > 0">
          <Task v-for="(task, index) in taskData" :task="task" :key="index" />
      </div>
      <div v-else class="empty flex flex-column">
        <img src="@/assets/illustration-empty.svg" alt="">
        <h3>There is nothing here.</h3>
        <p>Create a new task by clicking the New Task button and get started</p>
      </div>
  </div>
</template>


<script>
import Task from "@/components/Task.vue"
import { mapMutations, mapState, mapActions } from "vuex"
  export default {
      name: "Home",
      data() {
        return {
            projectsList: [],
            clientsList: [],
            currentProject: {
              id: null,
              name: "",
              description: ""
            },
            filterMenu: null,
            currentClient: {
                id: null,
                name: "",
                document: "",
                email: ""
            },
            selectedClientId : null,
            selectedProjectId : null
        }
      },
      components: {
          Task
      },
      created() {
          this.clientsList = this.clientData.flat();
          this.projectsList = this.projectData.flat();
      },
      methods: {
          ...mapMutations(['TOGGLE_TASK', 'TOGGLE_CLIENT', 'TOGGLE_PROJECT', 'SET_CURRENT_PROJECT', 'SET_CURRENT_CLIENT']),
          ...mapActions(['GET_PROJECTS', 'GET_CLIENTS', 'GET_TASKS']),
          newTask() {
              this.TOGGLE_TASK();
          },
          toggleFilterMenu() {
              this.filterMenu = !this.filterMenu;
          },
          newClient() {
              this.TOGGLE_CLIENT();
          },
          newProject() {
              this.TOGGLE_PROJECT();
          },
          async selectClient() {
              const client = this.clientsList.find(client => client.id === this.selectedClientId);
              this.currentClient = client
              await this.GET_PROJECTS({ payload: client });
              this.SET_CURRENT_CLIENT({ payload: this.currentClient })
          },
          async selectProject() {
              const project = this.projectsList.find(project => project.id === this.selectedProjectId)
              this.currentProject = project

              if (this.currentProject?.id) {
                  await this.GET_TASKS({ payload: project });
                  this.SET_CURRENT_PROJECT({ payload: this.currentProject })
              }
          }
      },
      computed: {
          ...mapState(['taskData', 'clientData', 'projectData', 'projectsLoaded', 'clientsLoaded'])
      },
      watch: {
          projectsLoaded: {
              immediate: true,
              handler(newProjectsLoaded) {
                  if (newProjectsLoaded) {
                      this.projectsList = this.projectData.flat();
                      this.currentProject = this.projectData.flat();
                  }
              }
          },
          clientsLoaded: {
              immediate: true,
              handler(newClientsLoaded) {
                  if (newClientsLoaded) {
                      this.clientsList = this.clientData.flat();
                      this.currentClient = this.clientData.flat();
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

      .filter {
        position: relative;
        margin-right: 40px;
        cursor: pointer;

        img {
          margin-left: 12px;
          width: 9px;
          height: 5px;
        }

        .filter-menu {
          width: 120px;
          position: absolute;
          top: 25px;
          list-style: none;
          background-color: #1e2139;
          box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);

          li {
            cursor: pointer;
            font-size: 12px;
            padding: 10px 20px;

            &.hover {
              color: #1e2139;
              background-color: white;
            }
          }
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

      input, select, textarea {
          width: 60%;
          height: 35%;
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
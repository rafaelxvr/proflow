<template>
  <div class="home container">
      <div class="header flex">
          <div class="left flex flex-column">
              <h1>Tasks</h1>
              <div class="input flex flex-column">
                  <label for="clients">Projects</label>
                  <select v-model="this.project.name">
                      <option v-for="project in projectData" :value="project" :key="project.name">{{ project.name }}</option>
                  </select>
              </div>
              <div class="input flex flex-column">
                  <label for="clients">Client</label>
                  <select v-model="client.name">
                      <option v-for="client in clients" :value="client.name" :key="client.name">{{ client.name }}</option>
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
import { mapMutations, mapState } from "vuex"
  export default {
      name: "Home",
      data() {
        return {
            projects: [],
            clients: [],
            client: {
                id: null,
                name: "",
                documentId: "",
                email: ""
            },
            project: {
              id: null,
              name: ""
            },
            filterMenu: null,
        }
      },
      components: {
          Task
      },
      created() {
          this.clients = [this.clientData];
          console.log(this.clients)
      },
      methods: {
          ...mapMutations(['TOGGLE_TASK', 'TOGGLE_CLIENT', 'TOGGLE_PROJECT']),
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
          }
      },
      computed: {
          ...mapState(['taskData', 'clientData', 'projectData'])
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
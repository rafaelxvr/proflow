<template>
  <div @click="checkClick" ref="taskWrap" class="task-wrap flex flex-column">
      <form @submit.prevent="submitForm" class="task-content">
          <LoadingScreen v-show="loading"/>
          <h1 v-if="!editTask">New Task</h1>
          <h1 v-else>Edit Task</h1>

          <div class="task-information flex flex-column">
            <h4>Task Information</h4>
              <div class="input flex flex-column">
                  <label for="taskName">Task Name</label>
                  <input required type="text" id="taskName" v-model="task.name">
              </div>
              <div class="task-details flex">
                  <div class="input flex flex-column">
                      <label for="taskSector">Sector</label>
                      <input required type="text" id="taskSector" v-model="task.sectorName">
                  </div>
                  <div class="input flex flex-column">
                      <label for="taskEpic">Epic</label>
                      <input required type="text" id="taskEpic" v-model="task.epicName">
                  </div>
                  <div class="input flex flex-column">
                      <label for="taskCreator">Creator</label>
                      <input required type="text" id="taskCreator" v-model="task.creatorName">
                  </div>
              </div>
          </div>
          <div class="task-description flex flex-column">
              <h4>Task Description</h4>
              <div class="input flex flex-column">
                  <label for="taskDescription">Description</label>
                  <textarea required id="taskDescription" v-model="task.description" />
              </div>
          </div>
          <div class="input flex flex-column">
              <label for="taskStartDate">Start Date</label>
              <input required type="date" id="taskStartDate" v-model="task.startDate">
          </div>
          <div class="input flex flex-column">
              <label for="taskDueDate">Due Date</label>
              <input required type="date" id="taskDueDate" v-model="task.dueDate">
          </div>

          <div class="task-subtasks">
              <h3>Subtask List</h3>
              <table class="subtask-list">
                  <tr class="table-heading flex">
                      <th class="subtask-name">Name</th>
                      <th class="subtask-description">Description</th>
                  </tr>
                  <tr class="table-subtask flex" v-for="subtask in subtaskList" :key="subtask.id">
                      <td class="subtask-name"><input type="text" v-model="subtask.name" /></td>
                      <td class="subtask-description"><input type="text" v-model="subtask.description" /></td>
                      <img @click="deleteSubtask(subtask.id)" src="@/assets/icon-delete.svg" alt="" />
                  </tr>
              </table>

              <div @click="addNewSubtask" class="flex button">
                  <img src="@/assets/icon-plus.svg" alt="" />
                  Add New Subtask
              </div>
          </div>

          <div class="save flex">
              <div class="left">
                  <button type="button" @click="closeTask" class="red">Cancel</button>
              </div>
              <div class="right flex">
                  <button v-if="!editTask" type="submit" v-on:@click.once="saveDraft" class="dark-purple">Save Draft</button>
                  <button v-if="!editTask" type="submit" v-on:@click.once="publishTask" class="purple">Create Task</button>
                  <button v-if="editTask" type="submit" v-on:@click.once="updateTask" class="purple">Update Task</button>
              </div>
          </div>
      </form>
  </div>
</template>

<script>

import { mapMutations, mapState, mapActions } from "vuex";
import { uid } from "uid";
import LoadingScreen from '@/components/LoadingScreen.vue'

export default {
    name: "TaskModal",
    components: {
        LoadingScreen
    },
    data() {
        return {
            task: {
                id: null,
                name: null,
                description: null,
                creatorName: null,
                sectorName: null,
                epicName: null,
                startDate: null,
                dueDate: null,
                status: null,
                project: null
            },
            dateOptions: { year: "numeric", month: "short", day: "numeric" },
            subtaskList: [],
            taskStartDateUnix: null,
            loading: null
        }
    },
    created() {
        const currentProject = this.currentProjectArray.flat()[0]
        this.task.project = {
            id: currentProject.id,
            name: currentProject.name,
            description: currentProject.description,
            status: currentProject.status
        }

        if (this.editTask) {
            const currentTask = this.currentTaskArray[0];
            for (const prop in currentTask) {
                if (currentTask.hasOwnProperty(prop)) {
                    this.task[prop] = currentTask[prop];
                }
            }
            if (currentTask.subtaskList !== null && currentTask.subtaskList.length > 0)
                this.subtaskList = currentTask.subtaskList;
        }
    },
    computed: {
        ...mapState(['editTask', 'currentTaskArray', 'currentProjectArray', 'currentClientArray']),
    },
    methods: {
        ...mapMutations(['TOGGLE_TASK', 'TOGGLE_MODAL', 'TOGGLE_EDIT_TASK']),
        ...mapActions(['UPDATE_TASK', 'GET_TASKS']),
        checkClick(event) {
          if (event.target === this.$refs.taskWrap) {
            this.TOGGLE_MODAL();
          }
        },
        closeTask() {
            this.TOGGLE_TASK();
            if (this.editTask) {
                this.TOGGLE_EDIT_TASK();
            }
        },
        addNewSubtask() {
            this.subtaskList.push({
                id: uid(),
                name: "",
                description: ""
            })
        },
        deleteSubtask(id) {
            this.subtaskList = this.subtaskList.filter(subtask => subtask.id !== id);
        },
        publishTask() {
            this.task.status = "ready";
        },
        saveDraft() {
            this.task.status = "backlog";
        },
        async uploadTask() {
            if (this.subtaskList.length <= 0) {
                alert('Please ensure you added subtasks!')
                return;
            }

            this.loading = true;

            await fetch("http://localhost:8080/api/tasks/add", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(this.task),
                })
                .then(res => res.json())
                .then(data => {
                   this.task.id = data?.id;
                });

            await this.GET_TASKS({ payload: this.task.project });
            await this.uploadSubTasks()

            this.loading = false;
            this.TOGGLE_TASK();
        },
        async uploadSubTasks() {
            this.subtaskList.forEach(
                subtask => {
                    const subtaskData = {
                        id: subtask.id,
                        name: subtask.name,
                        description: subtask.description,
                        task: this.task
                    }
                    fetch("http://localhost:8080/api/subtasks/add", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json",
                        },
                        body: JSON.stringify(subtaskData),
                    }).catch(err => {
                        alert(err.message);
                    });
                }
            )
        },
        async updateTask() {
            if (this.subtaskList.length <= 0) {
                alert('Please ensure you added subtasks!')
                return;
            }
            this.loading = true;

            let payload = {
                id: null,
                name: null,
                description: null,
                creatorName: null,
                sectorName: null,
                epicName: null,
                startDate: null,
                dueDate: null,
                status: null,
                projectId: null,
                clientId: null,
                userId: null,
                subtaskList: []
            };

            await fetch("http://localhost:8080/api/tasks/update", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(this.task),
            })
                .then(res => res.json())
                .then(async data => {
                    payload = {
                        id: data.id,
                        name: data.name,
                        description: data.description,
                        creatorName: data.creatorName,
                        sectorName: data.sectorName,
                        epicName: data.epicName,
                        startDate: data.startDate,
                        dueDate: data.dueDate,
                        status: data.status,
                        projectId: data.projectId,
                        clientId: data.clientId,
                        userId: data.userId
                    };
                });
            payload.subtaskList = [...(await this.updateSubtask(payload))]
            await this.UPDATE_TASK(payload);
        },
        async updateSubtask(updatedTask) {
            this.subtaskList.forEach(
                subtask => {
                    const subtaskData = {
                        id: subtask.id,
                        name: subtask.name,
                        description: subtask.description,
                        task: updatedTask
                    }
                    fetch("http://localhost:8080/api/subtasks/update", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json",
                        },
                        body: JSON.stringify(subtaskData),
                    })
                        .then(res => res.json())
                        .then(data => {
                            return data
                        });
                }
            )
        },
        async submitForm() {
            if (this.editTask) {
                await this.updateTask()
                return;
            }

            await this.uploadTask()
        }
    }
}
</script>

<style lang="scss" scoped>
  .task-wrap {
    position: fixed;
    top: 0;
    left: 0;
    background-color: transparent;
    width: 100%;
    height: 100vh;
    overflow: scroll;
    &::-webkit-scrollbar {
      display: none;
    }
    @media(min-width: 900px) {
      left: 90px;
    }

    .task-content {
      position: relative;
      padding: 56px;
      max-width: 700px;
      width: 100%;
      background-color: #141625;
      color: white;
      box-shadow: 10px 4px 6px -1px rgba(0, 0, 0, 0.2), 0 2px 4px -1px rgba(0, 0, 0, 0.06);

      h1 {
        margin-bottom: 48px;
        color: white;
      }

      h3 {
        margin-bottom: 16px;
        font-size: 18px;
        color: #777f98;
      }

      h4 {
        color: #7c5dfa;
        font-size: 12px;
        margin-bottom: 24px;
      }

      .task-information, task-description {
        margin-bottom: 48px;

        .task-details {
          gap: 16px;
          div {
            flex: 1;
          }
        }
      }

      .task-subtasks {
        .subtask-list {
          width: 100%;

          .table-heading, table-subtask {
            gap: 16px;
            font-size: 12px;

            .subtask-name {
              flex-basis: 30%;
            }

            .subtask-description {
              flex-basis: 70%;
            }
          }

          .table-heading {
            margin-bottom: 16px;

            th {
              text-align: center;
            }
          }

          .table-subtask {
            position: relative;
            margin-bottom: 24px;

            td {
                padding: 8px;
            }

            img {
              position: absolute;
              top: 50px;
              right: 0;
              width: 12px;
              height: 16px;
            }
          }
        }

        .button {
          color: white;
          background-color: #252945;
          align-items: center;
          justify-content: center;
          width: 100%;

          img {
            margin-right: 4px;
          }
        }
      }

      .save {
        margin-top: 60px;

        div {
          flex: 1;
        }

        .right {
          justify-content: flex-end;
        }
      }
    }

    .input {
      margin-bottom: 24px;

    }

    label {
      font-size: 12px;
      margin-bottom: 6px;
    }

    input, select, textarea {
      width: 100%;
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
</style>
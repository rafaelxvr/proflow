<template>
  <div @click="checkClick" ref="taskWrap" class="task-wrap flex flex-column">
      <form @submit.prevent="submitForm" class="task-content">
          <h1>New Task</h1>
          <div class="task-information flex flex-column">
            <h4>Task Information</h4>
              <div class="input flex flex-column">
                  <label for="taskName">Task Name</label>
                  <input required type="text" id="taskName" v-model="taskName">
              </div>
              <div class="task-details flex">
                  <div class="input flex flex-column">
                      <label for="taskSector">Sector</label>
                      <input required type="text" id="taskSector" v-model="taskSector">
                  </div>
                  <div class="input flex flex-column">
                      <label for="taskEpic">Epic</label>
                      <input required type="text" id="taskEpic" v-model="taskEpic">
                  </div>
                  <div class="input flex flex-column">
                      <label for="taskCreator">Creator</label>
                      <select v-model="taskCreator">
                          <option v-for="user in usersList" :value="user" :key="user.id">{{ user }}</option>
                      </select>
                  </div>
              </div>
          </div>
          <div class="task-description flex flex-column">
              <h4>Task Description</h4>
              <div class="input flex flex-column">
                  <label for="taskDescription">Description</label>
                  <textarea required id="taskDescription" v-model="taskDescription" />
              </div>
          </div>
          <div class="input flex flex-column">
              <label for="taskStartDate">Start Date</label>
              <input required type="date" id="taskStartDate" v-model="taskStartDate">
          </div>
          <div class="input flex flex-column">
              <label for="taskDueDate">Due Date</label>
              <input required type="date" id="taskDueDate" v-model="taskDueDate">
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
                  <button v-if="!editTask" type="submit" @click="saveDraft" class="dark-purple">Save Draft</button>
                  <button v-if="!editTask" type="submit" @click="publishTask" class="purple">Create Task</button>
                  <button v-if="editTask" type="sumbit" class="purple">Update Task</button>
              </div>
          </div>
      </form>
  </div>
</template>

<script>

import { mapMutations } from "vuex";
import { uid } from "uid";

export default {
    name: "TaskModal",
    data() {
        return {
            dateOptions: { year: "numeric", month: "short", day: "numeric" },
            taskId: null,
            taskName: null,
            taskSector: null,
            taskEpic: null,
            taskCreator: null,
            taskDescription: null,
            taskStartDate: null,
            taskDueDate: null,
            projectName: null,
            clientName: null,
            subtaskList: [],
            usersList: [],
            taskStartDateUnix: null
        }
    },
    methods: {
        ...mapMutations(['TOGGLE_TASK']),
        closeTask() {
            this.TOGGLE_TASK();
        },
        addNewSubtask() {
            this.subtaskList.push({
                id: uid(),
                name: "",
                description: ""
            })
        },
        deleteSubtask(id) {
            this.subtaskList = this.subtaskList.filter(subtask => subtask.id !== id)
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
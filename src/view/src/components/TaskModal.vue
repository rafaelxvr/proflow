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
              <label for="taskStartDate">Due Date</label>
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
export default {
    name: "TaskModal",
    data() {
        return {
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
            usersList: []
        }
    }
}
</script>

<style scoped>

</style>
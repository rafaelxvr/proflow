import { createStore } from "vuex";

export default createStore({
    state: {
        taskData: [],
        taskModal: null,
        modalActive: null,
        tasksLoaded: null,
        currentTaskArray: null,
        editTask: null
    },
    mutations: {
        TOGGLE_TASK(state) {
            state.taskModal = !state.taskModal;
        },
        TOGGLE_MODAL(state) {
            state.modalActive = !state.modalActive;
        },
        SET_TASK_DATA(state, payload) {
            state.taskData.push(payload);
        },
        TASKS_LOADED(state) {
            state.tasksLoaded = true;
        },
        SET_CURRENT_TASK(state, payload) {
            state.currentTaskArray = state.taskData.filter(task => {
                return task.id === parseInt(payload)
            })
        },
        TOGGLE_EDIT_TASK(state){
            state.editTask = !state.editTask;
        }
    },
    actions: {
        async GET_TASKS({ commit, state }) {
            //to do : Load tasks by ProjectId and ClientId
            await fetch("http://localhost:8080/api/tasks/")
                .then((res) => res.json())
                .then((data) => {
                    data.forEach(async record => {
                        if (!state.taskData.some(task => record.id === task.id)) {
                            await fetch(`http://localhost:8080/api/subtasks/tasks/${record.id}`)
                                .then(res => res.json())
                                .then(subtasks => {
                                    record.subtaskList = subtasks.map(subtask => ({ id: subtask.id, name: subtask.name, description: subtask.description }));
                                })
                            commit('SET_TASK_DATA', record);
                        }
                    })
                    commit('TASKS_LOADED');
                });
        }
    },
    modules: {}
})
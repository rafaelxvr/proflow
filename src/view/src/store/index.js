import { createStore } from "vuex";

export default createStore({
    state: {
        taskData: [],
        taskModal: null,
        modalActive: null,
        tasksLoaded: null,
        currentTaskArray: null
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
        }
    },
    actions: {
        async GET_TASKS({ commit, state }) {
            //to do : Load tasks by ProjectId and ClientId
            await fetch("http://localhost:8080/api/tasks/")
                .then((res) => res.json())
                .then((data) => {
                    data.forEach(record => {
                        if (!state.taskData.some(task => record.id === task.id)) {
                            commit('SET_TASK_DATA', record);
                        }
                    })
                    commit('TASKS_LOADED');
                });
        }
    },
    modules: {}
})
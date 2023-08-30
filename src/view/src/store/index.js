import { createStore } from "vuex";

export default createStore({
    state: {
        modalActive: null,
        taskData: [],
        taskModal: null,
        tasksLoaded: null,
        currentTaskArray: null,
        clientData: [],
        clientModal: null,
        clientsLoaded: null,
        currentClientArray: null,
        editClient: null,
        projectData: [],
        projectModal: null,
        projectsLoaded: null,
        currentProjectArray: null,
        editProject: null,
    },
    mutations: {
        TOGGLE_MODAL(state) {
            state.modalActive = !state.modalActive;
        },
        // TASK MUTATIONS
        TOGGLE_TASK(state) {
            state.taskModal = !state.taskModal;
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
        },
        DELETE_TASK(state, payload) {
            state.taskData.filter((task) => task.id !== payload);
        },
        UPDATE_TASK_STATUS(state, payload) {
            state.projectData.forEach(task => {
                if (task.id === payload.id) {
                    task.status = payload.status
                }
            })
        },
        //CLIENT MUTATIONS
        TOGGLE_CLIENT(state) {
            state.clientModal = !state.clientModal;
        },
        SET_CLIENT_DATA(state, payload) {
            state.clientData.push(payload);
        },
        CLIENTS_LOADED(state) {
            state.clientsLoaded = true;
        },
        SET_CURRENT_CLIENT(state, { payload }) {
            state.currentClientArray = state.clientData.flat().filter(client => {
                return client.id === parseInt(payload.id)
            })
        },
        TOGGLE_EDIT_CLIENT(state){
            state.editClient = !state.editClient;
        },
        // PROJECT MUTATIONS
        TOGGLE_PROJECT(state) {
            state.projectModal = !state.projectModal;
        },
        SET_PROJECT_DATA(state, payload) {
            state.projectData.push(payload);
        },
        PROJECTS_LOADED(state) {
            state.projectsLoaded = true;
        },
        SET_CURRENT_PROJECT(state, payload) {
            state.currentProjectArray = state.projectData.flat().filter(project => {
                return project.id === parseInt(payload.id)
            })
        },
        TOGGLE_EDIT_PROJECT(state){
            state.editProject = !state.editProject;
        },
        DELETE_PROJECT(state, payload) {
            state.projectData.filter((project) => project.id !== payload);
        }
    },
    actions: {
        async GET_TASKS({ commit, state }, { payload }) {
            console.log(payload)
            await fetch(`http://localhost:8080/api/tasks/projects/${payload.id}`)
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
        },
        async UPDATE_TASK({ commit, dispatch, state }, { payload }) {
            await dispatch('GET_TASKS');
            commit('TOGGLE_TASK', state);
            commit('TOGGLE_EDIT_TASK', state);
            commit('SET_CURRENT_TASK', state, payload.id)
        },
        async DELETE_TASK({ commit }, payload) {
            await fetch(`http://localhost:8080/api/tasks/${payload}`,{
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json",
                },
            })
                .then((res) => res.json())
                .then(() => {
                    commit('DELETE_TASK');
                });
        },
        async GET_CLIENTS({ commit }) {
            await fetch("http://localhost:8080/api/clients/")
                .then((res) => res.json())
                .then((data) => {
                    commit('SET_CLIENT_DATA', data);
                });
            commit('CLIENTS_LOADED');
        },
        async UPDATE_CLIENT({ commit, dispatch, state }, { payload }) {
            await dispatch('GET_CLIENTS');
            commit('TOGGLE_CLIENT', state);
            commit('TOGGLE_EDIT_CLIENT', state);
            commit('SET_CURRENT_CLIENT', state, payload.id)
        },
        async GET_PROJECTS({ commit, state }, { payload }) {
            await fetch(`http://localhost:8080/api/projects/clients/${payload.id}`)
                .then((res) => res.json())
                .then((data) => {
                    commit('SET_PROJECT_DATA', data);
                });
            commit('PROJECTS_LOADED');
        },
        async UPDATE_PROJECT({ commit, dispatch, state }, { payload }) {
            await dispatch('GET_PROJECTS');
            commit('TOGGLE_PROJECT', state);
            commit('TOGGLE_EDIT_PROJECT', state);
            commit('SET_CURRENT_PROJECT', state, payload.id)
        },
        async DELETE_PROJECT({ commit }, payload) {
            await fetch(`http://localhost:8080/api/projects/${payload}`, {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json",
                }
            })
                .then((res) => res.json())
                .then(() => {
                    commit('DELETE_TASK');
                });
        }
    },
    modules: {}
})
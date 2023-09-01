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
            state.taskData = payload;
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
            state.clientData = payload;
        },
        CLIENTS_LOADED(state) {
            state.clientsLoaded = true;
        },
        SET_CURRENT_CLIENT(state, { payload }) {
            state.currentClientArray = state.clientData.filter(client => {
                return client.id === parseInt(payload.id)
            })
        },
        TOGGLE_EDIT_CLIENT(state) {
            state.editClient = !state.editClient;
        },
        DELETE_CLIENT(state, payload) {
            state.clientData.filter((client) => client.id !== payload);
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
            state.taskData = [];
        
            const taskDataResponse = await fetch(`http://localhost:8080/api/tasks/projects/${payload.id}`);
            const taskData = await taskDataResponse.json();
        
            const subtaskPromises = taskData.map(async (record) => {
                if (!state.taskData.some((task) => record.id === task.id)) {
                    const subtasksResponse = await fetch(`http://localhost:8080/api/subtasks/tasks/${record.id}`);
                    const subtasks = await subtasksResponse.json();
        
                    record.subtaskList = subtasks.map((subtask) => ({
                        id: subtask.id,
                        name: subtask.name,
                        description: subtask.description,
                    }));
        
                    return record;
                }
            });
        
            const records = await Promise.all(subtaskPromises);
        
            commit('SET_TASK_DATA', records);
            commit('TASKS_LOADED');
        },
        async UPDATE_TASK({ dispatch, commit, state }, { payload }) {
            await dispatch('GET_TASKS', { payload: payload.project })
            commit('TOGGLE_TASK', state);
            commit('TOGGLE_EDIT_TASK', state);
            commit('SET_CURRENT_TASK', payload.id);
        },
        async DELETE_TASK({ commit }, payload) {
            await fetch(`http://localhost:8080/api/tasks/${payload}`,{
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json",
                },
            })
                .then(() => {
                    commit('DELETE_TASK', payload);
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
            commit('SET_CURRENT_CLIENT', { payload: payload })
        },
        async DELETE_CLIENT({ commit }, payload) {
            await fetch(`http://localhost:8080/api/clients/${payload}`,{
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json",
                },
            })
                .then(() => {
                    commit('DELETE_CLIENT', payload);
                });
        },
        async GET_PROJECTS({ commit, state }, { payload }) {
            await fetch(`http://localhost:8080/api/projects/clients/${payload.id}`)
                .then((res) => res.json())
                .then((data) => {
                    state.projectData = [];
                    data.forEach(project =>
                        commit('SET_PROJECT_DATA', project)
                    )
                });
            commit('PROJECTS_LOADED');
        },
        async UPDATE_PROJECT({ commit, dispatch, state }, { payload }) {
            await dispatch('GET_PROJECTS', { payload: payload.client });
            commit('TOGGLE_PROJECT', state);
            commit('TOGGLE_EDIT_PROJECT', state);
            commit('SET_CURRENT_PROJECT', payload)
        },
        async DELETE_PROJECT({ commit }, payload) {
            await fetch(`http://localhost:8080/api/projects/${payload}`, {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json",
                }
            })
                .then(() => {
                    commit('DELETE_PROJECT', payload);
                });
        }
    },
    modules: {}
})
import { createStore } from "vuex";

export default createStore({
    state: {
        taskModal: null,
    },
    mutations: {
        TOGGLE_TASK(state) {
            state.taskModal = !state.taskModal
        }
    },
    actions: {},
    modules: {}
})
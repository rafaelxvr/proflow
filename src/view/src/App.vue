<template>
  <div v-if="clientsLoaded">
    <div v-if="!mobile" class="app flex">
      <NavigationBar />
      <div class="app-content flex flex-column">
        <Modal v-if="modalActive" />
        <transition name="task">
          <TaskModal v-if="taskModal" />
        </transition>
        <transition name="client">
          <ClientModal v-if="clientModal" />
        </transition>
        <transition name="project">
          <ProjectModal v-if="projectModal" />
        </transition>
        <router-view />
      </div>
    </div>
    <div v-else class="mobile-message flex flex-column">
      <h2>Sorry, this app is not supported on Mobile Devices</h2>
      <p>To use this app, please use a Computer or Tablet</p>
    </div>
  </div>
</template>

<script>
import NavigationBar from '@/components/NavigationBar.vue'
import TaskModal from '@/components/TaskModal.vue'
import ClientModal from '@/components/ClientModal.vue'
import Modal from '@/components/Modal.vue'
import { mapState, mapActions } from 'vuex'
import ProjectModal from '@/components/ProjectModal.vue'
export default {
  data() {
    return {
      mobile: null
    }
  },
  components: {
    ProjectModal,
    NavigationBar,
    TaskModal,
    Modal,
    ClientModal
  },
  created() {
    this.GET_CLIENTS()
    this.checkScreen()
    window.addEventListener('resize', this.checkScreen)
  },
  methods: {
    ...mapActions(['GET_CLIENTS']),
    checkScreen() {
      const windowWidth = window.innerWidth
      if (windowWidth <= 750) {
        this.mobile = true
        return
      }
      this.mobile = false
    }
  },
  computed: {
    ...mapState([
      'taskModal',
      'modalActive',
      'tasksLoaded',
      'clientModal',
      'clientsLoaded',
      'projectModal',
      'projectsLoaded'
    ])
  }
}
</script>

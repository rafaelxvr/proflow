<template>
  <div @click="checkClick" ref="projectWrap" class="project-wrap flex flex-column">
    <form @submit.prevent="submitForm" class="project-content">
      <LoadingScreen v-show="loading" />
      <h1 v-if="!editProject">New Project</h1>
      <h1 v-else>Edit Project</h1>

      <div class="project-information flex flex-column">
        <h4>Project Information</h4>
        <div class="input flex flex-column">
          <label for="projectName">Project Name</label>
          <input required type="text" id="projectName" v-model="project.name" />
        </div>
        <div class="project-details flex">
          <div class="input flex flex-column">
            <label for="projectDescription">Project Description</label>
            <input required type="text" id="projectDescription" v-model="project.description" />
          </div>
          <div class="input flex flex-column">
            <label for="status">Project Status</label>
            <select v-model="project.status" :key="project.status">
              <option selected>Select a Status</option>
              <option>Backlog</option>
              <option>Ready For Dev</option>
              <option>In Progress</option>
              <option>Done</option>
            </select>
          </div>
        </div>
      </div>

      <div class="save flex">
        <div class="left">
          <button type="button" @click="closeProject" class="red">Cancel</button>
        </div>
        <div class="right flex">
          <button v-if="!editProject" type="submit" v-on:@click.once="uploadProject" class="purple">
            Create Project
          </button>
          <button v-if="editProject" type="submit" v-on:@click.once="updateProject" class="purple">
            Update Project
          </button>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import { mapMutations, mapState, mapActions } from 'vuex'
import LoadingScreen from '@/components/LoadingScreen.vue'

export default {
  name: 'ProjectModal',
  components: {
    LoadingScreen
  },
  data() {
    return {
      project: {
        id: null,
        name: '',
        description: '',
        status: '',
        client: null
      },
      selectedStatus: null,
      loading: null
    }
  },
  created() {
    this.project.client = this.currentClientArray[0]

    if (this.editProject) {
      const currentProject = this.currentProjectArray[0]
      for (const prop in currentProject) {
        if (currentProject.hasOwnProperty(prop)) {
          this.project[prop] = currentProject[prop]
        }
      }
    }
  },
  computed: {
    ...mapState(['editProject', 'currentProjectArray', 'currentClientArray', 'projectData'])
  },
  methods: {
    ...mapMutations(['TOGGLE_PROJECT', 'TOGGLE_MODAL', 'TOGGLE_EDIT_PROJECT']),
    ...mapActions(['UPDATE_PROJECT', 'GET_PROJECTS']),
    checkClick(event) {
      if (event.target === this.$refs.projectWrap) {
        this.TOGGLE_MODAL()
      }
    },
    closeProject() {
      this.TOGGLE_PROJECT()
      if (this.editProject) {
        this.TOGGLE_EDIT_PROJECT()
      }
    },
    async uploadProject() {
      this.loading = true

      await fetch('http://localhost:8080/api/projects/add', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(this.project)
      })
        .then((res) => res.json())
        .then((data) => {
          this.project.id = data?.id
        })

      await this.GET_PROJECTS({ payload: this.project.client })

      this.loading = false
      this.TOGGLE_PROJECT()
    },
    async updateProject() {
      this.loading = true

      let payload = {
        id: null,
        name: '',
        document: '',
        email: '',
        client: this.currentClientArray[0]
      }

      await fetch('http://localhost:8080/api/projects/', {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(this.project)
      })
        .then((res) => res.json())
        .then(async (data) => {
          payload = {
            id: data.id,
            name: data.name,
            description: data.description,
            status: data.status,
            client: data.client
          }
        })

      await this.UPDATE_PROJECT({ payload: payload })
    },
    async submitForm() {
      if (this.editProject) {
        await this.updateProject()
        return
      }

      await this.uploadProject()
    }
  }
}
</script>

<style lang="scss" scoped>
.project-wrap {
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
  @media (min-width: 900px) {
    left: 90px;
  }

  .project-content {
    position: relative;
    padding: 56px;
    max-width: 700px;
    width: 100%;
    background-color: #141625;
    color: white;
    box-shadow:
      10px 4px 6px -1px rgba(0, 0, 0, 0.2),
      0 2px 4px -1px rgba(0, 0, 0, 0.06);

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

    .project-information {
      margin-bottom: 48px;

      .project-details {
        gap: 16px;
        div {
          flex: 1;
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

  input,
  select {
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

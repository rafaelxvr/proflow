<template>
  <div @click="checkClick" ref="clientWrap" class="client-wrap flex flex-column">
      <form @submit.prevent="submitForm" class="client-content">
          <LoadingScreen v-show="loading"/>
          <h1 v-if="!editTask">New Client</h1>
          <h1 v-else>Edit Client</h1>

          <div class="client-information flex flex-column">
            <h4>Client Information</h4>
              <div class="input flex flex-column">
                  <label for="clientName">Client Name</label>
                  <input required type="text" id="clientName" v-model="client.name">
              </div>
              <div class="task-details flex">
                  <div class="input flex flex-column">
                      <label for="clientDocument">Document</label>
                      <input required type="text" id="clientDocument" v-model="client.document">
                  </div>
                  <div class="input flex flex-column">
                      <label for="clientEmail">Epic</label>
                      <input required type="text" id="clientEmail" v-model="client.email">
                  </div>
              </div>
          </div>

          <div class="save flex">
              <div class="left">
                  <button type="button" @click="closeClient" class="red">Cancel</button>
              </div>
              <div class="right flex">
                  <button v-if="!editTask" type="submit" @click="publishClient" class="purple">Create Client</button>
                  <button v-if="editTask" type="submit" @click="updateClient" class="purple">Update Client</button>
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
            client: {
                id: null,
                name: '',
                documentId: '',
                email: ''
            },
            currentClientArray: [],
            loading: null
        }
    },
    created() {
        if (this.editClient) {
            const currentClient = this.currentClientArray[0];
            for (const prop in currentClient) {
                if (currentClient.hasOwnProperty(prop)) {
                    this.client[prop] = currentClient[prop];
                }
            }
        }
    },
    computed: {
        ...mapState(['editClient', 'currentClientArray']),
    },
    methods: {
        ...mapMutations(['TOGGLE_CLIENT', 'TOGGLE_MODAL', 'TOGGLE_EDIT_CLIENT']),
        ...mapActions(['UPDATE_CLIENT']),
        checkClick(event) {
          if (event.target === this.$refs.clientWrap) {
            this.TOGGLE_MODAL();
          }
        },
        closeClient() {
            this.CLOSE_CLIENT();
            if (this.editTask) {
                this.TOGGLE_EDIT_CLIENT();
            }
        },
        async uploadClient() {
            if (this.subtaskList.length <= 0) {
                alert('Please ensure you added subtasks!')
                return;
            }

            this.loading = true;

            await fetch("http://localhost:8080/api/client/add", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(this.client),
                })
                .then(res => res.json())
                .then(data => {
                   this.client.id = data?.id;
                });

            this.loading = false;
            this.TOGGLE_CLIENT();
        },
        async updateClient() {
            if (this.subtaskList.length <= 0) {
                alert('Please ensure you added subtasks!')
                return;
            }
            this.loading = true;

            let payload = {
                id: null,
                name: '',
                document: '',
                email: ''
            };

            await fetch("http://localhost:8080/api/client/update", {
                method: "PUT",
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
                        documentId: data.documentId,
                        email: data.email
                    };
                });
            await this.UPDATE_CLIENT(payload);
        },
        async submitForm() {
            if (this.editTask) {
                await this.updateClient()
                return;
            }

            await this.uploadClient()
        }
    }
}
</script>

<style lang="scss" scoped>
  .client-wrap {
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

    .client-wrap {
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
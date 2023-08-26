<template>
  <main>
      <Navbar/>
      <div class="my-5">
          <div class="mx-auto w-25" style="max-width: 100%;">
              <h2 class="text-center mb-3">
                  Update Client
              </h2>
              <form @submit.prevent="updateClient">
                  <div class="row">
                      <div class="col-md-12 form-group mb-3">
                          <label for="name" class="form-label">Name</label>
                          <input type="text" name="name" id="name" class="form-control" placeholder="Name" required v-model="client.name">
                      </div>
                  </div>

                  <div class="row">
                      <div class="col-md-12 form-group mb-3">
                          <label for="document" class="form-label">Document</label>
                          <input type="text" name="document" id="document" class="form-control" placeholder="Document" required v-model="client.document">
                      </div>
                  </div>

                  <div class="row">
                      <div class="col-md-12 form-group mb-3">
                          <label for="email" class="form-label">Email</label>
                          <input type="text" name="email" id="email" class="form-control" placeholder="Email" required v-model="client.email">
                      </div>
                  </div>

                  <div class="row">
                      <div class="col-md-12 form-group">
                          <input type="submit" value="Submit" class="btn btn-primary w-100">
                      </div>
                  </div>
              </form>
          </div>
      </div>
  </main>
</template>

<script>
import Navbar from "../components/HeaderNav.vue";

export default {
    name: "UpdateClient.vue",
    components: { Navbar },
    data() {
        return {
            client: {
                id: '',
                name: '',
                email: '',
                document: ''
            }
        }
    },
    methods: {
        getClient() {
            fetch(`/clients/${this.$route.params.id}`)
                .then(res => res.json)
                .then(data => {
                    this.client = data
                    console.log(this.client)
                })
        },
        updateClient() {
          fetch('/clients', {
              method: 'PUT',
              headers: {
                  'Content-Type': 'application/json'
              },
              body: JSON.stringify(this.client)
          })
              .then(data => {
                  console.log(data)
                  this.$router.push("/")
              })
        }
    },
    beforeMount() {
        this.methods.getClients()
    }
}
</script>

<style scoped>

</style>
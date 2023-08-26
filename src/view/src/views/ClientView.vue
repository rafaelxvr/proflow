<template>
  <main>
      <Navbar/>
      <div class="container">
          <div class="row">
              <div class="col-md-12">
                <h1 class="text-center">View Clients</h1>
                  <a href="/add" class="btn btn-primary">Add Clients</a>
                  <table class="table table-striped">
                      <thead>
                      <tr>
                          <th scope="col">Id</th>
                          <th scope="col">Name</th>
                          <th scope="col">Email</th>
                      </tr>
                      </thead>
                      <tbody>
                        <tr v-for="client in clients" :key="client.id">
                            <th scope="row">{{ client.id }}</th>
                            <td>{{ client.name }}</td>
                            <td>{{ client.email }}</td>
                            <td>
                                <a class="btn btn-primary" :href="`/edit/${client.id}`">Edit</a>
                                <button class="btn btn-danger mx-2" @click="deleteClient(client.id)">Delete</button>
                            </td>
                        </tr>
                      </tbody>
                  </table>
              </div>
          </div>
      </div>
  </main>
</template>

<script>
import Navbar from "../components/HeaderNav.vue";

export default {
    name: "ClientView",
    components: { Navbar },
    data() {
        return {
            clients: []
        }
    },
    methods: {
        getClients() {
            fetch('/clients')
                .then(res => res.json)
                .then(data => {
                    this.clients = data
                    console.log(data)
                })
        },
        deleteClient(id) {
            fetch(`/clients/${id}`, {
                method: 'DELETE'
            })
            .then(data => {
                console.log(data)
                this.getClients()
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
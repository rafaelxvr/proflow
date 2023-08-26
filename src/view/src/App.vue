<template>
    <div v-if="!mobile" class="app flex">
        <NavigationBar/>
        <div class="app-content flex flex-column">
            <TaskModal />
            <router-view />
        </div>
    </div>
    <div v-else class="mobile-message flex flex-column">
        <h2>Sorry, this app is not supported on Mobile Devices</h2>
        <p>To use this app, please use a Computer or Tablet</p>
    </div>
</template>

<script>
import NavigationBar from "@/components/NavigationBar.vue";
import TaskModal from "@/components/TaskModal.vue"
    export default {
        data() {
          return {
              mobile: null
          }
        },
        components: {
            NavigationBar,
            TaskModal
        },
        created(){
          this.checkScreen();
          window.addEventListener("resize", this.checkScreen);
        },
        methods: {
            checkScreen() {
                const windowWidth = window.innerWidth;
                if(windowWidth <= 750) {
                    this.mobile = true;
                    return;
                }
                this.mobile = false;
            }
        }
    }
</script>

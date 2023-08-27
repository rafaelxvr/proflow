<template>
  <div class="home container">
      <div class="header flex">
          <div class="left flex flex-column">
              <h1>Tasks</h1>
              <span>Project Name + Client Name</span>
          </div>
          <div class="right flex">
              <div @click="toggleFilterMenu" class="filter flex">
                  <span>Filter by Status</span>
                  <img src="@/assets/icon-arrow-down.svg" alt="">
                  <ul v-show="filterMenu" class="filter-menu">
                      <li>Backlog</li>
                      <li>Ready for Dev</li>
                      <li>In Progress</li>
                      <li>Done</li>
                      <li>Clear Filter</li>
                  </ul>
              </div>
              <div @click="newTask" class="button flex">
                  <div class="inner-button flex">
                      <img src="@/assets/icon-plus.svg" alt="">
                  </div>
                  <span>New Task</span>
              </div>
          </div>
      </div>
  </div>
</template>


<script>
import { mapMutations } from "vuex"
  export default {
      name: "Home",
      data() {
        return {
            filterMenu: null,
        }
      },
      components: {},
      methods: {
          ...mapMutations(['TOGGLE_TASK']),
          newTask() {
              this.TOGGLE_TASK();
          },
          toggleFilterMenu() {
              this.filterMenu = !this.filterMenu;
          }
      }
  }
</script>

<style lang="scss" scoped>
  .home {
    color: white;
  }

  .header {
    margin-bottom: 65px;

    .left,
    .right {
      flex: 1;
    }

    .right {
      justify-content: flex-end;
      align-items: center;

      .button,
      .filter {
        align-items: center;

        span {
          font-size: 12px;
        }
      }

      .filter {
        position: relative;
        margin-right: 40px;
        cursor: pointer;

        img {
          margin-left: 12px;
          width: 9px;
          height: 5px;
        }

        .filter-menu {
          width: 120px;
          position: absolute;
          top: 25px;
          list-style: none;
          background-color: #1e2139;
          box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);

          li {
            cursor: pointer;
            font-size: 12px;
            padding: 10px 20px;

            &.hover {
              color: #1e2139;
              background-color: white;
            }
          }
        }
      }

      .button {
        padding: 8px 10px;
        background-color: #7c5dfa;
        border-radius: 40px;

        .inner-button {
          margin-right: 8px;
          border-radius: 50%;
          padding: 8px;
          align-items: center;
          justify-content: center;
          background-color: white;

          img {
            width: 10px;
            height: 10px;
          }
        }
      }
    }
  }
</style>
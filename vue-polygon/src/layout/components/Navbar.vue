<template>
  <div class="navbar">
    <hamburger :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />

    <breadcrumb class="breadcrumb-container" />

    <div class="right-menu">
      <el-dropdown class="avatar-container" trigger="click">
        <div class="avatar-wrapper">
          <label class="user-avatar">{{employee ? employee.employeeName : null}}</label>
          <i class="el-icon-caret-bottom" />
        </div>
        <el-dropdown-menu slot="dropdown" class="user-dropdown">
          <router-link to="/">
            <el-dropdown-item>
              首页
            </el-dropdown-item>
          </router-link>
          <el-dropdown-item divided @click.native="dialogVisible = true">
            <span style="display:block;">个人中心</span>
          </el-dropdown-item>
          <a target="_blank" href="https://github.com/PanJiaChen/vue-admin-template/">
            <el-dropdown-item>Github</el-dropdown-item>
          </a>
          <el-dropdown-item divided @click.native="logout">
            <span style="display:block;">注销</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <el-dialog title="个人中心" :show-close="false" :close-on-click-modal="false" :visible.sync="dialogVisible">
      <div>
        <label>员工编号: </label>
        <span>{{employee.employeeNo}}</span>
      </div>
      <div>
        <label>员工姓名: </label>
        <span>{{employee.employeeName}}</span>
      </div>
      <div>
        <label>所属部门</label>
        <span>{{employee.departmentId}}</span>
      </div>
      <div>
        <label>拥有角色: </label>
        <span v-for="role in employee.roles">{{role.nameZh}}</span>
      </div>
      <div>
        <label>更改密码: </label>
        <el-input v-model="password" required autocomplete="off"></el-input>
      </div>
      
      <div slot="footer" class="dialog-footer">
        <el-button @click="reset">取 消</el-button>
        <el-button type="primary" @click="onSubmit">保 存</el-button>
      </div>
    </el-dialog>
  </div>

</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import { updatePassword } from '@/api/employee'

export default {
  components: {
    Breadcrumb,
    Hamburger
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'employee'
    ])
  },
  data(){
    return {
      dialogVisible: false,
      password: ""
    }
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    async logout() {
      await this.$store.dispatch('employee/logout')
      this.$router.push(`/login?redirect=${this.$route.fullPath}`)
    },
    onSubmit(){
      let params = {id: this.employee.id, password: this.password}
      updatePassword(params).then(() => {
        this.dialogVisible = false
        this.logout()
      })
    },
    reset(){
      this.password = ''
      this.dialogVisible = false;
    }
  }
}
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color:transparent;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;
    margin-right: 20px;
    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 20px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>

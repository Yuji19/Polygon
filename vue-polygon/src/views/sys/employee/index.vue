<template>
  <div class="app-container">
    <el-form :inline="true" :model="form" >
      <el-form-item label="员工编号">
        <el-input v-model="form.no" ></el-input>
      </el-form-item>
      <el-form-item label="员工姓名">
        <el-input v-model="form.name" ></el-input>
      </el-form-item>
      <el-form-item label="所属部门">
        <el-select v-model="form.departmentId" clearable placeholder="请选择部门">
            <el-option v-for="item in departments" :label="item.name" :value="item.id"></el-option>
          </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
        <el-button type="success" v-permission="['employee_add']" @click="dialogVisible = true">添加</el-button>
      </el-form-item>
    </el-form>
    

    <!-- 添加用户表单弹框  -->
    <el-dialog title="用户添加" :show-close="false" :close-on-click-modal="false" :visible.sync="dialogVisible">
      <el-form :model="addForm" :rules="rules" ref="addForm">
        <el-form-item label="员工编号" prop="no" label-width="120">
          <el-input v-model="addForm.no" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="员工姓名" prop="name" label-width="120">
          <el-input v-model="addForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="所属部门" prop="departmentId" label-width="120">
          <el-select v-model="addForm.departmentId" placeholder="请选择部门">
            <el-option v-for="item in departments" :label="item.name" :key="'c'+item.id" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="拥有角色" label-width="120">
          <el-select v-model="addForm.rids" multiple  placeholder="请选择角色">
            <el-option v-for="item in roles" :label="item.nameZh" :key="'a'+item.id" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetAddForm('addForm')">取 消</el-button>
        <el-button type="primary" @click="onSubmitEmployee('addForm')">保 存</el-button>
      </div>
    </el-dialog>

    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="ID" width="95">
        <template slot-scope="scope">
          {{ scope.$index+1 }}
        </template>
      </el-table-column>
      <el-table-column label="员工编号" width="150" >
        <template slot-scope="scope">
          {{ scope.row.no }}
        </template>
      </el-table-column>
      <el-table-column label="员工姓名" width="110" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="所属部门" width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.departmentName }}
        </template>
      </el-table-column>
      <el-table-column label="状态" width="150" align="center">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.enabled"
            active-text="启用"
            active-color="#13ce66"
            :disabled="$_hasPermission('employee_update')"
            @change="handleEnabledChange(scope.row)"
            inactive-text="禁用" style="font-size: 12px">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="角色" width="110" align="center">
        <template slot-scope="scope">
          <span v-for="role in scope.row.roles" >
            {{ role.nameZh }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="操作"  align="center">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" v-permission="['employee_update']"    @click="showBaseVisible(scope.row)">修改</el-button>
          <el-button type="primary" size="mini" v-permission="['employee_add','employee_delete']" @click="showRoleVisible(scope.row)">角色</el-button>
          <el-button type="danger" size="mini" v-permission="['employee_delete']">删除</el-button>
        </template>
      </el-table-column>
      
    </el-table>

    <!-- 角色增减弹框 -->
    <el-dialog title="角色增减" :show-close="false" :close-on-click-modal="false" :visible.sync="roleVisible">
      <el-select v-model="rids"  multiple placeholder="请选择">
        <el-option
          v-for="(item,index) in roles"
          :key="index"
          :label="item.nameZh"
          :value="item.id">
        </el-option>
      </el-select>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetRids">取 消</el-button>
        <el-button type="primary" @click="onSubmitRids">保 存</el-button>
      </div>
    </el-dialog>

    <!-- 用户基础信息弹框 -->
    <el-dialog title="用户基础信息" :show-close="false" :close-on-click-modal="false" :visible.sync="baseVisible">
      <el-form :model="updateForm" :rules="rules" ref="updateForm">
        <el-form-item label="员工编号" prop="no" label-width="120">
          <el-input v-model="updateForm.no" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="员工姓名" prop="name" label-width="120">
          <el-input v-model="updateForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="所属部门" prop="departmentId" label-width="120">
          <el-select v-model="updateForm.departmentId" placeholder="请选择部门">
            <el-option v-for="item in departments" :label="item.name" :key="'c'+item.id" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetUpdateForm('updateForm')">取 消</el-button>
        <el-button type="primary" @click="onSubmitBase('updateForm')">保 存</el-button>
      </div>
    </el-dialog>
   
    <el-pagination
      @current-change="handleCurrentChange"
      :current-page.sync="form.pageNum"
      :page-size="form.pageSize"
      layout="total,prev, pager, next, jumper"
      :total="totalCount"
       v-show="this.list.length>0">
    </el-pagination>
      
  </div>
</template>

<script>

  import { mapGetters } from 'vuex'
  import { getList, addInfo ,updateInfo, updateEnabled, addEmployeeRole, deleteEmployeeRole } from '@/api/employee'
  import { getAllDepartment } from '@/api/department'
  import { getAllRole } from '@/api/role'

  export default {

    data(){
      return {
        list:[],
        listLoading: false,
        totalCount: 0,
        form:{
          no: "",
          name: "",
          departmentId: null,
          pageNum: 1,
          pageSize: 10
        },
        dialogVisible: false,
        addForm:{
          no: "",
          name: "",
          departmentId: null,
          rids: []
        },
        departments: [],
        roles: [],
        rules: {
          no: [{required: true,message: '员工编号不能为空',trigger: 'blur'}],
          name: [{required: true,message: '员工姓名不能为空',trigger: 'blur'}],
          departmentId: [{required: true,message: '员工姓名不能为空',trigger: 'blur'}]
        },
        roleVisible: false,
        rids: [],
        row: null,
        updateForm: {},
        baseVisible: false
      }
    },
    computed: {
      ...mapGetters([
          'employee'
        ])
    },
    mounted() {

      this.form.departmentId = this.employee ? this.employee.departmentId : 0
      this.fetchData()
      this.fetchDepartments()
      this.fetchRoles()
      
    },
    methods: {
      fetchData() {
        this.listLoading = true
        getList(this.form).then(resp => {
          this.list = resp.data.records
          this.totalCount = resp.data.totalCount
          this.listLoading = false
          if (this.form.departmentId == 0) {
            this.form.departmentId = ""
          }
        })
      },
      fetchDepartments(){
        getAllDepartment().then(resp => {
          this.departments = resp.data
        })
      },
      fetchRoles(){
        getAllRole().then(resp => {
          this.roles = resp.data
        })
      },
      handleCurrentChange(pageNum){
        this.form.pageNum = pageNum
        this.fetchData()
      },
      onSubmit(){
        if (this.form.departmentId == "") {
          this.form.departmentId = 0
        }
        this.fetchData()
      },
      onSubmitEmployee(addForm){
        this.$refs[addForm].validate((valid) => {
          if (valid) {
            addInfo(this.addForm).then(resp => {
               this.dialogVisible = false
               //刷新
               this.fetchData()
              })
          }
        })
      },
      resetAddForm(addForm){
        this.dialogVisible = false
        this.$refs[addForm].resetFields()
      },
      showRoleVisible(row){
        this.row = row
        this.rids = []
        for(let role of this.row.roles){
          this.rids.push(role.id)
        }
        this.roleVisible = true
      },
      onSubmitRids(){
        //原拥有的角色
        let rolesBak = []
        for(let role of this.row.roles){
          rolesBak.push(role.id)
        }
        //经选择后的角色
        var selRids = this.rids
        //待删除的角色
        var delRids = []
        for(let i = 0; i < rolesBak.length; i++){
          if(selRids.length > 0){
            for(let j = 0; j < selRids.length; j++){
              if(rolesBak[i] == selRids[j]){
                selRids.splice(j, 1);  //移除用户已拥有的角色，剩下的就是要添加的角色
                break;
              }else{
                delRids.push(rolesBak[i]); //要删除的角色
                break;
              }
            }
          }else{

            delRids.push(rolesBak[i]);
          }
          
        }
          
       
        if(selRids.length > 0){
          let params = {
            rids: selRids,
            eid: this.row.id
          }
          //add
          addEmployeeRole(params).then(resp => {
             if (delRids.length < 1){
                this.roleVisible = false
                this.rids = []
                if (this.row.id === this.employee.id) {
                  this.$store.dispatch('employee/getInfo').then(() => {
                    let path = this.$route.path
                    this.$router.replace({
                      path: '/redirect',
                      query: {
                        path: path
                      }
                    })
                  })
                }else{
                  this.fetchData()
                }
                return
             }
          })
        }
        if (delRids.length > 0) {
          let params = {
            rids: delRids,
            eid: this.row.id
          }
          //delete
          deleteEmployeeRole(params).then(resp => {
              this.roleVisible = false
              this.rids = []
              if (this.row.id === this.employee.id) {
                this.$store.dispatch('employee/getInfo').then(() => {
                  let path = this.$route.path
                    this.$router.replace({
                      path: '/redirect',
                      query: {
                        path: path
                      }
                    })
                })
              }else{
                this.fetchData()
              }
          })
        }
        
          
      },
      resetRids(){
        this.rids = []
        this.roleVisible = false
      },
      showBaseVisible(row){
        this.updateForm = row
        this.baseVisible = true
      },
      onSubmitBase(updateForm){
        this.$refs[updateForm].validate((valid) => {
          if (valid) {
            delete this.updateForm.roles
            updateInfo(this.updateForm).then(resp => {
               this.baseVisible = false
               //刷新
               this.fetchData()
              })
          }
        })
      },
      resetUpdateForm(updateForm){
        this.baseVisible = false
        this.$refs[updateForm].resetFields()
      },
      handleEnabledChange(row){
        let params = {id:row.id, enabled:row.enabled}
        updateEnabled(params).then(resp => {

        })
      }
    }
  }

</script>
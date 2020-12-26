<template>
  <div class="app-container">
    <el-form :inline="true" :model="form" >
      <el-form-item label="角色名称">
        <el-input v-model="form.name" ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
        <el-button type="success" v-permission="['employee_add']" @click="dialogVisible = true">添加</el-button>
      </el-form-item>
    </el-form>
    

    <!-- 添加角色表单弹框  -->
    <el-dialog title="角色添加" :show-close="false" :close-on-click-modal="false" :visible.sync="dialogVisible">
      <el-form :model="addForm" :rules="rules" ref="addForm">
        <el-form-item label="角色名称" prop="no" label-width="120">
          <el-input v-model="addForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="角色中文名称" prop="name" label-width="120">
          <el-input v-model="addForm.nameZh" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="操作权限" label-width="120">
          <el-select v-model="pids1" multiple  placeholder="请赋予权限">
            <el-option v-for="(item,index) in permissions1" :label="item.operation.name" :key="'a'+index" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="页面权限" label-width="120">
          <el-select v-model="pids2" multiple  placeholder="请赋予权限">
            <el-option v-for="(item,index) in permissions2" :label="item.menu.name" :key="'b'+index" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetAddForm('addForm')">取 消</el-button>
        <el-button type="primary" @click="onSubmitRole('addForm')">保 存</el-button>
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
      <el-table-column label="角色名称" width="150" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="角色中文名称" width="150" align="center">
        <template slot-scope="scope">
          {{ scope.row.nameZh }}
        </template>
      </el-table-column>
      <el-table-column label="创建时间" width="180" align="center">
        <template slot-scope="scope">
          {{ scope.row.gmtCreate }}
        </template>
      </el-table-column>
      <el-table-column label="修改时间" width="180" align="center">
        <template slot-scope="scope">
          {{ scope.row.gmtModified }}
        </template>
      </el-table-column>
      <el-table-column label="操作"  align="center">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" v-permission="['role_update']"    @click="showBaseVisible(scope.row)">修改</el-button>
          <el-button type="primary" size="mini" v-permission="['role_add','role_delete']" @click="showPermissionVisible(scope.row)">权限</el-button>
          <el-button type="danger" size="mini" v-permission="['role_delete']" @click="handleDeleteRole(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
      
    </el-table>

    <!-- 权限增减弹框 -->
    <el-dialog title="权限增减" :show-close="false" :close-on-click-modal="false" :visible.sync="permissionVisible">
      <div style="display:inline;">
        <label>操作权限:</label>
        <el-select v-model="own1"  multiple placeholder="请选择">
          <el-option
            v-for="(item,index) in permissions1"
            :key="'c'+index"
            :label="item.operation.name"
            :value="item.id">
          </el-option>
        </el-select>
      </div>
      <div style="display:inline;">
        <label>页面权限:</label>
        <el-select v-model="own2"  multiple placeholder="请选择">
          <el-option
            v-for="(item,index) in permissions2"
            :key="'d'+index"
            :label="item.menu.name"
            :value="item.id">
          </el-option>
        </el-select>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetPids">取 消</el-button>
        <el-button type="primary" @click="onSubmitPids">保 存</el-button>
      </div>
    </el-dialog>

    <!-- 角色基础信息弹框 -->
    <el-dialog title="用户基础信息" :show-close="false" :close-on-click-modal="false" :visible.sync="baseVisible">
      <el-form :model="updateForm" :rules="rules" ref="updateForm">
        <el-form-item label="角色名称" prop="no" label-width="120">
          <el-input v-model="updateForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="角色中文名称" prop="name" label-width="120">
          <el-input v-model="updateForm.nameZh" autocomplete="off"></el-input>
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
  import { getAllRole, getRolePage, addRole, deleteRole, addRolePermission, 
    deleteRolePermission, updateRoleBase } from '@/api/role'
  import { getAllPermission, getPermissionOfOperation, 
    getPermissionOfMenu } from '@/api/permission'

  export default {

    data(){
      return {
        list:[],
        listLoading: false,
        totalCount: 0,
        form:{
          name: "",
          pageNum: 1,
          pageSize: 10
        },
        dialogVisible: false,
        addForm:{
          name: "",
          nameZh: "",
          pids: []
        },
        pids1: [],
        pids2: [],
        rules: {
          name: [{required: true,message: '角色名称不能为空',trigger: 'blur'}],
        },
        permissionVisible: false,
        updateForm: {
          name: '',
          nameZh: ''
        },
        baseVisible: false,
        row: null,
        permissions1: [],
        permissions2: [],
        own1: [],
        own2: [],
        ownBak1: [],
        ownBak2: []
      }
    },
    computed: {
      ...mapGetters([
          'employee'
        ])
    },
    mounted() {
      this.fetchData()
      this.fetchAllPermission()
    },
    methods: {
      fetchData() {
        this.listLoading = true
        getRolePage(this.form).then(resp => {
          this.list = resp.data.records
          this.totalCount = resp.data.totalCount
          this.listLoading = false
        })
      },
      fetchAllPermission(){
        getAllPermission().then(resp => {
          this.permissions1 = resp.data[0]
          this.permissions2 = resp.data[1]
        })
      },
      handleCurrentChange(pageNum){
        this.form.pageNum = pageNum
        this.fetchData()
      },
      onSubmit(){
        this.fetchData()
      },
      onSubmitRole(addForm){
        this.$refs[addForm].validate((valid) => {
          if (valid) {
            this.addForm.pids = this.pids1.concat(this.pids2)
            addRole(this.addForm).then(resp => {
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
        this.addForm.pids=[]
        this.pids1=[]
        this.pids2=[]
      },
      showPermissionVisible(row){
        this.row = row;
        getPermissionOfOperation(row.id).then(resp => {
          for (let i=0; i < resp.data.length; i++ ) {
            this.own1.splice(i,1,resp.data[i].id)
          }

          this.ownBak1 = JSON.parse(JSON.stringify(this.own1))
          
        })
        getPermissionOfMenu(row.id).then(resp => {
          for (let i=0; i < resp.data.length; i++ ) {
            this.own2.splice(i,1,resp.data[i].id)
          }
          this.ownBak2 = JSON.parse(JSON.stringify(this.own2))
        })
        
        this.permissionVisible = true
        
      },
     
      onSubmitPids(){
        //原拥有的权限
        let ownBak = this.ownBak1.concat(this.ownBak2)
        
        //经选择后的权限
        let selPids = this.own1.concat(this.own2)
        
        //待删除的权限
        var delPids = []
        for(let i = 0; i < ownBak.length; i++){
          if(selPids.length > 0){
            for(let j = 0; j < selPids.length; j++){
              if(ownBak[i] == selPids[j]){
                selPids.splice(j, 1);  //移除角色已拥有的权限，剩下的就是要添加的权限
                break;
              }else{
                delPids.push(ownBak[i]); //要删除的权限
                break;
              }
            }
          }else{

            delPids.push(ownBak[i]);
          }
          
        }
          
       
        if(selPids.length > 0){
          let params = {
            rid: this.row.id,
            pids: selPids
          }
          //add
          addRolePermission(params).then(resp => {
             
          })
        }
        if (delPids.length > 0) {
          let params = {
            rid: this.row.id,
            pids: delPids
          }
          //delete
          deleteRolePermission(params).then(resp => {
             
          })
        }

        this.permissionVisible = false
        this.own1 = []
        this.own2 = []
        this.ownBak1 = []
        this.ownBak2 = []
      },
      resetPids(){
        this.own1 = []
        this.own2 = []
        this.ownBak1 = []
        this.ownBak2 = []
        this.permissionVisible = false
      },
      showBaseVisible(row){
        this.updateForm = Object.assign({},row)
        this.baseVisible = true
      },
      onSubmitBase(updateForm){
        this.$refs[updateForm].validate((valid) => {
          if (valid) {
            updateRoleBase(this.updateForm).then(resp => {
              this.baseVisible = false
              this.fetchData()
            })
          }
        })
      },
      resetUpdateForm(updateForm){
        this.baseVisible = false
        this.$refs[updateForm].resetFields()
      },
      handleDeleteRole(rid){
        this.$confirm('此操作将删除该角色, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteRole(rid).then(resp => {
            this.fetchData()
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
      }
    }
  }

</script>
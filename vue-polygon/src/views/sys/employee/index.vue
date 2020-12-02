<template>
  <div class="app-container">
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
          {{ scope.$index }}
        </template>
      </el-table-column>
      <el-table-column label="员工编号">
        <template slot-scope="scope">
          {{ scope.row.employeeNo }}
        </template>
      </el-table-column>
      <el-table-column label="员工姓名" width="110" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.employeeName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="所属部门" width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.departmentId }}
        </template>
      </el-table-column>
      <el-table-column label="状态" width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.enabled }}
        </template>
      </el-table-column>
      <el-table-column label="角色" width="110" align="center">
        <template slot-scope="scope">
          <span v-for="role in scope.row.roles" >
            {{ role.nameZh }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="110" align="center">
        <template slot-scope="scope">
          <el-button type="primary" v-hasPermission="'employee_update'">修改</el-button>
          <el-button type="success" v-hasPermission="'employee_add'">增加角色</el-button>
          <el-button type="danger" v-hasPermission="'employee_delete'">删除角色</el-button>
          <el-button type="danger" v-hasPermission="'employee_delete'">删除</el-button>
        </template>
      </el-table-column>
      
    </el-table>

   
    <el-pagination
      @current-change="handleCurrentChange"
      :current-page.sync="pageNum"
      :page-size="pageSize"
      layout="total,prev, pager, next, jumper"
      :total="totalCount"
       v-show="this.list.length>0">
    </el-pagination>
      
  </div>
</template>

<script>
  import {getList} from '@/api/employee'
  export default {
    data(){
      return {
        list:[],
        listLoading: true,
        pageNum: 1,
        pageSize: 10,
        totalCount: 0
      }
    },
    mounted() {
      this.fetchData()
    },
    methods: {
      fetchData() {
        this.listLoading = true
        let params = {employeeNo: "" , pageNum:this.pageNum,pageSize:this.pageSize}
        getList(params).then(response => {
          this.list = response.data.records
          this.totalCount = response.data.totalCount
          this.listLoading = false
        })
      },
      handleCurrentChange(pageNum){
        this.pageNum = pageNum
      }
    }
  }

</script>
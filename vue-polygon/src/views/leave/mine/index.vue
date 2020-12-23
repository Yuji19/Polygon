<template>
  <div class="app-container">
    <el-form :inline="true" :model="form" >
      <el-form-item label="流程编号">
        <el-input v-model="form.flowNo" ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
      </el-form-item>
    </el-form>
    

    <!-- 审批弹框 -->
    <el-dialog title="流程审批"  :visible.sync="dialogVisible">
      <div>
        <label>请假单编号: {{leave.id}}</label>
        <label>申请人: {{leave.employeeName}}</label>
        <label>请假类型: {{leave.type}}</label>
        <label>请假理由: {{leave.reason}}</label>
        <label>开始时间: {{leave.startDate}}</label>
        <label>结束时间: {{leave.endDate}}</label>
      </div>
      <el-table
        :data="approves"
        border
        fit
        style="width: 100%"
      >
        <el-table-column align="center" label="ID" width="95">
          <template slot-scope="scope">
            {{ scope.$index+1 }}
          </template>
        </el-table-column>
        <el-table-column label="节点名称" width="150" >
          <template slot-scope="scope">
            {{ scope.row.flowNodeName }}
          </template>
        </el-table-column>
        <el-table-column label="审批人员" width="110" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.approveName }}</span>
          </template>
        </el-table-column>
        <el-table-column label="审批意见" width="110" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.approveInfo }}</span>
          </template>
        </el-table-column>
        <el-table-column label="审批状态" width="110" align="center">
          <template slot-scope="scope">
            {{ scope.row.approveState }}
          </template>
        </el-table-column>
      </el-table>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">确定</el-button>
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
      <el-table-column label="流程编号" width="150" >
        <template slot-scope="scope">
          {{ scope.row.flowNo }}
        </template>
      </el-table-column>
      <el-table-column label="流程名称" width="110" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.flowName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请人" width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.creator }}
        </template>
      </el-table-column>
      <el-table-column label="申请时间" width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.gmtCreate }}
        </template>
      </el-table-column>
      <el-table-column label="当前节点" width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.flowNodeName }}
        </template>
      </el-table-column>
      <el-table-column label="当前审批人" width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.approveName }}
        </template>
      </el-table-column>
      <el-table-column label="状态" width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.flowState }}
        </template>
      </el-table-column>
     
      <el-table-column label="操作"  align="center">
        <template slot-scope="scope">
          <el-button type="primary" size="mini"    @click="showApproveVisible(scope.row)">查看</el-button>
        </template>
      </el-table-column>
      
    </el-table>
   
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
  import { getLeave, getMineLeaveFlowPage} from '@/api/leave'
  import { getApproveList } from '@/api/approve'

  export default {

    data(){
      return {
        list:[],
        listLoading: false,
        totalCount: 0,
        form:{
          flowNo: '',
          employeeNo: '',
          pageNum: 1,
          pageSize: 10
        },
        dialogVisible: false,
        leave: {
          id: 0,
          employeeNo: '',
          employeeName: '',
          type: '',
          reason: '',
          startDate: '',
          endDate: ''
        },
        approves: []
      }
    },
    computed: {
      ...mapGetters([
        'employee'
      ])
    },
    mounted() {
      this.fetchData()
      
    },
    methods: {
      fetchData() {
        this.listLoading = true
        this.form.employeeNo = this.employee.no
        getMineLeaveFlowPage(this.form).then(response => {
          this.list = response.data.records
          this.totalCount = response.data.totalCount
          this.listLoading = false
        })
      },
      handleCurrentChange(pageNum){
        this.form.pageNum = pageNum
        this.fetchData()
      },
      onSubmit(){
        this.fetchData()
      },
      showApproveVisible(row){
        getLeave(row.businessNo).then(resp => {
          this.leave = resp.data
        })
        getApproveList(row.flowNo).then(resp => {
          this.approves = resp.data
        })
        this.dialogVisible = true
      }
    }
  }

</script>
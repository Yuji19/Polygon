<template>
  <div class="app-container">
     <el-form :model="form" :rules="rules" ref="form">
        <el-form-item label="员工编号" prop="employeeNo">
          <el-input v-model="form.employeeNo"></el-input>
        </el-form-item>
        <el-form-item label="员工姓名" prop="employeeName" >
          <el-input  v-model="form.employeeName"></el-input>
        </el-form-item>
        <el-form-item label="请假类型" prop="type">
          <el-select v-model="form.type" style="width: 100%;" placeholder="请选择请假类型">
            <el-option label="病假" value="病假"></el-option>
            <el-option label="休假" value="休假"></el-option>
            <el-option label="年假" value="年假"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="理由" prop="reason">
            <el-input type="textarea" v-model="form.reason"></el-input>
        </el-form-item>
        <el-form-item label="" prop="date">
            <el-date-picker
              v-model="date"
              type="datetimerange"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
              :default-time="['00:00:00', '23:59:59']"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期">
            </el-date-picker>
        </el-form-item>
        
        <el-form-item label="主任审批">
          <el-select v-model="form.eNo[0]"  placeholder="请选择">
            <el-option v-for="item in directors" :label="item.name" :value="item.no"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="部长审批">
          <el-select v-model="form.eNo[1]"  placeholder="请选择">
            <el-option v-for="item in ministers" :label="item.name" :value="item.no"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit('form')">保存</el-button>
        </el-form-item>
      </el-form>
      
  </div>
</template>

<script>

  import { mapGetters } from 'vuex'
  import { getListByRoleAndDept } from '@/api/employee'
  import { addLeave } from '@/api/leave'

  export default {

    data(){
      return {
        form: {
          employeeNo: '',
          employeeName: '',
          type: '',
          reason: '',
          startDate: null,
          endDate: null,
          eNo: ["",""]
        },
        date: [],
        directors: [],
        ministers: [],
        rules: {
          employeeNo: [
            {required: true,message: '请输入员工编号', trigger: 'blur'}
          ],
          employeeName: [
            {required: true, message: '请输入员工名称', trigger: 'blur'}
          ],
          type: [
            {required: true, message: '请选择类型', trigger: 'blur'}
          ],
          reason: [
            {required: true, message: '请输入编制人', trigger: 'blur'}
          ],
          data: [
            {required: true, message: '请选择日期', trigger: 'blur'}
          ],
          eNo: [
            {required: true, message: '请选择审批人', trigger: 'blur'}
          ]
        },
        
        }
    },
    computed: {
      ...mapGetters([
          'employee'
        ])
    },
    mounted() {
      this.fetchDirectors()
      this.fetchMinisters()
    },
    methods: {
      fetchDirectors(){
        let params={ rid: 14, deptId: this.employee.departmentId }
        getListByRoleAndDept(params).then(resp => {
          this.directors = resp.data
        })
      },
      fetchMinisters(){
        let params={ rid: 15, deptId: this.employee.departmentId }
        getListByRoleAndDept(params).then(resp => {
          this.ministers = resp.data
        })
      },
      onSubmit(form){
        this.$refs[form].validate((valid) => {
          if (valid) {
            this.form.startDate = this.date[0]
            this.form.endDate = this.date[1]
            addLeave(this.form).then(resp => {
              this.$router.push({ path: '/approve'})
            })
          }
        })
      }

    }
  }

</script>
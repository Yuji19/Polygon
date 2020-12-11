<template>
  <div class="app-container">
     <el-form :model="form" :rules="rules" ref="form">
        <el-form-item label="员工编号" prop="employeeNo">
          <el-input v-model="form.employeeNo"></el-input>
        </el-form-item>
        <el-form-item label="员工姓名" prop="employeeName" >
          <el-input  v-model="form.employeeName"></el-input>
        </el-form-item>
        <el-form-item label="文档类型" prop="type">
          <el-select v-model="form.type" style="width: 100%;" placeholder="请选择文档类型">
            <el-option label="文档类型1" value="文档类型1"></el-option>
            <el-option label="文档类型2" value="文档类型2"></el-option>
            <el-option label="文档类型3" value="文档类型3"></el-option>
            <el-option label="文档类型4" value="文档类型4"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="编制人" prop="reason">
            <el-input type="textarea" v-model="form.reason"></el-input>
        </el-form-item>
        <el-form-item label="下发日期" prop="date">
            <el-date-picker
              v-model="date"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期">
            </el-date-picker>
        </el-form-item>
        
        <el-form-item label="受控状态">
          <el-select v-model="form.eNo"  placeholder="请选择状态">
            <el-option v-for="" label="受控" value="受控"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit('form')">保存</el-button>
        </el-form-item>
      </el-form>
      
  </div>
</template>

<script>

  import { addArchive } from '@/api/archive'
  import { getAllDepartment } from '@/api/department'

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
          eNo: []
        },
        date: []
        departments: [],
        rules: {
          employeeNo: [
            {required: true,message: '请输入文档编号', trigger: 'blur'}
          ],
          employeeName: [
            {required: true, message: '请输入文档名称', trigger: 'blur'}
          ],
          type: [
            {required: true, message: '请选择编制单位', trigger: 'blur'}
          ],
          reason: [
            {required: true, message: '请输入编制人', trigger: 'blur'}
          ],
          data: [
            {required: true, message: '请选择文档类型', trigger: 'blur'}
          ],
          eNo: [
            {required: true, message: '请选择下发日期', trigger: 'blur'}
          ]
        },
        
        }
    },
    mounted() {
      this.fetchDepartments()
      
    },
    methods: {
      fetchDepartments(){
        getAllDepartment().then(response => {
          this.departments = response.data
        })
      },
      handleChange(file){
        console.log(file)
        this.fileList = []
        this.form.file = file.raw
        this.fileList.push({
          name: file.name,
          url: ''
        });
        var tempName = file.name.substring(0,file.name.lastIndexOf("."))
        this.form.fileName = tempName
        
      },
      onSubmit(form){
        this.$refs[form].validate((valid) => {
          if (valid) {
            //带文件的表单数据不能直接用JSON格式发送，需要放进表单类型对象中
            let params = new FormData();
            params.append("fileNo",this.form.fileNo);
            params.append("oldFileNo", this.form.oldFileNo);
            params.append("file",this.form.file);
            params.append("fileName", this.form.fileName);
            params.append("editDept", this.form.editDept);
            params.append("editPerson", this.form.editPerson);
            params.append("fileType", this.form.fileType);
            params.append("issueDate", this.form.issueDate);
            params.append("status", this.form.status);
            params.append("note",this.form.note);
            addArchive(params).then(resp => {
              console.log("upload success")
            })
          }
        })
      }

    }
  }

</script>
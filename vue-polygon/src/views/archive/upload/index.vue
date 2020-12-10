<template>
  <div class="app-container">
     <el-form :model="form" :rules="rules" ref="form">
        <el-form-item label="文档编号" prop="fileNo">
          <el-input v-model="form.fileNo"></el-input>
        </el-form-item>
        <el-form-item label="旧版编号" >
          <el-input  v-model="form.oldFileNo"></el-input>
        </el-form-item>
        <el-form-item label="上传文件"  >
          <el-upload
          class="upload-demo"
          ref="upload"
          action=""
          :limit="1"
          :on-change = "handleChange"
          :file-list="fileList"
          :auto-upload="false">
            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="文档名称" prop="fileName">
          <el-input v-model="form.fileName"></el-input>
        </el-form-item>
        <el-form-item label="编制人" prop="editPerson">
            <el-input v-model="form.editPerson"></el-input>
        </el-form-item>
        <el-form-item label="编制单位" prop="editDept">
          <el-select v-model="form.editDept" style="width: 100%;" placeholder="请选择">
            <el-option v-for="(item, index) in departments" :label="item.name" :value="item.id" :key="index"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="文档类型" prop="fileType">
          <el-select v-model="form.fileType" style="width: 100%;" placeholder="请选择文档类型">
            <el-option label="文档类型1" value="文档类型1"></el-option>
            <el-option label="文档类型2" value="文档类型2"></el-option>
            <el-option label="文档类型3" value="文档类型3"></el-option>
            <el-option label="文档类型4" value="文档类型4"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="下发日期" prop="issueDate">
            <el-date-picker type="date"  placeholder="选择日期"  v-model="form.issueDate" value-format="yyyy-MM-dd" ></el-date-picker>
        </el-form-item>
        
        <el-form-item label="受控状态">
          <el-select v-model="form.status"  placeholder="请选择状态">
            <el-option label="受控" value="受控"></el-option>
            <el-option label="过期报废" value="过期报废"></el-option>
            <el-option label="替代报废" value="替代报废"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.note" />
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
          fileNo: '',
          oldFileNo: '',
          file: null,
          fileName: '',
          editDept: '',
          editPerson: '',
          fileType: '',
          issueDate: '',
          status: '受控',
          note:'',
        },
        fileList: [],
        changeVersion: false,
        departments: [],
        rules: {
          fileNo: [
            {required: true,message: '请输入文档编号', trigger: 'blur'}
          ],
          fileName: [
            {required: true, message: '请输入文档名称', trigger: 'blur'}
          ],
          editDept: [
            {required: true, message: '请选择编制单位', trigger: 'blur'}
          ],
          editPerson: [
            {required: true, message: '请输入编制人', trigger: 'blur'}
          ],
          fileType: [
            {required: true, message: '请选择文档类型', trigger: 'blur'}
          ],
          issueDate: [
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
<template>
  <div class="app-container">
    <el-form :inline="true" :model="form" >
      <el-form-item label="文档编号">
        <el-input v-model="form.fileNo" ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
      </el-form-item>
    </el-form>
    

    <!-- 文档修改弹框  -->
    <el-dialog title="文档修改"  :visible.sync="dialogVisible">
      <el-form :model="updateForm" :rules="rules" ref="updateForm">
        <el-form-item label="文档编号" prop="fileNo">
          <el-input v-model="updateForm.fileNo"></el-input>
        </el-form-item>
        <el-form-item label="旧版编号" >
          <el-input  v-model="updateForm.oldFileNo"></el-input>
        </el-form-item>
         <el-form-item label="">
          <el-button type="primary" @click="changeVersion = true" v-if="changeVersion == false" round>换版</el-button>
        </el-form-item>
        <div class="upload-file">
          <el-form-item label="上传文件"  v-if="changeVersion == true">
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
        </div>
        <el-form-item label="文档名称" prop="fileName">
          <el-input v-model="updateForm.fileName"></el-input>
        </el-form-item>
        <el-form-item label="编制人" prop="editPerson">
            <el-input v-model="updateForm.editPerson"></el-input>
        </el-form-item>
        <el-form-item label="编制单位" prop="editDept">
          <el-select v-model="updateForm.editDept" style="width: 100%;" placeholder="请选择">
            <el-option v-for="(item, index) in departments" :label="item.name" :value="item.id" :key="index"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="文档类型" prop="fileType">
          <el-select v-model="updateForm.fileType" style="width: 100%;" placeholder="请选择文档类型">
            <el-option label="文档类型1" value="文档类型1"></el-option>
            <el-option label="文档类型2" value="文档类型2"></el-option>
            <el-option label="文档类型3" value="文档类型3"></el-option>
            <el-option label="文档类型4" value="文档类型4"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="下发日期" prop="issueDate">
            <el-date-picker type="date"  placeholder="选择日期"  v-model="updateForm.issueDate" value-format="yyyy-MM-dd" ></el-date-picker>
        </el-form-item>
        
        <el-form-item label="受控状态">
          <el-select v-model="updateForm.status"  placeholder="请选择状态">
            <el-option label="受控" value="受控"></el-option>
            <el-option label="过期报废" value="过期报废"></el-option>
            <el-option label="替代报废" value="替代报废"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="updateForm.note" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetUpdateForm('updateForm')">取 消</el-button>
        <el-button type="primary" @click="onSubmitArchive('updateForm')">保 存</el-button>
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
      <el-table-column label="文档编号" width="150" >
        <template slot-scope="scope">
          {{ scope.row.fileNo }}
        </template>
      </el-table-column>
      <el-table-column label="文档名称" width="110" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.fileName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="文档类型" width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.fileType }}
        </template>
      </el-table-column>
      <el-table-column label="编辑单位" width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.editDept }}
        </template>
      </el-table-column>
      <el-table-column label="编辑人员" width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.editPerson }}
        </template>
      </el-table-column>
      <el-table-column label="下发日期" width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.issueDate }}
        </template>
      </el-table-column>
      <el-table-column label="状态" width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.status }}
        </template>
      </el-table-column>
      <el-table-column label="备注" width="110" align="center">
        <template slot-scope="scope">
          {{ scope.row.note }}
        </template>
      </el-table-column>
      <el-table-column label="操作"  align="center">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" v-permission="['archive_update']"    @click="showArchiveVisible(scope.row)">修改</el-button>
          <el-button type="danger" size="mini" v-permission="['archive_delete']" @click="handleDelete(scope.row.id)">删除</el-button>
          <el-button type="success" size="mini"  @click="handleDownload(scope.row)">下载</el-button>
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

  import { getList, download , updateInfo , addArchive , deleteArchive} from '@/api/archive'
  import { getAllDepartment } from '@/api/department'

  export default {

    data(){
      return {
        list:[],
        listLoading: false,
        totalCount: 0,
        form:{
          fileNo: "",
          pageNum: 1,
          pageSize: 10
        },
        dialogVisible: false,
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
        updateForm: {
          id: 0,
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
          row: null
        }
    },
    mounted() {
      this.fetchData()
      this.fetchDepartments()
      
    },
    methods: {
      fetchData() {
        this.listLoading = true
        getList(this.form).then(response => {
          this.list = response.data.records
          this.totalCount = response.data.totalCount
          this.listLoading = false
        })
      },
      fetchDepartments(){
        getAllDepartment().then(response => {
          this.departments = response.data
        })
      },
      handleCurrentChange(pageNum){
        this.form.pageNum = pageNum
        this.fetchData()
      },
      handleChange(file){
        console.log(file)
        this.fileList = []
        this.updateForm.file = file.raw
        this.fileList.push({
          name: file.name,
          url: ''
        });
        var tempName = file.name.substring(0,file.name.lastIndexOf("."))
        this.updateForm.fileName = tempName
        
      },
      onSubmit(){
        this.fetchData()
      },
      showArchiveVisible(row){
        //重新指向一个新的引用,避免和row共享同一内存区域
        this.updateForm = Object.assign({}, row)
        this.dialogVisible = true
      },
      onSubmitArchive(updateForm){
        this.$refs[updateForm].validate((valid) => {
          if (valid) {
            console.log(this.updateForm.file+" "+this.changeVersion)
            if (this.updateForm.file === undefined && this.changeVersion === false) {
             
                let params = Object.assign({},this.updateForm)
                //删除file属性
                delete params.file
              updateInfo(params).then(resp => {
                this.dialogVisible = false
                this.changeVersion = false
                this.fetchData()
              })
            }

            if (this.updateForm.file !== undefined && this.updateForm.file.size > 0) {
              
              //带文件的表单数据不能直接用JSON格式发送，需要放进表单类型对象中
              let params = new FormData()
              params.append("fileNo",this.updateForm.fileNo)
              params.append("oldFileNo", this.updateForm.oldFileNo)
              params.append("file",this.updateForm.file)
              params.append("fileName", this.updateForm.fileName)
              params.append("editDept", this.updateForm.editDept)
              params.append("editPerson", this.updateForm.editPerson)
              params.append("fileType", this.updateForm.fileType)
              params.append("issueDate", this.updateForm.issueDate)
              params.append("status", this.updateForm.status)
              params.append("note",this.updateForm.note)

              addArchive(params).then(resp => {
                this.dialogVisible = false
                this.updateForm.file = null
                this.changeVersion = false
                this.fetchData()
              })
            }
            
          }
        })
      },
      resetUpdateForm(updateForm){
        this.dialogVisible = false
        this.$refs[updateForm].resetFields()
      },
      handleDownload(row){
        
        download(row.fileNo).then(resp=> {
          
            //由服务端返回的文件流构成直接下载的链接
            let url = window.URL.createObjectURL(new Blob([resp.data]));
            let link = document.createElement('a');
            link.style.display = 'none';
            link.href = url;
            link.setAttribute('download',row.fileName);
            document.body.appendChild(link);
            link.click();
            window.URL.revokeObjectURL(link.href);
        })
      },
      handleDelete(id){
        let param = []
        param.push(id)
        deleteArchive(param).then(resp => {
          this.fetchData()
        })
      }
    }
  }

</script>
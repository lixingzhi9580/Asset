<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="page" />
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>我的弹框</title>

<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<script src="${ctx}/js/jquery-1.12.2.min.js"></script>
<script src="${ctx}/js/bootstrap.min.js"></script>
</head>
<body>
    <div id="test">		        

		<div id="top">			
				<el-button type="text" @click="search" style="color:white">查询</el-button>	
				<el-button type="text" @click="handleadd" style="color:white">添加</el-button>	
			</span>						
		</div>	
				
		<br/>

        <div style="margin-top:15px">	

		  <el-table
		    ref="testTable"		  
		    :data="tableData"
		    style="width:100%"
		    border
		    >
		    <el-table-column
		      prop="job_NAME"
		      label="任务名称"
		      sortable
		      show-overflow-tooltip>
		    </el-table-column>
		    
		    <el-table-column
		      prop="job_GROUP"
		      label="任务所在组"
		      sortable>
		    </el-table-column>
		    
   		    <el-table-column
		      prop="job_CLASS_NAME"
		      label="任务类名"
		      sortable>
		    </el-table-column>
		    
   		    <el-table-column
		      prop="trigger_NAME"
		      label="触发器名称"
		      sortable>
		    </el-table-column>
		    
		    <el-table-column
		      prop="trigger_GROUP"
		      label="触发器所在组"
		      sortable>
		    </el-table-column>
		    
		    <el-table-column
		      prop="cron_EXPRESSION"
		      label="表达式"
		      sortable>
		    </el-table-column>
		    
		    <el-table-column
		      prop="time_ZONE_ID"
		      label="时区"
		      sortable>
		    </el-table-column>
		    
	        <el-table-column label="操作" width="300">
		      <template scope="scope">
		      	<el-button
		          size="small"
		          type="warning"
		          @click="handlePause(scope.$index, scope.row)">暂停</el-button>
		          
		        <el-button
		          size="small"
		          type="info"
		          @click="handleResume(scope.$index, scope.row)">恢复</el-button>
		          
		        <el-button
		          size="small"
		          type="danger"
		          @click="handleDelete(scope.$index, scope.row)">删除</el-button>
		          
		        <el-button
		          size="small"
		          type="success"
		          @click="handleUpdate(scope.$index, scope.row)">修改</el-button>
		      </template>
		    </el-table-column>
		  </el-table>
		  
		  <div align="center">
			  <el-pagination
			      @size-change="handleSizeChange"
			      @current-change="handleCurrentChange"
			      :current-page="currentPage"
			      :page-sizes="[10, 20, 30, 40]"
			      :page-size="pagesize"
			      layout="total, sizes, prev, pager, next, jumper"
			      :total="totalCount">
			  </el-pagination>
		  </div>
		</div> 
		
		<el-dialog title="添加任务" :visible.sync="dialogFormVisible">
		  <el-form :model="form">
		    <el-form-item label="任务名称" label-width="120px" style="width:35%">
		      <el-input v-model="form.jobName" auto-complete="off"></el-input>
		    </el-form-item>	    
		    <el-form-item label="任务分组" label-width="120px" style="width:35%">
		      <el-input v-model="form.jobGroup" auto-complete="off"></el-input>
		    </el-form-item>
		    <el-form-item label="表达式" label-width="120px" style="width:35%">
		      <el-input v-model="form.cronExpression" auto-complete="off"></el-input>
		    </el-form-item>
		  </el-form>
		  <div slot="footer" class="dialog-footer">
		    <el-button @click="dialogFormVisible = false">取 消</el-button>
		    <el-button type="primary" @click="add">确 定</el-button>
		  </div>
		</el-dialog>
		
		<el-dialog title="修改任务" :visible.sync="updateFormVisible">
		  <el-form :model="updateform">
		    <el-form-item label="表达式" label-width="120px" style="width:35%">
		      <el-input v-model="updateform.cronExpression" auto-complete="off"></el-input>
		    </el-form-item>
		  </el-form>
		  <div slot="footer" class="dialog-footer">
		    <el-button @click="updateFormVisible = false">取 消</el-button>
		    <el-button type="primary" @click="update">确 定</el-button>
		  </div>
		</el-dialog>
		
    </div>
	
    <footer align="center">
        <p>&copy; Quartz 任务管理</p>
    </footer>

	<script>
	var vue = new Vue({			
			el:"#test",
		    data: {		  
		    	//表格当前页数据
		    	tableData: [],
		        
		        //请求的URL
		        url:'quartz/job/queryjob',
		        
		        //默认每页数据量
		        pagesize: 10,		        
		        
		        //当前页码
		        currentPage: 1,
		        
		        //查询的页码
		        start: 1,
		        
		        //默认数据总数
		        totalCount: 1000,
		        
		        //添加对话框默认可见性
		        dialogFormVisible: false,
		        
		        //修改对话框默认可见性
		        updateFormVisible: false,
		        
		        //提交的表单
		        form: {
		        	jobName: '',
		        	jobGroup: '',
		        	cronExpression: '',
		          },
		          
		        updateform: {
		        	jobName: '',
		        	jobGroup: '',
		        	cronExpression: '',
		        },
		    },

		    methods: {
		    	
		        //从服务器读取数据
				loadData: function(pageNum, pageSize){					
					this.$http.get('quartz/job/queryjob?' + 'pageNum=' +  pageNum + '&pageSize=' + pageSize).then(function(res){
						console.log(res)
                		this.tableData = res.body.JobAndTrigger.list;
                		this.totalCount = res.body.number;
                	},function(){
                  		console.log('failed');
                	});					
				},			    		        
				      
		        //单行删除
			    handleDelete: function(index, row) {
					this.$http.post('quartz/job/deletejob',{"jobClassName":row.job_NAME,"jobGroupName":row.job_GROUP},{emulateJSON: true}).then(function(res){
						this.loadData( this.currentPage, this.pagesize);
		            },function(){
		                console.log('failed');
		            });
		        },
		        
		        //暂停任务
		        handlePause: function(index, row){
		        	this.$http.post('quartz/job/pausejob',{"jobClassName":row.job_NAME,"jobGroupName":row.job_GROUP},{emulateJSON: true}).then(function(res){
						this.loadData( this.currentPage, this.pagesize);
		            },function(){
		                console.log('failed');
		            });
		        },
		        
		        //恢复任务
		        handleResume: function(index, row){
		        	this.$http.post('quartz/job/resumejob',{"jobClassName":row.job_NAME,"jobGroupName":row.job_GROUP},{emulateJSON: true}).then(function(res){
						this.loadData( this.currentPage, this.pagesize);
		            },function(){
		                console.log('failed');
		            });
		        },
		        
		        //搜索
		        search: function(){
		        	this.loadData(this.currentPage, this.pagesize);
		        },
		        
		        //弹出对话框
		        handleadd: function(){		                
		            this.dialogFormVisible = true;	              
		        },
		        
		        //添加
		        add: function(){
		        	this.$http.post('quartz/job/addjob',{"jobClassName":this.form.jobName,"jobGroupName":this.form.jobGroup,"cronExpression":this.form.cronExpression},{emulateJSON: true}).then(function(res){
        				this.loadData(this.currentPage, this.pagesize);
        				this.dialogFormVisible = false;
                    },function(){
                        console.log('failed');
                    });
		        },
		        
		        //更新
		        handleUpdate: function(index, row){
		        	console.log(row)
		        	this.updateFormVisible = true;
		        	this.updateform.jobName = row.job_CLASS_NAME;
		        	this.updateform.jobGroup = row.job_GROUP;
		        },
		        
		        //更新任务
		        update: function(){
		        	this.$http.post
		        	('quartz/job/reschedulejob',
		        			{"jobClassName":this.updateform.jobName,
		        			 "jobGroupName":this.updateform.jobGroup,
		        			 "cronExpression":this.updateform.cronExpression
		        			 },{emulateJSON: true}
		        	).then(function(res){
		        		this.loadData(this.currentPage, this.pagesize);
        				this.updateFormVisible = false;
		        	},function(){
                        console.log('failed');
                    });
		    
		        },
		      
		        //每页显示数据量变更
		        handleSizeChange: function(val) {
		            this.pagesize = val;
		            this.loadData(this.currentPage, this.pagesize);
		        },
		        
		        //页码变更
		        handleCurrentChange: function(val) {
		            this.currentPage = val;
		            this.loadData(this.currentPage, this.pagesize);
		        },	      
		        		        
		    },	    
		    
		    
		  });
	
		  //载入数据
    	  vue.loadData(vue.currentPage, vue.pagesize);
	</script>  
	
</body>
</html>
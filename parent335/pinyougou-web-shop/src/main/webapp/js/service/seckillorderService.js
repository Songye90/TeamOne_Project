//服务层
app.service('seckillorderService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../seckillorder/findAll.do');
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../seckillorder/findPage.do?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../seckillorder/findOne.do?id='+id);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../seckillorder/search.do?page='+page+"&rows="+rows, searchEntity);
	}    	
});

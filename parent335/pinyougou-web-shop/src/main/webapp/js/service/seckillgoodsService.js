//服务层
app.service('seckillgoodsService',function($http){
	    	

	//分页 
	this.findPage=function(page,rows){
		return $http.get('../seckillgoods/findPage.do?page='+page+'&rows='+rows);
	}

	//查询实体
	this.findOne=function(id){
		return $http.get('../seckillgoods/findOne.do?id='+id);
	}
	//增加
	this.add=function(entity){
		return  $http.post('../seckillgoods/add.do',entity );
	}

	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../seckillgoods/search.do?page='+page+"&rows="+rows, searchEntity);
	}    	
});

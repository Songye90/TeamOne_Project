//服务层
app.service('userService',function($http){

    //查询实体
    this.deleteOne=function(id){
        return $http.get('../user/deleteOne.do?id='+id);
    }


    //删除
    this.deleteMany=function(ids){
        return $http.get('../user/deleteMany.do?ids='+ids);
    }
    //搜索
    this.search=function(page,rows,searchEntity){
        return $http.post('../user/search.do?page='+page+"&rows="+rows, searchEntity);
    }


});

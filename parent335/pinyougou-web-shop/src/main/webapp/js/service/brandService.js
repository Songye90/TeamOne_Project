// 定义服务层:
app.service("brandService",function($http){
    this.add = function(entity){
        return $http.post("../brand/saveBrand.do",entity);
    }

    this.findPage = function(page,rows){
        return $http.get("../brand/findPage.do?pageNo="+page+"&pageSize="+rows);
    }

    this.search = function(page,rows,searchEntity){
        return $http.post("../brand/search.do?pageNo="+page+"&pageSize="+rows,searchEntity);
    }

});
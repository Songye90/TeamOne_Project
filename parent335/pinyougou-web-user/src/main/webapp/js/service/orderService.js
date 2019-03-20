app.service('orderService',function($http){
    //分页
    this.findAll=function(page,rows){
        return $http.get('../user/findAll.do?page='+page+'&rows='+rows);
    }

});
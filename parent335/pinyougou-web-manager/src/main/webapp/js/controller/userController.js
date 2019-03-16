//控制层
app.controller('userController' ,function($scope,$controller   ,userService){

    $controller('baseController',{$scope:$scope});//继承

    $scope.deleteOne=function (id) {
        userService.deleteOne(id).success(
            function (response) {
                if(response.flag){
                    $scope.reloadList();//刷新列表
                }else {
                    alert(response.message);
                }
            }
        )
    }
    $scope.deleteMany=function(){
        //获取选中的复选框
        userService.deleteMany( $scope.selectIds ).success(
            function(response){
                if (response.flag) {
                    $scope.reloadList();//刷新列表
                    $scope.selectIds = [];
                }
    }
        );
    }
    $scope.searchEntity={};//定义搜索对象

    //搜索
    $scope.search=function(page,rows){
        userService.search(page,rows,$scope.searchEntity).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }

});

//控制层
app.controller('userController', function ($scope, $controller, userService) {

    $controller('baseController', {$scope: $scope});//继承

    $scope.deleteOne = function (id) {
        userService.deleteOne(id).success(
            function (response) {
                if (response.flag) {
                    $scope.reloadList();//刷新列表
                } else {
                    alert(response.message);
                }
            }
        )
    }
    $scope.deleteMany = function () {
        //获取选中的复选框
        userService.deleteMany($scope.selectIds).success(
            function (response) {
                if (response.flag) {
                    $scope.reloadList();//刷新列表
                    $scope.selectIds = [];
                }
            }
        );
    }
    $scope.searchEntity = {};//定义搜索对象

    //搜索
    $scope.search = function (page, rows) {
        userService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }
    $scope.hot = function (message) {
        alert("用户上次登陆时间：" + message)
    }
    $scope.Excel = {};//定义导出表格相关信息对象
    $scope.excel = function exportExcel() {
        if (confirm("确认把该搜索结果导出Excel表格?")) {

            var startTime = $scope.Excel.startTime;
            var endTime = $scope.Excel.endTime;
            var consumesType = $scope.Excel.consumesType;
            var conPaymentStatus = $scope.Excel.conPaymentStatus;

            userService.excel(startTime,endTime,consumesType,conPaymentStatus).success(function (response) {
                if (response.flag) {
                    alert(response.message);
                } else {
                    alert(response.message);
                }
            })

        }
    }


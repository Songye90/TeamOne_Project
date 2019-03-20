app.controller('orderController',function($scope,$controller,orderService){

        $controller('baseController',{$scope:$scope});

        //分页
        $scope.search=function (page, rows) {
            orderService.findAll(page,rows).success(
                function (response) {
                    $scope.list=response.rows;
                    $scope.paginationConf.totalItems=response.total;
                }
            )
        }


    });

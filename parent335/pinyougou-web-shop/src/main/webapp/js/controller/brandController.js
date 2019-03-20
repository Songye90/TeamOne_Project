// 定义控制器:
app.controller("brandController",function($scope,$controller,brandService){
// AngularJS中的继承:伪继承
    $controller('baseController',{$scope:$scope});
	// 分页查询
	$scope.findPage = function(page,rows){
		// 向后台发送请求获取数据:
		brandService.findPage(page,rows).success(function(response){
			$scope.paginationConf.totalItems = response.total;
			$scope.list = response.rows;
		});
	}

	// 保存品牌的方法:
	$scope.save = function(){
		// 区分是保存还是修改
		var object;
            // 保存
		object = brandService.add($scope.entity);
		object.success(function(response){
			// {flag:true,message:xxx}
			// 判断保存是否成功:
			if(response.flag){
				// 保存成功
				alert(response.message);
				$scope.reloadList();
			}else{
				// 保存失败
				alert(response.message);
			}
		});
	}

    $scope.searchEntity={};

    // 假设定义一个查询的实体：searchEntity
    $scope.search = function(page,rows){
        // 向后台发送请求获取数据:
        brandService.search(page,rows,$scope.searchEntity).success(function(response){
            $scope.paginationConf.totalItems = response.total;
            $scope.list = response.rows;
        });
    }

});

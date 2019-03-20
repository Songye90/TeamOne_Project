 //控制层 
app.controller('itemCatController' ,function($scope,$controller   ,itemCatService){	
	
	$controller('baseController',{$scope:$scope});//继承
	

	
	//分页
	$scope.findPage=function(page,rows){			
		itemCatService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}

	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象
		serviceObject=itemCatService.add( $scope.entity  );//增加
		serviceObject.success(
			function(response){
				if(response.flag){
					//重新查询 
		        	$scope.reloadList();//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	}

	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		itemCatService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	// 根据父ID查询分类
	$scope.findByParentId =function(parentId){
		itemCatService.findByParentId(parentId).success(function(response){
			$scope.list=response;
		});
	}
	
	// 定义一个变量记录当前是第几级分类
	$scope.grade = 1;
	
	$scope.setGrade = function(value){
		$scope.grade = value;
	}
	
	$scope.selectList = function(p_entity){
		
		if($scope.grade == 1){
			$scope.entity_1 = null;
			$scope.entity_2 = null;
		}
		if($scope.grade == 2){
			$scope.entity_1 = p_entity;
			$scope.entity_2 = null;
		}
		if($scope.grade == 3){
			$scope.entity_2 = p_entity;
		}
		
		$scope.findByParentId(p_entity.id);
	}
	
	
	
	
	
	
	
	
	
	
    
});	

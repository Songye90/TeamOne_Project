 //控制层 
app.controller('seckillgoodsController' ,function($scope,$controller,$location,typeTemplateService ,itemCatService,uploadService ,seckillgoodsService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
        seckillgoodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){
        seckillgoodsService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(){	
		var id = $location.search()['id'];
		if(null == id){
			return;
		}
        seckillgoodsService.findOne(id).success(
			function(response){
				$scope.entity= response;	
				
				// 调用处理富文本编辑器：
				editor.html($scope.entity.goodsDesc.introduction);
				
				// 处理图片列表，因为图片信息保存的是JSON的字符串，让前台识别为JSON格式对象
				$scope.entity.goodsDesc.itemImages = JSON.parse( $scope.entity.goodsDesc.itemImages );
				
				// 处理扩展属性:
				$scope.entity.goodsDesc.customAttributeItems = JSON.parse( $scope.entity.goodsDesc.customAttributeItems );
			
				// 处理规格
				$scope.entity.goodsDesc.specificationItems = JSON.parse($scope.entity.goodsDesc.specificationItems);
			
				// 遍历SKU的集合:
				for(var i=0;i<$scope.entity.itemList.length;i++){
					$scope.entity.itemList[i].spec = JSON.parse( $scope.entity.itemList[i].spec );
				}
			}
		);				
	}

	//保存 
	$scope.saveseckill=function(){
		// 再添加之前，获得富文本编辑器中的内容。
		$scope.entity.goodsDesc.introduction=editor.html();
		var serviceObject;//服务层对象
		serviceObject=seckillgoodsService.add( $scope.entity  );//增加
		serviceObject.success(
			function(response){
				if(response.flag){
					//重新查询 
		        	alert(response.message);
		        	location.href="goods.html";
				}else{
					alert(response.message);
				}
			}		
		);				
	}
    // 显示状态
    $scope.status = ["未审核","审核通过","审核未通过","关闭"];

	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){
        seckillgoodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}

});	

//服务层
app.service('userService', function ($http) {

    //删除单个
    this.deleteOne = function (id) {
        return $http.get('../user/deleteOne.do?id=' + id);
    }


    //删除多个
    this.deleteMany = function (ids) {
        return $http.get('../user/deleteMany.do?ids=' + ids);
    }
    //搜索
    this.search = function (page, rows, searchEntity) {
        return $http.post('../user/search.do?page=' + page + "&rows=" + rows, searchEntity);
    }

    this.excel = function (startTime,endTime,consumesType,conPaymentStatus) {
        return $http.get("../user/exportExcel.do?startTime=" + startTime
            + "&endTime=" + endTime + "&consumesType="
            + consumesType + "&conPaymentStatus=" + conPaymentStatus
        );
    }
});

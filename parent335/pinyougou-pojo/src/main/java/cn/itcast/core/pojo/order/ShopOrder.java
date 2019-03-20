package cn.itcast.core.pojo.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ShopOrder implements Serializable {

    private BigDecimal price;
    private Integer num;
    private BigDecimal totalFee;
    private Long goodsId;
    private Date createTime;
    private String sourceType;
    private String status;
    private String dates;
    private Date firstday;
    private Date lastday;

    public ShopOrder() {
    }

    public ShopOrder(BigDecimal price, Integer num, BigDecimal totalFee, Long goodsId, Date createTime, String sourceType, String status, String dates, Date firstday, Date lastday) {
        this.price = price;
        this.num = num;
        this.totalFee = totalFee;
        this.goodsId = goodsId;
        this.createTime = createTime;
        this.sourceType = sourceType;
        this.status = status;
        this.dates = dates;
        this.firstday = firstday;
        this.lastday = lastday;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public Date getFirstday() {
        return firstday;
    }

    public void setFirstday(Date firstday) {
        this.firstday = firstday;
    }

    public Date getLastday() {
        return lastday;
    }

    public void setLastday(Date lastday) {
        this.lastday = lastday;
    }
}

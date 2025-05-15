package com.ceir.CeirCode.filtermodel;

import com.ceir.CeirCode.model.app.AllRequest;

public class LinkMgmtFilter extends AllRequest {

    public String linkId;
    public String startDate;
    public String endDate;
    public String linkStatus;
    public String order,orderColumnName;
    private String searchString;

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getLinkStatus() {
        return linkStatus;
    }

    public void setLinkStatus(String linkStatus) {
        this.linkStatus = linkStatus;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrderColumnName() {
        return orderColumnName;
    }

    public void setOrderColumnName(String orderColumnName) {
        this.orderColumnName = orderColumnName;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LinkMgmtFilter{");
        sb.append("linkId='").append(linkId).append('\'');
        sb.append(", startDate='").append(startDate).append('\'');
        sb.append(", endDate='").append(endDate).append('\'');
        sb.append(", linkStatus='").append(linkStatus).append('\'');
        sb.append(", order='").append(order).append('\'');
        sb.append(", orderColumnName='").append(orderColumnName).append('\'');
        sb.append(", searchString='").append(searchString).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

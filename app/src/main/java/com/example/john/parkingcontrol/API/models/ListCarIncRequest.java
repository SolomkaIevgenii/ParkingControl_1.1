package com.example.john.parkingcontrol.API.models;

public class ListCarIncRequest {

    private String DateFrom;
    private String DateTo;
    private String AuthorLogin;
    private String CarNumber;
    private String StatusId;
    private String Page;

    public String getDateFrom() {
        return DateFrom;
    }

    public void setDateFrom(String dateFrom) {
        DateFrom = dateFrom;
    }

    public String getDateTo() {
        return DateTo;
    }

    public void setDateTo(String dateTo) {
        DateTo = dateTo;
    }

    public String getAuthorLogin() {
        return AuthorLogin;
    }

    public void setAuthorLogin(String authorLogin) {
        AuthorLogin = authorLogin;
    }

    public String getCarNumber() {
        return CarNumber;
    }

    public void setCarNumber(String carNumber) {
        CarNumber = carNumber;
    }

    public String getStatusId() {
        return StatusId;
    }

    public void setStatusId(String statusId) {
        StatusId = statusId;
    }

    public String getPage() {
        return Page;
    }

    public void setPage(String page) {
        Page = page;
    }
}

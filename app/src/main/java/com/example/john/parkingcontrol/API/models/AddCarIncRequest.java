package com.example.john.parkingcontrol.API.models;

public class AddCarIncRequest {

    private String CarNumber;
    private String CarOwner;
    private String Id;
    private String CreateDate;
    private String FullDescription;
    private String ShortDescription;
    private String IncidentType;
    private String IncidentTypeId;
    private String AuthorLogin;
    private String LawEnactment;
    private String LawEnactmentId;
    private String PenaltyAmount;
    private String PenaltyPaymentDate;
    private String IncidentStatus;
    private String IncidentStatusId;
    private String Location;
    private String LocationId;

    public String getCarNumber() {
        return CarNumber;
    }

    public void setCarNumber(String carNumber) {
        CarNumber = carNumber;
    }

    public String getCarOwner() {
        return CarOwner;
    }

    public void setCarOwner(String carOwner) {
        CarOwner = carOwner;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getFullDescription() {
        return FullDescription;
    }

    public void setFullDescription(String fullDescription) {
        FullDescription = fullDescription;
    }

    public String getShortDescription() {
        return ShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        ShortDescription = shortDescription;
    }

    public String getIncidentType() {
        return IncidentType;
    }

    public void setIncidentType(String incidentType) {
        IncidentType = incidentType;
    }

    public String getIncidentTypeId() {
        return IncidentTypeId;
    }

    public void setIncidentTypeId(String incidentTypeId) {
        IncidentTypeId = incidentTypeId;
    }

    public String getAuthorLogin() {
        return AuthorLogin;
    }

    public void setAuthorLogin(String authorLogin) {
        AuthorLogin = authorLogin;
    }

    public String getLawEnactment() {
        return LawEnactment;
    }

    public void setLawEnactment(String lawEnactment) {
        LawEnactment = lawEnactment;
    }

    public String getLawEnactmentId() {
        return LawEnactmentId;
    }

    public void setLawEnactmentId(String lawEnactmentId) {
        LawEnactmentId = lawEnactmentId;
    }

    public String getPenaltyAmount() {
        return PenaltyAmount;
    }

    public void setPenaltyAmount(String penaltyAmount) {
        PenaltyAmount = penaltyAmount;
    }

    public String getPenaltyPaymentDate() {
        return PenaltyPaymentDate;
    }

    public void setPenaltyPaymentDate(String penaltyPaymentDate) {
        PenaltyPaymentDate = penaltyPaymentDate;
    }

    public String getIncidentStatus() {
        return IncidentStatus;
    }

    public void setIncidentStatus(String incidentStatus) {
        IncidentStatus = incidentStatus;
    }

    public String getIncidentStatusId() {
        return IncidentStatusId;
    }

    public void setIncidentStatusId(String incidentStatusId) {
        IncidentStatusId = incidentStatusId;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getLocationId() {
        return LocationId;
    }

    public void setLocationId(String locationId) {
        LocationId = locationId;
    }
}

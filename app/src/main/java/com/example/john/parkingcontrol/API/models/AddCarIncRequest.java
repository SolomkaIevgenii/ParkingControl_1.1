package com.example.john.parkingcontrol.API.models;

public class AddCarIncRequest {

    private String carNumber;
    private String carOwner;
    private int id;
    private String author;
    private int authorId;
    private String createDate;
    private String series;
    private String number;
    private String fullDescription;
    private String shortDescription;
    private String incidentType;
    private int incidentTypeId;
    private String incidentAddress;
    private String lawEnactment;
    private int lawEnactmentId;
    private double penaltyAmount;
    private String PenaltyPaymentDate;
    private String IncidentStatus;
    private int incidentStatusId;
    private String location;
    private int locationId;
    private boolean forwardedToCustomsService;
    private boolean forwardedToExecutiveService;
    private String documentType;
    private int documentTypeId;


    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarOwner() {
        return carOwner;
    }

    public void setCarOwner(String carOwner) {
        this.carOwner = carOwner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getIncidentType() {
        return incidentType;
    }

    public void setIncidentType(String incidentType) {
        this.incidentType = incidentType;
    }

    public int getIncidentTypeId() {
        return incidentTypeId;
    }

    public void setIncidentTypeId(int incidentTypeId) {
        this.incidentTypeId = incidentTypeId;
    }

    public String getIncidentAddress() {
        return incidentAddress;
    }

    public void setIncidentAddress(String incidentAddress) {
        this.incidentAddress = incidentAddress;
    }

    public String getLawEnactment() {
        return lawEnactment;
    }

    public void setLawEnactment(String lawEnactment) {
        this.lawEnactment = lawEnactment;
    }

    public int getLawEnactmentId() {
        return lawEnactmentId;
    }

    public void setLawEnactmentId(int lawEnactmentId) {
        this.lawEnactmentId = lawEnactmentId;
    }

    public double getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(double penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
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

    public int getIncidentStatusId() {
        return incidentStatusId;
    }

    public void setIncidentStatusId(int incidentStatusId) {
        this.incidentStatusId = incidentStatusId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public boolean getForwardedToCustomsService() {
        return forwardedToCustomsService;
    }

    public void setForwardedToCustomsService(boolean forwardedToCustomsService) {
        this.forwardedToCustomsService = forwardedToCustomsService;
    }

    public boolean getForwardedToExecutiveService() {
        return forwardedToExecutiveService;
    }

    public void setForwardedToExecutiveService(boolean forwardedToExecutiveService) {
        this.forwardedToExecutiveService = forwardedToExecutiveService;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public int getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(int documentTypeId) {
        this.documentTypeId = documentTypeId;
    }
}

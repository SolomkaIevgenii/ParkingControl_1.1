package com.example.john.parkingcontrol.API.models;

public class AddCarIncRequest {

    String guid;
    String carNumber;
    String carDriverName;
    String description;
    String carDriverContacts;
    Double gpsLong;
    Double gpsLat;
    String incidentAddress;
    int lawEnactmentId;
    int documentTypeId;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarDriverName() {
        return carDriverName;
    }

    public void setCarDriverName(String carDriverName) {
        this.carDriverName = carDriverName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCarDriverContacts() {
        return carDriverContacts;
    }

    public void setCarDriverContacts(String carDriverContacts) {
        this.carDriverContacts = carDriverContacts;
    }

    public Double getGpsLong() {
        return gpsLong;
    }

    public void setGpsLong(Double gpsLong) {
        this.gpsLong = gpsLong;
    }

    public Double getGpsLat() {
        return gpsLat;
    }

    public void setGpsLat(Double gpsLat) {
        this.gpsLat = gpsLat;
    }

    public String getIncidentAddress() {
        return incidentAddress;
    }

    public void setIncidentAddress(String incidentAddress) {
        this.incidentAddress = incidentAddress;
    }

    public int getLawEnactmentId() {
        return lawEnactmentId;
    }

    public void setLawEnactmentId(int lawEnactmentId) {
        this.lawEnactmentId = lawEnactmentId;
    }

    public int getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(int documentTypeId) {
        this.documentTypeId = documentTypeId;
    }
}

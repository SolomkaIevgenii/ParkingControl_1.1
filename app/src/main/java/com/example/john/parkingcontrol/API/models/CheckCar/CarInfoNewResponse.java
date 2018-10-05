package com.example.john.parkingcontrol.API.models.CheckCar;

public class CarInfoNewResponse {

    public com.example.john.parkingcontrol.API.models.CheckCar.CarInfo getCarInfo() {
        return CarInfo;
    }

    public  CarInfo CarInfo;


    public com.example.john.parkingcontrol.API.models.CheckCar.ParkingInfo getParkingInfo() {
        return ParkingInfo;
    }

    private ParkingInfo ParkingInfo;
}

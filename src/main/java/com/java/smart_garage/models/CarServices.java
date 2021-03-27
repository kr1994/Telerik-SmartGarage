package com.java.smart_garage.models;

import javax.persistence.*;

@Entity
@Table(name = "car_services")
public class CarServices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int carServicesId;

    @OneToOne
    @Column(name = "car_id")
    private Cars car;

    @OneToOne
    @Column(name = "service_id")
    private Services service;

    public CarServices() {
    }

    public CarServices(int carServicesId, Cars car, Services service) {
        this.carServicesId = carServicesId;
        this.car = car;
        this.service = service;
    }


    public void setCarServicesId(int carServicesId) {
        this.carServicesId = carServicesId;
    }

    public void setCar(Cars car) {
        this.car = car;
    }

    public void setService(Services service) {
        this.service = service;
    }

    public int getCarServicesId() {
        return carServicesId;
    }

    public Cars getCar() {
        return car;
    }

    public Services getService() {
        return service;
    }

}

package com.java.smart_garage.models;

import javax.persistence.*;

@Entity
@Table(name = "car_services")
public class CarService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int carServicesId;

    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private WorkService service;

    public CarService() {
    }

    public CarService(int carServicesId, Car car, WorkService service) {
        this.carServicesId = carServicesId;
        this.car = car;
        this.service = service;
    }


    public void setCarServicesId(int carServicesId) {
        this.carServicesId = carServicesId;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setService(WorkService service) {
        this.service = service;
    }

    public int getCarServicesId() {
        return carServicesId;
    }

    public Car getCar() {
        return car;
    }

    public WorkService getService() {
        return service;
    }

}

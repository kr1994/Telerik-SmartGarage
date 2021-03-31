package com.java.smart_garage.models;

import javax.persistence.*;

@Entity
@Table(name = "services")
public class WorkService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private int serviceId;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "service_price")
    private double servicePrice;

    public WorkService() {
    }

    public WorkService(int serviceId, String serviceName, double servicePrice) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
    }


    public void setWorkServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public void setWorkServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setWorkServicePrice(double servicePrice) {
        this.servicePrice = servicePrice;
    }

    public int getWorkServiceId() {
        return serviceId;
    }

    public String getWorkServiceName() {
        return serviceName;
    }

    public double getWorkServicePrice() {
        return servicePrice;
    }


}

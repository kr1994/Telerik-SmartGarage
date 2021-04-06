package com.java.smart_garage.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "services")
public class WorkService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private int workServiceId;

    @Column(name = "service_name")
    private String workServiceName;

    @Column(name = "service_price")
    private double workServicePrice;

    //@Column(name = "service_price")
    //private Date dateFinished;

    public WorkService() {
    }

    public WorkService(int workServiceId, String workServiceName, double workServicePrice) {
        this.workServiceId = workServiceId;
        this.workServiceName = workServiceName;
        this.workServicePrice = workServicePrice;
    }


    public void setWorkServiceId(int workServiceId) {
        this.workServiceId = workServiceId;
    }

    public void setWorkServiceName(String workServiceName) {
        this.workServiceName = workServiceName;
    }

    public void setWorkServicePrice(double workServicePrice) {
        this.workServicePrice = workServicePrice;
    }

    public int getWorkServiceId() {
        return workServiceId;
    }

    public String getWorkServiceName() {
        return workServiceName;
    }

    public double getWorkServicePrice() {
        return workServicePrice;
    }


}

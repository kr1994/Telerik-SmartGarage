package com.java.smart_garage.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

import static com.java.smart_garage.models.ModelsConstants.ModelsConstants.ACTIVE;
import static com.java.smart_garage.models.ModelsConstants.ModelsConstants.EMPLOYEE;

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


    @ManyToOne
    @JoinColumn(name = "status_id")
    private WorkServiceStatus status;

    public WorkService() {
    }

    public WorkService(int workServiceId, String workServiceName, double workServicePrice, WorkServiceStatus status) {
        this.workServiceId = workServiceId;
        this.workServiceName = workServiceName;
        this.workServicePrice = workServicePrice;
        this.status = status;
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


    public void setStatus(WorkServiceStatus status) {
        this.status = status;
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

    public WorkServiceStatus getStatus() {
        return status;
    }

    @JsonIgnore
    public boolean isActive() {
        return getStatus().getStatus().equals(ACTIVE);
    }
}

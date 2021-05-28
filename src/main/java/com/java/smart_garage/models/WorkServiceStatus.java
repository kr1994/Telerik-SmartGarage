package com.java.smart_garage.models;

import javax.persistence.*;

@Entity
@Table(name = "work_service_status")
public class WorkServiceStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private int statusId;

    @Column(name = "status")
    private String status;

    public WorkServiceStatus() {
    }

    public WorkServiceStatus(int statusId, String status) {
        this.statusId = statusId;
        this.status = status;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

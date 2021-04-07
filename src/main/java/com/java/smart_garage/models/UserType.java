package com.java.smart_garage.models;

import javax.persistence.*;

@Entity
@Table(name = "types")
public class UserType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_type_id")
    private int typeId;

    @Column(name = "type")
    private String type;

    public UserType() {
    }

    public UserType(int typeId, String type) {
        this.typeId = typeId;
        this.type = type;
    }


    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public int getTypeId() {
        return typeId;
    }
}

package com.java.smart_garage.models;

import javax.persistence.*;

@Entity
@Table(name = "user_types")
public class UserTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_type_id")
    private int typeId;

    @Column(name = "type")
    private String type;

    public UserTypes() {
    }

    public UserTypes(int typeId, String type) {
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

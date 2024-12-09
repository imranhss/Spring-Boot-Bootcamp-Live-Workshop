package com.emranhss.sptingbootbotcamp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    @Column(length = 30)
    private String name;
    private  String email;
    private String cellNo;

    private  String image;

    public Student() {
    }

    public Student(int id, String name, String email, String cellNo, String image) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cellNo = cellNo;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellNo() {
        return cellNo;
    }

    public void setCellNo(String cellNo) {
        this.cellNo = cellNo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

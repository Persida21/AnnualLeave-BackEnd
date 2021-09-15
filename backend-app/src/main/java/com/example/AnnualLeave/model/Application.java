package com.example.AnnualLeave.model;

import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.security.Key;
import java.sql.Timestamp;
import java.util.Date;

@Table(name = "Application", indexes = {
        @Index(name = "idx_application_user", columnList = "user")
})
@Entity
public class Application {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    public enum TypeId{
        DAYOFF , VACATION , COMPENSATIONDAY
    }
    private TypeId type;
    private String title;
    private String description;
    private Long startDate;
    private Long endDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user" ,  referencedColumnName = "user_id")
    private User user;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public TypeId getType() {
        return type;
    }

    public void setType(TypeId type) {
        this.type = type;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

}

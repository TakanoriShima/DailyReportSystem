package com.techacademy.entity;

import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name="report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "report_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reportDate;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name="content", nullable = false)
    @Type(type = "text")
    private String content;

    /** 登録日時：createdAt */
    @Column(name = "created_at", nullable = true, updatable = false)
    @CreatedDate
    private Timestamp createdAt;

    /** 更新日時：updatedAt */
    @Column(name = "updated_at", nullable = true)
    @LastModifiedDate
    private Timestamp updatedAt;

    /** ID：employee_id */
    @ManyToOne
    @JoinColumn(name ="employee_id", referencedColumnName = "id")
    private Employee employee;

    @PrePersist
    public void onPrePersist() {
        setCreatedAt(new Timestamp(System.currentTimeMillis()));
        setUpdatedAt(new Timestamp(System.currentTimeMillis()));
    }

    @PreUpdate
    public void onPreUpdate() {
        setUpdatedAt(new Timestamp(System.currentTimeMillis()));
    }
}

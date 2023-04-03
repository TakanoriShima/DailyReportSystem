package com.techacademy.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")
@Where(clause = "delete_flag = 0")
public class Employee {

    /** ID：id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /** 氏名：name */
    @Column(length = 20, nullable = false)
    @NotEmpty
    @Length(max=20)
    private String name;

    /** 削除フラグ：deleteFlag */
    @Column(name = "delete_flag", nullable = true)
    private Integer deleteFlag;

    /** 登録日時：createdAt */
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private Timestamp createdAt;

    /** 更新日時：updatedAt */
    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private Timestamp updatedAt;

    /** authentication */
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private Authentication authentication;

    /** report */
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Report> reports;

    @PrePersist
    public void onPrePersist() {
        setCreatedAt(new Timestamp(System.currentTimeMillis()));
        setUpdatedAt(new Timestamp(System.currentTimeMillis()));
    }

    @PreUpdate
    public void onPreUpdate() {
        setUpdatedAt(new Timestamp(System.currentTimeMillis()));
    }

//    @PreRemove
//    @Transactional
//    private void preRemove() {
//        if (authentication!=null) {
//            authentication.setEmployee(null);
//        }
//    }

}

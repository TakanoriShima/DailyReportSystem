package com.techacademy.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "authentication"
//, uniqueConstraints = {@UniqueConstraint(name = "employee_authentication", columnNames = {"employee_id"})}
)
public class Authentication {

    public static enum Role {
        管理者, 一般
    }

    /** 社員番号：code */
    @Id
    @Column(name = "code", nullable = true, updatable = true)
    @NotEmpty
    private String code;

    /** パスワード：password */
    @Column(length = 255)
    @NotBlank
    @Length(max=255)
    private String password;

    /** 権限：role */
    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    /** ID：employee_id */
    @OneToOne
    @JoinColumn(name ="employee_id", referencedColumnName = "id")
    private Employee employee;

}

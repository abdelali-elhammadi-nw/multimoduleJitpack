package com.nimbleways.springboilerplate.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name ="employment_date", nullable = false)
    private LocalDate employmentDate;

    @Column(name="role", nullable = false)
    private String role;

    @Column(name="should_receive_mail_notifications", nullable = false)
    private boolean shouldReceiveMailNotifications;

    @Column(name="should_receive_approval_notifications", nullable = false)
    private boolean shouldReceiveApprovalNotifications;

}

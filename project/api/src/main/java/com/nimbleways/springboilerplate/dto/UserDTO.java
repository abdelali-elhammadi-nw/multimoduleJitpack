package com.nimbleways.springboilerplate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String name;
    private String email;
    private String role;
    private LocalDate employmentDate;
    private boolean shouldReceiveApprovalNotifications;
    private boolean shouldReceiveMailNotifications;

}

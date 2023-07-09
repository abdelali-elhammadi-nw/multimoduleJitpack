package com.nimbleways.springboilerplate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequestDTO {

    private String password;
    private boolean shouldReceiveMailNotification;
    private boolean shouldReceiveApprovalNotifications;

}

package com.nimbleways.springboilerplate.mappers;

import com.nimbleways.springboilerplate.dto.SignupRequestDTO;
import com.nimbleways.springboilerplate.dto.UserDTO;
import com.nimbleways.springboilerplate.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    @Mapping(target="id", expression = "java(user.getId().toString())")
    UserDTO userToUserDTO(User user);

    @Mapping(target="id", ignore = true)
    @Mapping(target="shouldReceiveApprovalNotifications", ignore = true)
    @Mapping(target="shouldReceiveMailNotifications", ignore = true)
    UserDTO signupRequestDtoToUserDTO(SignupRequestDTO signupRequestDTO);

    default User userDTOToUser(final UserDTO userDTO){
        if(userDTO==null) {
            return null;
        }
        final User user = new User();
        user.setRole(userDTO.getRole());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setEmploymentDate(userDTO.getEmploymentDate());
        user.setShouldReceiveApprovalNotifications(userDTO.isShouldReceiveApprovalNotifications());
        user.setShouldReceiveMailNotifications(userDTO.isShouldReceiveMailNotifications());
        if(userDTO.getId() != null) {
            user.setId(UUID.fromString(userDTO.getId()));
        }
        return user;
    }

}
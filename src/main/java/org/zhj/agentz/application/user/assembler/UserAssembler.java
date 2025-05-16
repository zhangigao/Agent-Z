package org.zhj.agentz.application.user.assembler;

import org.springframework.beans.BeanUtils;
import org.zhj.agentz.application.user.dto.UserDTO;
import org.zhj.agentz.domain.user.model.UserEntity;
import org.zhj.agentz.interfaces.dto.user.request.RegisterRequest;
import org.zhj.agentz.interfaces.dto.user.request.UserUpdateRequest;

public class UserAssembler {

    public static UserDTO toDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();

        BeanUtils.copyProperties(userEntity, userDTO);
        return userDTO;
    }

    public static UserEntity toEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDTO, userEntity);
        return userEntity;
    }

    public static UserEntity toEntity(RegisterRequest registerRequest) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(registerRequest, userEntity);
        return userEntity;
    }

    public static UserEntity toEntity(UserUpdateRequest userUpdateRequest, String userId) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userUpdateRequest, userEntity);
        userEntity.setId(userId);
        return userEntity;
    }
}

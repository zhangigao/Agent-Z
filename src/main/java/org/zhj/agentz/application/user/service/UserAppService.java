package org.zhj.agentz.application.user.service;

import org.springframework.stereotype.Service;
import org.zhj.agentz.application.user.assembler.UserAssembler;
import org.zhj.agentz.application.user.dto.UserDTO;
import org.zhj.agentz.domain.user.model.UserEntity;
import org.zhj.agentz.domain.user.service.UserDomainService;
import org.zhj.agentz.interfaces.dto.user.request.UserUpdateRequest;


@Service
public class UserAppService {

    private final UserDomainService userDomainService;

    public UserAppService(UserDomainService userDomainService) {
        this.userDomainService = userDomainService;
    }

    /** 获取用户信息 */
    public UserDTO getUserInfo(String id) {
        UserEntity userEntity = userDomainService.getUserInfo(id);
        return UserAssembler.toDTO(userEntity);
    }

    /** 修改用户信息 */
    public void updateUserInfo(UserUpdateRequest userUpdateRequest, String userId) {
        UserEntity user = UserAssembler.toEntity(userUpdateRequest, userId);
        userDomainService.updateUserInfo(user);
    }
}

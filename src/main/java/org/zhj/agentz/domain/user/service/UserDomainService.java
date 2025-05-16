package org.zhj.agentz.domain.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import org.zhj.agentz.domain.user.model.UserEntity;
import org.zhj.agentz.domain.user.repository.UserRepository;
import org.zhj.agentz.infrastructure.exception.BusinessException;
import org.zhj.agentz.infrastructure.utils.PasswordUtils;

import java.util.UUID;

@Service
public class UserDomainService {

    private final UserRepository userRepository;

    public UserDomainService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /** 获取用户信息 */
    public UserEntity getUserInfo(String id) {
        return userRepository.selectById(id);
    }

    /** 根据邮箱或手机号查找用户
     * @param account 邮箱或手机号
     * @return 用户实体，如果不存在则返回null */
    public UserEntity findUserByAccount(String account) {
        LambdaQueryWrapper<UserEntity> wrapper = Wrappers.<UserEntity>lambdaQuery().eq(UserEntity::getEmail, account)
                .or().eq(UserEntity::getPhone, account);

        return userRepository.selectOne(wrapper);
    }

    /** 注册 密码加密存储 */
    public UserEntity register(String email, String phone, String password) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setPhone(phone);
        userEntity.setPassword(PasswordUtils.encode(password));
        userEntity.valid();
        checkAccountExist(userEntity.getEmail(), userEntity.getPhone());

        // 生成昵称
        String nickname = generateNickname();
        userEntity.setNickname(nickname);
        userRepository.insert(userEntity);
        return userEntity;
    }

    public UserEntity login(String account, String password) {
        LambdaQueryWrapper<UserEntity> wrapper = Wrappers.<UserEntity>lambdaQuery().eq(UserEntity::getEmail, account)
                .or().eq(UserEntity::getPhone, account);

        UserEntity userEntity = userRepository.selectOne(wrapper);

        if (userEntity == null || !PasswordUtils.matches(password, userEntity.getPassword())) {
            throw new BusinessException("账号密码错误");
        }
        return userEntity;
    }

    /** 检查账号是否存在，邮箱 or 手机号任意值
     * @param email 邮箱账号
     * @param phone 手机号账号 */
    public void checkAccountExist(String email, String phone) {
        LambdaQueryWrapper<UserEntity> wrapper = Wrappers.<UserEntity>lambdaQuery().eq(UserEntity::getEmail, email).or()
                .eq(UserEntity::getPhone, phone);
        if (userRepository.exists(wrapper)) {
            throw new BusinessException("账号已存在,不可重复账注册");
        }
    }

    /** 随机生成用户昵称
     * @return 用户昵称 */
    private String generateNickname() {
        return "agent-x" + UUID.randomUUID().toString().replace("-", "").substring(0, 6);
    }

    public void updateUserInfo(UserEntity user) {
        userRepository.checkedUpdateById(user);
    }

    /** 更新用户密码
     * @param userId 用户ID
     * @param newPassword 新密码 */
    public void updatePassword(String userId, String newPassword) {
        UserEntity user = userRepository.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 加密新密码
        String encodedPassword = PasswordUtils.encode(newPassword);
        user.setPassword(encodedPassword);

        userRepository.checkedUpdateById(user);
    }
}

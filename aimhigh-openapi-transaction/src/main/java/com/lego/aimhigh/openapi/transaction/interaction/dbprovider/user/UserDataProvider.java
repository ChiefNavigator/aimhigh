package com.lego.aimhigh.openapi.transaction.interaction.dbprovider.user;

import com.lego.aimhigh.openapi.transaction.domain.entity.user.User;
import com.lego.aimhigh.openapi.transaction.domain.usecase.user.model.CreateUserModel;
import com.lego.aimhigh.openapi.transaction.domain.usecase.user.model.GetUserModel;
import com.lego.aimhigh.openapi.transaction.interaction.dbprovider.user.mapper.JpaUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class UserDataProvider implements CreateUserModel, GetUserModel {

  private final UserRepository userRepository;

  @Override
  public User createUser(User user) {
    JpaUser jpaUser = new JpaUser();
    jpaUser.setName(user.getName());
    jpaUser.setDeleted(false);
    LocalDateTime now = LocalDateTime.now();
    jpaUser.setCreatedBy(user.getName());
    jpaUser.setUpdatedBy(user.getName());
    jpaUser.setCreatedAt(now);
    jpaUser.setUpdatedAt(now);

    return JpaUserMapper.to(userRepository.save(jpaUser));
  }

  @Override
  public User getUser(Long userId) {
    JpaUser jpaUser = userRepository.findById(userId).orElseThrow();

    return JpaUserMapper.to(jpaUser);
  }
}

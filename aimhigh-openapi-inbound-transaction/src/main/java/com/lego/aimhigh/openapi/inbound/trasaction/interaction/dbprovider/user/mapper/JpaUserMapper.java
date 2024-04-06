package com.lego.aimhigh.openapi.inbound.trasaction.interaction.dbprovider.user.mapper;

import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.user.User;
import com.lego.aimhigh.openapi.inbound.trasaction.interaction.dbprovider.user.JpaUser;

public class JpaUserMapper {

  private JpaUserMapper() {
  }

  public static User to(JpaUser jpaUser) {
    User user = new User();
    user.setId(jpaUser.getId());
    user.setName(jpaUser.getName());

    return user;
  }
}

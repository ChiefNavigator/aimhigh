package com.lego.aimhigh.openapi.transaction.domain.usecase.user.model;

import com.lego.aimhigh.openapi.transaction.domain.entity.user.User;

public interface CreateUserModel {

  User createUser(User user);
}

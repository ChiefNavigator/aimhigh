package com.lego.aimhigh.openapi.transaction.domain.usecase.user.model;

import com.lego.aimhigh.openapi.transaction.domain.entity.user.User;

public interface GetUserModel {

  User getUser(Long userId);
}

package com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.user;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class User {
  @NonNull private Long id;
  @NonNull private String name;
}

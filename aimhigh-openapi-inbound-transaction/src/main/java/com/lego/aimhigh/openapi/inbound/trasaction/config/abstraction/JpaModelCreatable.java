package com.lego.aimhigh.openapi.inbound.trasaction.config.abstraction;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class JpaModelCreatable {

  @Column
  private boolean deleted;

  @Column
  private String createdBy;

  @Column
  private String updatedBy;

  @Column
  private LocalDateTime createdAt;

  @Column
  private LocalDateTime updatedAt;

}

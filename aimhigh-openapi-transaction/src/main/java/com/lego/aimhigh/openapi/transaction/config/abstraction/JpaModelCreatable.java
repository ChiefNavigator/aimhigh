package com.lego.aimhigh.openapi.transaction.config.abstraction;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
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

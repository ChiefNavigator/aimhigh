package com.lego.aimhigh.openapi.inbound.trasaction.interaction.dbprovider.bizremit;

import com.lego.aimhigh.openapi.inbound.trasaction.config.abstraction.JpaModelCreatable;
import com.lego.aimhigh.openapi.inbound.trasaction.interaction.dbprovider.bizremit.contant.JpaBizRemitRequestStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "BizRemitRequests")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JpaBizRemitRequest extends JpaModelCreatable {
  @Id
  @GeneratedValue
  @Column
  private Long id;

  @Column
  private Long userId;

  @Column
  private Long userKcdBankAccountId;

  @Column
  private LocalDateTime requestDate;

  @Column
  private String fromAccountId;

  @Column
  private String toAccountId;

  @Column
  private Long amount;

  @Column
  private JpaBizRemitRequestStatus status;
}

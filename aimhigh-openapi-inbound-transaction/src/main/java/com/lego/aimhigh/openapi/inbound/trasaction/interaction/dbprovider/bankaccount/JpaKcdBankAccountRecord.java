package com.lego.aimhigh.openapi.inbound.trasaction.interaction.dbprovider.bankaccount;

import com.lego.aimhigh.openapi.inbound.trasaction.config.abstraction.JpaModelCreatable;
import com.lego.aimhigh.openapi.inbound.trasaction.interaction.dbprovider.bankaccount.contant.JpaKcdBankAccountAction;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "KcdBankAccountRecords")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JpaKcdBankAccountRecord extends JpaModelCreatable {
  @Id
  @GeneratedValue
  @Column
  private Long id;

  @ManyToOne
  @JoinColumn(name = "kcdBankAccountId")
  private JpaKcdBankAccount kcdBankAccount;

  @Column
  private JpaKcdBankAccountAction action;

  @Column
  private Long amount;
}

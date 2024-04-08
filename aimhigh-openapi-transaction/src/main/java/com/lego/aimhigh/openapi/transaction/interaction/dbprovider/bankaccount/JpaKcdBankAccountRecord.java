package com.lego.aimhigh.openapi.transaction.interaction.dbprovider.bankaccount;

import com.lego.aimhigh.openapi.transaction.config.abstraction.JpaModelCreatable;
import com.lego.aimhigh.openapi.transaction.interaction.dbprovider.bankaccount.contant.JpaKcdBankAccountAction;
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

  @Column(unique = true)
  private String bankTransactionId;

  @ManyToOne
  @JoinColumn(name = "kcdBankAccountId")
  private JpaKcdBankAccount kcdBankAccount;

  @Column
  private JpaKcdBankAccountAction action;

  @Column
  private Long amount;
}

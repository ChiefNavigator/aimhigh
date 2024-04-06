package com.lego.aimhigh.openapi.inbound.trasaction.interaction.dbprovider.bankaccount;

import com.lego.aimhigh.openapi.inbound.trasaction.config.abstraction.JpaModelCreatable;
import com.lego.aimhigh.openapi.inbound.trasaction.interaction.dbprovider.user.JpaUser;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "KcdBankAccounts")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JpaKcdBankAccount extends JpaModelCreatable {

  @Id
  @GeneratedValue
  @Column
  private Long id;

  @Column(unique = true)
  private String accountNumber;

  @OneToOne
  @JoinColumn(name = "userId")
  private JpaUser user;

  @Column
  private Long amount;

  @Version
  private Long version;
}

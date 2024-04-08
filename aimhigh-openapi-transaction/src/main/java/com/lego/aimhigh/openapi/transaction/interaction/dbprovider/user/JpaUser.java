package com.lego.aimhigh.openapi.transaction.interaction.dbprovider.user;

import com.lego.aimhigh.openapi.transaction.config.abstraction.JpaModelCreatable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JpaUser extends JpaModelCreatable {

  @Id
  @GeneratedValue
  @Column
  private Long id;

  @Column
  private String name;

}

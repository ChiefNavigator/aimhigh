package com.lego.aimhigh.openapi.transaction.interaction.dbprovider.bizremit;

import com.lego.aimhigh.openapi.transaction.config.abstraction.JpaModelCreatable;
import com.lego.aimhigh.openapi.transaction.interaction.dbprovider.bizremit.contant.JpaBizRemitRequestStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "BizRemitRequestRecords")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JpaBizRemitRequestRecord extends JpaModelCreatable {

  @Id
  @GeneratedValue
  @Column
  private Long id;

  @ManyToOne
  @JoinColumn(name = "bizRemitRequestId")
  private JpaBizRemitRequest bizRemitRequest;

  @Column
  private JpaBizRemitRequestStatus status;

}

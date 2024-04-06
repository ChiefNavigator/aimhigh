package com.lego.aimhigh.openapi.inbound.trasaction.interaction.apigateway.openapi.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountTransferResult {
  private final String tran_no;
  private final String bank_tran_id;
  private final String bank_tran_date;
  private final String bank_code_tran;
  private final String bank_rsp_code;
  private final String bank_rsp_message;
  private final String bank_code_std;
  private final String bank_code_sub;
  private final String bank_name;
  private final String savings_bank_name;
  private final String account_num;
  private final String account_seq;
  private final String account_num_masked;
  private final String print_content;
  private final String account_holder_name;
  private final String tran_amt;
  private final String cms_num;
}

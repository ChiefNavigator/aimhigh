package com.lego.aimhigh.openapi.inbound.trasaction.interaction.apigateway.openapi.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AccountTransferRequestBody {
  private final String cntr_account_type;
  private final String cntr_account_num;
  private final String wd_pass_phrase;
  private final String wd_print_content;
  private final String name_check_option;
  private final String sub_frnc_name;
  private final String sub_frnc_num;
  private final String sub_frnc_business_num;
  private final String tran_dtime;
  private final String req_cnt;
  private final List<AccountTransferRequest> req_list;
}

package com.ocs.portal.dto.request.balanceAdjustment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotePaymentData {
  private Long noteId;

  private Long eventPaymentId;

  private Long charge;

  private Date createdDate;

  private Long spId;
}

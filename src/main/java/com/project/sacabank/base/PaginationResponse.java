package com.project.sacabank.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class PaginationResponse {
  private Object list;
  private Integer count;
  private Integer totalPage;
}

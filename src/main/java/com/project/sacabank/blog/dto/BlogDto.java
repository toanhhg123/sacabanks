package com.project.sacabank.blog.dto;

import java.util.List;

import com.project.sacabank.base.BaseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class BlogDto extends BaseDto {
  private String content;
  private String title;
  private List<String> focusKeywords;
  private String slug;
  private String image;
  private String shortDesc;

}

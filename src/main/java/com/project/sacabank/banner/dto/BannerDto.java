package com.project.sacabank.banner.dto;

import com.project.sacabank.base.BaseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BannerDto extends BaseDto {
    private String image;
    private String title;
    private String description;
    private Boolean isActive;
}

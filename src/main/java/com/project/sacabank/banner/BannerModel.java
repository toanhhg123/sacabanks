package com.project.sacabank.banner;

import com.google.auto.value.AutoValue.Builder;
import com.project.sacabank.banner.dto.BannerDto;
import com.project.sacabank.base.BaseDto;
import com.project.sacabank.base.BaseModel;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "banner")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BannerModel extends BaseModel {
    private String image;
    private String title;
    private String description;
    private Boolean isActive;

    @Override
    public BaseModel fromDto(BaseDto baseDto) {
        BannerDto bannerDto = (BannerDto) baseDto;

        // if (bannerDto.getIsActive() != null) {
        // this.setIsActive(bannerDto.getIsActive());
        // }

        if (bannerDto.getImage() != null) {
            this.setImage(bannerDto.getImage());
        }

        if (bannerDto.getTitle() != null) {
            this.setTitle(bannerDto.getTitle());
        }

        if (bannerDto.getDescription() != null) {
            this.setDescription(bannerDto.getDescription());
        }

        return this;
    }
}

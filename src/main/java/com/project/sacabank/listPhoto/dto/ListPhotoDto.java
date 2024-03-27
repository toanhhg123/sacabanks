package com.project.sacabank.listPhoto.dto;

import java.util.UUID;

import com.google.auto.value.AutoValue.Builder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ListPhotoDto {
  String photoUrl;
  UUID productId;
}

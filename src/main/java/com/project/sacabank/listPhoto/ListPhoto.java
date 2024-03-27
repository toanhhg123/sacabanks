package com.project.sacabank.listPhoto;

import java.util.UUID;

import com.project.sacabank.base.BaseModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class ListPhoto extends BaseModel {
  String photoUrl;

  @Column(name = "product_id")
  UUID productId;
}

package com.project.sacabank.base;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BaseModel {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  protected UUID id;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  protected Date createdAt;
  // @Column(name="time", columnDefinition="DATETIME DEFAULT NOW()")

  @LastModifiedDate
  @Column(name = "updated_at")
  protected Date updatedAt;

  public BaseModel fromDto(BaseDto baseDto) {
    return this;
  }

}

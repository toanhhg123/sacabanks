package com.project.sacabank.blog;

import java.util.List;

import com.project.sacabank.base.BaseDto;
import com.project.sacabank.base.BaseModel;
import com.project.sacabank.blog.dto.BlogDto;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Table(name = "blog")
public class BlogModel extends BaseModel {
  @Lob
  @Column(columnDefinition = "LONGTEXT")
  private String content;
  private String title;

  private String image;
  private String shortDesc;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "blog_focus_keywords", joinColumns = @JoinColumn(name = "blog_id"))
  @Column(name = "keyword")
  private List<String> focusKeywords;

  private String slug;

  @Override
  public BaseModel fromDto(BaseDto baseDto) {
    var dto = (BlogDto) baseDto;

    if (dto.getContent() != null) {
      this.setContent(dto.getContent());
    }

    if (dto.getTitle() != null) {
      this.setTitle(dto.getTitle());
    }

    if (dto.getFocusKeywords() != null) {
      this.setFocusKeywords(dto.getFocusKeywords());
    }

    if (dto.getSlug() != null) {
      this.setSlug(dto.getSlug());
    }

    if (dto.getImage() != null) {
      this.setImage(dto.getImage());
    }

    if (dto.getShortDesc() != null) {
      this.setShortDesc(dto.getShortDesc());
    }

    return this;
  }
}

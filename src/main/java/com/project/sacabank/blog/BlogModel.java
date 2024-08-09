package com.project.sacabank.blog;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.project.sacabank.base.BaseDto;
import com.project.sacabank.base.BaseModel;
import com.project.sacabank.blog.dto.BlogDto;

import io.hypersistence.utils.hibernate.type.json.JsonType;
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

    return this;
  }
}

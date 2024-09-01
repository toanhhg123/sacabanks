package com.project.sacabank.product.model;

import java.util.List;

import com.project.sacabank.base.BaseModel;
import com.project.sacabank.category.model.Category;
import com.project.sacabank.listPhoto.ListPhoto;
import com.project.sacabank.product.dto.ProductDto;
import com.project.sacabank.productCategory.ProductCategoryModel;
import com.project.sacabank.user.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Product extends BaseModel {
  private String title;
  private String slug;
  private String itemNumber;
  private String material;
  private String finishing;
  private Float dimensionL;
  private Float dimensionW;
  private Float dimensionH;
  private Float netWeight;
  private Double price;
  private Integer quantity;
  private String mainPhoto;
  private String tags;
  @Column(name = "description")
  private String desc;

  @ManyToOne()
  @JoinColumn(name = "user_id", nullable = true, referencedColumnName = "id")
  private User user;

  @ManyToOne()
  @JoinColumn(name = "category_id", nullable = true, referencedColumnName = "id")
  private Category category;

  @OneToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "list_photo", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "id"))
  private List<ListPhoto> listPhoto;

  @OneToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "category_product", joinColumns = @JoinColumn(name = "productId"), inverseJoinColumns = @JoinColumn(name = "id"))
  private List<ProductCategoryModel> productCategories;

  @Convert(converter = ListDetailsAttributeConverter.class)
  @Column(name = "list_details")
  private List<DetailItem> listDetails;

  public void updateFromDTO(ProductDto productDTO) {
    if (productDTO.getTitle() != null) {
      this.setTitle(productDTO.getTitle());
    }
    if (productDTO.getSlug() != null && productDTO.getSlug() != productDTO.getSlug()) {
      this.setSlug(productDTO.getSlug());
    }
    if (productDTO.getItemNumber() != null) {
      this.setItemNumber(productDTO.getItemNumber());
    }
    if (productDTO.getMaterial() != null) {
      this.setMaterial(productDTO.getMaterial());
    }
    if (productDTO.getFinishing() != null) {
      this.setFinishing(productDTO.getFinishing());
    }
    if (productDTO.getSlug() != null && productDTO.getSlug() != this.getSlug()) {
      this.setSlug(productDTO.getSlug());
    }
    if (productDTO.getDimensionL() != null) {
      this.setDimensionL(productDTO.getDimensionL());
    }
    if (productDTO.getDimensionW() != null) {
      this.setDimensionW(productDTO.getDimensionW());
    }
    if (productDTO.getDimensionH() != null) {
      this.setDimensionH(productDTO.getDimensionH());
    }
    if (productDTO.getNetWeight() != null) {
      this.setNetWeight(productDTO.getNetWeight());
    }
    if (productDTO.getPrice() != null) {
      this.setPrice(productDTO.getPrice());
    }
    if (productDTO.getQuantity() != null) {
      this.setQuantity(productDTO.getQuantity());
    }
    if (productDTO.getMainPhoto() != null) {
      this.setMainPhoto(productDTO.getMainPhoto());
    }
    if (productDTO.getTags() != null) {
      this.setTags(productDTO.getTags());
    }
    if (productDTO.getDesc() != null) {
      this.setDesc(productDTO.getDesc());
    }

  }
}

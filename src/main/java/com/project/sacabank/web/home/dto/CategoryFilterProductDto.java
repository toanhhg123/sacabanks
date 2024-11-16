package com.project.sacabank.web.home.dto;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.SqlResultSetMapping;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SqlResultSetMapping(name = "CategoryFilterProductDtoMapping", classes = @ConstructorResult(targetClass = SupplierDto.class, columns = {
                @ColumnResult(name = "id", type = UUID.class),
                @ColumnResult(name = "name", type = String.class),
                @ColumnResult(name = "children", type = String.class),
                @ColumnResult(name = "image", type = String.class)

}))
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryFilterProductDto {
        @Id
        private UUID id;
        private String name;
        private String image;

        @Type(JsonType.class)
        @Column(name = "children")
        private List<CategoryFilterProductDto> children;

}

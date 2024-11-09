package com.project.sacabank.web.home.dto;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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
@SqlResultSetMapping(name = "SupplierDtoMapping", classes = @ConstructorResult(targetClass = SupplierDto.class, columns = {
        @ColumnResult(name = "first_letter", type = String.class),
        @ColumnResult(name = "company_names", type = String.class)
}))
public class SupplierDto {
    @Id
    private String firstLetter;

    @Type(JsonType.class)
    @Column(name = "company_names")
    private List<CompanyInfo> companyInfos;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CompanyInfo {
        @JsonProperty("id")
        private UUID id;

        @JsonProperty("username")
        private String username;

        @JsonProperty("shortName")
        private String shortName;

        @JsonProperty("companyName")
        private String companyName;
    }
}

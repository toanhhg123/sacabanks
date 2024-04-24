package com.project.sacabank.product.model;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import jakarta.persistence.AttributeConverter;

public class ListDetailsAttributeConverter implements AttributeConverter<List<DetailItem>, String> {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String convertToDatabaseColumn(List<DetailItem> attribute) {
    try {
      return objectMapper.writeValueAsString(attribute);
    } catch (JsonProcessingException jpe) {
      return null;
    }
  }

  @Override
  public List<DetailItem> convertToEntityAttribute(String dbData) {
    try {
      if (dbData == null) {
        return List.of();
      }
      return objectMapper.readValue(dbData, new TypeReference<List<DetailItem>>() {
      });
    } catch (JsonProcessingException e) {
      return List.of();
    }
  }
}
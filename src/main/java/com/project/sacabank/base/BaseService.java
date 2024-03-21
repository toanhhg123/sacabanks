package com.project.sacabank.base;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseService<TModel extends BaseModel> {

  @Autowired
  BaseRepository<TModel, UUID> nBaseRepository;

  @Autowired
  private ModelMapper mapper;

  public BaseService(BaseRepository<TModel, UUID> nBaseRepository) {
    this.nBaseRepository = nBaseRepository;
  }

  public List<TModel> getAll() {
    var data = this.nBaseRepository.findAll();
    return data;
  }

  @SuppressWarnings("null")
  public <TYPE_DTO extends BaseDto> TModel create(TYPE_DTO dto, Class<TModel> returnType) {
    var body = mapper.map(dto, returnType);
    return nBaseRepository.save(body);
  }

}

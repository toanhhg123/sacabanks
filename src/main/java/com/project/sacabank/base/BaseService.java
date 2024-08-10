package com.project.sacabank.base;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.sacabank.exception.CustomException;

@SuppressWarnings("unchecked")
public class BaseService<TModel extends BaseModel> {

  BaseRepository<TModel, UUID> nBaseRepository;

  @Autowired
  private ModelMapper mapper;

  @Autowired
  protected FullRepo repositories;

  public void InJectRepository(BaseRepository<TModel, UUID> repo) {
    this.nBaseRepository = repo;
  }

  public List<TModel> getAll() {
    var data = this.nBaseRepository.findAll();
    return data;
  }

  public TModel getById(UUID id) {
    var data = this.nBaseRepository.findById(id);
    if (!data.isPresent())
      throw new CustomException("không tìm thấy phần tử");
    return data.get();
  }

  public TModel create(BaseDto dto) {
    Class<TModel> modelClass = (Class<TModel>) ((ParameterizedType) getClass().getGenericSuperclass())
        .getActualTypeArguments()[0];
    TModel entity = mapper.map(dto, modelClass);
    return nBaseRepository.save(entity);
  }

  public TModel removeById(UUID id) {
    var model = nBaseRepository.findById(id);
    if (!model.isPresent())
      throw new CustomException("không tìm thấy phần tử");

    nBaseRepository.delete(model.get());
    return model.get();
  }

  public TModel update(UUID id, BaseDto baseDto) {
    var model = nBaseRepository.findById(id);
    if (!model.isPresent())
      throw new CustomException("không tìm thấy phần tử");

    var data = model.get();

    data = (TModel) data.fromDto(baseDto);

    return nBaseRepository.save(data);
  }

}

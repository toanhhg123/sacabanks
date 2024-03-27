package com.project.sacabank.listPhoto;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.sacabank.listPhoto.dto.ListPhotoDto;

@Service
public class ListPhotoService {
  @Autowired
  ListPhotoRepository listPhotoRepository;

  @Autowired
  ModelMapper mapper;

  public ListPhoto create(ListPhotoDto listPhotoDto) {
    ListPhoto listPhoto = mapper.map(listPhotoDto, ListPhoto.class);
    return listPhotoRepository.save(listPhoto);
  }

  public ListPhoto delete(UUID id) {
    var listPhoto = listPhotoRepository.findById(id);
    listPhotoRepository.delete(listPhoto.get());
    return listPhoto.get();
  }

}

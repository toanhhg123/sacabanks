package com.project.sacabank.blog;

import org.springframework.stereotype.Service;

import com.project.sacabank.base.BaseService;
import com.project.sacabank.base.FullRepo;
import com.project.sacabank.exception.CustomException;

@Service
public class BlogService extends BaseService<BlogModel> {

  public BlogService(FullRepo fullRepo) {
    super.InJectRepository(fullRepo.blogRepository);
  }

  public BlogModel getBySlug(String slug) {
    var slugOption = this.repositories.blogRepository.findBySlug(slug);

    if (!slugOption.isPresent())
      throw new CustomException("Không tìm thấy bài viết");

    return slugOption.get();
  }

  public void IsValidSlug(String slug) {
    var slugOption = this.repositories.blogRepository.findBySlug(slug);

    if (slugOption.isPresent())
      throw new CustomException("Link bài viết này đã được tạo trước đó");

  }

}

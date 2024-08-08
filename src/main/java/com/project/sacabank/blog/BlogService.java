package com.project.sacabank.blog;

import org.springframework.stereotype.Service;

import com.project.sacabank.base.BaseService;

@Service
public class BlogService extends BaseService<BlogModel> {

  public BlogService(BlogRepository repository) {
    super.InJectRepository(repository);
  }

}

package com.project.sacabank.banner;

import static com.project.sacabank.utils.Constants.PAGE_SIZE;

import java.util.Optional;

import org.hibernate.service.spi.InjectService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.project.sacabank.base.BaseService;
import com.project.sacabank.base.FullRepo;
import com.project.sacabank.base.PaginationResponse;

@Service
public class BannerService extends BaseService<BannerModel> {
    public BannerService(FullRepo fullRepo) {
        super.InJectRepository(fullRepo.bannerRepository);
    }

    public PaginationResponse getAll(Optional<Boolean> isActive, Optional<Integer> page, Optional<Integer> pageSize) {
        Specification<BannerModel> spec = Specification.where(null);

        var pageNumber = page.isPresent() && page.get() > 0 ? page.get() - 1 : 0;
        var size = pageSize.isPresent() ? pageSize.get() : PAGE_SIZE;

        if (isActive.isPresent()) {
            spec = spec.and(BannerSpecifications.isActive(isActive.get()));
        }

        var count = nBaseRepository.count(spec);
        var list = nBaseRepository.findAll(spec, PageRequest.of(pageNumber, size));
        var totalPage = (int) Math.ceil((double) count / size);

        return PaginationResponse.builder().totalPage(totalPage).count(count).list(list).build();

    }
}

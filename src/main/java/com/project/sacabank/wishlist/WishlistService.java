package com.project.sacabank.wishlist;

import static com.project.sacabank.utils.Constants.PAGE_SIZE;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.project.sacabank.base.BaseDto;
import com.project.sacabank.base.BaseService;
import com.project.sacabank.base.FullRepo;
import com.project.sacabank.base.PaginationResponse;
import com.project.sacabank.wishlist.dto.WishlistDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WishlistService extends BaseService<WishlistModel> {

    public WishlistService(FullRepo fullRepo) {
        super.InJectRepository(fullRepo.wishlistRepository);
    }

    public PaginationResponse getWishlistPagination(
            UUID userId,
            Optional<String> search,
            Optional<Integer> page,
            Optional<Integer> limit) {

        Specification<WishlistModel> spec = Specification.where(WishlistSpecifications.equalUserId(userId));

        if (search.isPresent()) {
            spec = spec.and(WishlistSpecifications.searchByProduct(search.get()));
        }

        var pageNumber = page.isPresent() && page.get() > 0 ? page.get() - 1 : 0;
        var size = limit.isPresent() ? limit.get() : PAGE_SIZE;

        var count = nBaseRepository.count(spec);
        var list = nBaseRepository.findAll(spec, PageRequest.of(pageNumber, size));
        var totalPage = (int) Math.ceil((double) count / size);

        return PaginationResponse.builder().totalPage(totalPage).count(count).list(list).build();
    }

    @Override
    public WishlistModel create(BaseDto dto) {
        var wishlistDto = (WishlistDto) dto;
        var wishlist = repositories.wishlistRepository.findByProductIdAndUserId(wishlistDto.getProductId(),
                wishlistDto.getUserId());
        if (wishlist.isPresent()) {
            return wishlist.get();
        }
        return super.create(dto);
    }

}

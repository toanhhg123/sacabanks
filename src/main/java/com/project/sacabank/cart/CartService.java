package com.project.sacabank.cart;

import static com.project.sacabank.utils.Constants.PAGE_SIZE;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.project.sacabank.base.BaseService;
import com.project.sacabank.base.FullRepo;
import com.project.sacabank.base.PaginationResponse;

@Service
public class CartService extends BaseService<CartModel> {

    public CartService(FullRepo repositories) {
        super.InJectRepository(repositories.cartRepository);
    }

    public PaginationResponse getCartPagination(
            UUID userId,
            Optional<String> search,
            Optional<Integer> page,
            Optional<Integer> limit) {

        Specification<CartModel> spec = Specification.where(CartSpecifications.equalUserId(userId));

        var pageNumber = page.isPresent() && page.get() > 0 ? page.get() - 1 : 0;
        var size = limit.isPresent() ? limit.get() : PAGE_SIZE;

        var count = nBaseRepository.count(spec);
        var list = nBaseRepository.findAll(spec, PageRequest.of(pageNumber, size));
        var totalPage = (int) Math.ceil((double) count / size);

        return PaginationResponse.builder().totalPage(totalPage).count(count).list(list).build();
    }

}

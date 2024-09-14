package com.project.sacabank.productComment;

import static com.project.sacabank.utils.Constants.PAGE_SIZE;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.sacabank.base.BaseService;
import com.project.sacabank.base.FullRepo;
import com.project.sacabank.base.PaginationResponse;
import com.project.sacabank.productComment.dto.ProductCommentQueryDto;

import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductCommentService extends BaseService<ProductCommentModel> {
    public ProductCommentService(FullRepo fullRepo) {
        InJectRepository(fullRepo.productCommentRepository);
    }

    public PaginationResponse getByProductId(UUID productId, Optional<Integer> page,
            Optional<Integer> pageSize) {
        var pageNumber = page.isPresent() && page.get() > 0 ? page.get() - 1 : 0;
        var size = pageSize.isPresent() ? pageSize.get() : PAGE_SIZE;

        Pageable pageable = PageRequest.of(pageNumber, size);

        String countQuery = """
                SELECT COUNT(product.id) FROM product WHERE product.id = :id
                """;

        String nativeQuery = """
                SELECT
                    product_comment.*,
                    JSON_OBJECT("id", BIN_TO_UUID(u.id), "username", u.username, "email", u.email, "avatar", u.avatar) as user,
                    JSON_OBJECT("id", BIN_TO_UUID(product.id), "title", product.title, "slug", product.slug, "mainPhoto", product.main_photo) as product
                FROM
                    product_comment
                    JOIN `user` u ON u.id = product_comment.user_id
                    JOIN product ON product_comment.product_id = product.id
                WHERE product.id = :id
                LIMIT
                """
                + pageable.getPageSize() + " OFFSET " + pageable.getOffset();

        Query query = repositories.entityManager.createNativeQuery(nativeQuery, ProductCommentQueryDto.class);
        query.setParameter("id", productId);

        @SuppressWarnings("unchecked")
        List<ProductCommentQueryDto> results = query.getResultList();

        // * GET COUNT - TOTAL-PAGE ============
        Query cQuery = repositories.entityManager.createNativeQuery(countQuery);
        cQuery.setParameter("id", productId);
        var count = (Number) cQuery.getSingleResult();
        var totalPage = (int) Math.ceil(count.doubleValue() / size);
        // * ========================

        return PaginationResponse.builder().totalPage(totalPage).count(count.intValue()).list(results).build();

    }
}

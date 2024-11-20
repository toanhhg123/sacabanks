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
import com.project.sacabank.productComment.dto.ProductCommentManagerDto;
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

    public List<ProductCommentQueryDto> getByProductIdActive(UUID productId) {

        String nativeQuery = """
                SELECT
                    product_comment.*,
                    JSON_OBJECT("id", BIN_TO_UUID(u.id), "username", u.username, "email", u.email, "avatar", u.avatar) as user,
                    JSON_OBJECT("id", BIN_TO_UUID(product.id), "title", product.title, "slug", product.slug, "mainPhoto", product.main_photo) as product
                FROM
                    product_comment
                    JOIN `user` u ON u.id = product_comment.user_id
                    JOIN product ON product_comment.product_id = product.id
                WHERE product.id = :id and product_comment.is_active = true
                """;

        Query query = repositories.entityManager.createNativeQuery(nativeQuery, ProductCommentQueryDto.class);
        query.setParameter("id", productId);

        @SuppressWarnings("unchecked")
        List<ProductCommentQueryDto> results = query.getResultList();

        return results;

    }

    public PaginationResponse getProductCommentManager(
            Optional<UUID> userId,
            Optional<Integer> page,
            Optional<Integer> pageSize) {

        var pageNumber = page.isPresent() && page.get() > 0 ? page.get() - 1 : 0;
        var size = pageSize.isPresent() ? pageSize.get() : PAGE_SIZE;
        Pageable pageable = PageRequest.of(pageNumber, size);

        String mainQuery = """
                SELECT
                    p.id as id,
                    main_photo,
                    p.title,
                	price,
                	quantity,
                    p.created_at,
                    p.updated_at,
                	slug,
                	JSON_OBJECT("id", BIN_TO_UUID(u.id), "username", u.username, "email", u.email, "avatar", u.avatar) as `user`,
                	COUNT(CASE WHEN pc.id IS NOT NULL AND pc.is_active = 1 THEN 1 END) AS quantity_comment_active,
                    COUNT(CASE WHEN pc.id IS NOT NULL AND (pc.is_active = 0 OR pc.is_active IS NULL) THEN 1 END) AS quantity_comment_no_active

                FROM
                	product p
                    LEFT JOIN product_comment pc ON pc.product_id = p.id
                    LEFT JOIN `user` u ON u.id = p.user_id
                """;

        var whereClause = userId.isPresent() ? " WHERE p.user_id = :userId " : "";
        var groupAndPagination = """
                  GROUP BY
                p.id
                  LIMIT
                  """ + pageable.getPageSize() + " OFFSET " + pageable.getOffset();

        mainQuery += whereClause + groupAndPagination;

        String countQuery = """
                SELECT COUNT(p.id) FROM product p
                """ + whereClause;

        Query query = repositories.entityManager.createNativeQuery(mainQuery, ProductCommentManagerDto.class);
        Query cQuery = repositories.entityManager.createNativeQuery(countQuery);

        if (userId.isPresent()) {
            query.setParameter("userId", userId.get());
            cQuery.setParameter("userId", userId.get());
        }

        // * GET COUNT - TOTAL-PAGE ============

        var count = (Number) cQuery.getSingleResult();
        @SuppressWarnings("unchecked")
        List<ProductCommentManagerDto> results = query.getResultList();
        var totalPage = (int) Math.ceil(count.doubleValue() / size);
        // * ========================

        return PaginationResponse.builder().totalPage(totalPage).count(count.intValue()).list(results).build();

    }
}

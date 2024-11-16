package com.project.sacabank.web.home;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.project.sacabank.web.home.dto.CategoryFilterProductDto;
import com.project.sacabank.web.home.dto.ProductDtoHomeQuery;
import com.project.sacabank.web.home.dto.SupplierDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HomeService {
    private final EntityManager entityManager;

    public List<ProductDtoHomeQuery> getProductPreview(int limit) {
        String sqlString = """
                SELECT NEW com.project.sacabank.web.home.dto.ProductDtoHomeQuery(
                    p.id,
                    p.title,
                    p.slug,
                    u.username,
                    p.mainPhoto,
                    p.price, p.tags)
                FROM Product p
                LEFT JOIN User u on p.userId = u.id
                """;

        return entityManager.createQuery(sqlString, ProductDtoHomeQuery.class)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<ProductDtoHomeQuery> getProductNew(int limit) {
        String sqlString = """
                SELECT NEW com.project.sacabank.web.home.dto.ProductDtoHomeQuery(
                    p.id,
                    p.title,
                    p.slug,
                    u.username,
                    p.mainPhoto,
                    p.price, p.tags)
                FROM Product p
                LEFT JOIN User u on p.userId = u.id
                WHERE p.tags = 'NEW'
                """;

        return entityManager.createQuery(sqlString, ProductDtoHomeQuery.class)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<ProductDtoHomeQuery> getProductHot(int limit) {
        String sqlString = """
                SELECT NEW com.project.sacabank.web.home.dto.ProductDtoHomeQuery(
                    p.id,
                    p.title,
                    p.slug,
                    u.username,
                    p.mainPhoto,
                    p.price, p.tags)
                FROM Product p
                LEFT JOIN User u on p.userId = u.id
                WHERE p.tags = 'HOT'
                """;

        return entityManager.createQuery(sqlString, ProductDtoHomeQuery.class)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<ProductDtoHomeQuery> getProductBestSeller(int limit) {
        String sqlString = """
                SELECT NEW com.project.sacabank.web.home.dto.ProductDtoHomeQuery(
                    p.id,
                    p.title,
                    p.slug,
                    u.username,
                    p.mainPhoto,
                    p.price, p.tags)
                FROM Product p
                LEFT JOIN User u on p.userId = u.id
                WHERE p.tags = 'BESTSELLER'
                """;

        return entityManager.createQuery(sqlString, ProductDtoHomeQuery.class)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<ProductDtoHomeQuery> getProductHome() {
        return getProductPreview(8);
    }

    public List<ProductDtoHomeQuery> getProductHome(UUID userId) {

        String sqlString = """
                SELECT NEW com.project.sacabank.web.home.dto.ProductDtoHomeQuery(
                    p.id,
                    p.title,
                    p.slug,
                    u.username,
                    p.mainPhoto,
                    p.price,
                    p.tags)
                FROM Product p
                LEFT JOIN User u on p.userId = u.id
                WHERE u.id = :userId
                """;

        return entityManager.createQuery(sqlString, ProductDtoHomeQuery.class)
                .setParameter("userId", userId)
                .setMaxResults(50)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<SupplierDto> getSupplierGroupFirstName() {
        String queryString = """
                SELECT
                    CASE
                        WHEN REGEXP_REPLACE(UPPER(LEFT(COALESCE(TRIM(user.short_name_company), user.company_name), 1)), '[^A-Z]', '') = '' THEN 'Other'
                        ELSE UPPER(LEFT(COALESCE(TRIM(user.short_name_company), user.company_name), 1))
                      END AS first_letter,

                      JSON_ARRAYAGG(
                        JSON_OBJECT(
                          'id', BIN_TO_UUID(user.id),
                          'username', user.username,
                          'shortName', user.short_name_company,
                          'companyName', user.company_name
                        )
                      ) AS company_names

                FROM
                    user
                WHERE
                    user.`role` = 'VENDOR'
                    AND COALESCE(TRIM(user.short_name_company), user.company_name) IS NOT NULL
                GROUP BY
                    CASE
                        WHEN REGEXP_REPLACE(UPPER(LEFT(COALESCE(TRIM(user.short_name_company), user.company_name), 1)), '[^A-Z]', '') = '' THEN 'other'
                        ELSE UPPER(LEFT(COALESCE(TRIM(user.short_name_company), user.company_name), 1))
                      END
                ORDER BY
                    CASE
                        WHEN first_letter = 'Other' THEN 1
                        ELSE 0
                    END,
                    first_letter
                                    """;

        Query nativeQuery = entityManager.createNativeQuery(queryString, SupplierDto.class);

        return nativeQuery.getResultList();

    }

    @SuppressWarnings("unchecked")
    public List<CategoryFilterProductDto> getCategoryFilter() {
        String queryString = """
                SELECT
                    c.id,
                    c.name,
                    c.image,
                    JSON_ARRAYAGG(JSON_OBJECT('id', BIN_TO_UUID(c2.id), 'name', c2.name, 'image', c2.image)) as children
                FROM category c
                join category c2 on c2.category_id = c.id
                GROUP by c.id
                    """;

        Query nativeQuery = entityManager.createNativeQuery(queryString, CategoryFilterProductDto.class);

        return nativeQuery.getResultList();

    }

    @SuppressWarnings("unchecked")
    public List<CategoryFilterProductDto> getCategoryFilter(UUID categoryId) {
        String queryString = """
                SELECT
                    c.id,
                    c.name,
                    c.image,
                    CASE
                        WHEN COUNT(c2.id) = 0 THEN '[]'
                        ELSE JSON_ARRAYAGG(
                            JSON_OBJECT('id', BIN_TO_UUID(c2.id), 'name', c2.name, 'image', c2.image)
                        )
                    END as children
                FROM category c
                left join category c2 on c2.category_id = c.id
                where c.category_id = :categoryId
                GROUP by c.id
                    """;

        Query nativeQuery = entityManager.createNativeQuery(queryString, CategoryFilterProductDto.class)
                .setParameter("categoryId", categoryId);

        return nativeQuery.getResultList();

    }

}

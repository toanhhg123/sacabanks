package com.project.sacabank.web.home;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.sacabank.web.home.dto.ProductDtoHomeQuery;
import com.project.sacabank.web.home.dto.SupplierDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HomeService {
    private final EntityManager entityManager;

    public List<ProductDtoHomeQuery> getProductHome() {

        String sqlString = """
                SELECT NEW com.project.sacabank.web.home.dto.ProductDtoHomeQuery(
                    p.id,
                    p.title,
                    p.slug,
                    u.username,
                    p.mainPhoto,
                    p.price)
                FROM Product p
                LEFT JOIN User u on p.userId = u.id
                """;

        return entityManager.createQuery(sqlString, ProductDtoHomeQuery.class)
                .setMaxResults(8)
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

        return (List<SupplierDto>) nativeQuery.getResultList();

    }

}

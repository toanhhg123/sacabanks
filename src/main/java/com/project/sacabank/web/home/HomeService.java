package com.project.sacabank.web.home;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.sacabank.web.home.dto.ProductDtoHomeQuery;

import jakarta.persistence.EntityManager;
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

}

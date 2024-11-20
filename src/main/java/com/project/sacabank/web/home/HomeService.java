package com.project.sacabank.web.home;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.project.sacabank.productCompare.ProductCompare;
import com.project.sacabank.productCompare.ProductCompareRepository;
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
    private final ProductCompareRepository productCompareRepository;

    public List<ProductDtoHomeQuery> getProductPreview(int limit) {
        String sqlString = """
                SELECT NEW com.project.sacabank.web.home.dto.ProductDtoHomeQuery(
                    p.id,
                    p.title,
                    p.slug,
                    u.shortNameCompany,
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
                    u.shortNameCompany,
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
                    u.shortNameCompany,
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
                    u.shortNameCompany,
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

    @SuppressWarnings("unchecked")
    public List<ProductDtoHomeQuery> getProductsRelated(UUID productId) {
        String queryString = """
                SELECT DISTINCT
                    p2.id,
                    p2.title,
                    p2.slug,
                    u.short_name_company as user_provider_name,
                    p2.main_photo,
                    p2.price,
                    p2.tags
                FROM (
                    SELECT DISTINCT
                        cp.category_id AS category_id
                    FROM product p
                    JOIN category_product cp
                    ON cp.product_id = p.id
                    WHERE p.id = :productId
                ) AS tbl_category
                JOIN category_product cp2 ON cp2.category_id = tbl_category.category_id
                JOIN product p2 ON p2.id = cp2.product_id
                JOIN `user` u ON u.id = p2.user_id
                LIMIT 8
                              """;

        Query nativeQuery = entityManager.createNativeQuery(
                queryString,
                ProductDtoHomeQuery.class)
                .setParameter("productId", productId);

        return nativeQuery.getResultList();

    }

    public byte[] exportCompareProduct(UUID userId) throws IOException {
        var productCompares = productCompareRepository.findByUserId(userId);

        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet("trinh-vat-lieu");

        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(sheet, row, 0, "Tên", style);
        createCell(sheet, row, 1, "Nhà cung cấp", style);
        createCell(sheet, row, 2, "Giá", style);
        createCell(sheet, row, 3, "Số lượng", style);
        createCell(sheet, row, 4, "Chất liệu", style);
        createCell(sheet, row, 5, "Hoàn thiện", style);
        createCell(sheet, row, 6, "Chiều dài", style);
        createCell(sheet, row, 7, "Chiều rộng", style);
        createCell(sheet, row, 8, "Chiều cao", style);
        createCell(sheet, row, 9, "Khối lượng", style);
        createCell(sheet, row, 10, "Mô tả", style);

        writeExeclProductCompare(workbook, sheet, productCompares);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();

    }

    private void writeExeclProductCompare(
            XSSFWorkbook workbook,
            XSSFSheet sheet,
            List<ProductCompare> productCompares) {

        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (var productCompare : productCompares) {
            Row row = sheet.createRow(rowCount++);
            var product = productCompare.getProduct();
            DecimalFormat formatter = new DecimalFormat("#,###");

            createCell(sheet, row, 0, product.getTitle(), style);
            createCell(sheet, row, 1, product.getUser().getShortNameCompany(), style);
            createCell(sheet, row, 2, formatter.format(product.getPrice()), style);
            createCell(sheet, row, 3, product.getQuantity(), style);
            createCell(sheet, row, 4, product.getMaterial(), style);
            createCell(sheet, row, 5, product.getFinishing(), style);
            createCell(sheet, row, 6, product.getDimensionL().toString(), style);
            createCell(sheet, row, 7, product.getDimensionW().toString(), style);
            createCell(sheet, row, 8, product.getDimensionH().toString(), style);
            createCell(sheet, row, 9, product.getNetWeight().toString(), style);
            createCell(sheet, row, 10, product.getDesc(), style);
        }
    }

    private void createCell(XSSFSheet sheet, Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style);
    }

}

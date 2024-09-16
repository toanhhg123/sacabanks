package com.project.sacabank.ProductDocument;

import static com.project.sacabank.utils.Constants.PAGE_SIZE;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.project.sacabank.base.BaseDto;
import com.project.sacabank.base.BaseService;
import com.project.sacabank.base.FullRepo;
import com.project.sacabank.base.PaginationResponse;
import com.project.sacabank.file.FilesStorageService;

@Service
public class ProductDocumentService extends BaseService<ProductDocumentModel> {

    @Autowired
    FilesStorageService storageService;

    public ProductDocumentService(FullRepo fullRepo) {
        super.InJectRepository(fullRepo.productDocumentRepository);
    }

    public PaginationResponse gets(
            Optional<UUID> productId,
            Optional<Integer> page,
            Optional<Integer> limit) {

        Specification<ProductDocumentModel> spec = Specification.where(null);
        var pageNumber = page.isPresent() && page.get() > 0 ? page.get() - 1 : 0;
        var size = limit.isPresent() ? limit.get() : PAGE_SIZE;

        if (productId.isPresent()) {
            spec = spec.and(ProductDocumentSpecifications.equalByProductId(productId.get()));
        }

        var count = nBaseRepository.count(spec);
        var list = nBaseRepository.findAll(spec, PageRequest.of(pageNumber, size));
        var totalPage = (int) Math.ceil((double) count / size);

        return PaginationResponse.builder().totalPage(totalPage).count(count).list(list).build();

    }

    @Override
    public ProductDocumentModel create(BaseDto dto) {
        ProductDocumentDto vendorDocumentDto = (ProductDocumentDto) dto;

        if (vendorDocumentDto.getFileData() != null) {
            String linkFile = storageService.save(vendorDocumentDto.getFileData());
            vendorDocumentDto.setFile(linkFile);
        }

        return super.create(vendorDocumentDto);
    }

    @Override
    public ProductDocumentModel update(UUID id, BaseDto baseDto) {
        ProductDocumentDto vendorDocumentDto = (ProductDocumentDto) baseDto;

        if (vendorDocumentDto.getFileData() != null) {
            String linkFile = storageService.save(vendorDocumentDto.getFileData());
            vendorDocumentDto.setFile(linkFile);
        }

        return super.update(id, baseDto);
    }

}

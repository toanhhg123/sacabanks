package com.project.sacabank.vendorDocument;

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
public class VendorDocumentService extends BaseService<VendorDocumentModel> {

    @Autowired
    FilesStorageService storageService;

    public VendorDocumentService(FullRepo fullRepo) {
        super.InJectRepository(fullRepo.vendorDocumentRepository);
    }

    public PaginationResponse gets(
            Optional<UUID> userId,
            Optional<Integer> page,
            Optional<Integer> limit) {

        Specification<VendorDocumentModel> spec = Specification.where(null);
        var pageNumber = page.isPresent() && page.get() > 0 ? page.get() - 1 : 0;
        var size = limit.isPresent() ? limit.get() : PAGE_SIZE;

        if (userId.isPresent()) {
            spec = spec.and(VendorDocumentSpecifications.equalUserId(userId.get()));
        }

        var count = nBaseRepository.count(spec);
        var list = nBaseRepository.findAll(spec, PageRequest.of(pageNumber, size));
        var totalPage = (int) Math.ceil((double) count / size);

        return PaginationResponse.builder().totalPage(totalPage).count(count).list(list).build();

    }

    @Override
    public VendorDocumentModel create(BaseDto dto) {
        VendorDocumentDto vendorDocumentDto = (VendorDocumentDto) dto;

        if (vendorDocumentDto.getFileData() != null) {
            String linkFile = storageService.save(vendorDocumentDto.getFileData());
            vendorDocumentDto.setFile(linkFile);
        }

        return super.create(vendorDocumentDto);
    }

    @Override
    public VendorDocumentModel update(UUID id, BaseDto baseDto) {
        VendorDocumentDto vendorDocumentDto = (VendorDocumentDto) baseDto;

        if (vendorDocumentDto.getFileData() != null) {
            String linkFile = storageService.save(vendorDocumentDto.getFileData());
            vendorDocumentDto.setFile(linkFile);
        }

        return super.update(id, baseDto);
    }

}

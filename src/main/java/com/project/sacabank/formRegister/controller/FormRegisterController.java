package com.project.sacabank.formRegister.controller;

import static com.project.sacabank.utils.Constants.API_REGISTER_VENDOR_PATH;
import static com.project.sacabank.utils.Constants.PAGE_SIZE;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.sacabank.base.BaseController;
import com.project.sacabank.base.PaginationResponse;
import com.project.sacabank.exception.CustomException;
import com.project.sacabank.exception.ResourceNotFoundException;
import com.project.sacabank.formRegister.FormRegisterSpecifications;
import com.project.sacabank.formRegister.dto.FormRegisterDto;
import com.project.sacabank.formRegister.model.FormRegister;
import com.project.sacabank.formRegister.repository.FormRegisterRepository;
import com.project.sacabank.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping(path = API_REGISTER_VENDOR_PATH)
public class FormRegisterController extends BaseController {

  @Autowired
  FormRegisterRepository formRegisterRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  private ModelMapper mapper;

  @GetMapping("")
  public ResponseEntity<?> gets(
      @RequestParam(defaultValue = "") String search,
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam Optional<Integer> limit) {
    Specification<FormRegister> spec = Specification.where(null);

    var pageNumber = page > 0 ? page - 1 : 0;
    var size = limit.isPresent() ? limit.get() : PAGE_SIZE;

    if (!search.isEmpty()) {
      spec = spec.or(FormRegisterSpecifications.companyGroupIsLike(search));
      spec = spec.or(FormRegisterSpecifications.phoneIsLike(search));
      spec = spec.or(FormRegisterSpecifications.emailIsLike(search));
      spec = spec.or(FormRegisterSpecifications.companyNameIsLike(search));
    }

    var count = formRegisterRepository.count(spec);
    var list = formRegisterRepository.findAll(spec, PageRequest.of(pageNumber, size));
    var totalPage = (int) Math.ceil((double) count / size);

    var data = PaginationResponse.builder().totalPage(totalPage).count(count).list(list).build();

    return this.onSuccess(data);
  }

  @GetMapping("{id}")
  public ResponseEntity<?> findById(@PathVariable("id") UUID id) {
    var formRegister = formRegisterRepository.findById(id);
    if (formRegister.isEmpty())
      throw new CustomException("not found form register");

    return this.onSuccess(formRegister.get());
  }

  @PostMapping("/register")
  public ResponseEntity<?> create(@RequestBody FormRegisterDto formRegisterCreate) {
    var formRegister = mapper.map(formRegisterCreate, FormRegister.class);

    boolean existsByEmail = userRepository.existsByEmail(formRegister.getEmail());
    boolean existsByPhoneNumber = userRepository.existsByPhoneNumber(formRegister.getPhone());

    boolean existsByPhoneOrEmail = formRegisterRepository.existsByPhoneOrEmail(formRegister.getPhone(),
        formRegister.getEmail());

    if (existsByEmail || existsByPhoneOrEmail) {
      CustomException.throwError("Địa chỉ email đã tồn tại");
    }

    if (existsByPhoneNumber) {
      CustomException.throwError("Số điện thoại đã tồn tại");
    }
    var data = formRegisterRepository.save(formRegister);
    return this.onSuccess(data);
  }

  @Transactional
  @PatchMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody FormRegisterDto formRegisterDto) {

    FormRegister formRegister = formRegisterRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("form resgiter not found"));

    formRegister.setCompanyName(formRegisterDto.getCompanyName());
    formRegister.setAddress(formRegisterDto.getAddress());
    formRegister.setEmail(formRegisterDto.getEmail());
    formRegister.setPhone(formRegisterDto.getPhone());
    formRegister.setFullNameOwnerCompany(formRegisterDto.getFullNameOwnerCompany());
    formRegister.setCompanyGroup(formRegisterDto.getCompanyGroup());
    formRegister.setMainProductGroup(formRegisterDto.getMainProductGroup());
    formRegister.setNumberProductService(formRegisterDto.getNumberProductService());
    formRegister.setRevenueEachYear(formRegisterDto.getRevenueEachYear());
    formRegister.setDescription(formRegisterDto.getDescription());
    formRegister.setListCertificate(formRegisterDto.getListCertificate());
    formRegister.setListLinkProduct(formRegisterDto.getListLinkProduct());
    formRegister.setCompanyWishesCooperate(formRegisterDto.getCompanyWishesCooperate());
    formRegister.setLinkProfile(formRegisterDto.getLinkProfile());
    formRegister.setImplementerName(formRegisterDto.getImplementerName());
    formRegister.setImplementerPhone(formRegisterDto.getImplementerPhone());
    formRegister.setLinkWebsite(formRegisterDto.getLinkWebsite());
    formRegister.setActive(formRegisterDto.getIsActive());
    formRegister.setCollab(formRegisterDto.getCollab());
    formRegister.setProfits(formRegisterDto.getProfits());

    return this.onSuccess(formRegisterRepository.save(formRegister));
  }

  @Transactional
  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
    FormRegister formRegister = formRegisterRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("form register not found"));
    formRegisterRepository.delete(formRegister);
    return this.onSuccess("");
  }

}

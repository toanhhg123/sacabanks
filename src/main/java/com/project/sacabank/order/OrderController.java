package com.project.sacabank.order;

import static com.project.sacabank.utils.Constants.API_ORDER_PATH;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.sacabank.base.BaseController;
import com.project.sacabank.base.ResponseObject;
import com.project.sacabank.order.dto.OrderDto;
import com.project.sacabank.user.model.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = API_ORDER_PATH)
@RequiredArgsConstructor
public class OrderController extends BaseController {

  private final OrderService service;

  @GetMapping("")
  public ResponseEntity<ResponseObject> gets(@RequestParam Optional<Integer> page,
      @RequestParam Optional<Integer> pageSize) {

    var userId = this.getUserInfo().getId();
    var isAdmin = this.isManager();

    return this
        .onSuccess(service.getPagination(Boolean.TRUE.equals(isAdmin) ? Optional.empty() : Optional.of(userId), page,
            pageSize));
  }

  @GetMapping("/my_order")
  public ResponseEntity<ResponseObject> getMyOrder(@RequestParam Optional<Integer> page) {
    User user = this.getUserInfo();
    return this.onSuccess(service.getByUserId(user.getId(), page));
  }

  @PostMapping("")
  public ResponseEntity<ResponseObject> create(@RequestBody OrderDto orderDto) {
    User user = this.getUserInfo();
    orderDto.setUserId(user.getId());
    return this.onSuccess(service.create(orderDto));
  }

  @PostMapping("/add_all_cart")
  public ResponseEntity<ResponseObject> addAllCart() {
    User user = this.getUserInfo();
    return this.onSuccess(service.addOrderAllCart(user.getId()));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<ResponseObject> delete(@PathVariable("id") UUID id) {
    return this.onSuccess(service.delete(id));
  }

}

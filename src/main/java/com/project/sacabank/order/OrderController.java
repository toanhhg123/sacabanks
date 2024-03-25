package com.project.sacabank.order;

import static com.project.sacabank.utils.Constants.API_ORDER_PATH;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.project.sacabank.order.dto.OrderDto;
import com.project.sacabank.user.model.User;

@RestController
@RequestMapping(path = API_ORDER_PATH)
public class OrderController extends BaseController {
  @Autowired
  OrderService service;

  @GetMapping("")
  public ResponseEntity<?> gets(@RequestParam Optional<Integer> page) {
    return this.onSuccess(service.gets(page));
  }

  @GetMapping("/my_order")
  public ResponseEntity<?> getMyOrder(@RequestParam Optional<Integer> page) {
    User user = this.getUserInfo();
    return this.onSuccess(service.getByUserId(user.getId(), page));
  }

  @PostMapping("")
  public ResponseEntity<?> create(@RequestBody OrderDto orderDto) {
    User user = this.getUserInfo();
    orderDto.setUserId(user.getId());
    return this.onSuccess(service.create(orderDto));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
    return this.onSuccess(service.delete(id));
  }

}

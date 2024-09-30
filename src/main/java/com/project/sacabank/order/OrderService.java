package com.project.sacabank.order;

import static com.project.sacabank.utils.Constants.PAGE_SIZE;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.sacabank.base.PaginationResponse;
import com.project.sacabank.exception.CustomException;
import com.project.sacabank.order.dto.OrderDto;
import com.project.sacabank.order.model.Order;
import com.project.sacabank.order.model.OrderItem;
import com.project.sacabank.order.repository.OrderItemRepository;
import com.project.sacabank.order.repository.OrderRepository;
import com.project.sacabank.product.model.Product;
import com.project.sacabank.product.repository.ProductRepository;
import com.project.sacabank.user.repository.UserRepository;

@SuppressWarnings("null")
@Service
public class OrderService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  OrderRepository orderRepository;

  @Autowired
  OrderItemRepository orderItemRepository;

  @Autowired
  ProductRepository productRepository;

  public List<Order> gets(Optional<Integer> page) {
    Specification<Order> spec = Specification.where(null);
    var pageNumber = page.isPresent() ? page.get() : 0;
    return orderRepository.findAll(spec, PageRequest.of(pageNumber, PAGE_SIZE));
  }

  public PaginationResponse getByUserId(Optional<UUID> userId, Optional<Integer> page, Optional<Integer> pageSize) {
    var pageNumber = page.isPresent() && page.get() > 0 ? page.get() - 1 : 0;
    var size = pageSize.isPresent() ? pageSize.get() : PAGE_SIZE;
    Pageable pageable = PageRequest.of(pageNumber, size);

    Specification<Order> spec = Specification.where(null);
    if (userId.isPresent()) {
      spec = spec.and(OrderSpecifications.userIdIsEqual(userId.get()));
    }

    var count = orderRepository.count(spec);
    var list = orderRepository.findAll(spec, pageable);
    var totalPage = (int) Math.ceil((double) count / size);

    return PaginationResponse.builder().totalPage(totalPage).count(count).list(list).build();
  }

  public List<Order> getByUserId(UUID userId, Optional<Integer> page) {
    Specification<Order> spec = Specification.where(null);
    spec = spec.and(OrderSpecifications.userIdIsEqual(userId));
    var pageNumber = page.isPresent() ? page.get() : 0;
    return orderRepository.findAll(spec, PageRequest.of(pageNumber, PAGE_SIZE));
  }

  @Transactional
  public Order create(OrderDto orderDto) {
    var user = userRepository.findById(orderDto.getUserId());
    if (!user.isPresent()) {
      throw new CustomException("not found user");
    }

    Order order = orderRepository.save(Order.builder().user(user.get()).build());

    var orderItems = orderDto.getOrderItemDtos().stream().map((var orderItemDto) -> {
      var product = productRepository.findById(orderItemDto.getProductId());
      return OrderItem.builder().product(product.get()).quantity(orderItemDto.getQuantity()).order(order).build();
    }).collect(Collectors.toList());

    orderItemRepository.saveAll(orderItems);

    return order;
  }

  public Order delete(UUID id) {

    var order = orderRepository.findById(id);

    if (!order.isPresent()) {
      throw new CustomException("not found order");
    }

    orderItemRepository.deleteByOrder_id(id);
    orderRepository.delete(order.get());

    return order.get();
  }
}

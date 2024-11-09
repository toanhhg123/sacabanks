package com.project.sacabank.web.home;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.sacabank.base.BaseController;
import com.project.sacabank.base.PaginationResponse;
import com.project.sacabank.blog.BlogRepository;
import com.project.sacabank.blog.BlogService;
import com.project.sacabank.cart.CartService;
import com.project.sacabank.cart.dto.CartDto;
import com.project.sacabank.exception.CustomException;
import com.project.sacabank.product.model.Product;
import com.project.sacabank.product.service.ProductService;
import com.project.sacabank.repositories.CategoryRepository;
import com.project.sacabank.user.model.User;
import com.project.sacabank.user.repository.UserRepository;
import com.project.sacabank.user.service.UserService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class HomeController extends BaseController {

    private final HomeService homeService;
    private final BlogRepository blogRepository;
    private final CartService cartService;
    private final CategoryRepository categoryRepository;
    private final ProductService productService;
    private final UserService userService;
    private final BlogService blogService;
    private final UserRepository userRepository;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        var products = homeService.getProductHome();
        var blogs = blogRepository.findAll(Specification.where(null), PageRequest.of(0, 8));
        var vendors = userService.getAllUserVendor("", Optional.empty(), 10);

        model.addAttribute("products", products);
        model.addAttribute("blogs", blogs);
        model.addAttribute("vendors", vendors);

        return "home";
    }

    @GetMapping("/home")
    public String viewHome() {
        return "home";
    }

    @GetMapping("/san-pham")
    public String viewProduct(@RequestParam(name = "categoryId", required = false) UUID categoryId,
            @RequestParam(name = "type", required = false) String type,
            @RequestParam Optional<String> search,
            @RequestParam(defaultValue = "1") Optional<Integer> page,
            @RequestParam Optional<Integer> limit,
            @RequestParam Optional<UUID> userId,
            @RequestParam Optional<Boolean> isNullQuantity,
            Model model) {

        var categories = categoryRepository.findByCategoryId(categoryId);
        var products = productService.getAll(
                search,
                page,
                limit,
                Optional.ofNullable(categoryId), userId,
                Optional.ofNullable(type != null && type.equals("warehouse")));
        var vendors = userService.getAllUserVendor("", Optional.empty(), 3);

        model.addAttribute("categories", categories);
        model.addAttribute("products", products.getList());
        model.addAttribute("totalPages", products.getTotalPage());
        model.addAttribute("currentPage", page.get());
        model.addAttribute("vendors", vendors);

        return "product";
    }

    @GetMapping("/san-pham/{slug}")
    public String getProductDetails(@PathVariable String slug, Model model) {

        Product product = productService.findBySlug(slug);
        var products = homeService.getProductHome();

        model.addAttribute("product", product);
        model.addAttribute("products", products);

        return "product-details";
    }

    @GetMapping("/bai-viet")
    public String getBlogs(Model model) {
        var blogs = blogService.getAll();
        model.addAttribute("blogs", blogs);

        return "blog";
    }

    @GetMapping("/bai-viet/{slug}")
    public String getBlogs(@PathVariable String slug, Model model) {
        var blog = blogService.getBySlug(slug);
        var blogs = blogService.getAll();

        model.addAttribute("blogs", blogs);
        model.addAttribute("blog", blog);

        return "blog-details";
    }

    @GetMapping("/gio-hang")
    public String viewCart(Model model) {

        var carts = cartService.getCartPagination(
                getUserServiceInfo().getId(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty());

        model.addAttribute("carts", carts.getList());

        return "cart";
    }

    @PostMapping("/gio-hang/add")
    public String viewCart(CartDto cartDto, Model model) {

        cartDto.setUserId(getUserServiceInfo().getId());
        cartService.create(cartDto);

        return "redirect:/gio-hang";
    }

    @PostMapping("/gio-hang/remove/{id}")
    public String viewCart(@PathVariable UUID id) {

        cartService.removeById(id);

        return "redirect:/gio-hang";
    }

    @GetMapping("/nha-cung-cap")
    public String viewSupplier(Model model) {

        var suppliers = homeService.getSupplierGroupFirstName();

        model.addAttribute("suppliers", suppliers);

        return "supplier";

    }

    @GetMapping("/nha-cung-cap/{id}")
    public Object viewSupplierDetails(Model model, @PathVariable UUID id) {

        User user = userRepository.findById(id).orElseThrow(() -> new CustomException("Khong tim thay"));
        PaginationResponse products = productService.getByUserId(user, Optional.empty(), Optional.empty(),
                Optional.empty());

        model.addAttribute("user", user);
        model.addAttribute("products", products.getList());

        return products;

    }

}

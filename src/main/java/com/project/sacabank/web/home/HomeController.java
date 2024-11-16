package com.project.sacabank.web.home;

import java.util.List;
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
import com.project.sacabank.blog.BlogRepository;
import com.project.sacabank.blog.BlogService;
import com.project.sacabank.cart.CartService;
import com.project.sacabank.cart.dto.CartDto;
import com.project.sacabank.exception.CustomException;
import com.project.sacabank.product.model.Product;
import com.project.sacabank.product.service.ProductService;
import com.project.sacabank.productCompare.ProductCompare;
import com.project.sacabank.productCompare.ProductCompareRepository;
import com.project.sacabank.repositories.CategoryRepository;
import com.project.sacabank.user.model.User;
import com.project.sacabank.user.service.UserService;
import com.project.sacabank.wishlist.WishlistModel;
import com.project.sacabank.wishlist.WishlistRepository;
import com.project.sacabank.wishlist.WishlistService;
import com.project.sacabank.wishlist.dto.WishlistDto;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@AllArgsConstructor
@Slf4j
public class HomeController extends BaseController {

    private final int DEFAULT_NUMBER_ITEM = 8;

    private final HomeService homeService;
    private final BlogRepository blogRepository;
    private final CartService cartService;
    private final CategoryRepository categoryRepository;
    private final ProductService productService;
    private final UserService userService;
    private final BlogService blogService;
    private final ProductCompareRepository productCompareRepository;
    private final WishlistService wishlistService;
    private final WishlistRepository wishlistRepository;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        var products = homeService.getProductHome();
        var productsNew = homeService.getProductNew(DEFAULT_NUMBER_ITEM);
        var productsHot = homeService.getProductHot(DEFAULT_NUMBER_ITEM);
        var productsBestSeller = homeService.getProductBestSeller(DEFAULT_NUMBER_ITEM);

        var blogs = blogRepository.findAll(Specification.where(null), PageRequest.of(0, 8));
        var vendors = userService.getAllUserVendor("", Optional.empty(), 10);

        model.addAttribute("products", products);
        model.addAttribute("blogs", blogs);
        model.addAttribute("vendors", vendors);

        model.addAttribute("productsNew", productsNew);
        model.addAttribute("productsHot", productsHot);
        model.addAttribute("productsBestSeller", productsBestSeller);

        return "home";
    }

    @GetMapping("/home")
    public String viewHome() {
        return "home";
    }

    @GetMapping("/san-pham")
    public String viewProduct(
            @RequestParam(name = "categoryId", required = false) UUID categoryId,
            @RequestParam(name = "type", required = false) String type,
            @RequestParam Optional<String> search,
            @RequestParam(defaultValue = "1") Optional<Integer> page,
            @RequestParam Optional<Integer> limit,
            @RequestParam Optional<UUID> userId,
            @RequestParam Optional<Boolean> isNullQuantity,
            Model model) {

        var categories = categoryId != null
                ? homeService.getCategoryFilter(categoryId)
                : homeService.getCategoryFilter();

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
    public String viewSupplierDetails(Model model, @PathVariable UUID id) {

        User user = userRepository.findById(id).orElseThrow(() -> new CustomException("Khong tim thay"));

        if (user.getBanner() == null || user.getBanner().isEmpty())
            user.setBanner("static/assets/img/hero/hero-2.png");

        var products = homeService.getProductHome(user.getId());
        var productsNew = products.stream().filter(p -> p.getTags().contains("NEW")).toList();
        var productsHot = products.stream().filter(p -> p.getTags().contains("HOT")).toList();
        var productsBestSeller = products.stream().filter(p -> p.getTags().contains("BESTSELLER")).toList();

        model.addAttribute("user", user);
        model.addAttribute("products", products);
        model.addAttribute("productsNew", productsNew);
        model.addAttribute("productsHot", productsHot);
        model.addAttribute("productsBestSeller", productsBestSeller);

        log.info("banner is: {}", user.getBanner());

        return "supplier-details";

    }

    @PostMapping("/compare/{productId}")
    public String addProductCompare(@PathVariable UUID productId) {

        var isExist = productCompareRepository.existsByUserIdAndProductId(getUserServiceInfo().getId(), productId);

        if (!isExist) {

            ProductCompare productCompare = ProductCompare.builder()
                    .userId(getUserServiceInfo().getId())
                    .productId(productId)
                    .build();

            productCompareRepository.save(productCompare);

        }

        return "redirect:/trinh-vat-lieu";
    }

    @GetMapping("/trinh-vat-lieu")
    public String viewCompare(Model model) {

        var productCompares = productCompareRepository.findByUserId(getUserServiceInfo().getId());

        model.addAttribute("productCompares", productCompares);

        return "compare-product";

    }

    @PostMapping("/trinh-vat-lieu/delete/{id}")
    public String deleteProductCompare(@PathVariable UUID id) {

        productCompareRepository.findById(id)
                .ifPresent(productCompareRepository::delete);

        return "redirect:/trinh-vat-lieu";

    }

    @GetMapping("/chuong-trinh-khuyen-mai")
    public String viewPromotional(Model model) {

        var products = homeService.getProductHome();
        model.addAttribute("products", products);

        return "promotional";

    }

    @GetMapping("/yeu-thich")
    public String viewWishList(Model model) {

        var productWishList = wishlistService.getWishlistPagination(
                getUserServiceInfo().getId(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(100));

        var list = (List<WishlistModel>) productWishList.getList();
        var products = list.stream().map(WishlistModel::getProduct);

        model.addAttribute("products", products);

        return "wish-list";

    }

    @PostMapping("/yeu-thich/delete/{id}")
    public String deleteWishList(@PathVariable UUID id) {

        wishlistRepository.removeByProductIdAndUserId(id, getUserInfo().getId());

        return "redirect:/yeu-thich";

    }

    @PostMapping("/yeu-thich/{id}")
    public String addWishList(@PathVariable UUID id) {

        WishlistDto wishlistDto = WishlistDto
                .builder()
                .userId(getUserInfo().getId())
                .productId(id)
                .build();

        wishlistService.create(wishlistDto);

        return "redirect:/yeu-thich";

    }

}

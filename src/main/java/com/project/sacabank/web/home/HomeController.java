package com.project.sacabank.web.home;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.sacabank.ProductDocument.ProductDocumentService;
import com.project.sacabank.base.BaseController;
import com.project.sacabank.base.PaginationResponse;
import com.project.sacabank.blog.BlogModel;
import com.project.sacabank.blog.BlogRepository;
import com.project.sacabank.blog.BlogService;
import com.project.sacabank.cart.CartService;
import com.project.sacabank.cart.dto.CartDto;
import com.project.sacabank.exception.CustomException;
import com.project.sacabank.order.OrderService;
import com.project.sacabank.product.model.Product;
import com.project.sacabank.product.repository.ProductRepository;
import com.project.sacabank.product.service.ProductService;
import com.project.sacabank.productComment.ProductCommentModel;
import com.project.sacabank.productComment.ProductCommentService;
import com.project.sacabank.productComment.dto.ProductCommentDto;
import com.project.sacabank.productComment.dto.ProductCommentQueryDto;
import com.project.sacabank.productCompare.ProductCompare;
import com.project.sacabank.productCompare.ProductCompareRepository;
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
    private final ProductService productService;
    private final UserService userService;
    private final BlogService blogService;
    private final ProductCompareRepository productCompareRepository;
    private final WishlistService wishlistService;
    private final WishlistRepository wishlistRepository;
    private final OrderService orderService;
    private final ProductDocumentService productDocumentService;
    private final ProductCommentService productCommentService;
    private final ProductRepository productRepository;

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
        var productsRelated = homeService.getProductsRelated(product.getId());
        PaginationResponse productDocuments = productDocumentService.gets(
                Optional.of(product.getId()),
                Optional.of(0),
                Optional.of(100));
        List<ProductCommentQueryDto> productComments = productCommentService
                .getByProductIdActive(product.getId());

        model.addAttribute("product", product);
        model.addAttribute("products", productsRelated);
        model.addAttribute("productDocuments", productDocuments.getList());
        model.addAttribute("productComments", productComments);

        return "product-details";
    }

    @PostMapping("/product-comment")
    public String addProductComment(ProductCommentDto productCommentDto) {

        productCommentDto.setUserId(getUserInfo().getId());
        productCommentDto.setIsActive(false);
        ProductCommentModel productComment = productCommentService.create(productCommentDto);

        Product product = productRepository
                .findById(productComment.getProductId())
                .orElseThrow(() -> new CustomException(""));

        return "redirect:/san-pham/" + product.getSlug();
    }

    @GetMapping("/bai-viet")
    public String getBlogs(Model model) {
        var blogs = blogService.getAll();
        model.addAttribute("blogs", blogs);

        return "blog";
    }

    @GetMapping("/bai-viet/{slug}")
    public String getBlogs(@PathVariable String slug, Model model, @RequestParam int index) {
        var blog = blogService.getBySlug(slug);
        var blogs = blogService.getAll();

        model.addAttribute("blogs", blogs);
        model.addAttribute("blog", blog);
        model.addAttribute("index", index);

        return "blog-details";
    }

    @GetMapping("/bai-viet/index/{index}")
    public String getBlogSkipIndex(@PathVariable(required = true) int index) {
        var count = blogRepository.count();

        if (index >= count) {
            index = 0;
        }

        PageRequest pageable = PageRequest.of(index, 1);
        Page<BlogModel> blogPage = blogRepository.findAll(pageable);

        BlogModel blog = blogPage.getContent().get(0);

        return "redirect:/bai-viet/" + blog.getSlug() + "?index=" + index;

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

    @GetMapping("/trinh-vat-lieu/download")
    public ResponseEntity<ByteArrayResource> compareDownload(Model model) throws IOException {

        byte[] fileData = homeService.exportCompareProduct(getUserInfo().getId());

        ByteArrayResource resource = new ByteArrayResource(fileData);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=product-compare.xlsx");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body(resource);

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

    @PostMapping("/xac-nhan-hoa-don")
    public String acceptOrder() {

        orderService.addOrderAllCart(getUserServiceInfo().getId());

        return "redirect:/xac-nhan-hoa-don-thanh-cong";

    }

    @GetMapping("/xac-nhan-hoa-don-thanh-cong")
    public String orderSuccess() {

        return "order-success";

    }

}

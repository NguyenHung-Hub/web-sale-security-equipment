package com.metan.websalesecurityequipment.controller;

import com.metan.websalesecurityequipment.model.*;
import com.metan.websalesecurityequipment.model.request.OrderRequest;
import com.metan.websalesecurityequipment.service.*;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.Normalizer;
import java.util.*;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/dashboard")
public class Dashboard {

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    private final OrderService orderService;
    private final AwsService awsService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;

    public Dashboard(OrderService orderService, AwsService awsService, ProductService productService,
                     CategoryService categoryService, BrandService brandService) {
        this.orderService = orderService;
        this.awsService = awsService;
        this.productService = productService;
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    @GetMapping
    public String dashboard(Model model) {

        return "redirect:/dashboard/product";
    }

    @GetMapping("/product")
    public String product(Model model, HttpServletRequest request, RedirectAttributes redirect) {
        Product product = new Product();
        model.addAttribute("product", product);

        List<Product> products = productService.findAll();
        model.addAttribute("products", products);

        List<Category> categories = categoryService.findAllParentCategory();
        model.addAttribute("categories", categories);

        List<Category> categories2 = new ArrayList<>();


        List<Brand> brands = brandService.findAll();
        model.addAttribute("brands", brands);

        System.out.println("sub");
        for (Category c :
                categories) {
            for (Category c2 :
                    c.getCategories()) {
                System.out.println("out:   " + c2.getName());

            }
        }

        request.getSession().setAttribute("productlist", null);
        if (model.asMap().get("success") != null)
            redirect.addFlashAttribute("success", model.asMap().get("success").toString());

        return "redirect:/dashboard/product/page/1";
    }

    @GetMapping("/product/page/{pageNumber}")
    public String showEmployeePage(HttpServletRequest request,
                                   @PathVariable int pageNumber, Model model) {
        Product product = new Product();
        model.addAttribute("product", product);

        List<Product> products = productService.findAll();
        model.addAttribute("products", products);

        List<Category> categories = categoryService.findAllParentCategory();
        model.addAttribute("categories", categories);

        List<Category> categories2 = new ArrayList<>();


        List<Brand> brands = brandService.findAll();
        model.addAttribute("brands", brands);

        for (Product p :
                products) {
            System.out.println(p.getProductId());
        }

        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o1.getProductId().compareTo(o2.getProductId());
            }
        });


        PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("productlist");
        int pagesize = 5;
//        List<Employee> list =(List<Employee>) employeeService.findAll();
        System.out.println(products.size());
        if (pages == null) {
            pages = new PagedListHolder<>(products);
            pages.setPageSize(pagesize);
        } else {
            final int goToPage = pageNumber - 1;
            if (goToPage <= pages.getPageCount() && goToPage >= 0) {
                pages.setPage(goToPage);
            }
        }
        request.getSession().setAttribute("productlist", pages);
        int current = pages.getPage() + 1;
        int begin = Math.max(1, current - products.size());
        int end = Math.min(begin + 5, pages.getPageCount());
        int totalPageCount = pages.getPageCount();
        String baseUrl = "/dashboard/product/page/";
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("baseUrl", baseUrl);
        model.addAttribute("productsPage", pages);
        return "product_db";
    }

    @GetMapping("/brand")
    public String brand(Model model) {

        Brand brand = new Brand();
        model.addAttribute("brand", brand);

        List<Brand> brands = brandService.findAll();
        model.addAttribute("brands", brands);

        long newId = brandService.getLastId() + 1;
        model.addAttribute("newId", newId);
        System.out.println("\n\nnewId: " + newId);


        return "brand_db";
    }

    @GetMapping("/orders")
    public String showOrderPage(Model model,
                                @RequestParam(name = "status", required = false, defaultValue = "PROCESSING") String status,
                                @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        Sort sort = Sort.by("modified_at").descending();
        Pageable pageable = PageRequest.of(page, 10, sort);
        Page<Order> orders = orderService.findOrderByStatus(status, pageable);
        model.addAttribute("orders", orders);
        return "order_db";
    }

    @PostMapping("/orders")
    @ResponseBody
    public Page<Order> order(Model model, @RequestBody OrderRequest req) {
        Sort sort = Sort.by("modified_at").descending();
        Pageable pageable = PageRequest.of(req.getCurrentPage(), 10, sort);
        Page<Order> orders = orderService.findOrderByStatus(req.getStatus(), pageable);
        return orders;
    }

    @GetMapping("/orders/{status}/{id}")
    public String setStatusOrder(@PathVariable(name = "status") String status,
                                 @PathVariable(name = "id") String id) {
        System.out.println("hehe chinh ne"+OrderStatus.valueOf(status));
        Order order= orderService.findById(id);
        order.setOrderStatus(OrderStatus.valueOf(status));
        orderService.save(order);
        return "redirect:/dashboard/orders";
    }

    @PostMapping(value = "/product/modal")
    public String saveProduct(@ModelAttribute("product") Product p,
                              @RequestParam("image") MultipartFile img) {
        String fileName = awsService.save(img);
        p.setThumbnail("https://chinh1506.s3.amazonaws.com/" + fileName);
        p.setCategory(categoryService.findCategoryByCategoryId(p.getCategory().getCategoryId()));
        p.setTitle(p.getCategory().getName() + " " + p.getName());
        System.out.println(p.getCategory().getCategoryId());
        p.setCreatedAt(new Date());
        p.setModifiedAt(new Date());

        float discount = p.getDiscountPercentBase() / 100;
        p.setDiscountPercentBase(discount);

        p.setSlug(toSlug(p.getTitle()));
        productService.saveProduct(p);
        System.out.println("Da them");
        return "redirect:/dashboard/product";
    }

    @PostMapping(value = "/product/update")
    public String updateProduct(@ModelAttribute("product") Product p,
                                @RequestParam(name = "image", required = false) MultipartFile img) {
        Product product = productService.findProductById(p.getProductId());
        System.out.println("\t\t\t\t hinh ne:" + img.getOriginalFilename());

        if (!img.getOriginalFilename().trim().equals("")) {
            String fileName = awsService.save(img);
            if (product.getThumbnail() != null && !product.getThumbnail().trim().equals("")) {
                awsService.delete(product.getThumbnail().replace("https://chinh1506.s3.amazonaws.com/", "").trim());
            }
            p.setThumbnail("https://chinh1506.s3.amazonaws.com/" + fileName);
        }
        System.out.println(p);
        product.setThumbnail(p.getThumbnail());
        product.setLongDesc(p.getLongDesc());
        product.setShortDesc(p.getShortDesc());
        product.setName(p.getName());
        product.setPrice(p.getPrice());
        product.setQuantity(p.getQuantity());
        product.setTitle(p.getName() + " " + p.getProductId());
        productService.saveProduct(product);
        System.out.println("Da sua");
        return "redirect:/dashboard/product";
    }

    public static String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        String minute = String.valueOf(new Date().getMinutes());
        slug += "-" + minute;
        return slug.toLowerCase(Locale.ENGLISH);
    }

    @GetMapping(value = "/product/{id}/delete")
    public String deleteProduct(@PathVariable("id") String id) {
        System.out.println("\n\n\n delete: " + id);

        productService.deleteProduct(productService.findProductById(id));
        return "redirect:/dashboard/product";
    }


}

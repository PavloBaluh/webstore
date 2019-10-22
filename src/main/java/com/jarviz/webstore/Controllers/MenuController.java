package com.jarviz.webstore.Controllers;

import com.jarviz.webstore.Models.Category;
import com.jarviz.webstore.Models.Product;
import com.jarviz.webstore.Models.Property;
import com.jarviz.webstore.Service.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/menu")
public class MenuController {
    private CategoryService categoryService;
    private ProductService productService;
    private GroupService groupService;
    private SubCategoryService subCategoryService;

    @GetMapping("/getCategories")
    public List<Category> getCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/getProductById/{id}")
    public HashMap<String, Object> getProductById (@PathVariable("id") Integer id){
        return productService.getProductById(id);
    }

    @GetMapping("/getProductsByGroup/{group}")
    public List<Product> getProductsByGroup(@PathVariable("group") String groupAndPage) {
        return productService.getProductsByGroup(groupAndPage);
    }

    @GetMapping("/getHierarchy/{group}")
    public List<String> getHierarchy(@PathVariable("group") String group) {
        return groupService.getHierarchyByGroupName(group);
    }

    @PostMapping("/GetSortedProducts")
    public List<Product> GetSortedProducts(@RequestParam("priceFrom") Float priceFrom,
                                           @RequestParam("priceTo") Float priceTo,
                                           @RequestParam("sortDirection") String direction,
                                           @RequestParam("sortBy") String sortBy,
                                           @RequestParam("limit") Integer limit,
                                           @RequestParam("group") String group,
                                           @RequestParam("properties") String properties,
                                           @RequestParam("page") Integer page) {
        return productService.getSortedProducts(priceFrom, priceTo, limit, direction, sortBy, group, properties, page);
    }

    @GetMapping("/GetMinMaxPriceByGroup/{group}")
    public List<Integer> getMinMaxPriceByGroup(@PathVariable("group") String group) {
        return this.productService.getMinMaxPriceByGroup(group);
    }

    @GetMapping("/suchProductsByChars/{charSequence}")
    public List<Product> suchProductsByChars(@PathVariable String charSequence) {
        return this.productService.getProductsByChars(charSequence);
    }

    @GetMapping("/getAllProperties/{sub}")
    public List<Property> getAllProperties(@PathVariable("sub") String subCategory){
        return subCategoryService.getAllPropertiesBySubCategory(subCategory) ;
    }

    @PostMapping("/getProductsCount/{group}")
    public Integer getProductsCount(@PathVariable("group") String group,
                                    Float priceFrom,
                                    Float priceTo,
                                    String properties) {
        return productService.getProductsCount(group,priceFrom,priceTo,properties);
    }
}

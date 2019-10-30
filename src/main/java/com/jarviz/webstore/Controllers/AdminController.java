package com.jarviz.webstore.Controllers;
import com.jarviz.webstore.Models.*;
import com.jarviz.webstore.Service.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private AdminService adminService;
    private OrderEntityService orderEntityService;
    private ProductService productService;
    private CategoryService categoryService;
    private GroupService groupService;
    private SubCategoryService subCategoryService;
    private PropertyService propertyService;
    private PropertyValueService propertyValueService;

    @GetMapping("/gerAllUsers")
    public List<User> getAllUsers() {
        return adminService.getAllUsers();
    }

    @GetMapping("/lockUnlockUser/{id}")
    public User lockUnlockUser(@PathVariable("id") Integer id) {
        return adminService.lockUnlock(id);
    }

    @GetMapping("/getAllOrders")
    public List<Orders> getAllOrders() {
        return this.adminService.getAllOrders();
    }

    @GetMapping("/changeOrderStatus/{info}")
    public void changeOrderStatus(@PathVariable("info") String info) {
        this.orderEntityService.changeOrderStatus(info);
    }

    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts() {
        return productService.getAll();
    }

    @GetMapping("/renameCategory/{data}")
    public boolean renameCategory(@PathVariable String data) throws IOException {
        return categoryService.rename(data);
    }

    @PostMapping("/editSubCategory/{data}")
    public boolean renameSubCategory(@PathVariable String data, MultipartFile picture) throws IOException {
        return subCategoryService.edit(data,picture);
    }

    @PostMapping("/editGroup/{data}")
    public boolean renameGroup(@PathVariable String data,MultipartFile picture) throws IOException {
        return groupService.edit(data,picture);
    }

    @GetMapping("/addProduct/{groupId}")
    public Product renameGroup(@PathVariable Integer groupId) {
        return productService.add(groupId);
    }

    @GetMapping("/getAllProperties")
    public List<Property> getAllProperties (){
        return propertyService.getAll();
    }

    @GetMapping("/addProperty")
    public Property addProperty (){
        return this.propertyService.addNew();
    }

    @GetMapping("/renameProperty/{data}")
    public Boolean renameProperty (@PathVariable("data")String data) throws IOException {
        return this.propertyService.rename(data);
    }

    @GetMapping("/renamePropertyValue/{data}")
    public Boolean renamePropertyValue (@PathVariable("data")String data) throws IOException {
        return this.propertyValueService.rename(data);
    }

    @GetMapping("/addPropertyValue/{id}")
    public PropertyValue addPropertyValue (@PathVariable("id") Integer id){
        return propertyValueService.addNew(id);
    }

    @PostMapping("/addCategory/{name}")
    public Category addCategory(@PathVariable("name") String name) {
        return categoryService.add(name);
    }

    @PostMapping("/addSubCategory/{name}")
    public SubCategory addSubCategory(@PathVariable("name") String name, @RequestBody Category category) {
        return subCategoryService.add(name, category);
    }

    @PostMapping("/addGroup/{name}")
    public Group addGroup(@PathVariable("name") String name, @RequestBody SubCategory subCategory) {
        return groupService.add(name, subCategory);
    }

    @PostMapping("/changeProductInfo")
    public boolean changeProductInfo(Product product, Integer groupId, MultipartFile productPicture, String properties,String oldPicture) throws IOException {
        return adminService.changeProductInfo(product,groupId,productPicture,properties,oldPicture);
    }


    @DeleteMapping("/deleteCategory/{id}")
    public void deleteCategory(@PathVariable("id") Integer id) {
        categoryService.delete(id);
    }

    @DeleteMapping("/deleteSubCategory/{id}")
    public void deleteSubCategory(@PathVariable("id") Integer id) {
        this.subCategoryService.delete(id);
    }

    @DeleteMapping("/deleteGroup/{id}")
    public void deleteGroup(@PathVariable("id") Integer id) {
        groupService.delete(id);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public void deleteProduct(@PathVariable("id") Integer id) {
        productService.delete(id);
    }


    @DeleteMapping("/deleteOrderEntity/{id}")
    public boolean deleteOrderEntity(@PathVariable("id") Integer id) throws IOException {
        return orderEntityService.deleteOrderEntity(id);
    }
    @DeleteMapping("/deleteProperty/{id}")
    public void deleteProperty(@PathVariable("id") Integer id) {
        propertyService.delete(id);
    }
    @DeleteMapping("/deletePropertyValue/{id}")
    public void deletePropertyValue(@PathVariable("id") Integer id) {
        propertyValueService.delete(id);
    }
}

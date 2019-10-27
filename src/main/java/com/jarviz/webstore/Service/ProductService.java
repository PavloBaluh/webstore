package com.jarviz.webstore.Service;

import com.jarviz.webstore.Dao.ProductDao;
import com.jarviz.webstore.Models.Group;
import com.jarviz.webstore.Models.Product;
import com.jarviz.webstore.Models.Property;
import com.jarviz.webstore.Models.PropertyValue;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {
    private GroupService groupService;
    private ProductDao productDao;

    public HashMap<String, Object> getProductById(Integer id) {
        Product product = productDao.getOne(id);
        List<Property> properties = new ArrayList<>();
        product.getPropertyValues().forEach((propertyValue) -> {
            propertyValue.getProperty().setValues(Arrays.asList(propertyValue));
            properties.add(propertyValue.getProperty());
        });
        HashMap<String, Object> map = new HashMap<>();
        map.put("product", product);
        map.put("properties", properties);
        return map;
    }

    public Integer getProductsCount(String group, Float priceFrom, Float PriceTo, String properties) {
        Group groupByName = groupService.getByName(group);
        int productCount;
        if ((priceFrom == 0) && (PriceTo == 0)) {
            productCount = productDao.getProductCountWithGroup(groupByName).size();
        } else {
            List<Product> products = productDao.getProductCount(groupByName, priceFrom, PriceTo);
            if ((!properties.isEmpty()) && (!properties.equals("undefined"))) {
                products = getProductsByProperties(properties, products);
            }
            productCount = products.size();
        }
        return productCount;

    }

    public List<Product> getProductsByGroup(String groupAndPage) {
        String[] split = groupAndPage.split(",");
        Group groupByName = groupService.getByName(split[0]);
        return productDao.getProductsByGroup(groupByName, PageRequest.of(Integer.valueOf(split[1]), 4));
    }


    public List<Product> getSortedProducts(float priceFrom, float intPriceTo, int limit, String direction, String sortBy, String group, String properties, Integer page) {
        Group groupByName = groupService.getByName(group);
        List<Product> products;
        if (!properties.isEmpty()) {
            List<Product> byGroup = productDao.getByGroup(groupByName);
            products = getProductsByProperties(properties, byGroup);
            products = sort(priceFrom, intPriceTo, limit, direction, sortBy, page, products);
        } else {
            if (direction.equals("desc")) {
                products = this.productDao.getSortedProducts(priceFrom, intPriceTo, groupByName, PageRequest.of(page, limit, Sort.by(sortBy).descending()));
            } else
                products = this.productDao.getSortedProducts(priceFrom, intPriceTo, groupByName, PageRequest.of(page, limit, Sort.by(sortBy)));
        }
        return products;
    }


    private List<Product> sort(float priceFrom, float intPriceTo, int limit, String direction, String sortBy, Integer page, List<Product> products) {
        List<Product> sortedList = products.stream().filter(product -> (product.getPrice() >= priceFrom && product.getPrice() <= intPriceTo)).collect(Collectors.toList());
        sortedList = choseSortBy(sortBy, sortedList);
        if (direction.equals("desc")) Collections.reverse(sortedList);
        if (page == 0) {
            return sortedList.stream().limit(limit).collect(Collectors.toList());
        } else {
            return sortedList.stream().skip(page * limit).collect(Collectors.toList());
        }
    }

    private List<Product> choseSortBy(String sortBy, List<Product> products) {
        List<Product> sortedList = new ArrayList<>();
        if (sortBy.equals("title")) {
            sortedList = products.stream().sorted(Comparator.comparing(Product::getTitle)).collect(Collectors.toList());
        }
        if (sortBy.equals("price")) {
            sortedList = products.stream().sorted(Comparator.comparing(Product::getPrice)).collect(Collectors.toList());
        }
        if (sortBy.equals("data")) {
            sortedList = products.stream().sorted(Comparator.comparing(Product::getDate)).collect(Collectors.toList());
        }
        if (sortBy.equals("rate")) {
            sortedList = products.stream().sorted(Comparator.comparing(Product::getRate)).collect(Collectors.toList());
        }
        return sortedList;
    }

    private List<Product> getProductsByProperties(String properties, List<Product> products) {
        ArrayList<Integer> propertiesArray = new ArrayList<>();
        List<Product> newProducts = new ArrayList<>();
        for (String el : properties.split(",")) {
            propertiesArray.add(Integer.valueOf(el));
        }
        for (Product product : products) {
            ArrayList<Integer> currentPropertiesArray = new ArrayList<>();
            List<PropertyValue> propertyValues = product.getPropertyValues();
            propertyValues.sort(Comparator.comparingInt(PropertyValue::getId));
            for (PropertyValue propertyValue : product.getPropertyValues()) {
                currentPropertiesArray.add(propertyValue.getId());
            }
            if (currentPropertiesArray.containsAll(propertiesArray)) newProducts.add(product);
        }
        return newProducts;
    }


    public List<Integer> getMinMaxPriceByGroup(String group) {
        List<Integer> minMax = new ArrayList<>();
        Group groupByName = groupService.getByName(group);
        List<Product> byGroup = productDao.getByGroup(groupByName);
        float maxPrice = 0;
        float minPrice = byGroup.get(0).getPrice();
        for (Product product : byGroup) {
            if (product.getPrice() > maxPrice) maxPrice = product.getPrice();
            if (product.getPrice() < minPrice) minPrice = product.getPrice();
        }
        minMax.add((int) Math.ceil(minPrice));
        minMax.add((int) Math.ceil(maxPrice));
        return minMax;
    }

    public Product get(Integer id) {
        return productDao.getOne(id);
    }

    public List<Product> getProductsByChars(String sequence) {
        return productDao.getByCharsSequence(sequence, PageRequest.of(0, 4));
    }

    public List<Product> getMostPopularProducts() {
        return productDao.getMostPopularProducts(PageRequest.of(0, 4));
    }

    public List<Product> getLatestProducts() {
        return productDao.getLatestProducts(PageRequest.of(0, 4));
    }

    public List<Product> getAll(){
        return productDao.findAll();
    }

    public void delete(Integer id) {
        Product product = productDao.getOne(id);
        productDao.delete(product);
    }
}

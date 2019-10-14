package com.jarviz.webstore.Service;

import com.jarviz.webstore.Dao.GroupDao;
import com.jarviz.webstore.Dao.ProductDao;
import com.jarviz.webstore.Models.Group;
import com.jarviz.webstore.Models.Product;
import com.jarviz.webstore.Models.PropertyValue;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private GroupDao groupDao;
    private ProductDao productDao;

    public void save(Product product) {
        productDao.save(product);
    }


    public Product getProductById(Integer id) {
        return productDao.getOne(id);
    }

    public Integer getProductsCount(String group, Float priceFrom, Float PriceTo, String properties) {
        Group groupByName = groupDao.getByName(group);
        int productCount = 0;
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
        Group groupByName = groupDao.getByName(split[0]);

        return productDao.getProductsByGroup(groupByName, PageRequest.of(Integer.valueOf(split[1]), 4));
    }


    public List<Product> getSortedProducts(float priceFrom, float intPriceTo, int limit, String direction, String sortBy, String group, String properties, Integer page) {
        Group groupByName = groupDao.getByName(group);
        List<Product> products;
        if (direction.equals("desc")) {
            products = this.productDao.getSortedProducts(priceFrom, intPriceTo, groupByName, PageRequest.of(page, limit, Sort.by(sortBy).descending()));
        } else
            products = this.productDao.getSortedProducts(priceFrom, intPriceTo, groupByName, PageRequest.of(page, limit, Sort.by(sortBy)));
        if (!properties.isEmpty()) {
            return getProductsByProperties(properties, products);
        }
        return products;
    }

    private List<Product> getProductsByProperties(String properties, List<Product> products) {
        ArrayList<Integer> propertiesArray = new ArrayList<>();
        List<Product> newProducts = new ArrayList<>();
        for (String el : properties.split(",")) {
            propertiesArray.add(Integer.valueOf(el));
        }
        for (Product product : products) {
            ArrayList<Integer> currentpropertiesArray = new ArrayList<>();
            List<PropertyValue> propertyValues = product.getPropertyValues();
            propertyValues.sort(Comparator.comparingInt(PropertyValue::getId));
            for (PropertyValue propertyValue : product.getPropertyValues()) {
                currentpropertiesArray.add(propertyValue.getId());
            }
            if (currentpropertiesArray.containsAll(propertiesArray)) newProducts.add(product);
        }
        return newProducts;
    }


    public List<Integer> getMinMaxPriceByGroup(String group) {
        List<Integer> minMax = new ArrayList<>();
        Group groupByName = groupDao.getByName(group);
        List<Product> byGroup = this.productDao.getByGroup(groupByName);
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
        return this.productDao.getByCharsSequence(sequence, PageRequest.of(0, 4));
    }
}

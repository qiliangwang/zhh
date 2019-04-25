package com.iceberg.service;

import com.iceberg.dataobject.ProductCategory;

import java.util.List;

/**
 * 类目
 * @author wangql
 */
public interface CategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}

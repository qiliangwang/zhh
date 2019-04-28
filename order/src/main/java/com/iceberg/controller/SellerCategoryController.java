package com.iceberg.controller;

import com.iceberg.VO.ResultVO;
import com.iceberg.dataobject.ProductCategory;
import com.iceberg.exception.SellException;
import com.iceberg.form.CategoryForm;
import com.iceberg.service.CategoryService;
import com.iceberg.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 卖家类目
 * @author wangql
 */
@RestController
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 类目列表
     * @return
     */
    @GetMapping("/list")
    public ResultVO list() {
        List<ProductCategory> categoryList = categoryService.findAll();
        return ResultVOUtil.success(categoryList);
    }

    /**
     * 展示
     * @param categoryId
     * @return
     */
    @GetMapping("/index")
    public ResultVO index(@RequestParam(value = "categoryId", required = false) Integer categoryId) {
        if (categoryId != null) {
            ProductCategory productCategory = categoryService.findOne(categoryId);
            if (productCategory == null) {
                return ResultVOUtil.error(1, "categoryId非法");
            }
            return ResultVOUtil.success(productCategory);
        } else {
            return ResultVOUtil.error(1, "categoryId必填");
        }
    }

    /**
     * 保存/更新
     * @param form
     * @param bindingResult
     * @return
     */
    @PostMapping("/save")
    public ResultVO save(@Valid CategoryForm form,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
        }

        ProductCategory productCategory = new ProductCategory();
        try {
            if (form.getCategoryId() != null) {
                productCategory = categoryService.findOne(form.getCategoryId());
                if (productCategory == null) {
                    return ResultVOUtil.error(1, "categoryId非法");
                }
            }
            BeanUtils.copyProperties(form, productCategory);
            categoryService.save(productCategory);
        } catch (SellException e) {
            return ResultVOUtil.error(1, e.getMessage());
        }
        return ResultVOUtil.success();
    }
}

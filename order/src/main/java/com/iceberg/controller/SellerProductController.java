package com.iceberg.controller;

import com.iceberg.VO.ResultVO;
import com.iceberg.dataobject.ProductCategory;
import com.iceberg.dataobject.ProductInfo;
import com.iceberg.exception.SellException;
import com.iceberg.form.ProductForm;
import com.iceberg.service.CategoryService;
import com.iceberg.service.ProductService;
import com.iceberg.utils.KeyUtil;
import com.iceberg.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 卖家端商品
 * @author wangql
 */
@RestController
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 列表
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ResultVO list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                         @RequestParam(value = "size", defaultValue = "10") Integer size,
                         Map<String, Object> map) {
        PageRequest request = new PageRequest(page - 1, size);
        Page<ProductInfo> productInfoPage = productService.findAll(request);
        return ResultVOUtil.success(productInfoPage);
    }

    /**
     * 商品上架
     * @param productId
     * @return
     */
    @GetMapping("/on_sale")
    public ResultVO onSale(@RequestParam("productId") String productId) {
        try {
            productService.onSale(productId);
        } catch (SellException e) {
            return ResultVOUtil.error(1, e.getMessage());
        }
        return ResultVOUtil.success();
    }
    /**
     * 商品下架
     * @param productId
     * @return
     */
    @GetMapping("/off_sale")
    public ResultVO offSale(@RequestParam("productId") String productId) {
        try {
            productService.offSale(productId);
        } catch (SellException e) {
            return ResultVOUtil.error(1, e.getMessage());
        }

        return ResultVOUtil.success();
    }

    @GetMapping("/detail")
    public ResultVO index(@RequestParam(value = "productId", required = false) String productId) {
        if (!StringUtils.isEmpty(productId)) {
            ProductInfo productInfo = productService.findOne(productId);
            return ResultVOUtil.success(productInfo);
        } else {
            return ResultVOUtil.error(1, "productId必填");
        }
    }

    /**
     * 保存/更新
     * @param form
     * @param bindingResult
     * @return
     */
    @PostMapping("/save")
    public ResultVO save(@Valid ProductForm form,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
        }

        ProductInfo productInfo = new ProductInfo();
        try {
            //如果productId为空, 说明是新增
            if (!StringUtils.isEmpty(form.getProductId())) {
                productInfo = productService.findOne(form.getProductId());
            } else {
                form.setProductId(KeyUtil.genUniqueKey());
                productInfo.setCreateTime(new Date());
            }
            BeanUtils.copyProperties(form, productInfo);
            productService.save(productInfo);
        } catch (SellException e) {
            return ResultVOUtil.error(1, e.getMessage());
        }
        return ResultVOUtil.success();
    }
}

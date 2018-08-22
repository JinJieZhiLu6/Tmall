/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: PropertyValueController
 * Author:   jack
 * Date:     18/08/20 15:00
 * Description: 设置商品属性控制层
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package ecjtu.mall.controller;

import ecjtu.mall.pojo.Category;
import ecjtu.mall.pojo.Product;
import ecjtu.mall.pojo.PropertyValue;
import ecjtu.mall.service.CategoryService;
import ecjtu.mall.service.ProductService;
import ecjtu.mall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈设置商品属性控制层〉
 *
 * @author jack
 * @create 18/08/20
 * @since 1.0.0
 */
@Controller
public class PropertyValueController {

    @Autowired
    private PropertyValueService propertyValueService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 跳转到商品属性编辑页面
     * @return
     */
    @RequestMapping("admin_propertyValue_edit")
    public String ToPropertyValueEditPage(Integer pid, Model model){
        Product product = productService.selectProductById(pid);
        Category category = categoryService.findCategoryById(product.getCid());
        product.setCategory(category);
        propertyValueService.init(product);
        List<PropertyValue> propertyValues = propertyValueService.selectAllPropertyValue(pid);

        model.addAttribute("p",product);
        model.addAttribute("pvs",propertyValues);
        return "admin/editPropertyValue";
    }

    /**
     * 更新，ajax
     * @param pv
     * @return
     */
    @RequestMapping("admin_propertyValue_update")
    @ResponseBody
    public String update(PropertyValue pv) {
        propertyValueService.updatePropertyValue(pv);
        return "success";
    }

}

/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: PropertyController
 * Author:   jack
 * Date:     18/08/19 9:52
 * Description: 商品属性控制层
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package ecjtu.mall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ecjtu.mall.pojo.Category;
import ecjtu.mall.pojo.Property;
import ecjtu.mall.service.CategoryService;
import ecjtu.mall.service.PropertyService;
import ecjtu.mall.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈商品属性控制层〉
 *
 * @author jack
 * @create 18/08/19
 * @since 1.0.0
 */
@Controller
public class PropertyController {

    @Autowired
    private PropertyService propertyService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 根据商品分类id跳转到商品属性页面
     * @param cid
     * @param model
     * @param page
     * @return
     */
    @RequestMapping("admin_property_list")
    public String findAll(Integer cid, Model model, PageUtils page){
        Category category = categoryService.findCategoryById(cid);

        List<Property> properties = propertyService.selectByCid(cid);

        PageHelper.offsetPage(page.getStart(),page.getCount());
        int total = (int) new PageInfo<>(properties).getTotal();
        page.setTotal(total);
        page.setParam("&cid="+cid);

        model.addAttribute("ps",properties);
        model.addAttribute("c", category);
        model.addAttribute("page", page);
        return "admin/listProperty";
    }

    /**
     * 跳转到修改属性页面
     * @return
     */
    @RequestMapping("admin_property_edit")
    public String edit(Integer id,Model model){

        Property property = propertyService.editById(id);

        model.addAttribute("p",property);
        return "admin/editProperty";
    }

    /**
     * 修改商品分类属性名称
     * @return
     */
    @RequestMapping("admin_property_update")
    public String update(Property property){
        propertyService.updateProperty(property);

        return "redirect:admin_property_list?cid="+property.getCid();
    }

    /**
     * 删除商品属性
     * @param id
     * @return
     */
    @RequestMapping("admin_property_delete")
    public String delete(Integer id){
        Property property = propertyService.selectById(id);
        propertyService.deletePropertyById(id);
        return "redirect:admin_property_list?cid="+property.getCid();
    }

    /**
     * 添加商品属性
     * @return
     */
    @RequestMapping("admin_property_add")
    public String addProperty(Property property){
        propertyService.insertProperty(property);
        return "redirect:admin_property_list?cid="+property.getCid();
    }
}

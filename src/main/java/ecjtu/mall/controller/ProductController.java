/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ProductController
 * Author:   jack
 * Date:     18/08/19 17:06
 * Description: 具体商品控制层
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package ecjtu.mall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ecjtu.mall.pojo.Category;
import ecjtu.mall.pojo.Product;
import ecjtu.mall.service.CategoryService;
import ecjtu.mall.service.ProductService;
import ecjtu.mall.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈具体商品控制层〉
 *
 * @author jack
 * @create 18/08/19
 * @since 1.0.0
 */
@Controller
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 根据商品分类id查询出所有商品
     * @param cid
     * @param model
     * @param page
     * @return
     */
    @RequestMapping("admin_product_list")
    public String findAllProduct(Integer cid, Model model, PageUtils page){
        //查询出所有商品
        List<Product> products = productService.selectAllProductByCid(cid);
        //查询到商品分类
        Category category = categoryService.findCategoryById(cid);
        //分页帮助类
        PageHelper.offsetPage(page.getStart(),page.getCount());
        int total = (int) new PageInfo<>(products).getTotal();
        //设置分页参数
        page.setTotal(total);
        page.setParam("&cid="+cid);
        //设置属性传递到页面
        model.addAttribute("ps",products);
        model.addAttribute("c",category);
        model.addAttribute("page",page);
        return "admin/listProduct";
    }

    /**
     * 添加商品分类
     * @param product
     * @return
     */
    @RequestMapping("admin_product_add")
    public String insertProduct(Product product){
        product.setCreateDate(new Date());
        productService.insertProduct(product);
        return "redirect:/admin_product_list?cid="+product.getCid();
    }

    /**
     * 跳转到商品编辑页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("admin_product_edit")
    public String toProductEditePage(Integer id, Model model){
        Product product = productService.selectProductById(id);
        model.addAttribute("p",product);
        return "admin/editProduct";
    }

    /**
     * 更新商品信息
     * @return
     */
    @RequestMapping("admin_product_update")
    public String updateProduct(Product product){
        productService.updateProduct(product);
        return "redirect:/admin_product_list?cid="+product.getCid();
    }

    /**
     * 删除商品信息
     * @param id
     * @return
     */
    @RequestMapping("admin_product_delete")
    public String deleteProduct(Integer id){
        Product product = productService.selectProductById(id);
        Integer cid = product.getCid();
        productService.deleteProduct(id);
        return "redirect:/admin_product_list?cid="+cid;
    }

}

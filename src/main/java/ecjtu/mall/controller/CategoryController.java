/**
 * Copyright (C), 2015-2018, 华东交大
 * FileName: CategoryController
 * Author:   jack
 * Date:     18/08/16 16:54
 * Description: 商品分类Controller层
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package ecjtu.mall.controller;

import ecjtu.mall.pojo.Category;
import ecjtu.mall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * 〈一句话功能简述〉<br> 
 * 〈商品分类Controller层〉
 *
 * @author jack
 * @create 18/08/16
 * @since 1.0.0
 */
@Controller
public class CategoryController {

    @Autowired
    private CategoryService service;

    /**
     * 将查询到的数据
     * @param model
     * @return
     */
    @RequestMapping("admin_property_list")
    public String findAll(Model model) {
        List<Category> list = service.findAll();
        model.addAttribute("cs", list);
        return "admin/listCategory";
    }

    /**
     * 上传图片
     * @param image
     * @param category
     * @param session
     * @return
     * @throws IOException
     */
    @RequestMapping("admin_category_add")
    public String addToCategory(MultipartFile image, Category category, HttpSession session) throws IOException {
        //将表单信息添加进数据库
        service.addToCategory(category);
        // 图片新名字
        String name = category.getId().toString();
        // 图片原名字
        String oldName = image.getOriginalFilename();
        // 后缀名
        String exeName = oldName.substring(oldName.lastIndexOf("."));
        //获取该项目的路径
        String path = session.getServletContext().getRealPath("img/category");
        //path是文件夹，后面的是文件
        File pic = new File(path, name + exeName);
        //如果文件夹不存在则创建
        if(!pic.getParentFile().exists())
            pic.getParentFile().mkdirs();
        //保存图片到本地
        image.transferTo(pic);

        return "redirect:/admin_property_list";
    }

    @RequestMapping("admin_category_delete")
    public String deleteCategoryById(Integer id, HttpSession session){
        //删除该类商品分类
        service.deleteById(id);
        //同时删除图片
        /*File  imageFolder= new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder,id+".jpg");
        file.delete();*/
        return "redirect:/admin_property_list";
    }
}

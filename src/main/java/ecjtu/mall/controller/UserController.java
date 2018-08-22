/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: UserController
 * Author:   jack
 * Date:     18/08/20 20:21
 * Description: 用户管理控制层
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package ecjtu.mall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ecjtu.mall.pojo.User;
import ecjtu.mall.service.UserService;
import ecjtu.mall.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈用户管理控制层〉
 *
 * @author jack
 * @create 18/08/20
 * @since 1.0.0
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("admin_user_list")
    public String listAllUser(Model model,PageUtils page){
        List<User> users = userService.list();

        PageHelper.offsetPage(page.getStart(),page.getCount());
        int total = (int) new PageInfo<>(users).getTotal();
        page.setTotal(total);

        model.addAttribute("us",users);
        model.addAttribute("page",page);
        return "admin/listUser";
    }
}

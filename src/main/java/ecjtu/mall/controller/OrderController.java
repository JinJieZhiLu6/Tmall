/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: OrderController
 * Author:   jack
 * Date:     18/08/20 21:22
 * Description: 订单控制层
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package ecjtu.mall.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ecjtu.mall.pojo.Order;
import ecjtu.mall.service.OrderItemService;
import ecjtu.mall.service.OrderService;
import ecjtu.mall.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈订单控制层〉
 *
 * @author jack
 * @create 18/08/20
 * @since 1.0.0
 */
@Controller
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;

    /**
     * 跳转到订单页面
     * @param model
     * @param page
     * @return
     */
    @RequestMapping("admin_order_list")
    public String list(Model model, PageUtils page){
        PageHelper.offsetPage(page.getStart(),page.getCount());

        List<Order> os= orderService.list();

        int total = (int) new PageInfo<>(os).getTotal();
        page.setTotal(total);

        orderItemService.fill(os);

        model.addAttribute("os", os);
        model.addAttribute("page", page);

        return "admin/listOrder";
    }

    /**
     * 订单发货
     * @param o
     * @return
     * @throws IOException
     */
    @RequestMapping("admin_order_delivery")
    public String delivery(Order o) throws IOException {
        o.setDeliveryDate(new Date());
        o.setStatus(OrderService.waitConfirm);
        orderService.update(o);
        return "redirect:admin_order_list";
    }
}

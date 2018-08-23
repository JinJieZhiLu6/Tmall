/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: OrderService
 * Author:   jack
 * Date:     18/08/20 21:03
 * Description: 订单业务层
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package ecjtu.mall.service;

import ecjtu.mall.pojo.Order;
import ecjtu.mall.pojo.OrderItem;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈订单业务层〉
 *
 * @author jack
 * @create 18/08/20
 * @since 1.0.0
 */
public interface OrderService {

    String waitPay = "waitPay";
    String waitDelivery = "waitDelivery";
    String waitConfirm = "waitConfirm";
    String waitReview = "waitReview";
    String finish = "finish";
    String delete = "delete";

    void add(Order c);
    void delete(int id);
    void update(Order c);
    Order get(int id);
    List<Order> list();
    float add(Order c,List<OrderItem> ois);
    List<Order> list(int uid, String excludedStatus);
    Order selectByUid(Integer uid);
}

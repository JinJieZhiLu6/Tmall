/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: OrderItemServiceImpl
 * Author:   jack
 * Date:     18/08/20 21:00
 * Description: 订单详情业务层
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
 * 〈订单详情业务层〉
 *
 * @author jack
 * @create 18/08/20
 * @since 1.0.0
 */
public interface OrderItemService {

    void add(OrderItem c);

    void delete(int id);

    void update(OrderItem c);

    OrderItem get(int id);

    List<OrderItem> list();

    void fill(List<Order> os);

    void fill(Order o);

    int getSaleCount(int  pid);

    List<OrderItem> listByUser(int uid);
}

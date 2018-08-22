/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: OrderItemServiceImpl
 * Author:   jack
 * Date:     18/08/20 21:01
 * Description: 订单项业务实现
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package ecjtu.mall.service.impl;

import ecjtu.mall.mapper.OrderItemMapper;
import ecjtu.mall.pojo.Order;
import ecjtu.mall.pojo.OrderItem;
import ecjtu.mall.pojo.OrderItemExample;
import ecjtu.mall.pojo.Product;
import ecjtu.mall.service.OrderItemService;
import ecjtu.mall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈订单项业务实现〉
 *
 * @author jack
 * @create 18/08/20
 * @since 1.0.0
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    OrderItemMapper orderItemMapper;
    @Autowired
    ProductService productService;

    /**
     * 添加订单项
     * @param c
     */
    @Override
    public void add(OrderItem c) {
        orderItemMapper.insert(c);
    }

    /**
     * 删除订单项
     * @param id
     */
    @Override
    public void delete(int id) {
        orderItemMapper.deleteByPrimaryKey(id);
    }

    /**
     * 跟新订单项
     * @param c
     */
    @Override
    public void update(OrderItem c) {
        orderItemMapper.updateByPrimaryKeySelective(c);
    }

    /**
     * 根据id查询订单项
     * @param id
     * @return
     */
    @Override
    public OrderItem get(int id) {
        OrderItem result = orderItemMapper.selectByPrimaryKey(id);
        setProduct(result);
        return result;
    }

    /**
     * 查询所有订单项
     * @return
     */
    public List<OrderItem> list(){
        OrderItemExample example =new OrderItemExample();
        example.setOrderByClause("id desc");
        return orderItemMapper.selectByExample(example);

    }

    /**
     * 与订单关联
     * @param os
     */
    @Override
    public void fill(List<Order> os) {
        for (Order o : os) {
            fill(o);
        }
    }

    /**
     * 填充
     * @param o
     */
    public void fill(Order o) {
        OrderItemExample example =new OrderItemExample();
        example.createCriteria().andOidEqualTo(o.getId());
        example.setOrderByClause("id desc");
        List<OrderItem> ois =orderItemMapper.selectByExample(example);
        setProduct(ois);

        float total = 0;
        int totalNumber = 0;
        for (OrderItem oi : ois) {
            total+=oi.getNumber()*oi.getProduct().getPromotePrice();
            totalNumber+=oi.getNumber();
        }
        o.setTotal(total);
        o.setTotalNumber(totalNumber);
        o.setOrderItems(ois);

    }

    /**
     * 与product关联
     * @param ois
     */
    public void setProduct(List<OrderItem> ois){
        for (OrderItem oi: ois) {
            setProduct(oi);
        }
    }
    private void setProduct(OrderItem oi) {
        Product p = productService.selectProductById(oi.getPid());
        oi.setProduct(p);
    }

    /**
     * 获取销量
     * @param pid
     * @return
     */
    @Override
    public int getSaleCount(int pid) {
        OrderItemExample example =new OrderItemExample();
        example.createCriteria().andPidEqualTo(pid);
        List<OrderItem> ois =orderItemMapper.selectByExample(example);
        int result =0;
        for (OrderItem oi : ois) {
            result += oi.getNumber();
        }
        return result;
    }

    /**
     * 根据用户id查询订单项
     * @param uid
     * @return
     */
    @Override
    public List<OrderItem> listByUser(int uid) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andUidEqualTo(uid).andOidIsNull();
        List<OrderItem> result =orderItemMapper.selectByExample(example);
        setProduct(result);
        return result;
    }
}

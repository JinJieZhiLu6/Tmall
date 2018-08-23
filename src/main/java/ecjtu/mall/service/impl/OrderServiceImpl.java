/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: OrderServiceImpl
 * Author:   jack
 * Date:     18/08/20 21:03
 * Description: 订单业务实现
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package ecjtu.mall.service.impl;

import ecjtu.mall.mapper.OrderMapper;
import ecjtu.mall.pojo.Order;
import ecjtu.mall.pojo.OrderExample;
import ecjtu.mall.pojo.OrderItem;
import ecjtu.mall.pojo.User;
import ecjtu.mall.service.OrderItemService;
import ecjtu.mall.service.OrderService;
import ecjtu.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈订单业务实现〉
 *
 * @author jack
 * @create 18/08/20
 * @since 1.0.0
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserService userService;
    @Autowired
    OrderItemService orderItemService;

    /**
     * 添加订单
     * @param c
     */
    @Override
    public void add(Order c) {
        orderMapper.insert(c);
    }

    /**
     * 删除订单
     * @param id
     */
    @Override
    public void delete(int id) {
        orderMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新订单
     * @param c
     */
    @Override
    public void update(Order c) {
        orderMapper.updateByPrimaryKeySelective(c);
    }

    /**
     * 查询单个订单
     * @param id
     * @return
     */
    @Override
    public Order get(int id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有订单
     * @return
     */
    public List<Order> list(){
        OrderExample example =new OrderExample();
        example.setOrderByClause("id desc");
        List<Order> result =orderMapper.selectByExample(example);
        setUser(result);
        return result;
    }

    /**
     * 设置用户，是由哪个用户下的单
     * @param os
     */
    public void setUser(List<Order> os){
        for (Order o : os)
            setUser(o);
    }
    public void setUser(Order o){
        int uid = o.getUid();
        User u = userService.get(uid);
        o.setUser(u);
    }

    /**
     * 添加订单项
     * 事务管理
     * @param o
     * @param ois
     * @return
     */
    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")
    public float add(Order o, List<OrderItem> ois) {
        float total = 0;
        add(o);

        if(false)
            throw new RuntimeException();

        for (OrderItem oi: ois) {
            oi.setOid(o.getId());
            orderItemService.update(oi);
            total+=oi.getProduct().getPromotePrice()*oi.getNumber();
        }
        return total;
    }

    /**
     * 根据订单状态查询订单
     * @param uid
     * @param excludedStatus
     * @return
     */
    @Override
    public List<Order> list(int uid, String excludedStatus) {
        OrderExample example =new OrderExample();
        example.createCriteria().andUidEqualTo(uid).andStatusNotEqualTo(excludedStatus);
        example.setOrderByClause("id desc");
        return orderMapper.selectByExample(example);
    }

    /***
     * 根据用户id查询订单信息
     * @param uid
     * @return
     */
    @Override
    public Order selectByUid(Integer uid) {

        OrderExample example = new OrderExample();
        example.createCriteria().andUidEqualTo(uid);
        List<Order> orders = orderMapper.selectByExample(example);
        if(null != orders && orders.size() != 0){
            return orders.get(0);
        }else return null;
    }
}

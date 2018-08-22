/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: UserServiceImpl
 * Author:   jack
 * Date:     18/08/20 20:19
 * Description: 用户管理业务实现
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package ecjtu.mall.service.impl;

import ecjtu.mall.mapper.UserMapper;
import ecjtu.mall.pojo.User;
import ecjtu.mall.pojo.UserExample;
import ecjtu.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈用户管理业务实现〉
 *
 * @author jack
 * @create 18/08/20
 * @since 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 添加用户信息
     * @param user
     */
    @Override
    public void add(User user) {
        userMapper.insert(user);
    }

    /**
     * 删除用户信息
     * @param id
     */
    @Override
    public void delete(int id) {
        userMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新用户信息
     * @param user
     */
    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKey(user);
    }

    /**
     * 根据用户id查询用户信息
     * @param id
     * @return
     */
    @Override
    public User get(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> list() {
        UserExample example = new UserExample();
        return userMapper.selectByExample(example);
    }

    /**
     * 判断用户名是否存在
     * @param name
     * @return
     */
    @Override
    public boolean isExist(String name) {
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(name);
        List<User> users = userMapper.selectByExample(example);
        if (users.isEmpty()){
            return false;
        }else return true;
    }

    /**
     * 根据用户名密码查找用户
     * @param name
     * @param password
     * @return
     */
    @Override
    public User get(String name, String password) {
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(name).andPasswordEqualTo(password);
        List<User> users = userMapper.selectByExample(example);
        for (User user: users) {
            return user;
        }
        return null;
    }


}

/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: UserService
 * Author:   jack
 * Date:     18/08/20 20:18
 * Description: 用户管理业务层
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package ecjtu.mall.service;

import ecjtu.mall.pojo.User;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈用户管理业务层〉
 *
 * @author jack
 * @create 18/08/20
 * @since 1.0.0
 */
public interface UserService {

    void add(User user);

    void delete(int id);

    void update(User user);

    User get(int id);

    List<User> list();

    boolean isExist(String name);

    User get(String name, String password);
}

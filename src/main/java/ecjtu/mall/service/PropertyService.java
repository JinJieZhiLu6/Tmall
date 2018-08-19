/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: PropertyService
 * Author:   jack
 * Date:     18/08/19 9:38
 * Description: 某一商品分类具体商品业务层
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package ecjtu.mall.service;

import ecjtu.mall.pojo.Property;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈某一商品分类具体商品业务层〉
 *
 * @author jack
 * @create 18/08/19
 * @since 1.0.0
 */
public interface PropertyService {

    Property selectById(Integer id);
    List<Property> selectByCid(Integer cid);
    Property editById(Integer id);
    void updateProperty(Property property);
    void deletePropertyById(Integer id);
    void insertProperty(Property property);
}

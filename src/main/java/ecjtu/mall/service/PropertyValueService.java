/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: PropertyValueService
 * Author:   jack
 * Date:     18/08/20 14:51
 * Description: 商品属性设置业务层
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package ecjtu.mall.service;

import ecjtu.mall.pojo.Product;
import ecjtu.mall.pojo.PropertyValue;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈商品属性设置业务层〉
 *
 * @author jack
 * @create 18/08/20
 * @since 1.0.0
 */

public interface PropertyValueService {

    List<PropertyValue> selectAllPropertyValue(Integer pid);

    void init(Product product);

    PropertyValue get(int ptid, int pid);

    void updatePropertyValue(PropertyValue propertyValue);
}

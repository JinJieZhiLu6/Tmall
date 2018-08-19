/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: PropertyServiceImpl
 * Author:   jack
 * Date:     18/08/19 9:45
 * Description: 某一类商品分类具体商品业务实现
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package ecjtu.mall.service.impl;

import ecjtu.mall.mapper.PropertyMapper;
import ecjtu.mall.pojo.Property;
import ecjtu.mall.pojo.PropertyExample;
import ecjtu.mall.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈某一类商品分类具体商品业务实现〉
 *
 * @author jack
 * @create 18/08/19
 * @since 1.0.0
 */
@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyMapper propertyMapper;

    /**
     * 查询单个商品分类属性
     * @param cid
     * @return
     */
    @Override
    public List<Property> selectByCid(Integer cid) {
        PropertyExample example =new PropertyExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");
        List<Property> properties = propertyMapper.selectByExample(example);
        return properties;
    }

    /**
     * 根据id查询商品属性
     * @param id
     * @return
     */
    @Override
    public Property editById(Integer id) {
        Property property = propertyMapper.selectByPrimaryKey(id);
        return property;
    }

    /**
     * 跟新商品属性名称
     * @param property
     */
    @Override
    public void updateProperty(Property property) {
        propertyMapper.updateByPrimaryKeySelective(property);
    }

    /**
     * 根据商品属性id删除商品属性
     * @param id
     */
    @Override
    public void deletePropertyById(Integer id) {
        propertyMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据id获取商品属性
     * @param id
     * @return
     */
    @Override
    public Property selectById(Integer id) {
        Property property = propertyMapper.selectByPrimaryKey(id);
        return property;
    }

    /**
     * 添加商品属性
     * @param property
     */
    @Override
    public void insertProperty(Property property) {
        propertyMapper.insert(property);
    }
}

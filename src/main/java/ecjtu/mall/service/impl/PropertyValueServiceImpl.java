/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: PropertyValueServiceImpl
 * Author:   jack
 * Date:     18/08/20 14:52
 * Description: 商品属性设置业务实现
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package ecjtu.mall.service.impl;

import ecjtu.mall.mapper.PropertyValueMapper;
import ecjtu.mall.pojo.Product;
import ecjtu.mall.pojo.Property;
import ecjtu.mall.pojo.PropertyValue;
import ecjtu.mall.pojo.PropertyValueExample;
import ecjtu.mall.service.ProductService;
import ecjtu.mall.service.PropertyService;
import ecjtu.mall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈商品属性设置业务实现〉
 *
 * @author jack
 * @create 18/08/20
 * @since 1.0.0
 */
@Service
public class PropertyValueServiceImpl implements PropertyValueService {

    @Autowired
    private PropertyValueMapper propertyValueMapper;
    @Autowired
    PropertyService propertyService;

    /**
     * 根据pid查询所有商品属性
     * @param pid
     * @return
     */
    @Override
    public List<PropertyValue> selectAllPropertyValue(Integer pid) {

        PropertyValueExample example = new PropertyValueExample();
        PropertyValueExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(pid);
        return propertyValueMapper.selectByExample(example);
    }


    /**
     * 初始化商品属性
     * @param p
     */
    @Override
    public void init(Product p) {

        List<Property> pts = propertyService.selectByCid(p.getCid());

        for (Property pt: pts) {
            PropertyValue pv = get(pt.getId(),p.getId());
            if(null == pv){
                pv = new PropertyValue();
                pv.setPid(p.getId());
                pv.setPtid(pt.getId());
                propertyValueMapper.insert(pv);
            }
        }

    }

    /**
     * 获取商品
     * @param ptid
     * @param pid
     * @return
     */
    @Override
    public PropertyValue get(int ptid, int pid) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPtidEqualTo(ptid).andPidEqualTo(pid);
        List<PropertyValue> pvs= propertyValueMapper.selectByExample(example);
        if (pvs.isEmpty())
            return null;
        return pvs.get(0);
    }

    /**
     * 更新商品属性
     * @param propertyValue
     */
    @Override
    public void updatePropertyValue(PropertyValue propertyValue) {
        propertyValueMapper.updateByPrimaryKeySelective(propertyValue);
    }
}

/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ProductImageServiceImpl
 * Author:   jack
 * Date:     18/08/19 21:35
 * Description: 商品图片管理业务实现
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package ecjtu.mall.service.impl;

import ecjtu.mall.mapper.ProductImageMapper;
import ecjtu.mall.pojo.ProductImage;
import ecjtu.mall.pojo.ProductImageExample;
import ecjtu.mall.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈商品图片管理业务实现〉
 *
 * @author jack
 * @create 18/08/19
 * @since 1.0.0
 */
@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    ProductImageMapper productImageMapper;

    /**
     * 添加商品图片
     * @param pi
     */
    @Override
    public void add(ProductImage pi) {
        productImageMapper.insert(pi);
    }

    /**
     * 删除图片
     * @param id
     */
    @Override
    public void delete(int id) {
        productImageMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新图片
     * @param pi
     */
    @Override
    public void update(ProductImage pi) {
        productImageMapper.updateByPrimaryKeySelective(pi);
    }

    /**
     * 查询图片
     * @param id
     * @return
     */
    @Override
    public ProductImage get(int id) {
        return productImageMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有
     * @param pid
     * @param type
     * @return
     */
    @Override
    public List<ProductImage> list(int pid, String type) {
        ProductImageExample example =new ProductImageExample();
        example.createCriteria()
                .andPidEqualTo(pid)
                .andTypeEqualTo(type);
        example.setOrderByClause("id desc");
        return productImageMapper.selectByExample(example);
    }

}

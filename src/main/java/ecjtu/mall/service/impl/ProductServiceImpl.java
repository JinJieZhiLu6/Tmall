/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ProductServiceImpl
 * Author:   jack
 * Date:     18/08/19 17:09
 * Description: 具体商品业务实现
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package ecjtu.mall.service.impl;

import ecjtu.mall.mapper.ProductMapper;
import ecjtu.mall.pojo.Product;
import ecjtu.mall.pojo.ProductExample;
import ecjtu.mall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈具体商品业务实现〉
 *
 * @author jack
 * @create 18/08/19
 * @since 1.0.0
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    /**
     * 根据商品分类id查询所有商品
     * @param cid
     * @return
     */
    @Override
    public List<Product> selectAllProductByCid(Integer cid) {

        ProductExample example = new ProductExample();
        ProductExample.Criteria criteria = example.createCriteria();
        criteria.andCidEqualTo(cid);
        List<Product> products = productMapper.selectByExample(example);
        return products;
    }

    /**
     * 插入商品信息
     * @param product
     */
    @Override
    public void insertProduct(Product product) {
        productMapper.insert(product);
    }

    /**
     * 根据id查询商品信息
     * @param id
     * @return
     */
    @Override
    public Product selectProductById(Integer id) {
        Product product = productMapper.selectByPrimaryKey(id);
        return product;
    }

    /**
     * 更新商品信息
     * @param product
     */
    @Override
    public void updateProduct(Product product) {
        productMapper.updateByPrimaryKey(product);
    }

    /**
     * 根据商品id删除商品
     * @param id
     */
    @Override
    public void deleteProduct(Integer id) {
        productMapper.deleteByPrimaryKey(id);
    }
}

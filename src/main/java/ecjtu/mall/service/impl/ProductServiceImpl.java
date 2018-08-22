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
import ecjtu.mall.pojo.Category;
import ecjtu.mall.pojo.Product;
import ecjtu.mall.pojo.ProductExample;
import ecjtu.mall.pojo.ProductImage;
import ecjtu.mall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    ProductMapper productMapper;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ReviewService reviewService;

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
        setFirstProductImage(products);
        setCategory(products);
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
        setFirstProductImage(product);
        setCategory(product);
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

    /**
     * 给商品分类添加商品
     * @param ps
     */
    public void setCategory(List<Product> ps){
        for (Product p : ps)
            setCategory(p);
    }
    public void setCategory(Product p){
        int cid = p.getCid();
        Category c = categoryService.findCategoryById(cid);
        p.setCategory(c);
    }


    @Override
    public void setFirstProductImage(Product p) {
        List<ProductImage> pis = productImageService.list(p.getId(), ProductImageService.type_single);
        if (!pis.isEmpty()) {
            ProductImage pi = pis.get(0);
            p.setFirstProductImage(pi);
        }
    }

    @Override
    public void fill(List<Category> cs) {
        for (Category c : cs) {
            fill(c);
        }
    }

    @Override
    public void fillByRow(List<Category> cs) {
        int productNumberEachRow = 8;
        for (Category c : cs) {
            List<Product> products =  c.getProducts();
            List<List<Product>> productsByRow =  new ArrayList<>();
            for (int i = 0; i < products.size(); i+=productNumberEachRow) {
                int size = i+productNumberEachRow;
                size= size>products.size()?products.size():size;
                List<Product> productsOfEachRow =products.subList(i, size);
                productsByRow.add(productsOfEachRow);
            }
            c.setProductsByRow(productsByRow);
        }
    }

    @Override
    public void setSaleAndReviewNumber(Product p) {
        int saleCount = orderItemService.getSaleCount(p.getId());
        p.setSaleCount(saleCount);

        int reviewCount = reviewService.getCount(p.getId());
        p.setReviewCount(reviewCount);
    }

    @Override
    public void setSaleAndReviewNumber(List<Product> ps) {
        for (Product p : ps) {
            setSaleAndReviewNumber(p);
        }
    }

    @Override
    public List<Product> search(String keyword) {
        ProductExample example = new ProductExample();
        example.createCriteria().andNameLike("%" + keyword + "%");
        example.setOrderByClause("id desc");
        List result = productMapper.selectByExample(example);
        setFirstProductImage(result);
        setCategory(result);
        return result;
    }

    @Override
    public void fill(Category c) {
        List<Product> ps = selectAllProductByCid(c.getId());
        c.setProducts(ps);
    }

    public void setFirstProductImage(List<Product> ps) {
        for (Product p : ps) {
            setFirstProductImage(p);
        }
    }
}

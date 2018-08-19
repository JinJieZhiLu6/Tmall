/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ProductService
 * Author:   jack
 * Date:     18/08/19 17:07
 * Description: 具体商品业务层
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package ecjtu.mall.service;

import ecjtu.mall.pojo.Product;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈具体商品业务层〉
 *
 * @author jack
 * @create 18/08/19
 * @since 1.0.0
 */
public interface ProductService {

    List<Product> selectAllProductByCid(Integer cid);
    void insertProduct(Product product);
    Product selectProductById(Integer id);
    void updateProduct(Product product);
    void deleteProduct(Integer id);
}

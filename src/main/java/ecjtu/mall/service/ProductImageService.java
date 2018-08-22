/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ProductImageService
 * Author:   jack
 * Date:     18/08/19 21:35
 * Description: 商品图片管理业务层
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package ecjtu.mall.service;

import ecjtu.mall.pojo.ProductImage;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈商品图片管理业务层〉
 *
 * @author jack
 * @create 18/08/19
 * @since 1.0.0
 */
public interface ProductImageService {

    String type_single = "type_single";//单张图片
    String type_detail = "type_detail";//详情图片

    void add(ProductImage pi);
    void delete(int id);
    void update(ProductImage pi);
    ProductImage get(int id);
    List<ProductImage> list(int pid, String type);

}

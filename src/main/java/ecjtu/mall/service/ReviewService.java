/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ReviewService
 * Author:   jack
 * Date:     18/08/21 14:15
 * Description: 用户评论业务层
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package ecjtu.mall.service;

import ecjtu.mall.pojo.Review;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈用户评论业务层〉
 *
 * @author jack
 * @create 18/08/21
 * @since 1.0.0
 */
public interface ReviewService {

    void add(Review c);

    void delete(int id);

    void update(Review c);

    Review get(int id);

    List<Review> list(int pid);

    int getCount(int pid);


}

/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ReviewServiceImpl
 * Author:   jack
 * Date:     18/08/21 14:19
 * Description: 用户评论业务实现
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package ecjtu.mall.service.impl;

import ecjtu.mall.mapper.ReviewMapper;
import ecjtu.mall.pojo.Review;
import ecjtu.mall.pojo.ReviewExample;
import ecjtu.mall.pojo.User;
import ecjtu.mall.service.ReviewService;
import ecjtu.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈用户评论业务实现〉
 *
 * @author jack
 * @create 18/08/21
 * @since 1.0.0
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;
    @Autowired
    UserService userService;

    /**
     * 添加评论
     * @param c
     */
    @Override
    public void add(Review c) {
        reviewMapper.insert(c);
    }

    /**
     * 删除评论
     * @param id
     */
    @Override
    public void delete(int id) {
        reviewMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改评论
     * @param c
     */
    @Override
    public void update(Review c) {
        reviewMapper.updateByPrimaryKey(c);
    }

    /**
     * 查询评论
     * @param id
     * @return
     */
    @Override
    public Review get(int id) {
        return reviewMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有评论
     * @param pid
     * @return
     */
    @Override
    public List<Review> list(int pid) {
        ReviewExample example = new ReviewExample();
        example.createCriteria().andPidEqualTo(pid);
        List<Review> reviews = reviewMapper.selectByExample(example);
        setUser(reviews);
        return reviews;
    }

    /**
     * 查询评论条数
     * @param pid
     * @return
     */
    @Override
    public int getCount(int pid) {
        return list(pid).size();
    }

    /**
     * 评论是由哪个用户评论的
     * @param reviews
     */
    public void setUser(List<Review> reviews){
        for (Review review : reviews) {
            setUser(review);
        }
    }

    private void setUser(Review review) {
        int uid = review.getUid();
        User user =userService.get(uid);
        review.setUser(user);
    }
}

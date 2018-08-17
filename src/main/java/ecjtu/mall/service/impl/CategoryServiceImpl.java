/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: CategoryServiceImpl
 * Author:   jack
 * Date:     18/08/16 15:36
 * Description: 商品分类业务实现
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package ecjtu.mall.service.impl;

import ecjtu.mall.mapper.CategoryMapper;
import ecjtu.mall.pojo.Category;
import ecjtu.mall.pojo.CategoryExample;
import ecjtu.mall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈商品分类业务实现〉
 *
 * @author jack
 * @create 18/08/16
 * @since 1.0.0
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 查询所有分类信息
     * @return
     */
    @Override
    public List<Category> findAll() {
        CategoryExample example = new CategoryExample();
        //查询所有
        example.setOrderByClause("id");
        List<Category> list = categoryMapper.selectByExample(example);
        return list;
    }

    /**
     * 添加商品分类
     * @param category
     */
    @Override
    public void addToCategory(Category category) {
        categoryMapper.insertSelective(category);
    }

    @Override
    public void deleteById(Integer id) {
        categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Category findCategoryById(Integer id) {
        Category category = categoryMapper.selectByPrimaryKey(id);
        return category;
    }

    @Override
    public void updateCategoryById(Category category) {
        categoryMapper.updateByPrimaryKey(category);
    }
}

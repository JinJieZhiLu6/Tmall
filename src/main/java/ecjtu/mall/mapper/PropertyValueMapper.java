package ecjtu.mall.mapper;

import ecjtu.mall.pojo.PropertyValue;
import ecjtu.mall.pojo.PropertyValueExample;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyValueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PropertyValue record);

    int insertSelective(PropertyValue record);

    List<PropertyValue> selectByExample(PropertyValueExample example);

    PropertyValue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PropertyValue record);

    int updateByPrimaryKey(PropertyValue record);
}
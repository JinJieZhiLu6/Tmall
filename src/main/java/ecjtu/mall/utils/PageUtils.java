/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: PageUtils
 * Author:   jack
 * Date:     18/08/16 21:19
 * Description: 分页工具
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package ecjtu.mall.utils;

/**
 * 〈一句话功能简述〉<br> 
 * 〈分页工具〉
 *
 * @author jack
 * @create 18/08/16
 * @since 1.0.0
 */
public class PageUtils {

    private Integer startIndex;//开始位置
    private Integer count;//每页显示条数
    private Integer total;//当前表里的数据总条数



    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}

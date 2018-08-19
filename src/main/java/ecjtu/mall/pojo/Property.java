package ecjtu.mall.pojo;

/**
 * 〈一句话功能简述〉<br>
 * 〈某一商品分类具体商品模型层〉
 *
 * @author jack
 * @create 18/08/19
 * @since 1.0.0
 */
public class Property {
    private Integer id;

    private Integer cid;

    private String name;

    private Category category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
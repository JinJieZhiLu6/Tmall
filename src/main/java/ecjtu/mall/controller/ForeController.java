/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ForeController
 * Author:   jack
 * Date:     18/08/21 9:11
 * Description: 前台交互控制器
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package ecjtu.mall.controller;

import com.github.pagehelper.PageHelper;
import comparator.*;
import ecjtu.mall.pojo.*;
import ecjtu.mall.service.*;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈前台交互控制器〉
 *
 * @author jack
 * @create 18/08/21
 * @since 1.0.0
 */
@Controller
public class ForeController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private PropertyValueService propertyValueService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private ReviewService reviewService;

    /**
     * 跳转到首页
     * @param model
     * @return
     */
    @RequestMapping("forehome")
    public String home(Model model) {
        //查找到所有商品分类
        List<Category> cs= categoryService.findAll();
        //给商品分类填充商品
        productService.fill(cs);
        //给界面下每行分类添加商品
        productService.fillByRow(cs);
        model.addAttribute("cs", cs);
        return "fore/home";
    }


    /**
     * 用户注册
     * @param model
     * @param user
     * @return
     */
    @RequestMapping("foreregister")
    public String register(Model model, User user) {
        String name =  user.getName();
        //注册时使名字规范
        //可以实现HTML标签及转义字符之间的转换。
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);
        boolean exist = userService.isExist(name);

        if(exist){
            String m ="用户名已经被使用,不能使用";
            model.addAttribute("msg", m);
            model.addAttribute("user", null);
            return "fore/register";
        }
        userService.add(user);

        return "redirect:registerSuccessPage";
    }

    /**
     * 用户登录
     * @param name
     * @param password
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("forelogin")
    public String login(@RequestParam("name") String name, @RequestParam("password") String password, Model model, HttpSession session) {
        name = HtmlUtils.htmlEscape(name);
        User user = userService.get(name,password);

        if(null==user){
            model.addAttribute("msg", "账号密码错误");
            return "fore/login";
        }
        session.setAttribute("user", user);
        return "redirect:forehome";
    }

    /**
     * 退出登录
     * @param session
     * @return
     */
    @RequestMapping("forelogout")
    public String logout( HttpSession session) {
        session.removeAttribute("user");
        return "redirect:forehome";
    }

    /**
     * 跳转到产品页面
     * 携带产品数据
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping("foreproduct")
    public String product( int pid, Model model) {
        //根据用户点击的商品获取商品id得到商品信息
        Product p = productService.selectProductById(pid);
        //根据商品id获取商品分类信息，用于导航栏显示分类信息
        Category category = categoryService.findCategoryById(p.getCid());
        //将分类信息设置给商品
        p.setCategory(category);

        //给商品设置图片，单张图片和详情图片
        List<ProductImage> productSingleImages = productImageService.list(p.getId(), ProductImageService.type_single);
        List<ProductImage> productDetailImages = productImageService.list(p.getId(), ProductImageService.type_detail);
        p.setProductSingleImages(productSingleImages);
        p.setProductDetailImages(productDetailImages);

        //给商品设置商品属性和评论列表
        List<PropertyValue> pvs = propertyValueService.selectAllPropertyValue(p.getId());
        List<Review> reviews = reviewService.list(p.getId());
        productService.setSaleAndReviewNumber(p);

        model.addAttribute("reviews", reviews);
        model.addAttribute("p", p);
        model.addAttribute("pvs", pvs);
        return "fore/product";
    }

    /**
     * 登录状态检测，ajax验证
     * @param session
     * @return
     */
    @RequestMapping("forecheckLogin")
    @ResponseBody
    public String checkLogin( HttpSession session) {
        User user =(User)  session.getAttribute("user");
        if(null!=user)
            return "success";
        return "fail";
    }

    /**
     * ajax方式登录
     * @param name
     * @param password
     * @param session
     * @return
     */
    @RequestMapping("foreloginAjax")
    @ResponseBody
    public String loginAjax(@RequestParam("name") String name, @RequestParam("password") String password,HttpSession session) {
        name = HtmlUtils.htmlEscape(name);
        User user = userService.get(name,password);

        if(null==user){
            return "fail";
        }
        session.setAttribute("user", user);
        return "success";
    }

    /**
     * 首页显示产品分类并给分类填充商品
     * @param cid
     * @param sort
     * @param model
     * @return
     */
    @RequestMapping("forecategory")
    public String category(int cid,String sort, Model model) {
        //根据cid获取商品分类
        Category c = categoryService.findCategoryById(cid);
        //给商品分类填充商品
        productService.fill(c);
        productService.setSaleAndReviewNumber(c.getProducts());
        //根据传过来的排序方式进行排序
        if(null!=sort){
            switch(sort){
                case "review":
                    Collections.sort(c.getProducts(),new ProductReviewComparator());
                    break;
                case "date" :
                    Collections.sort(c.getProducts(),new ProductDateComparator());
                    break;

                case "saleCount" :
                    Collections.sort(c.getProducts(),new ProductSaleCountComparator());
                    break;

                case "price":
                    Collections.sort(c.getProducts(),new ProductPriceComparator());
                    break;

                case "all":
                    Collections.sort(c.getProducts(),new ProductAllComparator());
                    break;
            }
        }

        model.addAttribute("c", c);
        return "fore/category";
    }

    /**
     * 关键词查找商品
     * @param keyword
     * @param model
     * @return
     */
    @RequestMapping("foresearch")
    public String search( String keyword,Model model){
        //设置分页属性
        PageHelper.offsetPage(0,20);
        List<Product> ps= productService.search(keyword);
        productService.setSaleAndReviewNumber(ps);

        model.addAttribute("ps",ps);
        return "fore/searchResult";
    }

    /**
     * 立即购买
     * a. 如果已经存在这个产品对应的OrderItem，并且还没有生成订单，即还在购物车中。那么就应该在对应的OrderItem基础上，调整数量
     *      a.1 基于用户对象user，查询没有生成订单的订单项集合
     *      a.2 遍历这个集合
     *      a.3 如果产品是一样的话，就进行数量追加
     *      a.4 获取这个订单项的 id
     *
     * b. 如果不存在对应的OrderItem,那么就新增一个订单项OrderItem
     *      b.1 生成新的订单项
     *      b.2 设置数量，用户和产品
     *      b.3 插入到数据库
     *      b.4 获取这个订单项的 id
     * @param pid
     * @param num
     * @param session
     * @return
     */
    @RequestMapping("forebuyone")
    public String buyOne(int pid, int num, HttpSession session) {
        Product p = productService.selectProductById(pid);
        int oiid = 0;

        User user =(User)  session.getAttribute("user");
        //true表示订单项含有该商品
        boolean found = false;
        List<OrderItem> ois = orderItemService.listByUser(user.getId());
        for (OrderItem oi : ois) {
            //判断用户订单项是否含有该商品
            //如果有则只增加该商品的数量，并将found置为true
            if(oi.getProduct().getId().intValue() == p.getId().intValue()){
                oi.setNumber(oi.getNumber()+num);
                orderItemService.update(oi);
                found = true;
                oiid = oi.getId();
                break;
            }
        }
        //如果找不到则添加为新的订单项
        if(!found){
            OrderItem oi = new OrderItem();
            oi.setUid(user.getId());
            oi.setNumber(num);
            oi.setPid(pid);
            orderItemService.add(oi);
            oiid = oi.getId();
        }
        return "redirect:forebuy?oiid="+oiid;
    }

    /**
     * 支付
     * 1. 通过字符串数组获取参数oiid
     *      获取多个oiid是因为根据购物流程环节与表关系，
     *      结算页面还需要显示在购物车中选中的多条OrderItem数据，
     *      所以为了兼容从购物车页面跳转过来的需求，要用字符串数组获取多个oiid
     * 2. 准备一个泛型是OrderItem的集合ois
     * 3. 根据前面步骤获取的oiids，从数据库中取出OrderItem对象，并放入ois集合中
     * 4. 累计这些ois的价格总数，赋值在total上
     * 5. 把订单项集合放在session的属性 "ois" 上
     * 6. 把总价格放在 model的属性 "total" 上
     * 7. 服务端跳转到buy.jsp
     * @param model
     * @param oiid
     * @param session
     * @return
     */
    @RequestMapping("forebuy")
    public String buy( Model model,String[] oiid,HttpSession session){
        List<OrderItem> ois = new ArrayList<>();
        float total = 0;

        for (String strid : oiid) {
            int id = Integer.parseInt(strid);
            OrderItem oi= orderItemService.get(id);
            total +=oi.getProduct().getPromotePrice()*oi.getNumber();
            ois.add(oi);
        }

        //用于填写用户手机地址等信息回显
        User user = (User) session.getAttribute("user");
        Integer id = user.getId();
        Order order = orderService.selectByUid(id);

        if(null == order){

        }

        session.setAttribute("order",order);
        session.setAttribute("ois", ois);
        model.addAttribute("total", total);
        return "fore/buy";
    }

    /**
     * 添加到购物车
     * 逻辑同立即购买
     * @param pid
     * @param num
     * @param session
     * @return
     */
    @RequestMapping("foreaddCart")
    @ResponseBody
    public String addCart(int pid, int num, HttpSession session) {
        Product p = productService.selectProductById(pid);
        User user =(User)  session.getAttribute("user");
        boolean found = false;

        List<OrderItem> ois = orderItemService.listByUser(user.getId());
        for (OrderItem oi : ois) {
            if(oi.getProduct().getId().intValue()==p.getId().intValue()){
                oi.setNumber(oi.getNumber()+num);
                orderItemService.update(oi);
                found = true;
                break;
            }
        }

        if(!found){
            OrderItem oi = new OrderItem();
            oi.setUid(user.getId());
            oi.setNumber(num);
            oi.setPid(pid);
            orderItemService.add(oi);
        }
        return "success";
    }

    /**
     * 查看购物车
     * 1. 通过session获取当前用户
     *     要登录才访问，否则拿不到用户对象,会报错
     * 2. 获取为这个用户关联的订单项集合 ois
     * 3. 把ois放在model中
     * 4. 服务端跳转到cart.jsp
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("forecart")
    public String cart( Model model,HttpSession session) {
        User user =(User)  session.getAttribute("user");
        List<OrderItem> ois = orderItemService.listByUser(user.getId());
        model.addAttribute("ois", ois);
        return "fore/cart";
    }

    /**
     * 购物车页面操作
     * 1. 判断用户是否登录
     * 2. 获取pid和number
     * 3. 遍历出用户当前所有的未生成订单的OrderItem
     * 4. 根据pid找到匹配的OrderItem，并修改数量后更新到数据库
     * 5. 返回字符串"success"
     * @param session
     * @param pid
     * @param number
     * @return
     */
    @RequestMapping("forechangeOrderItem")
    @ResponseBody
    public String changeOrderItem(HttpSession session, int pid, int number) {
        User user =(User)  session.getAttribute("user");
        if(null==user)
            return "fail";

        List<OrderItem> ois = orderItemService.listByUser(user.getId());
        for (OrderItem oi : ois) {
            if(oi.getProduct().getId().intValue()==pid){
                oi.setNumber(number);
                orderItemService.update(oi);
                break;
            }
        }
        return "success";
    }

    /**
     * 删除订单
     * @param session
     * @param oiid
     * @return
     */
    @RequestMapping("foredeleteOrderItem")
    @ResponseBody
    public String deleteOrderItem(HttpSession session,int oiid){
        User user =(User)  session.getAttribute("user");
        if(null==user)
            return "fail";
        orderItemService.delete(oiid);
        return "success";
    }

    /**
     * 生成订单
     * 1. 从session中获取user对象
     * 2. 通过参数Order接受地址，邮编，收货人，用户留言等信息
     * 3. 根据当前时间加上一个4位随机数生成订单号
     * 4. 根据上述参数，创建订单对象
     * 5. 把订单状态设置为等待支付
     * 6. 从session中获取订单项集合 ( 订单项集合被放到了session中 )
     * 7. 把订单加入到数据库，并且遍历订单项集合，设置每个订单项的order，更新到数据库
     * 8. 统计本次订单的总金额
     * 9. 客户端跳转到确认支付页forealipay，并带上订单id和总金额
     * @param model
     * @param order
     * @param session
     * @return
     */
    @RequestMapping("forecreateOrder")
    public String createOrder( Model model,Order order,HttpSession session){
        User user =(User)  session.getAttribute("user");
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(10000);
        order.setOrderCode(orderCode);
        order.setCreateDate(new Date());
        order.setUid(user.getId());
        order.setStatus(OrderService.waitPay);
        List<OrderItem> ois= (List<OrderItem>)  session.getAttribute("ois");

        float total =orderService.add(order,ois);
        return "redirect:forealipay?oid="+order.getId() +"&total="+total;
    }

    /**
     * 支付
     * 1. 在上一步确认访问按钮提交数据到/forepayed,导致ForeController.payed方法被调用
     *      1.1 获取参数oid
     *      1.2 根据oid获取到订单对象order
     *      1.3 修改订单对象的状态和支付时间
     *      1.4 更新这个订单对象到数据库
     *      1.5 把这个订单对象放在model的属性"o"上
     *      1.6 服务端跳转到payed.jsp
     * 2. payed.jsp
     *     与 register.jsp 相仿，payed.jsp也包含了header.jsp, top.jsp, simpleSearch.jsp， footer.jsp 等公共页面。
     *     中间是支付成功业务页面 payedPage.jsp
     * 3. payedPage.jsp
     *     显示订单中的地址，邮编，收货人，手机号码等等
     * @param oid
     * @param model
     * @return
     */
    @RequestMapping("forepayed")
    public String payed(int oid, Model model) {
        Order order = orderService.get(oid);
        order.setStatus(OrderService.waitDelivery);
        order.setPayDate(new Date());
        orderService.update(order);
        model.addAttribute("o", order);
        return "fore/payed";
    }

    /**
     * 跳转到我的订单页面
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("forebought")
    public String bought( Model model,HttpSession session) {
        User user =(User)  session.getAttribute("user");
        List<Order> os= orderService.list(user.getId(),OrderService.delete);

        orderItemService.fill(os);

        model.addAttribute("os", os);

        return "fore/bought";
    }

    /**
     * 确认支付
     * @param model
     * @param oid
     * @return
     */
    @RequestMapping("foreconfirmPay")
    public String confirmPay( Model model,int oid) {
        Order o = orderService.get(oid);
        orderItemService.fill(o);
        model.addAttribute("o", o);
        return "fore/confirmPay";
    }

    /**
     * 确认收货
     * @param oid
     * @return
     */
    @RequestMapping("foreorderConfirmed")
    public String orderConfirmed(int oid) {
        Order o = orderService.get(oid);
        o.setStatus(OrderService.waitReview);
        o.setConfirmDate(new Date());
        orderService.update(o);
        return "fore/orderConfirmed";
    }

    /**
     * 删除订单
     * @param oid
     * @return
     */
    @RequestMapping("foredeleteOrder")
    @ResponseBody
    public String deleteOrder(int oid){
        Order o = orderService.get(oid);
        o.setStatus(OrderService.delete);
        orderService.update(o);
        return "success";
    }

    /**
     * 添加评论
     * 1 获取参数oid
     * 2 根据oid获取订单对象o
     * 3 为订单对象填充订单项
     * 4 获取第一个订单项对应的产品,因为在评价页面需要显示一个产品图片，那么就使用这第一个产品的图片了
     * 5 获取这个产品的评价集合
     * 6 为产品设置评价数量和销量
     * 7 把产品，订单和评价集合放在request上
     * 8 服务端跳转到 review.jsp
     * @param model
     * @param oid
     * @return
     */
    @RequestMapping("forereview")
    public String review( Model model,int oid) {
        Order o = orderService.get(oid);
        orderItemService.fill(o);
        Product p = o.getOrderItems().get(0).getProduct();
        List<Review> reviews = reviewService.list(p.getId());
        productService.setSaleAndReviewNumber(p);
        model.addAttribute("p", p);
        model.addAttribute("o", o);
        model.addAttribute("reviews", reviews);
        return "fore/review";
    }

    /**
     * 提交评价
     * 1 获取参数oid
     * 2 根据oid获取订单对象o
     * 3 修改订单对象状态
     * 4 更新订单对象到数据库
     * 5 获取参数pid
     * 6 根据pid获取产品对象
     * 7 获取参数content (评价信息)
     * 8 对评价信息进行转义，道理同注册ForeController.register()
     * 9 从session中获取当前用户
     * 10 创建评价对象review
     * 11 为评价对象review设置 评价信息，产品，时间，用户
     * 12 增加到数据库
     * 13.客户端跳转到/forereview： 评价产品页面，并带上参数showonly=true
     * @param session
     * @param oid
     * @param pid
     * @param content
     * @return
     */
    @RequestMapping("foredoreview")
    public String doReview( HttpSession session,@RequestParam("oid") int oid,@RequestParam("pid") int pid,String content) {
        Order o = orderService.get(oid);
        o.setStatus(OrderService.finish);
        orderService.update(o);

        Product p = productService.selectProductById(pid);
        content = HtmlUtils.htmlEscape(content);

        User user =(User)  session.getAttribute("user");
        Review review = new Review();
        review.setContent(content);
        review.setPid(pid);
        review.setCreateDate(new Date());
        review.setUid(user.getId());
        reviewService.add(review);

        return "redirect:forereview?oid="+oid+"&showonly=true";
    }
}

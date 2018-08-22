/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: PageController
 * Author:   jack
 * Date:     18/08/21 10:23
 * Description: 服务跳转
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package ecjtu.mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 〈一句话功能简述〉<br> 
 * 〈服务跳转〉
 *
 * @author jack
 * @create 18/08/21
 * @since 1.0.0
 */
@Controller
public class PageController {

    /**
     * 跳转到注册页面
     * @return
     */
    @RequestMapping("registerPage")
    public String registerPage() {
        return "fore/register";
    }

    /**
     * 跳转到注册成功页面
     * @return
     */
    @RequestMapping("registerSuccessPage")
    public String registerSuccessPage() {
        return "fore/registerSuccess";
    }

    /**
     * 跳转到登录页面
     * @return
     */
    @RequestMapping("loginPage")
    public String loginPage() {
        return "fore/login";
    }

    /**
     * 跳转到支付页面
     * @return
     */
    @RequestMapping("forealipay")
    public String alipay(){
        return "fore/alipay";
    }
}

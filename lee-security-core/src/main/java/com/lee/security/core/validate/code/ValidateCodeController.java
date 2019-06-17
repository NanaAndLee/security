package com.lee.security.core.validate.code;

import com.lee.security.core.validate.code.image.ImageCode;
import com.lee.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
public class ValidateCodeController {
//
//    public static final String SESSION_KEY = "SESSION_KEY_CODE_IMAGE";
//
//    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
//
//    @Autowired
//    private ValidateCodeGenerator imageCodeGenerator;//图片验证码生成器
//
//    @Autowired
//    private ValidateCodeGenerator smsCodeGenerator;//短信验证生成器
//
//    @Autowired
//    private SmsCodeSender smsCodeSender;//注入发送器
//
//    //发送图形验证码的接口
//    @GetMapping("/code/image")
//    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        //1.根据随机数生成图片
//        ImageCode imageCode = (ImageCode) imageCodeGenerator.generate(new ServletWebRequest( request ));
//
//        //2.将随机数放到session中
//        //这里需要用到一个操作session的工具类
//        sessionStrategy.setAttribute( new ServletWebRequest( request ), SESSION_KEY, imageCode );
//
//        //3.再将生成的图片写到接口的相应中，显示在前台页面
//        ImageIO.write( imageCode.getImage(), "JPEG", response.getOutputStream() );
//
//    }
//
//    //发送短信验证码的接口
//    @GetMapping("/code/sms")
//    public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletRequestBindingException {
//        //1.生成随机验证码
//        ValidateCode smsCode = smsCodeGenerator.generate(new ServletWebRequest( request ));
//
//        //2.将随机数放到session中
//        //这里需要用到一个操作session的工具类
//        sessionStrategy.setAttribute( new ServletWebRequest( request ), SESSION_KEY, smsCode );
//
//        //3.调用一个短信服务将信息发送到手机上去，这里要用一个接口封装起来，因为不同的应用用的短信服务供应商可能不同
//
//        //从请求中获取手机号
//        String mobile = ServletRequestUtils.getRequiredStringParameter( request,  "mobile");
//
//        smsCodeSender.send( "17638099730", smsCode.getCode() );
//    }

//    =================重构v.1========================

    //依赖搜索
    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;

    /**
     * 创建验证码，根据验证码类型的不同，调用不同的 {@link ValidateCodeProcessor}接口实现
     * @param request
     * @param response
     * @param type
     * @throws Exception
     */
    @GetMapping("/code/{type}")
    public void createCode(HttpServletRequest request,
                           HttpServletResponse response,
                           @PathVariable String type) throws Exception {
        validateCodeProcessors.get( type + "CodeProcessor" ).create( new ServletWebRequest( request,response ) );
    }

}

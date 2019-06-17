package com.lee.security.core.validate.code.image;

import com.lee.security.core.validate.code.impl.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

/**
 * 图片验证码处理器
 *
 * @author litenghui
 */
@Component("imageCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

    /**
     * 发送图片验证码，将其写到响应中去
     * @param request
     * @param imageCode
     * @throws Exception
     */
    @Override
    protected void send(ServletWebRequest request, ImageCode imageCode) throws Exception {
        ImageIO.write( imageCode.getImage(), "JPEG", request.getResponse().getOutputStream() );
    }
}

package com.lee.code;

import com.lee.security.core.properties.SecurityProperties;
import com.lee.security.core.validate.code.image.ImageCode;
import com.lee.security.core.validate.code.ValidateCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

//覆盖接口，并且bean的名字相同，这样spring检测到有这个bean就不会使用它自带的验证码生成逻辑了
//@Component("imageCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ImageCode generate(ServletWebRequest request) {

//        int width = 67;//图片宽
        //从请求中获取，如果请求中没有的话就使用默认配置
        int width = ServletRequestUtils.getIntParameter( request.getRequest(), "width", securityProperties.getCode().getImageCodeProperties().getWidth() );
//        int height = 23;//图片高
        int height = ServletRequestUtils.getIntParameter( request.getRequest(), "height", securityProperties.getCode().getImageCodeProperties().getHeight() );
        BufferedImage image = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );

        Graphics g = image.getGraphics();

        Random random = new Random();

        //生成一个图片
        g.setColor( Color.WHITE );
        g.fillRect( 0, 0, width, height );
        g.setFont( new Font( "Time New Roman", Font.ITALIC, 20 ) );

        //在生成的图片上生成干扰条纹
        g.setColor( Color.LIGHT_GRAY );
        for (int i = 0; i < 100; i++) {
            int x = random.nextInt( width );
            int y = random.nextInt( height );
            int x1 = random.nextInt( 12 );
            int y1 = random.nextInt( 12 );
            g.drawLine( x, y, x1, y1 );
        }

        String sRand = "";
        //4 验证码的个数
//        for (int i = 0; i < 4; i++) {
        //读取配置的长度
        for (int i = 0; i < securityProperties.getCode().getImageCodeProperties().getLength(); i++) {
            String rand = String.valueOf( random.nextInt( 10 ) );
            sRand += rand;
            g.setColor( new Color( 20 + random.nextInt( 110 ), 20 + random.nextInt( 110 ), 20 + random.nextInt( 110 ) ) );
            g.drawString( rand, 13 * i + 6, 16 );
        }

        g.dispose();
        //生成的图片  随机生成的验证码  过期时间
        //动态设置过期时间
        return new ImageCode( image, sRand, securityProperties.getCode().getImageCodeProperties().getExpireIn() );
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}

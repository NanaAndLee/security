package com.lee.security.core.properties;

//图形验证码的应用级默认配置
public class ImageCodeProperties extends SmsCodeProperties{
//图形验证码的基本参数可配置
    private int width = 67;//图片的宽
    private int height = 23;//图片的高

    //短信验证码一般是6位，网页图形验证码一般是4位
    public ImageCodeProperties(){
        setLength( 4 );
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

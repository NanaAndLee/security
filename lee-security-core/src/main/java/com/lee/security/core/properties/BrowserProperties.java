package com.lee.security.core.properties;

//登录处理格式配置
public class BrowserProperties {
    // 不做配置时登录时使用系统指定的标准登陆页面
    private String loginPage = "/lee-signIn.html";

    // 不做配置时登陆成功或失败时默认返回JSON格式的信息
    private LoginType loginType = LoginType.JSON;

    //默认一个小时，一般时一周或者半个月
    private int rememberMeSeconds = 3600;

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }
}

package com.lee.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * 实际开发时，业务逻辑比较复杂，传的参数比较多，此时可以构建辅助类
 */
public class UserQueryCondition {

    @ApiModelProperty(value = "用户名称")
    private String username;

    @ApiModelProperty(value = "用户年龄起始值")
    private int age;

    @ApiModelProperty(value = "用户年龄最大值")
    private int ageTo;

    @ApiModelProperty(value = "用户其他描述")
    private String xxx;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAgeTo() {
        return ageTo;
    }

    public void setAgeTo(int ageTo) {
        this.ageTo = ageTo;
    }

    public String getXxx() {
        return xxx;
    }

    public void setXxx(String xxx) {
        this.xxx = xxx;
    }
}

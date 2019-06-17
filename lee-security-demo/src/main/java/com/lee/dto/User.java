package com.lee.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.lee.validator.MyConstraint;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Past;
import java.util.Date;

public class User {

    public interface UserSimpleView {};
    public interface UserDetailView extends UserSimpleView {};

    @ApiModelProperty(value = "entity用户id")
    private String id;

    @NotBlank(message = "密码不能为空🤬")
    @ApiModelProperty(value = "entity用户密码")
    private String password;

//    @MyConstraint(message = "测试一下自己的自定以校验类")
    @ApiModelProperty(value = "entity用户名称")
    private String username;

    @Past(message = "生日必须时过去的时间呦，亲😙")
    @ApiModelProperty(value = "用户出生日期")
    private Date birthday;

    @JsonView(UserSimpleView.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonView(UserDetailView.class)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonView(UserSimpleView.class)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonView(UserSimpleView.class)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}

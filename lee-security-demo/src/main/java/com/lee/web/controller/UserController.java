package com.lee.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.lee.dto.User;
import com.lee.dto.UserQueryCondition;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 使用了http，引入了资源的概念，使用http方法进行操作
     *
     * @return
     */

//    @GetMapping("/me")
//    public Object getCurrentUser(){
//        return SecurityContextHolder.getContext().getAuthentication();
//    }
    //作用同上
//    @GetMapping("/me")
//    public Object getCurrentUser(Authentication authentication){
//        return authentication;
//    }
    //如果不想返回过多无用的认证用户的信息，只需要用户名之类的，可以返回UserDetails
    @GetMapping("/me")
    public Object getCurrentUser(@AuthenticationPrincipal UserDetails user){
        return user;
    }

    @DeleteMapping("{id:\\d+}")
    @ApiOperation(value = "用户删除")
    public void delete(@ApiParam(value = "用户id") @PathVariable String id) {
        System.out.println( id );
    }

    @PutMapping("/{id:\\d+}")
    @ApiOperation(value = "用户更新")
    public User update(@Valid @RequestBody User user, BindingResult errors) {
        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach( error -> System.out.println( error.getDefaultMessage() ) );
//            errors.getAllErrors().stream().forEach( error -> {
//                FieldError fieldError = (FieldError)error;
//                String message = fieldError.getField() + " : " + error.getDefaultMessage();
//                System.out.println(message);
//            } );
        }
//        System.out.println(user.getBirthday());
        return user;
    }

    /**
     * @param user @RequestBody传入的参数时Json串，然后Json串转换成User对象，@Vaild 对准换后的对象进行校验
     *             校验如果传入的对象的一些数据不符合要求，错误信息会写入rrors对象里面
     * @return
     */
    @PostMapping
    @ApiOperation(value = "用户创建")
    public User create(@Valid @RequestBody User user, BindingResult errors) {

        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach( error -> System.out.println( error.getDefaultMessage() ) );
        }

        System.out.println( user.getId() );
        System.out.println( user.getUsername() );
        System.out.println( user.getPassword() );
        System.out.println( user.getBirthday() );

        user.setId( "1" );
        return user;
    }

    @GetMapping
    @JsonView(User.UserSimpleView.class)
    @ApiOperation(value = "用户查询服务")
    public List<User> query(UserQueryCondition condition, @PageableDefault(page = 1, size = 10, sort = {"username"}, direction = Sort.Direction.DESC) Pageable pageable) {
        List<User> userList = new ArrayList<>();
        System.out.println(ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));
        System.out.println( pageable.getPageSize() );
        System.out.println( pageable.getPageNumber() );
        System.out.println( pageable.getSort() );

        User user = new User();
        user.setUsername( "测试用户" );
        user.setPassword( "testPassowrd" );
        user.setId( "1" );
        user.setBirthday( new Date(  ) );
        userList.add( user );
        userList.add( new User() );
        userList.add( new User() );
//        if (userList == null) {
//            System.out.println( "!!!!!!!!!!!!!!!!!!返回的参数为空" );
//        } else {
//            for (User u:
//                 userList) {
//                System.out.println(ReflectionToStringBuilder
//                        .toString(u, ToStringStyle.MULTI_LINE_STYLE));
//            }
//        }
        return userList;
    }

    @GetMapping("/test")
    public Map<String, Object> querytest(){
        Map<String, Object> modelMap = new HashMap<>(  );
        User user1 = new User();
        user1.setId( "1" );user1.setUsername( "lee" );user1.setPassword( "123456" );user1.setBirthday( new Date(  ));
        User user2 = new User();
        user2.setId( "2" );user2.setUsername( "tom" );user2.setPassword( "111111" );
        modelMap.put( "user1",user1 );
        modelMap.put( "user2", user2 );
        return modelMap;
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    @ApiOperation(value = "用户详情服务")
    public User getInfo(@ApiParam(value = "用户id") @PathVariable String id) {
//        throw new UserNotExistException( id );//这里的异常直接被编写的一场处理器拿走了，没进入TimeInterceptor的afterCompletion方法里面，所以afterCompletion里面的Exception仍然为空
//        throw new RuntimeException( "user is not exist" );//这里TimeInterceptor的afterCompletion方法会先拿到这个异常  看控制台 e :
        System.out.println( "进入getInfo服务" );
        User user = new User();
        user.setUsername( "tom" );
        user.setPassword( "tom's password" );
        return user;
    }
}

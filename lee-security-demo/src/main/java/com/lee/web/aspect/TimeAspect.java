package com.lee.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component//加了之后才能称为spring容器的一部分，才能起作用
public class TimeAspect {

    /**
     * 增强方法
     * * 任何返回值的方法        UserController 该Controller下的 *. 任何方法   .任何参数类型的
     * UserController的所有方法上，在被调用的时候起作用
     *
     * 切片的好处，在拦截器中我们是拿不到Controller方法的参数的，但是在切片中能通过ProceedingJoinPoint拿到参数
     * @param proceedingJoinPoint
     * @return
     */
    @Around( "execution(* com.lee.web.controller.UserController.*(..))" )
    public Object handlerControllerMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("======time aspect start======");
        long start = new Date(  ).getTime();
        Object[] objects = proceedingJoinPoint.getArgs();
        for (Object o : objects){
            System.out.println("obj is : " + o);
        }
        Object o = proceedingJoinPoint.proceed();  //这里才真正进入到Controller方法里面去
        System.out.println("time asepct 用时 : "+ (new Date(  ).getTime() - start)+"ms");
        System.out.println("======time aspect end======");
//        ！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！返回null就被拦截了，Controller什么都不会返回
        return o;
    }
}

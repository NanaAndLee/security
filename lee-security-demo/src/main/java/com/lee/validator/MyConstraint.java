package com.lee.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( {ElementType.METHOD, ElementType.FIELD} )//表明注解可以标注在什么上面，这里就字段
@Retention( RetentionPolicy.RUNTIME )//表明时运行时的注解
@Constraint( validatedBy = MyConstraintValidator.class) //表明注解的实现类，就是校验的逻辑在哪个类实现的
public @interface MyConstraint {
//    任何注解要使用就必须包含3个方法，点击进其他的注解类查看，前三个 如下
    /*
    @NotBlank注解里的
    String message() default "{org.hibernate.validator.constraints.NotBlank.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
     */

    String message();//校验不不通过时要发出的自定义提示信息，也可以制定默认值default "{默认提示信息}"

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

package com.lee.validator;

import com.lee.service.HelloService;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

                            /**
                             * 这里的String指定标注的类型只能是String
                             */
public class MyConstraintValidator implements ConstraintValidator<MyConstraint, String > {

    /*
    这里并没有什么用，只是说明一下，该类不用加@Component注解，implements ConstraintValidator会自动被spring管理
     */
    @Autowired
    HelloService helloService;

    @Override
    public void initialize(MyConstraint myConstraint) {
        System.out.println("====Init > MyConstraintValidator====");
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("====isValidStart=====");
        System.out.println(helloService.Greeting());
        System.out.println("拿到值 : " + s);
        System.out.println("校验逻辑");
        if (null == s){
            return false;//这里我们让它返回false去测试能否输出自定义的信息
        }
        return false;
    }
}

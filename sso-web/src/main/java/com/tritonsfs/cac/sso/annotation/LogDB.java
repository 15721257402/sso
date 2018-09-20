package com.tritonsfs.cac.sso.annotation;

import java.lang.annotation.*;

/**
 * @Time 2018/4/13
 * @Author zlian
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface LogDB {
    String value() default "";

    TypeEnum type() default TypeEnum.operation;

    public enum TypeEnum{

        Login("00"),//登录
        LoginOut("01"),//登出
        operation("02");//增删改操作

        private final String value;

        private TypeEnum(String value){
            this.value = value;
        }

        public String getValue(){
            return this.value;
        }

    }
}

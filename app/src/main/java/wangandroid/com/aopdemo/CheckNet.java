package wangandroid.com.aopdemo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2018/3/7.
 */
@Target(ElementType.METHOD)//注解放在哪上 METHOD 方法上
@Retention(RetentionPolicy.RUNTIME)//在什么时候编译，RUNTIME运行时
public @interface CheckNet {

}

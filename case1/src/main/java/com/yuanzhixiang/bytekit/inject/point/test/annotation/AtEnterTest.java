package com.yuanzhixiang.bytekit.inject.point.test.annotation;

import com.alibaba.bytekit.asm.binding.Binding;
import com.alibaba.bytekit.asm.interceptor.annotation.AtEnter;
import com.yuanzhixiang.bytekit.ByteKitUtil;
import com.yuanzhixiang.bytekit.inject.point.test.bean.Sample;

/**
 * @author ZhiXiang Yuan
 * @date 2021/04/22 14:34
 */
public class AtEnterTest {

    public static class SampleInterceptor {

        @AtEnter
        public static void intercept(@Binding.MethodName String methodName) {
            System.out.printf("Method name : [%s]%n", methodName);
            System.out.println("Execute at enter.");
        }
    }

    public static void main(String[] args) {
        ByteKitUtil.reTransform(Sample.class, SampleInterceptor.class, true);

        Sample sample = new Sample();
        sample.execute();
    }

    // 以下是修改过字节码之后的 Sample
//    public class Sample {
//        public Sample() {
//            String string = "<init>";
//            System.out.printf("Method name : [%s]%n", string);
//            System.out.println("Execute at enter.");
//        }
//
//        public void execute() {
//            String string = "execute";
//            System.out.printf("Method name : [%s]%n", string);
//            System.out.println("Execute at enter.");
//            System.out.println("This is a sample.");
//        }
//    }
}

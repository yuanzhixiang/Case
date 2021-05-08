package com.yuanzhixiang.bytekit.inject.point.test.annotation;

import com.alibaba.bytekit.asm.binding.Binding;
import com.alibaba.bytekit.asm.interceptor.annotation.AtExit;
import com.yuanzhixiang.bytekit.ByteKitUtil;
import com.yuanzhixiang.bytekit.inject.point.test.bean.Sample;

/**
 * @author ZhiXiang Yuan
 * @date 2021/04/22 14:48
 */
public class AtExitTest {

    public static class SampleInterceptor {

        @AtExit
        public static void intercept(@Binding.MethodName String methodName) {
            System.out.printf("Method name : [%s]%n", methodName);
            System.out.println("Execute at exit.");
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
//            System.out.println("Execute at exit.");
//        }
//
//        public void execute() {
//            System.out.println("This is a sample.");
//            String string = "execute";
//            System.out.printf("Method name : [%s]%n", string);
//            System.out.println("Execute at exit.");
//        }
//    }

}

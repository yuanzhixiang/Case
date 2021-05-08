package com.yuanzhixiang.bytekit.inject.point.test.option;

import com.alibaba.bytekit.asm.binding.Binding;
import com.alibaba.bytekit.asm.interceptor.annotation.AtExit;
import com.yuanzhixiang.bytekit.ByteKitUtil;
import com.yuanzhixiang.bytekit.inject.point.test.annotation.AtExitTest;
import com.yuanzhixiang.bytekit.inject.point.test.annotation.AtExitTest.SampleInterceptor;
import com.yuanzhixiang.bytekit.inject.point.test.bean.Sample;

/**
 * @author ZhiXiang Yuan
 * @date 2021/05/07 17:06
 */
public class InlineTest {

    public static class SampleInterceptor {

        // inline 为 false，那么会直接调用 InlineTest.SampleInterceptor 而不是将代码放到其上下文中
        @AtExit(inline = false)
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
//            InlineTest.SampleInterceptor.intercept((String)"<init>");
//        }
//
//        public void execute() {
//            System.out.println("This is a sample.");
//            InlineTest.SampleInterceptor.intercept((String)"execute");
//        }
//    }
}

package com.yuanzhixiang.bytekit.inject.point.test.annotation;

import com.alibaba.bytekit.asm.binding.Binding;
import com.alibaba.bytekit.asm.interceptor.annotation.AtFieldAccess;
import com.yuanzhixiang.bytekit.ByteKitUtil;

/**
 * @author ZhiXiang Yuan
 * @date 2021/04/22 14:57
 */
public class AtFieldAccessTest {

    public static class Sample {

        private int value;

        public void method1() {
            value = 1;
        }

        public void method2() {
        }
    }

    public static class SampleInterceptor {

        @AtFieldAccess(name = "value", inline = false)
        public static void intercept(@Binding.MethodName String methodName) {
        }
    }

    public static void main(String[] args) {
        ByteKitUtil.reTransform(Sample.class, SampleInterceptor.class, true);

        Sample sample = new Sample();
    }

    // 以下是修改过字节码之后的 Sample
//    public static class AtFieldAccessTest.Sample {
//        private int value;
//
//        public void method1() {
//            AtFieldAccessTest.SampleInterceptor.intercept((String)"method1");
//            this.value = 1;
//        }
//
//        public void method2() {
//        }
//    }

}

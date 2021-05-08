package com.yuanzhixiang.bytekit;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.alibaba.deps.org.objectweb.asm.tree.ClassNode;
import com.alibaba.deps.org.objectweb.asm.tree.MethodNode;
import com.alibaba.bytekit.asm.MethodProcessor;
import com.alibaba.bytekit.asm.binding.Binding;
import com.alibaba.bytekit.asm.interceptor.InterceptorProcessor;
import com.alibaba.bytekit.asm.interceptor.annotation.AtEnter;
import com.alibaba.bytekit.asm.interceptor.annotation.AtExceptionExit;
import com.alibaba.bytekit.asm.interceptor.annotation.AtExit;
import com.alibaba.bytekit.asm.interceptor.annotation.ExceptionHandler;
import com.alibaba.bytekit.asm.interceptor.parser.DefaultInterceptorClassParser;
import com.alibaba.bytekit.utils.AgentUtils;
import com.alibaba.bytekit.utils.AsmUtils;
import com.alibaba.bytekit.utils.Decompiler;

/**
 * @author ZhiXiang Yuan
 * @date 2021/04/22 10:59
 */
public class ByteKitUtil {

    public static class Sample {

        private int exceptionCount = 0;

        public String hello(String str, boolean exception) {
            if (exception) {
                exceptionCount++;
                throw new RuntimeException("test exception, str: " + str);
            }
            return "hello " + str;
        }
    }

    public static class PrintExceptionSuppressHandler {

        @ExceptionHandler(inline = true)
        public static void onSuppress(@Binding.Throwable Throwable e, @Binding.Class Object clazz) {
            System.out.println("exception handler: " + clazz);
            e.printStackTrace();
        }
    }

    public static class SampleInterceptor {

        @AtEnter(inline = true, suppress = RuntimeException.class, suppressHandler = PrintExceptionSuppressHandler.class)
        public static void atEnter(
            @Binding.This Object object,
            @Binding.Class Object clazz,
            @Binding.Args Object[] args,
            @Binding.MethodName String methodName,
            @Binding.MethodDesc String methodDesc) {
            System.out.println("atEnter, args[0]: " + args[0]);
        }

        @AtExit(inline = true)
        public static void atExit(@Binding.Return Object returnObject) {
            System.out.println("atExit, returnObject: " + returnObject);
        }

        @AtExceptionExit(inline = true, onException = RuntimeException.class)
        public static void atExceptionExit(
            @Binding.Throwable RuntimeException ex,
            @Binding.Field(name = "exceptionCount") int exceptionCount) {
            System.out.println("atExceptionExit, ex: " + ex.getMessage() + ", field exceptionCount: " + exceptionCount);
        }
    }

    public static void main(String[] args) throws Exception {
        AgentUtils.install();

        // 解析定义的 Interceptor类 和相关的注解
        DefaultInterceptorClassParser interceptorClassParser = new DefaultInterceptorClassParser();
        List<InterceptorProcessor> processors = interceptorClassParser.parse(SampleInterceptor.class);

        // 加载字节码
        ClassNode classNode = AsmUtils.loadClass(Sample.class);

        // 对加载到的字节码做增强处理
        for (MethodNode methodNode : classNode.methods) {
            if ("hello".equals(methodNode.name)) {
                MethodProcessor methodProcessor = new MethodProcessor(classNode, methodNode);
                for (InterceptorProcessor interceptor : processors) {
                    interceptor.process(methodProcessor);
                }
            }
        }

        // 获取增强后的字节码
        byte[] bytes = AsmUtils.toBytes(classNode);

        // 查看反编译结果
        System.out.println(Decompiler.decompile(bytes));

        // 等待，查看未增强里的输出结果
        TimeUnit.SECONDS.sleep(10);

        // 通过 reTransform 增强类
        AgentUtils.reTransform(Sample.class, bytes);
    }

    /**
     * 热更新 class
     *
     * @param needModifyClass       需要被修改的 class
     * @param interceptorClass      用于修改 class 的拦截器
     * @param printReTransformClass 是否打印修改过的 class 的反编译的结果
     */
    public static void reTransform(Class<?> needModifyClass, Class<?> interceptorClass, boolean printReTransformClass) {
        try {
            AgentUtils.install();

            // 解析的 Interceptor 和相关的注解
            DefaultInterceptorClassParser interceptorClassParser = new DefaultInterceptorClassParser();
            List<InterceptorProcessor> processors = interceptorClassParser.parse(interceptorClass);

            // 加载字节码
            ClassNode classNode = AsmUtils.loadClass(needModifyClass);

            // 对加载到的字节码做增强处理
            for (MethodNode methodNode : classNode.methods) {
                MethodProcessor methodProcessor = new MethodProcessor(classNode, methodNode);
                for (InterceptorProcessor interceptor : processors) {
                    interceptor.process(methodProcessor);
                }
            }

            // 获取增强后的字节码
            byte[] bytes = AsmUtils.toBytes(classNode);

            // 查看反编译结果
            if (printReTransformClass) {
                System.out.println(Decompiler.decompile(bytes));
            }

            // 通过 reTransform 增强类
            AgentUtils.reTransform(needModifyClass, bytes);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

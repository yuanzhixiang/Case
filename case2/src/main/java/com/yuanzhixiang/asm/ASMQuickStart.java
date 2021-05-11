package com.yuanzhixiang.asm;

import static org.objectweb.asm.Opcodes.ASM5;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import com.yuanzhixiang.asm.util.Decompiler;

import cn.hutool.core.io.FileUtil;

/**
 * @author ZhiXiang Yuan
 * @date 2021/05/08 17:16
 */
public class ASMQuickStart {

    public static void main(String[] args) {
        // 读取文件字节码
        byte[] bytes = FileUtil.readBytes("/Users/yuanzhixiang/Desktop/GenericWebApplicationContext.class");

        // 创建 ClassReader 对字节码进行解析
        ClassReader classReader = new ClassReader(bytes);
        // 创建 ClassWriter，新生成的字节码会写入其中
        ClassWriter classWriter = new ClassWriter(0);
        // 创建 visitor 对字节码进行修改
        final ClassVisitor classVisitor = new ClassVisitor(ASM5, classWriter) {

            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
                String[] exceptions) {
                if ("getName".equals(name)) {
                    name = "getNameModify";
                }
                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }
        };
        // 实际执行修改
        classReader.accept(classVisitor, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG);
        // 读取修改后的字节码
        byte[] bytesModified = classWriter.toByteArray();

        // 反编译打印结果
        System.out.println(Decompiler.decompile(bytesModified));
    }

}

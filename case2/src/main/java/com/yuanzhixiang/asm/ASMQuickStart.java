package com.yuanzhixiang.asm;

import static org.objectweb.asm.Opcodes.ASM5;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.yuanzhixiang.asm.util.Decompiler;

import cn.hutool.core.io.FileUtil;

/**
 * @author ZhiXiang Yuan
 * @date 2021/05/08 17:16
 */
public class ASMQuickStart {

    public static void main(String[] args) {
        byte[] bytes = FileUtil.readBytes("./Bean.class");

        ClassReader classReader = new ClassReader(bytes);
        ClassWriter classWriter = new ClassWriter(0);
        final ClassVisitor classVisitor = new ClassVisitor(ASM5, classWriter) {


            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
                String[] exceptions) {
                if ("getName".equals(name)) {
                    name = "getNameModify";
                }
                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }

            @Override
            public void visitEnd() {
                super.visitEnd();
            }
        };

        classReader.accept(classVisitor, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG);
        byte[] bytesModified = classWriter.toByteArray();

        System.out.println(Decompiler.decompile(bytesModified));
    }

}

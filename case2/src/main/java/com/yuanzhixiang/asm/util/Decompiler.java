package com.yuanzhixiang.asm.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.benf.cfr.reader.api.CfrDriver;
import org.benf.cfr.reader.api.OutputSinkFactory;

import cn.hutool.core.io.FileUtil;
import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.InsnList;
import jdk.internal.org.objectweb.asm.tree.MethodNode;
import jdk.internal.org.objectweb.asm.util.Printer;
import jdk.internal.org.objectweb.asm.util.Textifier;
import jdk.internal.org.objectweb.asm.util.TraceClassVisitor;
import jdk.internal.org.objectweb.asm.util.TraceMethodVisitor;

public class Decompiler {

    public static String decompile(byte[] bytecode) {
        String result;

        File tempDirectory = new File(System.getProperty("java.io.tmpdir"));
        File file = new File(tempDirectory, UUID.randomUUID().toString());
        FileUtil.writeBytes(bytecode, file);

        result = decompile(file.getAbsolutePath(), null);
        return result;
    }

    public static String decompile(String path) throws IOException {
        byte[] byteArray = FileUtil.readBytes(new File(path));
        return decompile(byteArray);
    }

    public static String toString(MethodNode methodNode) {
        Printer printer = new Textifier();
        TraceMethodVisitor methodPrinter = new TraceMethodVisitor(printer);

        methodNode.accept(methodPrinter);

        StringWriter sw = new StringWriter();
        printer.print(new PrintWriter(sw));
        printer.getText().clear();

        return sw.toString();
    }

    public static String toString(ClassNode classNode) {
        Printer printer = new Textifier();
        StringWriter sw = new StringWriter();
        PrintWriter printWriter = new PrintWriter(sw);

        TraceClassVisitor traceClassVisitor = new TraceClassVisitor(printWriter);

        classNode.accept(traceClassVisitor);

        printer.print(printWriter);
        printer.getText().clear();

        return sw.toString();
    }



    public static String toString(InsnList insnList) {
        Printer printer = new Textifier();
        TraceMethodVisitor mp = new TraceMethodVisitor(printer);
        insnList.accept(mp);

        StringWriter sw = new StringWriter();
        printer.print(new PrintWriter(sw));
        printer.getText().clear();
        return sw.toString();
    }

    public static String toString(AbstractInsnNode insn) {
        Printer printer = new Textifier();
        TraceMethodVisitor mp = new TraceMethodVisitor(printer);
        insn.accept(mp);

        StringWriter sw = new StringWriter();
        printer.print(new PrintWriter(sw));
        printer.getText().clear();
        return sw.toString();
    }


    /**
     * @param classFilePath
     * @param methodName
     * @return
     */
    public static String decompile(String classFilePath, String methodName) {
        final StringBuilder result = new StringBuilder(8192);

        OutputSinkFactory mySink = new OutputSinkFactory() {
            @Override
            public List<SinkClass> getSupportedSinks(SinkType sinkType, Collection<SinkClass> collection) {
                return Arrays.asList(SinkClass.STRING, SinkClass.DECOMPILED, SinkClass.DECOMPILED_MULTIVER,
                    SinkClass.EXCEPTION_MESSAGE);
            }

            @Override
            public <T> Sink<T> getSink(final SinkType sinkType, SinkClass sinkClass) {
                return new Sink<T>() {
                    @Override
                    public void write(T sinkable) {
                        // skip message like: Analysing type demo.MathGame
                        if (sinkType == SinkType.PROGRESS) {
                            return;
                        }
                        result.append(sinkable);
                    }
                };
            }
        };

        HashMap<String, String> options = new HashMap<String, String>();
        /**
         * @see org.benf.cfr.reader.util.MiscConstants.Version.getVersion() Currently,
         *      the cfr version is wrong. so disable show cfr version.
         */
        options.put("showversion", "false");
        if (methodName != null) {
            options.put("methodname", methodName);
        }

        CfrDriver driver = new CfrDriver.Builder().withOptions(options).withOutputSink(mySink).build();
        List<String> toAnalyse = new ArrayList<String>();
        toAnalyse.add(classFilePath);
        driver.analyse(toAnalyse);

        return result.toString();
    }


}
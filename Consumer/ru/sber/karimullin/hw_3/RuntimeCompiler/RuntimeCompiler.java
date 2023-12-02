package ru.sber.karimullin.hw_3.RuntimeCompiler;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Collections;

public class RuntimeCompiler {
    private static final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    private static final StandardJavaFileManager fileManager = compiler.getStandardFileManager(
            null, null, null);
    private static final File outputDir = new File("temp");

    static {
        if (!outputDir.isDirectory()) {
            outputDir.mkdirs();
        }

        try {
            fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Collections.singletonList(outputDir));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File getFile() {
        return outputDir.getAbsoluteFile();
    }

    public static void compile(String name, String toCompile) {
        JavaFileObject fileObject = new JavaSourceFromString(name, toCompile);
        Iterable<? extends JavaFileObject> compilationUnits = Collections.singletonList(fileObject);
        compiler.getTask(null, fileManager, null, null, null, compilationUnits).call();
    }

    static class JavaSourceFromString extends SimpleJavaFileObject {
        final String code;
        JavaSourceFromString(String name, String code) {
            super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.code = code;
        }
        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return code;
        }
    }
}

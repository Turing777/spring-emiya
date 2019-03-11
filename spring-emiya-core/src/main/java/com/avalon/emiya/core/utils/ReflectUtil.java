package com.avalon.emiya.core.utils;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 *
 * @author huhao
 * @since 2019/3/11 15:46
 */
public class ReflectUtil {

    private static final String FILE_PROTOCOL = "file";

    private static final String JAR_PROTOCOL = "jar";

    private static final String SUFFIX = ".class";

    public static Set<Class<?>> getClasses(String packageName) {
        if (packageName == null || "".equals(packageName)) {
            return Collections.emptySet();
        }
        String packagePath = packageName.replace(".", "/");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Set<Class<?>> classes = new HashSet<>();

        try {
            Enumeration<URL> dirs = classLoader.getResources(packagePath);
            while (dirs.hasMoreElements()) {
                URL fileUrl = dirs.nextElement();
                String filePath = fileUrl.getPath();
                if (FILE_PROTOCOL.equalsIgnoreCase(fileUrl.getProtocol())) {
                    classes.addAll(getClassesByFilePath(filePath, packagePath));
                } else if (JAR_PROTOCOL.equals(fileUrl.getProtocol())) {
                    //处理Jar包中的Class
                    JarURLConnection jarURLConnection = (JarURLConnection) fileUrl.openConnection();
                    JarFile jarFile = jarURLConnection.getJarFile();
                    classes.addAll(getClassesByJar(jarFile));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }

    private static Set<Class<?>> getClassesByFilePath(String filePath, String packagePath) {
        File file = new File(filePath);
        Set<Class<?>> classes = new HashSet<>();
        File[] chirldFiles = file.listFiles();
        if (chirldFiles == null) {
            return classes;
        }
        for (File chirldFile : chirldFiles) {
            String path = chirldFile.getAbsolutePath();
            path = path.replaceAll("\\\\", "/");
            if (!chirldFile.isDirectory() && path.endsWith(SUFFIX)) {
                String className = path.substring(path.indexOf(packagePath), path.lastIndexOf(SUFFIX))
                        .replaceAll("/", ".");
                try {
                    Class clazz = Class.forName(className);
                    classes.add(clazz);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                classes.addAll(getClassesByFilePath(path, packagePath));
            }
        }
        return classes;
    }

    private static Set<Class<?>> getClassesByJar(JarFile jarFile) {
        Set<Class<?>> classes = new HashSet<>();
        try {
            Enumeration<JarEntry> jarEntries = jarFile.entries();
            while (jarEntries.hasMoreElements()) {
                JarEntry jarEntry = jarEntries.nextElement();
                String entryName = jarEntry.getName();
                if (!jarEntry.isDirectory() && entryName.endsWith(SUFFIX)) {
                    String className = entryName.substring(0, entryName.lastIndexOf(SUFFIX))
                            .replaceAll("/", ".");
                    Class clazz = Class.forName(className);
                    classes.add(clazz);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return classes;
    }
}

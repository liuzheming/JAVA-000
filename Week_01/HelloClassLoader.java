package week_01;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: 使用类加载器，加载一个class文件，并调用其实例的方法
 * <p>
 * Created by lzm on 2020/10/20.
 */
public class HelloClassLoader extends ClassLoader {

    private static Logger LOGGER = LoggerFactory.getLogger(HelloClassLoader.class);


    public static void main(String... args) throws Exception {

        try {
            Class clazz = new HelloClassLoader().findClass("Hello"); // 加载并初始化hello类
            LOGGER.info("clazz:{}", clazz);
            Object obj = clazz.newInstance();
            Method method = clazz.getMethod("hello");
            if (method != null) {
                method.invoke(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将文件读取为byte[],再将每个byte取反
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        File file = new File("/Users/lzm/Desktop/job/prepare/week1/Hello/Hello.xlass");
        byte[] byteArr = new byte[(int) file.length()];

        try {
            new FileInputStream(file).read(byteArr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        byte[] bytes = new byte[(int) file.length()];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (~byteArr[i]);
        }

        return defineClass(name, bytes, 0, bytes.length);
    }


    private byte[] decode(String str) {
        return java.util.Base64.getDecoder().decode(str);
    }
}

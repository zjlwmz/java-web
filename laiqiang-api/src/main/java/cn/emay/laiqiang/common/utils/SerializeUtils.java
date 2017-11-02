package cn.emay.laiqiang.common.utils;

import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**序列化 反序列化工具
 * Created by hjs on 16/4/10.
 */
public class SerializeUtils {

    private static final Logger logger = Logger.getLogger(SerializeUtils.class);

    /**
     * 序列化 返回字节
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return  bytes;
        } catch (Exception e) {
            logger.info("context", e);
        }
        return null;
    }

    /**
     * 序列化 返回字符串
     * @param object
     * @return
     */
    public static String serialize2(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            return baos.toString("ISO-8859-1");
        } catch (Exception e) {
            logger.info("context", e);
        }
        return null;
    }

    /**
     * 反序列化
     * @param bytes
     * @return
     */
    public static Object unSerialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois =new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            logger.info("context", e);
        }
        return null;
    }


}


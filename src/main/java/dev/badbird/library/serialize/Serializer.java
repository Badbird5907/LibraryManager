package dev.badbird.library.serialize;

import java.io.*;

public interface Serializer {
    <T> T deserialize(InputStream is, Class<T> clazz);
    void serialize(Object obj, OutputStream os);

    default void serialize(File out, Object o) {
        try {
            FileOutputStream fos = new FileOutputStream(out);
            serialize(o, fos);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    default <T> T deserialize(File in, Class<T> clazz) {
        try {
            FileInputStream fis = new FileInputStream(in);
            return deserialize(fis, clazz);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

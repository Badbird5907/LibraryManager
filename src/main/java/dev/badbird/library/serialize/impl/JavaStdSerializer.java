package dev.badbird.library.serialize.impl;

import dev.badbird.library.serialize.Serializer;

import java.io.*;

public class JavaStdSerializer implements Serializer {
    private static JavaStdSerializer instance = new JavaStdSerializer();

    public static JavaStdSerializer getInstance() {
        return instance;
    }
    private JavaStdSerializer() {
        if (instance != null) throw new IllegalArgumentException("singleton");
    }

    @Override
    public <T> T deserialize(InputStream is, Class<T> clazz) {
        // sanity check; we only support serializable objects
        if (!(Serializable.class.isAssignableFrom(clazz))) {
            throw new IllegalArgumentException("Not serializable");
        }
        // construct object from input stream
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void serialize(Object obj, OutputStream os) {
        try {
            // use OOS to serialize object
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

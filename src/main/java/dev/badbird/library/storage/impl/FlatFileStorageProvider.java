package dev.badbird.library.storage.impl;

import dev.badbird.library.object.Book;
import dev.badbird.library.serialize.impl.JavaStdSerializer;
import dev.badbird.library.storage.StorageProvider;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class FlatFileStorageProvider implements StorageProvider {
    @Override
    public void saveBooks(Collection<Book> books, File ser) {
        JavaStdSerializer.getInstance().serialize(ser, books);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<Book> loadBooks(File ser) {
        if (!ser.exists()) return new HashSet<>();
        return (Set<Book>) JavaStdSerializer.getInstance().deserialize(ser, HashSet.class);
    }
}

package com.eroms.repository;

import java.io.*;
import java.util.*;

public class FileStorageEngine<T extends Serializable, ID> implements Repository<T, ID> {
    private final String storageFilePath;
    private final Map<ID, T> cacheMemory = new HashMap<>();

    public FileStorageEngine(String storageFilePath) {
        this.storageFilePath = storageFilePath;
        loadFromDisk();
    }

    @Override
    public synchronized void save(T entity) {
        ID id = extractId(entity);
        cacheMemory.put(id, entity);
        persistToDisk();
    }

    @Override
    public synchronized Optional<T> findById(ID id) {
        return Optional.ofNullable(cacheMemory.get(id));
    }

    @Override
    public synchronized List<T> findAll() {
        return new ArrayList<>(cacheMemory.values());
    }

    @Override
    public synchronized void deleteById(ID id) {
        cacheMemory.remove(id);
        persistToDisk();
    }

    @SuppressWarnings("unchecked")
    private ID extractId(T entity) {
        try {
            var method = entity.getClass().getMethod("getId");
            return (ID) method.invoke(entity);
        } catch (Exception e) {
            try {
                var method = entity.getClass().getMethod("getOrderId");
                return (ID) method.invoke(entity);
            } catch (Exception ex) {
                try {
                    var method = entity.getClass().getMethod("getUserId");
                    return (ID) method.invoke(entity);
                } catch (Exception exc) {
                    throw new IllegalArgumentException("Target entity lacks standard identification getter method.");
                }
            }
        }
    }

    private void persistToDisk() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storageFilePath))) {
            oos.writeObject(cacheMemory);
        } catch (IOException ignored) {}
    }

    @SuppressWarnings("unchecked")
    private void loadFromDisk() {
        File file = new File(storageFilePath);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Map<ID, T> loaded = (Map<ID, T>) ois.readObject();
            cacheMemory.putAll(loaded);
        } catch (Exception ignored) {}
    }
}
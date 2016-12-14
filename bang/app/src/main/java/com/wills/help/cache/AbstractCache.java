package com.wills.help.cache;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.wills.help.utils.AppConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * com.wills.help.cache
 * Created by lizhaoyong
 * 2016/12/13.
 */

public abstract class AbstractCache<K, V> implements Map<K, V>{

    protected HashMap<K, V> cache;
    private Context context;

    public AbstractCache(Context context) {
        this.context = context;
        cache = new HashMap<>();
    }

    public synchronized void setCache(K k, V v){
        File file = new File(getDir()+"/"+getFile(k));
        try {
            file.createNewFile();
            file.deleteOnExit();
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
            writeCache(outputStream,v);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized V removeCache(Object k){
        return cache.remove(k);
    }

    private String getDir(){
        String root = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            root = Environment.getExternalStorageDirectory().getAbsolutePath()+ AppConfig.CACHE_DIR;
        }else {
            root = context.getCacheDir().getAbsolutePath();
        }
        File file = new File(root);
        if (!file.exists()){
            file.mkdirs();
        }
        return root;
    }

    public File getCache(K k){
        return new File(getDir() + "/" + getFile(k));
    }

    public abstract String getFile(K k);
    public abstract void writeCache(ObjectOutputStream outputStream, V value) throws IOException;
    public abstract V readCache(K k) throws IOException;

    @Override
    public void clear() {
        cache.clear();
        File[] files = new File(getDir()).listFiles();
        if (files == null){
            return;
        }
        for (File file : files){
            file.delete();
        }
    }

    @Override
    public boolean containsKey(Object o) {
        return cache.containsKey(o);
    }

    @Override
    public boolean containsValue(Object o) {
        return cache.containsValue(o);
    }

    @NonNull
    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    @Override
    public V get(Object o) {
        K k = (K) o;
        V v = null;
        v = cache.get(k);
        if (v!=null){
            return v;
        }
        try {
            v = readCache(k);
        }catch (IOException e){
            e.printStackTrace();
        }
        if (v!=null){
            cache.put(k,v);
        }
        return v;
    }

    @Override
    public boolean isEmpty() {
        return cache.isEmpty();
    }

    @NonNull
    @Override
    public Set<K> keySet() {
        return cache.keySet();
    }

    @Override
    public V put(K k, V v) {
        cache.put(k, v);
        setCache(k, v);
        return v;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {

    }

    @Override
    public synchronized V remove(Object o) {
        V v  = removeCache(o);
        File file = getCache((K) o);
        if (file.exists()){
            file.delete();
        }
        return v;
    }

    @Override
    public int size() {
        return cache.size();
    }

    @NonNull
    @Override
    public Collection<V> values() {
        return null;
    }
}

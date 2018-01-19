package com.swuos.mobile.utils.json;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Vector;

/**
 * JSON序列化支持
 *
 * @author wangyu
 */
public class JsonUtil {

    /**
     * 将java对象转换为JSONObject
     *
     * @param object
     * @return 如果转换失败，将返回null
     */
    public static JSONObject toJSONObject(Object object) {
        JSONObject jsonObject = new JSONObject();
        Class<?> currentClass = object.getClass();
        while (currentClass != Object.class) {
            Field[] fields = currentClass.getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAccessible())
                    field.setAccessible(true);

                if (field.isAnnotationPresent(JsonTransparent.class)) {
                    // 忽略掉JsonTransparent注解的部分
                    continue;
                }

                int modifiers = field.getModifiers();
                if (Modifier.isFinal(modifiers) || Modifier.isStatic(modifiers)) {
                    // 忽略掉static 和 final 修饰的变量
                    continue;
                }

                String fieldName;
                if (field.isAnnotationPresent(JsonField.class)) {
                    fieldName = field.getAnnotation(JsonField.class).value();
                } else {
                    fieldName = field.getName();
                }

                try {
                    Object value = getJsonObject(field.get(object));
                    if (value == null) {
                        continue;
                    } else {
                        jsonObject.put(fieldName, value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            currentClass = currentClass.getSuperclass();
        }
        return jsonObject;
    }

    public static JSONArray toJSONArray(List<?> datas) {
        JSONArray jsonArray = new JSONArray();
        for (Object object : datas) {
            jsonArray.put(getJsonObject(object));
        }
        return jsonArray;
    }

    public static JSONArray toJSONArray(Object[] datas) {
        JSONArray jsonArray = new JSONArray();
        for (Object object : datas) {
            jsonArray.put(getJsonObject(object));
        }
        return jsonArray;
    }

    /**
     * 将java对象转换为json支持的类型
     *
     * @param target java类型
     * @return json类型
     */
    private static Object getJsonObject(Object target) {
        Object result;
        try {
            if (target == null) {
                return null;
            }
            Class<?> fieldType = target.getClass();
            if (fieldType.isArray()) {
                int length = Array.getLength(target);
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < length; i++) {
                    Object arrayValue = Array.get(target, i);
                    Object value = getJsonObject(arrayValue);
                    if (value != null)
                        jsonArray.put(value);
                }
                result = jsonArray;
            } else if (Collection.class.isAssignableFrom(fieldType)) {
                // 集合类型转换
                Collection<?> list = (Collection<?>) target;
                JSONArray jsonArray = new JSONArray();
                for (Object object : list) {
                    Object value = getJsonObject(object);
                    if (value != null)
                        jsonArray.put(value);
                }
                result = jsonArray;
            } else if (Map.class.isAssignableFrom(fieldType)) {
                throw new RuntimeException("不支持 Map 类型的数据转换");
            } else if (fieldType == Integer.class || fieldType == Character.class || fieldType == Double.class
                    || fieldType == Float.class || fieldType == Byte.class || fieldType == Long.class
                    || fieldType == Short.class || fieldType == String.class || fieldType == Boolean.class) {
                // 基本数据类型转换
                result = target;
            } else {
                result = toJSONObject(target);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }

    /**
     * 将JSONArray转换为
     *
     * @param jsonArray  JSON数组
     * @param entityType 对应的JAVA类型
     * @param <V>
     * @return 转换后的java对象
     */
    public static <V> List<V> toList(JSONArray jsonArray, Class<V> entityType) {
        List<V> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                V v;

                String json = jsonArray.getString(i);
                if (json.startsWith("{")) {
                    JSONObject jsonObject = new JSONObject(json);
                    v = toObject(jsonObject, entityType);
                } else {
                    v = (V) getJavaObject(json, entityType, null);
                }

                if (v != null) {
                    list.add(v);
                } else {
                    Log.d("json", "json序列化失败：" + json);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    /**
     * 将jsonObject转换为java对象
     *
     * @return 转换失败，返回null
     */
    public static <V> V toObject(JSONObject jsonObject, Class<V> entityType) {
        if (jsonObject == null) {
            throw new NullPointerException("jsonObject == null");
        }
        V v;
        try {
            v = entityType.newInstance();
            Class<?> currentClass = entityType;
            while (currentClass != Object.class) {
                Field[] fields = currentClass.getDeclaredFields();
                for (Field field : fields) {
                    try {
                        if (field.isAnnotationPresent(JsonTransparent.class)) {
                            // 忽略掉JsonTransparent注解的部分
                            continue;
                        }

                        int modifiers = field.getModifiers();
                        if (Modifier.isFinal(modifiers) || Modifier.isStatic(modifiers)) {
                            // 忽略掉static 和 final 修饰的变量
                            continue;
                        }

                        if (!field.isAccessible())
                            field.setAccessible(true);

                        String fieldName;
                        if (field.isAnnotationPresent(JsonField.class)) {
                            fieldName = field.getAnnotation(JsonField.class).value();
                        } else {
                            fieldName = field.getName();
                        }

                        String StringObject = jsonObject.optString(fieldName, null);
                        if (StringObject == null) {
                            // 跳过json中不存在的字段
                            continue;
                        }

                        try {
                            Object value = getJavaObject(StringObject, field.getType(), field);
                            field.set(v, value);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                currentClass = currentClass.getSuperclass();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            v = null;
        }
        return v;
    }

    @SuppressWarnings("unchecked")
    private static Object getJavaObject(String jsonString, Class<?> targetType, Field field) throws JSONException,
            InstantiationException, IllegalAccessException {
        Object result = null;
        if (targetType == Integer.class || targetType == int.class) {
            result = Integer.parseInt(jsonString);
        } else if (targetType == Character.class || targetType == char.class) {
            result = Character.valueOf((char) Integer.parseInt(jsonString));
        } else if (targetType == Double.class || targetType == double.class) {
            result = Double.parseDouble(jsonString);
        } else if (targetType == Float.class || targetType == float.class) {
            result = Float.parseFloat(jsonString);
        } else if (targetType == Byte.class || targetType == byte.class) {
            result = Byte.parseByte(jsonString);
        } else if (targetType == Long.class || targetType == long.class) {
            result = Long.parseLong(jsonString);
        } else if (targetType == Short.class || targetType == short.class) {
            result = Short.parseShort(jsonString);
        } else if (targetType == Boolean.class || targetType == boolean.class) {
            result = Boolean.parseBoolean(jsonString);
        } else if (targetType == String.class) {
            result = jsonString;
        } else if (targetType.isArray()) {
            // 数组类型
            JSONArray jsonArray = new JSONArray(jsonString);
            int length = jsonArray.length();
            result = Array.newInstance(targetType.getComponentType(), length);
            for (int i = 0; i < length; i++) {
                String json = jsonArray.getString(i);
                Array.set(result, i, getJavaObject(json, targetType.getComponentType(), null));
            }
        } else if (Collection.class.isAssignableFrom(targetType)) {
            JSONArray jsonArray = new JSONArray(jsonString);
            int length = jsonArray.length();
            Collection<Object> list;
            if (targetType.equals(List.class)) {
                list = new ArrayList<Object>();
            } else if (targetType.equals(Set.class)) {
                list = new HashSet<Object>();
            } else if (targetType.equals(Queue.class) || targetType.equals(Deque.class)) {
                list = new LinkedList<Object>();
            } else if (targetType.equals(ArrayList.class) || targetType.equals(LinkedList.class)
                    || targetType.equals(HashSet.class) || targetType.equals(TreeSet.class)
                    || targetType.equals(Vector.class) || targetType.equals(Stack.class)) {
                list = (Collection<Object>) targetType.newInstance();
            } else {
                throw new RuntimeException("不支持的java集合类型！");
            }
            for (int i = 0; i < length; i++) {
                String json = jsonArray.getString(i);
                list.add(getJavaObject(json, getCollectionClass(field), null));
            }
            result = list;
        } else if (Map.class.isAssignableFrom(targetType)) {
            throw new RuntimeException("不支持 Map 类型的数据转换");
        } else {
            result = toObject(new JSONObject(jsonString), targetType);
        }
        return result;
    }

    /** 获取泛型类型 */
    private static Class<?> getCollectionClass(Field field) {
        Class<?> entityClass = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
        return entityClass;
    }
}

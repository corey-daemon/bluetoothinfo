/*
 * Copyright (C) 2016 Intelli4u
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.intelli4u.bluetooth.info;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.util.Log;

public final class Reflect {
    private static final String TAG = "BluetoothInfo.Reflect";

    public static Object forName(String className) {
        try {
            final Class<?> clazz = Class.forName(className);
            return clazz.getConstructors()[0].newInstance();
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "ClassNotFoundException of class.forName for " + className);
        } catch (InstantiationException e) {
            Log.e(TAG, "InstantiationException of class.forName for " + className);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "IllegalAccessException of class.forName for " + className);
        } catch (InvocationTargetException e) {
            Log.e(TAG, "InvocationTargetException of class.forName for " + className);
        }

        return null;
    }

    public static String executeMethod(Object obj, String name) {
        final Class<?> clazz = obj.getClass();
        try {
            final Method method = clazz.getMethod(name);
            return (String) method.invoke(obj);
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "NoSuchMethodException of executeMethod for " + name);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "IllegalAccessException of executeMethod for " + name);
        } catch (InvocationTargetException e) {
            Log.e(TAG, "InvocationTargetException of executeMethod for " + name);
        }

        return null;
    }

    public static Object executeMethod(Object obj, String name, Class<?>[] parameters, Object... args) {
        final Class<?> clazz = obj.getClass();
        try {
            final Method method = clazz.getMethod(name, parameters);
            return method.invoke(obj, args);
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "NoSuchMethodException of executeMethod for " + name);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "IllegalAccessException of executeMethod for " + name);
        } catch (InvocationTargetException e) {
            Log.e(TAG, "InvocationTargetException of executeMethod for " + name);
        }

        return null;
    }

    public static int getInt(Class<?> clazz, String name) {
        try {
            final Field field = clazz.getField(name);
            return field.getInt(clazz);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "IllegalAccessException of getInt for " + name);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "IllegalArgumentException of getInt for " + name);
        } catch (NoSuchFieldException e) {
            Log.e(TAG, "NoSuchFieldException of getInt for " + name);
        }

        return -1;
    }
}
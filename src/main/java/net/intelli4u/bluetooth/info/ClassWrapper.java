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

import android.bluetooth.BluetoothClass;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassWrapper {
    public static final int PROFILE_A2DP;
    public static final int PROFILE_HEADSET;
    public static final int PERIPHERAL_KEYBOARD;
    public static final int PERIPHERAL_KEYBOARD_POINTING;
    public static final int PERIPHERAL_POINTING;

    static {
        PROFILE_A2DP = reflectConstant(BluetoothClass.class, "PROFILE_A2DP");
        PROFILE_HEADSET = reflectConstant(BluetoothClass.class, "PROFILE_HEADSET");
        PERIPHERAL_KEYBOARD = reflectConstant(BluetoothClass.Device.class, "PERIPHERAL_KEYBOARD");
        PERIPHERAL_KEYBOARD_POINTING = reflectConstant(BluetoothClass.Device.class,
                "PERIPHERAL_KEYBOARD_POINTING");
        PERIPHERAL_POINTING = reflectConstant(BluetoothClass.Device.class, "PERIPHERAL_POINTING");
    }

    private final BluetoothClass mBluetoothClass;

    public ClassWrapper(BluetoothClass bluetoothClass) {
        mBluetoothClass = bluetoothClass;
    }

    public int getMajorDeviceClass() {
        return mBluetoothClass.getMajorDeviceClass();
    }

    public int getDeviceClass() {
        return mBluetoothClass.getDeviceClass();
    }

    public boolean doesClassMatch(int profile) {
        return (Boolean) reflectMethod(mBluetoothClass, "doesClassMatch",
                new Class<?>[] {Integer.class}, profile);
    }

    private static Object reflectMethod(Object obj, String name, Class<?>[] parameters, Object args) {
        final Class<?> clazz = obj.getClass();
        try {
            final Method method = clazz.getMethod(name, parameters);
            return method.invoke(obj, args);
        } catch (NoSuchMethodException e) {
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }

        return null;
    }

    private static int reflectConstant(Class<?> clazz, String name) {
        try {
            final Field field = clazz.getField(name);
            return field.getInt(clazz);
        } catch (IllegalAccessException e) {
        } catch (IllegalArgumentException e) {
        } catch (NoSuchFieldException e) {
        }

        return -1;
    }
}

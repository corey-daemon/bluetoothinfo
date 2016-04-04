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

public class ClassWrapper {
    public static final int PROFILE_A2DP;
    public static final int PROFILE_HEADSET;
    public static final int PERIPHERAL_KEYBOARD;
    public static final int PERIPHERAL_KEYBOARD_POINTING;
    public static final int PERIPHERAL_POINTING;

    static {
        PROFILE_A2DP = Reflect.getInt(BluetoothClass.class, "PROFILE_A2DP");
        PROFILE_HEADSET = Reflect.getInt(BluetoothClass.class, "PROFILE_HEADSET");
        PERIPHERAL_KEYBOARD = Reflect.getInt(BluetoothClass.Device.class, "PERIPHERAL_KEYBOARD");
        PERIPHERAL_KEYBOARD_POINTING = Reflect.getInt(BluetoothClass.Device.class,
                "PERIPHERAL_KEYBOARD_POINTING");
        PERIPHERAL_POINTING = Reflect.getInt(BluetoothClass.Device.class, "PERIPHERAL_POINTING");
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
        return (Boolean) Reflect.executeMethod(mBluetoothClass, "doesClassMatch",
                new Class<?>[] {int.class}, profile);
    }
}

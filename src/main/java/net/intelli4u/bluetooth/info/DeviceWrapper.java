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
import android.bluetooth.BluetoothDevice;
import android.os.ParcelUuid;

public class DeviceWrapper {
    private final BluetoothDevice mDevice;

    public DeviceWrapper(BluetoothDevice device) {
        mDevice = device;
    }

    public String getAddress() {
        return mDevice.getAddress();
    }

    public String getAlias() {
        return Reflect.executeMethod(mDevice, "getAlias");
    }

    public String getAliasName() {
        return Reflect.executeMethod(mDevice, "getAliasName");
    }

    public BluetoothClass getBluetoothClass() {
        return mDevice.getBluetoothClass();
    }

    public LocalBluetoothClass getLocalBluetoothClass() {
        return new LocalBluetoothClass(getBluetoothClass());
    }

    public String getName() {
        return mDevice.getName();
    }

    public int getType() {
        return mDevice.getType();
    }

    public ParcelUuid[] getUuids() {
        return mDevice.getUuids();
    }

    public BluetoothDevice getDevice() {
        return mDevice;
    }
}

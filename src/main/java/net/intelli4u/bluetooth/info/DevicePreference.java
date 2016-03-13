/*
 * Copyright (C) 2014 Intelli4u
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
import android.content.Context;
import android.content.Intent;
import android.preference.Preference;

public class DevicePreference extends Preference implements Preference.OnPreferenceClickListener {
    private final BluetoothDevice mDevice;

    public DevicePreference(Context context, BluetoothDevice device) {
        super(context);

        mDevice = device;

        setTitle(mDevice.getName());
        setSummary(mDevice.getAddress());
        int iconId = getBluetoothClassDrawable(mDevice);
        setIcon(iconId);

        setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        Intent intent = new Intent(getContext(), DeviceInfo.class);
        intent.putExtra(Constants.EXTRA_DEVICE, mDevice);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        getContext().startActivity(intent);
        return true;
    }

    public static int getBluetoothClassDrawable(final BluetoothDevice device) {
        BluetoothClass bluetoothClass = device.getBluetoothClass();

        if (bluetoothClass != null) {
            switch (bluetoothClass.getMajorDeviceClass()) {
            case BluetoothClass.Device.Major.COMPUTER:
                return R.drawable.ic_bt_laptop;
            case BluetoothClass.Device.Major.PHONE:
                return R.drawable.ic_bt_cellphone;
            case BluetoothClass.Device.Major.PERIPHERAL:
                switch (bluetoothClass.getDeviceClass()) {
                case BluetoothClass.Device.PERIPHERAL_KEYBOARD:
                case BluetoothClass.Device.PERIPHERAL_KEYBOARD_POINTING:
                    return R.drawable.ic_bt_keyboard_hid;
                case BluetoothClass.Device.PERIPHERAL_POINTING:
                    return R.drawable.ic_bt_pointing_hid;
                default:
                    return R.drawable.ic_bt_misc_hid;
                }
            case BluetoothClass.Device.Major.IMAGING:
                return R.drawable.ic_bt_imaging;
            default:
                break;
            }

            if (bluetoothClass.doesClassMatch(BluetoothClass.PROFILE_A2DP)) {
                return R.drawable.ic_bt_headphones_a2dp;
            } else if (bluetoothClass
                    .doesClassMatch(BluetoothClass.PROFILE_HEADSET)) {
                return R.drawable.ic_bt_headset_hfp;
            }
        }

        return 0;
    }
}

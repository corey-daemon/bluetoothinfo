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
import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceGroup;
import android.util.Log;
import android.util.SparseIntArray;

public class DeviceInfo extends PreferenceActivity {
    private static final String TAG = "DeviceInfo";

    private static final SparseIntArray SERVICE = new SparseIntArray();

    static {
        SERVICE.put(BluetoothClass.Service.LIMITED_DISCOVERABILITY,
                R.string.service_limited_discoverability);
        SERVICE.put(BluetoothClass.Service.POSITIONING, R.string.service_positioning);
        SERVICE.put(BluetoothClass.Service.NETWORKING, R.string.service_networking);
        SERVICE.put(BluetoothClass.Service.RENDER, R.string.service_render);
        SERVICE.put(BluetoothClass.Service.CAPTURE, R.string.service_capture);
        SERVICE.put(BluetoothClass.Service.OBJECT_TRANSFER, R.string.service_object_transfer);
        SERVICE.put(BluetoothClass.Service.AUDIO, R.string.service_audio);
        SERVICE.put(BluetoothClass.Service.TELEPHONY, R.string.service_telephony);
        SERVICE.put(BluetoothClass.Service.INFORMATION, R.string.service_information);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

        Intent intent = getIntent();
        if (intent != null) {
            BluetoothDevice device = intent.getParcelableExtra(Constants.EXTRA_DEVICE);
            LocalBluetoothDevice wrapper = new LocalBluetoothDevice(device);

            addPreference(DevicePreference.getBluetoothClassDrawable(wrapper),
                    R.string.device_name, wrapper.getName());
            addPreference(-1, R.string.device_alias, wrapper.getAlias());
            addPreference(-1, R.string.device_address, wrapper.getAddress());
            addPreference(-1, R.string.device_type, R.array.device_type, device.getType());

            handleBluetoothClass(wrapper);
            handleBluetoothService(wrapper);
            handleBluetoothUuids(wrapper);
        }
    }

    private void handleBluetoothService(final LocalBluetoothDevice wrapper) {
        BluetoothClass bluetoothClass = wrapper.getBluetoothClass();
        if (bluetoothClass != null) {
            final PreferenceGroup preference = new PreferenceCategory(this);

            preference.setTitle(R.string.device_service);
            preference.setEnabled(true);
            getPreferenceScreen().addPreference(preference);       

            for (int i = 0; i < SERVICE.size(); i++) {
                int service = SERVICE.keyAt(i);
                if (bluetoothClass.hasService(service)) {
                    addPreference(preference, -1,
                            getResources().getString(SERVICE.valueAt(i)),
                            "0x" + Integer.toHexString(service));
                }                         
            }
        }
    }

    private void handleBluetoothClass(final LocalBluetoothDevice wrapper) {
        BluetoothClass bluetoothClass = wrapper.getBluetoothClass();
        if (bluetoothClass != null) {
            addPreference(DevicePreference.getBluetoothClassDrawable(wrapper),
                    R.string.device_class, "0x" + bluetoothClass.toString());

            int mainClassStr = -1;
            int deviceClassStr = -1;
            switch (bluetoothClass.getMajorDeviceClass()) {
            case BluetoothClass.Device.Major.MISC:
                mainClassStr = R.string.class_major_misc;
                break;
            case BluetoothClass.Device.Major.COMPUTER:
                mainClassStr = R.string.class_major_computer;
                switch (bluetoothClass.getDeviceClass()) {
                case BluetoothClass.Device.COMPUTER_UNCATEGORIZED:
                    deviceClassStr = R.string.class_device_uncategorized;
                    break;
                case BluetoothClass.Device.COMPUTER_DESKTOP:
                    deviceClassStr = R.string.class_device_computer_desktop;
                    break;
                case BluetoothClass.Device.COMPUTER_SERVER:
                    deviceClassStr = R.string.class_device_computer_server;
                    break;
                case BluetoothClass.Device.COMPUTER_LAPTOP:
                    deviceClassStr = R.string.class_device_computer_laptop;
                    break;
                case BluetoothClass.Device.COMPUTER_HANDHELD_PC_PDA:
                    deviceClassStr = R.string.class_device_computer_handheld_pc;
                    break;
                case BluetoothClass.Device.COMPUTER_PALM_SIZE_PC_PDA:
                    deviceClassStr = R.string.class_device_computer_palm_size_pc;
                    break;
                case BluetoothClass.Device.COMPUTER_WEARABLE:
                    deviceClassStr = R.string.class_device_computer_wearable;
                    break;
                }
                break;
            case BluetoothClass.Device.Major.PHONE:
                mainClassStr = R.string.class_major_cellphone;
                switch (bluetoothClass.getDeviceClass()) {
                case BluetoothClass.Device.PHONE_UNCATEGORIZED:
                    deviceClassStr = R.string.class_device_uncategorized;
                    break;
                case BluetoothClass.Device.PHONE_CELLULAR:
                    deviceClassStr = R.string.class_device_phone_cellular;
                    break;
                case BluetoothClass.Device.PHONE_CORDLESS:
                    deviceClassStr = R.string.class_device_phone_cordless;
                    break;
                case BluetoothClass.Device.PHONE_SMART:
                    deviceClassStr = R.string.class_device_phone_smart;
                    break;
                case BluetoothClass.Device.PHONE_MODEM_OR_GATEWAY:
                    deviceClassStr = R.string.class_device_phone_modem_or_gateway;
                    break;
                case BluetoothClass.Device.PHONE_ISDN:
                    deviceClassStr = R.string.class_device_phone_isdn;
                    break;
                }
                break;
            case BluetoothClass.Device.Major.NETWORKING:
                mainClassStr = R.string.class_major_networking;
                break;
            case BluetoothClass.Device.Major.AUDIO_VIDEO:
                mainClassStr = R.string.class_major_audio_video;
                switch (bluetoothClass.getDeviceClass()) {
                case BluetoothClass.Device.AUDIO_VIDEO_UNCATEGORIZED:
                    deviceClassStr = R.string.class_device_uncategorized;
                    break;
                case BluetoothClass.Device.AUDIO_VIDEO_WEARABLE_HEADSET:
                    deviceClassStr = R.string.class_device_audio_video_headset;
                    break;
                case BluetoothClass.Device.AUDIO_VIDEO_HANDSFREE:
                    deviceClassStr = R.string.class_device_audio_video_handsfree;
                    break;
                case BluetoothClass.Device.AUDIO_VIDEO_MICROPHONE:
                    deviceClassStr = R.string.class_device_audio_vidoo_microphone;
                    break;
                case BluetoothClass.Device.AUDIO_VIDEO_LOUDSPEAKER:
                    deviceClassStr = R.string.class_device_audio_video_loudspeaker;
                    break;
                case BluetoothClass.Device.AUDIO_VIDEO_HEADPHONES:
                    deviceClassStr = R.string.class_device_audio_video_headphone;
                    break;
                case BluetoothClass.Device.AUDIO_VIDEO_PORTABLE_AUDIO:
                    deviceClassStr = R.string.class_device_audio_video_portable_audio;
                    break;
                case BluetoothClass.Device.AUDIO_VIDEO_SET_TOP_BOX:
                    deviceClassStr = R.string.class_device_audio_video_set_top_box;
                    break;
                case BluetoothClass.Device.AUDIO_VIDEO_HIFI_AUDIO:
                    deviceClassStr = R.string.class_device_audio_video_hifi_audio;
                    break;
                case BluetoothClass.Device.AUDIO_VIDEO_VCR:
                    deviceClassStr = R.string.class_device_audio_video_vcr;
                    break;
                case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_CAMERA:
                    deviceClassStr = R.string.class_device_audio_video_video_camera;
                    break;
                case BluetoothClass.Device.AUDIO_VIDEO_CAMCORDER:
                    deviceClassStr = R.string.class_device_audio_video_camcorder;
                    break;
                case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_MONITOR:
                    deviceClassStr = R.string.class_device_audio_video_video_monitor;
                    break;
                case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_DISPLAY_AND_LOUDSPEAKER:
                    deviceClassStr = R.string.class_device_audio_video_video_display_and_loudspeaker;
                    break;
                case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_CONFERENCING:
                    deviceClassStr = R.string.class_device_audio_video_video_conference;
                    break;
                case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_GAMING_TOY:
                    deviceClassStr = R.string.class_device_audio_video_gaming_toy;
                    break;
                }
                break;
            case BluetoothClass.Device.Major.PERIPHERAL:
                mainClassStr = R.string.class_major_peripheral;
                int deviceClass = bluetoothClass.getDeviceClass();
                if (deviceClass == LocalBluetoothClass.PERIPHERAL_KEYBOARD) {
                    deviceClassStr = R.string.class_device_keyboard;
                } else if (deviceClass == LocalBluetoothClass.PERIPHERAL_KEYBOARD_POINTING) {
                    deviceClassStr = R.string.class_device_keyboard_hid;
                } else if (deviceClass == LocalBluetoothClass.PERIPHERAL_POINTING) {
                    deviceClassStr = R.string.class_device_pointing;
                } else {
                    deviceClassStr = R.string.class_device_misc;
                }
                break;
            case BluetoothClass.Device.Major.IMAGING:
                mainClassStr = R.string.class_major_imaging;
                break;
            case BluetoothClass.Device.Major.WEARABLE:
                mainClassStr = R.string.class_major_wearable;
                switch (bluetoothClass.getDeviceClass()) {
                case BluetoothClass.Device.WEARABLE_UNCATEGORIZED:
                    deviceClassStr = R.string.class_device_uncategorized;
                    break;
                case BluetoothClass.Device.WEARABLE_WRIST_WATCH:
                    deviceClassStr = R.string.class_device_wearable_wrist_watch;
                    break;
                case BluetoothClass.Device.WEARABLE_PAGER:
                    deviceClassStr = R.string.class_device_wearable_pager;
                    break;
                case BluetoothClass.Device.WEARABLE_JACKET:
                    deviceClassStr = R.string.class_device_wearable_jacket;
                    break;
                case BluetoothClass.Device.WEARABLE_HELMET:
                    deviceClassStr = R.string.class_device_wearable_helmet;
                    break;
                case BluetoothClass.Device.WEARABLE_GLASSES:
                    deviceClassStr = R.string.class_device_wearable_glasses;
                    break;
                }
                break;
            case BluetoothClass.Device.Major.TOY:
                mainClassStr = R.string.class_major_toy;
                switch (bluetoothClass.getDeviceClass()) {
                case BluetoothClass.Device.TOY_UNCATEGORIZED:
                    deviceClassStr = R.string.class_device_uncategorized;
                    break;
                case BluetoothClass.Device.TOY_ROBOT:
                    deviceClassStr = R.string.class_device_toy_robot;
                    break;
                case BluetoothClass.Device.TOY_VEHICLE:
                    deviceClassStr = R.string.class_device_toy_vehicle;
                    break;
                case BluetoothClass.Device.TOY_DOLL_ACTION_FIGURE:
                    deviceClassStr = R.string.class_device_toy_doll_action_figure;
                    break;
                case BluetoothClass.Device.TOY_CONTROLLER:
                    deviceClassStr = R.string.class_device_toy_controller;
                    break;
                case BluetoothClass.Device.TOY_GAME:
                    deviceClassStr = R.string.class_device_toy_game;
                    break;
                }
                break;
            case BluetoothClass.Device.Major.HEALTH:
                mainClassStr = R.string.class_major_health;
                switch (bluetoothClass.getDeviceClass()) {
                case BluetoothClass.Device.HEALTH_UNCATEGORIZED:
                    deviceClassStr = R.string.class_device_uncategorized;
                    break;
                case BluetoothClass.Device.HEALTH_BLOOD_PRESSURE:
                    deviceClassStr = R.string.class_device_health_blood_pressure;
                    break;
                case BluetoothClass.Device.HEALTH_THERMOMETER:
                    deviceClassStr = R.string.class_device_health_thermometer;
                    break;
                case BluetoothClass.Device.HEALTH_WEIGHING:
                    deviceClassStr = R.string.class_device_health_weighing;
                    break;
                case BluetoothClass.Device.HEALTH_GLUCOSE:
                    deviceClassStr = R.string.class_device_health_glucose;
                    break;
                case BluetoothClass.Device.HEALTH_PULSE_OXIMETER:
                    deviceClassStr = R.string.class_device_health_pulse_oximeter;
                    break;
                case BluetoothClass.Device.HEALTH_PULSE_RATE:
                    deviceClassStr = R.string.class_device_health_pulse_rate;
                    break;
                case BluetoothClass.Device.HEALTH_DATA_DISPLAY:
                    deviceClassStr = R.string.class_device_health_data_display;
                    break;
                }
                break;
            case BluetoothClass.Device.Major.UNCATEGORIZED:
                mainClassStr = R.string.class_major_uncategorized;
                break;
            default:
                break;
            }

            if (mainClassStr != -1) {
                addPreference(DevicePreference.getBluetoothMajorClassDrawable(wrapper),
                        R.string.class_major,
                        String.format("%s (0x%s)",
                                getResources().getString(mainClassStr),
                                Integer.toHexString(bluetoothClass.getMajorDeviceClass())));
            }

            if (deviceClassStr != -1) {
                addPreference(DevicePreference.getBluetoothClassDrawable(wrapper),
                        R.string.class_device,
                        String.format("%s (0x%s)",
                                getResources().getString(deviceClassStr),
                                Integer.toHexString(bluetoothClass.getDeviceClass())));
            }
        }
    }

    private void handleBluetoothUuids(final LocalBluetoothDevice wrapper) {
        ParcelUuid[] uuids = wrapper.getUuids();
        if (uuids != null) {
            final PreferenceGroup preference = new PreferenceCategory(this);

            preference.setTitle(R.string.device_uuid);
            preference.setEnabled(true);
            getPreferenceScreen().addPreference(preference);

            for (ParcelUuid uuid : uuids) {
                addPreference(preference, -1,
                        getResources().getString(LocalBluetoothUuid.getProfileResId(uuid)),
                        uuid.toString());
            }
        }
    }

    private void addPreference(int drawable, int title, int summary) {
        addPreference(drawable, getResources().getString(title), getResources()
                .getString(summary));
    }

    private void addPreference(int drawable, int title, String summary) {
        addPreference(drawable, getResources().getString(title), summary);
    }

    private void addPreference(int drawable, int title, int array, int summary) {
        String[] str = getResources().getStringArray(array);
        if (summary >= 0 && summary < str.length) {
            addPreference(drawable, title, str[summary]);
        } else {
            Log.e(TAG, "size of array is " + str.length + ", but index is "
                    + summary);
        }
    }

    private void addPreference(int drawable, String title, String summary) {
        addPreference(getPreferenceScreen(), drawable, title, summary);
    }

    private void addPreference(PreferenceGroup group, int drawable, String title, String summary) {
        if (summary == null)
            return;

        Preference preference = new Preference(this);
        preference.setTitle(title);
        preference.setSummary(summary);

        if (drawable > 0) {
            preference.setIcon(drawable);
        }

        group.addPreference(preference);
    }
}

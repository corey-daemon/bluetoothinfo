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
import android.util.Log;

public class DeviceInfo extends PreferenceActivity {

    private static final String TAG = "DeviceInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

        Intent intent = getIntent();
        if (intent != null) {
            BluetoothDevice device = intent.getParcelableExtra(Constants.EXTRA_DEVICE);
            DeviceWrapper wrapper = new DeviceWrapper(device);

            addPreference(DevicePreference.getBluetoothClassDrawable(wrapper),
                    R.string.device_name, wrapper.getName());
            addPreference(-1, R.string.device_alias, wrapper.getAlias());
            addPreference(-1, R.string.device_address, wrapper.getAddress());
            addPreference(-1, R.string.device_type, R.array.device_type, device.getType());

            handleBluetoothClass(wrapper);

            ParcelUuid[] uuids = wrapper.getUuids();
            if (uuids != null) {
                for (ParcelUuid uuid : uuids) {
                    addPreference(-1, R.string.device_uuid, uuid.toString());
                }
            }
        }
    }

    private void handleBluetoothClass(final DeviceWrapper wrapper) {
        BluetoothClass bluetoothClass = wrapper.getBluetoothClass();
        if (bluetoothClass != null) {
            addPreference(DevicePreference.getBluetoothClassDrawable(wrapper),
                    R.string.device_class, "0x" + bluetoothClass.toString());

            switch (bluetoothClass.getMajorDeviceClass()) {
            case BluetoothClass.Device.Major.MISC:
                addPreference(-1, R.string.class_major, R.string.class_major_misc);
                break;
            case BluetoothClass.Device.Major.COMPUTER:
                addPreference(R.drawable.ic_bt_laptop, R.string.class_major,
                        R.string.class_major_computer);
                switch (bluetoothClass.getDeviceClass()) {
                case BluetoothClass.Device.COMPUTER_UNCATEGORIZED:
                    addPreference(-1, R.string.class_device, R.string.class_device_uncategorized);
                    break;
                case BluetoothClass.Device.COMPUTER_DESKTOP:
                    addPreference(-1, R.string.class_device,
                            R.string.class_device_computer_desktop);
                    break;
                case BluetoothClass.Device.COMPUTER_SERVER:
                    addPreference(-1, R.string.class_device,
                            R.string.class_device_computer_server);
                    break;
                case BluetoothClass.Device.COMPUTER_LAPTOP:
                    addPreference(R.drawable.ic_bt_laptop, R.string.class_device,
                            R.string.class_device_computer_laptop);
                    break;
                case BluetoothClass.Device.COMPUTER_HANDHELD_PC_PDA:
                    addPreference(-1, R.string.class_device,
                            R.string.class_device_computer_handheld_pc);
                    break;
                case BluetoothClass.Device.COMPUTER_PALM_SIZE_PC_PDA:
                    addPreference(-1, R.string.class_device,
                            R.string.class_device_computer_palm_size_pc);
                    break;
                case BluetoothClass.Device.COMPUTER_WEARABLE:
                    addPreference(-1, R.string.class_device,
                            R.string.class_device_computer_wearable);
                    break;
                }
                break;
            case BluetoothClass.Device.Major.PHONE:
                addPreference(R.drawable.ic_bt_cellphone, R.string.class_major,
                        R.string.class_major_cellphone);
                switch (bluetoothClass.getDeviceClass()) {
                case BluetoothClass.Device.PHONE_UNCATEGORIZED:
                    addPreference(-1, R.string.class_device, R.string.class_device_uncategorized);
                    break;
                case BluetoothClass.Device.PHONE_CELLULAR:
                    addPreference(-1, R.string.class_device, R.string.class_device_phone_cellular);
                    break;
                case BluetoothClass.Device.PHONE_CORDLESS:
                    addPreference(-1, R.string.class_device, R.string.class_device_phone_cordless);
                    break;
                case BluetoothClass.Device.PHONE_SMART:
                    addPreference(-1, R.string.class_device, R.string.class_device_phone_smart);
                    break;
                case BluetoothClass.Device.PHONE_MODEM_OR_GATEWAY:
                    addPreference(-1, R.string.class_device,
                            R.string.class_device_phone_modem_or_gateway);
                    break;
                case BluetoothClass.Device.PHONE_ISDN:
                    addPreference(-1, R.string.class_device, R.string.class_device_phone_isdn);
                    break;
                }
                break;
            case BluetoothClass.Device.Major.NETWORKING:
                addPreference(-1, R.string.class_major, R.string.class_major_networking);
                break;
            case BluetoothClass.Device.Major.AUDIO_VIDEO:
                addPreference(-1, R.string.class_major, R.string.class_major_audio_video);
                switch (bluetoothClass.getDeviceClass()) {
                case BluetoothClass.Device.AUDIO_VIDEO_UNCATEGORIZED:
                    addPreference(-1, R.string.class_device, R.string.class_device_uncategorized);
                    break;
                case BluetoothClass.Device.AUDIO_VIDEO_WEARABLE_HEADSET:
                    addPreference(-1, R.string.class_device,
                            R.string.class_device_audio_video_headset);
                    break;
                case BluetoothClass.Device.AUDIO_VIDEO_HANDSFREE:
                    addPreference(-1, R.string.class_device,
                            R.string.class_device_audio_video_handsfree);
                    break;
                case BluetoothClass.Device.AUDIO_VIDEO_MICROPHONE:
                    addPreference(-1, R.string.class_device,
                            R.string.class_device_audio_vidoo_microphone);
                    break;
                case BluetoothClass.Device.AUDIO_VIDEO_LOUDSPEAKER:
                    addPreference(-1, R.string.class_device,
                            R.string.class_device_audio_video_loudspeaker);
                    break;
                case BluetoothClass.Device.AUDIO_VIDEO_HEADPHONES:
                    addPreference(-1, R.string.class_device,
                            R.string.class_device_audio_video_headphone);
                    break;
                case BluetoothClass.Device.AUDIO_VIDEO_PORTABLE_AUDIO:
                    addPreference(-1, R.string.class_device,
                            R.string.class_device_audio_video_portable_audio);
                    break;
                case BluetoothClass.Device.AUDIO_VIDEO_SET_TOP_BOX:
                    addPreference(-1, R.string.class_device,
                            R.string.class_device_audio_video_set_top_box);
                    break;
                case BluetoothClass.Device.AUDIO_VIDEO_HIFI_AUDIO:
                    addPreference(-1, R.string.class_device,
                            R.string.class_device_audio_video_hifi_audio);
                    break;
                case BluetoothClass.Device.AUDIO_VIDEO_VCR:
                    addPreference(-1, R.string.class_device, R.string.class_device_audio_video_vcr);
                    break;
                case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_CAMERA:
                    addPreference(-1, R.string.class_device,
                            R.string.class_device_audio_video_video_camera);
                    break;
                case BluetoothClass.Device.AUDIO_VIDEO_CAMCORDER:
                    addPreference(-1, R.string.class_device,
                            R.string.class_device_audio_video_camcorder);
                    break;
                case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_MONITOR:
                    addPreference(-1, R.string.class_device,
                            R.string.class_device_audio_video_video_monitor);
                    break;
                case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_DISPLAY_AND_LOUDSPEAKER:
                    addPreference(-1, R.string.class_device,
                            R.string.class_device_audio_video_video_display_and_loudspeaker);
                    break;
                case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_CONFERENCING:
                    addPreference(-1, R.string.class_device,
                            R.string.class_device_audio_video_video_conference);
                    break;
                case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_GAMING_TOY:
                    addPreference(-1, R.string.class_device,
                            R.string.class_device_audio_video_gaming_toy);
                    break;
                }
                break;
            case BluetoothClass.Device.Major.PERIPHERAL:
                addPreference(-1, R.string.class_major, R.string.class_major_peripheral);
                int deviceClass = bluetoothClass.getDeviceClass();
                if (deviceClass == ClassWrapper.PERIPHERAL_KEYBOARD) {
                    addPreference(R.drawable.ic_bt_keyboard_hid, R.string.class_device,
                            R.string.class_device_keyboard);
                } else if (deviceClass == ClassWrapper.PERIPHERAL_KEYBOARD_POINTING) {
                    addPreference(R.drawable.ic_bt_keyboard_hid, R.string.class_device,
                            R.string.class_device_keyboard_hid);
                } else if (deviceClass == ClassWrapper.PERIPHERAL_POINTING) {
                    addPreference(R.drawable.ic_bt_pointing_hid, R.string.class_device,
                            R.string.class_device_pointing);
                } else {
                    addPreference(R.drawable.ic_bt_misc_hid,
                            R.string.class_device, R.string.class_device_misc);
                }
                break;
            case BluetoothClass.Device.Major.IMAGING:
                addPreference(R.drawable.ic_bt_imaging, R.string.class_major,
                        R.string.class_major_imaging);
                break;
            case BluetoothClass.Device.Major.WEARABLE:
                addPreference(-1, R.string.class_major, R.string.class_major_wearable);
                switch (bluetoothClass.getDeviceClass()) {
                case BluetoothClass.Device.WEARABLE_UNCATEGORIZED:
                    addPreference(-1, R.string.class_device, R.string.class_device_uncategorized);
                    break;
                case BluetoothClass.Device.WEARABLE_WRIST_WATCH:
                    addPreference(-1, R.string.class_device,
                            R.string.class_device_wearable_wrist_watch);
                    break;
                case BluetoothClass.Device.WEARABLE_PAGER:
                    addPreference(-1, R.string.class_device, R.string.class_device_wearable_pager);
                    break;
                case BluetoothClass.Device.WEARABLE_JACKET:
                    addPreference(-1, R.string.class_device,
                            R.string.class_device_wearable_jacket);
                    break;
                case BluetoothClass.Device.WEARABLE_HELMET:
                    addPreference(-1, R.string.class_device,
                            R.string.class_device_wearable_helmet);
                    break;
                case BluetoothClass.Device.WEARABLE_GLASSES:
                    addPreference(-1, R.string.class_device,
                            R.string.class_device_wearable_glasses);
                    break;
                }
                break;
            case BluetoothClass.Device.Major.TOY:
                addPreference(-1, R.string.class_major, R.string.class_major_toy);
                switch (bluetoothClass.getDeviceClass()) {
                case BluetoothClass.Device.TOY_UNCATEGORIZED:
                    addPreference(-1, R.string.class_device, R.string.class_device_uncategorized);
                    break;
                case BluetoothClass.Device.TOY_ROBOT:
                    addPreference(-1, R.string.class_device, R.string.class_device_toy_robot);
                    break;
                case BluetoothClass.Device.TOY_VEHICLE:
                    addPreference(-1, R.string.class_device, R.string.class_device_toy_vehicle);
                    break;
                case BluetoothClass.Device.TOY_DOLL_ACTION_FIGURE:
                    addPreference(-1, R.string.class_device,
                            R.string.class_device_toy_doll_action_figure);
                    break;
                case BluetoothClass.Device.TOY_CONTROLLER:
                    addPreference(-1, R.string.class_device, R.string.class_device_toy_controller);
                    break;
                case BluetoothClass.Device.TOY_GAME:
                    addPreference(-1, R.string.class_device, R.string.class_device_toy_game);
                    break;
                }
                break;
            case BluetoothClass.Device.Major.HEALTH:
                addPreference(-1, R.string.class_major, R.string.class_major_health);
                switch (bluetoothClass.getDeviceClass()) {
                case BluetoothClass.Device.HEALTH_UNCATEGORIZED:
                    addPreference(-1, R.string.class_device, R.string.class_device_uncategorized);
                    break;
                case BluetoothClass.Device.HEALTH_BLOOD_PRESSURE:
                    addPreference(-1, R.string.class_device,
                            R.string.class_device_health_blood_pressure);
                    break;
                case BluetoothClass.Device.HEALTH_THERMOMETER:
                    addPreference(-1, R.string.class_device,
                            R.string.class_device_health_thermometer);
                    break;
                case BluetoothClass.Device.HEALTH_WEIGHING:
                    addPreference(-1, R.string.class_device, R.string.class_device_health_weighing);
                    break;
                case BluetoothClass.Device.HEALTH_GLUCOSE:
                    addPreference(-1, R.string.class_device, R.string.class_device_health_glucose);
                    break;
                case BluetoothClass.Device.HEALTH_PULSE_OXIMETER:
                    addPreference(-1, R.string.class_device,
                            R.string.class_device_health_pulse_oximeter);
                    break;
                case BluetoothClass.Device.HEALTH_PULSE_RATE:
                    addPreference(-1, R.string.class_device,
                            R.string.class_device_health_pulse_rate);
                    break;
                case BluetoothClass.Device.HEALTH_DATA_DISPLAY:
                    addPreference(-1, R.string.class_device,
                            R.string.class_device_health_data_display);
                    break;
                }
                break;
            case BluetoothClass.Device.Major.UNCATEGORIZED:
                addPreference(-1, R.string.class_major, R.string.class_major_uncategorized);
                break;
            default:
                break;
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
        if (summary == null)
            return;

        Preference preference = new Preference(this);
        preference.setTitle(title);
        preference.setSummary(summary);

        if (drawable != -1) {
            preference.setIcon(drawable);
        }

        getPreferenceScreen().addPreference(preference);
    }
}

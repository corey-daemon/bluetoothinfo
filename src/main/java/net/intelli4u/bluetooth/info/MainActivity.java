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

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceGroup;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Set;

public class MainActivity extends PreferenceActivity {

    private static final int MENU_ID_SCAN = Menu.FIRST;

    private BluetoothAdapter mAdapter;
    private Preference mMyDevice;

    private PreferenceGroup mDeviceCategory;

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                addDeviceCategory();
                DevicePreference preference = new DevicePreference(getBaseContext(), device);
                mDeviceCategory.addPreference(preference);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

        mAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mAdapter == null) {
            // Bluetooth isn't supported
            finish();
            return;
        }

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);

        mMyDevice = new Preference(this);
        mMyDevice.setIcon(R.drawable.ic_bt_cellphone);
        mMyDevice.setTitle(mAdapter.getName());
        mMyDevice.setSummary(mAdapter.getAddress());
        getPreferenceScreen().addPreference(mMyDevice);

        /* get the bonded device */
        Set<BluetoothDevice> bondedDevices = mAdapter.getBondedDevices();
        if (bondedDevices != null && bondedDevices.size() > 0) {
            PreferenceCategory bondedCategory = new PreferenceCategory(this);
            bondedCategory.setTitle(R.string.bluetooth_bonded_devices);
            bondedCategory.setEnabled(true);
            getPreferenceScreen().addPreference(bondedCategory);

            for (BluetoothDevice device : bondedDevices) {
                DevicePreference preference = new DevicePreference(getBaseContext(), device);
                bondedCategory.addPreference(preference);
            }
        }

        if (!mAdapter.isEnabled()) {
            // Show dialog to open the Bluetooth
            // https://developer.android.com/guide/topics/connectivity/bluetooth.html
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(intent);
        } else if (!mAdapter.isDiscovering()) {
            mAdapter.startDiscovery();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        if (mAdapter == null)
            return false;

        boolean isEnabled = mAdapter.getState() == BluetoothAdapter.STATE_ON;
        boolean isDiscovering = mAdapter.isDiscovering();

        int textId = isDiscovering ? R.string.bluetooth_searching_for_devices
                : R.string.bluetooth_search_for_devices;

        menu.add(Menu.NONE, MENU_ID_SCAN, 0, textId)
                .setEnabled(isEnabled && !isDiscovering)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case MENU_ID_SCAN:
            Intent intent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            startActivity(intent);
            break;
        }

        return true;
    }

    private void addDeviceCategory() {
        if (mDeviceCategory == null) {
            mDeviceCategory = new PreferenceCategory(this);

            mDeviceCategory.setTitle(R.string.bluetooth_devices);
            mDeviceCategory.setEnabled(true);
            getPreferenceScreen().addPreference(mDeviceCategory);
        }
    }
}

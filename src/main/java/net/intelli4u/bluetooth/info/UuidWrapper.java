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

import android.os.ParcelUuid;
import android.util.Log;

public class UuidWrapper {
    private static final String BLUETOOTH_UUID_CLASS = "android.bluetooth.BluetoothUuid";

    private static final ParcelUuid BASE_UUID =
            ParcelUuid.fromString("00000000-0000-1000-8000-00805F9B34FB");
    private static final ParcelUuid HANDSFREE_AG =
            ParcelUuid.fromString("0000111F-0000-1000-8000-00805F9B34FB");
    private static final ParcelUuid HSP_AG =
            ParcelUuid.fromString("00001112-0000-1000-8000-00805F9B34FB");
    private static final ParcelUuid PBAP_PCE =
            ParcelUuid.fromString("0000112e-0000-1000-8000-00805F9B34FB");
    private static final ParcelUuid PBAP_PSE =
            ParcelUuid.fromString("0000112f-0000-1000-8000-00805F9B34FB");
    private static final ParcelUuid OBEX_OBJECT_PUSH =
            ParcelUuid.fromString("00001105-0000-1000-8000-00805f9b34fb");

    private static Object mObject;

    static {
        mObject = Reflect.forName(BLUETOOTH_UUID_CLASS);
    }

    private static boolean hasProfile(ParcelUuid uuid, String methodName) {
        if (mObject != null) {
            Object ret = Reflect.executeMethod(mObject, methodName,
                    new Class<?>[] {ParcelUuid.class}, uuid);
            return ret != null ? (Boolean) ret : false;
        } else {
            return false;
        }
    }

    public static boolean isBaseUuid(ParcelUuid uuid) {
        return BASE_UUID.equals(uuid);
    }

    public static boolean isPbapClient(ParcelUuid uuid) {
        return PBAP_PCE.equals(uuid);
    }

    public static boolean isPbapServer(ParcelUuid uuid) {
        return PBAP_PSE.equals(uuid);
    }

    public static boolean isOpp(ParcelUuid uuid) {
        return OBEX_OBJECT_PUSH.equals(uuid);
    }

    public static boolean isAudioSource(ParcelUuid uuid) {
        return hasProfile(uuid, "isAudioSource");
    }

    public static boolean isAudioSink(ParcelUuid uuid) {
        return hasProfile(uuid, "isAudioSink");
    }

    public static boolean isAdvAudioDist(ParcelUuid uuid) {
        return hasProfile(uuid, "isAdvAudioDist");
    }

    public static boolean isHandsfree(ParcelUuid uuid) {
        return hasProfile(uuid, "isHandsfree");
    }

    public static boolean isHandsfreeAg(ParcelUuid uuid) {
        return HANDSFREE_AG.equals(uuid);
    }

    public static boolean isHeadset(ParcelUuid uuid) {
        return hasProfile(uuid, "isHeadset");
    }

    public static boolean isHspAg(ParcelUuid uuid) {
        return HSP_AG.equals(uuid);
    }

    public static boolean isAvrcpController(ParcelUuid uuid) {
        return hasProfile(uuid, "isAvrcpController");
    }

    public static boolean isAvrcpTarget(ParcelUuid uuid) {
        return hasProfile(uuid, "isAvrcpTarget");
    }

    public static boolean isInputDevice(ParcelUuid uuid) {
        return hasProfile(uuid, "isInputDevice");
    }

    public static boolean isPanu(ParcelUuid uuid) {
        return hasProfile(uuid, "isPanu");
    }

    public static boolean isNap(ParcelUuid uuid) {
        return hasProfile(uuid, "isNap");
    }

    public static boolean isBnep(ParcelUuid uuid) {
        return hasProfile(uuid, "isBnep");
    }

    public static boolean isMap(ParcelUuid uuid) {
        return hasProfile(uuid, "isMap");
    }

    public static boolean isMns(ParcelUuid uuid) {
        return hasProfile(uuid, "isMns");
    }

    public static boolean isMas(ParcelUuid uuid) {
        return hasProfile(uuid, "isMas");
    }

    public static boolean isSap(ParcelUuid uuid) {
        return hasProfile(uuid, "isSap");
    }
}

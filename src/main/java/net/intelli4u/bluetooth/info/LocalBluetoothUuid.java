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

public class LocalBluetoothUuid {
    private static final String BLUETOOTH_UUID_CLASS = "android.bluetooth.BluetoothUuid";

    private static final ParcelUuid BASE_UUID =
            ParcelUuid.fromString("00000000-0000-1000-8000-00805f9b34fb");
    private static final ParcelUuid BNEP =
            ParcelUuid.fromString("0000000f-0000-1000-8000-00805f9b34fb");
    private static final ParcelUuid OBEX_OBJECT_PUSH =
            ParcelUuid.fromString("00001105-0000-1000-8000-00805f9b34fb");
    private static final ParcelUuid HSP =
            ParcelUuid.fromString("00001108-0000-1000-8000-00805f9b34fb");
    private static final ParcelUuid AUDIO_SOURCE =
            ParcelUuid.fromString("0000110a-0000-1000-8000-00805f9b34fb");
    private static final ParcelUuid AUDIO_SINK =
            ParcelUuid.fromString("0000110b-0000-1000-8000-00805f9b34fb");
    private static final ParcelUuid AVRCP_TARGET =
            ParcelUuid.fromString("0000110c-0000-1000-8000-00805f9b34fb");
    private static final ParcelUuid ADV_AUDIO_DIST =
            ParcelUuid.fromString("0000110d-0000-1000-8000-00805f9b34fb");
    private static final ParcelUuid AVRCP_CONTROLLER = 
            ParcelUuid.fromString("0000110e-0000-1000-8000-00805f9b34fb");
    private static final ParcelUuid HSP_AG =
            ParcelUuid.fromString("00001112-0000-1000-8000-00805f9b34fb");
    private static final ParcelUuid PANU =
            ParcelUuid.fromString("00001115-0000-1000-8000-00805f9b34fb");
    private static final ParcelUuid NAP =
            ParcelUuid.fromString("00001116-0000-1000-8000-00805f9b34fb");
    private static final ParcelUuid HANDSFREE =
            ParcelUuid.fromString("0000111e-0000-1000-8000-00805f9b34fb");
    private static final ParcelUuid HANDSFREE_AG =
            ParcelUuid.fromString("0000111f-0000-1000-8000-00805f9b34fb");
    private static final ParcelUuid HID =
            ParcelUuid.fromString("00001124-0000-1000-8000-00805f9b34fb");
    private static final ParcelUuid SAP =
            ParcelUuid.fromString("0000112d-0000-1000-8000-00805f9b34fb");
    private static final ParcelUuid PBAP_PCE =
            ParcelUuid.fromString("0000112e-0000-1000-8000-00805f9b34fb");
    private static final ParcelUuid PBAP_PSE =
            ParcelUuid.fromString("0000112f-0000-1000-8000-00805f9b34fb");
    private static final ParcelUuid MAS =
            ParcelUuid.fromString("00001132-0000-1000-8000-00805f9b34fb");
    private static final ParcelUuid MNS =
            ParcelUuid.fromString("00001133-0000-1000-8000-00805f9b34fb");
    private static final ParcelUuid MAP =
            ParcelUuid.fromString("00001134-0000-1000-8000-00805f9b34fb");
    private static final ParcelUuid HOGP =
            ParcelUuid.fromString("00001812-0000-1000-8000-00805f9b34fb");

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
        return hasProfile(uuid, "isAudioSource") || AUDIO_SOURCE.equals(uuid);
    }

    public static boolean isAudioSink(ParcelUuid uuid) {
        return hasProfile(uuid, "isAudioSink") || AUDIO_SINK.equals(uuid);
    }

    public static boolean isAdvAudioDist(ParcelUuid uuid) {
        return hasProfile(uuid, "isAdvAudioDist") || ADV_AUDIO_DIST.equals(uuid);
    }

    public static boolean isHandsfree(ParcelUuid uuid) {
        return hasProfile(uuid, "isHandsfree") || HANDSFREE.equals(uuid);
    }

    public static boolean isHandsfreeAg(ParcelUuid uuid) {
        return HANDSFREE_AG.equals(uuid);
    }

    public static boolean isHeadset(ParcelUuid uuid) {
        return hasProfile(uuid, "isHeadset") || HSP.equals(uuid);
    }

    public static boolean isHspAg(ParcelUuid uuid) {
        return HSP_AG.equals(uuid);
    }

    public static boolean isAvrcpController(ParcelUuid uuid) {
        return hasProfile(uuid, "isAvrcpController") || AVRCP_CONTROLLER.equals(uuid);
    }

    public static boolean isAvrcpTarget(ParcelUuid uuid) {
        return hasProfile(uuid, "isAvrcpTarget") || AVRCP_TARGET.equals(uuid);
    }

    public static boolean isInputDevice(ParcelUuid uuid) {
        return hasProfile(uuid, "isInputDevice");
    }

    public static boolean isPanu(ParcelUuid uuid) {
        return hasProfile(uuid, "isPanu") || PANU.equals(uuid);
    }

    public static boolean isNap(ParcelUuid uuid) {
        return hasProfile(uuid, "isNap") || NAP.equals(uuid);
    }

    public static boolean isBnep(ParcelUuid uuid) {
        return hasProfile(uuid, "isBnep") || BNEP.equals(uuid);
    }

    public static boolean isHogp(ParcelUuid uuid) {
        return HOGP.equals(uuid);
    }

    public static boolean isMap(ParcelUuid uuid) {
        return hasProfile(uuid, "isMap") || MAP.equals(uuid);
    }

    public static boolean isMns(ParcelUuid uuid) {
        return hasProfile(uuid, "isMns") || MNS.equals(uuid);
    }

    public static boolean isMas(ParcelUuid uuid) {
        return hasProfile(uuid, "isMas") || MAS.equals(uuid);
    }

    public static boolean isSap(ParcelUuid uuid) {
        return hasProfile(uuid, "isSap") || SAP.equals(uuid);
    }
}

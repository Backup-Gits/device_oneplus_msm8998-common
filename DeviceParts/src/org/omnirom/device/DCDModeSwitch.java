/*
* Copyright (C) 2019 The OmniROM Project
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*
*/
package org.omnirom.device;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceManager;

import org.omnirom.device.DeviceSettings;

public class DCDModeSwitch implements OnPreferenceChangeListener {

    private static final String FILE = "/proc/flicker_free/flicker_free";

    public static final String SETTINGS_KEY = DeviceSettings.KEY_DCD_SWITCH;

    private Context mContext;

    public DCDModeSwitch(Context context) {
        mContext = context;
    }

    public static String getFile() {
        if (Utils.fileWritable(FILE)) {
            return FILE;
        }
        return null;
    }

    public static boolean isSupported() {
        return Utils.fileWritable(getFile());
    }

    public static boolean isCurrentlyEnabled(Context context) {
        return Utils.getFileValueAsBoolean(getFile(), false);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        Boolean enabled = (Boolean) newValue;
        sharedPrefs.edit().putBoolean(SETTINGS_KEY, enabled ? false : true).commit();
        Utils.writeValue(getFile(), enabled ? "1" : "0");
        return true;
    }
}

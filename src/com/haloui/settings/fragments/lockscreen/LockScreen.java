/*
 * Copyright (C) 2016-2024 haloui Android Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.haloui.settings.fragments.lockscreen;

import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.SwitchPreferenceCompat;

import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.util.haloui.OmniJawsClient;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexable;

@SearchIndexable
public class LockScreen extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener {

    public static final String TAG = "LockScreen";

    private static final String KEY_SMARTSPACE = "lockscreen_smartspace_enabled";
    private static final String KEY_WEATHER = "lockscreen_weather_enabled";

    private SwitchPreferenceCompat mSmartspace;
    private Preference mWeather;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.haloui_settings_lock_screen);

        mSmartspace = findPreference(KEY_SMARTSPACE);
        mSmartspace.setOnPreferenceChangeListener(this);

        mWeather = findPreference(KEY_WEATHER);
        updateWeatherSettings();
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mSmartspace) {
            mSmartspace.setChecked((Boolean) newValue);
            updateWeatherSettings();
            return true;
        }
        return false;
    }

    private void updateWeatherSettings() {
        if (mWeather == null || mSmartspace == null) return;

        boolean enabled = OmniJawsClient.get().isOmniJawsEnabled(getContext())
                && !mSmartspace.isChecked();
        mWeather.setEnabled(enabled);
        mWeather.setSummary(enabled
                ? R.string.lockscreen_weather_summary
                : R.string.lockscreen_weather_enabled_info);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateWeatherSettings();
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.HALOUI;
    }

    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.haloui_settings_lock_screen);
}

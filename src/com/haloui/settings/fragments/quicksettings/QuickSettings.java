/*
 * Copyright (C) 2026 haloUI
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.haloui.settings.fragments.quicksettings;

import static com.haloui.settings.utils.ResourceUtils.isRtlMode;

import android.content.res.Resources;
import android.os.Bundle;

import com.android.internal.logging.nano.MetricsProto.MetricsEvent;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexable;

import lineageos.preference.LineageSecureSettingListPreference;
import lineageos.preference.LineageSecureSettingSwitchPreference;
import lineageos.preference.LineageSystemSettingListPreference;

@SearchIndexable
public class QuickSettings extends SettingsPreferenceFragment {

    public static final String TAG = "QuickSettings";

    private static final String QS_BRIGHTNESS_SLIDER_POSITION = "qs_brightness_slider_position";
    private static final String QS_SHOW_AUTO_BRIGHTNESS = "qs_show_auto_brightness";
    private static final String QS_SHOW_BRIGHTNESS_SLIDER = "qs_show_brightness_slider";
    private static final String STATUS_BAR_QUICK_QS_PULLDOWN = "qs_quick_pulldown";

    private static final int QS_BRIGHTNESS_SLIDER_HIDDEN = 0;

    private static final int PULLDOWN_DIR_NONE = 0;
    private static final int PULLDOWN_DIR_RIGHT = 1;
    private static final int PULLDOWN_DIR_LEFT = 2;

    private LineageSecureSettingListPreference mQsBrightnessSliderPosition;
    private LineageSecureSettingSwitchPreference mQsShowAutoBrightness;
    private LineageSystemSettingListPreference mQuickPulldown;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.haloui_settings_quick_settings);

        mQsShowAutoBrightness = findPreference(QS_SHOW_AUTO_BRIGHTNESS);
        mQsBrightnessSliderPosition = findPreference(QS_BRIGHTNESS_SLIDER_POSITION);
        LineageSecureSettingListPreference qsShowBrightnessSlider =
                findPreference(QS_SHOW_BRIGHTNESS_SLIDER);
        qsShowBrightnessSlider.setOnPreferenceChangeListener((preference, newValue) -> {
            enableQuickSettingsBrightnessSliderDependents(Integer.parseInt((String) newValue));
            return true;
        });
        enableQuickSettingsBrightnessSliderDependents(qsShowBrightnessSlider.getIntValue(1));

        mQuickPulldown = findPreference(STATUS_BAR_QUICK_QS_PULLDOWN);
        mQuickPulldown.setSummaryProvider(preference -> {
            int value = Integer.parseInt(
                    ((LineageSystemSettingListPreference) preference).getValue());
            Resources res = preference.getContext().getResources();

            switch (value) {
                case PULLDOWN_DIR_NONE:
                    return res.getString(R.string.status_bar_quick_qs_pulldown_off);
                case PULLDOWN_DIR_LEFT:
                case PULLDOWN_DIR_RIGHT:
                    int side = (value == PULLDOWN_DIR_LEFT) ^ isRtlMode(res)
                            ? R.string.status_bar_quick_qs_pulldown_summary_left
                            : R.string.status_bar_quick_qs_pulldown_summary_right;

                    return res.getString(R.string.status_bar_quick_qs_pulldown_summary,
                            res.getString(side));
            }
            return "";
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isRtlMode(getResources())) {
            mQuickPulldown.setEntries(R.array.status_bar_quick_qs_pulldown_entries_rtl);
        } else {
            mQuickPulldown.setEntries(R.array.status_bar_quick_qs_pulldown_entries);
        }
    }

    private void enableQuickSettingsBrightnessSliderDependents(int showBrightnessSlider) {
        boolean enabled = showBrightnessSlider != QS_BRIGHTNESS_SLIDER_HIDDEN;

        mQsBrightnessSliderPosition.setEnabled(enabled);
        mQsShowAutoBrightness.setEnabled(enabled);
    }

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.HALOUI;
    }

    /**
     * For search
     */
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.haloui_settings_quick_settings);
}

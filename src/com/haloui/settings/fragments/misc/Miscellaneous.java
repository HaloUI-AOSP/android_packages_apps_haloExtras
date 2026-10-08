/*
 * Copyright (C) 2026 haloUI
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.haloui.settings.fragments.misc;

import android.os.Bundle;

import com.android.internal.logging.nano.MetricsProto;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexable;
import com.android.internal.logging.nano.MetricsProto.MetricsEvent;

@SearchIndexable
public class Miscellaneous extends SettingsPreferenceFragment {

    public static final String TAG = "Misc";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.haloui_settings_misc);
    }

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.HALOUI;
    }

    /**
     * For search
     */
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.haloui_settings_misc);
}

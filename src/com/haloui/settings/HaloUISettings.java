/*
 * Copyright (C) 2026 haloUI
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.haloui.settings;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.instrumentation.Instrumentable;
import com.android.settingslib.search.SearchIndexable;
import com.android.internal.logging.nano.MetricsProto.MetricsEvent;

@SearchIndexable
public class HaloUISettings extends DashboardFragment {

    private static final String TAG = "HaloUISettings";

    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.haloui_settings;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.HALOUI;
    }

    @Override
    protected String getLogTag() {
        return TAG;
    }

    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.haloui_settings);
}

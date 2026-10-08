/*
 * Copyright (C) 2026 haloUI
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.haloui.settings;

import android.content.Context;

import com.android.settings.core.BasePreferenceController;

public class TopLevelHaloUISettingsPreferenceController extends BasePreferenceController {

    public TopLevelHaloUISettingsPreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }
}

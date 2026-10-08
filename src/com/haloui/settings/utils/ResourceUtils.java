/*
 * Copyright (C) 2026 haloUI
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package com.haloui.settings.utils;

import android.content.res.Resources;
import android.view.View;

public final class ResourceUtils {

    private ResourceUtils() {
    }

    public static boolean isRtlMode(final Resources res) {
        return res.getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
    }
}

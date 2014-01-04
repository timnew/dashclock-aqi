package org.windy.dashclock.extensions.aqi.settings;

import com.google.common.base.Joiner;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

public class SettingsHelper {

    public static final String ITEM_SEPARATOR = ",";
    public static final Joiner JOINER = Joiner.on(ITEM_SEPARATOR);

    public static Set<String> splitValue(String value) {
        if (value.isEmpty())
            return new HashSet<String>();

        return new HashSet<String>(asList(value.split(ITEM_SEPARATOR)));
    }

    public static String rejoinValue(Set<String> set) {
        return JOINER.join(set);
    }
}

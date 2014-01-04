package org.windy.dashclock.extensions.aqi.settings;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.windy.dashclock.extensions.aqi.settings.SettingsHelper.rejoinValue;
import static org.windy.dashclock.extensions.aqi.settings.SettingsHelper.splitValue;

public class SettingsHelperTest {
    @Test
    public void should_split_value() {
        Set<String> strings = splitValue("A,B,C");

        assertThat(strings)
                .hasSize(3)
                .containsExactly("A", "B", "C");
    }

    @Test
    public void should_handle_one_item() {
        Set<String> strings = splitValue("A");

        assertThat(strings)
                .hasSize(1)
                .containsExactly("A");
    }

    @Test
    public void should_handle_empty_string() {
        Set<String> strings = splitValue("");

        assertThat(strings).isEmpty();
    }

    @Test
    public void should_rejoin_values() {
        String value = rejoinValue(new HashSet<String>(asList("A", "B", "C")));

        assertThat(value).isEqualTo("A,B,C");
    }

    @Test
    public void should_rejoin_one_item_set() {
        String value = rejoinValue(new HashSet<String>(asList("A")));

        assertThat(value).isEqualTo("A");
    }

    @Test
    public void should_rejoin_empty_set() {
        String value = rejoinValue(new HashSet<String>());

        assertThat(value).isEqualTo("");
    }
}

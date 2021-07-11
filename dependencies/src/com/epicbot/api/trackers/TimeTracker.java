package com.epicbot.api.trackers;

import java.time.Duration;
import java.time.Instant;

/**
 * Created by Mitchell on 25-10-2015.
 */
public class TimeTracker {

    private static final String formatter = "%02d";
    private Instant startInstant;

    public TimeTracker() {
        this.reset();
    }

    /**
     * Resets the TimeTracker.
     */
    public void reset() {
        startInstant = Instant.now();
    }

    /**
     * Returns a java8 Duration object.
     *
     * @return java8 Duration object.
     */
    private Duration getDuration() {
        return Duration.between(startInstant, Instant.now());
    }

    /**
     * @return the amount of Milliseconds elapsed.
     */
    public long getMillisElapsed() {
        return getDuration().toMillis();
    }

    /**
     * @return the amount of Seconds elapsed.
     */
    public long getSecElapsed() {
        return getDuration().getSeconds();
    }

    /**
     * @return the amount of Minutes elapsed.
     */
    public long getMinElapsed() {
        return getDuration().toMinutes();
    }

    /**
     * @return the amount of Hours elapsed.
     */
    public long getHourElapsed() {
        return getDuration().toHours();
    }

    /**
     * @return the amount of Days elapsed.
     */
    public long getDaysElapsed() {
        return getDuration().toDays();
    }

    /**
     * @return a String containing the Duration.
     */
    public String format() {
        return this.toString();
    }

    @Override
    public String toString() {
        Duration duration = getDuration();

        if (getDaysElapsed() >= 1) {
            return String.format(formatter, getDaysElapsed()) + ":" + String.format(formatter, getHourElapsed() % 24) + ":" + String.format(formatter, getMinElapsed() % 60) + ":" + String.format(formatter, getSecElapsed() % 60);
        } else {
            return String.format(formatter, getHourElapsed()) + ":" + String.format(formatter, getMinElapsed() % 60) + ":" + String.format(formatter, duration.getSeconds() % 60);
        }
    }
}

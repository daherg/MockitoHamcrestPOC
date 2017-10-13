package com.mockito.hamcrest;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.Assert.notNull;

public class Superclass {

    /**
     * Removes dates in future.
     */
    public List<LocalDate> removeDatesAfterThreshold(List<LocalDate> dates, LocalDate threshold) {
        notNull(dates, "dates must not be null");
        notNull(threshold, "threshold date must not be null");
        return dates.stream()
                .filter(d -> d.isBefore(threshold))
                .collect(Collectors.toList());
    }
}

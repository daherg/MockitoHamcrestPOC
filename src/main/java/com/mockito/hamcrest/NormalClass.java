package com.mockito.hamcrest;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class NormalClass extends Superclass {

    @Autowired
    private RemoteService remoteService;

    public List<LocalDate> work() {
        List<LocalDate> randomDateList = generateRandomDates();
        randomDateList.add(fetchDateFromRemoteService());
        return removeDatesAfterThreshold(randomDateList, LocalDate.now()); // inherited from parent
    }

    protected List<LocalDate> generateRandomDates() {
        long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2050, 12, 31).toEpochDay();
        List<LocalDate> dateList = Collections.emptyList();
        for (int i = 0; i < 100; i++) {
            long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
            dateList.add(LocalDate.ofEpochDay(randomDay));
        }
        return dateList;
    }

    public LocalDate fetchDateFromRemoteService() {
        // image fancy logic to determine parameter.
        String anyParameter = "anyParameter"; // this is where we use the argumentCaptor to check the paramater
        String returnDate = remoteService.fetchDateAsStringFromRemote(anyParameter);
        return LocalDate.parse(returnDate);
    }
}

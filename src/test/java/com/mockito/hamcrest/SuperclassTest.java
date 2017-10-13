package com.mockito.hamcrest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SuperclassTest {

    private Superclass service = new Superclass();


    @Test(expected = IllegalArgumentException.class)
    public void testRemoveDatesInFutureInvalidArgumentsListIsNull() throws Exception {
        service.removeDatesAfterThreshold(null, LocalDate.MAX);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveDatesInFutureInvalidArgumentsThresholdIsNull() throws Exception {
        service.removeDatesAfterThreshold(Arrays.asList(LocalDate.MAX, LocalDate.MIN), null);
    }

    @Test
    public void testRemoveDatesInFutureFilterCorrectly() throws Exception {
        // prepare
        LocalDate pastDate1 = LocalDate.of(2015, 1, 1);
        LocalDate pastDate2 = LocalDate.of(2010, 1, 1);
        LocalDate futureDate = LocalDate.of(2020, 1, 1);
        List<LocalDate> dateList = Arrays.asList(pastDate1, pastDate2, futureDate);

        // execute
        List<LocalDate> actual = service.removeDatesAfterThreshold(dateList, LocalDate.now());

        // assert
        assertThat(actual, hasSize(2));
        assertThat(actual, hasItem(pastDate1));
        assertThat(actual, hasItem(pastDate2));
        assertThat(actual, not(hasItem(futureDate)));
    }
}
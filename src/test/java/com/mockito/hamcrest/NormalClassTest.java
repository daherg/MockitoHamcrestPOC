package com.mockito.hamcrest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class NormalClassTest {

    @Mock
    private RemoteService remoteServiceMock;

    @InjectMocks
    private NormalClass service = new NormalClass();

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testFetchDateFromRemoteService() throws Exception {
        // prepare
        when(remoteServiceMock.fetchDateAsStringFromRemote(any())).thenReturn("2017-04-02");
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        // execute
        LocalDate actualReturn = service.fetchDateFromRemoteService();

        // assert
        verify(remoteServiceMock, times(1)).fetchDateAsStringFromRemote(stringArgumentCaptor.capture());
        String actualUri = stringArgumentCaptor.getValue();

        assertThat(actualReturn, is(equalTo(LocalDate.of(2017, 04, 02))));
        assertThat(actualUri, is(equalTo("anyParameter")));
        // argumentCaptor allows us to get the value of the parameter
    }

    /**
     * Now we test the {@link NormalClass#work()} method.
     * This method calls other methods which were already tested.
     * We only want to focus on the business logic of the 'work' method. Hence we "stub" all other method calls.
     *
     * "Mock would not work in this case. because "mock" will mock all methods of our object to be tested.
     * For this we need the 'spy'
     */
    @Test
    public void testWork() {
        // convert mock to spy
        service = spy(service);

        // stub "generateRandomDates"
        List listMock = mock(List.class);
        // notice difference to mocked objects -> when(service.generateRandomDates()).thenReturn(listMock);
        doReturn(listMock).when(service).generateRandomDates();

        // stub "removeDatesBeforeThreshold(...)
        // notice that we can easily stub the inherited class
        doReturn(listMock).when(service).removeDatesAfterThreshold(any(), any());

        // stub "fetchDateFromRemoteService"
        LocalDate expectedDate = LocalDate.of(2012, 12, 12);
        doReturn(expectedDate).when(service).fetchDateFromRemoteService();

        // execute test
        List<LocalDate> actual = service.work();

        // test business logic of "work method"
        // by verifying that the methods were called (and partially by matching the parameter) and checking the returned object
        verify(listMock, times(1)).add(expectedDate); // we can match the actual object here. test will fail here if the 'equals' method return "false"
        verify(service, times(1)).generateRandomDates();
        verify(service, times(1)).removeDatesAfterThreshold(any(), any());
        assertThat(actual, is(equalTo(listMock)));
    }

    @Test
    public void testAllMethods() {
        // test all methods together...
    }

}
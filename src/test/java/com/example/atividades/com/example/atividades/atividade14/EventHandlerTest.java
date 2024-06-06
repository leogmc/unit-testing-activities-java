package com.example.atividades.atividade14;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EventHandlerTest {

    private EmailService mockEmailService;
    private EventHandler eventHandler;

    @BeforeEach
    public void setUp() {
        mockEmailService = mock(EmailService.class);
        eventHandler = new EventHandler(mockEmailService);
    }

    @Test
    public void testHandleEvent_Success() {
        String event = "Test Event";

        eventHandler.handleEvent(event);

        verify(mockEmailService, times(1)).sendEmail("test@example.com", "Event Occurred", event);
    }

    @Test
    public void testHandleEvent_EmptyEvent() {
        String event = "";

        eventHandler.handleEvent(event);

        verify(mockEmailService, times(1)).sendEmail("test@example.com", "Event Occurred", event);
    }

    @Test
    public void testHandleEvent_NullEvent() {
        String event = null;

        eventHandler.handleEvent(event);

        verify(mockEmailService, times(1)).sendEmail("test@example.com", "Event Occurred", event);
    }

    @Test
    public void testHandleEvent_CaptureArguments() {
        String event = "Test Event";

        eventHandler.handleEvent(event);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(mockEmailService).sendEmail(captor.capture(), captor.capture(), captor.capture());

        assertEquals("test@example.com", captor.getAllValues().get(0));
        assertEquals("Event Occurred", captor.getAllValues().get(1));
        assertEquals(event, captor.getAllValues().get(2));
    }
}

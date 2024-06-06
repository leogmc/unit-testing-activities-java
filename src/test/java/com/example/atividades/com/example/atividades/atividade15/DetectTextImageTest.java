package com.example.atividades.atividade15;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.DetectTextRequest;
import software.amazon.awssdk.services.rekognition.model.DetectTextResponse;
import software.amazon.awssdk.services.rekognition.model.TextDetection;
import software.amazon.awssdk.services.rekognition.model.RekognitionException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DetectTextImageTest {

    @Mock
    private RekognitionClient mockRekognitionClient;

    @InjectMocks
    private DetectTextImage detectTextImage;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        detectTextImage = new DetectTextImage("src/test/resources/img-example-for-aws-task.jpg", mockRekognitionClient);
    }

    @Test
    public void testDetectTextLabels() {
        RekognitionClient rekClient = mock(RekognitionClient.class);
        String sourceImage = "data/img-example-for-aws-task.jpg";
        String resultFilePath = "data/detect-text-results.txt";
        DetectTextImage dti = new DetectTextImage(sourceImage, rekClient);

        TextDetection textDetection = TextDetection.builder()
                .detectedText("some text")
                .confidence(99.0f)
                .id(1)
                .type("LINE")
                .build();

        List<TextDetection> textDetections = Arrays.asList(textDetection);
        DetectTextResponse response = DetectTextResponse.builder()
                .textDetections(textDetections)
                .build();

        when(rekClient.detectText(any(DetectTextRequest.class))).thenReturn(response);

        dti.detectTextLabels(resultFilePath);

        ArgumentCaptor<DetectTextRequest> argumentCaptor = ArgumentCaptor.forClass(DetectTextRequest.class);
        verify(rekClient).detectText(argumentCaptor.capture());
    }

    @Test
    public void testSaveResultToTextFile() throws IOException {
        // Create a mock TextDetection object
        TextDetection mockTextDetection = TextDetection.builder()
                .detectedText("Sample Text")
                .confidence(95.5f)
                .id(1)
                .parentId(0)
                .type("WORD")
                .build();
        
        // Create a mock list of TextDetection objects
        List<TextDetection> mockTextCollection = Collections.singletonList(mockTextDetection);
        
        // Use a real FileWriter with a test file path
        String testFilePath = "src/test/resources/test-results.txt";
        detectTextImage.saveResultToTextFile(mockTextCollection, testFilePath);

        // Verify the file content by reading it back
        List<String> actualLines = Files.readAllLines(Paths.get(testFilePath));
        assertTrue(actualLines.contains("Detected: Sample Text"));
        assertTrue(actualLines.contains("Confidence: 95.5"));
        assertTrue(actualLines.contains("Id: 1"));
        assertTrue(actualLines.contains("Parent Id: 0"));
        assertTrue(actualLines.contains("Type: WORD"));

        // Clean up the test file
        Files.deleteIfExists(Paths.get(testFilePath));
    }

    @Test
    public void testDetectTextLabelsNoTextDetected() throws IOException {
        // Create a mock response with no text detections
        DetectTextResponse mockResponse = DetectTextResponse.builder()
                .textDetections(Collections.emptyList())
                .build();

        // Mock the Rekognition client to return the mock response
        when(mockRekognitionClient.detectText(any(DetectTextRequest.class))).thenReturn(mockResponse);

        // Execute the method and ensure no exceptions are thrown
        assertDoesNotThrow(() -> detectTextImage.detectTextLabels("src/test/resources/test-results-no-text.txt"));

        // Verify interactions
        verify(mockRekognitionClient, times(1)).detectText(any(DetectTextRequest.class));

        // Verify the file content
        List<String> actualLines = Files.readAllLines(Paths.get("src/test/resources/test-results-no-text.txt"));
        assertTrue(actualLines.isEmpty());

        // Clean up the test file
        Files.deleteIfExists(Paths.get("src/test/resources/test-results-no-text.txt"));
    }

    @Test
    public void testDetectTextLabelsExceptionHandling() {
        // Mock the Rekognition client to throw an exception
        when(mockRekognitionClient.detectText(any(DetectTextRequest.class))).thenThrow(RekognitionException.builder().message("Error").build());

        // Execute the method and ensure it handles the exception without crashing
        assertDoesNotThrow(() -> detectTextImage.detectTextLabels("src/test/resources/test-results-exception.txt"));

        // Verify interactions
        verify(mockRekognitionClient, times(1)).detectText(any(DetectTextRequest.class));
    }

    @Test
    public void testDetectTextLabelsMultipleTextDetections() throws IOException {
        // Create multiple mock TextDetection objects
        TextDetection mockTextDetection1 = TextDetection.builder()
                .detectedText("Hello")
                .confidence(99.0f)
                .id(1)
                .parentId(0)
                .type("WORD")
                .build();

        TextDetection mockTextDetection2 = TextDetection.builder()
                .detectedText("World")
                .confidence(98.0f)
                .id(2)
                .parentId(0)
                .type("WORD")
                .build();

        // Create a mock response with multiple text detections
        DetectTextResponse mockResponse = DetectTextResponse.builder()
                .textDetections(List.of(mockTextDetection1, mockTextDetection2))
                .build();

        // Mock the Rekognition client to return the mock response
        when(mockRekognitionClient.detectText(any(DetectTextRequest.class))).thenReturn(mockResponse);

        // Execute the method and ensure no exceptions are thrown
        assertDoesNotThrow(() -> detectTextImage.detectTextLabels("src/test/resources/test-results-multiple-text.txt"));

        // Verify interactions
        verify(mockRekognitionClient, times(1)).detectText(any(DetectTextRequest.class));

        // Verify the file content by reading it back
        List<String> actualLines = Files.readAllLines(Paths.get("src/test/resources/test-results-multiple-text.txt"));
        assertTrue(actualLines.contains("Detected: Hello"));
        assertTrue(actualLines.contains("Detected: World"));
        assertTrue(actualLines.contains("Confidence: 99.0"));
        assertTrue(actualLines.contains("Confidence: 98.0"));

        // Clean up the test file
        Files.deleteIfExists(Paths.get("src/test/resources/test-results-multiple-text.txt"));
    }

    @Test
    public void testSaveResultToTextFileWithNullFilePath() {
        TextDetection mockTextDetection = TextDetection.builder()
                .detectedText("Sample Text")
                .confidence(95.5f)
                .id(1)
                .parentId(0)
                .type("WORD")
                .build();

        List<TextDetection> mockTextCollection = Collections.singletonList(mockTextDetection);
        
        assertThrows(NullPointerException.class, () -> detectTextImage.saveResultToTextFile(mockTextCollection, null));
    }
}

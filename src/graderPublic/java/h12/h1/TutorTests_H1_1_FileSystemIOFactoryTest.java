package h12.h1;

import h12.ioFactory.FileSystemIOFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.sourcegrade.jagr.api.testing.extension.JagrExecutionCondition;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.io.*;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission()
public class TutorTests_H1_1_FileSystemIOFactoryTest {

    @Test
    @ExtendWith(JagrExecutionCondition.class)
    public void testFileSystemIOFactoryReader() throws IOException {
        String resourceName = "test.txt";
        String expected = "Hello, World!";
        Context context = contextBuilder()
            .add("ressourceName", resourceName)
            .add("content", expected)
            .subject("FileSystemIOFactory#createReader(String)")
            .build();

        final FileSystemIOFactory fileSystemIOFactory = new FileSystemIOFactory();
        for (int i = 0; i < 10; i++) {
            TutorBufferedReader.reset0();
            TutorFileReader.reset0();
            TutorFileReader.DELEGATE_SUPPLIER = () -> new StringReader(expected + "\n");
            try (BufferedReader br = fileSystemIOFactory.createReader(resourceName)) {
                assertEquals(expected, br.readLine(), context,
                    TR -> "The returned BufferedReader did return the correct content");
            }
            assertEquals(resourceName, TutorFileReader.FILE_NAME, context,
                TR -> "The FileReader did not get called with the correct resourceName");
            assertEquals(TutorBufferedReader.IN, TutorFileReader.INSTANCE, context,
                TR -> "The BufferedReader wasn't created with the correct FileReader");
        }
    }

    @Test
    public void testSupportsReader() {
        Context context = contextBuilder()
                .subject("FileSystemIOFactory#supportsReader()")
                .build();
        final FileSystemIOFactory fileSystemIOFactory = new FileSystemIOFactory();
        assertTrue(fileSystemIOFactory.supportsReader(), context,
                TR -> "The method supportsReader() should return true");
    }
}

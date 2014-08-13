package org.anderes.edu.dojo.boxplot;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.InOrder;

public class BoxploterTest {
   
    private List<Double> values = Arrays.asList(18d, 24d, 19d, 19d, 20d, 25d, 24d, 18d, 24d, 17d);
    private Boxplot boxplot = new Boxplot(values);
    private Boxploter boxploter = new Boxploter(boxplot);
    private OutputStream mockOutputStream = mock(OutputStream.class);
    
    @Test 
    public void shouldBeAsciiOutput() throws IOException {
        boxploter.writeTo(System.out, 80);
        boxploter.writeTo(mockOutputStream, 80);
        
        final InOrder inOrder = inOrder(mockOutputStream);
        inOrder.verify(mockOutputStream).write("17      18      19      20      21      22      23      24      25      26      ".getBytes());
        inOrder.verify(mockOutputStream).write(System.lineSeparator().getBytes());
        inOrder.verify(mockOutputStream).write("┌───────┬───────┬───────┬───────┬───────┬───────┬───────┬───────┬───────┐".getBytes());
        inOrder.verifyNoMoreInteractions();
    }
}

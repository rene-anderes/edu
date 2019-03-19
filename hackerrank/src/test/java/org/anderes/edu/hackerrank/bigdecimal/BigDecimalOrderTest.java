package org.anderes.edu.hackerrank.bigdecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

public class BigDecimalOrderTest {

    @Test
    void newOrderArray() {
        final int n = 9;
        String[] s = { "-100", "50", "0", "56.6", "90", "0.12", ".12", "02.34", "000.000", null, null };
        final String[] expectedArray = { "90", "56.6", "50", "02.34", "0.12", ".12", "0", "000.000", "-100"};
        
        s = getSortetdArray(s, n);
        
        assertThat(s , arrayContaining(expectedArray));
    }
    
    private static String[] getSortetdArray(String[] s, int count) {
        return Arrays.stream(s)
                .filter(v -> v != null)
                .sorted((v1, v2) -> {
                    final BigDecimal b1 = new BigDecimal(v1);
                    final BigDecimal b2 = new BigDecimal(v2);
                    return b2.compareTo(b1);
                })
                .collect(java.util.stream.Collectors.toList()).toArray(new String[count]);
    }
    
    public static void main(String []args) {
        //Input
        Scanner sc= new Scanner(System.in);
        int n=sc.nextInt();
        String []s=new String[n+2];
        for(int i=0;i<n;i++) {
            s[i]=sc.next();
        }
        sc.close();

        //Write your code here
        s = getSortetdArray(s, n);
        
        //Output
        for(int i=0;i<n;i++) {
            System.out.println(s[i]);
        }
    }
}

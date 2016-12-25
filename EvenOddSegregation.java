package com.test;

import java.util.Arrays;

/**
 * <p>
 * Created with IntelliJ IDEA. <br/>
 * User: Ankur jain <br/>
 * Date: 25-Dec-16 <br/>
 * Time: 08:31 AM <br/>
 */
public class EvenOddSegregation {
    public static void main(String args[]) {
        int array[] = new int[]{2, 4, 7, 6, 1, 3, 5, 4};
        boolean loopRequired;
        do {
            loopRequired = false;
            for (int i = 0; i < array.length - 1; i++) {

                if (array[i] % 2 != 0 && array[i + 1] % 2 == 0) {

                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    loopRequired = true;
                }
            }
        } while (loopRequired);
        for (int k = 0; k < array.length; k++) {
            System.out.println(array[k]);
        }
    }
}

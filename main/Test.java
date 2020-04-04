package main;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class Test {

    public static void main(String[] args) {

        Integer a[] = new Integer[5];
        System.out.println(a[0]);
        int k=0;
        for(int i=0; i<5; i++){
            if(a[i]==null){
                k++;
            }
        }
        System.out.println(k);
    }
}

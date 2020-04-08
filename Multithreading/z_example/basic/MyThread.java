package Multithreading.z_example.basic;

public class MyThread extends Thread{

    public static void main(String[] args) {
        String honorificPrefix = "100002148";
        String givenName = "100002148";
        String middleName = "100002148";
        String familyName = "100002148";
        String honorificSuffix = "100002148";
        String phoneNumbers = "100002148";
        String jsonRequest = "{\"userName\":\""+honorificPrefix+"\",\"password\":\""+givenName+"\"}";
        System.out.println(jsonRequest);
    }
}

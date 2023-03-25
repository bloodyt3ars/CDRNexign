package org.example;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        List<CallRecord> callRecords = null;
        try {
            callRecords = ReadCDR.readCDR("cdrtest.txt");
            Collections.sort(callRecords);
        } catch (IOException e) {
            throw new IOException("Incorrect path");
        } catch (Exception e) {
            System.out.println(e);
        }
        Set<String> phoneNumbers= new HashSet<>();
        for (CallRecord callRecord : callRecords) {
            phoneNumbers.add(callRecord.getPhoneNumber());
        }
        for (String phoneNumber: phoneNumbers){
            CreateReport.createReport(phoneNumber, callRecords);
        }
        System.out.println("Конец выполнения программы");
    }
}
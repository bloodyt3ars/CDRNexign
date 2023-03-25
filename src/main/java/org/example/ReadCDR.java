package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReadCDR {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static List<CallRecord> readCDR(String filePath) throws IOException {
        List<CallRecord> callRecords = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            String phoneNumber = fields[1].trim();
            CallType callType = fields[0].trim().equals("01") ?CallType.OUTGOING : CallType.INCOMING;
            LocalDateTime callStart = LocalDateTime.parse(fields[2].trim(), DATE_TIME_FORMATTER);
            LocalDateTime callEnd = LocalDateTime.parse(fields[3].trim(), DATE_TIME_FORMATTER);
            TariffType tariffType;
            if (fields[4].trim().equals("06")){
                tariffType = TariffType.UNLIMITED_300;
            } else if (fields[4].trim().equals("03")) {
                tariffType = TariffType.PER_MINUTE;
            }
            else if(fields[4].trim().equals("11")){
                tariffType = TariffType.NORMAL;
            }
            else {
                throw new IllegalArgumentException("Unknown tariff type");
            }
            callRecords.add(new CallRecord(phoneNumber, callType, callStart, callEnd, tariffType));
        }
        reader.close();
        return callRecords;
    }
}

package org.example;

import java.util.ArrayList;
import java.util.List;

public class Report {


    private String phoneNumber;
    private List<CallRecord> callRecords;
    private double totalCost;
    private TariffType tariffType;
    private int freeMinutes;

    public Report(String number, List<CallRecord> callRecords) {
        this.phoneNumber = number;
        this.callRecords = getPhoneNumberCallRecords(callRecords);
        this.tariffType = this.getCallRecords().get(0).getTariffType();
        if (tariffType==TariffType.UNLIMITED_300){
            this.totalCost = 100;
        }
        else {
            this.totalCost = 0;
        }
        this.freeMinutes = this.getCallRecords().get(0).getTariffType().getFreeMinutes();
    }
    private List<CallRecord> getPhoneNumberCallRecords(List<CallRecord> callRecords){
        List<CallRecord> phoneNumberCallRecords = new ArrayList<>();
        for (CallRecord callRecord: callRecords) {
            if (callRecord.getPhoneNumber().equals(phoneNumber)){
                phoneNumberCallRecords.add(callRecord);
            }
        }
        return phoneNumberCallRecords;
    }

    public String getNumber() {
        return phoneNumber;
    }


    public double getCost() {
        int minutesRemain = freeMinutes;
        for (CallRecord callRecord: getCallRecords()) {
            if (tariffType == TariffType.UNLIMITED_300){
                totalCost = totalCost + callRecord.calculateUnlimited300Cost(minutesRemain);
                minutesRemain -= callRecord.getCallDurationMinutes();
            } else if (tariffType == TariffType.PER_MINUTE) {
                totalCost += callRecord.calculatePerMinuteCost();
            } else if (tariffType == TariffType.NORMAL) {
                totalCost += callRecord.calculateNormalCost(minutesRemain);
                if (callRecord.getCallType()==CallType.OUTGOING){
                    minutesRemain -= callRecord.getCallDurationMinutes();
                }
            }
            else {
                throw new IllegalArgumentException("Unknown tariff type");
            }
        }
        return totalCost;
    }

    public List<CallRecord> getCallRecords() {
        return callRecords;
    }
}

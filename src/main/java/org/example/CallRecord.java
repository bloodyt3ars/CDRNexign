package org.example;

import java.time.Duration;
import java.time.LocalDateTime;

public class CallRecord  implements Comparable<CallRecord>{
    private String phoneNumber; // номер абонента
    private CallType  callType; // тип вызова (01 - исходящие, 02 - входящие)
    private LocalDateTime callStart; // дата и время начала звонка
    private LocalDateTime callEnd; // дата и время окончания звонка
    private TariffType tariffType; // тип тарифа (06 - Безлимит 300, 03 - Поминутный, 11 - Обычный)
    private double cost;


    public CallRecord(String phoneNumber, CallType  callType, LocalDateTime callStart, LocalDateTime callEnd, TariffType tariffType){
        this.phoneNumber = phoneNumber;
        this.callType = callType;
        this.callStart = callStart;
        this.callEnd = callEnd;
        this.tariffType = tariffType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public CallType getCallType() {
        return callType;
    }

    public LocalDateTime getCallStart() {
        return callStart;
    }

    public LocalDateTime getCallEnd() {
        return callEnd;
    }

    public TariffType getTariffType() {
        return tariffType;
    }
    public int getCallDurationMinutes() {
        Duration duration = Duration.between(callStart, callEnd);
        if (duration.getSeconds() / 1 % 60 == 0.0){
            return (int) duration.getSeconds() / 60;
        }
        else {
            return ((int) duration.getSeconds() / 60)+1;
        }
    }
    private String getCallDurationMinute() {
        Duration duration = Duration.between(callStart, callEnd);
        long second = duration.getSeconds();
        long hour = second / 3600;
        long min = second / 60 % 60;
        long sec = second / 1 % 60;
        return String.format("%02d:%02d:%02d", hour, min, sec);
    }

    public double calculateUnlimited300Cost(int freeMinutes) {
        double cost = 0;
        int duration = getCallDurationMinutes();
        if (freeMinutes >= 0) {
            cost = 0;
        } else {
            if ((freeMinutes >= 0) && (duration > freeMinutes)){
                cost = duration-freeMinutes * 1.0;
            } else {
                cost = duration * 1.0;
            }
        }
        this.cost = cost;
        return cost;
    }

    public double calculatePerMinuteCost() {
        int duration = getCallDurationMinutes();
        this.cost = duration * 1.5;
        return duration * 1.5;
    }

    //тут тоже
    public double calculateNormalCost(int freeMinutes) {
        double cost = 0;
        int duration = getCallDurationMinutes();
        if (callType == CallType.INCOMING) {
            cost = 0;
        } else {
            if ((freeMinutes >= 0) && (duration < freeMinutes)) {
                cost = duration * 0.5;
            } else if ((freeMinutes >= 0) && (duration > freeMinutes)){
                cost = freeMinutes * 0.5 + duration-freeMinutes * 1.5;
            } else {
                cost = duration * 1.5;
            }
        }
        this.cost = cost;
        return cost;
    }


    public String toString(){
        String template = "|%11s |%21s |%21s | %10s | %7.2f |";
        String result = String.format(template, callType.getName(), callStart, callEnd, getCallDurationMinute(),cost);
        return result;
    }


    @Override
    public int compareTo(CallRecord o) {
        if(callStart.compareTo(o.getCallStart()) < 0 )
            return -1;
        if(callStart.compareTo(o.getCallStart()) == 0 )
            return 0;
        return 1;
    }
}

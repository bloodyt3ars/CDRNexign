package org.example;

import javax.imageio.IIOException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateReport {
    public static void createReport(String phoneNumber, List<CallRecord> callRecords) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(String.format("reports/report_for_%s.txt", phoneNumber));
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound: " + e);
            throw new NullPointerException("Folder not found, change path");
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            Report report = new Report(phoneNumber, callRecords);
            double totalCost = report.getCost();
            String dash = "-----------------------------------------------------------------------------------";
            String phone = String.format("Report for phone number %s:", phoneNumber);
            String template = "|%11s |%21s |%21s | %10s | %7s |";
            String tariffIndex = String.format("Tariff type: %s", report.getCallRecords().get(0).getTariffType().getName());
            String header = String.format(template, "Call type", "Start time", "End time", "Duration", "Cost");
            String fileData = tariffIndex + "\n" + dash + "\n" + phone + "\n" + dash + "\n" + dash + "\n" + header + "\n" + dash;
            fos.write(fileData.getBytes());
            for (CallRecord callRecord : report.getCallRecords()) {
                fos.write(("\n" + callRecord.toString()).getBytes());
            }
            String footer = String.format("|%58s|%14.2f rubles |", "Total cost: ", totalCost);
            String fileData2 = "\n" + dash + "\n" + footer + "\n" + dash;
            fos.write(fileData2.getBytes());
            fos.flush();
            fos.close();
        }
        catch (IOException e){
            System.out.println("IOException: "+e);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}

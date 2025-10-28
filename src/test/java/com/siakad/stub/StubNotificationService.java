package com.siakad.stub;

import com.siakad.service.NotificationService;
import java.util.ArrayList;
import java.util.List;

public class StubNotificationService implements NotificationService {

    private List<String> sentEmails = new ArrayList<>();
    private List<String> sentSMS = new ArrayList<>();

    @Override
    public void sendEmail(String email, String subject, String message) {
        String emailRecord = String.format("Email to: %s, Subject: %s, Message: %s",
                email, subject, message);
        sentEmails.add(emailRecord);
        System.out.println("STUB: " + emailRecord);
    }

    @Override
    public void sendSMS(String phone, String message) {
        String smsRecord = String.format("SMS to: %s, Message: %s", phone, message);
        sentSMS.add(smsRecord);
        System.out.println("STUB: " + smsRecord);
    }

    public List<String> getSentEmails() {
        return new ArrayList<>(sentEmails);
    }

    public List<String> getSentSMS() {
        return new ArrayList<>(sentSMS);
    }

    public void clear() {
        sentEmails.clear();
        sentSMS.clear();
    }
}
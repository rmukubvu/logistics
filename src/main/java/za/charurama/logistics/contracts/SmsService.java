package za.charurama.logistics.contracts;

import za.charurama.logistics.models.SmsResponse;

public interface SmsService {
    SmsResponse sendSms(String destination, String message);
}

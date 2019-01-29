package za.charurama.logistics.common;

public class PhoneNumber {
    public static String getCorrectPhoneNumber(String msisdn,String countryCode) {
        if (msisdn.startsWith("0"))
            return countryCode + msisdn.substring(1);
        return msisdn;
    }
}

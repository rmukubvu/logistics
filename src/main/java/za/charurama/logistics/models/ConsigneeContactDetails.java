package za.charurama.logistics.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "consignee_contact_details")
public class ConsigneeContactDetails {
    @Id
    private String id;
    @Indexed
    private String consigneeId;
    private String name;
    private String telephone;
    private String emailAddress;
    private String countryCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getConsigneeId() {
        return consigneeId;
    }

    public void setConsigneeId(String consigneeId) {
        this.consigneeId = consigneeId;
    }

}

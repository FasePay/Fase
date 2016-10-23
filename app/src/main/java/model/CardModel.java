package model;

/**
 * Created by shashank on 10/21/2016.
 */

public class CardModel {
    private String merchantName;
    private String amount;
    private String dates;

    public CardModel(String merchantName, String amount, String dates) {
        this.merchantName = merchantName;
        this.dates = dates;
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
}
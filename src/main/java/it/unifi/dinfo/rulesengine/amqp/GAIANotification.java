package it.unifi.dinfo.rulesengine.amqp;

public class GAIANotification {
    private String from;
    private String message;

    public GAIANotification(String from, String message) {
        this.from = from;
        this.message = message;
    }

    public GAIANotification(){}

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package it.unifi.dinfo.rulesengine.notification;

import java.io.Serializable;
import java.util.Map;

public class GAIANotification implements Serializable {
    private Long timestamp;
    private String rule;
    private Map values;
    private String description;
    private NotificationType type = NotificationType.info;

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Map getValues() {
        return values;
    }

    public void setValues(Map values) {
        this.values = values;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GAIANotification)) return false;

        GAIANotification that = (GAIANotification) o;

        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;
        if (rule != null ? !rule.equals(that.rule) : that.rule != null) return false;
        if (values != null ? !values.equals(that.values) : that.values != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        int result = timestamp != null ? timestamp.hashCode() : 0;
        result = 31 * result + (rule != null ? rule.hashCode() : 0);
        result = 31 * result + (values != null ? values.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}

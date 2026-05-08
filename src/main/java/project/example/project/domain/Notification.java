package project.example.project.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.example.project.commonDomain.ENotification;
import project.example.project.exceptions.DomainException;

@Entity
@Setter
@Getter
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label;
    private String description;
    private String actionDate;
    private boolean isRead=false;
    private ENotification notificationType;

    public void setLabel(String label) {
        if (label == null || label.trim().isEmpty()) {
            throw new DomainException("Notification label cannot be null or empty.");
        }
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new DomainException("Notification description cannot be null or empty.");
        }
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setActionDate(String actionDate) {
        if (actionDate == null || actionDate.trim().isEmpty()) {
            throw new DomainException("Notification action date cannot be null or empty.");
        }
        this.actionDate = actionDate;
    }

    public String getActionDate() {
        return actionDate;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    public boolean getRead() {
        return isRead;
    }
    

    
}

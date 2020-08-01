package india.lingayat.we.models;

import org.hibernate.annotations.CreationTimestamp;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Date;

@Entity
public class CreditsAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAdded;

    private String referenceNumber;

    private int creditsAdded;

    private Long userReference;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public int getCreditsAdded() {
        return creditsAdded;
    }

    public void setCreditsAdded(int creditsAdded) {
        this.creditsAdded = creditsAdded;
    }

    public Long getUserReference() {
        return userReference;
    }

    public void setUserReference(Long userReference) {
        this.userReference = userReference;
    }
}

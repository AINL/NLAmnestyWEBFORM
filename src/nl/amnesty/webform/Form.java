package nl.amnesty.webform;

import nl.amnesty.webform.util.Property;
import java.util.List;

public class Form {

    private long id;
    private long submissionid;
    private List<Property> propertylist;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Property> getPropertylist() {
        return propertylist;
    }

    public void setPropertylist(List<Property> propertylist) {
        this.propertylist = propertylist;
    }

    public long getSubmissionid() {
        return submissionid;
    }

    public void setSubmissionid(long submissionid) {
        this.submissionid = submissionid;
    }
}

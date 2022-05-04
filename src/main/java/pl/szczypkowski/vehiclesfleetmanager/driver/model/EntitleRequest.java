package pl.szczypkowski.vehiclesfleetmanager.driver.model;

import java.util.Date;

public class EntitleRequest {

    private String documentTyp;
    private Date expiryDate;

    public String getDocumentTyp() {
        return documentTyp;
    }

    public void setDocumentTyp(String documentTyp) {
        this.documentTyp = documentTyp;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}

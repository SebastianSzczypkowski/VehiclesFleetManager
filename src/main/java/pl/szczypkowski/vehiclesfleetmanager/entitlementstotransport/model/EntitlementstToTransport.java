package pl.szczypkowski.vehiclesfleetmanager.entitlementstotransport.model;



import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Table(name = "entitlements_to_transport")
@Entity
public class EntitlementstToTransport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_driver")
    private Long idDriver;

    @OneToOne
    @JoinColumn(name = "id_type_of_permissions")
    private TypeOfPermissions typeOfPermission;

    @Column(name = "expiry_date")
    private Date expiryDate;

    @Column(name = "document_typ")
    private String documentTyp;

    public EntitlementstToTransport() {
    }

    public EntitlementstToTransport(Long id, Long idDriver, TypeOfPermissions typeOfPermission, Date expiryDate, String documentTyp) {
        this.id = id;
        this.idDriver = idDriver;
        this.typeOfPermission = typeOfPermission;
        this.expiryDate = expiryDate;
        this.documentTyp = documentTyp;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(Long idDriver) {
        this.idDriver = idDriver;
    }

    public TypeOfPermissions getTypeOfPermission() {
        return typeOfPermission;
    }

    public void setTypeOfPermission(TypeOfPermissions typeOfPermission) {
        this.typeOfPermission = typeOfPermission;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getDocumentTyp() {
        return documentTyp;
    }

    public void setDocumentTyp(String documentTyp) {
        this.documentTyp = documentTyp;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntitlementstToTransport that = (EntitlementstToTransport) o;
        return Objects.equals(id, that.id) && Objects.equals(idDriver, that.idDriver) && Objects.equals(typeOfPermission, that.typeOfPermission) && Objects.equals(expiryDate, that.expiryDate) && Objects.equals(documentTyp, that.documentTyp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idDriver, typeOfPermission, expiryDate, documentTyp);
    }

    @Override
    public String toString() {
        return "Entitlementstotransport{" +
                "id=" + id +
                ", idDriver=" + idDriver +
                ", typeOfPermission=" + typeOfPermission +
                ", expiryDate=" + expiryDate +
                ", documentTyp='" + documentTyp + '\'' +
                '}';
    }
}

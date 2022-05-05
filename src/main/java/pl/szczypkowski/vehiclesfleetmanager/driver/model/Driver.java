package pl.szczypkowski.vehiclesfleetmanager.driver.model;


import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import pl.szczypkowski.vehiclesfleetmanager.entitlementstotransport.model.EntitlementstToTransport;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "driver")
@Indexed
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @FullTextField(analyzer = "name")
    @Column(name = "name")
    private String name;
    @FullTextField(analyzer = "name")
    @Column(name = "surname")
    private String surname;
    @Column(name = "pesel")
    private Long pesel;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @FullTextField(analyzer = "name")
    @Column(name = "address")
    private String address;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany()
    @JoinColumn(name = "entitlemenstst")
    private List<EntitlementstToTransport> entitlementstToTransport;
    @Transient
    private List<EntitleRequest>entitlement ;

    public Driver() {
    }

    public Driver(Long id, String name, String surname, Long pesel, Date dateOfBirth, String address, List<EntitlementstToTransport> entitlementstToTransport) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.entitlementstToTransport = entitlementstToTransport;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<EntitlementstToTransport> getEntitlementstToTransport() {
        return entitlementstToTransport;
    }

    public void setEntitlementstToTransport(List<EntitlementstToTransport> entitlementstToTransport) {
        this.entitlementstToTransport = entitlementstToTransport;
    }

    public List<EntitleRequest> getEntitlement() {
        return entitlement;
    }

    public void setEntitlement(List<EntitleRequest> entitlement) {
        this.entitlement = entitlement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Long getPesel() {
        return pesel;
    }

    public void setPesel(Long pesel) {
        this.pesel = pesel;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return Objects.equals(id, driver.id) && Objects.equals(name, driver.name) && Objects.equals(surname, driver.surname) && Objects.equals(pesel, driver.pesel) && Objects.equals(dateOfBirth, driver.dateOfBirth) && Objects.equals(address, driver.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, pesel, dateOfBirth, address);
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", pesel=" + pesel +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                '}';
    }
}

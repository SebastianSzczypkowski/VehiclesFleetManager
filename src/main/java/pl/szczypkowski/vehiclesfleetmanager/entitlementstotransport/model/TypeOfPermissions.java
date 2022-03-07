package pl.szczypkowski.vehiclesfleetmanager.entitlementstotransport.model;


import javax.persistence.*;
import java.util.Objects;

@Table(name = "type_of_permission")
@Entity
public class TypeOfPermissions {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "designation")
    private String designation;

    @Column(name = "description")
    private String description;

    public TypeOfPermissions() {
    }

    public TypeOfPermissions(Long id, String name, String designation, String description) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.description = description;
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
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
        if (o == null || getClass() != o.getClass()) return false;
        TypeOfPermissions that = (TypeOfPermissions) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(designation, that.designation) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, designation, description);
    }

    @Override
    public String toString() {
        return "TypeOfPermissions{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", designation='" + designation + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

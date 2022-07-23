package pl.szczypkowski.vehiclesfleetmanager.user.model;

import javax.persistence.*;

@Entity
@Table(name="roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_roles")
    private Long id;
    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private Roles name;

    public UserRole() {
    }

    public UserRole(Roles name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Roles getName() {
        return name;
    }

    public void setName(Roles name) {
        this.name = name;
    }
}

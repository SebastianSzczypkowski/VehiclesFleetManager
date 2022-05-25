package pl.szczypkowski.vehiclesfleetmanager.user.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Table(name = "user")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "name")
    private String name;

    @Column(name = "login")
    private String login;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "date_of_registration")
    private Date dateOfRegistration;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    public User() {
    }
    public User(@NonNull String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.dateOfRegistration =new Date();
        this.active =true;

    }

    public User(Long id, @NonNull String name, String login, Boolean active, Date dateOfRegistration, String email, String password) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.active = active;
        this.dateOfRegistration = dateOfRegistration;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(Date dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && name.equals(user.name) && Objects.equals(login, user.login) && Objects.equals(active, user.active) && Objects.equals(dateOfRegistration, user.dateOfRegistration) && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, login, active, dateOfRegistration, email, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", active=" + active +
                ", dateOfRegistration=" + dateOfRegistration +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

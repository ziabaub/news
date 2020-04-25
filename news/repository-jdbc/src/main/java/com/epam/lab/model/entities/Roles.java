package com.epam.lab.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "roles")
public class Roles implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @NotNull
    @Length(max = 20)
    private String role = "user";

    @OneToOne(mappedBy = "roles", cascade = CascadeType.REMOVE)
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = checkRole(role);

    }

    private String checkRole(String role) {
        if ("admin".equalsIgnoreCase(role)) {
            return this.role;
        }
        return role;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Roles roles = (Roles) o;
        return Objects.equals(id, roles.id) &&
                Objects.equals(role, roles.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role);
    }

    @Override
    public String toString() {
        return "Roles{" +
                "id=" + id +
                ", role='" + role + '\'' +
                '}';
    }
}

package com.tony.user_service.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Data 
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    private UUID id; 
    
    private String name;
    private String email;
    private String password;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Phone> phones = new ArrayList<>();

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
    }

    public User(String name, String email, String password) {
        this.generateId(); 
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void addPhone(Phone phone) {
        phones.add(phone);
        phone.setUser(this);
    }

    public List<Phone> getPhones() { return phones; }
    public void setPhones(List<Phone> phones) { this.phones = phones; }

    @CreationTimestamp
    @Column(name = "created", updatable = false)
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "modified")
    private LocalDateTime modified;

    @Column(name = "last_login")
    private LocalDateTime lastLogin; 

    @Column(name = "is_active")
    private Boolean active = true; 
    
    @Column(name = "token")
    private String token; 

    public Boolean getActive() {
      return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UUID getId() {
      return this.id;
    }

    public LocalDateTime getCreated() {
      return this.created;
    }

    public void setCreated(LocalDateTime created) {
      this.created = created;
    }

    public LocalDateTime getModified() {
     return this.modified;
    }

    public void setModified(LocalDateTime modified) {
      this.modified = modified;
    } 

    public LocalDateTime getLastLogin() {
     return this.lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
     this.lastLogin = lastLogin;
    }
}
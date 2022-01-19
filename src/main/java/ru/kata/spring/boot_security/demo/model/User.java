package ru.kata.spring.boot_security.demo.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity

@Table(name = "users")
@DynamicUpdate(value = true) // По умолчанию Hibernate если мы обновляем только одно поле.
// То он все равно обновляет все поля. Он делает это, потому что с нами параллельно может кто то работать с этим обьектом,
// и мы могли получить не то что хотели. По этому Hibernate дает нам возможность вк флажки. Мы говорим. Меняй только те поля,
// которые я обновляю
//@DynamicInsert(value = true)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Name should be not Empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Second name should be not Empty")
    @Size(min = 2, max = 30, message = "Second name should be between 2 and 30 characters")
    @Column(name = "second_name")
    private String second_name;

    @Min(value = 0, message = "Age should be greater then 0")
    @Column(name = "age")
    private int age;


    @Email(message = "Email should be valid ")
    @Column(name = "email", nullable = false, length = 256)
    private String email;

    @Column(name = "password", nullable = false, length = 64)
    private String password;

    //    @Fetch(FetchMode.SUBSELECT)
//    @ManyToMany(cascade = {
//            CascadeType.PERSIST,
//            CascadeType.MERGE})
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    //@Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "users_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles = new HashSet<>();

    public User() {
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public void removeRole(Role role) {
        roles.remove(role);
        role.getUser().remove(this);
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

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() { //срок Действия Учетной Записи Не Истек
        return true;
    } // срок Действия Учетной Записи Не Истек

    @Override
    public boolean isAccountNonLocked() { //не Заблокирована Ли Учетная запись
        return true;
    } // не Заблокирована Ли Учетная запись

    @Override
    public boolean isCredentialsNonExpired() { //не Истек Ли Срок Действия Учетных Данных
        return true;
    } // не Истек Ли Срок Действия Учетных Данных

    @Override
    public boolean isEnabled() { // включено
        return true;
    } //ВКЛЮЧЕН

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
package com.Anelie.vitrineVirtual.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String senha;

    private String nomeCompleto;



    //contrato de seguranca
    //dados utilizador
    // permissoes do utilizdr
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    // senha
    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    // false bloqueia logins
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //credenciais expirada, provavelmente n vamos usar
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // conta ativa, provavlmente n vamos usar
    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    public void setLogin(String login) {
        this.login = login;
    }
}

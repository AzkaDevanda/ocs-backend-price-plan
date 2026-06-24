package com.sts.sinorita.svc.role.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

//@Getter
//@Setter
@Entity
@Table(name = "BFM_USER", schema = "POT")
public class UserLogin implements UserDetails {
    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offer_ver_seq")
    @Column(name = "USER_ID")
    private Integer id;

    @Column(name = "USER_NAME")
    private String username;

    @Column(name = "PWD")
    private String password;

    @Column(name = "IS_LOCKED")
    private String isLocked;

    @Column(name="STATE")
    private String state;

//    private boolean active;
    @Column(name = "LOGIN_FAIL")
    private Integer loginFail;

    @Column(name ="LOGIN_FAIL_DATE")
    private LocalDateTime loginFailDate;

    @Column(name="USER_CODE")
    private String userCode;

    @Column(name = "FORCE_LOGIN")
    private String forceLogin;

    public String getForceLogin() {
        return forceLogin;
    }

    public void setForceLogin(String forceLogin) {
        this.forceLogin = forceLogin;
    }

    public void setUserCode(String userCode){
        this.userCode = userCode;
    }

    public String getUserCode(){
        return this.userCode;
    }


    public Integer getId(){
        return this.id;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // or your own logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public String getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(String isLocked) {
        this.isLocked = isLocked;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDateTime getLoginFailDate() {
        return loginFailDate;
    }

    public void setLoginFailDate(LocalDateTime loginFailDate) {
        this.loginFailDate = loginFailDate;
    }

    public Integer getLoginFail() {
        return loginFail;
    }

    public void setLoginFail(Integer loginFail) {
        this.loginFail = loginFail;
    }

    //    @Override
//    public boolean isEnabled() {
//        return this.active;
//    }

    // Also implement getAuthorities()
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

}

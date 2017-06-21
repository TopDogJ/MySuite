package com.mysuite.mytrade.api.entity.bean.user;

import com.mysuite.mytrade.api.entity.bean.EntityBean;
import com.mysuite.mytrade.api.entity.bean.security.Security;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by jianl on 7/06/2017.
 */
@Entity
public class User extends EntityBean {
    @Column(nullable = false, unique = true, length = 20)
    private String username;
    @Column(nullable = false, unique = true, length = 20)
    private String password;
    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<Security> selectedSecurity;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Security> getSelectedSecurity() {
        return selectedSecurity;
    }

    public void setSelectedSecurity(List<Security> selectedSecurity) {
        this.selectedSecurity = selectedSecurity;
    }

    @Override
    protected void appendToString(StringBuffer stringBuffer) {

    }
}

package es.source.code.model;

import java.io.Serializable;

/**
 * Created by this.zyq on 2016/6/14.
 *
 * @author this.zyq
 */
public class User implements Serializable{
    private String userName;
    private String password;
    private Boolean oldUser;

//    public User(Boolean oldUser, String password, String userName) {
//        this.oldUser = oldUser;
//        this.password = password;
//        this.userName = userName;
//    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getOldUser() {
        return oldUser;
    }

    public void setOldUser(Boolean oldUser) {
        this.oldUser = oldUser;
    }
}

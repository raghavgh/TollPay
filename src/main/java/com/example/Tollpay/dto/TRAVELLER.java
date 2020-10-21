package com.example.Tollpay.dto;


import javax.persistence.*;

@Entity
public class TRAVELLER {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String email;
    private String mobileNum;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setMobileNum(String mobileNum){
        this.mobileNum = mobileNum;
    }
    
    public String getMobileNum(){
        return this.mobileNum;
    }

    @Override
    public String toString() {
        return  "\n name = " + name +
                "\n password = " + password + "\n mobileNum = "+ mobileNum +"\n";
    }
}

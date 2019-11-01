package com.ershou.ershoumarket.service.model;

public class UserModel
{
    private int id;
    private String name;
    private int gender;
    private int age;
    private String telephone;
    private String register_mode;
    private String third_party_id;

    private String encrptPassword;

    public String getEncrptPassword()
    {
        return encrptPassword;
    }

    public void setEncrptPassword(String encrptPassword)
    {
        this.encrptPassword = encrptPassword;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getGender()
    {
        return gender;
    }

    public void setGender(Integer gender)
    {
        this.gender = gender;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public String getTelephone()
    {
        return telephone;
    }

    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }

    public String getRegister_mode()
    {
        return register_mode;
    }

    public void setRegister_mode(String register_mode)
    {
        this.register_mode = register_mode;
    }

    public String getThird_party_id()
    {
        return third_party_id;
    }

    public void setThird_party_id(String third_party_id)
    {
        this.third_party_id = third_party_id;
    }
}

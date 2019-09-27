package com.aut.parkit.View;

public class SignupUserProfile
{
    public String userFirst_Name;
    public String userLast_Name;
    public String userEmail;
    public String userVehicle;
    public String userPassword;
    public String userConfirm_Password;


    public SignupUserProfile(String userFirst_Name, String userLast_Name, String userEmail, String userVehicle, String userPassword, String userConfirm_Password)
    {
        this.userFirst_Name = userFirst_Name;
        this.userLast_Name = userLast_Name;
        this.userEmail = userEmail;
        this.userVehicle = userVehicle;
        this.userPassword = userPassword;
        this.userConfirm_Password = userConfirm_Password;
    }

}

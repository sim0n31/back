package com.upc.molinachirinostp.security.dtos;

public class ResetPasswordRequest {
    private String email;
    private String newPassword;
    private String secret;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    public String getSecret() { return secret; }
    public void setSecret(String secret) { this.secret = secret; }
}

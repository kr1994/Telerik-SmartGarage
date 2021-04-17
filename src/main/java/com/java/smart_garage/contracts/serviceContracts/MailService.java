package com.java.smart_garage.contracts.serviceContracts;


public interface MailService {

    public void sendMailForCredentials(String email, String username, String password);

    public void sendMailForNewPassword(String email, String newPassword);
}

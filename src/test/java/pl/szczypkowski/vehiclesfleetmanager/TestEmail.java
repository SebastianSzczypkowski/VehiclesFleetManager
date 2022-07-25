package pl.szczypkowski.vehiclesfleetmanager;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;
import pl.szczypkowski.vehiclesfleetmanager.email.service.EmailSender;
import org.junit.runner.RunWith;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class TestEmail {

    @Autowired
    private EmailSender emailSender;


    @Test
    void sendemail(){
        //TODO zmiana autoryzacji przez gmail
        emailSender.sendEmail("szsebastian146@gmail.com","Test","Testowa wiadomość");

    }

}

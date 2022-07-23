package pl.szczypkowski.vehiclesfleetmanager.email.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.szczypkowski.vehiclesfleetmanager.email.model.EmailRequest;
import pl.szczypkowski.vehiclesfleetmanager.user.model.User;
import pl.szczypkowski.vehiclesfleetmanager.user.repository.UserRepository;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class EmailService {


    private final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private final EmailSender emailSender;
    private final UserRepository userRepository;

    public EmailService(EmailSender emailSender, UserRepository userRepository) {
        this.emailSender = emailSender;

        this.userRepository = userRepository;
    }


    ThreadPoolExecutor executor =
            new ThreadPoolExecutor(10, 10, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    public ResponseEntity<?> sendEmail(EmailRequest emailRequest) {
       if(emailRequest!=null && emailRequest.getRecipient().size()>0)
       {
           AtomicInteger counter = new AtomicInteger();
           emailRequest.getRecipient().forEach(e->{
               //TODO wysyłanie wiadomości na wątkach + informacja na widok o postępie
               executor.execute(new Runnable() {
                   @Override
                   public void run() {
                       try {
                           Optional<User> userOptional = userRepository.findByName(e);
                           userOptional.ifPresent(user -> emailSender.sendEmail(user.getEmail(), emailRequest.getTitle(), emailRequest.getContent()));
                           counter.getAndIncrement();
                       } catch (Exception exe) {
                           exe.printStackTrace();
                           LOGGER.error("Nie udało się wysłać wiadomości do użytkownika : " + e);
                       }
                   }});
           });


           return ResponseEntity.status(HttpStatus.OK).body(ToJsonString.toJsonString(
                   "Wysłano "+counter+" z "+emailRequest.getRecipient().size()+" wiadomości"));

       }else
       {
       return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ToJsonString.toJsonString("Dane są nie kompletne. Nie można wysłać wiadomości "));
       }

    }

    public ResponseEntity<?> sendEmailWithAttachment(EmailRequest emailRequest, MultipartFile multipartFile) {

        try{
            if(emailRequest!=null && emailRequest.getRecipient().size()>0&& multipartFile!=null)


            {
                File fileToSend = new File("targetFile.tmp");
                multipartFile.transferTo(fileToSend);
                AtomicInteger counter = new AtomicInteger();
                emailRequest.getRecipient().forEach(e->{
                    try {
                        Optional<User> userOptional = userRepository.findByName(e);
                        userOptional.ifPresent(user -> emailSender.sendEmailWithAttachment(user.getEmail(), emailRequest.getTitle(), emailRequest.getContent(),fileToSend));
                        counter.getAndIncrement();
                    }catch (Exception exe)
                    {
                        exe.printStackTrace();
                        LOGGER.error("Nie udało się wysłać wiadomości do użytkownika : "+e);
                    }
                });


                return ResponseEntity.status(HttpStatus.OK).body(ToJsonString.toJsonString(
                        "Wysłano "+counter+" z "+emailRequest.getRecipient().size()+" wiadomości"));

            }else
            {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ToJsonString.toJsonString("Dane są nie kompletne. Nie można wysłać wiadomości "));
            }


        }catch (Exception e)
        {
            LOGGER.error("Wystąpił błąd podczas wysyłanie wiadomości z załącznikiem. Wiadomość: {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ToJsonString.toJsonString("Wystąpił błąd podczas wysyłanie wiadomości z załącznikiem"));
        }

    }
}

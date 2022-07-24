package pl.szczypkowski.vehiclesfleetmanager.email.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.szczypkowski.vehiclesfleetmanager.email.model.EmailRequest;
import pl.szczypkowski.vehiclesfleetmanager.email.service.EmailService;

@RestController
@RequestMapping("/api/email")
public class EmailSenderController {


    private final EmailService emailService;

    public EmailSenderController(EmailService emailService) {
        this.emailService = emailService;
    }


    @PostMapping("/send")
    public ResponseEntity<?> sendEmail(@RequestBody EmailRequest emailRequest)
    {
            return emailService.sendEmail(emailRequest);
    }

    @PostMapping("/send-with-attachment")
    public ResponseEntity<?> sendEmail(@RequestBody EmailRequest emailRequest,@RequestParam("file") MultipartFile multipartFile)
    {
        return emailService.sendEmailWithAttachment(emailRequest,multipartFile);
    }


}

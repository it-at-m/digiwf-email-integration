package io.muenchendigital.digiwf.email.integration.domain.service;

import io.muenchendigital.digiwf.email.integration.domain.model.Attachment;
import io.muenchendigital.digiwf.email.integration.domain.model.Mail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class MailingServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    private MailingService mailingService;

    @BeforeEach
    void beforeEach() {
        this.mailingService = new MailingService(this.javaMailSender, "from@example.com");
    }

    @Test
    void testSimpleMailValidation() {
        final Mail invalidMail = new Mail();

        final ConstraintViolationException constraintViolationException = assertThrows(ConstraintViolationException.class, () -> {
            this.mailingService.sendMail(invalidMail);
        });

        final Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();

        assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals("No receivers given")));
        assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals("No subject given")));
        assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals("No body given")));
    }

    @Test
    void testMailAttachmentValidation() {
        final List<Attachment> attachments = List.of(new Attachment());
        final Mail invalidMail = new Mail("receiver@example.com", null, null, "Subject", "body", null, attachments);

        final ConstraintViolationException constraintViolationException = assertThrows(ConstraintViolationException.class, () -> {
            this.mailingService.sendMail(invalidMail);
        });

        final Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();

        assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals("Presigned Url is mandatory")));
        assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals("Path is mandatory")));
        assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals("Action is mandatory")));
    }

    @Test
    void testOnlyGetActionsIsValid() {
        final List<Attachment> attachments = List.of(new Attachment("http://localhost:8080", "test/path", "POST"));
        final Mail invalidMail = new Mail("receiver@example.com", null, null, "Subject", "body", null, attachments);

        final ConstraintViolationException constraintViolationException = assertThrows(ConstraintViolationException.class, () -> {
            this.mailingService.sendMail(invalidMail);
        });

        final Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();

        assertTrue(violations.stream().anyMatch(violation -> violation.getMessage().equals("Only action GET is supported")));
    }

}

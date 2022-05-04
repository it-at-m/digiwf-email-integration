package io.muenchendigital.digiwf.email.integration.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Object contains all the information needed to send a mail.
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Mail {

    /**
     * Receiver addresses of the mail, comma separated.
     */
    private String receivers;

    /**
     * CC-Receiver addresses of the mail, comma separated.
     */
    private String receiversCc;

    /**
     * BCC-Receiver addresses of the mail, comma separated.
     */
    private String receiversBcc;

    /**
     * Subject of the mail.
     */
    private String subject;

    /**
     * Body of the mail.
     */
    private String body;

    /**
     * Reply to address
     */
    private String replyTo;

    private List<String> attachmentPaths;

    public boolean hasAttachement() {
        return this.attachmentPaths != null && this.attachmentPaths.size() > 0;
    }

    public boolean hasReplyTo() {
        return !StringUtils.isBlank(this.replyTo);
    }

}
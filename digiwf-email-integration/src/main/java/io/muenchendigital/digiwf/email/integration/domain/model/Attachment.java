package io.muenchendigital.digiwf.email.integration.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Attachment File you want to get from the S3 storage.
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Attachment {

    /**
     * Url to the s3 service.
     */
    @NotBlank(message = "Presigned Url is mandatory")
    private String url;

    /**
     * Path to the file inside in the S3 storage.
     */
    @NotBlank(message = "Path is mandatory")
    private String path;

    /**
     * Proper Http Method (Post, Put, Get, Delete) to interact with S3.
     * Note: Only GET is supported. The mail integration is not intended to modify files!
     */
    @NotBlank(message = "Action is mandatory")
    @Pattern(regexp = "GET", message = "Only action GET is supported")
    private String action;

}

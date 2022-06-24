package io.muenchendigital.digiwf.email.integration.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Attachment File you want to get from the S3 storage.
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Attachment {

    /**
     * The S3 presigned url to load the file from in the S3 storage.
     */
    private String s3PresignedUrl;

    /**
     * Mandatory filename.
     */
    private String fileName;

}

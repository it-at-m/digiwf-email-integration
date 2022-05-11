package io.muenchendigital.digiwf.email.integration.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

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
    private String documentStorageUrl;

    /**
     * Path to the file inside in the S3 storage.
     */
    private String attachmentPath;

    /**
     * Optional filename. If not set, the filename of the file in the S3 Storage is used.
     */
    private String fileName;

    public boolean hasRequiredData() {
        return StringUtils.isNotBlank(attachmentPath) && StringUtils.isNotBlank(documentStorageUrl);
    }

    public boolean hasFileName() {
        return StringUtils.isNotBlank(fileName);
    }
}

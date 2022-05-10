package io.muenchendigital.digiwf.email.integration.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Attachment {

    private String documentStorageUrl;

    private String attachmentPath;

    public boolean hasAllRelevantData() {
        return StringUtils.isNotBlank(attachmentPath) && StringUtils.isNotBlank(documentStorageUrl);
    }

}

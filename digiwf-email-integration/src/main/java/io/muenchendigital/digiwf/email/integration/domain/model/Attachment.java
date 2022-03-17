/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.email.integration.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Mail Attachement.
 * Can be anything e.g. a pdf document.
 *
 */
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Attachment {

    /**
     * Name of the attachment.
     */
    private final String name;

    /**
     * Content of the attachement.
     */
    private final byte[] content;
}

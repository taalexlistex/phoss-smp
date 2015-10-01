/**
 * Copyright (C) 2015 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Version: MPL 1.1/EUPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at:
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * Alternatively, the contents of this file may be used under the
 * terms of the EUPL, Version 1.1 or - as soon they will be approved
 * by the European Commission - subsequent versions of the EUPL
 * (the "Licence"); You may not use this work except in compliance
 * with the Licence.
 * You may obtain a copy of the Licence at:
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * If you wish to allow use of your version of this file only
 * under the terms of the EUPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the EUPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the EUPL License.
 */
package com.helger.peppol.smpserver.data.sql.model;

import java.io.Serializable;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.Converter;
import org.joda.time.LocalDateTime;

import com.helger.db.jpa.annotation.UsedOnlyByJPA;
import com.helger.db.jpa.eclipselink.converter.JPAJodaLocalDateTimeConverter;
import com.helger.peppol.smp.ExtensionType;
import com.helger.peppol.smp.SMPExtensionConverter;

/**
 * Endpoint generated by hbm2java
 *
 * @author PEPPOL.AT, BRZ, Philip Helger
 */
@Entity
@Table (name = "smp_endpoint")
@Converter (name = "localdatetime", converterClass = JPAJodaLocalDateTimeConverter.class)
public class DBEndpoint implements Serializable
{
  private DBEndpointID m_aID;
  private DBProcess m_aProcess;
  private String m_sEndpointReference;
  private boolean m_bRequireBusinessLevelSignature;
  private String m_sMinimumAuthenticationLevel;
  private LocalDateTime m_aServiceActivationDate;
  private LocalDateTime m_aServiceExpirationDate;
  private String m_sCertificate;
  private String m_sServiceDescription;
  private String m_sTechnicalContactUrl;
  private String m_sTechnicalInformationUrl;
  private String m_sExtension;

  @Deprecated
  @UsedOnlyByJPA
  public DBEndpoint ()
  {}

  public DBEndpoint (final DBEndpointID aID,
                     final DBProcess aProcess,
                     final String sEndpointReference,
                     final boolean bRequireBusinessLevelSignature,
                     final LocalDateTime aServiceExpirationDate,
                     final String sCertificate,
                     final String sServiceDescription,
                     final String sTechnicalContactUrl,
                     final String sExtension)
  {
    m_aID = aID;
    m_aProcess = aProcess;
    m_sEndpointReference = sEndpointReference;
    m_bRequireBusinessLevelSignature = bRequireBusinessLevelSignature;
    m_aServiceExpirationDate = aServiceExpirationDate;
    m_sCertificate = sCertificate;
    m_sServiceDescription = sServiceDescription;
    m_sTechnicalContactUrl = sTechnicalContactUrl;
    m_sExtension = sExtension;
  }

  public DBEndpoint (final DBEndpointID aID,
                     final DBProcess aProcess,
                     final String sExtension,
                     final boolean bRequireBusinessLevelSignature,
                     final String sMinimumAuthenticationLevel,
                     final LocalDateTime aServiceActivationDate,
                     final LocalDateTime aServiceExpirationDate,
                     final String sCertificate,
                     final String sServiceDescription,
                     final String sTechnicalContactUrl,
                     final String sTechnicalInformationUrl)
  {
    m_aID = aID;
    m_aProcess = aProcess;
    m_sExtension = sExtension;
    m_bRequireBusinessLevelSignature = bRequireBusinessLevelSignature;
    m_sMinimumAuthenticationLevel = sMinimumAuthenticationLevel;
    m_aServiceActivationDate = aServiceActivationDate;
    m_aServiceExpirationDate = aServiceExpirationDate;
    m_sCertificate = sCertificate;
    m_sServiceDescription = sServiceDescription;
    m_sTechnicalContactUrl = sTechnicalContactUrl;
    m_sTechnicalInformationUrl = sTechnicalInformationUrl;
  }

  @EmbeddedId
  public DBEndpointID getId ()
  {
    return m_aID;
  }

  public void setId (final DBEndpointID aID)
  {
    m_aID = aID;
  }

  @ManyToOne (fetch = FetchType.LAZY)
  @JoinColumns ({ @JoinColumn (name = "processIdentifier",
                               referencedColumnName = "processIdentifier",
                               nullable = false,
                               insertable = false,
                               updatable = false),
                  @JoinColumn (name = "processIdentifierType",
                               referencedColumnName = "processIdentifierType",
                               nullable = false,
                               insertable = false,
                               updatable = false),
                  @JoinColumn (name = "businessIdentifier",
                               referencedColumnName = "businessIdentifier",
                               nullable = false,
                               insertable = false,
                               updatable = false),
                  @JoinColumn (name = "businessIdentifierScheme",
                               referencedColumnName = "businessIdentifierScheme",
                               nullable = false,
                               insertable = false,
                               updatable = false),
                  @JoinColumn (name = "documentIdentifier",
                               referencedColumnName = "documentIdentifier",
                               nullable = false,
                               insertable = false,
                               updatable = false),
                  @JoinColumn (name = "documentIdentifierScheme",
                               referencedColumnName = "documentIdentifierScheme",
                               nullable = false,
                               insertable = false,
                               updatable = false) })
  public DBProcess getProcess ()
  {
    return m_aProcess;
  }

  public void setProcess (final DBProcess aProcess)
  {
    m_aProcess = aProcess;
  }

  @Lob
  @Column (name = "extension", length = 65535)
  public String getExtension ()
  {
    return m_sExtension;
  }

  public void setExtension (@Nullable final String sExtension)
  {
    m_sExtension = sExtension;
  }

  @Transient
  public void setExtension (@Nullable final ExtensionType aExtension)
  {
    setExtension (SMPExtensionConverter.convertToString (aExtension));
  }

  @Column (name = "endpointReference", nullable = false, length = 256)
  public String getEndpointReference ()
  {
    return m_sEndpointReference;
  }

  public void setEndpointReference (final String sEndpointReference)
  {
    m_sEndpointReference = sEndpointReference;
  }

  @Column (name = "requireBusinessLevelSignature", nullable = false)
  public boolean isRequireBusinessLevelSignature ()
  {
    return m_bRequireBusinessLevelSignature;
  }

  public void setRequireBusinessLevelSignature (final boolean bRequireBusinessLevelSignature)
  {
    m_bRequireBusinessLevelSignature = bRequireBusinessLevelSignature;
  }

  @Column (name = "minimumAuthenticationLevel", length = 256)
  public String getMinimumAuthenticationLevel ()
  {
    return m_sMinimumAuthenticationLevel;
  }

  public void setMinimumAuthenticationLevel (final String sMinimumAuthenticationLevel)
  {
    m_sMinimumAuthenticationLevel = sMinimumAuthenticationLevel;
  }

  @Temporal (TemporalType.TIMESTAMP)
  @Column (name = "serviceActivationDate", length = 19)
  @Convert ("localdatetime")
  public LocalDateTime getServiceActivationDate ()
  {
    return m_aServiceActivationDate;
  }

  public void setServiceActivationDate (final LocalDateTime aServiceActivationDate)
  {
    m_aServiceActivationDate = aServiceActivationDate;
  }

  @Temporal (TemporalType.TIMESTAMP)
  @Column (name = "serviceExpirationDate", nullable = false, length = 19)
  @Convert ("localdatetime")
  public LocalDateTime getServiceExpirationDate ()
  {
    return m_aServiceExpirationDate;
  }

  public void setServiceExpirationDate (final LocalDateTime aServiceExpirationDate)
  {
    m_aServiceExpirationDate = aServiceExpirationDate;
  }

  @Lob
  @Column (name = "certificate", nullable = false, length = 65535)
  public String getCertificate ()
  {
    return m_sCertificate;
  }

  public void setCertificate (final String sCertificate)
  {
    m_sCertificate = sCertificate;
  }

  @Lob
  @Column (name = "serviceDescription", nullable = false, length = 65535)
  public String getServiceDescription ()
  {
    return m_sServiceDescription;
  }

  public void setServiceDescription (final String sServiceDescription)
  {
    m_sServiceDescription = sServiceDescription;
  }

  @Column (name = "technicalContactUrl", nullable = false, length = 256)
  public String getTechnicalContactUrl ()
  {
    return m_sTechnicalContactUrl;
  }

  public void setTechnicalContactUrl (final String sTechnicalContactUrl)
  {
    m_sTechnicalContactUrl = sTechnicalContactUrl;
  }

  @Column (name = "technicalInformationUrl", length = 256)
  public String getTechnicalInformationUrl ()
  {
    return m_sTechnicalInformationUrl;
  }

  public void setTechnicalInformationUrl (final String sTechnicalInformationUrl)
  {
    m_sTechnicalInformationUrl = sTechnicalInformationUrl;
  }
}

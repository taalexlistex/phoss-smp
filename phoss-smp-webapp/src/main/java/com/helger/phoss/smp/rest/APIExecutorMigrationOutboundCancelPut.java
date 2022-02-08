/*
 * Copyright (C) 2014-2022 Philip Helger and contributors
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.phoss.smp.rest;

import java.util.Map;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.http.CHttp;
import com.helger.http.basicauth.BasicAuthClientCredentials;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.IIdentifierFactory;
import com.helger.phoss.smp.domain.SMPMetaManager;
import com.helger.phoss.smp.domain.pmigration.EParticipantMigrationDirection;
import com.helger.phoss.smp.domain.pmigration.EParticipantMigrationState;
import com.helger.phoss.smp.domain.pmigration.ISMPParticipantMigration;
import com.helger.phoss.smp.domain.pmigration.ISMPParticipantMigrationManager;
import com.helger.phoss.smp.domain.user.SMPUserManagerPhoton;
import com.helger.phoss.smp.exception.SMPBadRequestException;
import com.helger.phoss.smp.restapi.ISMPServerAPIDataProvider;
import com.helger.photon.api.IAPIDescriptor;
import com.helger.servlet.response.UnifiedResponse;
import com.helger.web.scope.IRequestWebScopeWithoutResponse;

/**
 * REST API to cancel an outbound migration for a participant
 *
 * @author Philip Helger
 * @since 5.6.0
 */
public final class APIExecutorMigrationOutboundCancelPut extends AbstractSMPAPIExecutor
{
  private static final Logger LOGGER = LoggerFactory.getLogger (APIExecutorMigrationOutboundCancelPut.class);

  public void invokeAPI (@Nonnull final IAPIDescriptor aAPIDescriptor,
                         @Nonnull @Nonempty final String sPath,
                         @Nonnull final Map <String, String> aPathVariables,
                         @Nonnull final IRequestWebScopeWithoutResponse aRequestScope,
                         @Nonnull final UnifiedResponse aUnifiedResponse) throws Exception
  {
    final String sLogPrefix = "[Migration-Outbound-Cancel] ";

    // Is the writable API disabled?
    if (SMPMetaManager.getSettings ().isRESTWritableAPIDisabled ())
    {
      LOGGER.warn (sLogPrefix + "The writable REST API is disabled. migrationOutboundCancel will not be executed.");
      aUnifiedResponse.setStatus (CHttp.HTTP_PRECONDITION_FAILED);
    }
    else
    {
      final String sServiceGroupID = aPathVariables.get (SMPRestFilter.PARAM_SERVICE_GROUP_ID);

      LOGGER.info (sLogPrefix + "Cancelling outbound migration for Service Groups ID '" + sServiceGroupID + "'");

      // Only authenticated user may do so
      final BasicAuthClientCredentials aBasicAuth = SMPRestRequestHelper.getMandatoryAuth (aRequestScope.headers ());
      SMPUserManagerPhoton.validateUserCredentials (aBasicAuth);

      final ISMPServerAPIDataProvider aDataProvider = new SMPRestDataProvider (aRequestScope, null);
      final ISMPParticipantMigrationManager aParticipantMigrationMgr = SMPMetaManager.getParticipantMigrationMgr ();
      final IIdentifierFactory aIdentifierFactory = SMPMetaManager.getIdentifierFactory ();

      final IParticipantIdentifier aServiceGroupID = aIdentifierFactory.parseParticipantIdentifier (sServiceGroupID);
      if (aServiceGroupID == null)
      {
        // Invalid identifier
        throw SMPBadRequestException.failedToParseSG (sServiceGroupID, aDataProvider.getCurrentURI ());
      }

      // Find matching migration object
      final ISMPParticipantMigration aMigration = aParticipantMigrationMgr.getParticipantMigrationOfParticipantID (EParticipantMigrationDirection.OUTBOUND,
                                                                                                                   aServiceGroupID);
      if (aMigration == null)
        throw new SMPBadRequestException ("Failed to resolve outbound participant migration for Service Group ID '" + sServiceGroupID + "'",
                                          aDataProvider.getCurrentURI ());

      final String sMigrationID = aMigration.getID ();

      // Change migration state
      if (aParticipantMigrationMgr.setParticipantMigrationState (sMigrationID, EParticipantMigrationState.CANCELLED).isUnchanged ())
      {
        throw new SMPBadRequestException ("Failed to cancel the outbound Participant Migration with ID '" + sMigrationID + "'",
                                          aDataProvider.getCurrentURI ());
      }

      LOGGER.info (sLogPrefix + "The outbound Participant Migration with ID '" + sMigrationID + "' was successfully cancelled!");
      aUnifiedResponse.setStatus (CHttp.HTTP_OK);
    }
  }
}

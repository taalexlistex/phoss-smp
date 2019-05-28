/**
 * Copyright (C) 2015-2019 Philip Helger and contributors
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The PEPPOL project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.peppol.smpserver.exception;

import javax.annotation.Nonnull;

import com.helger.peppol.smpserver.smlhook.RegistrationHookException;

/**
 * This exception is thrown if an error occurred communicating with the SML
 *
 * @author Philip Helger
 * @since 5.1.0
 */
public class SMPSMLException extends SMPServerException
{
  public SMPSMLException (@Nonnull final String sMsg, @Nonnull final RegistrationHookException aCause)
  {
    super (sMsg, aCause);
  }
}
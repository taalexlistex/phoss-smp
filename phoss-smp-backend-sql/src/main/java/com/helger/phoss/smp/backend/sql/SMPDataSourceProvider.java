/**
 * Copyright (C) 2015-2020 Philip Helger and contributors
 * philip[at]helger[dot]com
 *
 * The Original Code is Copyright The Peppol project (http://www.peppol.eu)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.helger.phoss.smp.backend.sql;

import com.helger.db.jdbc.AbstractConnector;
import com.helger.phoss.smp.SMPServerConfiguration;

final class SMPDataSourceProvider extends AbstractConnector
{
  @Override
  protected String getJDBCDriverClassName ()
  {
    return SMPServerConfiguration.getConfigFile ().getAsString (SMPJDBCConfiguration.CONFIG_JDBC_DRIVER);
  }

  @Override
  protected String getUserName ()
  {
    return SMPServerConfiguration.getConfigFile ().getAsString (SMPJDBCConfiguration.CONFIG_JDBC_USER);
  }

  @Override
  protected String getPassword ()
  {
    return SMPServerConfiguration.getConfigFile ().getAsString (SMPJDBCConfiguration.CONFIG_JDBC_PASSWORD);
  }

  @Override
  protected String getDatabaseName ()
  {
    return SMPServerConfiguration.getConfigFile ().getAsString (SMPJDBCConfiguration.CONFIG_TARGET_DATABASE);
  }

  @Override
  public String getConnectionUrl ()
  {
    return SMPServerConfiguration.getConfigFile ().getAsString (SMPJDBCConfiguration.CONFIG_JDBC_URL);
  }
}
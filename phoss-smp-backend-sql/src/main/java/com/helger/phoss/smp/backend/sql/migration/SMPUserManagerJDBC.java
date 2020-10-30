/**
 * Copyright (C) 2019-2020 Philip Helger and contributors
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
package com.helger.phoss.smp.backend.sql.migration;

import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.collection.impl.ICommonsMap;
import com.helger.commons.type.ObjectType;
import com.helger.db.jdbc.callback.ConstantPreparedStatementDataProvider;
import com.helger.db.jdbc.executor.DBExecutor;
import com.helger.db.jdbc.executor.DBResultRow;
import com.helger.phoss.smp.backend.sql.EDatabaseType;
import com.helger.phoss.smp.backend.sql.SMPDataSourceSingleton;
import com.helger.phoss.smp.backend.sql.domain.DBUser;
import com.helger.phoss.smp.backend.sql.mgr.AbstractJDBCEnabledManager;

/**
 * This is only used in the migration.
 *
 * @author Philip Helger
 * @since 5.3.0
 */
final class SMPUserManagerJDBC extends AbstractJDBCEnabledManager
{
  public static final ObjectType OT = new ObjectType ("smpuser");

  public SMPUserManagerJDBC (@Nonnull final DBExecutor aDBExec)
  {
    super (aDBExec);
  }

  @Nonnull
  @ReturnsMutableCopy
  public ICommonsList <DBUser> getAllUsers ()
  {
    final Optional <ICommonsList <DBResultRow>> aDBResult = executor ().queryAll ("SELECT username, password FROM smp_user");
    final ICommonsList <DBUser> ret = new CommonsArrayList <> ();
    if (aDBResult.isPresent ())
      for (final DBResultRow aRow : aDBResult.get ())
        ret.add (new DBUser (aRow.getAsString (0), aRow.getAsString (1)));
    return ret;
  }

  public void updateOwnershipsAndKillUsers (@Nonnull final ICommonsMap <String, String> aOldToNewMap)
  {
    ValueEnforcer.notNull (aOldToNewMap, "OldToNewMap");

    executor ().performInTransaction ( () -> {
      // Drop the Foreign Key Constraint - do this all the time
      try
      {
        final EDatabaseType eDBType = SMPDataSourceSingleton.getDatabaseType ();
        switch (eDBType)
        {
          case MYSQL:
            executor ().executeStatement ("ALTER TABLE smp_ownership DROP FOREIGN KEY FK_smp_ownership_username;");
            break;
          case POSTGRESQL:
            executor ().executeStatement ("ALTER TABLE smp_ownership DROP CONSTRAINT FK_smp_ownership_username;");
            break;
          default:
            throw new IllegalStateException ("The migration code for DB type " + eDBType + " is missing");
        }
      }
      catch (final RuntimeException ex)
      {
        // Ignore
      }

      // Update user names
      for (final Map.Entry <String, String> aEntry : aOldToNewMap.entrySet ())
      {
        final String sOld = aEntry.getKey ();
        final String sNew = aEntry.getValue ();
        executor ().insertOrUpdateOrDelete ("UPDATE smp_ownership SET username=? WHERE username=?",
                                            new ConstantPreparedStatementDataProvider (sNew, sOld));
      }

      try
      {
        executor ().executeStatement ("DROP TABLE smp_user;");
      }
      catch (final RuntimeException ex)
      {
        // Ignore
      }
    });
  }
}

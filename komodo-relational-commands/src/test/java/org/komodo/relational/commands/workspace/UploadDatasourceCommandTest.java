/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.komodo.relational.commands.workspace;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.komodo.relational.commands.AbstractCommandTest;
import org.komodo.relational.datasource.Datasource;
import org.komodo.relational.workspace.WorkspaceManager;
import org.komodo.shell.api.CommandResult;

/**
 * Test Class to test {@link UploadDatasourceCommand}.
 */
@SuppressWarnings( { "javadoc", "nls" } )
public final class UploadDatasourceCommandTest extends AbstractCommandTest {

    private static final String UPLOAD_SOURCE = UploadDatasourceCommandTest.class.getClassLoader()
    																						.getResource("dashboardDS.xml").getFile();

    @Test
    public void shouldUploadDatasource() throws Exception {
        final String[] commands = { "upload-datasource " + UPLOAD_SOURCE };
        final CommandResult result = execute( commands );
        assertCommandResultOk(result);

        WorkspaceManager wkspMgr = WorkspaceManager.getInstance(_repo);
        Datasource[] sources = wkspMgr.findDatasources(getTransaction());

        assertEquals(1, sources.length);
        assertEquals("DashboardDS", sources[0].getName(getTransaction()));
    }
    
    @Test( expected = AssertionError.class )
    public void shouldNotUploadDatasourceIfExists() throws Exception {
        final String[] commands = { "create-datasource DashboardDS ",
                                    "upload-datasource " + UPLOAD_SOURCE };
        execute( commands );
    }
    
    @Test
    public void shouldUploadDatasourceIfExistsWithOverwrite() throws Exception {
        final String[] commands = { "create-datasource DashboardDS ",
                                    "upload-datasource " + UPLOAD_SOURCE + " -o" };
        final CommandResult result = execute( commands );
        assertCommandResultOk(result);

        WorkspaceManager wkspMgr = WorkspaceManager.getInstance(_repo);
        Datasource[] sources = wkspMgr.findDatasources(getTransaction());

        assertEquals(1, sources.length);
        assertEquals("DashboardDS", sources[0].getName(getTransaction()));
    }

}

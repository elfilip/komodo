/*************************************************************************************
 * JBoss, Home of Professional Open Source.
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership. Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 ************************************************************************************/
package org.komodo.shell.api;

import java.io.InputStream;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.komodo.core.KEngine;
import org.komodo.spi.KException;
import org.komodo.spi.constants.StringConstants;
import org.komodo.spi.repository.KomodoObject;
import org.komodo.spi.repository.Repository.UnitOfWork;

/**
 * WorkspaceStatus interface.
 */
public interface WorkspaceStatus extends StringConstants {

    /**
     * The type id of the root object
     */
    String WORKSPACE_TYPE = "WORKSPACE"; //$NON-NLS-1$

    @SuppressWarnings("javadoc")
	public static final String RECORDING_FILE_KEY = "RECORDING_FILE"; //$NON-NLS-1$
    @SuppressWarnings("javadoc")
	public static final String IMPORT_DEFAULT_DIR_KEY = "IMPORT_DEFAULT_DIR"; //$NON-NLS-1$
    @SuppressWarnings("javadoc")
	public static final String EXPORT_DEFAULT_DIR_KEY = "EXPORT_DEFAULT_DIR"; //$NON-NLS-1$

    /**
     * Set to <code>true</code> if the command category should be displayed when help displays available commands.
     */
    String SHOW_COMMAND_CATEGORY = "SHOW_COMMAND_CATEGORY"; //$NON-NLS-1$

    /**
     * Set to <code>true</code> if the full path of the current context object should be displayed in the prompt.
     */
    String SHOW_FULL_PATH_IN_PROMPT_KEY = "SHOW_FULL_PATH_IN_PROMPT"; //$NON-NLS-1$

    /**
     * Set to <code>true</code> if the hidden properties should also be displayed.
     */
    String SHOW_HIDDEN_PROPERTIES_KEY = "SHOW_HIDDEN_PROPERTIES"; //$NON-NLS-1$

    /**
     * Set to <code>true</code> if property namespace prefixes should be shown.
     */
    String SHOW_PROP_NAME_PREFIX_KEY = "SHOW_PROP_NAME_PREFIX"; //$NON-NLS-1$

    /**
     * Set to <code>true</code> if the type of the current context object should be displayed in the prompt.
     */
    String SHOW_TYPE_IN_PROMPT_KEY = "SHOW_TYPE_IN_PROMPT"; //$NON-NLS-1$

    /**
     * Indicates if single commands should be auto-committed after executing.
     */
    String AUTO_COMMIT = "AUTO_COMMIT"; //$NON-NLS-1$

    /**
     * An unmodifiable collection of the valid global property names and their non-<code>null</code> default values.
     */
    Map< String, String > GLOBAL_PROPS = Collections.unmodifiableMap( new HashMap< String, String >() {

        private static final long serialVersionUID = 1L;

        // add default values for EVERY property
        {
            put( EXPORT_DEFAULT_DIR_KEY, "." ); //$NON-NLS-1$
            put( IMPORT_DEFAULT_DIR_KEY, "." ); //$NON-NLS-1$
            put( RECORDING_FILE_KEY, "./commandOutput.txt" ); //$NON-NLS-1$
            put( SHOW_COMMAND_CATEGORY, Boolean.TRUE.toString() );
            put( SHOW_FULL_PATH_IN_PROMPT_KEY, Boolean.FALSE.toString() );
            put( SHOW_HIDDEN_PROPERTIES_KEY, Boolean.FALSE.toString() );
            put( SHOW_PROP_NAME_PREFIX_KEY, Boolean.FALSE.toString() );
            put( SHOW_TYPE_IN_PROMPT_KEY, Boolean.TRUE.toString() );
            put( AUTO_COMMIT, Boolean.TRUE.toString() );
        }
    } );

    /**
     * @return the command names available for the current context (never <code>null</code>)
     * @throws Exception
     *         if an error occurs
     */
    String[] getAvailableCommandNames() throws Exception;

    /**
     * @return the commands available for the current context (never <code>null</code>)
     * @throws Exception
     *         if an error occurs
     */
    Set<ShellCommand> getAvailableCommands() throws Exception;

    /**
     * Update the available commands for the current context.
     */
    void updateAvailableCommands();

    /**
     * @param commandName
     *        the name of the command, or the command alias, being requested (cannot be empty)
     * @return the command (never <code>null</code>)
     * @throws Exception
     *         if command not found or an error occurs
     */
    ShellCommand getCommand( final String commandName ) throws Exception;

    /**
     * Sets the specified global property value.
     *
     * @param propKey
     *        the property key (cannot be empty)
     * @param propValue
     *        the property value (can be empty when changing to the default value)
     */
    void setGlobalProperty( final String propKey,
                            final String propValue );

    /**
     * Sets provided workspace properties on startup
     *
     * @param props
     *        the properties (can be <code>null</code> or null to remove)
     * @throws Exception
     *         if an error occurs
     */
    void setProvidedProperties( final Properties props ) throws Exception;

    /**
     * Sets the specified provided property value in workspace properties.
     *
     * @param propKey
     *        the property key (cannot be empty)
     * @param propValue
     *        the property value (can be empty when changing to the default value)
     */
    void setProvidedProperty( final String propKey,
                              final String propValue );

    /**
     * Gets a copy of the provided workspace properties.  This returns only the properties that are provided.
     *
     * @return the provided workspace properties (never <code>null</code> or empty)
     * @see #setProvidedProperty(String, String)
     */
    Properties getProvidedProperties();

    /**
     * Sets global workspace properties on startup
     *
     * @param props
     *        the properties (can be <code>null</code> or empty if resetting to default values)
     * @throws Exception
     *         if an error occurs
     */
    void setGlobalProperties( final Properties props ) throws Exception;

    /**
     * Gets a copy of the global workspace properties.  This includes the defined global properties and defined hidden properties.
     *
     * @return the global workspace properties (never <code>null</code> or empty)
     * @see #setGlobalProperty(String, String)
     */
    Properties getGlobalProperties();

    /**
     * @param propertyName the name of the property being checked (cannot be empty)
     * @return <code>true</code> if a known boolean property
     */
    boolean isBooleanProperty( final String propertyName );

    /**
     * @param propertyName
     *        the name of the property being checked (cannot be empty)
     * @param proposedValue
     *        the proposed value (can be empty if removing or resetting to the default value)
     * @return an error message or <code>null</code> if name and proposed value are valid
     */
    String validateGlobalPropertyValue( final String propertyName,
                                        final String proposedValue );

	/**
	 * Get the workspace context
	 * @return the workspace context
	 */
	KomodoObject getRootContext();

	/**
	 * Set the current workspace context
	 * @param context the current workspace context
	 * @throws Exception if error occurs
	 */
	void setCurrentContext(KomodoObject context) throws Exception;

    /**
     * Get the current workspace context
     * @return the current workspace context
     */
    KomodoObject getCurrentContext();

    /**
     * Get the context at the supplied display path
     * @param displayPath the display path
     * @return the context, null if not found.
     */
    KomodoObject getContextForDisplayPath(String displayPath);

    /**
     * Get the display path for the supplied context
     * @param kObj the context
     * @return the display path
     */
    String getDisplayPath(KomodoObject kObj);

    /**
     * Get the display path for the current workspace context
     * @return the display path
     */
    String getCurrentContextDisplayPath();

    /**
     * Close the recording output file if open
     */
    void closeRecordingWriter();

	/**
	 * Toggles the recording status 'on' or 'off'
	 * @param recordState 'true' to enable recording, 'false' to disable
	 */
	void setRecordingStatus(boolean recordState);

	/**
	 * Get the recording status
	 * @return 'true' if recording is enabled, 'false' if not.
	 */
	boolean getRecordingStatus();

	/**
	 * Get the Writer for recording output
	 * @return the recording writer
	 */
	Writer getRecordingWriter();

    /**
     * @return <code>true</code> if the command category is being displayed when help displays the available commands
     * @see #SHOW_COMMAND_CATEGORY
     */
    boolean isShowingCommandCategory();

    /**
     * @return <code>true</code> if the full object path is being displayed in the prompt
     * @see #SHOW_FULL_PATH_IN_PROMPT_KEY
     */
    boolean isShowingFullPathInPrompt();

    /**
     * @return <code>true</code> if hidden properties are being displayed
     * @see #SHOW_HIDDEN_PROPERTIES_KEY
     */
    boolean isShowingHiddenProperties();

    /**
     * @return <code>true</code> if property namespace prefixes should be shown
     * @see #SHOW_PROP_NAME_PREFIX_KEY
     */
    boolean isShowingPropertyNamePrefixes();

    /**
     * @return <code>true</code> if property namespace prefixes should be shown
     * @see #SHOW_TYPE_IN_PROMPT_KEY
     */
    boolean isShowingTypeInPrompt();

	/**
	 * Add a WorkspaceContext Event Handler
	 * @param handler the workspace context eventHandler
	 */
	void addHandler(WorkspaceStatusEventHandler handler);

	/**
	 * Remove a WorkspaceContext Event Handler
	 * @param handler the workspace context eventHandler
	 */
	void removeHandler(WorkspaceStatusEventHandler handler);

    /**
     * @return the input stream
     */
    InputStream getInputStream();

    /**
     * @return the output writer
     */
    Writer getOutputWriter();

    /**
     * Get the ShellCommandFactory
     * @return the command factory
     */
    ShellCommandFactory getCommandFactory();

    /**
     * @return the komodo engine
     */
    KEngine getEngine();

    /**
     * Commit
     * @param source identifier for commit
     * @throws Exception
     */
    void commit(String source) throws Exception;

    /**
     * Rolls back any unsaved changes.
     *
     * @param source
     *        the identifier used to name the transaction created after the rollback (cannot be empty)
     * @throws Exception
     *         if an error occurs
     */
    void rollback( final String source ) throws Exception;

    /**
     * The transaction should <strong>NEVER</strong> be cached by a caller since they can be committed by a command or the shell
     * framework at any time leaving the cached transaction in a state where it cannot be used.
     *
     * @return the current transaction (never <code>null</code>)
     */
    UnitOfWork getTransaction();

    /**
     * @param transaction
     *        the transaction (cannot be <code>null</code> or have a state that is not
     *        {@link org.komodo.spi.repository.Repository.UnitOfWork.State#NOT_STARTED})
     */
    void setTransaction( final UnitOfWork transaction );

    /**
     * @return the parent komodo shell
     */
    KomodoShell getShell();

    /**
     * Get Additional generic workspace state objects
     * @return the map of state objects
     */
    Map<String,KomodoObject> getStateObjects();

    /**
     * Set a generic workspace state object
     * @param key the state key
     * @param stateObj the state object
     * @throws KException the exception
     */
    void setStateObject(String key, KomodoObject stateObj) throws KException;

    /**
     * Remove a generic workspace state object
     * @param key the state key
     */
    void removeStateObject(String key);

    /**
     * Resolve a KomodoObject into a concrete class if available
     * @param <T> the specific {@link KomodoObject} type being resolved
     * @param kObj the KomodoObject
     * @return the resolved Object
     * @throws KException the exception
     */
    < T extends KomodoObject > T resolve ( final KomodoObject kObj ) throws KException;

    /**
     * Get the label provider for current context
     * @return the current label provider (never <code>null</code>)
     */
    KomodoObjectLabelProvider getCurrentContextLabelProvider();

    /**
     * Get the label provider for specified object
     * @param KomodoObject to be processed by label provider
     * @return the current label provider (never <code>null</code>)
     */
    KomodoObjectLabelProvider getObjectLabelProvider(KomodoObject kobject);

    /**
     * Get the object type string for display
     * @param kObj the KomodoObject
     * @return the type display string
     * @throws KException the exception
     */
    String getTypeDisplay ( final KomodoObject kObj ) throws KException;

    /**
     * Get status messages from providers that should be displayed
     * @param kObj the KomodoObject
     * @return the status messages
     */
    List<String> getProvidedStatusMessages( final KomodoObject kObj );

    /**
     * Initialize any workspace state from providers
     * @param globalProps the workspace global properties
     * @throws KException the exception
     */
    public void initProvidedStates( final Properties globalProps ) throws KException;

    /**
     * @return <code>true</code> if the current transaction should be committed after each command execution or rolled back if
     *         command fails
     * @see #AUTO_COMMIT
     */
    boolean isAutoCommit();

    /**
     * Returns a collection of available label providers. DefaultLableProvider is not included in the collection.
     *
     * @return a collection of alternative label providers
     */
    public Collection<KomodoObjectLabelProvider> getAlternateLabelProviders();

}

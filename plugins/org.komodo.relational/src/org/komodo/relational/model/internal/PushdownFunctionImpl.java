/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.komodo.relational.model.internal;

import org.komodo.relational.Messages;
import org.komodo.relational.Messages.Relational;
import org.komodo.relational.RelationalProperties;
import org.komodo.relational.internal.AdapterFactory;
import org.komodo.relational.internal.RelationalModelFactory;
import org.komodo.relational.internal.TypeResolver;
import org.komodo.relational.model.Model;
import org.komodo.relational.model.ProcedureResultSet;
import org.komodo.relational.model.PushdownFunction;
import org.komodo.repository.ObjectImpl;
import org.komodo.spi.KException;
import org.komodo.spi.repository.KomodoObject;
import org.komodo.spi.repository.KomodoType;
import org.komodo.spi.repository.Repository;
import org.komodo.spi.repository.Repository.UnitOfWork;
import org.modeshape.sequencer.ddl.dialect.teiid.TeiidDdlLexicon.CreateProcedure;
import org.modeshape.sequencer.ddl.dialect.teiid.TeiidDdlLexicon.SchemaElement;

/**
 * An implementation of a pushdown function.
 */
public final class PushdownFunctionImpl extends FunctionImpl implements PushdownFunction {

    /**
     * The resolver of a {@link PushdownFunction}.
     */
    public static final TypeResolver RESOLVER = new TypeResolver() {

        /**
         * {@inheritDoc}
         *
         * @see org.komodo.relational.internal.TypeResolver#create(org.komodo.spi.repository.Repository.UnitOfWork,
         *      org.komodo.spi.repository.KomodoObject, java.lang.String, org.komodo.relational.RelationalProperties)
         */
        @Override
        public PushdownFunction create( final UnitOfWork transaction,
                                        final KomodoObject parent,
                                        final String id,
                                        final RelationalProperties properties ) throws KException {
            final AdapterFactory adapter = new AdapterFactory( parent.getRepository() );
            final Model parentModel = adapter.adapt( transaction, parent, Model.class );
            return RelationalModelFactory.createPushdownFunction( transaction, parent.getRepository(), parentModel, id );
        }

        /**
         * {@inheritDoc}
         *
         * @see org.komodo.relational.internal.TypeResolver#identifier()
         */
        @Override
        public KomodoType identifier() {
            return IDENTIFIER;
        }

        /**
         * {@inheritDoc}
         *
         * @see org.komodo.relational.internal.TypeResolver#owningClass()
         */
        @Override
        public Class< PushdownFunctionImpl > owningClass() {
            return PushdownFunctionImpl.class;
        }

        /**
         * {@inheritDoc}
         *
         * @see org.komodo.relational.internal.TypeResolver#resolvable(org.komodo.spi.repository.Repository.UnitOfWork,
         *      org.komodo.spi.repository.Repository, org.komodo.spi.repository.KomodoObject)
         */
        @Override
        public boolean resolvable( final UnitOfWork transaction,
                                   final Repository repository,
                                   final KomodoObject kobject ) {
            try {
                ObjectImpl.validateType( transaction, repository, kobject, CreateProcedure.FUNCTION_STATEMENT );
                ObjectImpl.validatePropertyValue( transaction,
                                                  repository,
                                                  kobject,
                                                  SchemaElement.TYPE,
                                                  SchemaElementType.FOREIGN.toString() );
                return true;
            } catch (final Exception e) {
                // not resolvable
            }

            return false;
        }

        /**
         * {@inheritDoc}
         *
         * @see org.komodo.relational.internal.TypeResolver#resolve(org.komodo.spi.repository.Repository.UnitOfWork,
         *      org.komodo.spi.repository.Repository, org.komodo.spi.repository.KomodoObject)
         */
        @Override
        public PushdownFunction resolve( final UnitOfWork transaction,
                                         final Repository repository,
                                         final KomodoObject kobject ) throws KException {
            return new PushdownFunctionImpl( transaction, repository, kobject.getAbsolutePath() );
        }

    };

    /**
     * @param uow
     *        the transaction (can be <code>null</code> if update should be automatically committed)
     * @param repository
     *        the repository where the relational object exists (cannot be <code>null</code>)
     * @param workspacePath
     *        the workspace relative path (cannot be empty)
     * @throws KException
     *         if an error occurs or if node at specified path is not a procedure
     */
    public PushdownFunctionImpl( final UnitOfWork uow,
                                 final Repository repository,
                                 final String workspacePath ) throws KException {
        super( uow, repository, workspacePath );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.relational.model.PushdownFunction#getResultSet(org.komodo.spi.repository.Repository.UnitOfWork)
     */
    @Override
    public ProcedureResultSet getResultSet( final UnitOfWork uow ) throws KException {
        UnitOfWork transaction = uow;

        if (uow == null) {
            transaction = getRepository().createTransaction( "pushdownfunctionimpl-getResultSet", true, null ); //$NON-NLS-1$
        }

        assert ( transaction != null );

        try {
            ProcedureResultSet result = null;

            if (hasChild( transaction, CreateProcedure.RESULT_SET )) {
                final KomodoObject kobject = getChild( transaction, CreateProcedure.RESULT_SET );

                if (kobject != null) {
                    if (DataTypeResultSetImpl.RESOLVER.resolvable( transaction, getRepository(), kobject )) {
                        result = ( ProcedureResultSet )DataTypeResultSetImpl.RESOLVER.resolve( transaction,
                                                                                               getRepository(),
                                                                                               kobject );
                    } else if (TabularResultSetImpl.RESOLVER.resolvable( transaction, getRepository(), kobject )) {
                        result = ( ProcedureResultSet )TabularResultSetImpl.RESOLVER.resolve( transaction,
                                                                                              getRepository(),
                                                                                              kobject );
                    } else {
                        LOGGER.error( Messages.getString( Relational.UNEXPECTED_RESULT_SET_TYPE, kobject.getAbsolutePath() ) );
                    }
                }
            }

            if (uow == null) {
                transaction.commit();
            }

            return result;
        } catch (final Exception e) {
            throw handleError( uow, transaction, e );
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.spi.repository.KomodoObject#getTypeId()
     */
    @Override
    public int getTypeId() {
        return TYPE_ID;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.repository.ObjectImpl#getTypeIdentifier(org.komodo.spi.repository.Repository.UnitOfWork)
     */
    @Override
    public KomodoType getTypeIdentifier( final UnitOfWork uow ) {
        return RESOLVER.identifier();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.relational.model.PushdownFunction#removeResultSet(org.komodo.spi.repository.Repository.UnitOfWork)
     */
    @Override
    public void removeResultSet( final UnitOfWork uow ) throws KException {
        UnitOfWork transaction = uow;

        if (transaction == null) {
            transaction = getRepository().createTransaction( "pushdownfunctionimpl-removeResultSet", false, null ); //$NON-NLS-1$
        }

        assert ( transaction != null );

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug( "removeResultSet: transaction = {0}, pushdown function = {1}", transaction.getName(), getAbsolutePath() ); //$NON-NLS-1$
        }

        try {
            // delete existing result set
            final ProcedureResultSet resultSet = getResultSet( transaction );

            if (resultSet == null) {
                throw new KException( Messages.getString( Relational.RESULT_SET_NOT_FOUND_TO_REMOVE, getAbsolutePath() ) );
            }

            removeChild( transaction, resultSet.getName( transaction ) );

            if (uow == null) {
                transaction.commit();
            }
        } catch (final Exception e) {
            throw handleError( uow, transaction, e );
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.relational.model.PushdownFunction#setResultSet(org.komodo.spi.repository.Repository.UnitOfWork, boolean)
     */
    @Override
    public ProcedureResultSet setResultSet( final UnitOfWork uow,
                                            final boolean tabularResultSet ) throws KException {
        UnitOfWork transaction = uow;

        if (transaction == null) {
            transaction = getRepository().createTransaction( "pushdownfunctionimpl-setResultSet", false, null ); //$NON-NLS-1$
        }

        assert ( transaction != null );

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug( "setResultSet: transaction = {0}, tabularResultSet = {1}", transaction.getName(), tabularResultSet ); //$NON-NLS-1$
        }

        try {
            // delete existing result set
            final ProcedureResultSet resultSet = getResultSet( transaction );

            if (resultSet != null) {
                removeChild( transaction, resultSet.getName( transaction ) );
            }

            ProcedureResultSet result = null;

            if (tabularResultSet) {
                result = RelationalModelFactory.createTabularResultSet( transaction, getRepository(), this );
            } else {
                result = RelationalModelFactory.createDataTypeResultSet( transaction, getRepository(), this );
            }

            if (uow == null) {
                transaction.commit();
            }

            return result;
        } catch (final Exception e) {
            throw handleError( uow, transaction, e );
        }
    }

}

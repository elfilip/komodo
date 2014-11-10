/* Generated By:JJTree: Do not edit this line. Reference.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=TeiidNodeFactory,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.query.sql.symbol;

import org.komodo.spi.query.sql.symbol.IReference;
import org.teiid.query.metadata.TempMetadataID;
import org.teiid.query.parser.LanguageVisitor;
import org.teiid.query.parser.TeiidClientParser;
import org.teiid.query.sql.lang.SimpleNode;

/**
 *
 */
public class Reference extends SimpleNode implements Expression, IReference<LanguageVisitor> {

    /**
     *
     */
    public interface Constraint {
        /**
         * @param value
         * @throws Exception
         */
        public void validate(Object value) throws Exception;
    }

    private int refIndex;

    private Class<?> type;

    private ElementSymbol expression;

    private boolean positional;

    private boolean optional;

    private Constraint constraint;

    /**
     * @param p
     * @param id
     */
    public Reference(TeiidClientParser p, int id) {
        super(p, id);
    }

    @Override
    public Class<?> getType() {
        if (this.isPositional() && this.expression == null) {
            return type;
        }
        return expression.getType();
    }

    /**
     * @param type
     */
    public void setType(Class<?> type) {
        this.type = type;
    }

    /**
     * @return true if correlated
     */
    public boolean isCorrelated() {
        if (this.isPositional()) {
            return false;
        }
        //metadata hack
        if (this.expression.getGroupSymbol() == null || !(this.expression.getGroupSymbol().getMetadataID() instanceof TempMetadataID)) {
            return true;
        }
        
        TempMetadataID tid = (TempMetadataID)this.expression.getGroupSymbol().getMetadataID();
        return !tid.isScalarGroup();
    }

    @Override
    public boolean isPositional() {
        return this.positional;
    }

    /**
     * @param positional the positional to set
     */
    public void setPositional(boolean positional) {
        this.positional = positional;
    }

    @Override
    public ElementSymbol getExpression() {
        return this.expression;    
    }

    /**
     * @param expression the expression to set
     */
    public void setExpression(ElementSymbol expression) {
        this.expression = expression;
    }

    /**
     * @return reference index
     */
    public int getIndex() {
        return this.refIndex;
    }

    /**
     * @param refIndex
     */
    public void setIndex(int refIndex) {
        this.refIndex = refIndex;
    }

    /**
     * @return is optional
     */
    public boolean isOptional() {
        return optional;
    }
    
    /**
     * @param optional
     */
    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    /**
     * @return constraint
     */
    public Constraint getConstraint() {
        return constraint;
    }
    
    /**
     * @param constraint
     */
    public void setConstraint(Constraint constraint) {
        this.constraint = constraint;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.expression == null) ? 0 : this.expression.hashCode());
        result = prime * result + (this.positional ? 1231 : 1237);
        result = prime * result + this.refIndex;
        result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        Reference other = (Reference)obj;
        if (this.expression == null) {
            if (other.expression != null) return false;
        } else if (!this.expression.equals(other.expression)) return false;
        if (this.positional != other.positional) return false;
        if (this.refIndex != other.refIndex) return false;
        if (this.type == null) {
            if (other.type != null) return false;
        } else if (!this.type.equals(other.type)) return false;
        return true;
    }

    /** Accept the visitor. **/
    @Override
    public void acceptVisitor(LanguageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Reference clone() {
        Reference clone = new Reference(this.parser, this.id);

        if(getExpression() != null)
            clone.setExpression(getExpression().clone());
        if(this.type != null)
            clone.setType(this.type);
        clone.setPositional(isPositional());
        clone.setIndex(getIndex());
        clone.setConstraint(clone.getConstraint());
        
        return clone;
    }

}
/* JavaCC - OriginalChecksum=929474660e4f338ac928ae34c54e9019 (do not edit this line) */

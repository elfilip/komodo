/* Generated By:JJTree: Do not edit this line. AbstractSetCriteria.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=TeiidNodeFactory,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.query.sql.lang;

import org.teiid.query.parser.LanguageVisitor;
import org.teiid.query.parser.TeiidClientParser;
import org.teiid.query.sql.symbol.Expression;

/**
 *
 */
public abstract class AbstractSetCriteria extends Criteria implements PredicateCriteria {

    private Expression expression;

    /** Negation flag. Indicates whether the criteria expression contains a NOT. */
    private boolean negated = false;

    /**
     * @param p
     * @param id
     */
    public AbstractSetCriteria(TeiidClientParser p, int id) {
        super(p, id);
    }

    /**
     * @return the expression
     */
    public Expression getExpression() {
        return this.expression;
    }

    /**
     * @param expression the expression to set
     */
    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    /**
     * Returns whether this criteria is negated.
     * @return flag indicating whether this criteria contains a NOT
     */
    public boolean isNegated() {
        return negated;
    }
    
    /**
     * Sets the negation flag for this criteria.
     * @param negationFlag true if this criteria contains a NOT; false otherwise
     */
    public void setNegated(boolean negationFlag) {
        negated = negationFlag;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.expression == null) ? 0 : this.expression.hashCode());
        result = prime * result + (this.negated ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        AbstractSetCriteria other = (AbstractSetCriteria)obj;
        if (this.expression == null) {
            if (other.expression != null) return false;
        } else if (!this.expression.equals(other.expression)) return false;
        if (this.negated != other.negated) return false;
        return true;
    }

    /** Accept the visitor. **/
    @Override
    public void acceptVisitor(LanguageVisitor visitor) {
        visitor.visit(this);
    }
}
/* JavaCC - OriginalChecksum=b5f13c38d099fca361ab8d2d92744d6a (do not edit this line) */

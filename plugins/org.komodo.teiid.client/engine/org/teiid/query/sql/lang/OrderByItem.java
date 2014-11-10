/* Generated By:JJTree: Do not edit this line. OrderByItem.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.query.sql.lang;

import org.komodo.spi.query.sql.lang.IOrderByItem;
import org.komodo.spi.runtime.version.DefaultTeiidVersion.Version;
import org.teiid.language.SortSpecification;
import org.teiid.query.parser.LanguageVisitor;
import org.teiid.query.parser.TeiidNodeFactory.ASTNodes;
import org.teiid.query.parser.TeiidClientParser;
import org.teiid.query.sql.symbol.Constant;
import org.teiid.query.sql.symbol.Expression;
import org.teiid.query.sql.symbol.ExpressionSymbol;
import org.teiid.query.sql.symbol.Symbol;

/**
 *
 */
public class OrderByItem extends SimpleNode
    implements SortSpecification, IOrderByItem<Expression, LanguageVisitor> {

    private boolean ascending = true;

    private Expression symbol;

    private NullOrdering nullOrdering;

    private Integer expressionPosition; //set during resolving to the select clause position

    /**
     * @param p
     * @param id
     */
    public OrderByItem(TeiidClientParser p, int id) {
        super(p, id);
    }

    @Override
    public boolean isAscending() {
        return ascending;
    }

    /**
     * @param ascending
     */
    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    @Override
    public Expression getSymbol() {
        return symbol;
    }

    @Override
    public void setSymbol(Expression symbol) {
        if (isTeiidVersionOrGreater(Version.TEIID_8_6) && symbol != null
                && !(symbol instanceof Symbol) && !(symbol instanceof Constant)) {
            ExpressionSymbol ex = getTeiidParser().createASTNode(ASTNodes.EXPRESSION_SYMBOL);
            ex.setName("expr"); //$NON-NLS-1$
            ex.setExpression(symbol);
            symbol = ex;
        }

        this.symbol = symbol;
    }
    
    /**
     * @return ordering value
     */
    public NullOrdering getNullOrdering() {
        return nullOrdering;
    }
    
    /**
     * @param nullOrdering
     */
    public void setNullOrdering(NullOrdering nullOrdering) {
        this.nullOrdering = nullOrdering;
    }

    /**
     * @return expression position set during resolving
     */
    public int getExpressionPosition() {
        return expressionPosition == null?-1:expressionPosition;
    }

    /**
     * @param expressionPosition
     */
    public void setExpressionPosition(int expressionPosition) {
        this.expressionPosition = expressionPosition;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (this.ascending ? 1231 : 1237);
        result = prime * result + ((this.nullOrdering == null) ? 0 : this.nullOrdering.hashCode());
        result = prime * result + ((this.symbol == null) ? 0 : this.symbol.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        OrderByItem other = (OrderByItem)obj;
        if (this.ascending != other.ascending) return false;
        if (this.nullOrdering != other.nullOrdering) return false;
        if (this.symbol == null) {
            if (other.symbol != null) return false;
        } else if (!this.symbol.equals(other.symbol)) return false;
        return true;
    }

    /** Accept the visitor. **/
    @Override
    public void acceptVisitor(LanguageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public OrderByItem clone() {
        OrderByItem clone = new OrderByItem(this.parser, this.id);

        if(getSymbol() != null)
            clone.setSymbol(getSymbol().clone());
        if(getNullOrdering() != null)
            clone.setNullOrdering(getNullOrdering());
        clone.setAscending(isAscending());

        return clone;
    }

}
/* JavaCC - OriginalChecksum=8206369bce4a105220b34cfd64063716 (do not edit this line) */

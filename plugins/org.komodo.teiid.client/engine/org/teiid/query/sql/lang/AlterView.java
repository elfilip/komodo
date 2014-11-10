/* Generated By:JJTree: Do not edit this line. Alter.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.query.sql.lang;

import org.komodo.spi.query.sql.lang.IAlterView;
import org.teiid.query.parser.LanguageVisitor;
import org.teiid.query.parser.TeiidClientParser;
import org.teiid.query.sql.symbol.Expression;

/**
 *
 */
public class AlterView extends Alter<QueryCommand> implements IAlterView<Expression, LanguageVisitor> {

    /**
     * @param p
     * @param id
     */
    public AlterView(TeiidClientParser p, int id) {
        super(p, id);
    }

    @Override
    public int getType() {
        return TYPE_ALTER_VIEW;
    }

    /** Accept the visitor. **/
    @Override
    public void acceptVisitor(LanguageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public AlterView clone() {
        AlterView clone = new AlterView(this.parser, this.id);

        if(getDefinition() != null)
            clone.setDefinition(getDefinition().clone());
        if(getTarget() != null)
            clone.setTarget(getTarget().clone());
        if(getSourceHint() != null)
            clone.setSourceHint(getSourceHint());
        if(getOption() != null)
            clone.setOption(getOption().clone());

        copyMetadataState(clone);

        return clone;
    }

}
/* JavaCC - OriginalChecksum=4c2a7e700d4af2b1569d4947a1d82223 (do not edit this line) */

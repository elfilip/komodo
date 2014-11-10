/* Generated By:JJTree: Do not edit this line. ObjectColumn.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=TeiidNodeFactory,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.query.sql.lang;

import javax.script.CompiledScript;

import org.komodo.spi.query.sql.lang.IObjectColumn;
import org.teiid.query.parser.LanguageVisitor;
import org.teiid.query.parser.TeiidClientParser;
import org.teiid.query.sql.symbol.Expression;

/**
 *
 */
public class ObjectColumn extends ProjectedColumn implements IObjectColumn<LanguageVisitor> {

    private String path;

    private Expression defaultExpression;

    private CompiledScript compiledScript;

    /**
     * @param p
     * @param id
     */
    public ObjectColumn(TeiidClientParser p, int id) {
        super(p, id);
    }

    /**
     * @return the path
     */
    public String getPath() {
        return this.path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the defaultExpression
     */
    public Expression getDefaultExpression() {
        return this.defaultExpression;
    }

    /**
     * @param defaultExpression the defaultExpression to set
     */
    public void setDefaultExpression(Expression defaultExpression) {
        this.defaultExpression = defaultExpression;
    }

    /**
     * @return compiled script
     */
    public CompiledScript getCompiledScript() {
        return compiledScript;
    }
    
    /**
     * @param compiledScript
     */
    public void setCompiledScript(CompiledScript compiledScript) {
        this.compiledScript = compiledScript;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.defaultExpression == null) ? 0 : this.defaultExpression.hashCode());
        result = prime * result + ((this.path == null) ? 0 : this.path.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        ObjectColumn other = (ObjectColumn)obj;
        if (this.defaultExpression == null) {
            if (other.defaultExpression != null) return false;
        } else if (!this.defaultExpression.equals(other.defaultExpression)) return false;
        if (this.path == null) {
            if (other.path != null) return false;
        } else if (!this.path.equals(other.path)) return false;
        return true;
    }

    /** Accept the visitor. **/
    @Override
    public void acceptVisitor(LanguageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public ObjectColumn clone() {
        ObjectColumn clone = new ObjectColumn(this.parser, this.id);

        if(getPath() != null)
            clone.setPath(getPath());
        if(getDefaultExpression() != null)
            clone.setDefaultExpression(getDefaultExpression().clone());
        if(getType() != null)
            clone.setType(getType());
        if(getName() != null)
            clone.setName(getName());

        clone.setCompiledScript(getCompiledScript());

        return clone;
    }

}
/* JavaCC - OriginalChecksum=93bed62047b8d070d6aa7cdb43356933 (do not edit this line) */

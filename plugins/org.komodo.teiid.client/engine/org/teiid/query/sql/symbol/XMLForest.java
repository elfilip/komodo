/* Generated By:JJTree: Do not edit this line. XMLForest.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.query.sql.symbol;

import java.util.List;

import org.komodo.spi.query.sql.symbol.IXMLForest;
import org.teiid.core.types.DefaultDataTypeManager;
import org.teiid.query.parser.LanguageVisitor;
import org.teiid.query.parser.TeiidClientParser;
import org.teiid.query.sql.lang.SimpleNode;

/**
 *
 */
public class XMLForest extends SimpleNode implements Expression, IXMLForest<LanguageVisitor> {

    private List<DerivedColumn> args;

    private XMLNamespaces namespaces;

    /**
     * @param p
     * @param id
     */
    public XMLForest(TeiidClientParser p, int id) {
        super(p, id);
    }

    @Override
    public Class<?> getType() {
        return DefaultDataTypeManager.DefaultDataTypes.XML.getTypeClass();
    }

    /**
     * @return namespaces
     */
    public XMLNamespaces getNamespaces() {
        return namespaces;
    }
    
    /**
     * @param namespaces
     */
    public void setNamespaces(XMLNamespaces namespaces) {
        this.namespaces = namespaces;
    }
    
    /**
     * @return args
     */
    public List<DerivedColumn> getArgs() {
        return args;
    }

    /**
     * @param args
     */
    public void setArguments(List<DerivedColumn> args) {
        this.args = args;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.args == null) ? 0 : this.args.hashCode());
        result = prime * result + ((this.namespaces == null) ? 0 : this.namespaces.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        XMLForest other = (XMLForest)obj;
        if (this.args == null) {
            if (other.args != null) return false;
        } else if (!this.args.equals(other.args)) return false;
        if (this.namespaces == null) {
            if (other.namespaces != null) return false;
        } else if (!this.namespaces.equals(other.namespaces)) return false;
        return true;
    }

    /** Accept the visitor. **/
    @Override
    public void acceptVisitor(LanguageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public XMLForest clone() {
        XMLForest clone = new XMLForest(this.parser, this.id);

        if(getNamespaces() != null)
            clone.setNamespaces(getNamespaces().clone());
        if(getArgs() != null)
            clone.setArguments(cloneList(getArgs()));

        return clone;
    }

}
/* JavaCC - OriginalChecksum=f7a58421cd938bda6fd8786ca42a7b99 (do not edit this line) */

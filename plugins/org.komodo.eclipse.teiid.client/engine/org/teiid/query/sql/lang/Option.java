/* Generated By:JJTree: Do not edit this line. Option.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.query.sql.lang;

import java.util.ArrayList;
import java.util.List;

import org.komodo.spi.annotation.Since;
import org.komodo.spi.query.sql.lang.IOption;
import org.komodo.spi.runtime.version.ITeiidVersion;
import org.komodo.spi.runtime.version.TeiidVersion.Version;
import org.teiid.language.SQLConstants.Reserved;
import org.teiid.query.parser.LanguageVisitor;
import org.teiid.query.parser.TeiidParser;
import org.teiid.query.sql.visitor.SQLStringVisitor;

/**
 *
 */
public class Option extends SimpleNode implements IOption<LanguageVisitor> {

    /**
     * Make Dep token
     */
    public final static String MAKEDEP = Reserved.MAKEDEP; 

    /**
     * Make Not Dep token
     */
    public final static String MAKENOTDEP = Reserved.MAKENOTDEP;

    /**
     * Optional token
     */
    public final static String OPTIONAL = "optional"; //$NON-NLS-1$

    public static class MakeDep {
        private Integer max;
        private boolean join;
        private ITeiidVersion teiidVersion;
        
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (this.join ? 1231 : 1237);
            result = prime * result + ((this.max == null) ? 0 : this.max.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            MakeDep other = (MakeDep)obj;
            if (this.join != other.join)
                return false;
            if (this.max == null) {
                if (other.max != null)
                    return false;
            } else if (!this.max.equals(other.max))
                return false;
            return true;
        }

        public MakeDep(ITeiidVersion teiidVersion) {
            this.teiidVersion = teiidVersion;
        }
        
        @Override
        public String toString() {
            return new SQLStringVisitor(teiidVersion).appendMakeDepOptions(this).getSQLString();
        }

        public Integer getMax() {
            return max;
        }

        public void setMax(Integer max) {
            this.max = max;
        }
        
        public boolean isJoin() {
            return join;
        }
        
        public void setJoin(boolean join) {
            this.join = join;
        }
        
        public boolean isSimple() {
            return max == null && !join;
        }
    }

    private List<String> makeDependentGroups;

    @Since(Version.TEIID_8_5)
    private List<MakeDep> makeDependentOptions;

    private List<String> makeNotDependentGroups;

    private List<String> noCacheGroups;

    private boolean noCache;

    /**
     * @param p
     * @param id
     */
    public Option(TeiidParser p, int id) {
        super(p, id);
    }

    /**
     * Add group to make dependent
     * @param group Group to make dependent
     */
    public void addDependentGroup(String group) {
        addDependentGroup(group, new MakeDep(getTeiidVersion()));
    }
    
    public void addDependentGroup(String group, MakeDep makedep) {
        if (makedep == null) {
            return;
        }
        if(this.makeDependentGroups == null) {
            this.makeDependentGroups = new ArrayList<String>();
            this.makeDependentOptions = new ArrayList<MakeDep>();
        }
        this.makeDependentGroups.add(group);
        this.makeDependentOptions.add(makedep);
    }
    
    /** 
     * Get all groups to make dependent
     * @return List of String defining groups to be made dependent, may be null if no groups
     */
    @Override
    public List<String> getDependentGroups() {
        return this.makeDependentGroups;
    }

    public List<MakeDep> getMakeDepOptions() {
        return this.makeDependentOptions;
    }

    /**
     * Add group to make dependent
     * @param group Group to make dependent
     */
    public void addNotDependentGroup(String group) {
        if(this.makeNotDependentGroups == null) {
            this.makeNotDependentGroups = new ArrayList<String>();
        }
        this.makeNotDependentGroups.add(group);    
    }
    
    /** 
     * Get all groups to make dependent
     * @return List of String defining groups to be made dependent, may be null if no groups
     */
    @Override
    public List<String> getNotDependentGroups() {
        return this.makeNotDependentGroups;
    }
    
    /**
     * Add group that overrides the default behavior of Materialized View feautre
     * to route the query to the primary virtual group transformation instead of 
     * the Materialized View transformation.
     * @param group Group that overrides the default behavior of Materialized View
     */
    public void addNoCacheGroup(String group) {
        if(this.noCacheGroups == null) {
            this.noCacheGroups = new ArrayList<String>();
        }
        this.noCacheGroups.add(group);    
    }
    
    /** 
     * Get all groups that override the default behavior of Materialized View feautre
     * to route the query to the primary virtual group transformation instead of 
     * the Materialized View transformation.
     * @return List of String defining groups that overrides the default behavior of 
     * Materialized View, may be null if there are no groups
     */
    @Override
    public List<String> getNoCacheGroups() {
        return this.noCacheGroups;
    }
    
    @Override
    public boolean isNoCache() {
        return noCache;
    }

    /**
     * @param noCache
     */
    public void setNoCache(boolean noCache) {
        this.noCache = noCache;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.makeDependentGroups == null) ? 0 : this.makeDependentGroups.hashCode());
        result = prime * result + ((this.makeNotDependentGroups == null) ? 0 : this.makeNotDependentGroups.hashCode());
        result = prime * result + (this.noCache ? 1231 : 1237);
        result = prime * result + ((this.noCacheGroups == null) ? 0 : this.noCacheGroups.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        Option other = (Option)obj;
        if (this.makeDependentGroups == null) {
            if (other.makeDependentGroups != null) return false;
        } else if (!this.makeDependentGroups.equals(other.makeDependentGroups)) return false;
        if (this.makeNotDependentGroups == null) {
            if (other.makeNotDependentGroups != null) return false;
        } else if (!this.makeNotDependentGroups.equals(other.makeNotDependentGroups)) return false;
        if (this.noCache != other.noCache) return false;
        if (this.noCacheGroups == null) {
            if (other.noCacheGroups != null) return false;
        } else if (!this.noCacheGroups.equals(other.noCacheGroups)) return false;
        return true;
    }

    /** Accept the visitor. **/
    @Override
    public void acceptVisitor(LanguageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Option clone() {
        Option clone = new Option(this.parser, this.id);

        clone.setNoCache(isNoCache());

        if(this.makeDependentGroups != null) {
            clone.makeDependentGroups = new ArrayList<String>(this.makeDependentGroups);
            clone.makeDependentOptions = new ArrayList<MakeDep>(this.makeDependentOptions);
        }
            
        if(getNotDependentGroups() != null) {
            clone.makeNotDependentGroups = new ArrayList<String>(getNotDependentGroups());
        }
            
        if(getNoCacheGroups() != null) {
            clone.noCacheGroups = new ArrayList<String>(getNoCacheGroups());
        }

        return clone;
    }

}
/* JavaCC - OriginalChecksum=a564d0b56868fc308d004c702396106a (do not edit this line) */

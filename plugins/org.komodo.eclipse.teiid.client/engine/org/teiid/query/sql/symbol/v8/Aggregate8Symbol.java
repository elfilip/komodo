/* Generated By:JJTree: Do not edit this line. AggregateSymbol.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.teiid.query.sql.symbol.v8;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.komodo.spi.runtime.version.TeiidVersion.Version;
import org.teiid.core.types.DataTypeManagerService;
import org.teiid.core.types.DataTypeManagerService.DefaultDataTypes;
import org.teiid.query.parser.LanguageVisitor;
import org.teiid.query.parser.v8.Teiid8Parser;
import org.teiid.query.sql.lang.OrderBy;
import org.teiid.query.sql.symbol.AggregateSymbol;
import org.teiid.query.sql.symbol.Expression;
import org.teiid.query.sql.symbol.Function;

/**
 * From Teiid Version 8 onwards the AggregateSymbol extends Function
 */
public class Aggregate8Symbol extends Function implements AggregateSymbol {

    private boolean distinct;

    private OrderBy orderBy;

    private Expression condition;

    private Type aggregate;

    private boolean isWindowed;

    private static final Map<String, Type> nameMap = new TreeMap<String, Type>(String.CASE_INSENSITIVE_ORDER);

    static {
        for (Type t : Type.values()) {
            if (t == Type.USER_DEFINED) {
                continue;
            }
            nameMap.put(t.name(), t);
        }
    }

    private static final Class<?> COUNT_TYPE = DataTypeManagerService.DefaultDataTypes.INTEGER.getTypeClass();
    private static final Map<Class<?>, Class<?>> SUM_TYPES;
    private static final Map<Class<?>, Class<?>> AVG_TYPES;

    static {
        Class<?> byteClass = DataTypeManagerService.DefaultDataTypes.BYTE.getTypeClass();
        Class<?> longClass = DataTypeManagerService.DefaultDataTypes.LONG.getTypeClass();
        Class<?> shortClass = DataTypeManagerService.DefaultDataTypes.SHORT.getTypeClass();
        Class<?> integerClass = DataTypeManagerService.DefaultDataTypes.INTEGER.getTypeClass();
        Class<?> doubleClass = DataTypeManagerService.DefaultDataTypes.DOUBLE.getTypeClass();
        Class<?> bigDecimalClass = DataTypeManagerService.DefaultDataTypes.BIG_DECIMAL.getTypeClass();
        Class<?> bigIntegerClass = DataTypeManagerService.DefaultDataTypes.BIG_INTEGER.getTypeClass();
        Class<?> floatClass = DataTypeManagerService.DefaultDataTypes.FLOAT.getTypeClass();

        SUM_TYPES = new HashMap<Class<?>, Class<?>>();        
        SUM_TYPES.put(byteClass, longClass);
        SUM_TYPES.put(shortClass, longClass);
        SUM_TYPES.put(integerClass, longClass);
        SUM_TYPES.put(longClass, longClass);        
        SUM_TYPES.put(bigIntegerClass, bigIntegerClass);
        SUM_TYPES.put(floatClass, doubleClass);
        SUM_TYPES.put(doubleClass, doubleClass);        
        SUM_TYPES.put(bigDecimalClass, bigDecimalClass);
        
        AVG_TYPES = new HashMap<Class<?>, Class<?>>();
        DataTypeManagerService dataTypeManager = DataTypeManagerService.getInstance(Version.TEIID_8_0.get());
        if(dataTypeManager.isDecimalAsDouble()) {
            AVG_TYPES.put(byteClass, doubleClass);
            AVG_TYPES.put(shortClass, doubleClass);
            AVG_TYPES.put(integerClass, doubleClass);
            AVG_TYPES.put(longClass, doubleClass);
        } else {
            AVG_TYPES.put(byteClass, bigDecimalClass);
            AVG_TYPES.put(shortClass, bigDecimalClass);
            AVG_TYPES.put(integerClass, bigDecimalClass);
            AVG_TYPES.put(longClass, bigDecimalClass);
        }

        AVG_TYPES.put(bigIntegerClass, bigDecimalClass);
        AVG_TYPES.put(floatClass, doubleClass);
        AVG_TYPES.put(doubleClass, doubleClass);
        AVG_TYPES.put(bigDecimalClass, bigDecimalClass);
    }

    /**
     * @param p
     * @param id
     */
    public Aggregate8Symbol(Teiid8Parser p, int id) {
        super(p, id);
    }

    @Override
    public boolean isBoolean() {
        return this.aggregate == Type.EVERY 
                || this.aggregate == Type.SOME 
                || this.aggregate == Type.ANY;
    }

    @Override
    public boolean isEnhancedNumeric() {
        return this.aggregate == Type.STDDEV_POP 
        || this.aggregate == Type.STDDEV_SAMP
        || this.aggregate == Type.VAR_SAMP
        || this.aggregate == Type.VAR_POP;
    }

    private boolean isAnalytical() {
        switch (this.aggregate) {
        case RANK:
        case ROW_NUMBER:
        case DENSE_RANK:
            return true;
        default:
            return false;
        }
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        if (aggregate == null) {
            this.aggregate = nameMap.get(name);
            if (this.aggregate == null) {
                this.aggregate = Type.USER_DEFINED;
            }
        }
    }

    /**
    * Get the type of the symbol, which depends on the aggregate function and the
    * type of the contained expression
    * @return Type of the symbol
    */
    @Override
    public Class<?> getType() {
        switch (this.aggregate) {
            case COUNT:
                return COUNT_TYPE;
            case SUM:
                Class<?> expressionType = this.getArg(0).getType();
                return SUM_TYPES.get(expressionType);
            case AVG:
                expressionType = this.getArg(0).getType();
                return AVG_TYPES.get(expressionType);
            case ARRAY_AGG:
                if (this.getArg(0) == null) {
                    return null;
                }

                DefaultDataTypes dataType = getTeiidParser().getDataTypeService().getDataType(this.getArg(0).getType());
                return dataType.getTypeArrayClass();
            case TEXTAGG:
                return DataTypeManagerService.DefaultDataTypes.BLOB.getTypeClass();
            case USER_DEFINED:
                // TODO
                // Dont want to bring in function descriptors if one can help it
                // May need this for resolving

//                if (this.getFunctionDescriptor() == null) {
//                    return null;
//                }
//                return this.getFunctionDescriptor().getReturnType();
            case JSONARRAY_AGG:
                return DataTypeManagerService.DefaultDataTypes.CLOB.getTypeClass();
            case STRING_AGG:
                return super.getType();
            default:
                // ignore and carry on
        }

        if (isBoolean()) {
            return DataTypeManagerService.DefaultDataTypes.BOOLEAN.getTypeClass();
        }

        if (isEnhancedNumeric()) {
            return DataTypeManagerService.DefaultDataTypes.DOUBLE.getTypeClass();
        }

        if (isAnalytical()) {
            return DataTypeManagerService.DefaultDataTypes.INTEGER.getTypeClass();
        }

        if (this.getArgs().length == 0) {
            return null;
        }

        return this.getArg(0).getType();
    }

    @Override
    public void setAggregateFunction(String aggregateFunction) {
        setAggregateFunction(Type.valueOf(aggregateFunction));
    }

    @Override
    public void setAggregateFunction(Type aggregateFunction) {
        this.aggregate = aggregateFunction;
    }

    @Override
    public Type getAggregateFunction() {
        return this.aggregate;
    }

    /**
     * Get the distinct flag.  If true, aggregate symbol will remove duplicates during
     * computation.
     * @return True if duplicates should be removed during computation
     */
    @Override
    public boolean isDistinct() {
        return this.distinct;
    }

    @Override
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public OrderBy getOrderBy() {
        return orderBy;
    }

    @Override
    public void setOrderBy(OrderBy orderBy) {
        this.orderBy = orderBy;
    }

    @Override
    public Expression getCondition() {
        return condition;
    }
    
    @Override
    public void setCondition(Expression condition) {
        this.condition = condition;
    }

    @Override
    public boolean isWindowed() {
        return isWindowed;
    }

    @Override
    public void setWindowed(boolean isWindowed) {
        this.isWindowed = isWindowed;
    }

    @Override
    public String getCanonicalName() {
        // Only applicable to 7.7.0
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCanonicalName(String canonicalName) {
        // Only applicable to 7.7.0
        throw new UnsupportedOperationException();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.aggregate == null) ? 0 : this.aggregate.hashCode());
        result = prime * result + ((this.condition == null) ? 0 : this.condition.hashCode());
        result = prime * result + (this.distinct ? 1231 : 1237);
        result = prime * result + (this.isWindowed ? 1231 : 1237);
        result = prime * result + ((this.orderBy == null) ? 0 : this.orderBy.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        Aggregate8Symbol other = (Aggregate8Symbol)obj;
        if (this.aggregate != other.aggregate) return false;
        if (this.condition == null) {
            if (other.condition != null) return false;
        } else if (!this.condition.equals(other.condition)) return false;
        if (this.distinct != other.distinct) return false;
        if (this.isWindowed != other.isWindowed) return false;
        if (this.orderBy == null) {
            if (other.orderBy != null) return false;
        } else if (!this.orderBy.equals(other.orderBy)) return false;
        return true;
    }

    /** Accept the visitor. **/
    @Override
    public void acceptVisitor(LanguageVisitor visitor) {
        visitor.visit((AggregateSymbol)this);
    }

    @Override
    public Aggregate8Symbol clone() {
        Aggregate8Symbol clone = new Aggregate8Symbol((Teiid8Parser) this.parser, this.id);

        if(getAggregateFunction() != null)
            clone.setAggregateFunction(getAggregateFunction());
        if(getAggregateFunction() != null)
            clone.setAggregateFunction(getAggregateFunction());
        clone.setDistinct(isDistinct());
        if(getOrderBy() != null)
            clone.setOrderBy(getOrderBy().clone());
        if(getCondition() != null)
            clone.setCondition(getCondition().clone());
        clone.setWindowed(isWindowed());
        if(getName() != null)
            clone.setName(getName());
        if(getArgs() != null) {
            Expression[] cloned = new Expression[getArgs().length];
            for (int i = 0; i < getArgs().length; ++i) {
                cloned[i] = getArgs()[i].clone();
            }
            clone.setArgs(cloned);
        }
        if(super.getType() != null)
            clone.setType(super.getType());

        return clone;
    }

    @Override
    public Expression getExpression() {
        // No longer supported in 8.0.0+
        throw new UnsupportedOperationException();
    }

    @Override
    public void setExpression(Expression expression) {
        // No longer supported in 8.0.0+
        throw new UnsupportedOperationException();
    }
}
/* JavaCC - OriginalChecksum=7efdc98eb15b01c236003d9dc5fca445 (do not edit this line) */

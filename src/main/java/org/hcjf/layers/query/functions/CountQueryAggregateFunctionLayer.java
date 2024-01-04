package org.hcjf.layers.query.functions;

import org.hcjf.errors.HCJFRuntimeException;
import org.hcjf.layers.query.Enlarged;
import org.hcjf.layers.query.JoinableMap;
import org.hcjf.utils.Strings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author javaito
 */
public class CountQueryAggregateFunctionLayer extends BaseQueryAggregateFunctionLayer {

    private static final String NAME = "count";

    public CountQueryAggregateFunctionLayer() {
        super(NAME);
    }

    @Override
    public Collection evaluate(String alias, Collection resultSet, Object... parameters) {
        Collection result = resultSet;
        if(parameters.length == 0 || parameters[0].equals(Strings.ALL)) {
            Collection<JoinableMap> newResultSet = new ArrayList<>();
            JoinableMap size = new JoinableMap(new HashMap<>(), alias);
            size.put(alias, resultSet.size());
            newResultSet.add(size);
            result = newResultSet;
        } else {
            try {
                Object value;
                Integer countValue;
                for (Object row : resultSet) {
                    value = resolveValue(row, parameters[0]);
                    if(value instanceof Collection) {
                        countValue = ((Collection)value).size();
                    } else {
                        countValue = 1;
                    }
                    ((Enlarged)row).put(alias, countValue);
                }
            } catch (Exception ex){
                throw new HCJFRuntimeException("Count aggregate function fail", ex);
            }
        }
        return result;
    }
}

package org.hcjf.layers.query.evaluators;

import org.hcjf.layers.query.Queryable;

/**
 * Compare two object and return true if the objects are distinct and false in other ways.
 * @author javaito
 *
 */
public class Distinct extends Equals {

    public Distinct(Object leftValue, Object rightValue) {
        super(leftValue, rightValue);
    }

    /**
     * Evaluate if the evaluator's value and the object's value in the specified field of
     * the parameter instance are distinct.
     * This method support any kind of object like field value and parameter value too.
     * @param object Instance to obtain the field value.
     * @param dataSource Data source
     * @param consumer Data source consumer
     * @return True if the two values are distinct and false in other ways
     * @throws IllegalArgumentException If is impossible to get value from instance
     * with introspection.
     */
    @Override
    public boolean evaluate(Object object, Queryable.DataSource dataSource, Queryable.Consumer consumer) {
        return !super.evaluate(object, dataSource, consumer);
    }

}

package org.hcjf.layers.query.evaluators;

import org.hcjf.layers.query.Queryable;
import org.hcjf.layers.query.model.QueryParameter;

/**
 *
 * @author javaito
 */
public class And extends EvaluatorCollection implements Evaluator {

    public And(EvaluatorCollection parent) {
        super(parent);
    }

    /**
     * Makes a and concatenation with all the inner evaluators
     * @param object Object of the data collection.
     * @param dataSource Data source.
     * @param consumer Consumer.
     * @return Returns the result of the concatenation.
     */
    @Override
    public boolean evaluate(Object object, Queryable.DataSource dataSource, Queryable.Consumer consumer) {
        boolean result = true;

        for(Evaluator evaluator : getEvaluators()) {
            if (evaluator instanceof BooleanEvaluator &&
                    ((BooleanEvaluator) evaluator).getValue() instanceof QueryParameter &&
                    ((QueryParameter)((BooleanEvaluator) evaluator).getValue()).isUnderlying()) {
                continue;
            }
            result &= evaluator.evaluate(object, dataSource, consumer);
            if(!result) {
                break;
            }
        }

        return result;
    }
}

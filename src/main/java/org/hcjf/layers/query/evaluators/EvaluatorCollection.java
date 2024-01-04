package org.hcjf.layers.query.evaluators;

import org.hcjf.layers.query.model.QueryField;
import org.hcjf.layers.query.model.QueryFunction;
import org.hcjf.layers.query.model.QueryParameter;
import org.hcjf.layers.query.model.QueryResource;
import org.hcjf.log.Log;
import org.hcjf.properties.LayerSystemProperties;
import org.hcjf.properties.SystemProperties;

import java.util.*;

/**
 * Collection of evaluator components.
 * @author javaito
 */
public abstract class EvaluatorCollection {

    protected final Set<Evaluator> evaluators;
    private final EvaluatorCollection parent;

    public EvaluatorCollection() {
        this(null);
    }

    public EvaluatorCollection(EvaluatorCollection parent) {
        this.evaluators = new LinkedHashSet<>();
        this.parent = parent;
    }

    /**
     * Return the unmodifiable set with evaluators.
     * @return Evaluators.
     */
    public final Set<Evaluator> getEvaluators() {
        return Collections.unmodifiableSet(evaluators);
    }

    /**
     * Returns false if the collection is empty or all the elements are TrueEvaluator. It is used on reduced queries
     * to check if there are still some evaluators to eval.
     * @return true if the collection is not empty and there is at least one evaluator not instance of TrueEvaluator.
     */
    public boolean hasEvaluators() {
        boolean result = false;
        for (Evaluator evaluator : getEvaluators()) {
            if (evaluator instanceof EvaluatorCollection) {
                result = ((EvaluatorCollection)evaluator).hasEvaluators();
                if(result) {
                    break;
                }

            } else if (!(evaluator instanceof TrueEvaluator)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Return the parent of the evaluator collection, the parent is other
     * instance of evaluator collection.
     * @return Parent of the collection, could be null.
     */
    public final EvaluatorCollection up() {
        EvaluatorCollection result = parent;
        if(result == null) {
            result = this;
        }
        return result;
    }

    /**
     * This method remove the evaluator into the collection.
     * @param evaluator Evaluator to remove.
     */
    public void removeEvaluator(Evaluator evaluator) {
        evaluators.remove(evaluator);
    }

    /**
     * Add an instance of the evaluator object that evaluate if some instance of the
     * data collection must be in the result add or not.
     * @param evaluator FieldEvaluator instance.
     * @return Return the same instance of this class.
     * @throws IllegalArgumentException If the instance of the evaluator is null.
     */
    public final EvaluatorCollection addEvaluator(Evaluator evaluator) {
        if(evaluator == null) {
            throw new IllegalArgumentException("Null evaluator");
        }

        if(!evaluators.contains(evaluator)) {
            if(onAddEvaluator(evaluator)) {
                evaluators.add(checkEvaluator(evaluator));
            }
        } else {
            Log.w(SystemProperties.get(LayerSystemProperties.Query.LOG_TAG),
                    "Duplicate evaluator: $s", evaluator);
        }
        return this;
    }

    /**
     * This method is called when some evaluator is added to the collection.
     * @param evaluator Evaluator added.
     * @return True if the evaluator must be added into the evaluator collection.
     */
    protected boolean onAddEvaluator(Evaluator evaluator) {
        return true;
    }

    private QueryParameter checkQueryParameter(QueryParameter queryParameter) {
        if(queryParameter instanceof QueryField) {
            QueryField queryField = (QueryField) queryParameter;
            QueryResource resource = queryField.getResource();
        } else if(queryParameter instanceof QueryFunction) {
            QueryFunction function = (QueryFunction) queryParameter;
            for(Object functionParameter : function.getParameters()) {
                if(functionParameter instanceof QueryParameter) {
                    checkQueryParameter((QueryParameter) functionParameter);
                }
            }
        }
        return queryParameter;
    }

    protected Evaluator checkEvaluator(Evaluator evaluator) {
        if(evaluator instanceof FieldEvaluator) {
            FieldEvaluator fieldEvaluator = (FieldEvaluator) evaluator;
            if(fieldEvaluator.getLeftValue() instanceof QueryParameter) {
                checkQueryParameter((QueryParameter) fieldEvaluator.getLeftValue());
            }
            if(fieldEvaluator.getRightValue() instanceof QueryParameter) {
                checkQueryParameter((QueryParameter) fieldEvaluator.getRightValue());
            }
        }
        return evaluator;
    }

    /**
     * Return the collection of evaluators that corresponds to provided fieldName and types
     * @param fieldName Field name.
     * @param evaluatorType Evaluator types.
     * @return collection of evaluators
     */
    public Collection<Evaluator> getFieldEvaluators(String fieldName, Class<? extends FieldEvaluator>... evaluatorType) {
        Collection<Evaluator> results = new ArrayList<>();
        for (Evaluator evaluator : getEvaluators()) {
            if (evaluator instanceof EvaluatorCollection) {
                results.addAll( ((EvaluatorCollection)evaluator).getFieldEvaluators(fieldName, evaluatorType));

            } else if (evaluator instanceof FieldEvaluator) {
                FieldEvaluator fieldEvaluator = (FieldEvaluator) evaluator;
                if(fieldEvaluator.containsReference(fieldName)) {
                    if(evaluatorType.length == 0) {
                        results.add(fieldEvaluator);
                    } else {
                        for(int i = 0; i < evaluatorType.length; i++) {
                            if(fieldEvaluator.getClass().isAssignableFrom(evaluatorType[i])) {
                                results.add(fieldEvaluator);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return results;
    }

    /**
     * Add a particular evaluator that implements the true or false evaluation.
     * @param value Object to obtain the boolean value.
     * @return Return the same instance of this class.
     */
    public final EvaluatorCollection addBoolean(Object value) {
        return addEvaluator(new BooleanEvaluator(value));
    }

    /**
     * Add a particular evaluator that implements 'distinct' method.
     * @param leftValue Left value of the operation.
     * @param rightValue Right value of the operation.
     * @return Return the same instance of this class.
     */
    public final EvaluatorCollection distinct(Object leftValue, Object rightValue) {
        return addEvaluator(new Distinct(leftValue, rightValue));
    }

    /**
     * Add a particular evaluator that implements 'equals' method.
     * @param leftValue Left value of the operation.
     * @param rightValue Right value of the operation.
     * @return Return the same instance of this class.
     */
    public final EvaluatorCollection equals(Object leftValue, Object rightValue) {
        return addEvaluator(new Equals(leftValue, rightValue));
    }

    /**
     * Add a particular evaluator that implements 'greater than' method.
     * @param leftValue Left value of the operation.
     * @param rightValue Right value of the operation.
     * @return Return the same instance of this class.
     */
    public final EvaluatorCollection greaterThan(Object leftValue, Object rightValue) {
        return addEvaluator(new GreaterThan(leftValue, rightValue));
    }

    /**
     * Add a particular evaluator that implements 'greater than or equals' method.
     * @param leftValue Left value of the operation.
     * @param rightValue Right value of the operation.
     * @return Return the same instance of this class.
     */
    public final EvaluatorCollection greaterThanOrEquals(Object leftValue, Object rightValue) {
        return addEvaluator(new GreaterThanOrEqual(leftValue, rightValue));
    }

    /**
     * Add a particular evaluator that implements 'in' method.
     * @param leftValue Left value of the operation.
     * @param rightValue Right value of the operation.
     * @return Return the same instance of this class.
     */
    public final EvaluatorCollection in(Object leftValue, Object rightValue) {
        return addEvaluator(new In(leftValue, rightValue));
    }

    /**
     * Add a particular evaluator that implements 'not in' method.
     * @param leftValue Left value of the operation.
     * @param rightValue Right value of the operation.
     * @return Return the same instance of this class.
     */
    public final EvaluatorCollection notIn(Object leftValue, Object rightValue) {
        return addEvaluator(new NotIn(leftValue, rightValue));
    }

    /**
     * Add a particular evaluator that implements 'smaller than' method.
     * @param leftValue Left value of the operation.
     * @param rightValue Right value of the operation.
     * @return Return the same instance of this class.
     */
    public final EvaluatorCollection smallerThan(Object leftValue, Object rightValue) {
        return addEvaluator(new SmallerThan(leftValue, rightValue));
    }

    /**
     * Add a particular evaluator that implements 'smaller than or equals' method.
     * @param leftValue Left value of the operation.
     * @param rightValue Right value of the operation.
     * @return Return the same instance of this class.
     */
    public final EvaluatorCollection smallerThanOrEqual(Object leftValue, Object rightValue) {
        return addEvaluator(new SmallerThanOrEqual(leftValue, rightValue));
    }

    /**
     * Add a particular evaluator that implements 'like' method.
     * @param leftValue Left value of the operation.
     * @param rightValue Right value of the operation.
     * @return Return the same instance of this class.
     */
    public final EvaluatorCollection like(Object leftValue, Object rightValue) {
        return addEvaluator(new Like(leftValue, rightValue));
    }

    /**
     * Add a group evaluator with 'or' function, by default return evaluate with false.
     * @return Return the instance of the new evaluator collection (or instance);
     */
    public final EvaluatorCollection or() {
        Or or = new Or(this);
        addEvaluator(or);
        return or;
    }

    /**
     * Add a group evaluator with 'and' function, by default return evaluate with false.
     * @return Return the instance of the new evaluator collection (and instance);
     */
    public final EvaluatorCollection and() {
        And and = new And(this);
        addEvaluator(and);
        return and;
    }
}

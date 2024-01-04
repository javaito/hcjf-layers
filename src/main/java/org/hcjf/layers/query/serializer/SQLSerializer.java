package org.hcjf.layers.query.serializer;

import org.hcjf.layers.Layer;
import org.hcjf.layers.query.Join;
import org.hcjf.layers.query.Query;
import org.hcjf.layers.query.Queryable;
import org.hcjf.layers.query.evaluators.*;
import org.hcjf.layers.query.model.*;
import org.hcjf.properties.LayerSystemProperties;
import org.hcjf.properties.SystemProperties;
import org.hcjf.utils.JsonUtils;
import org.hcjf.utils.Strings;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class SQLSerializer extends Layer implements QuerySerializer {

    private static final String NAME = "SQL";

    private static final class QueryResourcePatterns {

        private static final class QueryDynamicResource {
            private static final String TO_STRING_PATTERN = "(%s) as %s";
            private static final String TO_STRING_WITH_PATH_PATTERN = "(%s).%s as %s";
        }

        private static final class TextResource {
            private static final String TO_STRING_PATTERN = "'%s' as %s";
        }

    }

    @Override
    public String getImplName() {
        return NAME;
    }

    /**
     * This method creates query expression from a query instance.
     *
     * @param query Query instance.
     * @return Query expression.
     */
    @Override
    public String serialize(Query query){
        Strings.Builder resultBuilder = new Strings.Builder();

        //Print environment
        if(query.getEnvironment() != null && query.getEnvironment().size() > 0) {
            resultBuilder.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.ENVIRONMENT));
            resultBuilder.append(Strings.WHITE_SPACE);
            resultBuilder.append(Strings.RICH_TEXT_SEPARATOR);
            String json = JsonUtils.toJsonTree(query.getEnvironment()).toString();
            json = json.replace("'", "\\'");
            resultBuilder.append(json);
            resultBuilder.append(Strings.RICH_TEXT_SEPARATOR);
            resultBuilder.append(Strings.WHITE_SPACE);
        }

        //Print select
        resultBuilder.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.SELECT));
        resultBuilder.append(Strings.WHITE_SPACE);
        if (query.returnAll()) {
            resultBuilder.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.RETURN_ALL));
            SystemProperties.get(LayerSystemProperties.Query.ReservedWord.ARGUMENT_SEPARATOR);
            resultBuilder.append(Strings.WHITE_SPACE);
        }
        for (QueryReturnParameter field : query.getReturnParameters()) {
            resultBuilder = toStringQueryReturnValue(resultBuilder, field);
        }
        resultBuilder.cleanBuffer();

        //Print from
        resultBuilder.append(Strings.WHITE_SPACE);
        resultBuilder.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.FROM));
        resultBuilder.append(Strings.WHITE_SPACE);
        toStringQueryResource(resultBuilder, query.getResource());
        resultBuilder.append(Strings.WHITE_SPACE);

        //Print joins
        for (Join join : query.getJoins()) {
            if (!(join.getType() == Join.JoinType.JOIN)) {
                resultBuilder.append(join.getType());
                resultBuilder.append(Strings.WHITE_SPACE);
            }
            resultBuilder.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.JOIN)).append(Strings.WHITE_SPACE);
            resultBuilder.append(join.getResource().toString()).append(Strings.WHITE_SPACE);
            resultBuilder.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.ON)).append(Strings.WHITE_SPACE);
            if (join.getEvaluators().size() > 0) {
                toStringEvaluatorCollection(resultBuilder, join);
            }
        }

        // Print conditional body
        if (query.getEvaluators().size() > 0) {
            resultBuilder.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.WHERE)).append(Strings.WHITE_SPACE);
            toStringEvaluatorCollection(resultBuilder, query);
        }

        // Print group sentences
        if (query.getGroupParameters().size() > 0) {
            if(query.isDisjoint()) {
                resultBuilder.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.DISJOINT_BY)).append(Strings.WHITE_SPACE);
            } else {
                resultBuilder.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.GROUP_BY)).append(Strings.WHITE_SPACE);
            }
            for (QueryReturnParameter groupParameter : query.getGroupParameters()) {
                resultBuilder.append(groupParameter, SystemProperties.get(LayerSystemProperties.Query.ReservedWord.ARGUMENT_SEPARATOR));
            }
            resultBuilder.cleanBuffer();
            resultBuilder.append(Strings.WHITE_SPACE);
        }

        // Print order sentences
        if (query.getOrderParameters().size() > 0) {
            resultBuilder.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.ORDER_BY)).append(Strings.WHITE_SPACE);
            for (QueryOrderParameter orderField : query.getOrderParameters()) {
                resultBuilder.append(orderField);
                if (orderField.isDesc()) {
                    resultBuilder.append(Strings.WHITE_SPACE).append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.DESC));
                }
                resultBuilder.append(Strings.EMPTY_STRING, SystemProperties.get(LayerSystemProperties.Query.ReservedWord.ARGUMENT_SEPARATOR));
            }
            resultBuilder.cleanBuffer();
        }

        if (query.getStart() != null) {
            resultBuilder.append(Strings.WHITE_SPACE).append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.START));
            resultBuilder.append(Strings.WHITE_SPACE).append(query.getStart());
        }

        if (query.getUnderlyingStart() != null) {
            if(query.getStart() == null) {
                resultBuilder.append(Strings.WHITE_SPACE).append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.START)).append(Strings.WHITE_SPACE);
            }
            resultBuilder.append(Strings.ARGUMENT_SEPARATOR).append(query.getUnderlyingStart());
        }

        if (query.getLimit() != null) {
            resultBuilder.append(Strings.WHITE_SPACE).append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.LIMIT));
            resultBuilder.append(Strings.WHITE_SPACE).append(query.getLimit());
        }

        if (query.getUnderlyingLimit() != null) {
            if(query.getLimit() == null) {
                resultBuilder.append(Strings.WHITE_SPACE).append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.LIMIT)).append(Strings.WHITE_SPACE);
            }
            resultBuilder.append(Strings.ARGUMENT_SEPARATOR).append(query.getUnderlyingLimit());
        }

        // Print underlying body
        if(query.getUnderlyingFunctions() != null) {
            for (String resource : query.getUnderlyingFunctions().keySet()) {
                List<QueryReturnFunction> functions = query.getUnderlyingFunctions().get(resource);
                resultBuilder.append(Strings.WHITE_SPACE);
                resultBuilder.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.UNDERLYING));
                for (QueryReturnFunction function : functions) {
                    resultBuilder.append(Strings.WHITE_SPACE);
                    resultBuilder = toStringQueryReturnValue(resultBuilder, function);
                }
                resultBuilder.cleanBuffer();
                resultBuilder.append(Strings.WHITE_SPACE);
                resultBuilder.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.SRC));
                resultBuilder.append(Strings.WHITE_SPACE);
                resultBuilder.append(resource);
            }
        }

        // Print unions
        for(Queryable queryable : query.getUnions()) {
            resultBuilder.append(Strings.WHITE_SPACE);
            resultBuilder.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.UNION));
            resultBuilder.append(Strings.WHITE_SPACE);
            resultBuilder.append(queryable.toString());
        }

        return resultBuilder.toString();
    }

    /**
     * Creates string representation of query return parameter and add this representation into builder.
     * @param builder String builder instance.
     * @return  Returns the same builder that receive.
     */
    private Strings.Builder toStringQueryReturnValue(Strings.Builder builder, QueryReturnParameter queryReturnParameter) {
        if(queryReturnParameter instanceof QueryReturnLiteral) {
            Object value = ((QueryReturnLiteral) queryReturnParameter).getValue();
            if (value instanceof String) {
                builder.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.STRING_DELIMITER));
                builder.append(value);
                builder.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.STRING_DELIMITER));
            } else if (value instanceof Date) {
                builder.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.STRING_DELIMITER));
                builder.append(SystemProperties.getDateFormat(LayerSystemProperties.Query.DATE_FORMAT).format((Date) value));
                builder.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.STRING_DELIMITER));
            } else {
                builder.append(Objects.toString(value));
            }
        } else if(queryReturnParameter instanceof QueryReturnUnprocessedValue) {
            BaseEvaluator.UnprocessedValue unprocessedValue = ((QueryReturnUnprocessedValue) queryReturnParameter).getUnprocessedValue();
            if (unprocessedValue instanceof BaseEvaluator.QueryValue) {
                builder.append(Strings.START_GROUP);
                builder.append(((BaseEvaluator.QueryValue) unprocessedValue).getQuery().toString());
                builder.append(Strings.END_GROUP);
            } else {
                builder.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.REPLACEABLE_VALUE));
            }
//        } else if(queryReturnParameter instanceof QueryReturnFunction) {
//            QueryReturnFunction queryReturnFunction = (QueryReturnFunction) queryReturnParameter;
//            builder.append(queryReturnFunction.getFunctionName())
//            builder.append(Strings.START_GROUP);
//            for()
//            builder.append(((BaseEvaluator.QueryValue) unprocessedValue).getQuery().toString());
//            builder.append(Strings.END_GROUP);
        } else {
            builder.append(queryReturnParameter);
        }

        if (queryReturnParameter.getAlias() != null) {
            builder.append(Strings.WHITE_SPACE).append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.AS));
            builder.append(Strings.WHITE_SPACE).append(queryReturnParameter.getAlias());
        }
        builder.append(Strings.EMPTY_STRING, SystemProperties.get(LayerSystemProperties.Query.ReservedWord.ARGUMENT_SEPARATOR));
        return builder;
    }

    /**
     * Creates a string representation of query resource and append this representation into result object.
     * @param result Buffer with the current result.
     * @param resource Query resource object.
     */
    private void toStringQueryResource(Strings.Builder result, QueryResource resource) {
        if(resource instanceof QueryDynamicResource) {
            QueryDynamicResource queryDynamicResource = (QueryDynamicResource) resource;
            result.append(queryDynamicResource.getPath() == null ?
                    String.format(QueryResourcePatterns.QueryDynamicResource.TO_STRING_PATTERN,
                            queryDynamicResource.getQuery().toString(), queryDynamicResource.getResourceName()) :
                    String.format(QueryResourcePatterns.QueryDynamicResource.TO_STRING_WITH_PATH_PATTERN,
                            queryDynamicResource.getQuery().toString(), queryDynamicResource.getPath(), queryDynamicResource.getResourceName()));
        } else if(resource instanceof QueryJsonResource) {
            QueryJsonResource queryJsonResource = (QueryJsonResource) resource;
            result.append(String.format(QueryResourcePatterns.TextResource.TO_STRING_PATTERN,
                    JsonUtils.toJsonTree(queryJsonResource.getResourceValues()).toString(), queryJsonResource.getResourceName()));
        } else if(resource instanceof QueryTextResource) {
            QueryTextResource queryTextResource = (QueryTextResource) resource;
            result.append(String.format(QueryResourcePatterns.TextResource.TO_STRING_PATTERN,
                    JsonUtils.toJsonTree(queryTextResource.getText()).toString(), queryTextResource.getResourceName()));
        } else {
            result.append(resource.getResourceName());
        }
    }

    /**
     * Creates a string representation of evaluator collection.
     * @param result Buffer with the current result.
     * @param collection Collection in order to create the string representation.
     */
    private void toStringEvaluatorCollection(Strings.Builder result, EvaluatorCollection collection) {
        String separator = Strings.EMPTY_STRING;
        String separatorValue = collection instanceof Or ?
                SystemProperties.get(LayerSystemProperties.Query.ReservedWord.OR) :
                SystemProperties.get(LayerSystemProperties.Query.ReservedWord.AND);
        for(Evaluator evaluator : collection.getEvaluators()) {
            if(evaluator instanceof Or) {
                result.append(separator);
                result.append(Strings.WHITE_SPACE);
                if(((Or)evaluator).getEvaluators().size() == 1) {
                    toStringEvaluatorCollection(result, (Or) evaluator);
                } else {
                    result.append(Strings.START_GROUP);
                    toStringEvaluatorCollection(result, (Or) evaluator);
                    result.append(Strings.END_GROUP);
                }
                result.append(Strings.WHITE_SPACE);
            } else if(evaluator instanceof And) {
                result.append(separator);
                result.append(Strings.WHITE_SPACE);
                if (collection instanceof Query) {
                    toStringEvaluatorCollection(result, (And) evaluator);
                } else {
                    if (((And) evaluator).getEvaluators().size() == 1) {
                        toStringEvaluatorCollection(result, (And) evaluator);
                    } else {
                        result.append(Strings.START_GROUP);
                        toStringEvaluatorCollection(result, (And) evaluator);
                        result.append(Strings.END_GROUP);
                    }
                }
                result.append(Strings.WHITE_SPACE);
            } else if(evaluator instanceof BooleanEvaluator) {
                result.append(separator);
                BooleanEvaluator booleanEvaluator = (BooleanEvaluator) evaluator;
                if(booleanEvaluator.isTrueForced()) {
                    result.append(Boolean.TRUE.toString());
                } else {
                    result = toStringFieldEvaluatorValue(booleanEvaluator.getValue(), booleanEvaluator.getClass(), result);
                }
                result.append(Strings.WHITE_SPACE);
            } else if(evaluator instanceof FieldEvaluator) {
                result.append(separator);
                FieldEvaluator fieldEvaluator = (FieldEvaluator) evaluator;
                if(fieldEvaluator.isTrueForced()) {
                    result.append(Boolean.TRUE.toString());
                } else {
                    if (fieldEvaluator.getLeftValue() == null) {
                        result.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.NULL));
                    } else {
                        result = toStringFieldEvaluatorValue(fieldEvaluator.getLeftValue(), fieldEvaluator.getLeftValue().getClass(), result);
                    }
                    result.append(Strings.WHITE_SPACE);
                    if (fieldEvaluator instanceof Distinct) {
                        result.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.DISTINCT)).append(Strings.WHITE_SPACE);
                    } else if (fieldEvaluator instanceof Equals) {
                        result.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.EQUALS)).append(Strings.WHITE_SPACE);
                    } else if (fieldEvaluator instanceof GreaterThanOrEqual) {
                        result.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.GREATER_THAN_OR_EQUALS)).append(Strings.WHITE_SPACE);
                    } else if (fieldEvaluator instanceof GreaterThan) {
                        result.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.GREATER_THAN)).append(Strings.WHITE_SPACE);
                    } else if (fieldEvaluator instanceof NotIn) {
                        result.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.NOT_IN)).append(Strings.WHITE_SPACE);
                    } else if (fieldEvaluator instanceof In) {
                        result.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.IN)).append(Strings.WHITE_SPACE);
                    } else if (fieldEvaluator instanceof Like) {
                        result.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.LIKE)).append(Strings.WHITE_SPACE);
                    } else if (fieldEvaluator instanceof SmallerThanOrEqual) {
                        result.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.SMALLER_THAN_OR_EQUALS)).append(Strings.WHITE_SPACE);
                    } else if (fieldEvaluator instanceof SmallerThan) {
                        result.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.SMALLER_THAN)).append(Strings.WHITE_SPACE);
                    }
                    if (fieldEvaluator.getRightValue() == null) {
                        result.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.NULL));
                    } else {
                        result = toStringFieldEvaluatorValue(fieldEvaluator.getRightValue(), fieldEvaluator.getRightValue().getClass(), result);
                    }
                }
                result.append(Strings.WHITE_SPACE);
            }
            separator = separatorValue + Strings.WHITE_SPACE;
        }
    }

    /**
     * Creates the string representation of the field evaluator.
     * @param value Object to create the string representation.
     * @param type Object type.
     * @param result Buffer with the current result.
     * @return String representation of the field evaluator.
     */
    private static Strings.Builder toStringFieldEvaluatorValue(Object value, Class type, Strings.Builder result) {
        if(FieldEvaluator.ReplaceableValue.class.isAssignableFrom(type)) {
            result.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.REPLACEABLE_VALUE));
        } else if(FieldEvaluator.QueryValue.class.isAssignableFrom(type)) {
            result.append(Strings.START_GROUP);
            result.append(((FieldEvaluator.QueryValue)value).getQuery().toString());
            result.append(Strings.END_GROUP);
        } else if(String.class.isAssignableFrom(type)) {
            result.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.STRING_DELIMITER));
            result.append(value);
            result.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.STRING_DELIMITER));
        } else if(Date.class.isAssignableFrom(type)) {
            result.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.STRING_DELIMITER));
            result.append(SystemProperties.getDateFormat(LayerSystemProperties.Query.DATE_FORMAT).format((Date)value));
            result.append(SystemProperties.get(LayerSystemProperties.Query.ReservedWord.STRING_DELIMITER));
        } else if(Collection.class.isAssignableFrom(type)) {
            result.append(Strings.START_GROUP);
            String separator = Strings.EMPTY_STRING;
            for(Object object : (Collection)value) {
                if(object != null) {
                    result.append(separator);
                    result = toStringFieldEvaluatorValue(object, object.getClass(), result);
                    separator = SystemProperties.get(LayerSystemProperties.Query.ReservedWord.ARGUMENT_SEPARATOR);
                }
            }
            result.append(Strings.END_GROUP);
        } else {
            result.append(value.toString());
        }
        return result;
    }
}

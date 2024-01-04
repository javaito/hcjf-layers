package org.hcjf.properties;

import org.hcjf.layers.locale.DefaultLocaleLayer;
import org.nd4j.shade.jackson.databind.annotation.JsonAppend;

import java.util.*;

/**
 * This class overrides the system properties default implementation adding
 * some default values and properties definitions for the service-oriented platforms
 * works.
 * @author javaito
 */
public final class LayerSystemProperties extends Properties implements DefaultProperties {

    public static final class Locale {
        public static final String LOG_TAG = "hcjf.locale.log.tag";
        public static final String DEFAULT_LOCALE = "hcjf.default.locale";
        public static final String DEFAULT_LOCALE_LAYER_IMPLEMENTATION_CLASS_NAME = "hcjf.default.locale.layer.implementation.class.name";
        public static final String DEFAULT_LOCALE_LAYER_IMPLEMENTATION_NAME = "hcjf.default.locale.layer.implementation.name";
    }

    public static final class CodeEvaluator {

        public static final class Java {
            public static final String IMPL_NAME = "hcjf.code.evaluator.java.impl.name";
            public static final String J_SHELL_POOL_SIZE = "hcjf.code.evaluator.java.j.shell.pool.size";
            public static final String J_SHELL_INSTANCE_TIMEOUT = "hcjf.code.evaluator.java.j.shell.instance.timeout";
            public static final String SCRIPT_CACHE_SIZE = "hcjf.code.evaluator.java.script.cache.size";
            public static final String IMPORTS = "hcjf.code.evaluator.java.script.cache.imports";
        }

        public static final class Python {
            public static final String IMPL_NAME = "hcjf.code.evaluator.python.impl.name";
        }

        public static final class Js {
            public static final String IMPL_NAME = "hcjf.code.evaluator.js.impl.name";
        }
    }

    public static final class Query {
        public static final String SINGLE_PATTERN = "hcjf.query.single.pattern";
        public static final String LOG_TAG = "hcjf.query.log.tag";
        public static final String DEFAULT_LIMIT = "hcjf.query.default.limit";
        public static final String DEFAULT_DESC_ORDER = "hcjf.query.default.desc.order";
        public static final String SELECT_REGULAR_EXPRESSION = "hcjf.query.select.regular.expression";
        public static final String CONDITIONAL_REGULAR_EXPRESSION = "hcjf.query.conditional.regular.expression";
        public static final String EVALUATOR_COLLECTION_REGULAR_EXPRESSION = "hcjf.query.evaluator.collection.regular.expression";
        public static final String OPERATION_REGULAR_EXPRESSION = "hcjf.query.operation.regular.expression";
        public static final String JOIN_REGULAR_EXPRESSION = "hcjf.query.join.regular.expression";
        public static final String JOIN_RESOURCE_VALUE_INDEX = "hcjf.query.join.resource.value";
        public static final String JOIN_DYNAMIC_RESOURCE_INDEX = "hcjf.query.join.dynamic.resource.index";
        public static final String JOIN_DYNAMIC_RESOURCE_ALIAS_INDEX = "hcjf.query.join.dynamic.resource.alias.index";
        public static final String JOIN_CONDITIONAL_BODY_INDEX = "hcjf.query.join.conditional.body.index";
        public static final String UNION_REGULAR_EXPRESSION = "hcjf.query.union.regular.expression";
        public static final String SOURCE_REGULAR_EXPRESSION = "hcjf.query.source.regular.expression";
        public static final String AS_REGULAR_EXPRESSION = "hcjf.query.as.regular.expression";
        public static final String DESC_REGULAR_EXPRESSION = "hcjf.query.desc.regular.expression";
        public static final String ENVIRONMENT_GROUP_INDEX = "hcjf.query.environment.group.index";
        public static final String SELECT_GROUP_INDEX = "hcjf.query.select.group.index";
        public static final String FROM_GROUP_INDEX = "hcjf.query.from.group.index";
        public static final String CONDITIONAL_GROUP_INDEX = "hcjf.query.conditional.group.index";
        public static final String RESOURCE_VALUE_INDEX = "hcjf.query.resource.value.index";
        public static final String DYNAMIC_RESOURCE_INDEX = "hcjf.query.dynamic.resource.group.index";
        public static final String DYNAMIC_RESOURCE_ALIAS_INDEX = "hcjf.query.dynamic.resource.alias.group.index";
        public static final String JOIN_RESOURCE_NAME_INDEX = "hcjf.query.join.resource.name.index";
        public static final String JOIN_EVALUATORS_INDEX = "hcjf.query.join.evaluators.index";
        public static final String DATE_FORMAT = "hcjf.query.date.format";
        public static final String DECIMAL_SEPARATOR = "hcjf.query.decimal.separator";
        public static final String DECIMAL_FORMAT = "hcjf.query.decimal.format";
        public static final String SCIENTIFIC_NOTATION = "hcjf.query.scientific.notation";
        public static final String SCIENTIFIC_NOTATION_FORMAT = "hcjf.query.scientific.notation.format";
        public static final String EVALUATORS_CACHE_NAME = "hcjf.query.evaluators.cache";
        public static final String EVALUATOR_LEFT_VALUES_CACHE_NAME = "hcjf.query.evaluator.left.values.cache";
        public static final String EVALUATOR_RIGHT_VALUES_CACHE_NAME = "hcjf.query.evaluator.right.values.cache";
        public static final String COMPILER_CACHE_SIZE = "hcjf.query.compiler.cache.size";
        public static final String DEFAULT_COMPILER = "hcjf.query.default.compiler";
        public static final String DEFAULT_SERIALIZER = "hcjf.query.default.serializer";

        public static final class ReservedWord {
            public static final String ENVIRONMENT = "hcjf.query.environment.reserved.word";
            public static final String SELECT = "hcjf.query.select.reserved.word";
            public static final String FROM = "hcjf.query.from.reserved.word";
            public static final String JOIN = "hcjf.query.join.reserved.word";
            public static final String UNION = "hcjf.query.union.reserved.word";
            public static final String FULL = "hcjf.query.full.reserved.word";
            public static final String INNER = "hcjf.query.inner.join.reserved.word";
            public static final String LEFT = "hcjf.query.left.join.reserved.word";
            public static final String RIGHT = "hcjf.query.right.join.reserved.word";
            public static final String ON = "hcjf.query.on.reserved.word";
            public static final String WHERE = "hcjf.query.where.reserved.word";
            public static final String ORDER_BY = "hcjf.query.order.by.reserved.word";
            public static final String DESC = "hcjf.query.desc.reserved.word";
            public static final String LIMIT = "hcjf.query.limit.reserved.word";
            public static final String START = "hcjf.query.start.reserved.word";
            public static final String RETURN_ALL = "hcjf.query.return.all.reserved.word";
            public static final String ARGUMENT_SEPARATOR = "hcjf.query.argument.separator";
            public static final String EQUALS = "hcjf.query.equals.reserved.word";
            public static final String DISTINCT = "hcjf.query.distinct.reserved.word";
            public static final String DISTINCT_2 = "hcjf.query.distinct.2.reserved.word";
            public static final String GREATER_THAN = "hcjf.query.greater.than.reserved.word";
            public static final String GREATER_THAN_OR_EQUALS = "hcjf.query.greater.than.or.equals.reserved.word";
            public static final String SMALLER_THAN = "hcjf.query.smaller.than.reserved.word";
            public static final String SMALLER_THAN_OR_EQUALS = "hcjf.query.smaller.than.or.equals.reserved.word";
            public static final String IN = "hcjf.query.in.reserved.word";
            public static final String NOT_IN = "hcjf.query.not.in.reserved.word";
            public static final String NOT = "hcjf.query.not.reserved.word";
            public static final String NOT_2 = "hcjf.query.not.2.reserved.word";
            public static final String LIKE = "hcjf.query.like.reserved.word";
            public static final String LIKE_WILDCARD = "hcjf.query.like.wildcard.reserved.word";
            public static final String AND = "hcjf.query.and.reserved.word";
            public static final String OR = "hcjf.query.or.reserved.word";
            public static final String STATEMENT_END = "hcjf.query.statement.end.reserved.word";
            public static final String REPLACEABLE_VALUE = "hcjf.query.replaceable.value.reserved.word";
            public static final String STRING_DELIMITER = "hcjf.query.string.delimiter.reserved.word";
            public static final String NULL = "hcjf.query.null.reserved.word";
            public static final String TRUE = "hcjf.query.true.reserved.word";
            public static final String FALSE = "hcjf.query.false.reserved.word";
            public static final String AS = "hcjf.query.as.reserved.word";
            public static final String GROUP_BY = "hcjf.query.group.by.reserved.word";
            public static final String DISJOINT_BY = "hcjf.query.disjoint.by.reserved.word";
            public static final String UNDERLYING = "hcjf.query.underlying.reserved.word";
            public static final String SRC = "hcjf.query.src.reserved.word";
        }

        public static class Function {

            public static final String NAME_PREFIX = "hcjf.query.function.name.prefix";
            public static final String MATH_EVAL_EXPRESSION_NAME = "hcjf.query.function.math.eval.expression.name";
            public static final String MATH_FUNCTION_NAME = "hcjf.query.function.math.name";
            public static final String STRING_FUNCTION_NAME = "hcjf.query.function.string.name";
            public static final String DATE_FUNCTION_NAME = "hcjf.query.function.date.name";
            public static final String MATH_ADDITION = "hcjf.query.function.math.addition";
            public static final String MATH_SUBTRACTION = "hcjf.query.function.math.subtraction";
            public static final String MATH_MULTIPLICATION = "hcjf.query.function.math.multiplication";
            public static final String MATH_DIVISION = "hcjf.query.function.math.division";
            public static final String MATH_MODULUS = "hcjf.query.function.math.modulus";
            public static final String MATH_EQUALS = "hcjf.query.function.maht.equals";
            public static final String MATH_DISTINCT = "hcjf.query.function.math.distinct";
            public static final String MATH_DISTINCT_2 = "hcjf.query.function.math.distinct.2";
            public static final String MATH_GREATER_THAN = "hcjf.query.function.math.grater.than";
            public static final String MATH_GREATER_THAN_OR_EQUALS = "hcjf.query.function.math.grater.than.or.equals";
            public static final String MATH_LESS_THAN =  "hcjf.query.function.math.less.than";
            public static final String MATH_LESS_THAN_OR_EQUALS =  "hcjf.query.function.math.less.than.or.equals";
            public static final String REFERENCE_FUNCTION_NAME = "hcjf.query.function.reference.name";
            public static final String BSON_FUNCTION_NAME = "hcjf.query.function.bson.name";
            public static final String COLLECTION_FUNCTION_NAME = "hcjf.query.function.collection.name";
            public static final String OBJECT_FUNCTION_NAME = "hcjf.query.function.object.name";
            public static final String BIG_DECIMAL_DIVIDE_SCALE = "hcjf.query.function.big.decimal.divide.scale";
            public static final String MATH_OPERATION_RESULT_ROUND = "hcjf.query.function.math.operation.result.round";
            public static final String MATH_OPERATION_RESULT_ROUND_CONTEXT = "hcjf.query.function.math.operation.result.round.context";
        }
    }

    public static class Cache {
        public static final String SERVICE_NAME = "hcjf.cache.service.name";
        public static final String SERVICE_PRIORITY = "hcjf.cache.service.priority";
        public static final String INVALIDATOR_TIME_OUT = "hcjf.cache.invalidator.time.out";
    }

    //Java property names
    public static final String FILE_ENCODING = "file.encoding";

    private Properties properties;

    public LayerSystemProperties() {

        properties = new Properties();
        properties.put(Locale.DEFAULT_LOCALE, java.util.Locale.getDefault().toLanguageTag());
        properties.put(Locale.DEFAULT_LOCALE_LAYER_IMPLEMENTATION_NAME, DefaultLocaleLayer.class.getName());
        properties.put(Locale.DEFAULT_LOCALE_LAYER_IMPLEMENTATION_CLASS_NAME, DefaultLocaleLayer.class.getName());
        properties.put(Locale.LOG_TAG, "LOCALE");

        properties.put(CodeEvaluator.Java.IMPL_NAME, "java");
        properties.put(CodeEvaluator.Java.J_SHELL_POOL_SIZE, "5");
        properties.put(CodeEvaluator.Java.J_SHELL_INSTANCE_TIMEOUT, "5000");
        properties.put(CodeEvaluator.Java.SCRIPT_CACHE_SIZE, "10");
        properties.put(CodeEvaluator.Java.IMPORTS, "[]");
        properties.put(CodeEvaluator.Js.IMPL_NAME, "js");

        properties.put(Query.SINGLE_PATTERN, "SELECT * FROM %s");
        properties.put(Query.LOG_TAG, "QUERY");
        properties.put(Query.DEFAULT_LIMIT, "1000");
        properties.put(Query.DEFAULT_DESC_ORDER, "false");
        properties.put(Query.SELECT_REGULAR_EXPRESSION, "(?i)^(?<environment>environment[ ]{1,}'¡[0-9]{1,}·'[ ]{1,}){0,1}(?<select>select[ ]{1,}[a-zA-Z_0-9'=<>!,.~+-/*\\|%\\$&¡¿·@ ]{1,})(?<from>[  ]?from[  ](?<resourceValue>[a-zA-Z_0-9$¡¿·'.]{1,})(?<dynamicResource> as (?<dynamicResourceAlias>[a-zA-Z_0-9$¡¿·.]{1,}[ ]?)|[ ]?))(?<conditionalBody>[a-zA-Z_0-9'=,.~+-/\\|* ?%\\$&¡¿·@<>!\\:\\-()\\[\\]]{1,})?[$;]?");
        properties.put(Query.CONDITIONAL_REGULAR_EXPRESSION, "(?i)((?<=(^((inner |left |right |full )?join )|^where |^limit |^start |^order by |^group by |^disjoint by |^underlying |(( inner | left | right | full )?join )| where | limit | start | order by | group by | disjoint by | underlying )))|(?=(^((inner |left |right |full )?join )|^where |^limit |^start |^order by |^group by |^disjoint by |^underlying |(( inner | left | right | full )?join )| where | limit | start | order by | group by | disjoint by | underlying ))");
        properties.put(Query.EVALUATOR_COLLECTION_REGULAR_EXPRESSION, "(?i)((?<=( and | or ))|(?=( and | or )))");
        properties.put(Query.OPERATION_REGULAR_EXPRESSION, "(?i)(?<=(=|<>|!=|>|<|>=|<=| in | not in | like ))|(?=(=|<>|!=|>|<|>=|<=| in | not in | like ))");
        properties.put(Query.JOIN_REGULAR_EXPRESSION, "(?i)(((?<resourceValue>[a-zA-Z_0-9$¡¿·.]{1,})(?<dynamicResource>[ ]as[ ](?<dynamicResourceAlias>[a-zA-Z_0-9.]{1,})|[ ]?)) on (?<conditionalBody>[a-zA-Z_0-9'=,.~+-\\/* ?%\\$&¡¿·@<>!\\:\\-()\\[\\]]{1,}))");
        properties.put(Query.JOIN_RESOURCE_VALUE_INDEX, "resourceValue");
        properties.put(Query.JOIN_DYNAMIC_RESOURCE_INDEX, "dynamicResource");
        properties.put(Query.JOIN_DYNAMIC_RESOURCE_ALIAS_INDEX, "dynamicResourceAlias");
        properties.put(Query.JOIN_CONDITIONAL_BODY_INDEX, "conditionalBody");
        properties.put(Query.UNION_REGULAR_EXPRESSION, "(?i)((?<=( union ))|(?=( union )))");
        properties.put(Query.SOURCE_REGULAR_EXPRESSION, "(?i)((?<=( src ))|(?=( src )))");
        properties.put(Query.AS_REGULAR_EXPRESSION, "(?i)((?<=( as ))|(?=( as )))");
        properties.put(Query.DESC_REGULAR_EXPRESSION, "(?i)((?<=( desc| asc))|(?=( desc| asc)))");
        properties.put(Query.ENVIRONMENT_GROUP_INDEX, "environment");
        properties.put(Query.SELECT_GROUP_INDEX, "select");
        properties.put(Query.FROM_GROUP_INDEX, "from");
        properties.put(Query.CONDITIONAL_GROUP_INDEX, "conditionalBody");
        properties.put(Query.RESOURCE_VALUE_INDEX, "resourceValue");
        properties.put(Query.DYNAMIC_RESOURCE_INDEX, "dynamicResource");
        properties.put(Query.DYNAMIC_RESOURCE_ALIAS_INDEX, "dynamicResourceAlias");
        properties.put(Query.JOIN_RESOURCE_NAME_INDEX, "0");
        properties.put(Query.JOIN_EVALUATORS_INDEX, "1");
        properties.put(Query.DATE_FORMAT, "yyyy-MM-dd HH:mm:ss");
        properties.put(Query.DECIMAL_SEPARATOR, ".");
        properties.put(Query.DECIMAL_FORMAT, "0.000");
        properties.put(Query.SCIENTIFIC_NOTATION, "E");
        properties.put(Query.SCIENTIFIC_NOTATION_FORMAT, "0.0E0");
        properties.put(Query.EVALUATORS_CACHE_NAME, "__evaluators__cache__");
        properties.put(Query.EVALUATOR_LEFT_VALUES_CACHE_NAME, "__evaluator__left__values__cache__");
        properties.put(Query.EVALUATOR_RIGHT_VALUES_CACHE_NAME, "__evaluator__right__values__cache__");
        properties.put(Query.COMPILER_CACHE_SIZE, "1000");
        properties.put(Query.DEFAULT_COMPILER, "SQL");
        properties.put(Query.DEFAULT_SERIALIZER, "SQL");
        properties.put(Query.ReservedWord.ENVIRONMENT, "ENVIRONMENT");
        properties.put(Query.ReservedWord.SELECT, "SELECT");
        properties.put(Query.ReservedWord.FROM, "FROM");
        properties.put(Query.ReservedWord.JOIN, "JOIN");
        properties.put(Query.ReservedWord.UNION, "UNION");
        properties.put(Query.ReservedWord.FULL, "FULL");
        properties.put(Query.ReservedWord.INNER, "INNER");
        properties.put(Query.ReservedWord.LEFT, "LEFT");
        properties.put(Query.ReservedWord.RIGHT, "RIGHT");
        properties.put(Query.ReservedWord.ON, "ON");
        properties.put(Query.ReservedWord.WHERE, "WHERE");
        properties.put(Query.ReservedWord.ORDER_BY, "ORDER BY");
        properties.put(Query.ReservedWord.DESC, "DESC");
        properties.put(Query.ReservedWord.LIMIT, "LIMIT");
        properties.put(Query.ReservedWord.START, "START");
        properties.put(Query.ReservedWord.RETURN_ALL, "*");
        properties.put(Query.ReservedWord.ARGUMENT_SEPARATOR, ",");
        properties.put(Query.ReservedWord.EQUALS, "=");
        properties.put(Query.ReservedWord.DISTINCT, "<>");
        properties.put(Query.ReservedWord.DISTINCT_2, "!=");
        properties.put(Query.ReservedWord.GREATER_THAN, ">");
        properties.put(Query.ReservedWord.GREATER_THAN_OR_EQUALS, ">=");
        properties.put(Query.ReservedWord.SMALLER_THAN, "<");
        properties.put(Query.ReservedWord.SMALLER_THAN_OR_EQUALS, "<=");
        properties.put(Query.ReservedWord.IN, "IN");
        properties.put(Query.ReservedWord.NOT_IN, "NOT IN");
        properties.put(Query.ReservedWord.NOT, "NOT");
        properties.put(Query.ReservedWord.NOT_2, "!");
        properties.put(Query.ReservedWord.LIKE, "LIKE");
        properties.put(Query.ReservedWord.LIKE_WILDCARD, "%");
        properties.put(Query.ReservedWord.AND, "AND");
        properties.put(Query.ReservedWord.OR, "OR");
        properties.put(Query.ReservedWord.STATEMENT_END, ";");
        properties.put(Query.ReservedWord.REPLACEABLE_VALUE, "?");
        properties.put(Query.ReservedWord.STRING_DELIMITER, "'");
        properties.put(Query.ReservedWord.NULL, "NULL");
        properties.put(Query.ReservedWord.TRUE, "TRUE");
        properties.put(Query.ReservedWord.FALSE, "FALSE");
        properties.put(Query.ReservedWord.AS, "AS");
        properties.put(Query.ReservedWord.GROUP_BY, "GROUP BY");
        properties.put(Query.ReservedWord.DISJOINT_BY, "DISJOINT BY");
        properties.put(Query.ReservedWord.UNDERLYING, "UNDERLYING");
        properties.put(Query.ReservedWord.SRC, "SRC");
        properties.put(Query.Function.NAME_PREFIX, "query.");
        properties.put(Query.Function.MATH_FUNCTION_NAME, "math");
        properties.put(Query.Function.STRING_FUNCTION_NAME, "string");
        properties.put(Query.Function.DATE_FUNCTION_NAME, "date");
        properties.put(Query.Function.MATH_EVAL_EXPRESSION_NAME, "evalExpression");
        properties.put(Query.Function.MATH_ADDITION, "+");
        properties.put(Query.Function.MATH_SUBTRACTION, "-");
        properties.put(Query.Function.MATH_MULTIPLICATION, "*");
        properties.put(Query.Function.MATH_DIVISION, "/");
        properties.put(Query.Function.MATH_MODULUS, "%");
        properties.put(Query.Function.MATH_EQUALS, "=");
        properties.put(Query.Function.MATH_DISTINCT, "!=");
        properties.put(Query.Function.MATH_DISTINCT_2, "<>");
        properties.put(Query.Function.MATH_GREATER_THAN, ">");
        properties.put(Query.Function.MATH_GREATER_THAN_OR_EQUALS, ">=");
        properties.put(Query.Function.MATH_LESS_THAN,"<");
        properties.put(Query.Function.MATH_LESS_THAN_OR_EQUALS, "<=");
        properties.put(Query.Function.REFERENCE_FUNCTION_NAME, "reference");
        properties.put(Query.Function.BSON_FUNCTION_NAME, "bson");
        properties.put(Query.Function.COLLECTION_FUNCTION_NAME, "collection");
        properties.put(Query.Function.OBJECT_FUNCTION_NAME, "object");
        properties.put(Query.Function.BIG_DECIMAL_DIVIDE_SCALE, "8");
        properties.put(Query.Function.MATH_OPERATION_RESULT_ROUND, "true");
        properties.put(Query.Function.MATH_OPERATION_RESULT_ROUND_CONTEXT, "128");

        properties.put(Cache.SERVICE_NAME, "Cache");
        properties.put(Cache.SERVICE_PRIORITY, "0");
        properties.put(Cache.INVALIDATOR_TIME_OUT, "30000");
    }

    @Override
    public Properties getDefaults() {
        return properties;
    }

}

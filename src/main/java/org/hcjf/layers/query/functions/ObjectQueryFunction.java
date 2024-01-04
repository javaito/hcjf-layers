package org.hcjf.layers.query.functions;

import org.hcjf.properties.LayerSystemProperties;
import org.hcjf.properties.SystemProperties;
import org.hcjf.utils.JsonUtils;
import org.hcjf.utils.Matrix;

import java.math.BigDecimal;
import java.util.*;

public class ObjectQueryFunction extends BaseQueryFunctionLayer implements QueryFunctionLayerInterface {

    private static final String IS_NOT_NULL = "isNotNull";
    private static final String IS_NULL = "isNull";
    private static final String IS_COLLECTION = "isCollection";
    private static final String IS_MAP = "isMap";
    private static final String IS_DATE = "isDate";
    private static final String IS_STRING = "isString";
    private static final String IS_NUMBER = "isNumber";
    private static final String INSTANCE_OF = "instanceOf";
    private static final String IF = "if";
    private static final String CASE = "case";
    private static final String EQUALS = "equals";
    private static final String NEW = "new";
    private static final String NEW_UUID = "newUUID";
    private static final String UUID_TO_BASE64 = "uuidToBase64";
    private static final String BASE64_TO_UUID = "base64ToUuid";
    private static final String NEW_MAP = "newMap";
    private static final String NEW_ARRAY = "newArray";
    private static final String JSON_TO_OBJECT = "jsonToObject";

    private static final class InstanceOfValues {
        private static final String NULL = "NULL";
        private static final String COLLECTION = "COLLECTION";
        private static final String MAP = "MAP";
        private static final String DATE = "DATE";
        private static final String STRING = "STRING";
        private static final String NUMBER = "NUMBER";
        private static final String UUID = "UUID";
        private static final String BOOLEAN = "BOOLEAN";
        private static final String OBJECT = "OBJECT";
        private static final String MATRIX = "MATRIX";
    }

    public ObjectQueryFunction() {
        super(SystemProperties.get(LayerSystemProperties.Query.Function.OBJECT_FUNCTION_NAME));

        addFunctionName(IS_NOT_NULL);
        addFunctionName(IS_NULL);
        addFunctionName(IS_COLLECTION);
        addFunctionName(IS_MAP);
        addFunctionName(IS_DATE);
        addFunctionName(IS_STRING);
        addFunctionName(IS_NUMBER);
        addFunctionName(INSTANCE_OF);
        addFunctionName(IF);
        addFunctionName(CASE);
        addFunctionName(EQUALS);
        addFunctionName(NEW);
        addFunctionName(NEW_UUID);
        addFunctionName(UUID_TO_BASE64);
        addFunctionName(BASE64_TO_UUID);
        addFunctionName(NEW_MAP);
        addFunctionName(NEW_ARRAY);
        addFunctionName(JSON_TO_OBJECT);
    }

    @Override
    public Object evaluate(String functionName, Object... parameters) {
        Object result = null;
        switch(functionName) {
            case(IS_NOT_NULL): {
                boolean booleanValue = false;
                for(Object parameter : parameters) {
                    booleanValue = parameter != null;
                    if(!booleanValue) {
                        break;
                    }
                }
                result = booleanValue;
                break;
            }
            case(IS_NULL): {
                boolean booleanValue = false;
                for(Object parameter : parameters) {
                    booleanValue = parameter == null;
                    if(booleanValue) {
                        break;
                    }
                }
                result = booleanValue;
                break;
            }
            case(IF): {
                Boolean condition = getParameter(0, parameters);
                if(condition != null && condition) {
                    result = getParameter(1, parameters);
                } else {
                    if(parameters.length == 3) {
                        result = getParameter(2, parameters);
                    }
                }
                break;
            }
            case(CASE): {
                Object mainValue = getParameter(0, parameters);
                for (int i = 1; i < parameters.length; i += 2) {
                    if(i + 1 < parameters.length) {
                        if(mainValue.equals(parameters[i])) {
                            result = parameters[i+1];
                            break;
                        }
                    } else {
                        result = parameters[i];
                    }
                }
                break;
            }
            case(IS_COLLECTION): {
                result = getParameter(0, parameters) != null && getParameter(0, parameters) instanceof Collection;
                break;
            }
            case(IS_MAP): {
                result = getParameter(0, parameters) != null && getParameter(0, parameters) instanceof Map;
                break;
            }
            case(IS_DATE): {
                result = getParameter(0, parameters) != null && getParameter(0, parameters) instanceof Date;
                break;
            }
            case(IS_STRING): {
                result = getParameter(0, parameters) != null && getParameter(0, parameters) instanceof String;
                break;
            }
            case(IS_NUMBER): {
                result = getParameter(0, parameters) != null && getParameter(0, parameters) instanceof Number;
                break;
            }
            case(INSTANCE_OF): {
                Object parameter = null;
                if(parameters.length == 1) {
                    parameter = parameters[0];
                }
                if(parameter == null) {
                    result = InstanceOfValues.NULL;
                } else if(parameter instanceof Collection) {
                    result = InstanceOfValues.COLLECTION;
                } else if(parameter instanceof Map) {
                    result = InstanceOfValues.MAP;
                } else if(parameter instanceof Date) {
                    result = InstanceOfValues.DATE;
                } else if(parameter instanceof String) {
                    result = InstanceOfValues.STRING;
                } else if(parameter instanceof Number) {
                    result = InstanceOfValues.NUMBER;
                } else if(parameter instanceof UUID) {
                    result = InstanceOfValues.UUID;
                } else if(parameter instanceof Boolean) {
                    result = InstanceOfValues.BOOLEAN;
                } else if(parameter instanceof Matrix) {
                    result = InstanceOfValues.MATRIX;
                } else {
                    result = InstanceOfValues.OBJECT;
                }
                break;
            }
            case(EQUALS): {
                Object parameter1 = getParameter(0, parameters);
                Object parameter2 = getParameter(1, parameters);

                if(parameter1 instanceof Number && parameter2 instanceof Number) {
                    BigDecimal bigDecimal1 = BigDecimal.valueOf(((Number) parameter1).doubleValue());
                    BigDecimal bigDecimal2 = BigDecimal.valueOf(((Number) parameter2).doubleValue());
                    result = bigDecimal1.equals(bigDecimal2);
                } else {
                    result = Objects.equals(parameter1, parameter2);
                }
                break;
            }
            case(NEW): {
                if(parameters.length == 1) {
                    result = getParameter(0, parameters);
                } else {
                    result = null;
                }
                break;
            }
            case (NEW_UUID): {
                result = UUID.randomUUID();
                break;
            }
            case (UUID_TO_BASE64): {
                UUID uuid = getParameter(0, parameters);
                long msb = uuid.getMostSignificantBits();
                long lsb = uuid.getLeastSignificantBits();

                byte[] buffer = new byte[16];
                for (int i = 0; i < 8; i++) {
                    buffer[7 - i] = (byte) (msb >>> 8 * (7 - i));
                }
                for (int i = 8; i < 16; i++) {
                    buffer[23 - i] = (byte) (lsb >>> 8 * (7 - i));
                }
                result = Base64.getEncoder().encodeToString(buffer);
                break;
            }
            case (BASE64_TO_UUID): {
                String base64 = getParameter(0, parameters);
                byte[] decoded = Base64.getDecoder().decode(base64);
                long msb = 0;
                long lsb = 0;
                for (int i = 7; i >= 0; i--) {
                    msb = (msb << 8) | (decoded[i] & 0xff);
                }
                for (int i = 15; i >= 8; i--) {
                    lsb = (lsb << 8) | (decoded[i] & 0xff);
                }
                result = new UUID(msb, lsb);
                break;
            }
            case(NEW_MAP): {
                Map<String,Object> map = new HashMap<>();
                for (int i = 0; i < parameters.length; i+=2) {
                    String key = parameters[i].toString();
                    Object value = null;
                    if(parameters.length > i+1) {
                        value = parameters[i+1];
                    }
                    map.put(key, value);
                }
                result = map;
                break;
            }
            case(NEW_ARRAY): {
                Collection collection = new ArrayList();
                for(Object parameter : parameters) {
                    if(parameter instanceof Collection) {
                        collection.addAll((Collection) parameter);
                    } else if(parameter.getClass().isArray()) {
                        collection.addAll(Arrays.asList(parameter));
                    } else {
                        collection.add(parameter);
                    }
                }
                result = collection;
                break;
            }
            case(JSON_TO_OBJECT): {
                String stringValue = getParameter(0, parameters);
                result = JsonUtils.createObject(stringValue);
                break;
            }
        }
        return result;
    }
}

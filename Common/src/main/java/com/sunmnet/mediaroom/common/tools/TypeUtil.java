package com.sunmnet.mediaroom.common.tools;

import java.util.List;
import java.util.Map;

public class TypeUtil {

    public static int objToIntDef(Object obj, int def) {
        if (obj == null)
            return def;
        if (obj instanceof String) {
            try {
                return Integer.parseInt((String) obj);
            } catch (NumberFormatException e) {
                return def;
            }
        } else if (obj instanceof Long) {
            return ((Long) obj).intValue();
        } else if (obj instanceof Integer) {
            return (Integer) obj;
        } else if (obj instanceof Float) {
            return (((Float) obj).intValue());
        } else if (obj instanceof Byte) {
            return (((Byte) obj).intValue());
        } else if (obj instanceof Double) {
            return (((Double) obj).intValue());
        } else if (obj instanceof Character) {
            return ((Character) obj);
        }
        return def;
    }

    public static int objToInt(Object obj) {
        return objToIntDef(obj, 0);
    }

    public static long objToLongDef(Object obj, long def) {
        if (obj == null)
            return def;
        try {
            if (obj instanceof String) {
                try {
                    return Long.parseLong((String) obj);
                } catch (NumberFormatException e) {
                    return def;
                }
            } else if (obj instanceof Long) {
                return (Long) obj;
            } else if (obj instanceof Integer) {
                return (Integer) obj;
            } else if (obj instanceof Float) {
                return (((Float) obj).longValue());
            } else if (obj instanceof Byte) {
                return (((Byte) obj).longValue());
            } else if (obj instanceof Double) {
                return (((Double) obj).longValue());
            } else if (obj instanceof Character) {
                return (Character) obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public static long objToLong(Object obj) {
        return objToLongDef(obj, 0);
    }

    public static float objToFloatDef(Object obj, float def) {
        if (obj == null)
            return def;
        try {
            if (obj instanceof String) {
                try {
                    return Float.parseFloat((String) obj);
                } catch (NumberFormatException e) {
                    return def;
                }
            } else if (obj instanceof Long) {
                return ((Long) obj).floatValue();
            } else if (obj instanceof Integer) {
                return ((Integer) obj).floatValue();
            } else if (obj instanceof Float) {
                return (Float) obj;
            } else if (obj instanceof Byte) {
                return (((Byte) obj).floatValue());
            } else if (obj instanceof Double) {
                return (((Double) obj).floatValue());
            } else if (obj instanceof Character) {
                return (Character) obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public static float objToFloat(Object obj) {
        return objToFloatDef(obj, 0);
    }

    public static String objToStrDef(Object object, String def) {
        if (object != null)
            return object.toString();
        return def;
    }

    public static String objToStr(Object object) {
        return objToStrDef(object, null);
    }

    public static String objToStrNotNull(Object object) {
        return objToStrDef(object, "");
    }

    public static boolean objToBoolean(Object obj) {
        if (obj instanceof Boolean)
            return (boolean) obj;
        return obj instanceof String && Boolean.getBoolean((String) obj);
    }

    public static List<Map<String, Object>> castToMapList_SO(Object obj) {
        try {
            return (List<Map<String, Object>>) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Object> castToMap_SO(Object obj) {
        try {
            return (Map<String, Object>) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, String> castToMap_SS(Object obj) {
        try {
            return (Map<String, String>) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Integer> castToIntList(Object obj) {
        try {
            return (List<Integer>) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> castToStrList(Object obj) {
        try {
            return (List<String>) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

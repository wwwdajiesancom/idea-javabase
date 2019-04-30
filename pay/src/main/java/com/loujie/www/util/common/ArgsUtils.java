package com.loujie.www.util.common;


import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用基础操作/判断
 *
 * @author loujie
 */
public class ArgsUtils {

    private static final String[] SZ = new String[]{"零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
    private static final String[] DW = new String[]{"十", "百"};

    public static String getRealUrl(String realUrl, String jiao_yu_value) {
        if (!isEmpty(jiao_yu_value) && !isEmpty(realUrl)) {
            try {
                if (realUrl.indexOf("?") != -1) {
                    realUrl = realUrl + "&jiao_yu=" + URLEncoder.encode(jiao_yu_value, "utf-8");
                } else {
                    realUrl = realUrl + "?jiao_yu=" + URLEncoder.encode(jiao_yu_value, "utf-8");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return realUrl;
    }

    public static String toXml(Map<String, Object> map) {
        StringBuilder returnXml = new StringBuilder();
        returnXml.append("<xml>");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            returnXml.append("<" + entry.getKey() + ">");
            returnXml.append(entry.getValue());
            returnXml.append("</" + entry.getKey() + ">");
        }
        returnXml.append("</xml>");
        return returnXml.toString();
    }

    /**
     * 线程休息millis毫秒
     *
     * @param millis
     */
    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String convertSort(Integer sort) {
        if (sort <= 10) {
            return SZ[sort];
        } else if (sort < 100) {
            int g = sort % 10;// 个位
            int s = sort / 10;// 十位
            return (s == 1 ? "" : SZ[s]) + DW[0] + (g == 0 ? "" : SZ[g]);
        } else if (sort < 1000) {
            int g = sort % 10;
            int s = (sort % 100) / 10;
            int b = sort / 100;
            if (g == 0 && s == 0) {
                return SZ[b] + DW[1];
            }
            if (g == 0) {
                return SZ[b] + DW[1] + SZ[s] + DW[0];
            }
            if (s == 0) {
                return SZ[b] + DW[1] + SZ[0] + SZ[g];
            }
            return SZ[b] + DW[1] + SZ[s] + SZ[0] + SZ[g];
        } else {
            return parseString(sort);
        }
    }

    public static boolean regex(String regex, String matchStr) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(matchStr);
        return matcher.matches();
    }

    /**
     * 获取hash%mod
     *
     * @param mobile
     * @param mod
     * @return
     */
    public static int getHashCode(String mobile, int mod) {
        int hashCode = mobile.hashCode() % mod;
        return (hashCode < 0) ? (hashCode * -1) : hashCode;
    }

    /**
     * 给date增加天数
     *
     * @param date    现在时间
     * @param addDays 要增加几天,可以为正数/负数
     * @return Date
     */
    public static Date getDate(Date date, int addDays) {
        if (date == null) {
            date = Calendar.getInstance().getTime();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, addDays);
        return calendar.getTime();
    }

    /**
     * 给date增加分钟
     *
     * @param date       现在时间
     * @param addMinutes 要增加多少分钟
     * @return Date
     */
    public static Date getDateMinute(Date date, int addMinutes) {
        if (date == null) {
            date = Calendar.getInstance().getTime();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, addMinutes);
        return calendar.getTime();
    }

    public static Date getDateMonth(Date date, int addMonth) {
        if (date == null) {
            date = Calendar.getInstance().getTime();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, addMonth);
        return calendar.getTime();
    }

    /**
     * 获取随机字符串
     *
     * @param randomLength 字符串长度
     * @return
     */
    public static String getRandomStr(int randomLength) {
        char[] chars = { //
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
                'v', 'w', 'x', 'y', 'z', //
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
                'V', 'W', 'X', 'Y', 'Z', //
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',//
        };
        StringBuilder stringBuilder = new StringBuilder();
        int chars_len = chars.length;
        Random random = new Random();
        for (int i = 0; i < randomLength; i++) {
            stringBuilder.append(chars[random.nextInt(chars_len)]);
        }
        return stringBuilder.toString();
    }

    /**
     * 全匹配正则,返回List,如果为null/空,就是不匹配
     *
     * @param regex      正则表达式
     * @param matcherStr 需要匹配的字符串
     * @return List的长度, 是groupCount=()的数量
     */
    public static List<String> regexMatcher(String regex, String matcherStr) {
        List<String> result = null;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(matcherStr);
        if (matcher.matches()) {
            result = new ArrayList<String>();
            for (int i = 1; i <= matcher.groupCount(); i++) {
                result.add(matcher.group(i));
            }
        }
        return result;
    }

    /**
     * 部分匹配,返回第一个匹配的信息
     *
     * @param regex
     * @param matcherStr
     * @return
     */
    public static List<String> regexFind(String regex, String matcherStr) {
        List<String> returnList = null;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(matcherStr);
        while (matcher.find()) {
            returnList = new ArrayList<String>();
            for (int i = 0; i <= matcher.groupCount(); i++) {
                returnList.add(matcher.group(i));
            }
            break;
        }
        return returnList;
    }

    /**
     * 数组转换成集合,并去掉kong值
     *
     * @param args
     * @return
     */
    public static List<String> toList(String[] args) {
        List<String> returnList = new ArrayList<String>();
        if (args != null && args.length > 0) {
            for (String obj : args) {
                if (!returnList.contains(trim(obj))) {
                    returnList.add(trim(obj).toString());
                }
            }
        }
        return returnList;
    }

    /**
     * 去掉两头的空格
     *
     * @param strs
     * @return
     */
    public static Object trim(String... strs) {
        if (strs == null || strs.length == 0)
            return "";
        LinkedHashSet<String> sets = new LinkedHashSet<String>();
        for (String str : strs) {
            if (isEmpty(str)) {
                sets.add("");
            } else {
                sets.add(str.trim());
            }
        }
        if (sets.size() == 1) {
            return sets.toArray()[0];
        }
        return sets.toArray();
    }

    /**
     * 判断是否为空
     *
     * @param args
     * @return boolean
     */
    public static boolean isEmpty(String args) {
        if (args == null || args.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static String toString(List<String> list) {
        if (isEmpty(list)) {
            return null;
        }
        StringBuilder returnSB = new StringBuilder();
        for (String item : list) {
            returnSB.append(item).append(",");
        }
        return returnSB.reverse().deleteCharAt(0).reverse().toString();
    }

    /**
     * 判断List是否为空
     *
     * @param args
     * @return
     */
    public static boolean isEmpty(List<?> args) {
        if (args == null || args.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为空
     *
     * @param args
     * @return boolean
     */
    public static boolean isEmpty(Long args) {
        if (args == null) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为空
     *
     * @param args
     * @return boolean
     */
    public static boolean isEmpty(Integer args) {
        if (args == null) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为空
     *
     * @param args
     * @return boolean
     */
    public static boolean isEmpty(Double args) {
        if (args == null) {
            return true;
        }
        return false;
    }

    /**
     * 判断String[]是否为空
     *
     * @param args
     * @return boolean
     */
    public static boolean isEmpty(String[] args) {
        if (args == null || args.length == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为NULL,或负数
     *
     * @param args
     * @return boolean
     */
    public static boolean isEmptyOrMinus(Integer args) {
        if (args == null) {
            return true;
        }
        if (args < 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为NULL,或负数
     *
     * @param args
     * @return boolean
     */
    public static boolean isEmptyOrMinus(Long args) {
        if (args == null) {
            return true;
        }
        if (args < 0L) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为NULL,或负数
     *
     * @param args
     * @return boolean
     */
    public static boolean isEmptyOrMinus(Double args) {
        if (args == null) {
            return true;
        }
        if (args < 0L) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为NULL,或小于当前时间
     *
     * @param args
     * @return boolean
     */
    public static boolean isEmptyOrMinus(Date args) {
        if (args == null) {
            return true;
        }
        try {
            if (args.getTime() <= Calendar.getInstance().getTimeInMillis()) {
                return true;
            }
        } catch (Exception exc) {
            // 时间参数存在问题

            return true;
        }
        return false;
    }

    public static BigDecimal parseBigDecimal(String value, String... defaultV) {
        BigDecimal defaultBD = new BigDecimal("0");
        if (defaultV != null && defaultV.length > 0) {
            defaultBD = new BigDecimal(defaultV[0]);
        }
        if (isEmpty(value)) {
            return defaultBD;
        }
        try {
            BigDecimal returnV = new BigDecimal(value);
            if (returnV.doubleValue() < 0) {
                return defaultBD;
            }
            return returnV;
        } catch (Exception e) {
            return defaultBD;
        }
    }

    /**
     * 转换成Long
     *
     * @param obj
     * @return
     */
    public static Long parseLong(Object obj) {
        try {
            if (obj == null)
                return 0l;
            return Long.parseLong(obj.toString());
        } catch (Exception exc) {
            return 0l;
        }
    }

    /**
     * 转换成Integer
     *
     * @param obj
     * @return
     */
    public static Integer parseInteger(Object obj, Integer... defaultV) {
        Integer default_v = 0;
        if (defaultV != null && defaultV.length > 0) {
            if (defaultV[0] == 0) {
                default_v = null;
            } else {
                default_v = defaultV[0];
            }
        }
        try {
            if (obj == null)
                return default_v;
            return Integer.parseInt(obj.toString());
        } catch (Exception exc) {
            return default_v;
        }
    }

    /**
     * 转换成Double
     *
     * @param obj
     * @return
     */
    public static Double parseDouble(Object obj, Double... defaultV) {
        Double returnV = 0.00;
        if (defaultV != null && defaultV.length > 0) {
            returnV = defaultV[0];
        }
        try {
            if (obj == null)
                return returnV;
            return Double.parseDouble(obj.toString());
        } catch (Exception exc) {
            return returnV;
        }
    }

    /**
     * 转换成字符串
     *
     * @param obj
     * @return
     */
    public static String parseString(Object obj, String... defaultV) {
        String _defaultV = "";
        if (defaultV != null && defaultV.length > 0) {
            _defaultV = defaultV[0];
        }
        try {
            if (obj == null) {
                return _defaultV;
            }
            return String.valueOf(obj);
        } catch (Exception exc) {
            return _defaultV;
        }
    }

    /**
     * 是否包含了汉字
     *
     * @param str
     * @return
     */
    public static boolean isContainsChinese(String str) {
        String regEx = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(str);
        boolean flg = false;
        if (matcher.find()) {
            flg = true;
        }
        return flg;
    }

    /**
     * 比较Date大小
     *
     * @param smallDate 小
     * @param bigDate   大
     * @return 如果正确为true，否则为false
     */
    public static boolean compareDate(Date smallDate, Date bigDate) {
        if (smallDate == null || bigDate == null) {
            return false;
        }
        if (smallDate.getTime() <= bigDate.getTime()) {
            return true;
        }
        return false;
    }

    /**
     * 与当前时间比较,较小的话就是true
     *
     * @param smallDate
     * @return
     */
    public static boolean compareTo(Date smallDate) {
        try {
            if (Calendar.getInstance().getTime().compareTo(smallDate) == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Date转化一下格式
     *
     * @param date
     * @param format
     * @return Date
     */
    public static Date formatDate2(Date date, String format) {
        return ArgsUtils.parseDate(formatDate(date, format), format);
    }

    /**
     * 格式化日期，根据不同的格式
     *
     * @param date   时间
     * @param format 格式[yyyy][-MM][-dd] [HH][:mm][:ss]
     * @return String
     */
    public static String formatDate(Date date, String format) {
        if (date == null) {
            date = Calendar.getInstance().getTime();
        }
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 转换成Date
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static Date parseDate(String dateStr, String format) {
        if (isEmpty(dateStr)) {
            Date nowDate = Calendar.getInstance().getTime();
            dateStr = new SimpleDateFormat(format).format(nowDate);
        }
        try {
            return new SimpleDateFormat(format).parse(dateStr);
        } catch (ParseException e) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            } catch (ParseException e1) {
                return Calendar.getInstance().getTime();
            }
        }
    }

    /**
     * 转换成Date
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static Date parseDate(String dateStr, String format, Date returnDate) {
        if (isEmpty(dateStr)) {
            return returnDate;
        }
        try {
            return new SimpleDateFormat(format).parse(dateStr);
        } catch (ParseException e) {
            return returnDate;
        }
    }

    /**
     * 手机号通用判断
     *
     * @param telNum
     * @return
     */
    public static boolean isMobiPhoneNum(String telNum) {
        String regex = "^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(telNum);
        return m.matches();
    }

    public static class MapResolver {

        @SuppressWarnings("unchecked")
        public static Object get(Map<String, Object> map, String key) {
            if (map == null) return null;
            String[] keys = key.split("\\.");
            Object returnObject = map;
            for (int i = 0; i < keys.length; i++) {
                String tempKey = keys[i];
                if (tempKey.contains("[")) {
                    List<Object> tempList = (List<Object>) ((Map<String, Object>) returnObject)
                            .get(tempKey.replaceAll("\\[[\\d]+\\]", ""));
                    if (!ArgsUtils.isEmpty(tempList)) {
                        returnObject = tempList.get(0);
                    } else {
                        return null;
                    }
                } else {
                    returnObject = ((Map<String, Object>) returnObject).get(tempKey);
                }
            }
            return returnObject;
        }

        /**
         * 返回的结果为Map
         *
         * @param map
         * @param key
         * @return
         */
        @SuppressWarnings("unchecked")
        public static Map<String, Object> getMap(Map<String, Object> map, String key) {
            return (Map<String, Object>) get(map, key);
        }

        /**
         * 返回的结果为Object
         *
         * @param map
         * @param key
         * @return
         */
        public static Object getObject(Map<String, Object> map, String key) {
            return get(map, key);
        }
    }

}

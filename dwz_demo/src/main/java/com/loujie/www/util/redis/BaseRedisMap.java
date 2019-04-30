package com.loujie.www.util.redis;

/**
 * 创建redis键的基础类
 *
 * @author loujie
 *
 */
public class BaseRedisMap {

	public static final int baseHour = 24;// 基础小时

	/**
	 * 例子类
	 *
	 * @author loujie
	 *
	 */
	static class RedisMapExample {
		public static final String key_example = getRedisKey("key_example");// 最简单例子
		public static final int ttl_example = 6 * 60;// (单位:分钟)

		public static final String key_example_parentId = getRedisKey("key_example_%s");// 稍微复杂例子;用法:RedisMapExample.key_example_parentId.replace("{parentId}",
																								// "otherData");
		public static final int ttl_example_parentId = 6 * 60;// (单位:分钟)

	}

	/**
	 * 获取调用者的类全名(类名+'_%s') 这个主要是为了获取Service层的名称的
	 *
	 * @return
	 */
	private static String getSimpleName() {
		String clazzName = new SecurityManager() {
			public String getClassName() {
				String clazzName = getClassContext()[3].getName();
				int indexOf$ = clazzName.indexOf('$');
				if (indexOf$ == -1) {
					return clazzName;
				}
				return clazzName.substring(0, indexOf$);
			}
		}.getClassName();
		return clazzName;
	}

	/**
	 * 获取redis的键
	 *
	 * @param args
	 * @return
	 */
	protected static String getRedisKey(Object args) {
		String clazzName = getSimpleName();
		String[] arrs = clazzName.split("\\.");
		return String.format(arrs[arrs.length - 1] + "_%s", args);
	}

}

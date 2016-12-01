package com.myapp.myapp_dao.util;

import java.util.Collection;
import java.util.Collections;

public final class CommonUtil {

	private CommonUtil() {

	}
	
	public static boolean notNullAndEmpty(Collection<?> collection) {
		return collection != null && !collection.isEmpty();
	}

	public static boolean nullOrEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}


	public static <T> Iterable<T> safe(Iterable<T> iterable) {
		return iterable == null ? Collections.<T>emptyList() : iterable;
	}

	public static String[] safe(String[] array) {
		return array == null ? new String[0] : array;
	}


	public static boolean nullOrEmpty(String str) {
		return null == str || "".equals(str.trim()) || "null".equalsIgnoreCase(str.trim());
	}

	public static boolean notNullAndEmpty(String str) {
		return !(null == str || "".equals(str.trim()) || "null".equalsIgnoreCase(str.trim()));
	}

	public static boolean isNotNull(Object obj) {
		return (null != obj ? true : false);
	}

	public static boolean isNull(Object obj) {
		return (null == obj ? true : false);
	}
}

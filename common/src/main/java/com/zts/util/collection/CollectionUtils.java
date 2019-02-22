package com.zts.util.collection;

import java.util.*;

/**
 * date 2010-04-06
 * {@code} 一些常用的集合有关的帮助方法
 */
public class CollectionUtils {
	
	/** 判断集合是否为空
	 * @param collection
	 * @return true为空，false不为空
	 */
	public static boolean isEmpty(Collection<?> collection){
		return collection == null || collection.isEmpty();
	}
	/**
	 * 判断Map集合是否为空
	 * @param m
	 * @return true为空，false不为空
	 */ 
	public static boolean isEmpty(Map<?, ?> m){
		return m == null || m.size() < 1;
	}
	
	public static boolean isEmpty(Object[] o) {
		return o == null || o.length < 1;
	}
	
	public static boolean isNotEmpty(Object[] o){
		return !isEmpty(o);
	}
	/** 判断集合是否不为空
	 * @param collection
	 * @return true为非空，false为空
	 */
	public static boolean isNotEmpty(Map<?, ?> m){
		return m != null && m.size() > 0;
	}
	/**
	 * 判断Map集合是否不为空
	 * @param m
	 * @return true为非空，false为空
	 */
	public static boolean isNotEmpty(Collection<?> coll){
		return coll != null && !coll.isEmpty();
	}
	
	public static Collection<?> convert(Object o) {
		if(o instanceof Collection) {
			return (Collection<?>) o;
		}
		
		if(o instanceof Object[]) {
			return Arrays.asList((Object[]) o);
		}
		
		throw new RuntimeException("cannot convert to Collections.Object:" + o);
	}
	/**
	 * 
	 * 俩个list比较找出相对与后者来说不同的部分
	 * @param aList
	 * @param bList
	 * @return 不同的集合
	 */
	public static <T> List<T> compareLatterFindDiff(List<T> aList, List<T> bList) {
		List<T> ret = new ArrayList<T>();
		if(aList == bList) {
			return null;
		}
		
		if(bList == null) {
			return null;
		}
		if(aList == null) {
			return bList;
		}
		
		for (T bObj : bList) {
			boolean isFind = false;
			for (T aObj : aList) {
				
				if(bObj == aObj || bObj.equals(aObj)) {
					isFind = true; break;
				}
			}
			
			if(!isFind) { //删除
				ret.add(bObj);
			}
		}
		
		return ret;
	}
}

package com.qqtech.core.frame.controller;

import com.qqtech.core.common.util.BeanUtil;
import com.qqtech.core.common.util.StringUtil;

/**
 * Controller路径构建起
 */
public class ControllerPath {

	/**
	 * @fields entityClass 简单的实体类
	 */
	private Class<?> entityClass = null;
	/**
	 * @fields URL_SEPARATOR 路径分隔符
	 */
	private static final String URL_SEPARATOR = "/";

	/**
	 * @fields entityName 实体名称
	 */
	private String entityName = null;
	/**
	 * @fields words 实体类路径
	 */
	private String[] words = null;
	/**
	 * 系统层次名称，如/admin、/agent、/seller等等
	 */
	private String level = "";

	public ControllerPath(Class<?> genericClass) {
		if (genericClass == null) {
			throw new IllegalArgumentException("[genericClass] - must not be null!");
		}
		entityClass = BeanUtil.getGenericClass(genericClass);
		if (entityClass == null) {
			throw new IllegalArgumentException(genericClass.getName() + "不是泛型类型！");
		}
		words = getWords(entityClass.getSimpleName());
		entityName = words[words.length - 1];
		String generic = genericClass.getSimpleName();
		if (generic.indexOf(StringUtil.capitalize(entityClass.getSimpleName())) > 0) {
			level = generic.substring(0, generic.indexOf(StringUtil.capitalize(entityClass.getSimpleName())))
					.toLowerCase() + URL_SEPARATOR;
		}
	}

	/**
	 * 获取显示页面路径
	 * 
	 * @return String "sys/dict/viewDict"
	 */
	public String getEntityViewPath() {
		StringBuilder sb = new StringBuilder();
		sb.append(getBasePath());
		sb.append(PagePrefix.VIEW);
		sb.append(StringUtil.capitalize(entityName));
		return sb.toString();
	}

	/**
	 * 显示列表路径
	 * 
	 * @return String "sys/dict/listDict"
	 */
	public String getListViewPath() {
		StringBuilder sb = new StringBuilder();
		sb.append(getBasePath());
		sb.append(PagePrefix.LIST);
		sb.append(StringUtil.capitalize(entityName));
		return sb.toString();
	}

	/**
	 * 添加页面路径信息
	 * 
	 * @return
	 */
	public String getAddViewPath() {
		StringBuilder sb = new StringBuilder();
		sb.append(getBasePath());
		sb.append(PagePrefix.ADD);
		sb.append(StringUtil.capitalize(entityName));
		return sb.toString();
	}

	/**
	 * 编辑页面路径信息
	 * 
	 * @return
	 */
	public String getEditViewPath() {
		StringBuilder sb = new StringBuilder();
		sb.append(getBasePath());
		sb.append(PagePrefix.EDIT);
		sb.append(StringUtil.capitalize(entityName));
		return sb.toString();
	}

	/**
	 * 获取删除返回路径,默认重定向到列表页面
	 * 
	 * @return
	 */
	public String getRedirectListPath() {
		return "redirect:" + URL_SEPARATOR + level + getBasePath();
	}

	/**
	 * 获取实体的名称，全小写
	 * 
	 * @return
	 */
	public String getEntityName() {
		return entityName;
	}

	/**
	 * 以字符串中的大写字母为标示拆分字符串,如果字符串为null或空则返回null
	 * 
	 * @param str
	 * @return String[] 拆分后的字符串，已转换为全小写
	 */
	private String[] getWords(String str) {
		if (StringUtil.isBlank(str))
			return null;
		String[] words = str.split("(?<!^)(?=[A-Z])");
		for (int i = 0; i < words.length; i++) {
			words[i] = StringUtil.lowerCase(words[i]);
		}
		return words;
	}

	/**
	 * 获取类名路径信息，例如：SysDict 则返回 "sys/dict/"
	 * 
	 * @param clazz
	 *            类
	 * @return String 类名路径信息
	 */
	private String getBasePath() {
		StringBuffer sb = new StringBuffer();
		for (String word : words) {
			sb.append(word).append(URL_SEPARATOR);
		}
		return sb.append(level).toString();
	}

}

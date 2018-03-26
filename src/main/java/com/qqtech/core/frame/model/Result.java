package com.qqtech.core.frame.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import com.qqtech.core.common.enums.RespEnum;
import com.qqtech.core.common.util.StringUtil;

/**
 * 此类用于以json格式返回ajax的请求数据
 * 
 * @author andy
 * 
 */
public class Result implements Serializable {
	private static final long serialVersionUID = 4982283335441477366L;
	private int code = RespEnum.OK.getCode();
	private String msg;
	private Object obj;
	private Map<String, Object> map;

	public Result(Object obj) {
		this(RespEnum.OK, obj);
	}

	public Result(RespEnum status) {
		this(status, null);
	}

	@SuppressWarnings("rawtypes")
	public Result(RespEnum status, Object obj, Object... objs) {
		this.code = status.getCode();
		this.obj = obj;
		if (obj instanceof String) {
			msg = (String) obj;
		} else {
			if (ArrayUtils.isEmpty(objs)) {
				if (obj == null) {
					msg = "Nodata";
				} else if (obj instanceof List && (((List) obj).size() == 0)) {
					msg = "Nodata";
				} else {
					msg = "Success";
				}
			} else {
				msg = "Success";
			}
		}
		if (ArrayUtils.isNotEmpty(objs)) {
			if (map == null) {
				map = new HashMap<String, Object>();
			}
			for (int i = 0; i < objs.length; i++) {
				this.put("obj" + (i + 1), objs[i]);
			}
		}
	}

	public void put(String k, Object v) {
		map.put(k, v);
	}

	public Object get(String key) {
		return map.get(key);
	}

	/**
	 * 如果表单数据验证通过，则返回true，则可以继续执行业务操作
	 * 
	 * @return
	 */
	public boolean checkForm() {
		if (this.code == RespEnum.OK.getCode()) {
			return true;
		}
		return false;
	}

	/**
	 * 根据业务规定检验表单数据
	 * 
	 * @param msg
	 */
	public void addErr(String msg) {
		if (StringUtil.isBlank(this.msg)) {
			this.msg = msg;
		} else {
			this.msg += "\n" + msg;
		}
		this.code = RespEnum.PARAMERROR.getCode();
	}

	public Result() {
		code = RespEnum.OK.getCode();
		if (map == null) {
			map = new HashMap<String, Object>();
		}
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
}

package com.qqtech.core.frame.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.qqtech.core.common.constant.CoreConst;
import com.qqtech.core.common.util.SettingCfgUtil;
import com.qqtech.core.common.util.StringUtil;
import com.qqtech.core.common.util.TimeUtil;

/**
 * 基础类
 * 
 * @author andy
 * 
 *         2015-6-27
 */
@SuppressWarnings("serial")
public abstract class BaseDomain implements ABaseable {
	/**
	 * 主键(必须)
	 */
	protected Integer id;
	/**
	 * 创建时间(必须)
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	protected Timestamp newAt;
	/**
	 * 更新时间(必须)
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	protected Timestamp updAt;

	/** 分页的第几页 **/
	protected Integer page;
	/** 分页的每页记录数 **/
	protected Integer size;
	/**
	 * 前端使用这个变量传用户登录的token
	 */
	protected String userLoginToken;

	/**
	 * 字符串以分号连接的图片集合(如http://.../../x.jpg;http://../../aa.jpg)
	 */
	protected String imgString;

	/**
	 * 封装可处理的图片集合
	 */
	protected List<ImgDomain> imgItems;
	/**
	 * 封装大图集合
	 */
	protected List<ImgDomain> bigImgItems;

	/** 是否有图片:true-有;false-无 **/
	protected boolean hasImg;

	public String getImgString() {
		return imgString;
	}

	/**
	 * 
	 * @param imgString
	 *            图片字符串连接
	 * @param next
	 *            图片处理后缀，如@!qquan-StyleFour
	 */
	public void setImgString(String imgString, String next) {
		if (StringUtil.isBlank(next)) {
			next = "";
		}
		this.imgString = imgString;
		imgItems = new ArrayList<ImgDomain>();
		bigImgItems = new ArrayList<ImgDomain>();
		imgItems.add(new ImgDomain());
		bigImgItems.add(new ImgDomain());
		hasImg = false;
		if (StringUtil.isNotBlank(imgString)) {
			String[] imgArr = imgString.split(";");
			if (imgArr != null && imgArr.length > 0) {
				imgItems = new ArrayList<ImgDomain>();
				bigImgItems = new ArrayList<ImgDomain>();
				int count = imgArr.length;
				hasImg = true;
				for (String imgSource : imgArr) {
					ImgDomain imgDomain = new ImgDomain();
					imgDomain.setSource(getImgHandleDomain() + imgSource + next);
					imgDomain.setCount(count);
					imgItems.add(imgDomain);
					ImgDomain bigImgDomain = new ImgDomain();
					bigImgDomain.setSource(getFileDomain() + imgSource);
					bigImgDomain.setCount(count);
					bigImgItems.add(bigImgDomain);
				}
			}
		}
	}

	/**
	 * 获取文件服务器地址
	 * 
	 * @return
	 */
	public String getFileDomain() {
		return SettingCfgUtil.getConfigHelper("core.properties").getValue("core.file.domain",
				CoreConst.PARAMVALUE_CORE_FILE_DOMAIN);
	}

	/**
	 * 获取图片服务处理的域名(如可以使用：@!qquan-StyleFour这类处理的方法)
	 * 
	 * @return
	 */
	public String getImgHandleDomain() {
		return SettingCfgUtil.getConfigHelper("core.properties").getValue("core.imghandle.domain",
				CoreConst.PARAMVALUE_CORE_IMGHANDLE_DOMAIN);
	}

	public String getStrNewAt() {
		return TimeUtil.getStringTimeByFormat(newAt, "yyyy-MM-dd HH:mm");
	}

	public String getStrUpdAt() {
		return TimeUtil.getStringTimeByFormat(updAt, "yyyy-MM-dd HH:mm");
	}

	public String getCnNewAt() {
		return TimeUtil.time2cn(newAt);
	}

	public String getCnUpdAt() {
		return TimeUtil.time2cn(updAt);
	}

	/**
	 * 获取指定格式字符串：今天14:25,昨天14:25,前天14:25
	 * 
	 * @return
	 */
	public String getDayMinutesNewAt() {
		if (newAt != null) {
			return TimeUtil.getDayMinutesByFormat(newAt);
		}
		return "";
	}

	@Override
	public Timestamp getNewAt() {
		return newAt;
	}

	@Override
	public void setNewAt(Timestamp newAt) {
		this.newAt = newAt;
	}

	@Override
	public Timestamp getUpdAt() {
		return updAt;
	}

	@Override
	public void setUpdAt(Timestamp updAt) {
		this.updAt = updAt;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPage() {
		if (page == null || page < 1) {
			return 1;
		}
		return page;
	}

	public void setPage(Integer page) {
		if (page == null || page < 1) {
			this.page = 1;
		} else {
			this.page = page;
		}
	}

	public Integer getSize() {
		if (size == null || size < 1) {
			return 10;
		}
		return size;
	}

	public void setSize(Integer size) {
		if (size == null || size < 1) {
			this.size = 10;
		}
		this.size = size;
	}

	public String getUserLoginToken() {
		return userLoginToken;
	}

	public void setUserLoginToken(String userLoginToken) {
		this.userLoginToken = userLoginToken;
	}

	public List<ImgDomain> getImgItems() {
		return imgItems;
	}

	public boolean isHasImg() {
		return hasImg;
	}

	public List<ImgDomain> getBigImgItems() {
		return bigImgItems;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}

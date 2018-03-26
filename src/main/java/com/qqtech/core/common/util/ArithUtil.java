
package com.qqtech.core.common.util;

import java.math.BigDecimal;

public class ArithUtil {
	// 默认除法运算精度
	private static final int DEF_DIV_SCALE = 10;

	/**
	 * 计算获取两个经纬度距离
	 * 
	 * @param lng1
	 * @param lat1
	 * @param lng2
	 * @param lat2
	 * @return
	 */
	public static double getDistance(double lng1, double lat1, double lng2, double lat2) {
		double radLat1 = Math.toRadians(lat1);
		double radLat2 = Math.toRadians(lat2);
		double a = radLat1 - radLat2;
		double b = Math.toRadians(lng1) - Math.toRadians(lng2);
		double s = 2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * 6378137.0;// 取WGS84标准参考椭球中的地球长半径(单位:m)
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	public static BigDecimal add(String v1, String v2) {
		if (StringUtil.isBlank(v1)) {
			v1 = "0";
		}
		if (StringUtil.isBlank(v2)) {
			v2 = "0";
		}
		return new BigDecimal(v1).add(new BigDecimal(v2));
	}

	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	public static BigDecimal sub(String v1, String v2) {
		if (StringUtil.isBlank(v1)) {
			v1 = "0";
		}
		if (StringUtil.isBlank(v2)) {
			v2 = "0";
		}
		return new BigDecimal(v1).subtract(new BigDecimal(v2));
	}

	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	public static BigDecimal mul(String v1, String v2) {
		if (StringUtil.isBlank(v1)) {
			v1 = "0";
		}
		if (StringUtil.isBlank(v2)) {
			v2 = "0";
		}
		return new BigDecimal(v1).multiply(new BigDecimal(v2));
	}

	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	public static BigDecimal div(String v1, String v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	public static double div(double v1, double v2) {
		return div(Double.toString(v1), Double.toString(v2), DEF_DIV_SCALE).doubleValue();
	}

	public static BigDecimal div(String v1, String v2, int scale) {
		if (scale < 0) {
			scale = DEF_DIV_SCALE;
		}
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			scale = DEF_DIV_SCALE;
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 取保留2位小数的金额数据
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @return 四舍五入后的结果
	 */
	public static double roundMoney(BigDecimal b) {
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}

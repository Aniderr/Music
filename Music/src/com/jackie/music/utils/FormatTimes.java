package com.jackie.music.utils;

/**
 * 
 * Class description： ---- 格式化歌曲时间
 * 
 * @author Jackie
 * 
 *         创建日期：2015 / 2015年6月15日
 * 
 */
public class FormatTimes {

	/**
	 * 
	 * Method description： ---- 格式化歌曲总时长 参数说明：
	 * 
	 * @param times
	 * @return
	 * 
	 *         返回类型：String
	 */
	public static String formatTotal(int times) {

		// 分
		int min = times / 60000;

		// 秒
		int mm = times % 60000 / 1000;

		// 格式化秒
		String mms = "";

		// 格式化分
		String mins = "";

		// 格式化分
		if (min < 10) {
			mins = "0" + min;
		} else {
			mins = (min + "").trim();
		}

		// 格式化秒
		if (mm < 10) {
			mms = "0" + mm;
		} else {
			mms = (mm + "").trim();
		}

		return mins + ":" + mms;
	}

	/**
	 * 
	 * Method description： ---- 格式化歌曲当前播放时长 参数说明：
	 * 
	 * @param times
	 * @return
	 * 
	 *         返回类型：String
	 */
	public static String formatCurrent(int times) {

		// 分
		int min = times / 60;

		// 秒
		int mm = times % 60;

		// 格式化秒
		String mms = "";

		// 格式化分
		String mins = "";

		// 格式化分
		if (min < 10) {
			mins = "0" + min;
		} else {
			mins = (min + "").trim();
		}

		// 格式化秒
		if (mm < 10) {
			mms = "0" + mm;
		} else {
			mms = (mm + "").trim();
		}

		return mins + ":" + mms;
	}
}

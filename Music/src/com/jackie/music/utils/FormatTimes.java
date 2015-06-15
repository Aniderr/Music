package com.jackie.music.utils;

/**
 * 
 * Class description�� ---- ��ʽ������ʱ��
 * 
 * @author Jackie
 * 
 *         �������ڣ�2015 / 2015��6��15��
 * 
 */
public class FormatTimes {

	/**
	 * 
	 * Method description�� ---- ��ʽ��������ʱ�� ����˵����
	 * 
	 * @param times
	 * @return
	 * 
	 *         �������ͣ�String
	 */
	public static String formatTotal(int times) {

		// ��
		int min = times / 60000;

		// ��
		int mm = times % 60000 / 1000;

		// ��ʽ����
		String mms = "";

		// ��ʽ����
		String mins = "";

		// ��ʽ����
		if (min < 10) {
			mins = "0" + min;
		} else {
			mins = (min + "").trim();
		}

		// ��ʽ����
		if (mm < 10) {
			mms = "0" + mm;
		} else {
			mms = (mm + "").trim();
		}

		return mins + ":" + mms;
	}

	/**
	 * 
	 * Method description�� ---- ��ʽ��������ǰ����ʱ�� ����˵����
	 * 
	 * @param times
	 * @return
	 * 
	 *         �������ͣ�String
	 */
	public static String formatCurrent(int times) {

		// ��
		int min = times / 60;

		// ��
		int mm = times % 60;

		// ��ʽ����
		String mms = "";

		// ��ʽ����
		String mins = "";

		// ��ʽ����
		if (min < 10) {
			mins = "0" + min;
		} else {
			mins = (min + "").trim();
		}

		// ��ʽ����
		if (mm < 10) {
			mms = "0" + mm;
		} else {
			mms = (mm + "").trim();
		}

		return mins + ":" + mms;
	}
}

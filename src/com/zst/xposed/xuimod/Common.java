package com.zst.xposed.xuimod;

import java.lang.reflect.Field;


public class Common {

	public static final String MY_PACKAGE_NAME = Common.class.getPackage().getName();

	/* Preference keys */
	public static final String KEY_SECONDS_ENABLE ="seconds_enable";
	public static final String KEY_SECONDS_BOLD ="seconds_bold";
	public static final String KEY_SECONDS_CUSTOM ="seconds_custom";
	
	public static final String KEY_LISTVIEW_INTERPOLATOR ="listview_interpolator";
	public static final String KEY_LISTVIEW_ANIMATION ="listview_animation";
	public static final String KEY_LISTVIEW_CACHE ="listview_cache";
	public static final String KEY_XYLON_ANIM ="xylon_anim";

	public static final String KEY_VOLUME_TIMEOUT ="volume_timeout";
	public static final String KEY_VOLUME_ALPHA ="volume_alpha";
	public static final String KEY_LOCKSCREEN_VOLUME ="lockscreen_volume";
	
	public static final String KEY_BATTERYBAR_ENABLE ="batterybar_enable";
	public static final String KEY_BATTERYBAR_ANIMATE ="batterybar_animate";
	public static final String KEY_BATTERYBAR_STYLE ="batterybar_style";
	public static final String KEY_BATTERYBAR_COLOR ="batterybar_color"; //TODO
	public static final String KEY_BATTERYBAR_HEIGHT ="batterybar_height";


	//public static final String KEY_LOCKSCREEN_WAKE ="lockscreen_wake";


	/* Preference default values */
	public static final boolean DEFAULT_SECONDS_ENABLE =false;
	public static final boolean DEFAULT_SECONDS_BOLD =false;
	public static final String DEFAULT_SECONDS_CUSTOM ="";
	
	public static final String DEFAULT_LISTVIEW_INTERPOLATOR = "0";
	public static final String DEFAULT_LISTVIEW_ANIMATION = "0";
	public static final String DEFAULT_LISTVIEW_CACHE = "0";
	public static final boolean DEFAULT_XYLON_ANIM =false;

	public static final String DEFAULT_VOLUME_TIMEOUT = "3000";
	public static final String DEFAULT_VOLUME_ALPHA = "100";
	public static final boolean DEFAULT_LOCKSCREEN_VOLUME =false;
	
	public static final boolean DEFAULT_BATTERYBAR_ENABLE = false;
	public static final boolean DEFAULT_BATTERYBAR_ANIMATE = false;
	public static final boolean DEFAULT_BATTERYBAR_STYLE = false;
	public static final String DEFAULT_BATTERYBAR_COLOR ="FF33B5E5";
	public static final String DEFAULT_BATTERYBAR_HEIGHT ="2";

	//public static final boolean DEFAULT_SIGNATURE_CHECK_DISABLE =false;

	/* Helper Methods */
	public static Object getReflection(Object itemToGetObject , String objectName){
		try {
		Class<?> clazz = itemToGetObject.getClass();
		Field field;
		field = clazz.getDeclaredField(objectName);
		field.setAccessible(true);
		return field.get(itemToGetObject);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/*
	 * @param return true when successful
	 */
	public static boolean setReflection(Object itemToGetObject , String objectName , Object newValue){
		try {
		Class<?> clazz = itemToGetObject.getClass();
		Field field;
		field = clazz.getDeclaredField(objectName);
		field.setAccessible(true);
		field.set(itemToGetObject, newValue);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
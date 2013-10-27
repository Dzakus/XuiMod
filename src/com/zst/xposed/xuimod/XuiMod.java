package com.zst.xposed.xuimod;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.zst.xposed.xuimod.mods.BatteryBarMod;
import com.zst.xposed.xuimod.mods.ListViewAnimationMod;
import com.zst.xposed.xuimod.mods.ListViewCacheMod;
import com.zst.xposed.xuimod.mods.LockscreenTorchMod;
import com.zst.xposed.xuimod.mods.LockscreenVolumeMod;
import com.zst.xposed.xuimod.mods.SecondsClockMod;
import com.zst.xposed.xuimod.mods.VolumePanelMod;
import com.zst.xposed.xuimod.mods.XylonAnimMod;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class XuiMod implements IXposedHookZygoteInit,IXposedHookLoadPackage,IXposedHookInitPackageResources{
	
	protected static final String TAG = XuiMod.class.getSimpleName();
	public static String MODULE_PATH = null;
	public static XSharedPreferences pref;
	
	public BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Common.ACTION_SETTINGS_CHANGED)) {
				handleSettingsChaned();
			}
		}
	};
	
	@Override
	public void initZygote(StartupParam startupParam) throws Throwable {
		MODULE_PATH = startupParam.modulePath;		
		pref = new XSharedPreferences(Common.MY_PACKAGE_NAME);
	}
	
	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {		
		pref.reload();
		
		hookLoadPreference();
		SecondsClockMod.handleLoadPackage(lpparam);
		LockscreenVolumeMod.handleLoadPackage(lpparam,pref);
		ListViewAnimationMod.handleLoadPackage(pref);
		VolumePanelMod.handleLoadPackage(lpparam,pref);
		LockscreenTorchMod.handleLoadPackage(lpparam,pref);
		ListViewCacheMod.handleLoadPackage(pref);
	}

	@Override
	public void handleInitPackageResources(InitPackageResourcesParam resparam) throws Throwable {
		pref.reload();
		if (pref.getBoolean(Common.KEY_XYLON_ANIM, Common.DEFAULT_XYLON_ANIM)) 
			XylonAnimMod.handleInitPackageResources(resparam);
		if (pref.getBoolean(Common.KEY_BATTERYBAR_ENABLE, Common.DEFAULT_BATTERYBAR_ENABLE)) 
			BatteryBarMod.initResources(resparam);
	}

	public void handleSettingsChaned() {
		pref.reload();
		ListViewAnimationMod.loadPreference();
	}

	private void hookLoadPreference() {
		XposedHelpers.findAndHookMethod(Application.class, "onCreate", new XC_MethodHook() {

			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				IntentFilter filter = new IntentFilter();
				filter.addAction(Common.ACTION_SETTINGS_CHANGED);
				((Application) param.thisObject).registerReceiver(mBroadcastReceiver, filter);
			}
		});
	}

}

package com.example.hook;

import java.util.List;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

@SuppressLint("NewApi")
public class WeChatHookService extends AccessibilityService {
	
	private final static String MM_CHAT_CLASSNAME = "com.tencent.mm.ui.LauncherUI"; //微信聊天頁面
	private final static String MM_HOOK_CLASSNAME = "com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI"; //微信红包页面
	private final static String MM_DETAILS_CLASSNAME = "com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI";//红包详细界面
	
	private final static String MQ_CHAT_CLASSNAME = "com.tencent.mobileqq.activity.SplashActivity";
	private final static String MQ_DETAILS_CLASSNAME = "cooperation.qwallet.plugin.QWalletPluginProxyActivity";

	
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, START_STICKY, startId);
	}
	
	@Override
	public void onInterrupt() {
	}
	
	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
		if (event == null)
			return;
		if (event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
			List<CharSequence> texts = event.getText();
			for (CharSequence t : texts) {
				if (t.toString().contains("[微信红包]")) {
					handleNotification(event);
					break;
				}
			}
		}else if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
			if (MM_CHAT_CLASSNAME.equals(event.getClassName())) {
				// 在聊天界面,去点中红包
				checkHookInWeChat();
			} else if (MM_HOOK_CLASSNAME.equals(event.getClassName())) {
				// 点中了红包，下一步就是去拆红包
				performWeChatHookBtnClickAction();
			} else if (MM_DETAILS_CLASSNAME.equals(event.getClassName())) {
				// 拆完红包后看详细的纪录界面
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		}else{
			checkHookInQq();
		}
	}
	
	private void handleNotification(AccessibilityEvent event) {
		try {
			Notification notification = (Notification) event.getParcelableData();
			PendingIntent pendingIntent = notification.contentIntent;
			pendingIntent.send();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void performWeChatHookBtnClickAction() {
		AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
		if (nodeInfo == null) {
			return;
		}
		for(int i=0; i< nodeInfo.getChildCount(); i++){
			nodeInfo.getChild(i).performAction(AccessibilityNodeInfo.ACTION_CLICK);
		}
	}
	
	private void checkHookInQq(){
		AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
		if (nodeInfo == null) {
			return;
		}
		
		List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText("点击拆开");
		if (list.isEmpty()) {
			return;
		} else {
			// 最新的红包领起
			for (int i = list.size() - 1; i >= 0; i--) {
				AccessibilityNodeInfo parent = list.get(i).getParent();
				try {
					parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void checkHookInWeChat() {
		AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
		if (nodeInfo == null) {
			return;
		}
		
		List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText("领取红包"); // 找到聊天界面中包含 领取红包字符的控件
		if (list.isEmpty()) {
			list = nodeInfo.findAccessibilityNodeInfosByText("[微信红包]");
			for (AccessibilityNodeInfo n : list) {
				n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
				break;
			}
		} else {
			// 最新的红包领起
			for (int i = list.size() - 1; i >= 0; i--) {
				AccessibilityNodeInfo parent = list.get(i).getParent();
				try {
					parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}

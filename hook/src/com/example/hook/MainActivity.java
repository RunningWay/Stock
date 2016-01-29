package com.example.hook;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	Button qidong;
	TextView info;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		startService(new Intent(getApplicationContext(), WeChatHookService.class));
		
		findViewById(R.id.startSetting).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
			}
		});
		
//		qidong=(Button) findViewById(R.id.qidong);
//		info=(TextView) findViewById(R.id.info);
//		qidong.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(getApplicationContext(), "请打开辅助功能", 1).show();
//				startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
//			}
//		});
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
//		if (isAccessibilitySettingsOn(getApplicationContext())) {
//			qidong.setText("请打开辅助功能");
//		}else{
//			info.setText("");
//		}
	}
	
	// To check if service is enabled
	public boolean isAccessibilitySettingsOn(Context context) {
			int accessibilityEnabled = 0;
			final String service = context.getPackageName() + "/" + WeChatHookService.class.getName();
			boolean accessibilityFound = false;
			try {
				accessibilityEnabled = Settings.Secure.getInt(context.getApplicationContext().getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
			} catch (SettingNotFoundException e) {
			}
			TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');
			if (accessibilityEnabled == 1) {
				String settingValue = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
				if (settingValue != null) {
					TextUtils.SimpleStringSplitter splitter = mStringColonSplitter;
					splitter.setString(settingValue);
					while (splitter.hasNext()) {
						String accessabilityService = splitter.next();
						if (accessabilityService.equalsIgnoreCase(service)) {
							return true;
						}
					}
				}
			} else {
			}
			return accessibilityFound;
		}
}

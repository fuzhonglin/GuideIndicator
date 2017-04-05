package com.example.guidetest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GuideIndicator guide = (GuideIndicator) findViewById(R.id.guide);
        
        guide.setPointColor(0xffff0000, 0xffcccccc);
    	guide.setButtonBackgroud(R.drawable.button_red_pressed, R.drawable.button_red_normal);
    	guide.setButtonText("立即体验");
    	guide.setButtonTextColor(0xff000000, 0xffffffff);
    	guide.setButtonPadding(20, 0, 20, 0);
    	
    	int[] resIds = {R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
    	guide.setPagerRes(resIds);
	
    	guide.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("按钮被点击了");			
			}
		});
    }
    
}

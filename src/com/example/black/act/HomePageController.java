package com.example.black.act;

import android.app.ActionBar;
import android.view.View;

/**
 *	…Ë÷√actionbar
 * @author admin
 *
 */
public class HomePageController {
	public void setActionbar(View view,ActionBar actionBar){
		actionBar.setCustomView(view);
		actionBar.setDisplayShowTitleEnabled(false);
	    actionBar.setDisplayUseLogoEnabled(false);
	    actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);  
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.show();
	}
}

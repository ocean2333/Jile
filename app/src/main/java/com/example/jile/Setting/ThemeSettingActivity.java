package com.example.jile.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jile.LogoActivity;
import com.example.jile.MainView.MainActivity;
import com.example.jile.R;

import static com.example.jile.Constant.Constants.THEME_BLUE;
import static com.example.jile.Constant.Constants.THEME_GREEN;
import static com.example.jile.Constant.Constants.THEME_PINK;
import static com.example.jile.Constant.Constants.THEME_RED;
import static com.example.jile.Constant.Constants.THEME_WHITE;
import static com.example.jile.Constant.Constants.THEME_YELLOW;

public class ThemeSettingActivity extends AppCompatActivity {
    public static String theme;
    public static Resources.Theme t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setActivityTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_setting);
        LinearLayout rootLL = findViewById(R.id.rootLL);

        for(int i=2;i<8;i++){
            LinearLayout ll = (LinearLayout) rootLL.getChildAt(i);
            ImageView iv = (ImageView) ll.getChildAt(2);
            if(theme.equals(((TextView)ll.getChildAt(1)).getText().toString()))
                ((ImageView)((LinearLayout)ll).getChildAt(2)).setImageResource(R.drawable.ic_check);
            ll.setOnClickListener(v->{
                for(int j=2;j<8;j++){
                    ((ImageView)((LinearLayout)v).getChildAt(2)).setImageResource(R.color.white);
                }
                ((ImageView)((LinearLayout)v).getChildAt(2)).setImageResource(R.drawable.ic_check);
                theme = ((TextView)ll.getChildAt(1)).getText().toString();
                LogoActivity.sp.edit().putString("theme",theme).apply();
                recreate();
                t = getTheme();
            });
        }
    }

    /**
     * 设置该acitivity的主题与选项主题一致
     * 需要在setContentView前调用
     * @param activity 即this
     * */
    public static void setActivityTheme(Activity activity){
        switch (theme){
            case THEME_WHITE:
                activity.setTheme(R.style.myAppThemeWhite);
                break;
            case THEME_RED:
                activity.setTheme(R.style.myAppThemeRed);
                break;
            case THEME_BLUE:
                activity.setTheme(R.style.myAppThemeBlue);
                break;
            case THEME_PINK:
                activity.setTheme(R.style.myAppThemePink);
                break;
            case THEME_GREEN:
                activity.setTheme(R.style.myAppThemeGreen);
                break;
            case THEME_YELLOW:
                activity.setTheme(R.style.myAppThemeYellow);
                break;
            default:
                activity.setTheme(R.style.myAppThemeYellow);
        }
        if( activity instanceof MainActivity){
            ((MainActivity) activity).theme = theme;
        }
        if( activity instanceof SettingActivity){
            ((SettingActivity) activity).theme = theme;
        }
    }


}
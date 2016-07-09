package sample.coordinatebehavior.hyunji.introimagedownload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import java.io.File;
import java.util.Random;

/**
 * Created by hyunji on 16. 7. 9..
 */
public class IntroActivity extends AppCompatActivity {

    private LinearLayout introLayout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_intro);

        introLayout = (LinearLayout) findViewById(R.id.intro_bg);
        //LinearLayout mIntroScreen2 = (LinearLayout) findViewById(R.id.intro_bg);

        String localImgPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/.scshp/"; // 다운 잘되면 이걸로 불러와야 한다.
        File file = new File(localImgPath);
        String str;
        int num = 0;
        if (new File( localImgPath + "app_intro_01.png").exists()) {
            int imgCount = file.listFiles().length; // 파일 총 갯수 얻어오기
            Bitmap[] map = new Bitmap[imgCount];
            for (File f : file.listFiles()) {
                str = f.getName(); // 파일 이름 얻어오기
                map[num] = BitmapFactory.decodeFile(localImgPath + str);
                num++;
            }
            Random ran = new Random();
            Bitmap img = map[ran.nextInt(imgCount)];

            Drawable d = new BitmapDrawable(getResources(), img);
            introLayout.setBackground(d);
            introLayout.setVisibility(View.VISIBLE);
            //
        } else {
            // Default 배경 이미지 보여줌
            introLayout.setVisibility(View.VISIBLE);
        }


    }




}

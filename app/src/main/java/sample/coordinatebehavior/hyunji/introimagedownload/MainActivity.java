package sample.coordinatebehavior.hyunji.introimagedownload;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private String File_Name1 = "app_intro_01.png";
    private String File_Name2 = "app_intro_02.png";
    private String File_Name3 = "app_intro_03.png";//확장자를 포함한 파일명

    private String savePath; //앱안에 있는 곳에 다운하고 그 경로

    // 삼성카드 상용 이지지 URL
    final String[] imageUrlList = { "https://static22.samsungcard.com/images/shopping/mo/app/app_intro_01.png",
            "https://static22.samsungcard.com/images/shopping/mo/app/app_intro_02.png",
            "https://static22.samsungcard.com/images/shopping/mo/app/app_intro_03.png"};
    final String[] imgFileNameList = {File_Name1, File_Name2,File_Name3};
    private DownloadThread dThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void commonAppUpdate( final String imgversion) {

        // 이미지 버전 체크하고
        String imgVer;
        if (imgversion != null) {
            //이미지 버전 초기값
            imgVer = "0001";
            //SHPApplication.getInstance().getDao().getIntroimgVersion();
            if (imgVer.equals(imgversion)) {
                // 버전같으면 그냥 다운되어있던 이미지들을 랜덤으로 보여준다.

                if (new File(savePath + File_Name1).exists() == false
                        || new File(savePath + File_Name2).exists() == false
                        || new File(savePath + File_Name3).exists() == false) {

                    for (int i = 0; i < imageUrlList.length; i++) {
                        dThread = new DownloadThread(imageUrlList[i], imgFileNameList[i]);
                        // + imgFileNameList[i]
                        dThread.start();
                    }
                }
                // 보여준다
                // showloadFile();

            } else {
                // 이미지 버전 저장
                //SHPApplication.getInstance().getDao().setIntroImgVersion(imgversion);
                String saveImgVer = "0002";
                //SHPApplication.getInstance().getDao().getIntroimgVersion();
                if (!imgVer.equals(saveImgVer)) {

                    // Environment.getExternalStorageDirectory()+ folderName;
                    if (new File(savePath + File_Name1).exists() == false
                            || new File(savePath + File_Name2).exists() == false
                            || new File(savePath + File_Name3).exists() == false) {

                        for (int i = 0; i < imageUrlList.length; i++) {
                            dThread = new DownloadThread(imageUrlList[i], imgFileNameList[i]);
                            // + imgFileNameList[i]
                            dThread.start();
                        }
                    }
                }

            }
        } else {
            Log.d("TEST", "image aleady exist !!");
            // 끝난시점 콜백받도록

        }
    }


    // 다운로드 쓰레드로 돌림..
    public class DownloadThread extends Thread {

        private String ServerUrl;
        private String FileName;


        public DownloadThread(String serverPath, String localPath) {
            ServerUrl = serverPath;
            FileName = localPath;
        }

        @Override
        public void run() {
            URL imgurl;
            int Read;

            try {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                File imgPath = new File(path + "/.scshp");
                if (!imgPath.isDirectory()) {
                    imgPath.mkdirs();
                }

                imgurl = new URL(ServerUrl);
                HttpURLConnection conn = (HttpURLConnection) imgurl.openConnection();
                conn.setDoInput(true);
                conn.connect();

                int len = conn.getContentLength();
                byte[] tmpByte = new byte[len];
                InputStream is = conn.getInputStream();

                FileOutputStream fos = new FileOutputStream(path + "/.scshp/"+ FileName);
                for (; ; ) {
                    Read = is.read(tmpByte);
                    if (Read <= 0) {
                        break;
                    }
                    fos.write(tmpByte, 0, Read);
                }
                is.close();
                fos.close();
                conn.disconnect();

            } catch (MalformedURLException e) {
                Log.e("ERROR1", e.getMessage());
            } catch (IOException e) {
                Log.e("ERROR2", e.getMessage());
                e.printStackTrace();
            }
        }
    }
}

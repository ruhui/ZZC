package com.zzcar.zzc.networks;

import android.os.AsyncTask;

import com.zzcar.zzc.interfaces.ImageUploadListener;
import com.zzcar.zzc.utils.FileUtil;
import com.zzcar.zzc.utils.Tool;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/4.
 */

public class UploadFileWithoutLoding extends AsyncTask<String, Integer, String> {

    private ImageUploadListener uploadListener;

    public UploadFileWithoutLoding(ImageUploadListener uploadListener) {
        this.uploadListener = uploadListener;
    }

    @Override
    protected void onPostExecute(String result) {
        //最终结果的显示
        uploadListener.finishLoading(result, 0);
    }

    @Override
    protected void onPreExecute() {
        //开始前的准备工作
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        //显示进度
    }

    @Override
    protected String doInBackground(String... params) {
        //这里params[0]和params[1]是execute传入的两个参数
        String filePath = params[0];

        String newfilePath = FileUtil.scal(filePath);

        String uploadUrl = params[1];
        //下面即手机端上传文件的代码
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "******";
        try {
            URL url = new URL(uploadUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(6*1000);
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + end);
            dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\""
                    + newfilePath.substring(newfilePath.lastIndexOf("/") + 1)
                    + "\"" + end);
            dos.writeBytes(end);

            //获取文件总大小
            FileInputStream fis = new FileInputStream(newfilePath);
            //文件总长
            long total = fis.available();
            byte[] buffer = new byte[1024]; // 8k
            int count = 0;
            int length = 0;
            while ((count = fis.read(buffer)) != -1) {
                dos.write(buffer, 0, count);
                //获取进度，调用publishProgress()
                length += count;
                publishProgress((int) ((length / (float) total) * 100));
            }
            fis.close();
            dos.writeBytes(end);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
            dos.flush();

            InputStream is = httpURLConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            @SuppressWarnings("unused")
            String result = br.readLine();
            ResponseParent respon = Tool.parseJsonWithGson(result, ResponseParent.class);
            dos.close();
            is.close();
            return (String)respon.data;
        }catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}

package cn.hophin.shfy.androidinfo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/5/20.
 */
public class CMDExecute {
    public synchronized String run(String[] cmd, String workdirectory) throws IOException{
        String result = "";
        try {
            ProcessBuilder builder = new ProcessBuilder(cmd);
            if (workdirectory != null)
                builder.directory(new File(workdirectory));
            builder.redirectErrorStream(true);
            Process process = builder.start();
            InputStream inputStream = process.getInputStream();
            byte[] bytes = new byte[1024];
            while (inputStream.read(bytes) != -1) {
                result = result + new String(bytes);
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}

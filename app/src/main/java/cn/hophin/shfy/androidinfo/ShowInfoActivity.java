package cn.hophin.shfy.androidinfo;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.widget.TextView;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class ShowInfoActivity extends AppCompatActivity {
    public static final String POSITION = "cn.hophin.shfy.androidinfo.position";
    private Context context;

    public ShowInfoActivity() {
        this.context = this;
    }

    ProgressDialog progressDialog;
    private TextView titleTextView;
    private TextView infoTextView;
    private String[] properties = {
            "java.home",
            "java.version",
            "java.vendor",
            "java.vendor.url",
            "java.class.path",
            "java.class.version",
            "user.home",
            "user.name",
            "user.dir",
            "os.version",
            "os.name",
            "os.arch",
            "line.separator",
            "file.separator",
            "path.separator"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titleTextView = (TextView) findViewById(R.id.title);
        infoTextView = (TextView) findViewById(R.id.info);

        Intent intent = getIntent();
        int position = intent.getIntExtra(POSITION, -1);
        String activityName = intent.getStringExtra(Intent.EXTRA_TITLE);
        if (position != -1) {
            if (activityName.equals(SystemActivity.class.getSimpleName())) {
                switch (position) {
                    case 0:
                        infoTextView.setText(fetchVersionInfo());
                        titleTextView.setText("操作系统版本");
                        break;
                    case 1:
                        titleTextView.setText("系统信息");
                        infoTextView.setText(getSystemProperty(properties));
                        break;
                    case 2:
                        titleTextView.setText("运营商信息");
                        infoTextView.setText(fetchTelStatus(context));
                        break;
                }
            }
            if (activityName.equals(HardwareActivity.class.getSimpleName())) {
                switch (position) {
                    case 0:
                        titleTextView.setText("CPU信息");
                        infoTextView.setText(fetchCPUInfo());
                        break;
                    case 1:
                        titleTextView.setText("内存信息");
                        infoTextView.setText(fetchMemoryInfo());
                        break;
                    case 2:
                        titleTextView.setText("硬盘信息");
                        infoTextView.setText(fetchDiskInfo());
                        break;
                    case 3:
                        titleTextView.setText("网络信息");
                        infoTextView.setText(fetchNetInfo());
                        break;
                    case 4:
                        titleTextView.setText("显示屏信息");
                        infoTextView.setText(getDisplayMetrics(context));
                        break;

                }
            }
            if (activityName.equals(RuningActivity.class.getSimpleName())) {
                switch (position) {
                    case 0:
                        titleTextView.setText("正在运行的service");
                        infoTextView.setText(getRunningServicesInfo(context));
                        break;
                    case 1:
                        titleTextView.setText("运行的Task");
                        infoTextView.setText(getRunningTasksInfo(context));
                        break;
                    case 2:
                        titleTextView.setText("进程信息");
                        progressDialog=ProgressDialog.show(
                                context,
                                "请稍候...",
                                "正在获取进程信息，请稍候...",
                                true,
                                false);
                        //设置返回键可以结束对话框
                        progressDialog.setCancelable(true);
                        new fetchProcessInfoTask().execute();
//                        infoTextView.setText(fetchProcessInfo());
                        break;
                }
            }
        }

    }

    /**
     * 获取版本信息
     *
     * @return
     */
    private String fetchVersionInfo() {
        String result = "";
        CMDExecute cmdExecute = new CMDExecute();
        try {
            String[] args = {"/system/bin/cat", "/proc/version"};
            result = cmdExecute.run(args, "/system/bin/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 获取CPU信息
     *
     * @return
     */
    private String fetchCPUInfo() {
        String result = "";
        CMDExecute cmdExecute = new CMDExecute();
        try {
            String[] args = {"/system/bin/cat", "/proc/cpuinfo"};
            result = cmdExecute.run(args, "/system/bin/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 获取内存信息
     *
     * @return
     */
    private String fetchMemoryInfo() {
        String result = "";
        CMDExecute cmdExecute = new CMDExecute();
        try {
            String[] args = {"/system/bin/cat", "/proc/meminfo"};
            result = cmdExecute.run(args, "/system/bin/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 获取硬盘信息
     *
     * @return
     */
    private String fetchDiskInfo() {
        String result = "";
        CMDExecute cmdExecute = new CMDExecute();
        try {
            String[] args = {"/system/bin/df"};
            result = cmdExecute.run(args, "/system/bin/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取网络信息
     *
     * @return
     */
    private String fetchNetInfo() {
        String result = "";
        CMDExecute cmdExecute = new CMDExecute();
        try {
            String[] args = {"/system/bin/netcfg"};
            result = cmdExecute.run(args, "/system/bin/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 获取系统信息：通过调用 System.getProperty(String propertyName)
     *
     * @param properties:需要获取的properties
     * @return
     */
    private String getSystemProperty(String[] properties) {
        StringBuffer buffer = new StringBuffer();
        for (String property : properties) {
            buffer.append(property)
                    .append(":")
                    .append(System.getProperty(property))
                    .append("\n");
        }
        return buffer.toString();
    }

    /**
     * 获取运营商信息
     * 通过Context.getSystemService(Context.TELEPHONY_SERVICE)
     * 获取TelephonyManager对象
     * 通过TelephonyManager.getXXX()获取相关信息
     *
     * @param context
     * @return
     */
    private String fetchTelStatus(Context context) {
        TelephonyManager telephonyManager =
                (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String result = String.format(
                "DeviceId(IMEI)=%s\n" +
                        "DeviceSoftwareVersion=%s\n" +
                        "Line1Number=%s\n" +
                        "NetworkCountryIso=%s\n" +
                        "NetworkOperator=%s\n" +
                        "NetworkOperatorName=%s\n" +
                        "NetworkType=%d\n" +
                        "SimCountryIso=%s\n" +
                        "SimOperator=%s\n" +
                        "SimOperatorName=%s\n" +
                        "SimSerialNumber=%s\n" +
                        "SimState=%d\n" +
                        "SubscriberId=%s\n" +
                        "VoiceMailAlphaTag=%s\n" +
                        "VoiceMailNumber=%s\n" +
                        "IMSI MCC(Mobile Country Code):%d\n" +
                        "IMSI MNC(Mobile Network Code):%d",
                telephonyManager.getDeviceId(),
                telephonyManager.getDeviceSoftwareVersion(),
                telephonyManager.getLine1Number(),
                telephonyManager.getNetworkCountryIso(),
                telephonyManager.getNetworkOperator(),
                telephonyManager.getNetworkOperatorName(),
                telephonyManager.getNetworkType(),
                telephonyManager.getSimCountryIso(),
                telephonyManager.getSimOperator(),
                telephonyManager.getSimOperatorName(),
                telephonyManager.getSimSerialNumber(),
                telephonyManager.getSimState(),
                telephonyManager.getSubscriberId(),
                telephonyManager.getVoiceMailAlphaTag(),
                telephonyManager.getVoiceMailNumber(),
                context.getResources().getConfiguration().mcc,
                context.getResources().getConfiguration().mnc);
        return result;
    }

    /**
     * 获取显示屏信息
     */
    private String getDisplayMetrics(Context c) {
        String str = "";
        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics = c.getApplicationContext().getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        float density = displayMetrics.density;
        float xdpi = displayMetrics.xdpi;
        float ydpi = displayMetrics.ydpi;
        str = String.format(
                "The absolute width:%dpixels\n" +
                        "The absolute heightin:%dpixels\n" +
                        "The logical density of the display:%f\n" +
                        "X dimension:%fpixels per inch\n" +
                        "Y dimension:%fpixels per inch",
                screenWidth,
                screenHeight,
                density,
                xdpi,
                ydpi
        );
        return str;
    }

    /**
     * 获取正在运行的sevice信息
     */
    private String getRunningServicesInfo(Context context) {
        String str = "";
        StringBuffer buffer=new StringBuffer();
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceInfos
                = manager.getRunningServices(100);
        Iterator<ActivityManager.RunningServiceInfo> iterator
                = serviceInfos.iterator();
        while (iterator.hasNext()) {
            ActivityManager.RunningServiceInfo info = iterator.next();
            str = String.format(
                    "pid:%d\n" +
                            "process:%s\n" +
                            "service:%s\n" +
                            "crashCount:%d\n" +
                            "clientCount:%d\n\n",
                    info.pid,
                    info.process,
                    info.service.toShortString(),
                    info.crashCount,
                    info.clientCount
            );
            buffer.append(str);
        }
        return buffer.toString();
    }

    /**
     * 获取正在运行的tasks信息
     */
    private String getRunningTasksInfo(Context context) {
        StringBuffer buffer=new StringBuffer();
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfos
                = manager.getRunningTasks(100);
        Iterator<ActivityManager.RunningTaskInfo> iterator
                = taskInfos.iterator();
        while (iterator.hasNext()) {
            ActivityManager.RunningTaskInfo info = iterator.next();
            String str = String.format(
                    "id:%d\n" +
                            "baseActivity:%s\n" +
                            "numActivities:%d\n" +
                            "numRunning:%d\n\n",
                    info.id,
                    info.baseActivity.toShortString(),
                    info.numActivities,
                    info.numRunning
            );
            buffer.append(str);
        }
        return buffer.toString();
    }
    /**
     * 获取正在运行的进程信息
     */
    private String fetchProcessInfo(){
        String result="";
        CMDExecute execute=new CMDExecute();
        try {
            String[] args={"/system/bin/top","-n","1"};
            result=execute.run(args,"system/bin/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 异步获取正在运行的进程信息
     */
    public class fetchProcessInfoTask extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... params) {
            String result="";
            CMDExecute execute=new CMDExecute();
            try {
                String[] args={"/system/bin/top","-n","1"};
                result=execute.run(args,"system/bin/");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            infoTextView.setText(s);
            progressDialog.dismiss();
            super.onPostExecute(s);
        }
    }
}

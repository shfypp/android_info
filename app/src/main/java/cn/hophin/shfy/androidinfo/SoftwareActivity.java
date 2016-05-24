package cn.hophin.shfy.androidinfo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SoftwareActivity extends AppCompatActivity implements Runnable{
    private Context context;

    public SoftwareActivity() {
        this.context = this;
    }

    private static final String NAME = "name";
    private static final String DESC = "desc";
    private ListView itemList;
    private SimpleAdapter simpleAdapter;
    private List<Map<String, Object>> mapList;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_software);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        itemList=(ListView) findViewById(R.id.item_list);

        progressDialog=ProgressDialog.show(
                context,
                "请稍候...",
                "正在收集您已安装的软件信息...",
                true,false);
        Thread thread=new Thread(this);
        thread.start();

    }

    @Override
    public void run() {
        fetchInstalledApps();
        handler.sendEmptyMessage(0);
    }

    private Handler handler=new Handler(){
        public void handleMessage(Message message){
            refreshListItems();
            progressDialog.dismiss();
        }
    };

    private void refreshListItems() {
        mapList=fetchInstalledApps();
        simpleAdapter=new SimpleAdapter(
                context,mapList,R.layout.item_row,
                new String[]{NAME,DESC},
                new int[]{R.id.name,R.id.desc}
        );
        itemList.setAdapter(simpleAdapter);
        setTitle(String.format("软件信息，已安装%d款应用.",mapList.size()));
    }

    private List<Map<String, Object>> fetchInstalledApps() {
        List<ApplicationInfo> packages=getPackageManager().getInstalledApplications(0);
        mapList=new ArrayList<Map<String,Object>>(packages.size());
        Iterator<ApplicationInfo> iterator=packages.iterator();
        while (iterator.hasNext()){
            Map<String,Object> map=new HashMap<String,Object>();
            ApplicationInfo info=iterator.next();
            String packageName=info.packageName;
            String label="";

            label=getPackageManager()
                    .getApplicationLabel(info)
                    .toString();
            map.put(NAME,packageName);
            map.put(DESC,label);
            mapList.add(map);
        }
        return mapList;
    }
}

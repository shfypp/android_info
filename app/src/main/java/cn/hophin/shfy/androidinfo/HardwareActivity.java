package cn.hophin.shfy.androidinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HardwareActivity extends AppCompatActivity {
    private Context context;

    public HardwareActivity() {
        this.context = this;
    }

    private static final String NAME = "name";
    private static final String DESC = "desc";
    private ListView itemList;
    private SimpleAdapter simpleAdapter;
    private List<Map<String, Object>> mapList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hardware);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        itemList=(ListView) findViewById(R.id.item_list);
        mapList=buildListForSimpleAdapter();
        simpleAdapter=new SimpleAdapter(
                context,
                mapList,
                R.layout.item_row,
                new String[]{NAME,DESC},
                new int[]{R.id.name,R.id.desc}
        );
        itemList.setAdapter(simpleAdapter);
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(context, ShowInfoActivity.class);
                intent.putExtra(Intent.EXTRA_TITLE,HardwareActivity.class.getSimpleName());
                intent.putExtra(ShowInfoActivity.POSITION, position);
                startActivity(intent);
            }
        });
    }

    private List<Map<String, Object>> buildListForSimpleAdapter() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(NAME, "CPU信息");
        map.put(DESC, "获取设备的CPU信息");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put(NAME, "内存信息");
        map.put(DESC, "手机设备的内存信息");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put(NAME, "硬盘信息");
        map.put(DESC, "获取设备的硬盘信息");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put(NAME, "网络信息");
        map.put(DESC, "获取设备的网络信息");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put(NAME, "显示屏信息");
        map.put(DESC, "获取设备显示屏信息");
        list.add(map);



        return list;

    }

}

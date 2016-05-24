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

public class SystemActivity extends AppCompatActivity {
    private Context context;

    public SystemActivity() {
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
        setContentView(R.layout.activity_system);
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
                Intent intent=new Intent();
                intent.setClass(context,ShowInfoActivity.class);
                intent.putExtra(ShowInfoActivity.POSITION, position);
                intent.putExtra(Intent.EXTRA_TITLE,SystemActivity.class.getSimpleName());
                startActivity(intent);
            }
        });
    }

    private List<Map<String, Object>> buildListForSimpleAdapter() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(NAME, "操作系统版本");
        map.put(DESC, "读取/proc/version信息");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put(NAME, "系统信息");
        map.put(DESC, "手机设备的系统信息");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put(NAME, "运营商信息");
        map.put(DESC, "手机网络的运营商信息");
        list.add(map);

        return list;

    }


}

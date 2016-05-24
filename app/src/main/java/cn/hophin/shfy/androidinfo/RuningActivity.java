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

public class RuningActivity extends AppCompatActivity {
    private Context context;

    public RuningActivity() {
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
        setContentView(R.layout.activity_runing);
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
                intent.putExtra(Intent.EXTRA_TITLE, RuningActivity.class.getSimpleName());
                intent.putExtra(ShowInfoActivity.POSITION, position);
                startActivity(intent);
            }
        });
    }
    private List<Map<String, Object>> buildListForSimpleAdapter() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(NAME, "运行的service");
        map.put(DESC, "获取正在运行的后台服务");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put(NAME, "运行的Task");
        map.put(DESC, "获取正在运行的任务");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put(NAME, "进程信息");
        map.put(DESC, "获取正在运行的程序");
        list.add(map);

        return list;

    }



}

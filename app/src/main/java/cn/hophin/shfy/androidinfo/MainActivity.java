package cn.hophin.shfy.androidinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public MainActivity() {
        this.context = this;
    }

    private Context context;
    private ListView itemList;
    private List<Map<String, Object>> mapList;

    private final String NAME = "name";
    private final String DESC = "desc";
    private final String IMAGE = "img";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        itemList = (ListView) findViewById(R.id.item_list);
        mapList = buildListForSimpleAdapter();
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                mapList,
                R.layout.item_row,
                new String[]{NAME, DESC, IMAGE},
                new int[]{R.id.name, R.id.desc, R.id.img}
        );
        itemList.setAdapter(simpleAdapter);
        itemList.setSelection(0);
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        startActivity(new Intent(context,SystemActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(context,HardwareActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(context,SoftwareActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(context,RuningActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(context,FSExploreActivity.class));
                        break;

                }
            }
        });
    }

    private List<Map<String, Object>> buildListForSimpleAdapter() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(NAME, "系统信息");
        map.put(DESC, "查看设备系统版本，运营商及系统信息");
        map.put(IMAGE, R.drawable.ic_perm_device_information_black_24dp);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put(NAME, "硬件信息");
        map.put(DESC, "查看包括CPU、硬盘、内存等信息");
        map.put(IMAGE, R.drawable.ic_memory_black_24dp);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put(NAME, "软件信息");
        map.put(DESC, "查看已安装的软件信息");
        map.put(IMAGE, R.drawable.ic_apps_black_24dp);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put(NAME, "运行时信息");
        map.put(DESC, "查看设备运行时的信息");
        map.put(IMAGE, R.drawable.ic_compare_arrows_black_24dp);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put(NAME, "文件浏览器");
        map.put(DESC, "浏览查看文件系统");
        map.put(IMAGE, R.drawable.ic_folder_open_black_24dp);
        list.add(map);





        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

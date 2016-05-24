package cn.hophin.shfy.androidinfo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FSExploreActivity extends AppCompatActivity {
    private Context context;

    private ListView itemList;
    private SimpleAdapter simpleAdapter;
    private List<Map<String, Object>> mapList;

    private static final String NAME = "name";
    private static final String DESC = "desc";
    private static final String IMG = "img";
    private String path = "/";

    public FSExploreActivity() {
        this.context = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fsexplore);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        itemList = (ListView) findViewById(R.id.item_list);
        refreshListItems("/");

    }

    private void refreshListItems(final String filePath) {
        mapList = buildListForSimpleAdapter(filePath);
        simpleAdapter = new SimpleAdapter(
                context,
                mapList,
                R.layout.item_row,
                new String[]{NAME, DESC, IMG},
                new int[]{R.id.name, R.id.desc, R.id.img}
        );
        itemList.setAdapter(simpleAdapter);
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    path = "/";
                    refreshListItems(path);
                } else if (position == 1) {
                    goToParent();
                } else {
                    path = (String) mapList.get(position).get(DESC);
                    File file = new File(path);
                    if (file.isDirectory()) {
                        if (new File(path).listFiles() != null) {
                            refreshListItems(path);
                        } else {
                            Toast.makeText(context,"没有权限",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    private List<Map<String, Object>> buildListForSimpleAdapter(String filePath) {
        File[] files = new File(filePath).listFiles();
        if (files != null) {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(NAME, "/");
            map.put(DESC, "根目录");
            map.put(IMG, R.drawable.ic_insert_drive_file_black_24dp);
            list.add(map);

            map = new HashMap<String, Object>();
            map.put(NAME, "..");
            map.put(DESC, "返回上层目录");
            map.put(IMG, R.drawable.ic_reply_black_24dp);
            list.add(map);

            for (File file : files) {
                map = new HashMap<String, Object>();
                if (file.isDirectory()) {
                    map.put(IMG, R.drawable.ic_folder_open_black_24dp);
                }else {
                    map.put(IMG,R.drawable.ic_short_text_black_24dp);
                }
                map.put(NAME, file.getName());
                map.put(DESC, file.getPath());
                list.add(map);

            }
            return list;
        }
        return null;


    }

    private void goToParent() {
        File file = new File(path);
        File fileParent = file.getParentFile();
        if (fileParent == null) {
            Toast.makeText(context, "已经是根目录，没有上级目录了", Toast.LENGTH_SHORT).show();
        } else {
            path = fileParent.getAbsolutePath();
            refreshListItems(path);
        }
    }

}

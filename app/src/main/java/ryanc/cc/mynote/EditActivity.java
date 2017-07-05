package ryanc.cc.mynote;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ryanc.cc.mynote.adapter.NoteListAdapter;
import ryanc.cc.mynote.bean.NoteBean;
import ryanc.cc.mynote.db.DatabaseHelper;

public class EditActivity extends AppCompatActivity {

    private List<NoteBean> mNoteBeanList;
    private DatabaseHelper mDatabaseHelper;
    private NoteListAdapter adapter;
    private ListView noteList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //系统写的 鬼知道是啥
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //获取控件数据
        final EditText title = (EditText) findViewById(R.id.et_note_title);
        final EditText content = (EditText) findViewById(R.id.et_note_content);

        mDatabaseHelper = new DatabaseHelper(this);
        mNoteBeanList = new ArrayList<>();
        adapter = new NoteListAdapter(this,mNoteBeanList);

        //保存按钮单击事件
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //非空验证
                if(title.length()==0){
                    Snackbar.make(view, "请输入便签标题！", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                if(content.length()==0){
                    Snackbar.make(view, "请输入便签内容！", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                //创建NoteBean对象封装数据
                NoteBean noteBean = new NoteBean();
                noteBean.setNoteTitle(title.getText().toString());
                noteBean.setNoteContent(content.getText().toString());
                noteBean.setNoteDate(getTime());

                //调用数据库帮助类的添加方法添加到SQLite数据库
                mDatabaseHelper.insertNote(noteBean);
                //添加到ListViewk控件
                mNoteBeanList.add(noteBean);
                finish();
            }
        });
    }



    //得到时间
    private String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm E");
        Date curDate = new Date();
        String str = format.format(curDate);
        return str;
    }
}

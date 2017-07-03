package ryanc.cc.mynote;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<NoteBean> mNoteBeanList;
    private DatabaseHelper mDatabaseHelper;
    private NoteListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabaseHelper = new DatabaseHelper(this);
        mNoteBeanList = new ArrayList<>();
        ListView noteList = (ListView)findViewById(R.id.lv_main);
        initNoteData();
        adapter = new NoteListAdapter(this,mNoteBeanList);
        noteList.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflate =LayoutInflater.from(MainActivity.this);
                View viewDialog = inflate.inflate(R.layout.new_note_data,null);

                //获取控件数据
                final EditText title = (EditText) viewDialog.findViewById(R.id.et_note_title);
                final EditText content = (EditText) viewDialog.findViewById(R.id.et_note_content);
                final DatePicker date = (DatePicker) viewDialog.findViewById(R.id.dp_note_date);
                builder.setView(viewDialog);
                builder.setTitle("New Note");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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
                        noteBean.noteTitle = title.getText().toString();
                        noteBean.noteContent = content.getText().toString();
                        noteBean.noteDate = date.getYear() + "-" + (date.getMonth()+1) + "-" + date.getDayOfMonth();

                        //调用数据库帮助类的添加方法添加到SQLite数据库
                        mDatabaseHelper.insertNote(noteBean);
                        //添加到ListViewk控件
                        mNoteBeanList.add(noteBean);
                        adapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("Cancel",null);
                builder.create().show();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * 初始化便签数据
     */
    private void initNoteData() {
        Cursor cursor = mDatabaseHelper.getAllNoteData();
        if(cursor!=null){
            while(cursor.moveToNext()){
                NoteBean noteBean = new NoteBean();
                noteBean.noteTitle = cursor.getString(cursor.getColumnIndex("note_title"));
                noteBean.noteDate = cursor.getString(cursor.getColumnIndex("note_date"));
                noteBean.noteContent = cursor.getString(cursor.getColumnIndex("note_content"));
                mNoteBeanList.add(noteBean);
            }
            cursor.close();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_note) {
            // Handle the camera action
        } else if (id == R.id.nav_timeline) {

        } else if (id == R.id.nav_exit){
            //调用退出程序的方法
            this.exitDialog();
        } else if (id == R.id.nav_share){
            this.shareapp();
        } else if (id == R.id.btn_share){
            this.shareapp();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 指定onClick
     * @param v
     */
    public void click(View v) {
        // TODO Auto-generated method stub

        //获取组件的资源id
        int id = v.getId();
        switch (id) {
            case R.id.btn_share:
                this.sharenote();
                break;
            default:
                break;
        }
    }


    /**
     * 退出程序的方法
     */
    private void exitDialog(){
        Dialog dialog = new AlertDialog.Builder(MainActivity.this)
                //设置对话框标题
                .setTitle("程序退出")
                //对话框内容
                .setMessage("您确定要退出吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //销毁进程
                        MainActivity.this.finish();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        //显示对话框
        dialog.show();
    }

    /**
     * 当按下手机返回键的时候
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断是否按下返回键
        if(keyCode == KeyEvent.KEYCODE_BACK){ //如果按了返回键
            MainActivity.this.exitDialog();    //调用退出的方法
        }
        return false;
    }

    //分享本app
    public void shareapp() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "http://ryanc.cc");
        shareIntent.setType("text/plain");

        //设置分享列表的标题，并且每次都显示分享列表
        startActivity(Intent.createChooser(shareIntent, "分享此应用到"));
    }

    //分享指定便签信息
    public void sharenote() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "http://ryanc.cc");
        shareIntent.setType("text/plain");

        //设置分享列表的标题，并且每次都显示分享列表
        startActivity(Intent.createChooser(shareIntent, "分享此便签到"));
    }
}

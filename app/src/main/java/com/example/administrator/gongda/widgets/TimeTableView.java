package com.example.administrator.gongda.widgets;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.gongda.R;
import com.example.administrator.gongda.bean.courses;
import com.example.administrator.gongda.dataBase.stuDatabaseDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 课表显示View
 *
 * @author shallcheek
 */
public class TimeTableView extends LinearLayout {
    /**
     * 配色数组
     */
    public static int colors[] = {R.drawable.select_label_san,
            R.drawable.select_label_er, R.drawable.select_label_si,
            R.drawable.select_label_wu, R.drawable.select_label_liu,
            R.drawable.select_label_qi, R.drawable.select_label_ba,
            R.drawable.select_label_jiu, R.drawable.select_label_sss,
            R.drawable.select_label_se, R.drawable.select_label_yiw,
            R.drawable.select_label_sy, R.drawable.select_label_yiwu,
            R.drawable.select_label_yi, R.drawable.select_label_wuw};
    private final static int START = 0;
    //最大节数
    public final static int MAXNUM = 12;
    //显示到星期几
    public final static int WEEKNUM = 7;
    //单个View高度
    private final static int TimeTableHeight = 50;
    //线的高度
    private final static int TimeTableLineHeight = 2;
    private final static int TimeTableNumWidth = 20;
    private final static int TimeTableWeekNameHeight = 30;
    private LinearLayout mHorizontalWeekLayout;//第一行的星期显示
    private LinearLayout mVerticalWeekLaout;//课程格子
    private String[] weekname = {"一", "二", "三", "四", "五", "六", "七"};
    public static String[] colorStr = new String[20];
    int colornum = 0;
    private stuDatabaseDao studatabaseDao = new stuDatabaseDao(getContext());
    //数据源
    int coursecamp;
    int courseweek;
    int coursestarttime;
    int courseendtime;
    int coursestartweek;
    int courseendweek;
    AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
    AlertDialog alertDialog2 = new AlertDialog.Builder(getContext()).create();
    private List<courses> mListTimeTable = new ArrayList<courses>();
    private ProgressBar progressBar = null;

    public TimeTableView(Context context) {
        super(context);
    }

    public TimeTableView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    Handler ioHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    progressBar.setVisibility(View.GONE);
                    alertDialog.dismiss();
                    alertDialog = new AlertDialog.Builder(getContext()).create();
                    Toast.makeText(getContext(), "添加成功", Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    progressBar.setVisibility(View.GONE);
                    alertDialog.dismiss();
                    alertDialog = new AlertDialog.Builder(getContext()).create();
                    Toast.makeText(getContext(), "课程时间重复，添加失败", Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    alertDialog2.dismiss();
                    alertDialog2 = new AlertDialog.Builder(getContext()).create();
                    Toast.makeText(getContext(), "删除成功", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * 横的分界线
     *
     * @return
     */
    private View getWeekTransverseLine() {
        TextView mWeekline = new TextView(getContext());
        mWeekline.setBackgroundColor(getResources().getColor(R.color.view_line));
        mWeekline.setHeight(TimeTableLineHeight);
        mWeekline.setWidth(LayoutParams.FILL_PARENT);
        return mWeekline;
    }

    /**
     * 竖向分界线
     *
     * @return
     */
    private View getWeekVerticalLine() {
        TextView mWeekline = new TextView(getContext());
        mWeekline.setBackgroundColor(getResources().getColor(R.color.view_line));
        mWeekline.setHeight(dip2px(TimeTableWeekNameHeight));
        mWeekline.setWidth((TimeTableLineHeight));
        return mWeekline;
    }


    private void initView() {

        mHorizontalWeekLayout = new LinearLayout(getContext());
        mHorizontalWeekLayout.setOrientation(HORIZONTAL);

        mVerticalWeekLaout = new LinearLayout(getContext());
        mVerticalWeekLaout.setOrientation(HORIZONTAL);
        //表格
        for (int i = 0; i <= WEEKNUM; i++) {
            switch (i) {
                case 0:
                    //课表出的0,0格子 空白的
                    TextView mTime = new TextView(getContext());
                    mTime.setHeight(dip2px(TimeTableWeekNameHeight));
                    mTime.setWidth((dip2px(TimeTableNumWidth)));
                    mHorizontalWeekLayout.addView(mTime);

                    //绘制1~MAXNUM
                    LinearLayout mMonday = new LinearLayout(getContext());
                    ViewGroup.LayoutParams mm = new ViewGroup.LayoutParams(dip2px(TimeTableNumWidth), dip2px(MAXNUM * TimeTableHeight) + MAXNUM * 2);
                    mMonday.setLayoutParams(mm);
                    mMonday.setOrientation(VERTICAL);
                    for (int j = 1; j <= MAXNUM; j++) {
                        TextView mNum = new TextView(getContext());
                        mNum.setGravity(Gravity.CENTER);
                        mNum.setTextColor(getResources().getColor(R.color.text_color));
                        mNum.setHeight(dip2px(TimeTableHeight));
                        mNum.setWidth(dip2px(TimeTableNumWidth));
                        mNum.setTextSize(14);
                        mNum.setText(j + "");
                        mMonday.addView(mNum);
                        mMonday.addView(getWeekTransverseLine());
                    }
                    mVerticalWeekLaout.addView(mMonday);
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    // 设置显示星期一 到星期天
                    LinearLayout mHoriView = new LinearLayout(getContext());
                    mHoriView.setOrientation(VERTICAL);
                    TextView mWeekName = new TextView(getContext());
                    mWeekName.setTextColor(getResources().getColor(R.color.text_color));
                    mWeekName.setWidth(((getViewWidth() - dip2px(TimeTableNumWidth))) / WEEKNUM);
                    mWeekName.setHeight(dip2px(TimeTableWeekNameHeight));
                    mWeekName.setGravity(Gravity.CENTER);
                    mWeekName.setTextSize(16);
                    mWeekName.setText(weekname[i - 1]);
                    mHoriView.addView(mWeekName);
                    mHorizontalWeekLayout.addView(mHoriView);

                    List<courses> mListMon = new ArrayList<>();
                    //遍历出星期1~7的课表
                    for (courses courses : mListTimeTable) {
                        if (Integer.parseInt(courses.getXinqiji()) == i) {
                            mListMon.add(courses);
                        }
                    }
                    //添加
                    LinearLayout mLayout = getTimeTableView(mListMon, i);
                    mLayout.setOrientation(VERTICAL);
                    ViewGroup.LayoutParams linearParams = new ViewGroup.LayoutParams((getViewWidth() - dip2px(20)) / WEEKNUM, LayoutParams.FILL_PARENT);
                    mLayout.setLayoutParams(linearParams);
                    mLayout.setWeightSum(1);
                    mVerticalWeekLaout.addView(mLayout);
                    break;

                default:
                    break;
            }
            TextView l = new TextView(getContext());
            l.setHeight(dip2px(TimeTableHeight * MAXNUM) + MAXNUM * 2);
            l.setWidth(2);
            l.setBackgroundColor(getResources().getColor(R.color.view_line));
            mVerticalWeekLaout.addView(l);
            mHorizontalWeekLayout.addView(getWeekVerticalLine());
        }
        addView(mHorizontalWeekLayout);
        addView(getWeekTransverseLine());
        addView(mVerticalWeekLaout);
        addView(getWeekTransverseLine());
    }

    private int getViewWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    private View addStartView(int startnum, final int week, final int start) {
        LinearLayout mStartView = new LinearLayout(getContext());
        mStartView.setOrientation(VERTICAL);
        for (int i = 1; i < startnum; i++) {
            TextView mTime = new TextView(getContext());
            mTime.setGravity(Gravity.CENTER);
            mTime.setHeight(dip2px(TimeTableHeight));
            mTime.setWidth(dip2px(TimeTableHeight));
            mStartView.addView(mTime);
            mStartView.addView(getWeekTransverseLine());
            final int num = i;
            //这里可以处理空白处点击添加课表
            mTime.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //   Dialog
                    addCourse(week);
                    //Toast.makeText(getContext(), "星期" + week + "第" + (start + num) + "节", Toast.LENGTH_LONG).show();
                }
            });

        }
        return mStartView;
    }

    private void addCourse(int week) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.addcoursedilog_layout, null);
        alertDialog.setView(view);
        alertDialog.setCanceledOnTouchOutside(false);
        Window window = alertDialog.getWindow();
        window.setWindowAnimations(R.style.MyDialog);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
        final EditText CourseName = (EditText) view.findViewById(R.id.courseName);
        final EditText CourseTea = (EditText) view.findViewById(R.id.courseTea);
        final EditText CourseAddress = (EditText) view.findViewById(R.id.courseAddress);
        final Spinner CourseCamp = (Spinner) view.findViewById(R.id.courseCamp);
        final Spinner CourseWeek = (Spinner) view.findViewById(R.id.courseWeek);
        final Spinner CourseStartTime = (Spinner) view.findViewById(R.id.courseStartTime);
        final Spinner CourseEndTime = (Spinner) view.findViewById(R.id.courseEndTime);
        final Spinner CourseStartWeek = (Spinner) view.findViewById(R.id.courseStartWeek);
        final Spinner CourseEndWeek = (Spinner) view.findViewById(R.id.courseEndWeek);
        Button CourseSubmit = (Button) view.findViewById(R.id.courseSubmit);
        Button CourseCancel = (Button) view.findViewById(R.id.CourseCancel);
        //CourseCamp.getSelectedItemPosition()
        alertDialog.show();
        CourseCamp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                coursecamp = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        CourseWeek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                courseweek = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        CourseStartTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                coursestarttime = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        CourseEndTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                courseendtime = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        CourseStartWeek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                coursestartweek = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        CourseEndWeek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                courseendweek = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        CourseSubmit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CourseName.getText() == null)
                    CourseName.setError("请输入后提交");
                else if (CourseAddress.getText() == null)
                    CourseTea.setError("请输入后提交");
                else if (CourseTea.getText() == null)
                    CourseAddress.setError("请输入后提交");
                else if (coursestarttime >= courseendtime)
                    Toast.makeText(getContext(), "课程开始时间必须小于结束时间", Toast.LENGTH_LONG).show();
                else if (coursestartweek > courseendweek) {
                    Toast.makeText(getContext(), "课程开始周必须小于等于结束周", Toast.LENGTH_LONG).show();
                } else {
                    final String coursename = CourseName.getText().toString();
                    final String coursetea = CourseTea.getText().toString();
                    final String courseaddress = CourseAddress.getText().toString();
                    progressBar.setVisibility(VISIBLE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferences sp = getContext().getSharedPreferences("userXiaoinfo", Context.MODE_PRIVATE);
                            String xueqi = sp.getInt("xueqi", 1) + "";
                            String chooseYear = sp.getInt("chooseYear", 2016) + "";
                            Map userinfo = new HashMap();
                            userinfo = studatabaseDao.getUserInfo();
                            List<courses> list = new ArrayList<courses>();
                            int flag = 0;
                            String camps[] = {"江浦校区", "丁家桥校区"};
                            String weeknames[] = {"星期一", "星期二", "星期三", "星期四", "星期四", "星期五", "星期六", "星期日"};
                            list.add(new courses(coursestarttime + "-" + courseendtime, coursename, "null", courseaddress, coursetea, coursestartweek + "-" + courseendweek + "周", camps[coursecamp], chooseYear + "", xueqi + "", weeknames[courseweek], (String) userinfo.get("sessionUserKey"), courseweek + 1 + ""));
                            List<courses> localhost = new ArrayList<courses>();
                            String sql = "select * from courses where xinqiji='" + (courseweek + 1) + "'";
                            localhost = studatabaseDao.getCoursesByQueryString(sql);
                            for (courses s : localhost) {
                                int starttime = s.getStartnum();
                                int endtime = s.getEndnum();
                                String kechenzhou = s.getKechenzhou();

                                if (!kechenzhou.contains(","))
                                    kechenzhou += ",";
                                String[] ts = kechenzhou.split(",");
                                for (int i = 0; i < ts.length; i++) {
                                    String t[] = null;
                                    if (ts[i].contains("单") || ts[i].contains("双"))
                                        t = (ts[i].substring(0, ts[i].length() - 4)).split("-");
                                    else
                                        t = (ts[i].substring(0, ts[i].length() - 1)).split("-");
                                    int startweek = Integer.parseInt(t[0]);
                                    int endweek = Integer.parseInt(t[1]);
                                    if ((coursestartweek >= startweek && coursestartweek <= endweek) || (courseendweek >= startweek && courseendweek <= endtime)) {
                                        if ((coursestarttime >= starttime && coursestarttime <= endtime) || (courseendtime >= starttime && courseendtime <= endtime)) {
                                            ioHandler.sendEmptyMessage(2);
                                            flag = 1;
                                            break;
                                        }
                                    }


                                }
                                if (flag == 1)
                                    break;
                            }
                            if (flag == 0) {
                                studatabaseDao.AddCourseInfo(list);
                                ioHandler.sendEmptyMessage(1);
                            }
                        }
                    }).start();

                }
            }
        });
        CourseCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                alertDialog = new AlertDialog.Builder(getContext()).create();
            }
        });


    }

    /**
     * 星期一到星期天的课表
     *
     * @param model
     * @param week
     * @return
     */
    private LinearLayout getTimeTableView(List<courses> model, int week) {
        LinearLayout mTimeTableView = new LinearLayout(getContext());
        mTimeTableView.setOrientation(VERTICAL);
        int modesize = model.size();
        if (modesize <= 0) {
            mTimeTableView.addView(addStartView(MAXNUM + 1, week, 0));
        } else {
            for (int i = 0; i < modesize; i++) {
                if (i == 0) {
                    //添加的0到开始节数的空格
                    mTimeTableView.addView(addStartView(model.get(0).getStartnum(), week, 0));
                    mTimeTableView.addView(getMode(model.get(0)));
                } else if (model.get(i).getStartnum() - model.get(i - 1).getStartnum() > 0) {
                    //填充
                    mTimeTableView.addView(addStartView(model.get(i).getStartnum() - model.get(i - 1).getEndnum(), week, model.get(i - 1).getEndnum()));
                    mTimeTableView.addView(getMode(model.get(i)));
                }
                if (i + 1 == modesize) {
                    mTimeTableView.addView(addStartView(MAXNUM - model.get(i).getEndnum(), week, model.get(i).getEndnum()));
                }
            }
        }
        return mTimeTableView;
    }

    /**
     * 获取单个课表View 也可以自定义我这个
     *
     * @param model 数据类型
     * @return
     */
    @SuppressWarnings("deprecation")
    private View getMode(final courses model) {
        LinearLayout mTimeTableView = new LinearLayout(getContext());
        mTimeTableView.setOrientation(VERTICAL);
        TextView mTimeTableNameView = new TextView(getContext());
        int num = model.getEndnum() - model.getStartnum();
        mTimeTableNameView.setHeight(dip2px((num + 1) * TimeTableHeight) + num * 2);
        mTimeTableNameView.setTextColor(getContext().getResources().getColor(
                android.R.color.white));
        mTimeTableNameView.setWidth(dip2px(50));
        mTimeTableNameView.setTextSize(16);
        mTimeTableNameView.setGravity(Gravity.CENTER);
        mTimeTableNameView.setText(model.getKechenmingchen() + "@" + model.getDidian());
        mTimeTableView.addView(mTimeTableNameView);
        mTimeTableView.addView(getWeekTransverseLine());
        mTimeTableView.setBackgroundDrawable(getContext().getResources()
                .getDrawable(colors[getColorNum(model.getKechenmingchen())]));
        mTimeTableView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), model.getKechenmingchen() + "@" + model.getDidian(), Toast.LENGTH_LONG).show();
                showDetails(model);
            }
        });
        return mTimeTableView;
    }

    private void showDetails(final courses model) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.detail_course_layout, null);
        alertDialog2.setView(view);
        Window window = alertDialog2.getWindow();
        alertDialog2.setCanceledOnTouchOutside(false);
        window.setWindowAnimations(R.style.MyDialog);
        TextView coursename = (TextView) view.findViewById(R.id.courseName2);
        TextView courseaddress = (TextView) view.findViewById(R.id.courseAddress2);
        TextView coursetea = (TextView) view.findViewById(R.id.courseTea2);
        TextView coursecamp = (TextView) view.findViewById(R.id.courseCamp2);
        TextView courseweek = (TextView) view.findViewById(R.id.courseWeek2);
        TextView courseweeks = (TextView) view.findViewById(R.id.courseWeeks);
        TextView coursetime = (TextView) view.findViewById(R.id.courseTimes2);
        Button coursedelete = (Button) view.findViewById(R.id.CourseDelete);
        Button coursecancel = (Button) view.findViewById(R.id.CourseCancel);
        coursename.setText(model.getKechenmingchen());
        courseaddress.setText(model.getDidian());
        coursetea.setText(model.getJiangshi());
        coursecamp.setText(model.getXiaoquming());
        courseweek.setText(model.getXinqi());
        courseweeks.setText(model.getKechenzhou());
        coursetime.setText(model.getStartnum() + " ~ " + model.getEndnum());
        coursecancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog2.dismiss();
                alertDialog2 = new AlertDialog.Builder(getContext()).create();
            }
        });
        coursedelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list = new ArrayList<String>();
                list.add(model.getJieshu());
                list.add(model.getKechenmingchen());
                list.add(model.getKechenzhou());
                list.add(model.getXinqiji());
                studatabaseDao.deleteCourseByStrings(list);
                ioHandler.sendEmptyMessage(3);


            }
        });
        alertDialog2.show();
    }

    /**
     * 转换dp
     *
     * @param dpValue
     * @return
     */
    public int dip2px(float dpValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setTimeTable(List<courses> mlist) {
        this.mListTimeTable = mlist;
        for (courses courses : mlist) {
            addTimeName(courses.getKechenmingchen());
        }
        initView();
        invalidate();
    }

    /**
     * 输入课表名循环判断是否数组存在该课表 如果存在输出true并退出循环 如果不存在则存入colorSt[20]数组
     *
     * @param name
     */
    private void addTimeName(String name) {
        boolean isRepeat = true;
        for (int i = 0; i < 20; i++) {
            if (name.equals(colorStr[i])) {
                isRepeat = true;
                break;
            } else {
                isRepeat = false;
            }
        }
        if (!isRepeat) {
            colorStr[colornum] = name;
            colornum++;
        }
    }

    /**
     * 获取数组中的课程名
     *
     * @param name
     * @return
     */
    public static int getColorNum(String name) {
        int num = 0;
        for (int i = 0; i < 20; i++) {
            if (name.equals(colorStr[i])) {
                num = i;
            }
        }
        return num;
    }
}

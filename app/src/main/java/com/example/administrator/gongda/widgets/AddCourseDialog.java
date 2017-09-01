package com.example.administrator.gongda.widgets;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.administrator.gongda.R;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/2/19.
 */

public class AddCourseDialog extends Dialog {
    @Bind(R.id.courseName)
    EditText CourseName;
    @Bind(R.id.courseTea)
    EditText CourseTea;
    @Bind(R.id.courseAddress)
    EditText CourseAddress;
    @Bind(R.id.courseCamp)
    Spinner CourseCamp;
    @Bind(R.id.courseWeek)
    Spinner CourseWeek;
    @Bind(R.id.courseStartTime)
    Spinner CourseStartTime;
    @Bind(R.id.courseEndTime)
    Spinner CourseEndTime;
    @Bind(R.id.courseSubmit)
    Button CourseSubmit;
    @Bind(R.id.CourseCancel)
    Button CourseCancel;
    private onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器

    public interface onYesOnclickListener {
        public void onYesClick();
    }

    public interface onNoOnclickListener {
        public void onNoClick();
    }
    public AddCourseDialog(Context context, int themeResId) {
        super(context, R.style.MyDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcoursedilog_layout);
        setCanceledOnTouchOutside(false);
        initEvent();
    }
    public void setYesOnclickListener( onYesOnclickListener onYesOnclickListener) {

        this.yesOnclickListener = onYesOnclickListener;
    }
    private void initEvent() {

    }
}

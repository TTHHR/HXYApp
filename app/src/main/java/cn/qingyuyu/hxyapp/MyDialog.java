package cn.qingyuyu.hxyapp;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapText;

/**
 * Created by Administrator on 2017\8\3 0003.
 */

public class MyDialog extends AlertDialog implements View.OnClickListener {
    Context cont;
    private AwesomeTextView messageTxt, titleTxt;
    private BootstrapButton okButton;

    public MyDialog(Context cont) {
        super(cont);
        this.cont = cont;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialoglayout);
        setCanceledOnTouchOutside(false);
        messageTxt = findViewById(R.id.message);
        titleTxt = findViewById(R.id.title);
        okButton = findViewById(R.id.okbutton);
        okButton.setOnClickListener(this);
    }

    public void setTitleTxt(String title) {

        titleTxt.setText(title);
    }

    public void setMessageTxt(String message) {
        messageTxt.setText(message);
    }

    public void setOkButtonTxt(String txt) {
        okButton.setText(txt);
    }

    @Override
    public void onClick(View view) {
        this.dismiss();
    }
}

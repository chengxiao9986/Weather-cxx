package cn.edu.pku.chengxiaoxiao.weather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Jason on 16/10/23.
 */

public class SelectCtiy extends AppCompatActivity implements View.OnClickListener{

    private ImageView mBackBtn;
    @Override
    protected void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);

        setContentView(R.layout.select_city);


        mBackBtn=(ImageView)findViewById(R.id.title_back);
        mBackBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.title_back:

                Intent i = new Intent();
                i.putExtra("cityCode", "101160101");
                setResult(RESULT_OK, i);

                finish();
                break;
            default:
                break;
        }
    }
}

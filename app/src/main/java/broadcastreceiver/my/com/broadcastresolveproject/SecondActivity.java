package broadcastreceiver.my.com.broadcastresolveproject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by AOW on 2018/4/17.
 * 普通广播
 *
 */

public class SecondActivity extends Activity {

    String tex;
    @Bind(R.id.normal_tv)
    TextView normal_tv;

    @OnClick({R.id.normal_tv})
    public void onClick(){
        Intent intent = new Intent();
        intent.setAction("com.my.second");
        intent.putExtra("second","666666");
        sendBroadcast(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        ButterKnife.bind(this);

//      注册广播
        IntentFilter intentf=new IntentFilter("com.my.second");
        MyBroadCastReveiver mybr=new MyBroadCastReveiver();
        registerReceiver(mybr,intentf);
    }

    class MyBroadCastReveiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            intent = getIntent();//获取传来的intent对象
            tex = intent.getStringExtra("second");
            normal_tv.setText(tex);
            Toast.makeText(SecondActivity.this,tex+"什么",Toast.LENGTH_LONG).show();
        }
    }

}

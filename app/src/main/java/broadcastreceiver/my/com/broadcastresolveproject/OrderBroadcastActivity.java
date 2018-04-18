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
 * Created by AOW on 2018/4/18.
 * 有序广播
 */

public class OrderBroadcastActivity extends Activity {

    @Bind(R.id.first_order_tv)
    TextView first_order_tv;
    @Bind(R.id.second_order_tv)
    TextView second_order_tv;
    @OnClick({R.id.first_order_tv})
    public void onCLick(){
        Intent intent2 = new Intent();
        intent2.setAction("com.my.order");
        intent2.putExtra("first","666666");
        sendOrderedBroadcast(intent2,null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity);
        ButterKnife.bind(this);

        IntentFilter intentFilter=new IntentFilter("com.my.order");
        intentFilter.setPriority(20);//设置优先级
        FirstBroadcast firstBroadcast=new FirstBroadcast();
        registerReceiver(firstBroadcast,intentFilter);

        IntentFilter intentFilter1=new IntentFilter("com.my.order");
        intentFilter.setPriority(0);//设置优先级
        SecondBroadcast firstBroadcast1=new SecondBroadcast();
        registerReceiver(firstBroadcast1,intentFilter1);
    }
    class FirstBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            //接收到的从MainActivity传递过来的值
            String firstvalue=intent.getStringExtra("first");
            first_order_tv.setText(firstvalue);
            //创建Bunble对象，并存入数据
            Bundle bundle=new Bundle();
            bundle.putString("second","优先级别低的Broadcast");
            setResultExtras(bundle);
            //终止广播  如果这条代码没被注释，优先级别低的广播不会被触发
//            abortBroadcast();

        }
    }

    class SecondBroadcast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            //解析前一个广播所存入的key为first消息
            Bundle bundle=getResultExtras(true);
            String first=bundle.getString("second");
            Toast.makeText(context,"优先级别低"+first,Toast.LENGTH_LONG).show();
            second_order_tv.setText(first);
        }
    }
}

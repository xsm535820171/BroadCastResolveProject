package broadcastreceiver.my.com.broadcastresolveproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * BroadCastReceiver:接收广播消息
 * 本质上就是一种全局监听器，用于监听系统全局的广播消息；由于BroadCastReceiver是一种全局的监听器，
 * 因此他可以非常方便的实现系统中不同组件之间的通信。例如，我们希望客户端程序与starService()方法
 * 启动的Service之间通信，就可以借助BroadCastReceiver来实现；
 *
 * 启动BroadCastReveiver只需要两步：
 *    1、创建需要启动的BroadCastReveiver的Intent(添加setAction(),和参数),
 *    2、调用Context的sendBroadcast()或sendOrderedBroadcast方法来启动指定的BroadcastReceiver;
 * 发出一个广播intent后，所有匹配该intent的广播都有可能被启动；
 *
 * 指定该BroadCastReveiver能匹配的Intent的方式（两种）：
 *    1、代码指定：调用BroadCastReveiver的Context的registerReveiver(BroadCastReveiver bs,IntentFilter inf)方法指定
 *    2、在AndroidManifest.xml文件中配置
 *
 * 注意：每次BroadCast发生后，系统会创建对应的BroadCastReveiver的实例，并自动触发他的onReceive()方法，onReceive()方法执行后，实例销毁
 *      onReceiver()方法中不能执行一些耗时操作，超过10秒会报ANR;如果需要在此方法中执行一些耗时操作，则需要考虑通过Intent启动一个Service
 *      来完成操作，不考虑使用新线程去完成好事操作，因为BroadCastReveiver的生命周期很短，可能出现子线程还没结束，BroadCastReveiver就已经退出了
 *      在
 *
 * BroadCast广播分为两种：
 * 1，普通广播：完全异步的，可以在同一刻被所有接受者接收到，消息传递的效率比较高，缺点不能将处理结果传递给下一个接收者
 * 2、有序广播：接受者按预先声明的优先级依次接收BroadCast,优先级生声明在<Intent-filter.../>元素的android:priority中；
 *            数越大级别越高，也可以调用IntentFilter的serPriority()进行设置;
 *            优先接到广播的接受者，可以调用BroadCast的abortBroadcast()终止广播，后面的广播接收者再也无法接收广播；
 *            另外，对于优先接收到广播者,可以通过setResultExtras(Bunble bundle),将消息存入到BroadCast,传递给写一个接受者，
 *
 *
 * Intent的几个重要属性:动作(Action),数据(Data),分类(Category),类型(Type),组件(Compent)以及扩展信(Extra)。
     在其<intent-filter>中声明了<action>，即目标action，如果我们需要做一个跳转的动作，就需要在Intent中指定目标的action，如下：

     public void gotoTargetActivity(View view) {
          Intent intent = new Intent("com.scott.intent.action.TARGET");
          startActivity(intent);
     }
 *  data和extras，即执行动作要操作的数据和传递到目标的附加信息:
 *  分类(Category):要执行动作的目标所具有的特质或行为归类,应用主界面Activity通常有<category android:name="android.intent.category.LAUNCHER" />
 *  type：要执行动作的目标Activity所能处理的MIME数据类型
 *  component，目标组件的包或类名称
 */
public class MainActivity extends AppCompatActivity {

    final String TAG="MainActivity";

    @OnClick({R.id.normal_tv,R.id.order_tv})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.normal_tv://普通广播
                Intent intent1=new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent1);
                break;
            case R.id.order_tv://有序广播
                Intent intent3=new Intent(MainActivity.this,OrderBroadcastActivity.class);
                startActivity(intent3);
                break;
        }

    }
    String tex="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //绑定初始化ButterKnife
        ButterKnife.bind(this);
        Log.e(TAG, "onCreate: ", null);
    }


}

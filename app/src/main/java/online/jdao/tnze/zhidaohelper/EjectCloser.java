package online.jdao.tnze.zhidaohelper;

import android.app.Service;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.IBinder;
import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

public class EjectCloser extends AccessibilityService {
    private final String TAG = getClass().getName();
    /**
     * 辅助功能是否启动
     */
    public static boolean mIsStart = false;

    public static EjectCloser mService;

    //初始化
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Toast.makeText(EjectCloser.this, "服务启动", Toast.LENGTH_LONG).show();
        mIsStart = true;
        mService = this;
    }

    //实现辅助功能
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
//        Toast.makeText(EjectCloser.this, event.getClassName(), Toast.LENGTH_LONG).show();

        //只关心窗口状态改变事件
        if(event.getEventType()!=AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED)
            return;

        if ("android.app.Dialog".equals(event.getClassName())) {
            AccessibilityNodeInfo s = event.getSource();

            if(s.getChildCount()>0 && s.getChild(0).getChildCount()>2 &&
                    s.getChild(0).getChild(0).getChildCount()>0 &&
                    s.getChild(0).getChild(0).getChild(0).getChildCount()>2)
            {
                Toast.makeText(EjectCloser.this, "检测到弹题", Toast.LENGTH_LONG).show();

                AccessibilityNodeInfo closeButton = s.getChild(0).getChild(1);
                AccessibilityNodeInfo choseA = s.getChild(0).getChild(0).getChild(0).getChild(2);

//                Rect outBounds = new Rect();
//                closeButton.getBoundsInScreen(outBounds);
//                Log.i(TAG, "onAccessibilityEvent: "+outBounds.toString());

                choseA.performAction(AccessibilityNodeInfo.ACTION_CLICK);//选A
                closeButton.performAction(AccessibilityNodeInfo.ACTION_CLICK);//关闭
            }
        }
//        else
//            Toast.makeText(EjectCloser.this, event.getClassName(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInterrupt() {
        Toast.makeText(EjectCloser.this, "服务中断", Toast.LENGTH_LONG).show();
        mIsStart = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Toast.makeText(EjectCloser.this, "服务关闭", Toast.LENGTH_LONG).show();
        mIsStart = false;
    }

}

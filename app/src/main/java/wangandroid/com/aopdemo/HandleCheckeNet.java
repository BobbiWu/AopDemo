package wangandroid.com.aopdemo;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by Administrator on 2018/3/7.
 * 处理切点，切面
 */
@Aspect
public class HandleCheckeNet {
    /**
     * 处理切点
     */
    @Pointcut("execution(@wangandroid.com.aopdemo.CheckNet * *(..))")
    public void checkBehavior() {
    }

    /**
     * 处理切面
     */
    @Around("checkBehavior()")
    public Object checkNet(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.e("TAG", "checkNet");
        // 做埋点  日志上传  权限检测（我写的，RxPermission , easyPermission） 网络检测
        // 网络检测
        // 1.获取 CheckNet 注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        CheckNet checkNet = signature.getMethod().getAnnotation(CheckNet.class);
        if (checkNet != null) {
            //判断有没有网络，先要获取context；
            Object object = joinPoint.getThis();
            Context context = getContext(object);
            //判断是否有网络
            if (!isNetworkAvailable(context)){
                // 没有网络不要往下执行
                Toast.makeText(context,"请检查您的网络",Toast.LENGTH_LONG).show();
                return null;
            }
        }
        return joinPoint.proceed();
    }

    /**
     * 获取上下文
     *
     * @param object
     * @return
     */
    private Context getContext(Object object) {
        if (object instanceof Activity) {
           return (Activity)object;
        } else if (object instanceof View) {
            View view=(View)object;
            return view.getContext();
        } else if (object instanceof Fragment) {
            Fragment fragment=(Fragment)object;
            return fragment.getActivity();
        }
        return null;
    }

    /**
     * 网络间检测
     * @param context
     * @return
     */
    private static boolean isNetworkAvailable(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

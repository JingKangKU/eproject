package com.chinaums.common.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;

import com.chinaums.common.utils.core.Utils;

import java.util.List;

public class UMSLocationUtil {

    private volatile static UMSLocationUtil instance;
    private LocationManager locationManager;
    private final Context mContext;
    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
        showLocation();
    }

    private static Location location;

    private UMSLocationUtil() {
        mContext = Utils.getContext();
        getLocation();
    }

    //采用Double CheckLock(DCL)实现单例
    public static UMSLocationUtil getInstance() {
        if (instance == null) {
            synchronized (UMSLocationUtil.class) {
                if (instance == null) {
                    instance = new UMSLocationUtil();
                }
            }
        }
        return instance;
    }

    private void getLocation() {
        //添加用户权限申请判断
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //添加用户权限申请判断
        //1.获取位置管理器
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        //2.获取位置提供器，GPS或是NetWork
        // 获取所有可用的位置提供器
        List<String> providerList = locationManager.getProviders(true);
        String locationProvider;
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            //GPS 定位的精准度比较高，但是非常耗电。
            System.out.println("=====GPS_PROVIDER=====");
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {//Google服务被墙不可用
            //网络定位的精准度稍差，但耗电量比较少。
            System.out.println("=====NETWORK_PROVIDER=====");
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            System.out.println("=====NO_PROVIDER=====");
            // 当没有可用的位置提供器时，弹出Toast提示用户
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            mContext.startActivity(intent);
            return;
        }
        location = locationManager.getLastKnownLocation(locationProvider);
        if (location != null) {
            // 显示当前设备的位置信息
            System.out.println("==显示当前设备的位置信息==");
            showLocation();
        } else {//当GPS信号弱没获取到位置的时候可从网络获取
            System.out.println("==Google服务被墙的解决办法==");
            getLngAndLatWithNetwork();//Google服务被墙的解决办法
        }
        // 监视地理位置变化，第二个和第三个参数分别为更新的最短时间minTime和最短距离minDistace
        //LocationManager 每隔 5 秒钟会检测一下位置的变化情况，当移动距离超过 10 米的时候，
        // 就会调用 LocationListener 的 onLocationChanged() 方法，并把新的位置信息作为参数传入。
        locationManager.requestLocationUpdates(locationProvider, 5000L, 10f, locationListener);
    }

    //获取经纬度
    private void showLocation() {
        if (location == null) {
            getLocation();
        } else {
            double latitude = location.getLatitude();//纬度
            double longitude = location.getLongitude();//经度
            if (callback != null) {
                callback.onGetLocation(latitude, longitude);
            }
        }
    }

    public void removeLocationUpdatesListener() {
        if (locationManager != null) {
            instance = null;
            locationManager.removeUpdates(locationListener);
        }
    }

    private final LocationListener locationListener = new LocationListener() {
        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {
        }

        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {
        }

        //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location loc) {
            System.out.println("==onLocationChanged==");
        }
    };

    //从网络获取经纬度
    private void getLngAndLatWithNetwork() {
        //添加用户权限申请判断
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, locationListener);
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        showLocation();
    }

    public interface Callback {
        void onGetLocation(double lat, double lng);
    }
}

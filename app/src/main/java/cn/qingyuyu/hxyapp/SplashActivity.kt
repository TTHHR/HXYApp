package cn.qingyuyu.hxyapp

import android.bluetooth.BluetoothAdapter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_splash.*
import android.bluetooth.BluetoothDevice
import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.beardedhen.androidbootstrap.TypefaceProvider
import java.util.*
import android.graphics.Color.parseColor
import android.view.ViewGroup
import android.content.Intent


class SplashActivity : AppCompatActivity() {

    lateinit var bluetoothAdapter: BluetoothAdapter
    //lateinit 是延迟初始化的意思

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TypefaceProvider.registerDefaultIconSets()
        setContentView(R.layout.activity_splash)
        // 去掉界面任务条
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    }

    override fun onStart() {
        super.onStart()
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "你的手机不支持蓝牙，应用即将退出！", Toast.LENGTH_LONG).show()
            Handler().postDelayed({ this.finish() }, 1000)
        } else {
            if (!bluetoothAdapter.isEnabled) {
                bluetoothAdapter.enable()
            }//确保蓝牙已经打开

            val pairedDevices = bluetoothAdapter.bondedDevices
            val deviceName: MutableList<String> = mutableListOf()
            for (device in pairedDevices) {
                deviceName.add(device.name)
            }
            val aa = ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, deviceName)
            devicelist.adapter = aa

            devicelist.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
                val intent = Intent()
                intent.putExtra("address", pairedDevices.elementAt(i).address)
                intent.setClass(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                this@SplashActivity.finish()
            }


            val md = MyDialog(this)
            md.show()
            md.setTitleTxt("info")
            md.setOkButtonTxt("ok")
            md.setMessageTxt("please select a device")
        }
    }


}

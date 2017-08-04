package cn.qingyuyu.hxyapp

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Toast
import java.io.InputStream
import java.io.OutputStream
import android.bluetooth.BluetoothDevice
import java.util.*


class MainActivity : AppCompatActivity() {
    var MY_UUID = "00001101-0000-1000-8000-00805F9B34FB"
    private var blueSocket: BluetoothSocket? = null
    private var bluetoothAdapter: BluetoothAdapter? = null
    private var handler: Handler? = null
    private var `in`: InputStream? = null
    private var out: OutputStream? = null
    private val runing = true//蓝牙连接
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ab = AlertDialog.Builder(this)
        val link = TextView(this)
        link.text = "正在连接蓝牙。。。"
        val pb = ProgressBar(this)
        val ll = LinearLayout(this)
        ll.orientation = LinearLayout.VERTICAL
        ll.addView(link)
        ll.addView(pb)
        ab.setView(ll)
        val ad = ab.create()
        ad.setCancelable(false)//不可以取消
        ad.setCanceledOnTouchOutside(false) //点击外面区域不会让dialog消失
        ad.show()

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
//        handler = object : Handler() {
//            fun handleMessage(ms: Message) {
//                val mess = ms.obj as String
//                Log.e("mmmmmmmm", mess)
//                if (mess == "connect") {
//                    try {
//                        if (`in` == null)
//                            `in` = blueSocket.getInputStream()
//                        /* 获取输出流 */
//                        if (out == null)
//                            out = blueSocket.getOutputStream()
//
//
//                        Thread(Runnable {
//                            while (runing) {
//
//                                try {
//                                    val bytes = `in`.available()
//                                    if (bytes != 0) {
//                                        val buffer = ByteArray(bytes)
//                                        `in`.read(buffer)
//                                        val data = String(buffer)
//
//                                        val message = Message()
//                                        message.obj = data
//                                        handler.sendMessage(message)
//                                    }
//                                    Thread.sleep(100)
//
//                                } catch (e: Exception) {
//                                    Log.e("read", e.toString())
//                                }
//
//                            }
//                        }).start()
//                    } catch (e: Exception) {
//                        Toast.makeText(applicationContext, "蓝牙连接失败", Toast.LENGTH_SHORT).show()
//                        ad.cancel()
//                        e.printStackTrace()
//                    }
//
//                    Toast.makeText(applicationContext, "蓝牙连接成功", Toast.LENGTH_SHORT).show()
//                    ad.cancel()
//
//                } else {
//                    //接收到的消息
//                }
//
//            }
//        }
//        Thread(Runnable {
//            try {
//                val i = intent
//                val address:String=i.getStringExtra("address")
//                var btDev: BluetoothDevice
//                btDev = bluetoothAdapter.getRemoteDevice(address)
//                Thread.sleep(500)
//                val uuid = UUID.fromString(MY_UUID)
//                blueSocket = btDev.createRfcommSocketToServiceRecord(uuid)
//                if (blueSocket == null)
//                    blueSocket.connect()
//                val message = Message()
//                message.obj = "connect"
//                handler.sendMessage(message)
//            } catch (e: Exception) {
//                Log.e("error", e.toString())
//                Toast.makeText(applicationContext, "蓝牙连接失败", Toast.LENGTH_SHORT).show()
//                ad.cancel()
//            }
//        }).start()

    }
}

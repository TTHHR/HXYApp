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
import java.io.OutputStream
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.hardware.Sensor
import java.util.*
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var MY_UUID = "00001101-0000-1000-8000-00805F9B34FB"
    private var blueSocket: BluetoothSocket? = null
    private var bluetoothAdapter: BluetoothAdapter? = null
    private var handler: Handler? = null
    private var out: OutputStream? = null
    private var running = true
    private var data: SanD = SanD()

    private var sensorManager: SensorManager? = null
    private val listenera = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val accx = event.values[0]
            val accy = event.values[1]
            val accz = event.values[2]
            data.accx = accx
            data.accy = accy
            data.accz = accz


        }

        override fun onAccuracyChanged(sensor: Sensor, i: Int) {

        }
    }
    private val listenerg = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val gyrox = event.values[0]
            val gyroy = event.values[1]
            val gyroz = event.values[2]
            data.gyrox = gyrox
            data.gyroy = gyroy
            data.gyroz = gyroz


        }

        override fun onAccuracyChanged(sensor: Sensor, i: Int) {

        }
    }
    private val listenerm = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val magx = event.values[0]
            val magy = event.values[1]
            val magz = event.values[2]
            data.magx = magx
            data.magy = magy
            data.magz = magz


        }

        override fun onAccuracyChanged(sensor: Sensor, i: Int) {

        }
    }






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensora = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager!!.registerListener(listenera, sensora, SensorManager.SENSOR_DELAY_GAME)
        val sensorg = sensorManager!!.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        sensorManager!!.registerListener(listenerg, sensorg, SensorManager.SENSOR_DELAY_GAME)
        val sensorm = sensorManager!!.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        sensorManager!!.registerListener(listenerm, sensorm, SensorManager.SENSOR_DELAY_GAME)



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
        handler = object : Handler() {
            override fun handleMessage(ms: Message) {
                val mess = ms.obj as String
                Log.e("mmmmmmmm", mess)
                if (mess == "connect") {
                    if (!blueSocket!!.isConnected)
                        blueSocket!!.connect()
                    try {
                        /* 获取输出流 */
                        if (out == null)
                            out = blueSocket!!.outputStream
                    } catch (e: Exception) {
                        Toast.makeText(applicationContext, "蓝牙连接失败", Toast.LENGTH_SHORT).show()
                        ad.cancel()
                        e.printStackTrace()
                    }
                    Toast.makeText(applicationContext, "蓝牙连接成功", Toast.LENGTH_SHORT).show()
                    ad.cancel()

                    Thread(Runnable {
                        val ch = charset("ascii")
                        while (running) {
                            if (out == null) {
                                val message = Message()
                                message.obj = "蓝牙连接中断"
                                handler!!.sendMessage(message)
                                running = false
                                break
                            }
                            val message = Message()
                            message.obj = "ui"
                            handler!!.sendMessage(message)
                            out!!.write(data.getData().toByteArray(ch))
                            out!!.flush()
                            Thread.sleep(1000)
                        }

                    }).start()
                } else if (mess == "ui") {
                    accxt.text = data.accx.toString()
                    accyt.text = data.accy.toString()
                    acczt.text = data.accz.toString()
                    gyroxt.text = data.gyrox.toString()
                    gyroyt.text = data.gyroy.toString()
                    gyrozt.text = data.gyroz.toString()
                    magxt.text = data.magx.toString()
                    magyt.text = data.magy.toString()
                    magzt.text = data.magz.toString()
                } else {
                    Toast.makeText(applicationContext, mess, Toast.LENGTH_LONG).show()
                }
            }
        }
        Thread(Runnable {
            try {
                val i = intent
                val address: String = i.getStringExtra("address")
                val btDev: BluetoothDevice
                btDev = bluetoothAdapter!!.getRemoteDevice(address)
                Thread.sleep(500)
                val uuid = UUID.fromString(MY_UUID)
                blueSocket = btDev.createRfcommSocketToServiceRecord(uuid)
                if (blueSocket == null)
                    blueSocket!!.connect()
                val message = Message()
                message.obj = "connect"
                handler!!.sendMessage(message)
            } catch (e: Exception) {
                Log.e("error", e.toString())
                Toast.makeText(applicationContext, "蓝牙连接失败", Toast.LENGTH_SHORT).show()
                ad.cancel()
            }
        }).start()

    }

    override fun onDestroy() {
        super.onDestroy()
        if (sensorManager != null) {
            sensorManager!!.unregisterListener(listenera)
            sensorManager!!.unregisterListener(listenerg)
            sensorManager!!.unregisterListener(listenerm)
        }
        running = false
        if (blueSocket != null) {
            blueSocket!!.close()
        }
    }
}

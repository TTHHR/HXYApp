package cn.qingyuyu.hxyapp

/**
 * Created by Administrator on 2017\8\5 0005.
 */
class SanD {
    var accx: Float = 0.0f
    var accy: Float = 0.0f
    var accz: Float = 0.0f

    var gyrox: Float = 0.0f
    var gyroy: Float = 0.0f
    var gyroz: Float = 0.0f

    var magx: Float = 0.0f
    var magy: Float = 0.0f
    var magz: Float = 0.0f

    fun getData(): String {
        return "" + accx + ":" + accy + ":" + accz + ":" + gyrox + ":" + gyroy + ":" + gyroz + ":" + magx + ":" + magy + ":" + magz + ":"
    }
}

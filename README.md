# HXYApp
一个简单的蓝牙传输IMU数据
 a sample bluetooth app for send imu data
 
 附上arduino的接收代码
 
 #include <SoftwareSerial.h>  
 SoftwareSerial mySerial(12,13);  
 void setup()
 {   
 Serial.begin(9600);  
 mySerial.begin(38400);
 } 
 void loop()
 {   
 if (mySerial.available() > 0)
 {   
 Serial.println(mySerial.readStringUntil(':')); 
 } 
 }

/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class dc_library_auto_serial_SerialPort */

#ifndef _Included_dc_library_auto_serial_SerialPort
#define _Included_dc_library_auto_serial_SerialPort
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     dc_library_auto_serial_SerialPort
 * Method:    openPort
 * Signature: (Ljava/lang/String;II)Ljava/io/FileDescriptor;
 */
JNIEXPORT jobject JNICALL Java_dc_library_auto_serial_SerialPort_openPort
  (JNIEnv *, jclass, jstring, jint, jint);

/*
 * Class:     dc_library_auto_serial_SerialPort
 * Method:    closePort
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_dc_library_auto_serial_SerialPort_closePort
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif

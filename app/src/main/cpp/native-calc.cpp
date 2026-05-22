#include <jni.h>

extern "C" {

JNIEXPORT jdouble JNICALL
Java_com_ryongma_calculator_NativeCalc_add(JNIEnv *env, jclass clazz, jdouble a, jdouble b) {
    return a + b;
}

JNIEXPORT jdouble JNICALL
Java_com_ryongma_calculator_NativeCalc_subtract(JNIEnv *env, jclass clazz, jdouble a, jdouble b) {
    return a - b;
}

}

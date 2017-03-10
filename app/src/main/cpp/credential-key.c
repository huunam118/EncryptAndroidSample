#include <jni.h>

JNIEXPORT jstring JNICALL
// must be Java_{package_and_classname}_{function_name}(JNI arguments)
// See more: https://www3.ntu.edu.sg/home/ehchua/programming/java/JavaNativeInterface.html
Java_com_namnh_credential_CredentialsAccessor_getSecretKey(JNIEnv *env, jclass type) {
    // key must be 128/192/256 bits ~ 16/24/32 bytes
    return (*env)->NewStringUTF(env, "ALIDwi24cXL5sCA2I4ANE81t19c36UaC");
}
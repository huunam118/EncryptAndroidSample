#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_namnh_credential_CredentialsAccessor_getSecretKey(JNIEnv *env, jclass type) {
    // key must be 128/192/256 bits ~ 16/24/32 bytes
    return (*env)->NewStringUTF(env, "ALIDwi24cXL5sCA2I4ANE81t19c36UaC");
}
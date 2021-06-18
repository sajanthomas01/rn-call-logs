package com.rncalllogs

import android.provider.CallLog
import com.facebook.react.bridge.*
import java.lang.Exception
import java.math.BigInteger


class RnCallLogsModule(private val reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String {
    return "RnCallLogs"
  }

  private val projection = listOf<String>(CallLog.Calls._ID, CallLog.Calls.NUMBER, CallLog.Calls.TYPE, CallLog.Calls.DURATION,
    CallLog.Calls.DATE, CallLog.Calls.COUNTRY_ISO)

  private val startEpoch: Long = 1614156464062;
  private val stopEpoch: Long = 1607944588068;

  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  @ReactMethod
  fun multiply(a: Int, b: Int, promise: Promise) {

    promise.resolve(a * b)

  }

  @ReactMethod
  fun getAllLogs(limit: Int = 0, skip: Int = 0, promise: Promise) {

    fetchCallLogs(promise);
  }

  @ReactMethod
  fun getOutgoingLogs(limit: Int = 0, skip: Int = 0, promise: Promise) {

  }

  @ReactMethod
  fun getIncomingLogs(limit: Int = 0, skip: Int = 0, promise: Promise) {

  }

  @ReactMethod
  fun getMissedLogs(limit: Int = 0, skip: Int = 0, promise: Promise) {

  }

  @ReactMethod
  fun getNotConnectedLogs(limit: Int = 0, skip: Int = 0, promise: Promise) {

  }

  private fun callTypeMaker(typeId: Int): String {
    return when(typeId){
      CallLog.Calls.INCOMING_TYPE -> "INCOMING";
      CallLog.Calls.OUTGOING_TYPE -> "OUTGOING";
      CallLog.Calls.MISSED_TYPE -> "MISSED";
      CallLog.Calls.VOICEMAIL_TYPE -> "VOICEMAIL";
      CallLog.Calls.REJECTED_TYPE -> "REJECTED";
      CallLog.Calls.BLOCKED_TYPE -> "BLOCKED";
      CallLog.Calls.ANSWERED_EXTERNALLY_TYPE -> "EXTERNAL";
      else -> ""
    }
  }

  private fun fetchCallLogs(promise: Promise) {
    try {
      val result = Arguments.createArray()

      val selectionQuery = "${CallLog.Calls.DATE} BETWEEN $stopEpoch  AND $startEpoch";
      val cursor = reactContext.contentResolver.query(CallLog.Calls.CONTENT_URI, projection.toTypedArray(), selectionQuery, null,
        "${CallLog.Calls.DATE} DESC")
      when (cursor?.count) {
        null -> {
          promise.reject("Error");
        }
        0 -> {
          promise.resolve(result);
        }
        else -> {
          System.out.println("======================COUNT==========="+cursor.count)
          cursor?.apply {
//            val nameIndex = getColumnIndex(CallLog.Calls.CACHED_NAME);
            val numberIndex = getColumnIndex(CallLog.Calls.NUMBER);
            val typeIndex = getColumnIndex(CallLog.Calls.TYPE);
            val dateIndex = getColumnIndex(CallLog.Calls.DATE);
            val durationIndex = getColumnIndex(CallLog.Calls.DURATION);
            val countryIndex = getColumnIndex(CallLog.Calls.COUNTRY_ISO);

            while (moveToNext()) {

              val logData = Arguments.createMap()

//              logData.putString("name", getString(nameIndex));
              logData.putString("number", getString(numberIndex));
              logData.putString("date", getString(dateIndex));
              logData.putString("duration", getString(durationIndex));
              logData.putString("country", getString(countryIndex));
              logData.putString("type", callTypeMaker(getInt(typeIndex)));
              result.pushMap(logData);
              // end of while loop
            }
            cursor.close();
            promise.resolve(result)
          }
          promise.resolve(result)
        }
      }
    } catch (e: Exception) {
      System.out.println("=================================")
        System.out.println("=================================")
          System.out.println(e)
            System.out.println("=================================")
              System.out.println("=================================")
      promise.reject("Error")
    }
  }
}

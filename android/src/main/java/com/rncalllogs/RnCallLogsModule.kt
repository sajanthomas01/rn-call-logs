/**
 * Author : Sajan Thomas(https://sajan.dev)
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 *
 */

package com.rncalllogs

import android.provider.CallLog
import android.util.Log
import com.facebook.react.bridge.*
import java.lang.Exception


class RnCallLogsModule(private val reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String {
    return "RnCallLogs"
  }

  private val projection = listOf<String>(
    CallLog.Calls._ID, CallLog.Calls.NUMBER, CallLog.Calls.TYPE, CallLog.Calls.DURATION,
    CallLog.Calls.DATE, CallLog.Calls.COUNTRY_ISO
  )
  private var selectionQuery: String? = null;
  private var filterSet: String? = "${CallLog.Calls.DATE} DESC";


  @ReactMethod
  fun getAllLogs(@androidx.annotation.Nullable filter: ReadableMap, promise: Promise) {
    if (queryMaker(filter, "all"))
      fetchCallLogs(promise);
  }

  @ReactMethod
  fun getOutgoingLogs(@androidx.annotation.Nullable filter: ReadableMap, promise: Promise) {
    if (queryMaker(filter, "outgoing"))
      fetchCallLogs(promise);
  }

  @ReactMethod
  fun getIncomingLogs(@androidx.annotation.Nullable filter: ReadableMap, promise: Promise) {
    if (queryMaker(filter, "incoming"))
      fetchCallLogs(promise);
  }

  @ReactMethod
  fun getMissedLogs(@androidx.annotation.Nullable filter: ReadableMap, promise: Promise) {
    if (queryMaker(filter, "missed"))
      fetchCallLogs(promise);
  }

  @ReactMethod
  fun getRejectedLogs(@androidx.annotation.Nullable filter: ReadableMap, promise: Promise) {
    if (queryMaker(filter, "rejected"))
      fetchCallLogs(promise);
  }

  @ReactMethod
  fun getBlockedLogs(@androidx.annotation.Nullable filter: ReadableMap, promise: Promise) {
    if (queryMaker(filter, "blocked"))
      fetchCallLogs(promise);
  }

  @ReactMethod
  fun getExternallyAnsweredLogs(
    @androidx.annotation.Nullable filter: ReadableMap,
    promise: Promise
  ) {
    if (queryMaker(filter, "externalAnswer"))
      fetchCallLogs(promise);
  }

  @ReactMethod
  fun getByNumber(@androidx.annotation.Nullable filter: ReadableMap, promise: Promise) {
    if (queryMaker(filter, "byNumber"))
      fetchCallLogs(promise);
  }


  @ReactMethod
  fun getNotConnectedLogs(@androidx.annotation.Nullable filter: ReadableMap, promise: Promise) {
    if (queryMaker(filter, "notConnected"))
      fetchCallLogs(promise);
  }

  //TODO: optimize the query, entire thing looks ugly although it works :D
  private fun queryMaker(filter: ReadableMap, type: String): Boolean {
    // reset things
    filterSet = "${CallLog.Calls.DATE} DESC";
    selectionQuery = null;

    when (type) {
      "notConnected" -> {
        selectionQuery =
          if (filter.getDouble("fromEpoch") > 0.0 && filter.getDouble("toEpoch") > 0.0) {
            "${CallLog.Calls.DATE} BETWEEN ${
              filter.getDouble("toEpoch").toString()
            }  AND ${filter.getDouble("fromEpoch").toString()} AND " +
              "${CallLog.Calls.DURATION} = 0 AND ${CallLog.Calls.TYPE} = ${CallLog.Calls.OUTGOING_TYPE}";

          } else {
            "${CallLog.Calls.DURATION} = 0 AND ${CallLog.Calls.TYPE} = ${CallLog.Calls.OUTGOING_TYPE}"
          }

        if (filter.hasKey("limit") && filter.getInt("limit") > 0) {
          filterSet = "$filterSet LIMIT ${filter.getInt("limit")}"
        }

        if (filter.hasKey("skip") && filter.getInt("skip") > 0) {
          filterSet = "$filterSet OFFSET ${filter.getInt("skip")}"
        }
        return true;
      }

      "byNumber" -> {

        selectionQuery =
          if (filter.getDouble("fromEpoch") > 0.0 && filter.getDouble("toEpoch") > 0.0) {
            "${CallLog.Calls.DATE} BETWEEN ${
              filter.getDouble("toEpoch").toString()
            }  AND ${filter.getDouble("fromEpoch").toString()}" +
              " AND ${CallLog.Calls.NUMBER} LIKE '%${filter.getString("phoneNumber")}%'";

          } else {
            "${CallLog.Calls.NUMBER} LIKE '%${filter.getString("phoneNumber")}%'";
          }

        if (filter.hasKey("type") && filter.getString("type").equals("INCOMING")) {
          selectionQuery =
            "$selectionQuery AND ${CallLog.Calls.TYPE} = ${CallLog.Calls.INCOMING_TYPE}"
        }

        if (filter.hasKey("type") && filter.getString("type").equals("OUTGOING")) {
          selectionQuery =
            "$selectionQuery AND ${CallLog.Calls.TYPE} = ${CallLog.Calls.OUTGOING_TYPE}"
        }

        if (filter.hasKey("type") && filter.getString("type").equals("MISSED")) {
          selectionQuery =
            "$selectionQuery AND ${CallLog.Calls.TYPE} = ${CallLog.Calls.MISSED_TYPE}"
        }

        if (filter.hasKey("type") && filter.getString("type").equals("VOICEMAIL")) {
          selectionQuery =
            "$selectionQuery AND ${CallLog.Calls.TYPE} = ${CallLog.Calls.VOICEMAIL_TYPE}"
        }

        if (filter.hasKey("type") && filter.getString("type").equals("REJECTED")) {
          selectionQuery =
            "$selectionQuery AND ${CallLog.Calls.TYPE} = ${CallLog.Calls.REJECTED_TYPE}"
        }

        if (filter.hasKey("type") && filter.getString("type").equals("BLOCKED")) {
          selectionQuery =
            "$selectionQuery AND ${CallLog.Calls.TYPE} = ${CallLog.Calls.BLOCKED_TYPE}"
        }

        if (filter.hasKey("type") && filter.getString("type").equals("EXTERNAL")) {
          selectionQuery =
            "$selectionQuery AND ${CallLog.Calls.TYPE} = ${CallLog.Calls.ANSWERED_EXTERNALLY_TYPE}"
        }

        if (filter.hasKey("limit") && filter.getInt("limit") > 0) {
          filterSet = "$filterSet LIMIT ${filter.getInt("limit")}"
        }

        if (filter.hasKey("skip") && filter.getInt("skip") > 0) {
          filterSet = "$filterSet OFFSET ${filter.getInt("skip")}"
        }
        return true;
      }

      "externalAnswer" -> {
        selectionQuery =
          if (filter.getDouble("fromEpoch") > 0.0 && filter.getDouble("toEpoch") > 0.0) {
            "${CallLog.Calls.DATE} BETWEEN ${
              filter.getDouble("toEpoch").toString()
            }  AND ${filter.getDouble("fromEpoch").toString()} AND " +
              "${CallLog.Calls.TYPE} = ${CallLog.Calls.ANSWERED_EXTERNALLY_TYPE}";

          } else {
            "${CallLog.Calls.TYPE} = ${CallLog.Calls.ANSWERED_EXTERNALLY_TYPE}"
          }

        if (filter.hasKey("limit") && filter.getInt("limit") > 0) {
          filterSet = "$filterSet LIMIT ${filter.getInt("limit")}"
        }

        if (filter.hasKey("skip") && filter.getInt("skip") > 0) {
          filterSet = "$filterSet OFFSET ${filter.getInt("skip")}"
        }
        return true;
      }

      "blocked" -> {
        selectionQuery =
          if (filter.getDouble("fromEpoch") > 0.0 && filter.getDouble("toEpoch") > 0.0) {
            "${CallLog.Calls.DATE} BETWEEN ${
              filter.getDouble("toEpoch").toString()
            }  AND ${filter.getDouble("fromEpoch").toString()} AND " +
              "${CallLog.Calls.TYPE} = ${CallLog.Calls.BLOCKED_TYPE}";

          } else {
            "${CallLog.Calls.TYPE} = ${CallLog.Calls.BLOCKED_TYPE}"
          }

        if (filter.hasKey("limit") && filter.getInt("limit") > 0) {
          filterSet = "$filterSet LIMIT ${filter.getInt("limit")}"
        }

        if (filter.hasKey("skip") && filter.getInt("skip") > 0) {
          filterSet = "$filterSet OFFSET ${filter.getInt("skip")}"
        }
        return true;
      }

      "rejected" -> {
        selectionQuery =
          if (filter.getDouble("fromEpoch") > 0.0 && filter.getDouble("toEpoch") > 0.0) {
            "${CallLog.Calls.DATE} BETWEEN ${
              filter.getDouble("toEpoch").toString()
            }  AND ${filter.getDouble("fromEpoch").toString()} AND " +
              "${CallLog.Calls.TYPE} = ${CallLog.Calls.REJECTED_TYPE}";

          } else {
            "${CallLog.Calls.TYPE} = ${CallLog.Calls.REJECTED_TYPE}"
          }

        if (filter.hasKey("limit") && filter.getInt("limit") > 0) {
          filterSet = "$filterSet LIMIT ${filter.getInt("limit")}"
        }

        if (filter.hasKey("skip") && filter.getInt("skip") > 0) {
          filterSet = "$filterSet OFFSET ${filter.getInt("skip")}"
        }
        return true;
      }

      "missed" -> {
        selectionQuery =
          if (filter.getDouble("fromEpoch") > 0.0 && filter.getDouble("toEpoch") > 0.0) {
            "${CallLog.Calls.DATE} BETWEEN ${
              filter.getDouble("toEpoch").toString()
            }  AND ${filter.getDouble("fromEpoch").toString()} AND " +
              "${CallLog.Calls.TYPE} = ${CallLog.Calls.MISSED_TYPE}";

          } else {
            "${CallLog.Calls.TYPE} = ${CallLog.Calls.MISSED_TYPE}"
          }

        if (filter.hasKey("limit") && filter.getInt("limit") > 0) {
          filterSet = "$filterSet LIMIT ${filter.getInt("limit")}"
        }

        if (filter.hasKey("skip") && filter.getInt("skip") > 0) {
          filterSet = "$filterSet OFFSET ${filter.getInt("skip")}"
        }
        return true;
      }

      "incoming" -> {
        selectionQuery =
          if (filter.getDouble("fromEpoch") > 0.0 && filter.getDouble("toEpoch") > 0.0) {
            "${CallLog.Calls.DATE} BETWEEN ${
              filter.getDouble("toEpoch").toString()
            }  AND ${filter.getDouble("fromEpoch").toString()} AND " +
              "${CallLog.Calls.TYPE} = ${CallLog.Calls.INCOMING_TYPE}";

          } else {
            "${CallLog.Calls.TYPE} = ${CallLog.Calls.INCOMING_TYPE}"
          }

        if (filter.hasKey("limit") && filter.getInt("limit") > 0) {
          filterSet = "$filterSet LIMIT ${filter.getInt("limit")}"
        }

        if (filter.hasKey("skip") && filter.getInt("skip") > 0) {
          filterSet = "$filterSet OFFSET ${filter.getInt("skip")}"
        }
        return true;
      }

      "outgoing" -> {
        selectionQuery =
          if (filter.getDouble("fromEpoch") > 0.0 && filter.getDouble("toEpoch") > 0.0) {
            "${CallLog.Calls.DATE} BETWEEN ${
              filter.getDouble("toEpoch").toString()
            }  AND ${filter.getDouble("fromEpoch").toString()} AND " +
              "${CallLog.Calls.TYPE} = ${CallLog.Calls.OUTGOING_TYPE}";

          } else {
            "${CallLog.Calls.TYPE} = ${CallLog.Calls.OUTGOING_TYPE}"
          }

        if (filter.hasKey("limit") && filter.getInt("limit") > 0) {
          filterSet = "$filterSet LIMIT ${filter.getInt("limit")}"
        }

        if (filter.hasKey("skip") && filter.getInt("skip") > 0) {
          filterSet = "$filterSet OFFSET ${filter.getInt("skip")}"
        }
        return true;
      }

      "all" -> {
        selectionQuery =
          if (filter.getDouble("fromEpoch") > 0.0 && filter.getDouble("toEpoch") > 0.0) {
            "${CallLog.Calls.DATE} BETWEEN ${
              filter.getDouble("toEpoch").toString()
            }  AND ${filter.getDouble("fromEpoch").toString()}";

          } else {
            ""
          }

        if (filter.hasKey("limit") && filter.getInt("limit") > 0) {
          filterSet = "$filterSet LIMIT ${filter.getInt("limit")}"
        }

        if (filter.hasKey("skip") && filter.getInt("skip") > 0) {
          filterSet = "$filterSet OFFSET ${filter.getInt("skip")}"
        }
        return true;
      }

    }
    return false;
  }


  private fun callTypeMaker(typeId: Int): String {
    return when (typeId) {
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

      val cursor = reactContext.contentResolver.query(
        CallLog.Calls.CONTENT_URI, projection.toTypedArray(), selectionQuery, null,
        filterSet
      )
      when (cursor?.count) {
        null -> {
          promise.reject("Error");
        }
        0 -> {
          promise.resolve(result);
        }
        else -> {
          cursor?.apply {
            val numberIndex = getColumnIndex(CallLog.Calls.NUMBER);
            val typeIndex = getColumnIndex(CallLog.Calls.TYPE);
            val dateIndex = getColumnIndex(CallLog.Calls.DATE);
            val durationIndex = getColumnIndex(CallLog.Calls.DURATION);
            val countryIndex = getColumnIndex(CallLog.Calls.COUNTRY_ISO);

            while (moveToNext()) {

              val logData = Arguments.createMap()

              logData.putString("number", getString(numberIndex));
              logData.putString("date", getString(dateIndex));
              logData.putString("duration", getString(durationIndex));
              logData.putString("country", getString(countryIndex));
              logData.putString("type", callTypeMaker(getInt(typeIndex)));
              result.pushMap(logData);

            }
            cursor.close();
            promise.resolve(result)
          }
          promise.resolve(result)
        }
      }
    } catch (e: Exception) {
      Log.d("RN-CALL-LOG-ERROR", e.message)
      promise.reject(e.message)
    }
  }
}

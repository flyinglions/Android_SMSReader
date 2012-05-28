package org.flying.lions;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import com.phonegap.api.Plugin;
import com.phonegap.api.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SMSReader extends Plugin {



    @Override
    public PluginResult execute(String action, JSONArray data, String callbackId) {
        Log.d("SMSReadPlugin", "Plugin Called");
        PluginResult result = null;
        JSONObject messages = new JSONObject();
        if (action.equals("inbox")) {
            try {
                messages = readSMS("inbox");
                Log.d("SMSReadPlugin", "Returning " + messages.toString());
                result = new PluginResult(PluginResult.Status.OK, messages);
            } catch (JSONException jsonEx) {
                Log.d("SMSReadPlugin", "Got JSON Exception "+ jsonEx.getMessage());
                result = new PluginResult(PluginResult.Status.JSON_EXCEPTION);
            }
        }
        else if(action.equals("sent")){
             try {
                messages = readSMS("sent");
                Log.d("SMSReadPlugin", "Returning " + messages.toString());
                result = new PluginResult(PluginResult.Status.OK, messages);
            } catch (JSONException jsonEx) {
                Log.d("SMSReadPlugin", "Got JSON Exception "+ jsonEx.getMessage());
                result = new PluginResult(PluginResult.Status.JSON_EXCEPTION);
            }
        }
        else {
            result = new PluginResult(PluginResult.Status.INVALID_ACTION);
            Log.d("SMSReadPlugin", "Invalid action : "+action+" passed");
        }
        return result;
    }


    //Read messages from inbox/or sent box.
    private JSONObject readSMS(String folder) throws JSONException {
        JSONObject data = new JSONObject();
        Uri uriSMSURI = Uri.parse("");
        if(folder.equals("inbox")){
            uriSMSURI = Uri.parse("content://sms/inbox");
        }
        else if(folder.equals("sent")){
                uriSMSURI = Uri.parse("content://sms/sent");
        }
        Cursor cur = getContentResolver().query(uriSMSURI, null, null, null,null);
        JSONArray smsList = new JSONArray();
        data.put("messages", smsList);
        while (cur.moveToNext()) {
          JSONObject sms = new JSONObject();
            sms.put("number",cur.getString(2));
            sms.put("text",cur.getString(11));

            smsList.put(sms);
        }
        return data;
    }

   
    private ContentResolver getContentResolver(){
       return this.ctx.getContentResolver();
    }



}
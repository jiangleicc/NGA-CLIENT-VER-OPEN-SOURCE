package sp.phone.task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import gov.anzong.androidnga.Utils;
import sp.phone.common.PhoneConfiguration;
import sp.phone.utils.ActivityUtils;
import sp.phone.utils.HttpUtil;
import sp.phone.utils.StringUtils;

/**
 * 版块列表
 * Created by elrond on 2017/9/28.
 */

public class GetAllForumsTask extends AsyncTask<String, Integer, String> {
    private static final String URL = "app_api.php?__lib=home&__act=category";
    private Context context;
    private String mUrl;

    public GetAllForumsTask(Context context) {
        this.context = context;
        mUrl = Utils.getNGAHost() + URL;
    }

    @Override
    protected String doInBackground(String... params) {
        String js = HttpUtil.getHtml(mUrl, PhoneConfiguration.getInstance().getCookie());
        return js;
    }

    @Override
    protected void onPreExecute() {
        ActivityUtils.getInstance().noticeSaying(context);
    }

    @Override
    protected void onPostExecute(String result) {
        ActivityUtils.getInstance().dismiss();
        if (StringUtils.isEmpty(result))
            return;

        String msg = StringUtils.getStringBetween(result, 0, "{\"0\":\"", "\"").result;
        if (!StringUtils.isEmpty(msg)) {
            Toast.makeText(context, msg.trim(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCancelled(String result) {
        this.onCancelled();
    }

    @Override
    protected void onCancelled() {
        ActivityUtils.getInstance().dismiss();
    }
}
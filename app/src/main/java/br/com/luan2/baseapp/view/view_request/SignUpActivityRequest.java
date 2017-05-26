package br.com.luan2.baseapp.view.view_request;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.github.rrsystems.utilsplus.android.UtilsPlus;

import java.util.HashMap;
import java.util.Map;

import br.com.luan2.baseapp.extras.CustomGsonAdapter;
import br.com.luan2.baseapp.model.User;
import br.com.luan2.baseapp.model.request.BaseRequest;
import br.com.luan2.baseapp.retrofit.ApiManager;
import br.com.luan2.baseapp.retrofit.CustomCallback;
import br.com.luan2.baseapp.retrofit.RequestInterfaceUser;
import br.com.luan2.baseapp.view.MainActivity;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Luan on 16/05/17.
 */

public class SignUpActivityRequest {

private Activity activity;
private Context context;

public SignUpActivityRequest(Context context, Activity activity){
    this.context = context;
    this.activity = activity;
}

    public void create() {
//        User auth = new Utils().getShared(context);
        User auth = new User();
        if (auth == null) {
            activity.finish();
        }

        String json = new CustomGsonAdapter().serialize(auth, "user");
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Token token=" + auth.getAuthentication());

        new ApiManager(context)
                .getRetrofit()
                .create(RequestInterfaceUser.class)
                .contractRefuse(header,"")
                .enqueue(new CustomCallback<BaseRequest>(activity, new CustomCallback.OnResponse<BaseRequest>() {
                    @Override
                    public void onResponse(BaseRequest response) {
                            Log.d("", "onResponse: ");
//                            new Utils().updateShared(context,response);
                            UtilsPlus.getInstance().toast("Cadastro realizado com sucesso", 5);
                            activity.finishAffinity();
                            activity.startActivity(new Intent(context, MainActivity.class));

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRetry(Throwable t) {
                        create();
                    }
                }));

    }


}

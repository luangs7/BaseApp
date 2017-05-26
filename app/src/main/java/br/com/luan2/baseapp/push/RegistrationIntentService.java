package br.com.luan2.baseapp.push;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.github.rrsystems.utilsplus.android.UtilsPlus;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

import br.com.luan2.baseapp.dao.LocalDbImplement;
import br.com.luan2.baseapp.extras.CustomGsonAdapter;
import br.com.luan2.baseapp.model.User;
import br.com.luan2.baseapp.retrofit.ApiManager;
import br.com.luan2.baseapp.retrofit.CustomCallback;
import br.com.luan2.baseapp.retrofit.RequestInterfaceUser;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Luan on 22/05/17.
 */

public class RegistrationIntentService  extends IntentService {
    public static final String LOG = "LOG";
    public Context context;

    public RegistrationIntentService() {
        super(LOG);
    }
    String refreshedToken;

    @Override
    protected void onHandleIntent(Intent intent) {

        synchronized (LOG) {
            try {

                String deviceid = new UtilsPlus().getDeviceID();
                 refreshedToken = FirebaseInstanceId.getInstance().getToken();
                context = this;
                //implementar request aqui
//                User user = new Utils().getShared(context);
//                if(!refreshedToken.equalsIgnoreCase(user.getDevice()))
                    register(refreshedToken);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void register(final String token) {
//        User auth = new Utils().getShared(this);
        User auth = new User();
        if (auth != null) {
            auth.setDevice(token);
            String json = new CustomGsonAdapter().serialize(auth, "user");
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

            Map<String, String> header = new HashMap<>();
            header.put("Authorization", "Token token=" + auth.getAuthentication());

            new ApiManager(this)
                    .getRetrofit()
                    .create(RequestInterfaceUser.class)
                    .recovery(header,body)
                    .enqueue(new CustomCallback<User>(context, new CustomCallback.OnResponse<User>() {
                        @Override
                        public void onResponse(User response) {
                            User user = response;
                            user.setDevice(refreshedToken);

                            new LocalDbImplement<User>(context).save(user);

                        }

                        @Override
                        public void onFailure(Throwable t) {
//                            Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onRetry(Throwable t) {
                            register(token);
                        }
                    },false));

        }
    }
}


package br.com.luan2.baseapp.retrofit;


import java.util.Map;

import br.com.luan2.baseapp.model.User;
import br.com.luan2.baseapp.model.request.BaseRequest;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Luan on 29/07/2016.
 */
public interface RequestInterfaceUser {

    //   <--------------------------------  POST  -------------------------------->


    @POST("users/password")
    Call<User> recovery(@HeaderMap Map<String, String> headers, @Body RequestBody json);

    @POST("users/update-avatar")
    @Multipart
    Call<User> updImage(@HeaderMap Map<String, String> headers, @Part MultipartBody.Part form);


//   <--------------------------------  GET  -------------------------------->



    @GET("contracts/{contract_id}/refuse")
    Call<BaseRequest> contractRefuse(@HeaderMap Map<String, String> headers, @Path("contract_id") String id);
}

package Controllers;

import Models.GoogleAccount;
import Helpers.ConfigLoader;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Form;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class GoogleLogin {

    private static final String CLIENT_ID = "XXXX";
    private static final String CLIENT_SECRET = "YYYY";
    private static final String REDIRECT_URI = ConfigLoader.get("google.redirect.uri");
    private static final String GRANT_TYPE = ConfigLoader.get("google.grant.type");
    private static final String LINK_GET_TOKEN = ConfigLoader.get("google.token.link");
    private static final String LINK_GET_USER_INFO = ConfigLoader.get("google.userinfo.link");

    public static String getToken(String code) throws ClientProtocolException, IOException {
        String response = Request.Post(LINK_GET_TOKEN)
                .bodyForm(Form.form()
                        .add("client_id", CLIENT_ID)    
                        .add("client_secret", CLIENT_SECRET)
                        .add("redirect_uri", REDIRECT_URI)
                        .add("code", code)
                        .add("grant_type", GRANT_TYPE)
                        .build())
                .execute()
                .returnContent()
                .asString();

        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        return jobj.get("access_token").getAsString();
    }

    public static GoogleAccount getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String response = Request.Get(LINK_GET_USER_INFO + accessToken)
                                 .execute()
                                 .returnContent()
                                 .asString();

        GoogleAccount googlePojo = new Gson().fromJson(response, GoogleAccount.class);

        System.out.println("AccessToken = " + accessToken);

        return googlePojo;
    }
}

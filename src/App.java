import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class App {

    private static final String BASE_URL = "https://self-signed.badssl.com";
    private static final String USERNAME = "";
    private static final String PASSWORD = "";
    private static final String TOKEN = "";
    private static final String METHOD = "GET";
    private static final String AUTH_TYPE = "";

    private static final String URL_PARAMETERS = "";

    public static void main(String[] args) throws Exception {

        byte[] postData = URL_PARAMETERS.getBytes(StandardCharsets.UTF_8);

        String userPass = USERNAME + ":" + PASSWORD;

        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });

        URL url = new URL(BASE_URL);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

        connection.setSSLSocketFactory(SSLContextBuilder.loadSSLContext());

        if ("Basic".equals(AUTH_TYPE)) {

            String basicAuth = "Basic "
                    + new String(Base64.getEncoder().encode(userPass.getBytes(StandardCharsets.UTF_8)));
            connection.setRequestProperty("Authorization", basicAuth);

        } else if ("Bearer".equals(AUTH_TYPE)) {

            String bearerAuth = "Bearer " + TOKEN;
            connection.setRequestProperty("Authorization", bearerAuth);

        }

        connection.setDoOutput(true);
        connection.setRequestMethod(METHOD);

        if ("POST".equals(METHOD)) {
            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.write(postData);
            }
        }

        int responseCode = connection.getResponseCode();
        System.out.println(METHOD + " Response Code :: " + responseCode);

        if (responseCode == HttpsURLConnection.HTTP_OK || responseCode == HttpsURLConnection.HTTP_CREATED) {

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println(String.valueOf(response));

            System.out.println(METHOD + " request SUCCESS");

        } else {

            System.out.println(METHOD + " request FAILED");
        }
    }

}
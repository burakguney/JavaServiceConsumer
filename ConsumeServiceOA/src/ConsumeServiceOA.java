import com.evam.sdk.outputaction.AbstractOutputAction;
import com.evam.sdk.outputaction.IOMParameter;
import com.evam.sdk.outputaction.OutputActionContext;
import com.evam.sdk.outputaction.model.DesignerMetaParameters;
import com.evam.sdk.outputaction.model.ReturnParameter;
import com.evam.sdk.outputaction.model.ReturnType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;

//import java.net.HttpURLConnection;
import javax.net.ssl.HttpsURLConnection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ConsumeServiceOA extends AbstractOutputAction {

    private static final Logger logger = LoggerFactory.getLogger(ConsumeServiceOA.class);

    //  Definitions for getParameters method
    private static final String BASE_URL = "baseUrl";
    private static final String USERNAME = "userName";
    private static final String PASSWORD = "password";
    private static final String TOKEN = "token";
    private static final String METHOD = "method";
    private static final String AUTH_TYPE = "authType";
    private static final String URL_PARAMETER = "urlParameter";
    //  END

    //  Definitions for getRetParams method
    private static String RESULT = "result";
    // END

    private static final String ACTION_DESCRIPTION = "Consuming a rest service.";

    public ConsumeServiceOA() {
    }

    @Override
    public int execute(OutputActionContext outputActionContext) throws Exception {
        // Evam parameters are assigned to variables here.
        String baseUrl = (String) outputActionContext.getParameter(BASE_URL);
        String userName = (String) outputActionContext.getParameter(USERNAME);
        String password = (String) outputActionContext.getParameter(PASSWORD);
        String token = (String) outputActionContext.getParameter(TOKEN);
        String method = (String) outputActionContext.getParameter(METHOD);
        String authType = (String) outputActionContext.getParameter(AUTH_TYPE);
        String urlParameter = (String) outputActionContext.getParameter(URL_PARAMETER);
        // END

        String userPass = userName + ":" + password; //For basic auth

        if ("GET".equals(method)) {

            URL url = new URL(baseUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            if ("Basic".equals(authType)) {

                String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userPass.getBytes(StandardCharsets.UTF_8)));
                connection.setRequestProperty("Authorization", basicAuth);

            } else if ("Bearer".equals(authType)) {

                String bearerAuth = "Bearer " + token;
                connection.setRequestProperty("Authorization", bearerAuth);

            }

            connection.setRequestMethod(method);

            int responseCode = connection.getResponseCode();
            logger.info("GET Response Code :: " + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                    response.append(System.lineSeparator());
                }
                in.close();

                outputActionContext.getReturnMap().put(RESULT, response);

                logger.info(String.valueOf(response));

                logger.info(outputActionContext.getReturnMap().get(RESULT).toString());

                logger.info("GET request SUCCESS");

            } else {

                logger.info("GET request FAILED");
            }
        } else if ("POST".equals(method)) {

            URL url = new URL(baseUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            if ("Basic".equals(authType)) {

                String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userPass.getBytes(StandardCharsets.UTF_8)));
                connection.setRequestProperty("Authorization", basicAuth);

            } else if ("Bearer".equals(authType)) {

                String bearerAuth = "Bearer " + token;
                connection.setRequestProperty("Authorization", bearerAuth);

            }

            connection.setRequestMethod(method);

            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            os.write(urlParameter.getBytes());
            os.flush();
            os.close();


            int responseCode = connection.getResponseCode();
            logger.info("POST Response Code :: " + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK || responseCode == HttpsURLConnection.HTTP_CREATED) {

                BufferedReader in = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                    response.append(System.lineSeparator());
                }
                in.close();

                logger.info(String.valueOf(response));

                logger.info("POST request SUCCESS.");

            } else {

                logger.info("POST request FAILED.");
            }
        }

        return 0;
    }

    @Override
    protected List<IOMParameter> getParameters() {
        ArrayList<IOMParameter> parameters = new ArrayList<>();

        parameters.add(new IOMParameter(BASE_URL, "Type base url here."));
        parameters.add(new IOMParameter(USERNAME, "Type username here."));
        parameters.add(new IOMParameter(PASSWORD, "Type password here."));
        parameters.add(new IOMParameter(TOKEN, "Type token here."));
        parameters.add(new IOMParameter(METHOD, "Type method here."));
        parameters.add(new IOMParameter(AUTH_TYPE, "Auth type, 'Basic' or 'Bearer'."));
        parameters.add(new IOMParameter(URL_PARAMETER, "Type url parameter here"));

        return parameters;
    }

    @Override
    public boolean actionInputStringShouldBeEvaluated() {
        return false;
    }

    @Override
    public String getVersion() {
        return "v1.0.1";
    }

    @Override
    public String getDescription() {
        return ACTION_DESCRIPTION;
    }

    @Override
    public boolean isReturnable() {
        return true;
    }

    @Override
    public ReturnParameter[] getRetParams(DesignerMetaParameters parameters) {
        return new ReturnParameter[]{new ReturnParameter(RESULT, ReturnType.String)};
    }
}



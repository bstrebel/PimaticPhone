package de.digitec.pimatic.api;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Properties;

import de.digitec.pimatic.utils.Debug;
import de.digitec.pimatic.utils.Secret;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class PimaticApiAndroidTest {

    Session client = null;
    Secret secret = null;
    String tag = "API";

    @Before
    public void getEnvironment() {
        secret = Debug.getSecret();
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        Debug.d("API", appContext.getPackageName());
        assertEquals("de.digitec.pimatic.api.test", appContext.getPackageName());
    }

    @Test
    public void create() throws java.lang.Exception {

        Session client = new Session(
                secret.getServer(),
                secret.getUsername(),
                secret.getPassword()
        );
        assertEquals(client.getUrl(),secret.getServer());
    }

    /**
     * Test login/logout with session cookie
     * @throws Exception
     */
    @Test
    public void login() throws java.lang.Exception {

        Session client = new Session(secret.getServer());

        Response response = client.login(secret.getUsername(), secret.getPassword());
        Debug.d(tag, "[login] " + response);
        assertEquals("Login failed!", response.success(), true);
        assertEquals("Invalid client status!",client.authenticated(), true);

        response = client.logout();
        Debug.d(tag, "[logout] " + response);
        assertEquals("Invalid client status!",client.authenticated(), false);
    }

    /**
     * Test GET request with basic authentication
     * @throws Exception
     */
    @Test
    public void config() throws java.lang.Exception {
        Response response = null;
        Session client = new Session(secret.getServer(), secret.getUsername(), secret.getPassword());
        // String response = client.login(username, password);
        Boolean authenticated = client.authenticated();
        response = client.get("/api/config");
        Debug.d(tag, "[config] " + response);
    }

    @Test
    public void getVariable() throws java.lang.Exception {
        String variable = "PimaticApiTest";
        Session client = new Session(secret.getServer(), secret.getUsername(), secret.getPassword());
        Response response = client.get("/api/variables/" + variable);
        Debug.d(tag, "[variable] " + response);
    }

    @Test
    public void setVariable() throws java.lang.Exception {
        Session client = new Session(secret.getServer(), secret.getUsername(), secret.getPassword());
        String variable = "PimaticApiTest";
        HashMap<String, Object> parms = new HashMap<>();
        parms.put("type", "value");
        parms.put("valueOrExpression", "NEW_VALUE");
        Response response = client.patch("/api/variables/" + variable, parms);
        Debug.d(tag, response.toString());
    }

    @Test
    public void postRequest() throws java.lang.Exception {
        Session client = new Session(secret.getServer());
        HashMap<String, Object> parms = new HashMap<>();
        parms.put("username", secret.getUsername());
        parms.put("password", secret.getPassword());
        Response response = client.post("/login", parms);
        Debug.d(tag, response.toString());
    }
}

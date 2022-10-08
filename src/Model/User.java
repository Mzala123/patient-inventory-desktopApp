/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import patient.inventory.desktop.PatientInventoryDesktop;
import static patient.inventory.desktop.PatientInventoryDesktop.Base_URL;

/**
 *
 * @author agile systems
 */
public class User {

    private String useremail;
    private String username;
    private String password;
    private String promptMessage;

    public User() {
    }
    
    

    public User(String useremail, String username, String password, String promptMessage) {
        this.useremail = useremail;
        this.username = username;
        this.password = password;
        this.promptMessage = promptMessage;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPromptMessage() {
        return promptMessage;
    }

    public void setPromptMessage(String promptMessage) {
        this.promptMessage = promptMessage;
    }

    public void sign_in() throws JSONException {
        String url = Base_URL + "/login";
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("username", getUsername()));
            params.add(new BasicNameValuePair("password", getPassword()));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            
            CloseableHttpResponse response = client.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            String res = new String();
            res = EntityUtils.toString(responseEntity);
            JSONObject json = new JSONObject(res);
            System.out.println("The json is "+json);
            if(json.getString("status").equals("success")){
                System.out.println("logged in successfully");   
            }else if(json.getString("status").equals("error")){
               System.err.println("Incorrect credentials");   
            }

            client.close();
        } catch (IOException ex) {

        }
    }

}

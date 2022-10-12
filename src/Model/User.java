/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.LoginController;
import static Controller.LoginController.login_details;
import static Controller.UserListController.userList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
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
    private String primary_id;
    private String avatar;
    private String phonenumber;
    private String role;
    private String user_type;

    public User() {
    }

    public User(String useremail, String username, String password, String promptMessage) {
        this.useremail = useremail;
        this.username = username;
        this.password = password;
        this.promptMessage = promptMessage;
    }

    public User(String useremail, String username, String primary_id, String avatar, String phonenumber, String role, String user_type) {
        this.useremail = useremail;
        this.username = username;
        this.primary_id = primary_id;
        this.avatar = avatar;
        this.phonenumber = phonenumber;
        this.role = role;
        this.user_type = user_type;
    }

    public User(String useremail, String username, String primary_id, String phonenumber, String role, String avatar) {
        this.useremail = useremail;
        this.username = username;
        this.primary_id = primary_id;
        this.avatar = avatar;
        this.phonenumber = phonenumber;
        this.role = role;
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

    public String getPrimary_id() {
        return primary_id;
    }

    public void setPrimary_id(String primary_id) {
        this.primary_id = primary_id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public void sign_in() throws JSONException {
        String url = Base_URL + "/login";
        SwitchWindow window = new SwitchWindow();
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
            System.out.println("The json is " + json);
            if (json.getString("status").equals("success")) {
                String access_token = json.getString("token");
                login_details.add(access_token);
                window.loadNewWindow("/View/UserList.fxml", "", true, true);
                LoginController.tempLoginStackpane.getScene().getWindow().hide();
                System.out.println("logged in successfully");
            } else if (json.getString("status").equals("error")) {
                System.err.println("Incorrect credentials");
            }

            client.close();
        } catch (IOException ex) {

        }
    }

    public void user_list() throws JSONException {
        String url = Base_URL + "/users";
        String access_token = login_details.get(0);
        System.out.println(access_token);
        JSONObject json = new JSONObject();
        String res = "";
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Authorization", "Bearer " + access_token);
            CloseableHttpResponse response = client.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            res = EntityUtils.toString(responseEntity);
            json = new JSONObject(res);
            //System.out.println(json);
            JSONObject attributes = json.getJSONObject("data");
            JSONArray data = attributes.getJSONArray("data");
            
           if (json.getString("status").equals("success")) {
                 userList.clear();
                for (int i = 0; i < data.length(); i++) {
                    if (data.get(i) instanceof JSONObject) {
                        JSONObject jsnObj = (JSONObject) data.get(i);

                        String role = (String) jsnObj.getJSONObject("attributes").get("role");
                       

                        String id = (String) jsnObj.getJSONObject("attributes").get("id").toString();
//                        if (!jsnObj.isNull((String) jsnObj.getJSONObject("attributes").get("id").toString())) {
//                            id = 
//                            setPrimary_id(id);
//                        } else {
//                        }

                        String username = (String) jsnObj.getJSONObject("attributes").get("username");
//                        if (!jsnObj.isNull((String) jsnObj.getJSONObject("attributes").get("username"))) {
//                            username = (String) jsnObj.getJSONObject("attributes").get("username");
//                            setUsername(username);
//                        } else {
//                        }

                        String phonenumber = (String) jsnObj.getJSONObject("attributes").getString("phone");
//                        if (!jsnObj.isNull((String) jsnObj.getJSONObject("attributes").getString("phone"))) {
//                            phonenumber = (String) jsnObj.getJSONObject("attributes").getString("phone");
//                            setPhonenumber(phonenumber);
//                        } else {
//
//                        }

                        String email = (String) jsnObj.getJSONObject("attributes").getString("email");
//                        if (!jsnObj.isNull((String) jsnObj.getJSONObject("attributes").getString("email"))) {
//                            email = (String) jsnObj.getJSONObject("attributes").getString("email");
//                            setUseremail(email);
//                        } else {
//
//                        }

                        String avatar = (String) jsnObj.getJSONObject("attributes").get("avatar");
//                        if (!jsnObj.isNull((String) jsnObj.getJSONObject("attributes").get("avatar"))) {
//                            avatar = (String) jsnObj.getJSONObject("attributes").get("avatar");
//                            setAvatar(avatar);
//                        } else {
//
//                        }
                        System.out.println("The email is "+email);
                        userList.addAll(new User(email, username, id, phonenumber, role, avatar));
                    }
                    
                }
            }
            client.close();
        } catch (IOException ex) {

        }
    }

}

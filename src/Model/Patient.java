/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

import static patient.inventory.desktop.PatientInventoryDesktop.Base_URL;

/**
 *
 * @author agile systems
 */
public class Patient {
    
    String firstname;
    String lastname;
    String gender;
    String district;
    String village;
    String occupation;
    String birthdate;

    public Patient(String firstname, String lastname, String gender, String district, String village, String occupation, String birthdate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.district = district;
        this.village = village;
        this.occupation = occupation;
        this.birthdate = birthdate;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
    
   
    public void create_patient_record(){
        String url = Base_URL+"/patient";
        try(CloseableHttpClient client = HttpClients.createDefault()){
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> params = new ArrayList<>();
            
            params.add(new BasicNameValuePair("firstname", getFirstname()));
            params.add(new BasicNameValuePair("lastname", getLastname()));
            params.add(new BasicNameValuePair("gender", getGender()));
            params.add(new BasicNameValuePair("district", getDistrict()));
            params.add(new BasicNameValuePair("village", getVillage()));
            params.add(new BasicNameValuePair("occupation", getOccupation()));
            params.add(new BasicNameValuePair("birthdate", getBirthdate()));
            
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpResponse response = client.execute(httpPost); 
            if(response.getStatusLine().getStatusCode() == 201){
                System.out.println("Record added successfully");
            }else{
                System.err.println("There was an error in creating a patient record");
            }
            
            client.close();
            
        }catch(IOException ex){
            
        }
    }
    
    public void get_patient_list(){
        String url = Base_URL+"/patient";
        JSONArray json = new JSONArray();
        String res ="";
        try(CloseableHttpClient client = HttpClients.createDefault()){
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = client.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            res = EntityUtils.toString(responseEntity);
            json = new JSONArray(res);
            
            if(response.getStatusLine().getStatusCode() == 200){
                   
            }else{
                
            }
            client.close();
        }catch(IOException ex){
            
        } catch (JSONException ex) {
            Logger.getLogger(Patient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

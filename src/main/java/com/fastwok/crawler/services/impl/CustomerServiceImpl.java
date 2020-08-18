package com.fastwok.crawler.services.impl;

import com.fastwok.crawler.services.isservice.CustomerService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.Date;
import java.util.regex.Pattern;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    @Override
    public String getCustomers() throws UnirestException {
        HttpResponse<JsonNode> response = getListCustomerByTime();
        JSONObject res = new JSONObject(response.getBody());
        JSONObject jsonObject = res.getJSONObject("object");
//                getJSONArray("result").getJSONObject(0).get("_id");
        int total = jsonObject.getInt("total");
        for(int n = 0; n < total; n++)
        {
            Object customer_id=jsonObject.getJSONArray("result").getJSONObject(n).get("_id");
//            log.info(String.valueOf(customer_id.toString()));
            HttpResponse<String> customer_detail = getCustomerDetail(customer_id.toString());
            JSONObject customer = new JSONObject(customer_detail.getBody()).getJSONArray("result").getJSONObject(0);
            JSONObject updateCustomer = getCustomerUpdate(customer);
            if (updateCustomer==null) continue;
            HttpResponse<JsonNode> responseUpdate = updateCustomer(updateCustomer,customer_id.toString());
            JSONObject resupdate = new JSONObject(responseUpdate.getBody());
            log.info(resupdate.toString());
        }

        return "";
    }
    private HttpResponse<String> getCustomerDetail(String customer_id)
            throws UnirestException {
        Date date = new Date();
        long timeMilli = date.getTime();
        return Unirest.get("https://crm.fastwork.vn:6012/crmcustomers/"+customer_id+"/detail/5efef3dd5a51cf1c10fab0e4")
                .queryString("type", "preview")
                .header("Accept", "*/*")
                .header("Authorization", "Basic dGhhbmd2dUBodGF1dG86N2Q1NzQ0YTI1NjM1MDE2Zjk4MzEyNjE1YWQyZWQzMzI=")
                .header("x-fw", String.valueOf(timeMilli))
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.125 Safari/537.36")
                .header("Accept-Language", "en-US,en;q=0.9,vi;q=0.8")
                .header("Referer", "https://app.fastwork.vn/crmcustomers")
                .header("Origin", "https://app.fastwork.vn")
                .header("Host", "crm.fastwork.vn:6012")
                .header("Connection", "keep-alive")
                .header("Accept-Encoding", "gzip, deflate, br")
                .asString();
    }
    private HttpResponse<JsonNode> getListCustomerByTime()
            throws UnirestException {
        Date date = new Date();
        long timeMilli = date.getTime();
        return Unirest.post(" https://crm.fastwork.vn:6012/crmcustomers/list/5efef3dd5a51cf1c10fab0e4?next=50&skip=0&orderBy=createdDate&desc=-1")
                .header("Accept", "*/*")
                .header("Authorization", "Basic dGhhbmd2dUBodGF1dG86N2Q1NzQ0YTI1NjM1MDE2Zjk4MzEyNjE1YWQyZWQzMzI=")
                .header("x-fw", String.valueOf(timeMilli))
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.125 Safari/537.36")
                .header("Accept-Language", "en-US,en;q=0.9,vi;q=0.8")
                .header("Referer", "https://app.fastwork.vn/crmcustomers")
                .header("Origin", "https://app.fastwork.vn")
                .header("Host", "crm.fastwork.vn:6012")
                .header("Connection", "keep-alive")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Content-Type", "application/json")
                .header("Sec-Fetch-Dest", "empty")
                .header("Sec-Fetch-Mode", "cors")
                .header("Sec-Fetch-Site", "same-site")
                .body("{\"filter\":\"all\",\"from_date\":1597597200000,\"to_date\":1598201999999,\"objfind\":{}}")
                .asJson();
    }
    private HttpResponse<JsonNode> updateCustomer(JSONObject data,String customer_id)
            throws UnirestException {
        Date date = new Date();
        long timeMilli = date.getTime();
        return Unirest.put(" https://crm.fastwork.vn:6012/crmcustomers/"+customer_id+"/info/5efef3dd5a51cf1c10fab0e4")
                .header("Accept", "*/*")
                .header("Authorization", "Basic dGhhbmd2dUBodGF1dG86N2Q1NzQ0YTI1NjM1MDE2Zjk4MzEyNjE1YWQyZWQzMzI=")
                .header("x-fw", String.valueOf(timeMilli))
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.125 Safari/537.36")
                .header("Accept-Language", "en-US,en;q=0.9,vi;q=0.8")
                .header("Referer", "https://app.fastwork.vn/crmcustomers")
                .header("Origin", "https://app.fastwork.vn")
                .header("Host", "crm.fastwork.vn:6012")
                .header("Connection", "keep-alive")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Content-Type", "application/json")
                .header("Sec-Fetch-Dest", "empty")
                .header("Sec-Fetch-Mode", "cors")
                .header("Sec-Fetch-Site", "same-site")
                .body(data.toString())
                .asJson();
    }
    private String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        temp = pattern.matcher(temp).replaceAll("");
        return temp.replaceAll("đ", "d").replaceAll(" ","").toUpperCase();
    }
    private JSONObject getCustomerUpdate(JSONObject customer){
        try {
            JSONObject data = customer.getJSONObject("data");
            Object type=data.getJSONObject("nguon_khach_hang").get("viewData");
            Object fax = data.getJSONObject("fax").get("viewData");
            JSONObject ma_khach_hang = data.getJSONObject("ma_khach_hang");
            if ((removeAccent(fax.toString())+"_"+type.toString())==ma_khach_hang.get("viewData").toString()){
            return null;
            }
            else {
                JSONObject updateData = new JSONObject();
                updateData.put("long",customer.get("long"));
                updateData.put("lat",customer.get("lat"));
                updateData.put("status",customer.getJSONObject("status"));
                updateData.put("trang_thai",customer.getJSONObject("trang_thai"));
                updateData.put("groupAssignTo",customer.getJSONObject("groupAssignTo"));
                ma_khach_hang.put("viewData",(removeAccent(fax.toString())+"_"+type.toString()));
                data.put("ma_khach_hang",ma_khach_hang);
                updateData.put("data",data);
                log.info(updateData.toString());
                return updateData;
            }
        }
        catch (Exception ex){
            log.info(ex.toString());
        }
        return null;
    }
}

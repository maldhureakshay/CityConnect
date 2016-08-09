package com.cityconnect.webservice;

/**
 * Created by Sandeep on 11/22/15.
 */
public class ApiConstants {

    //public static final String DOMAIN_URL= "http://54.169.218.189";//Production
    public static final String DOMAIN_URL= "http://testlink.co.in";
    //public static final String DOMAIN_URL= "http://192.168.0.127:8046"; //Local URL from Darshana
    //public static final String DOMAIN_URL= "http://192.168.0.127:8086"; //Local URL from Darshana 15 Dec 2015
   // public static final String DOMAIN_URL= "http://muhurtmaza.com";
    //public static final String DOMAIN_URL= "http://54.172.154.202/";//Parallel

    public static final String BASE_URL = DOMAIN_URL+"/the_city_connect/";

    public static final String POST = "POST";

    public static final String GET = "GET";

    // API IDS
    public static final int UPLOAD_ISSUE_ID=1;


    //API URLS
    public static final String UPLOAD_ISSUE_URL = BASE_URL + "save_issue/";

  }

package com.mastek.appengage;

import com.mastek.appengage.campaigns.CampaignModelTypeResponseModel;
import com.mastek.appengage.campaigns.create_campaings.CreateCampaignReqModel;
import com.mastek.appengage.campaigns.create_campaings.CreateCampaignsResModel;
import com.mastek.appengage.events.EventSummaryResponseModel;
import com.mastek.appengage.events.GetEventSummaryResponseModel;
import com.mastek.appengage.login.LoginResponseModel;
import com.mastek.appengage.screen_analytics.FetchScreenStatusResponseModel;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Pravin103082 on 02-02-2017.
 */

public interface WebServices
{
    @GET("getUserValidated")
    Call<LoginResponseModel> getUserValidated(@Query("username") String username, @Query("password") String password);

    @GET("getDashBoardCounters")
    Call<String> getDashBoardCounters(@Query("sd") String startDate, @Query("ed") String endDate, @Query("type") String type, @Query("akey") String akey);

    @GET("getDeviceCounters")
    Call<String> getDeviceCounters(@Query("searchBy") String searchBy,@Query("sd") String startDate, @Query("ed") String endDate, @Query("type") String type, @Query("akey") String akey);

    @GET("getLocationCounters")
    Call<String> getLocationCounters(@Query("sd") String startDate, @Query("ed") String endDate, @Query("akey") String akey);

    @GET("getUserInsights")
    Call<String> getUserInsights(@Query("sd") String startDate, @Query("ed") String endDate, @Query("akey") String akey);

    @GET("getSessionInsights")
    Call<String> getSessionInsights(@Query("sd") String startDate, @Query("ed") String endDate, @Query("akey") String akey);


       @GET("getEventNames")
    Call<List<EventSummaryResponseModel>> getEventNames(@Query("sd") String startDate, @Query("ed") String endDate, @Query("akey") String akey);



    @GET("getEventsummary")
    Call<List<GetEventSummaryResponseModel>> getEventsummary(@Query("sd") String startDate,
                                                             @Query("ed") String endDate,
                                                             @Query("akey") String akey,
                                                             @Query("en") String event);


    @GET("fetchCohorts")
    Call<List<CohortsResponseModel>> fetchCohorts(@Query("akey") String akey,@Query("type") String type);



    @GET("getCrashCounters")
    Call<String> getCrashCounters(@Query("sd") String startDate, @Query("ed") String endDate, @Query("akey") String akey);

    @GET("fetchScreenStats")
    Call<List<FetchScreenStatusResponseModel>> fetchScreenStats(@Query("sd") String startDate,
                                                                @Query("ed") String endDate,
                                                                @Query("akey") String akey,
                                                                @Query("platform") String platform,
                                                                @Query("type") String type);


    @GET("audience/{user}")
    Call<List<String>> getAudienceManufacturer(@Path("user") String user,@Query("akey") String akey);

    @GET("audience/model")
    Call<List<CampaignModelTypeResponseModel>> getAudienceForModel(@Query("akey") String akey);



    @POST("createCampaign")
    Call<CreateCampaignsResModel> createCampaigns(@Query("akey") String akey, @Body CreateCampaignReqModel createCampaignReqModel);



}

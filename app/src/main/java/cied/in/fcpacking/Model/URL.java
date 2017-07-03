package cied.in.fcpacking.Model;

/**
 * Created by cied on 16/5/17.
 */

public class URL {

    public static String liveRootUrl = "http://ec2-54-165-220-194.compute-1.amazonaws.com/";
    //public static String rootUrl = "http://ec2-54-165-220-194.compute-1.amazonaws.com/";
    public static String rootUrl = "http://ec2-54-152-55-119.compute-1.amazonaws.com/";
   /* public static String getPackingDetrailsUrl = liveRootUrl+"subscription/get-packing-details/";
    public static String getFarmDetrailsUrl = liveRootUrl+"farms/";
    public static String getStockDetrailsUrl = liveRootUrl+"farm/get-sourced-vegetables/";
    public static String getDeliveryCycleDetailsUrl = liveRootUrl+"subscription-delivery-cycle/";*/
// ================ development =======================================================

    public static String getPackingDetrailsUrl = rootUrl +"subscription/get-packing-details/";
    public static String getFarmDetrailsUrl = rootUrl +"farms/";
    public static String getStockDetrailsUrl = rootUrl +"farm/get-sourced-vegetables/";
    public static String getDeliveryCycleDetailsUrl = rootUrl +"subscription-delivery-cycle/";

}

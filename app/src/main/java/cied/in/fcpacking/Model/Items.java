package cied.in.fcpacking.Model;

import android.util.Log;

/**
 * Created by cied on 23/2/17.
 */
public class Items {

    private int customerId;
    private String fsid;
    private String customerName;
    private String customerZone;
    private String status;
    private String filledStatus;
    private String preferanceStatus;
    private String packQuantity;
    private String distributedQuantity;
    private String cancelledCount;
    private String complaintCount;
    private String priorityStatus;
    private String customerNote;
    private String vegetableId;
    private String vegetableName;
    private String vegetableQuantity;
    private String distributedStatus;
    private String incrementQuantity;
    private String preferanceValue;
    private String vegOrderedStatus;
    private String isVerified;
    private String deliveredStatus;
    private String vegRate;
    private String zoneId;
    private String customId;
    private int compId;
    private String vegetableQuality;
    private String subscriptionQuantity;
    private String tradeQuantity;
    private String receivedQuantity;
    private String preferanceQuantity;
    private String nonPreferanceQuantity;
    private String customQuantity;

    public String getCustomQuantity() {
        return customQuantity;
    }

    public void setCustomQuantity(String customQuantity) {
        this.customQuantity = customQuantity;
    }

    public Items(String vegetable_id, String vegetable_name, String is_available_for_weekly_once,
                 String stockQty,String qty) {
        this.vegetableId = vegetable_id;
        this.vegetableName = vegetable_name;
        this.nonPreferanceStatus = is_available_for_weekly_once;
        this.vegetableQuantity = stockQty;
        this.nonPreferanceQuantity = qty;
    }

    public String getPreferanceQuantity() {
        return preferanceQuantity;
    }

    public void setPreferanceQuantity(String preferanceQuantity) {
        this.preferanceQuantity = preferanceQuantity;
    }

    public String getNonPreferanceQuantity() {
        return nonPreferanceQuantity;
    }

    public void setNonPreferanceQuantity(String nonPreferanceQuantity) {
        this.nonPreferanceQuantity = nonPreferanceQuantity;
    }

    public int getVegetableRank() {
        return vegetableRank;
    }

    public void setVegetableRank(int vegetableRank) {
        this.vegetableRank = vegetableRank;
    }

    private String nonPreferanceStatus;
    private int vegetableRank;

    public Items(double distributedQuantity) {
        this.distributedQuantity = ""+distributedQuantity;
    }

    public String getNonPreferanceStatus() {
        return nonPreferanceStatus;
    }

    public void setNonPreferanceStatus(String nonPreferanceStatus) {
        this.nonPreferanceStatus = nonPreferanceStatus;
    }

    public Items(String id, String delivery_no, String delivery_date) {
        this.subscriptionDeliveryId = id;
        this.deliveryNumber = delivery_no;
        this.deliveryDate = delivery_date;
    }

    public Items(double aDouble, String vegetable_name, String total_quantity, String recieved_amount) {
        this.vegetableName = vegetable_name;
        this.orderedQuantity = total_quantity;
        this.receivedQuantity = recieved_amount;
    }

    public String getSubscriptionDeliveryId() {
        return subscriptionDeliveryId;
    }

    public void setSubscriptionDeliveryId(String subscriptionDeliveryId) {
        this.subscriptionDeliveryId = subscriptionDeliveryId;
    }

    public String getTradeDeliveryId() {
        return tradeDeliveryId;
    }

    public void setTradeDeliveryId(String tradeDeliveryId) {
        this.tradeDeliveryId = tradeDeliveryId;
    }

    public String getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(String deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    private String orderedQuantity;
    private String excessQuantity;
    private String wastageQuantity;
    private String complaintNotes;
    private String farmName;
    private String group;
    private String subGroup;
    private String orderId;
    private String subscriptionDeliveryId;
    private String tradeDeliveryId;
    private String deliveryNumber ;
    private String deliveryDate ;

    public String getCycleId() {
        return cycleId;
    }

    public void setCycleId(String cycleId) {
        this.cycleId = cycleId;
    }

    public String getIsCurentCycle() {
        return isCurentCycle;
    }

    public void setIsCurentCycle(String isCurentCycle) {
        this.isCurentCycle = isCurentCycle;
    }

    private String cycleId;
    private String isCurentCycle;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    private String unit;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Items(String id, String fs_id, String name, String group, String sub_group, String status,
                 String is_priority, String ordered_quantity, String note, String packing_status) {



        this.customerId = Integer.parseInt(id);
        this.fsid = fs_id;
        this.customerName = name;
        this.group = group;
        this.subGroup = sub_group;
        this.status = status;
        this.priorityStatus = is_priority;
        this.orderedQuantity = ordered_quantity;
        this.complaintNotes = note;
        this.packingStatus = packing_status;
    }

    public Items(int customerId, String fsid, String status, String farm_id, String vegetable_name,
                 String quantity, String vegetable_id,String id,String rate,int q) {
        this.customerId = customerId;
        this.fsid = fsid;
        this.status = status;
        this.farmId = farm_id;
        this.vegetableName = vegetable_name;
        this.vegetableQuantity = quantity;
        this.vegetableId = vegetable_id;
        this.orderId = id;
        this.vegRate = rate;
    }

    public String getSubGroup() {
        return subGroup;
    }

    public void setSubGroup(String subGroup) {
        this.subGroup = subGroup;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Items(String subscriptionVegQuantity, String tradeQuantity, String excessVegQuantity,
                 String wastageVegQuantity, String receivedQuantity, String vegetableQuality,String rate) {
        this.subscriptionQuantity = subscriptionVegQuantity;
        this.tradeQuantity = tradeQuantity;
        this.excessQuantity = excessVegQuantity;
        this.wastageQuantity = wastageVegQuantity;
        this.receivedQuantity = receivedQuantity;
        this.vegetableQuality = vegetableQuality;
        this.vegRate = rate;

    }

    public Items(String vegetable_id, String vegetable_name, String subscription_quantity,
                 String receivedQty, String farmId,String unit,int q,String cycle_id,
                 String nonPreferance_Status,String nonPreferance_Quantity,String preferance_Quantity,
                 String trade_Quantity,String orderUnit,String custom_Quantity) {

        this.vegetableId = vegetable_id;
        this.vegetableName = vegetable_name;
        this.orderedQuantity = subscription_quantity;
        this.receivedQuantity = receivedQty;
        this.farmId = farmId;
        this.cycleId = unit;
        this.subscriptionDeliveryId = cycle_id;
        this.nonPreferanceStatus = nonPreferance_Status;
        this.nonPreferanceQuantity = nonPreferance_Quantity;
        this.preferanceQuantity = preferance_Quantity;
        this.tradeQuantity = trade_Quantity;
        this.unit = orderUnit;
        this.customQuantity = custom_Quantity;
    }

    public Items(String farm_id, String farm_name,String status, int i) {
        this.farmId = farm_id;
        this.farmName = farm_name;
        this.status = status;
    }

    public Items(int i, String vegetable_id, String vegetable_name) {
        this.vegetableId = vegetable_id;
        this.vegetableName = vegetable_name;

        this.distributedStatus = vegetable_id;
        this.vegetableQuantity = vegetable_name;
    }

    public Items(String status, int i, int i1) {
        this.isVerified = status;
        this.status = status;
    }

    public Items(int i, int i1, String cancelled) {

        this.status = cancelled;
    }

    public Items(String vegetable, String vegetable_name, String subscription_quantity,
                 String trade_quantity, String ordered_quantity, String total_quantity,
                 String excess_quantity, String wastage, String rate, String farm_name, String farm,
                 String rating,String nonPreferance_Status,String cycleId,String nonPreferance_Quantity,
                 String preferance_Quantity,int demmy,String custom_Quantity) {

        this.vegetableId = vegetable;
        this.vegetableName = vegetable_name;
        this.orderId = subscription_quantity;
        this.tradeQuantity = trade_quantity;
        this.orderedQuantity = ordered_quantity;
        this.receivedQuantity = total_quantity;
        this.excessQuantity = excess_quantity;
        this.wastageQuantity = wastage;
        this.vegRate = rate;
        this.farmName = farm_name;
        this.farmId = farm;
        this.vegetableQuality = rating;
        this.nonPreferanceStatus = nonPreferance_Status;
        this.subscriptionDeliveryId = cycleId;
        this.nonPreferanceQuantity = nonPreferance_Quantity;
        this.preferanceQuantity = preferance_Quantity;
        this.customQuantity = custom_Quantity;
    }

    public Items(int i, int i1, int i2, String farmId, String received_Quantity) {
        this.farmId = farmId;
        this.receivedQuantity = received_Quantity;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public String getTradeQuantity() {
        return tradeQuantity;
    }

    public void setTradeQuantity(String tradeQuantity) {
        this.tradeQuantity = tradeQuantity;
    }

    public String getReceivedQuantity() {
        return receivedQuantity;
    }

    public void setReceivedQuantity(String receivedQuantity) {
        this.receivedQuantity = receivedQuantity;
    }

    public String getOrderedQuantity() {
        return orderedQuantity;
    }

    public void setOrderedQuantity(String orderedQuantity) {
        this.orderedQuantity = orderedQuantity;
    }

    public Items(int i, String complaintnotes) {
        this.complaintNotes = complaintnotes;
        this.filledStatus = complaintnotes;

    }

    public String getComplaintNotes() {
        return complaintNotes;
    }

    public void setComplaintNotes(String complaintNotes) {
        this.complaintNotes = complaintNotes;
    }

    public String getVegetableQuality() {
        return vegetableQuality;
    }

    public void setVegetableQuality(String vegetableQuality) {
        this.vegetableQuality = vegetableQuality;
    }

    public String getSubscriptionQuantity() {
        return subscriptionQuantity;
    }

    public void setSubscriptionQuantity(String subscriptionQuantity) {
        this.subscriptionQuantity = subscriptionQuantity;
    }

    public String getExcessQuantity() {
        return excessQuantity;
    }

    public void setExcessQuantity(String excessQuantity) {
        this.excessQuantity = excessQuantity;
    }

    public String getWastageQuantity() {
        return wastageQuantity;
    }

    public void setWastageQuantity(String wastageQuantity) {
        this.wastageQuantity = wastageQuantity;
    }

    public Items(float quantitySum) {
        this.distributedQuantity = ""+quantitySum;
    }

    public int getCompId() {
        return compId;
    }

    public void setCompId(int compId) {
        this.compId = compId;
    }

    public Items(String zone_id, int i) {
        this.complaintStatus = zone_id;
        this.vegetableQuantity = zone_id;
    }

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public Items(String zone_id, String zone) {
        this.zoneId = zone_id;
        this.zone = zone;

        this.receivedQuantity = zone_id;
        this.farmId = zone;

        this.vegetableId = zone_id;
        this.vegetableName = zone;

        this.status = zone_id;
        this.vegetableQuantity = zone;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    private String zone;

    public Items(String customerId, String customerName, String zone, String status, String cancelled_count,
                 String is_box_filled, String complaint_count, String fs_id, String pack_quantity,
                 String is_priority, String distributed_quantity, String note, String is_preferece_given,
                 String packingStatus,String zone_id,String cycle_Id) {

        int cid = Integer.parseInt(customerId);
        this.customerId = cid;
        this.customerName = customerName;
        this.customerZone = zone;
        this.status = status;
        this.orderedQuantity = cancelled_count;
        this.filledStatus = is_box_filled;
        this.complaintCount = complaint_count;
        this.fsid = fs_id;
        this.packQuantity = pack_quantity;
        this.priorityStatus = is_priority;
        this.distributedQuantity = distributed_quantity;
        this.customerNote = note;
        this.preferanceStatus = is_preferece_given;
        this.packingStatus = packingStatus;
        this.zoneId = zone_id;
        this.subscriptionDeliveryId = cycle_Id;

    }

    public Items(int customerId, String fsId, String vegetable_id, String farm_id, String is_distributed,
                 String value, String increment_quantity, String vegetable_name, String quantity,String isVerified,int rank,String cycle_Id) {

        this.customerId = customerId;
        this.fsid = fsId;
        this.vegetableId = vegetable_id;
        this. farmId = farm_id;
        this.distributedStatus = is_distributed;
        this.preferanceValue = value;
        this.incrementQuantity = increment_quantity;
        this.vegetableName = vegetable_name;
        this.vegetableQuantity = quantity;
        this.isVerified = isVerified;
        this.vegetableRank = rank;
        this.subscriptionDeliveryId = cycle_Id;
    }

    public Items(int customerId, String fsId, String vegetable_id, String vegetable_name, String status,
                 String days_due, String rate, String quantity,String id,String cycle_Id) {

        this.customerId = customerId;
        this.fsid = fsId;
        this.vegetableId = vegetable_id;
        this.vegetableName = vegetable_name;
        this.status = status;
        this.numberOfDays = days_due;
        this.vegRate = rate;
        this.vegetableQuantity = quantity;
        this.customId = id;
        this.subscriptionDeliveryId = cycle_Id;
    }

    public Items(int customerId, String fsid, String complaintStatus,String complaint_note,String cycle_Id) {

        this.customerId = customerId;
        this.fsid = fsid;
        this.complaintStatus = complaintStatus;
        this.complaintNotes = complaint_note;
        this.subscriptionDeliveryId = cycle_Id;
    }

    public String getVegRate() {
        return vegRate;
    }

    public void setVegRate(String vegRate) {
        this.vegRate = vegRate;
    }

    public String getFarmId() {
        return farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    private String numberOfDays;
    private String complaintStatus;
    private String packingStatus;
    private String farmId;

    public Items(String distributedStatus) {

        this.distributedStatus = distributedStatus;
        this.vegetableQuantity = distributedStatus;
        this.status = distributedStatus;
        this.packingStatus = distributedStatus;
        this.receivedQuantity = distributedStatus;
        this.farmId = distributedStatus;
        this.nonPreferanceStatus = distributedStatus;
        this.vegetableQuantity = distributedStatus;

    }

    public Items() {

    }

    public String getPackingStatus() {
        return packingStatus;
    }

    public void setPackingStatus(String packingStatus) {
        this.packingStatus = packingStatus;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFsid() {
        return fsid;
    }

    public void setFsid(String fsid) {
        this.fsid = fsid;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerZone() {
        return customerZone;
    }

    public void setCustomerZone(String customerZone) {
        this.customerZone = customerZone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFilledStatus() {
        return filledStatus;
    }

    public void setFilledStatus(String filledStatus) {
        this.filledStatus = filledStatus;
    }

    public String getPreferanceStatus() {
        return preferanceStatus;
    }

    public void setPreferanceStatus(String preferanceStatus) {
        this.preferanceStatus = preferanceStatus;
    }

    public String getPackQuantity() {
        return packQuantity;
    }

    public void setPackQuantity(String packQuantity) {
        this.packQuantity = packQuantity;
    }

    public String getDistributedQuantity() {
        return distributedQuantity;
    }

    public void setDistributedQuantity(String distributedQuantity) {
        this.distributedQuantity = distributedQuantity;
    }

    public String getCancelledCount() {
        return cancelledCount;
    }

    public void setCancelledCount(String cancelledCount) {
        this.cancelledCount = cancelledCount;
    }

    public String getComplaintCount() {
        return complaintCount;
    }

    public void setComplaintCount(String complaintCount) {
        this.complaintCount = complaintCount;
    }

    public String getPriorityStatus() {
        return priorityStatus;
    }

    public void setPriorityStatus(String priorityStatus) {
        this.priorityStatus = priorityStatus;
    }

    public String getCustomerNote() {
        return customerNote;
    }

    public void setCustomerNote(String customerNote) {
        this.customerNote = customerNote;
    }

    public String getVegetableId() {
        return vegetableId;
    }

    public void setVegetableId(String vegetableId) {
        this.vegetableId = vegetableId;
    }

    public String getVegetableName() {
        return vegetableName;
    }

    public void setVegetableName(String vegetableName) {
        this.vegetableName = vegetableName;
    }

    public String getVegetableQuantity() {
        return vegetableQuantity;
    }

    public void setVegetableQuantity(String vegetableQuantity) {
        this.vegetableQuantity = vegetableQuantity;
    }

    public String getDistributedStatus() {
        return distributedStatus;
    }

    public void setDistributedStatus(String distributedStatus) {
        this.distributedStatus = distributedStatus;
    }

    public String getIncrementQuantity() {
        return incrementQuantity;
    }

    public void setIncrementQuantity(String incrementQuantity) {
        this.incrementQuantity = incrementQuantity;
    }

    public String getPreferanceValue() {
        return preferanceValue;
    }

    public void setPreferanceValue(String preferanceValue) {
        this.preferanceValue = preferanceValue;
    }

    public String getVegOrderedStatus() {
        return vegOrderedStatus;
    }

    public void setVegOrderedStatus(String vegOrderedStatus) {
        this.vegOrderedStatus = vegOrderedStatus;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }

    public String getDeliveredStatus() {
        return deliveredStatus;
    }

    public void setDeliveredStatus(String deliveredStatus) {
        this.deliveredStatus = deliveredStatus;
    }

    public String getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(String numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public String getComplaintStatus() {
        return complaintStatus;
    }

    public void setComplaintStatus(String complaintStatus) {
        this.complaintStatus = complaintStatus;
    }
}

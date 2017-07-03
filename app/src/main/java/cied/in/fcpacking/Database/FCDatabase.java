package cied.in.fcpacking.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cied.in.fcpacking.Model.Items;

/**
 * Created by cied on 23/2/17.
 */
public class FCDatabase extends SQLiteOpenHelper{

    private static int databaseversion = 2;
    private static final String databasename = "packing_db";
    private static final String customerDetailsTable = "customerDetailsTable";
    private static final String vegetableDetailsTable = "vegetableDetailsTable";
    private static final String customVegetableDetailsTable = "customVegetableDetailsTable";
    private static final String complaintDetailsTable = "complaintDetailsTable";
    private static final String customerZoneDetailsTable = "customerZoneDetailsTable";
    private static final String vegetableStockTable = "vegetableStockTable";
    private static final String vegetableOrderTable = "vegetableOrderTable";
    private static final String orderedVegetableTable = "orderedVegetableTable";
    private static final String farmDetailsTable = "farmDetailsTable";
    private static final String tradeCustomerDetailsTable = "tradeCustomerDetailsTable";
    private static final String tradeOrederTable = "tradeOrederTable";
    private static final String deliveryCycleTable = "deliveryCycleTable";
    private static final String stockVegetableTable = "stockVegetableTable";

    private static final String id = "id";
    private static final String orderId = "orderId";
    private static final String customerId = "customerId";
    private static final String fsid = "fsid";
    private static final String customerName = "customerName";
    private static final String customerZone = "customerZone";
    private static final String status = "status";
    private static final String filledStatus = "filledStatus";
    private static final String preferanceStatus = "preferanceStatus";
    private static final String packQuantity = "packQuantity";
    private static final String distributedQuantity = "distributedQuantity";
    private static final String unit = "unit";
    private static final String complaintCount = "complaintCount";
    private static final String priorityStatus = "priorityStatus";
    private static final String customerNote = "customerNote";
    private static final String vegetableId = "vegetableId";
    private static final String vegetableName = "vegetableName";
    private static final String vegetableQuantity = "vegetableQuantity";
    private static final String distributedStatus = "distributedStatus";
    private static final String incrementQuantity = "incrementQuantity";
    private static final String preferanceValue = "preferanceValue";
    private static final String vegOrderedStatus = "vegOrderedStatus";
    private static final String isVerified = "isVerified";
    private static final String deliveredStatus = "deliveredStatus";
    private static final String numberOfDays = "numberOfDays";
    private static final String complaintStatus = "complaintStatus";
    private static final String packingStatus = "packingStatus";
    private static final String farmId = "farmId";
    private static final String farmName = "farmName";
    private static final String vegRate = "vegRate";
    private static final String zoneId = "zoneId";
    private static final String zone = "zone";
    private static final String group = "group";
    private static final String vegetableRank = "vegetableRank";
    private static final String subscriptionDeliveryId = "subscriptionDeliveryId";
    private static final String deliveryNumber = "deliveryNumber";
    private static final String deliveryDate = "deliveryDate";
    private static final String cycleId = "cycleId";
    private static final String isCurrentCycle = "isCurrentCycle";
    private static final String subGroup = "subGroup";
    private static final String vegetableQuality = "vegetableQuality";
    private static final String subscriptionQuantity = "subscriptionQuantity";
    private static final String tradeQuantity = "tradeQuantity";
    private static final String orderedQuantity = "orderedQuantity";
    private static final String recievedQuantity = "recievedQuantity";
    private static final String excessQuantity = "excessQuantity";
    private static final String wastageQuantity = "wastageQuantity";
    private static final String customId = "customId";
    private static final String complaintNotes = "complaintNotes";
    private static final String nonPreferanceStatus = "nonPreferanceStatus";
    private static final String nonPreferanceQuantity = "nonPreferanceQuantity";
    private static final String preferanceQuantity = "preferanceQuantity";
    private static final String customQuantity = "customQuantity";

    private static final String createCustomerDetailsTable = "create table customerDetailsTable(id integer primary key ,"
            + "customerId int,"
            + "fsid text,"
            + "customerName text,"
            + "customerZone text,"
            + "status text,"
            + "filledStatus text,"
            + "preferanceStatus text,"
            + "packQuantity text,"
            + "distributedQuantity text,"
            + "orderedQuantity text,"
            + "complaintCount text,"
            + "priorityStatus text,"
            + "packingStatus text,"
            + "zoneId text,"
            + "customerNote text,"
            + "subscriptionDeliveryId text);";

    private static final String createTradeCustomerDetailsTable = "create table tradeCustomerDetailsTable(customerId integer primary key ,"
            + "fsid text,"
            + "zone text,"
            + "customerName text,"
            + "subGroup text,"
            + "status text,"
            + "orderedQuantity text,"
            + "priorityStatus text,"
            + "packingStatus text,"
            + "customerNote text);";

    private static final String createStockVegetableTable = "create table stockVegetableTable(id integer primary key ,"
            + "vegetableId text,"
            + "vegetableName text,"
            + "vegetableQuantity text,"
            + "nonPreferanceStatus text,"
            + "nonPreferanceQuantity text);";

    private static final String createTradeOrderDetailsTable = "create table tradeOrederTable(id integer primary key ,"
            + "customerId int,"
            + "fsid text,"
            + "status text,"
            + "farmId text,"
            + "vegetableId text,"
            + "vegetableQuantity text,"
            + "orderId text,"
            + "vegRate text,"
            + "vegetableName text);";
    private static final String createVegetableDetailsTable = "create table vegetableDetailsTable(id integer primary key autoincrement,"
            + "customerId int,"
            + "fsid text,"
            + "vegetableId text,"
            + "vegetableName text,"
            + "vegetableQuantity text,"
            + "distributedStatus text,"
            + "incrementQuantity text,"
            + "preferanceValue text,"
            + "farmId text,"
            + "isVerified text,"
            + "vegetableRank text,"
            + "subscriptionDeliveryId int);";

    private static final String createVegetableStockTable = "create table vegetableStockTable(id integer primary key autoincrement,"
            + "vegetableId text,"
            + "vegetableName text,"
            + "orderId text,"
            + "tradeQuantity text,"
            + "orderedQuantity text,"
            + "recievedQuantity text,"
            + "excessQuantity text,"
            + "wastageQuantity text,"
            + "vegRate text,"
            + "farmName text,"
            + "farmId text,"
            + "vegetableQuality text,"
            + "nonPreferanceStatus text,"
            + "subscriptionDeliveryId text,"
            + "nonPreferanceQuantity text,"
            + "preferanceQuantity text,"
            + "customQuantity text);";
    private static final String createVegetableOrderTable = "create table vegetableOrderTable(id integer primary key autoincrement,"
            + "vegetableId text,"
            + "vegetableName text,"
            + "orderedQuantity text,"
            + "recievedQuantity text,"
            + "farmId text,"
            + "cycleId text,"
            + "subscriptionDeliveryId text,"
            + "nonPreferanceStatus text,"
            + "nonPreferanceQuantity text,"
            + "preferanceQuantity text,"
            + "tradeQuantity text,"
            + "unit text,"
            + "customQuantity text);";

    private static final String createDeliveryCycleTable = "create table deliveryCycleTable (id integer primary key autoincrement,"
            + "subscriptionDeliveryId text,"
            + "deliveryNumber text,"
            + "deliveryDate text);";

    private static final String createOrderedVegetableTable = "create table orderedVegetableTable(id integer primary key autoincrement,"
            + "vegetableId text,"
            + "vegetableName text);";
    private static final String createFarmDetailsTable = "create table farmDetailsTable(id integer primary key autoincrement,"
            + "farmId text,"
            + "farmName text,"
            + "status text);";

    private static final String createCustomVegetableDetailsTable = "create table customVegetableDetailsTable(id integer primary key autoincrement,"
            + "customerId int,"
            + "fsid text,"
            + "vegetableId text,"
            + "vegetableName text,"
            + "vegetableQuantity text,"
            + "status text,"
            + "vegRate text,"
            + "customId text,"
            + "numberOfDays text,"
            + "subscriptionDeliveryId text);";

    private static final String createComplaintDetailsTable = "create table complaintDetailsTable(id integer primary key autoincrement,"
            + "customerId int,"
            + "fsid text,"
            + "complaintNotes text,"
            + "complaintStatus text,"
            + "subscriptionDeliveryId text);";

    private static final String createCustomerZoneDetailsTable = "create table customerZoneDetailsTable(id integer primary key autoincrement,"
            + "zoneId text,"
            + "zone text);";
    public FCDatabase(Context context) {
        super(context, databasename, null, databaseversion);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(createCustomerDetailsTable);
        db.execSQL(createVegetableDetailsTable);
        db.execSQL(createCustomVegetableDetailsTable);
        db.execSQL(createComplaintDetailsTable);
        db.execSQL(createCustomerZoneDetailsTable);
        db.execSQL(createVegetableStockTable);
        db.execSQL(createVegetableOrderTable);
        db.execSQL(createFarmDetailsTable);
        db.execSQL(createOrderedVegetableTable);
        db.execSQL(createTradeCustomerDetailsTable);
        db.execSQL(createTradeOrderDetailsTable);
        db.execSQL(createDeliveryCycleTable);
        db.execSQL(createStockVegetableTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long addtoCustomerDetailsTable(Items aa) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(customerId, aa.getCustomerId());
        values.put(fsid, aa.getFsid());
        values.put(customerName, aa.getCustomerName());
        values.put(customerZone, aa.getCustomerZone());
        values.put(status, aa.getStatus());
        values.put(filledStatus, aa.getFilledStatus());
        values.put(preferanceStatus, aa.getPreferanceStatus());
        values.put(packQuantity, aa.getPackQuantity());
        values.put(distributedQuantity, aa.getDistributedQuantity());
        values.put(orderedQuantity, aa.getOrderedQuantity());
        values.put(complaintCount, aa.getComplaintCount());
        values.put(priorityStatus, aa.getPriorityStatus());
        values.put(customerNote, aa.getCustomerNote());
        values.put(packingStatus, aa.getPackingStatus());
        values.put(zoneId, aa.getZoneId());
        values.put(subscriptionDeliveryId, aa.getSubscriptionDeliveryId());

        long a = db.insert(customerDetailsTable, null, values);

        db.close();
        return a;
    }
    public long addtoStockVegetableTable(Items aa) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(vegetableId, aa.getVegetableId());
        values.put(vegetableName, aa.getVegetableName());
        values.put(vegetableQuantity, aa.getVegetableQuantity());
        values.put(nonPreferanceStatus, aa.getNonPreferanceStatus());
        values.put(nonPreferanceQuantity, aa.getNonPreferanceQuantity());

        long a = db.insert(stockVegetableTable, null, values);

        db.close();
        return a;
    }

    public long addtoTradeCustomerDetailsTable(Items aa) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(customerId, aa.getCustomerId());
        values.put(fsid, aa.getFsid());
        values.put(customerName, aa.getCustomerName());
        values.put(zone, aa.getGroup());
        values.put(subGroup, aa.getSubGroup());
        values.put(status, aa.getStatus());
        values.put(orderedQuantity, aa.getOrderedQuantity());
        values.put(priorityStatus, aa.getPriorityStatus());
        values.put(customerNote, aa.getCustomerNote());
        values.put(packingStatus, aa.getPackingStatus());

        long a = db.insert(tradeCustomerDetailsTable, null, values);

        db.close();
        return a;
    }

    public void updateTradeCustomerDetailsTable(Items listItems, String fsiD) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(packingStatus, listItems.getPackingStatus());

        db.update(tradeCustomerDetailsTable, values, fsid + " = ? ", new String[] { fsiD });

    }

    public long addtoTradeOrderDetailsTable(Items aa) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(customerId, aa.getCustomerId());
        values.put(fsid, aa.getFsid());
        values.put(status, aa.getStatus());
        values.put(farmId, aa.getFarmId());
        values.put(vegetableId, aa.getVegetableId());
        values.put(vegetableQuantity, aa.getVegetableQuantity());
        values.put(vegetableName, aa.getVegetableName());
        values.put(orderId, aa.getOrderId());
        values.put(vegRate, aa.getVegRate());

        long a = db.insert(tradeOrederTable, null, values);

        db.close();
        return a;
    }

    public List<Items> getStockVegetableTable() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM stockVegetableTable ";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setVegetableId(cursor.getString(1));
                aa.setVegetableName(cursor.getString(2));
                aa.setVegetableQuantity(cursor.getString(3));
                aa.setNonPreferanceStatus(cursor.getString(4));
                aa.setNonPreferanceQuantity(cursor.getString(5));

                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getStockVegetableTable(String veg_name) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM stockVegetableTable WHERE vegetableName LIKE '%"
                +veg_name+"%'";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setVegetableId(cursor.getString(1));
                aa.setVegetableName(cursor.getString(2));
                aa.setVegetableQuantity(cursor.getString(3));
                aa.setNonPreferanceStatus(cursor.getString(4));
                aa.setNonPreferanceQuantity(cursor.getString(5));

                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getStockVegetableTableForSync() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM stockVegetableTable WHERE nonPreferanceStatus = 'true' ";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setVegetableId(cursor.getString(1));
                aa.setVegetableName(cursor.getString(2));
                aa.setVegetableQuantity(cursor.getString(3));
                aa.setNonPreferanceStatus(cursor.getString(4));
                aa.setNonPreferanceQuantity(cursor.getString(5));

                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public void updateStockTable(Items listItems, String vegetable_id) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(vegetableQuantity, listItems.getVegetableQuantity());

        db.update(stockVegetableTable, values, vegetableId + " = ? ", new String[] { vegetable_id });

    }
    public void updateStatusForStockTable(Items listItems, String vegetable_id) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(nonPreferanceStatus, listItems.getNonPreferanceStatus());

        db.update(stockVegetableTable, values, vegetableId + " = ? ", new String[] { vegetable_id });

    }

    public List<Items> getTradeOrderDetailsTable() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM tradeOrederTable ";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setId(cursor.getString(0));
                aa.setCustomerId(cursor.getInt(1));
                aa.setFsid(cursor.getString(2));
                aa.setStatus(cursor.getString(3));
                aa.setFarmId(cursor.getString(4));
                aa.setVegetableId(cursor.getString(5));
                aa.setVegetableQuantity(cursor.getString(6));
                aa.setOrderId(cursor.getString(7));
                aa.setVegRate(cursor.getString(8));
                aa.setVegetableName(cursor.getString(9));

                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getTradeOrderDetailsTableForSync() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM tradeOrederTable WHERE NOT status = 'ORDERED'";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setId(cursor.getString(0));
                aa.setCustomerId(cursor.getInt(1));
                aa.setFsid(cursor.getString(2));
                aa.setStatus(cursor.getString(3));
                aa.setFarmId(cursor.getString(4));
                aa.setVegetableId(cursor.getString(5));
                aa.setVegetableQuantity(cursor.getString(6));
                aa.setOrderId(cursor.getString(7));
                aa.setVegRate(cursor.getString(8));
                aa.setVegetableName(cursor.getString(9));

                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<Items> getTradeOrderDetailsTable(int customer_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String cid = ""+customer_id;
        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM tradeOrederTable WHERE customerId = '"+customer_id+"'";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setId(cursor.getString(0));
                aa.setCustomerId(cursor.getInt(1));
                aa.setFsid(cursor.getString(2));
                aa.setStatus(cursor.getString(3));
                aa.setFarmId(cursor.getString(4));
                aa.setVegetableId(cursor.getString(5));
                aa.setVegetableQuantity(cursor.getString(6));
                aa.setOrderId(cursor.getString(7));
                aa.setVegRate(cursor.getString(8));
                aa.setVegetableName(cursor.getString(9));

                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<Items> getTradeOrderDetailsTable(int customer_id,String status) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM tradeOrederTable WHERE customerId = "+customer_id
                +" AND status = '"+status+"'";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setId(cursor.getString(0));
                aa.setCustomerId(cursor.getInt(1));
                aa.setFsid(cursor.getString(2));
                aa.setStatus(cursor.getString(3));
                aa.setFarmId(cursor.getString(4));
                aa.setVegetableId(cursor.getString(5));
                aa.setVegetableQuantity(cursor.getString(6));
                aa.setOrderId(cursor.getString(7));
                aa.setVegRate(cursor.getString(8));
                aa.setVegetableName(cursor.getString(9));


                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getTradeOrderDetailsTable(String vegname) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM tradeOrederTable  WHERE vegetableName LIKE '%"+vegname+"%'";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setId(cursor.getString(0));
                aa.setCustomerId(cursor.getInt(1));
                aa.setFsid(cursor.getString(2));
                aa.setStatus(cursor.getString(3));
                aa.setFarmId(cursor.getString(4));
                aa.setVegetableId(cursor.getString(5));
                aa.setVegetableQuantity(cursor.getString(6));
                aa.setOrderId(cursor.getString(7));
                aa.setVegRate(cursor.getString(8));
                aa.setVegetableName(cursor.getString(9));

                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public void updateTradeOrderDetailsTable(Items listItems, String orderId) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Log.e("vegetableQuantity.",listItems.getVegetableQuantity());
        values.put(status, listItems.getStatus());
        values.put(vegetableQuantity, listItems.getVegetableQuantity());

        db.update(tradeOrederTable, values, id + " = ? ", new String[] { orderId });

    }
    public void updateTradeOrderStatus(Items listItems, String orderId) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(status, listItems.getStatus());

        db.update(tradeOrederTable, values, id + " = ? ", new String[] { orderId });

    }

    public long addtoVegetableDetailsTable(Items aa) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(customerId, aa.getCustomerId());
        values.put(fsid, aa.getFsid());
        values.put(vegetableId, aa.getVegetableId());
        values.put(vegetableName, aa.getVegetableName());
        values.put(vegetableQuantity, aa.getVegetableQuantity());
        values.put(distributedStatus, aa.getDistributedStatus());
        values.put(incrementQuantity, aa.getIncrementQuantity());
        values.put(preferanceValue, aa.getPreferanceValue());
        values.put(farmId, aa.getFarmId());
        values.put(isVerified, aa.getIsVerified());
        values.put(vegetableRank, aa.getVegetableRank());
        values.put(subscriptionDeliveryId, aa.getSubscriptionDeliveryId());

        long a = db.insert(vegetableDetailsTable, null, values);

        db.close();
        return a;
    }


    public long addToVegetableStockTable(Items aa) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(vegetableId, aa.getVegetableId());
        values.put(vegetableName, aa.getVegetableName());
        values.put(orderId, aa.getOrderId());
        values.put(tradeQuantity, aa.getTradeQuantity());
        values.put(orderedQuantity, aa.getOrderedQuantity());
        values.put(recievedQuantity, aa.getReceivedQuantity());
        values.put(excessQuantity, aa.getExcessQuantity());
        values.put(wastageQuantity, aa.getWastageQuantity());
        values.put(vegRate, aa.getVegRate());
        values.put(farmName, aa.getFarmName());
        values.put(farmId, aa.getFarmId());
        values.put(vegetableQuality, aa.getVegetableQuality());
        values.put(nonPreferanceStatus, aa.getNonPreferanceStatus());
        values.put(subscriptionDeliveryId, aa.getSubscriptionDeliveryId());
        values.put(nonPreferanceQuantity, aa.getNonPreferanceQuantity());
        values.put(preferanceQuantity, aa.getPreferanceQuantity());
        values.put(customQuantity, aa.getCustomQuantity ());

        long a = db.insert(vegetableStockTable, null, values);

        db.close();
        return a;
    }

    public long addToVegetableOrderTable(Items aa) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        /*Log.e("vegetableId...",aa.getVegetableId());
        Log.e("vegetableName...",aa.getVegetableName());
       // Log.e("orderedQuantity...",aa.getOrderedQuantity());
       // Log.e("farmId...", aa.getFarmId());
        Log.e("SubscriptionDeliveryId",aa.getSubscriptionDeliveryId ());
*/
        values.put(vegetableId, aa.getVegetableId());
        values.put(vegetableName, aa.getVegetableName());
        values.put(orderedQuantity, aa.getOrderedQuantity());
        values.put(recievedQuantity, aa.getReceivedQuantity());
        values.put(farmId, aa.getFarmId ());
        values.put(cycleId, aa.getCycleId ());
        values.put(subscriptionDeliveryId, aa.getSubscriptionDeliveryId ());
        values.put(nonPreferanceStatus, aa.getNonPreferanceStatus ());
        values.put(nonPreferanceQuantity, aa.getNonPreferanceQuantity ());
        values.put(preferanceQuantity, aa.getPreferanceQuantity ());
        values.put(tradeQuantity, aa.getTradeQuantity ());
        values.put(unit, aa.getUnit ());
        values.put(customQuantity, aa.getCustomQuantity ());

        long a = db.insert(vegetableOrderTable, null, values);

        db.close();
        return a;
    }
    public long addToDeliveryCycleTable(Items aa) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(subscriptionDeliveryId, aa.getSubscriptionDeliveryId());
        values.put(deliveryNumber, aa.getDeliveryNumber());
        values.put(deliveryDate, aa.getDeliveryDate());

        long a = db.insert(deliveryCycleTable, null, values);

        db.close();
        return a;
    }

    public List<Items> getDeliveryCycleTable() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM deliveryCycleTable ";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setSubscriptionDeliveryId(cursor.getString(1));
                aa.setDeliveryNumber(cursor.getString(2));
                aa.setDeliveryDate(cursor.getString(3));
                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }


    public long addtoCustomVegetableDetailsTable(Items aa) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(customerId, aa.getCustomerId());
        values.put(fsid, aa.getFsid());
        values.put(vegetableId, aa.getVegetableId());
        values.put(vegetableName, aa.getVegetableName());
        values.put(vegetableQuantity, aa.getVegetableQuantity());
        values.put(status, aa.getStatus());
        values.put(vegRate, aa.getVegRate());
        values.put(numberOfDays, aa.getNumberOfDays());
        values.put(customId, aa.getCustomId());
        values.put(subscriptionDeliveryId, aa.getSubscriptionDeliveryId());

        long a = db.insert(customVegetableDetailsTable, null, values);

        db.close();
        return a;
    }
    public long addtoComplaintDetailsTable(Items aa) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(customerId, aa.getCustomerId());
        values.put(fsid, aa.getFsid());
        values.put(complaintStatus, aa.getComplaintStatus());
        values.put(complaintNotes, aa.getComplaintNotes());
        values.put(subscriptionDeliveryId, aa.getSubscriptionDeliveryId());

        Log.e("cycle id...",aa.getSubscriptionDeliveryId());
        Log.e("complaintStatus...",aa.getComplaintStatus());


        long a = db.insert(complaintDetailsTable, null, values);

        db.close();
        return a;
    }

    public long addtoCustomerZoneDetailsTable(Items aa) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(zoneId, aa.getZoneId());
        values.put(zone, aa.getZone());

        long a = db.insert(customerZoneDetailsTable, null, values);

        db.close();
        return a;
    }
    public long addtoFarmDetailsTable(Items aa) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(farmId, aa.getFarmId());
        values.put(farmName, aa.getFarmName());
        values.put(status, aa.getStatus());

        long a = db.insert(farmDetailsTable, null, values);

        db.close();
        return a;
    }
    public long addtoOrderedVegetableTable(Items aa) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(vegetableId, aa.getVegetableId());
        values.put(vegetableName, aa.getVegetableName());

        long a = db.insert(orderedVegetableTable, null, values);

        db.close();
        return a;
    }


    public List<Items> getCustomerZoneDetailsTable() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM customerZoneDetailsTable ";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setZoneId(cursor.getString(1));
                aa.setZone(cursor.getString(2));

                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getOrderedVegetableTable() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM orderedVegetableTable ";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setVegetableId(cursor.getString(1));
                aa.setVegetableName(cursor.getString(2));

                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<Items> getFarmDetailsTableDetails() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM farmDetailsTable ";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setFarmId(cursor.getString(1));
                aa.setFarmName(cursor.getString(2));
                aa.setStatus(cursor.getString(3));

                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getFarmDetailsTableDetails(int w,String farmname) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM farmDetailsTable WHERE farmName LIKE '%"+farmname+"%'";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setFarmId(cursor.getString(1));
                aa.setFarmName(cursor.getString(2));
                aa.setStatus(cursor.getString(3));

                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }


    public List<Items> getAddedFarmDetailsTableDetails() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM farmDetailsTable WHERE status = 'true'";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setFarmId(cursor.getString(1));
                aa.setFarmName(cursor.getString(2));
                aa.setStatus(cursor.getString(3));

                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getAddedFarmDetailsTableDetails(String farmname) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM farmDetailsTable WHERE status = 'true' AND  farmName LIKE '%"+farmname+"%'";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setFarmId(cursor.getString(1));
                aa.setFarmName(cursor.getString(2));
                aa.setStatus(cursor.getString(3));

                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<Items> getFarmDetailsTableDetails(String[] ids) {
        SQLiteDatabase db = this.getReadableDatabase();

        String inClause = Arrays.toString(ids);
        // Log.e("clause..........", inClause);
        inClause = inClause.replace("[", "(");
        inClause = inClause.replace("]", ")");

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM farmDetailsTable WHERE farmId in "+inClause;

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setFarmId(cursor.getString(1));
                aa.setFarmName(cursor.getString(2));
                aa.setStatus(cursor.getString(3));
                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getFarmDetailsTableDetails(String farmid) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM farmDetailsTable WHERE farmId = '"+farmid+"'";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setFarmId(cursor.getString(1));
                aa.setFarmName(cursor.getString(2));
                aa.setStatus(cursor.getString(3));
                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public void updateStatusFarmDetailsTable(Items listItems, String fsiD) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(status, listItems.getStatus());

        db.update(farmDetailsTable, values, farmId + " = ? ", new String[] { fsiD });

    }
    public void updateDistQuantityToCustomerDetailsTable(Items listItems, String fsiD) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(distributedQuantity, listItems.getDistributedQuantity());

        db.update(customerDetailsTable, values, fsid + " = ? ", new String[] { fsiD });

    }
    public void updateFilledStatusToCustomerDetailsTable(Items listItems, String fsiD) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(filledStatus, listItems.getFilledStatus());

        db.update(customerDetailsTable, values, fsid + " = ? ", new String[] { fsiD });

    }
    public List<Items> getCustomerDetailsTable(String fsid) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM customerDetailsTable ";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setCustomerId(cursor.getInt(1));
                aa.setFsid(cursor.getString(2));
                aa.setCustomerName(cursor.getString(3));
                aa.setCustomerZone(cursor.getString(4));
                aa.setStatus(cursor.getString(5));
                aa.setFilledStatus(cursor.getString(6));
                aa.setPreferanceStatus(cursor.getString(7));
                aa.setPackQuantity(cursor.getString(8));
                aa.setDistributedQuantity(cursor.getString(9));
                aa.setOrderedQuantity(cursor.getString(10));
                aa.setComplaintCount(cursor.getString(11));
                aa.setPriorityStatus(cursor.getString(12));
                aa.setPackingStatus(cursor.getString(13));
                aa.setZoneId(cursor.getString(14));
                aa.setCustomerNote(cursor.getString(15));
                aa.setSubscriptionDeliveryId(cursor.getString(16));
                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<Items> getCustomerDetailsTable(int cycle_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM customerDetailsTable WHERE subscriptionDeliveryId = "
                +cycle_id+" ORDER BY customerName COLLATE NOCASE ASC";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setCustomerId(cursor.getInt(1));
                aa.setFsid(cursor.getString(2));
                aa.setCustomerName(cursor.getString(3));
                aa.setCustomerZone(cursor.getString(4));
                aa.setStatus(cursor.getString(5));
                aa.setFilledStatus(cursor.getString(6));
                aa.setPreferanceStatus(cursor.getString(7));
                aa.setPackQuantity(cursor.getString(8));
                aa.setDistributedQuantity(cursor.getString(9));
                aa.setOrderedQuantity(cursor.getString(10));
                aa.setComplaintCount(cursor.getString(11));
                aa.setPriorityStatus(cursor.getString(12));
                aa.setPackingStatus(cursor.getString(13));
                aa.setZoneId(cursor.getString(14));
                aa.setCustomerNote(cursor.getString(15));
                aa.setSubscriptionDeliveryId(cursor.getString(16));

                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<Items> getCustomerDetailsTable(String compltedStatus,String filledstatus,String zoneid) {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = null;
        List<Items> contactList = new ArrayList<Items>();

        countQuery = "SELECT  * FROM customerDetailsTable WHERE filledStatus = '"
                +filledstatus+"' AND packingStatus = '"+compltedStatus+"' AND subscriptionDeliveryId = '"
                +zoneid+"'";

        Log.e("query...",countQuery);
        /*else {
            countQuery = "SELECT  * FROM customerDetailsTable WHERE filledStatus = '"
                    +filledstatus+"' AND packingStatus = '"+compltedStatus+"' AND zoneId = '"
                    +zoneid+"'";

        }*/

        Log.e("countQuery........",countQuery);

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {


                Items aa = new Items();
                aa.setCustomerId(cursor.getInt(1));
                aa.setFsid(cursor.getString(2));
                aa.setCustomerName(cursor.getString(3));
                aa.setCustomerZone(cursor.getString(4));
                aa.setStatus(cursor.getString(5));
                aa.setFilledStatus(cursor.getString(6));
                aa.setPreferanceStatus(cursor.getString(7));
                aa.setPackQuantity(cursor.getString(8));
                aa.setDistributedQuantity(cursor.getString(9));
                aa.setOrderedQuantity(cursor.getString(10));
                aa.setComplaintCount(cursor.getString(11));
                aa.setPriorityStatus(cursor.getString(12));
                aa.setPackingStatus(cursor.getString(13));
                aa.setZoneId(cursor.getString(14));
                aa.setCustomerNote(cursor.getString(15));
                aa.setSubscriptionDeliveryId(cursor.getString(16));
                contactList.add(aa);


            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getCustomerDetailsTableForIncompleteBox() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM customerDetailsTable WHERE packingStatus = 'Not Completed'";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setCustomerId(cursor.getInt(1));
                aa.setFsid(cursor.getString(2));
                aa.setCustomerName(cursor.getString(3));
                aa.setCustomerZone(cursor.getString(4));
                aa.setStatus(cursor.getString(5));
                aa.setFilledStatus(cursor.getString(6));
                aa.setPreferanceStatus(cursor.getString(7));
                aa.setPackQuantity(cursor.getString(8));
                aa.setDistributedQuantity(cursor.getString(9));
                aa.setOrderedQuantity(cursor.getString(10));
                aa.setComplaintCount(cursor.getString(11));
                aa.setPriorityStatus(cursor.getString(12));
                aa.setPackingStatus(cursor.getString(13));
                aa.setZoneId(cursor.getString(14));
                aa.setCustomerNote(cursor.getString(15));
                aa.setSubscriptionDeliveryId(cursor.getString(16));
                contactList.add(aa);


            } while (cursor.moveToNext());
        }
        return contactList;
    }


    public List<Items> getCustomerDetailsTableForUnfilledBox() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM customerDetailsTable WHERE filledStatus = 'false'";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {


                Items aa = new Items();
                aa.setCustomerId(cursor.getInt(1));
                aa.setFsid(cursor.getString(2));
                aa.setCustomerName(cursor.getString(3));
                aa.setCustomerZone(cursor.getString(4));
                aa.setStatus(cursor.getString(5));
                aa.setFilledStatus(cursor.getString(6));
                aa.setPreferanceStatus(cursor.getString(7));
                aa.setPackQuantity(cursor.getString(8));
                aa.setDistributedQuantity(cursor.getString(9));
                aa.setOrderedQuantity(cursor.getString(10));
                aa.setComplaintCount(cursor.getString(11));
                aa.setPriorityStatus(cursor.getString(12));
                aa.setPackingStatus(cursor.getString(13));
                aa.setZoneId(cursor.getString(14));
                aa.setCustomerNote(cursor.getString(15));
                aa.setSubscriptionDeliveryId(cursor.getString(16));
                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<Items> getTradeCustomerDetailsTable() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM tradeCustomerDetailsTable ";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {
                Items aa = new Items();
                aa.setCustomerId(cursor.getInt(0));
                aa.setFsid(cursor.getString(1));
                aa.setCustomerName(cursor.getString(3));
                aa.setGroup(cursor.getString(2));
                aa.setSubGroup(cursor.getString(4));
                aa.setStatus(cursor.getString(5));
                aa.setOrderedQuantity(cursor.getString(6));
                aa.setPriorityStatus(cursor.getString(7));
                aa.setPackingStatus(cursor.getString(8));
                aa.setCustomerNote(cursor.getString(9));
                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getTradeCustomerDetailsTable(String fsid) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM tradeCustomerDetailsTable WHERE fsid = '"+fsid+"'";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {
                Items aa = new Items();
                aa.setCustomerId(cursor.getInt(0));
                aa.setFsid(cursor.getString(1));
                aa.setCustomerName(cursor.getString(3));
                aa.setGroup(cursor.getString(2));
                aa.setSubGroup(cursor.getString(4));
                aa.setStatus(cursor.getString(5));
                aa.setOrderedQuantity(cursor.getString(6));
                aa.setPriorityStatus(cursor.getString(7));
                aa.setPackingStatus(cursor.getString(8));
                aa.setCustomerNote(cursor.getString(9));
                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getTradeCustomerDetailsTable(String fsid,String customer_name) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM tradeCustomerDetailsTable WHERE customerName LIKE '%"+fsid+"%'";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {
                Items aa = new Items();
                aa.setCustomerId(cursor.getInt(0));
                aa.setFsid(cursor.getString(1));
                aa.setCustomerName(cursor.getString(3));
                aa.setGroup(cursor.getString(2));
                aa.setSubGroup(cursor.getString(4));
                aa.setStatus(cursor.getString(5));
                aa.setOrderedQuantity(cursor.getString(6));
                aa.setPriorityStatus(cursor.getString(7));
                aa.setPackingStatus(cursor.getString(8));
                aa.setCustomerNote(cursor.getString(9));
                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getTradeCustomerDetailsTable(String status,int customerId) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM tradeCustomerDetailsTable ";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {
                Items aa = new Items();
                aa.setCustomerId(cursor.getInt(0));
                aa.setFsid(cursor.getString(1));
                aa.setCustomerName(cursor.getString(3));
                aa.setGroup(cursor.getString(2));
                aa.setSubGroup(cursor.getString(4));
                aa.setStatus(cursor.getString(5));
                aa.setOrderedQuantity(cursor.getString(6));
                aa.setPriorityStatus(cursor.getString(7));
                aa.setPackingStatus(cursor.getString(8));
                aa.setCustomerNote(cursor.getString(9));
                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getTradeCustomerDetailsTable(int customerId) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM tradeCustomerDetailsTable WHERE customerId = "+customerId;

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {
                Items aa = new Items();
                aa.setCustomerId(cursor.getInt(0));
                aa.setFsid(cursor.getString(1));
                aa.setCustomerName(cursor.getString(3));
                aa.setGroup(cursor.getString(2));
                aa.setSubGroup(cursor.getString(4));
                aa.setStatus(cursor.getString(5));
                aa.setOrderedQuantity(cursor.getString(6));
                aa.setPriorityStatus(cursor.getString(7));
                aa.setPackingStatus(cursor.getString(8));
                aa.setCustomerNote(cursor.getString(9));
                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<Items> getVegetableOrderTableDetails() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM vegetableOrderTable ORDER BY vegetableName COLLATE NOCASE ASC ";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setVegetableId(cursor.getString(1));
                aa.setVegetableName(cursor.getString(2));
                aa.setOrderedQuantity(cursor.getString(3));
                aa.setReceivedQuantity(cursor.getString(4));
                aa.setFarmId(cursor.getString(5));
                aa.setCycleId(cursor.getString(6));
                aa.setSubscriptionDeliveryId(cursor.getString(7));
                aa.setNonPreferanceStatus(cursor.getString(8));
                aa.setNonPreferanceQuantity(cursor.getString(9));
                aa.setPreferanceQuantity(cursor.getString(10));
                aa.setTradeQuantity(cursor.getString(11));
                aa.setUnit(cursor.getString(12));
                aa.setCustomQuantity(cursor.getString(13));

                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getVegetableOrderTableDetailsForRemoveDuplicate(String cycleId) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM vegetableOrderTable WHERE subscriptionDeliveryId = '"+cycleId
                +"'ORDER BY vegetableName COLLATE NOCASE ASC ";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setVegetableId(cursor.getString(1));
                aa.setVegetableName(cursor.getString(2));
                aa.setOrderedQuantity(cursor.getString(3));
                aa.setReceivedQuantity(cursor.getString(4));
                aa.setFarmId(cursor.getString(5));
                aa.setCycleId(cursor.getString(6));
                aa.setSubscriptionDeliveryId(cursor.getString(7));
                aa.setNonPreferanceStatus(cursor.getString(8));
                aa.setNonPreferanceQuantity(cursor.getString(9));
                aa.setPreferanceQuantity(cursor.getString(10));
                aa.setTradeQuantity(cursor.getString(11));
                aa.setUnit(cursor.getString(12));
                aa.setCustomQuantity(cursor.getString(13));


                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getVegetableOrderTableDetails(int subId) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM vegetableOrderTable WHERE subscriptionDeliveryId = '"
                +subId+"' ORDER BY vegetableName COLLATE NOCASE ASC ";

        Log.e("countQuery..",countQuery);
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setVegetableId(cursor.getString(1));
                aa.setVegetableName(cursor.getString(2));
                aa.setOrderedQuantity(cursor.getString(3));
                aa.setReceivedQuantity(cursor.getString(4));
                aa.setFarmId(cursor.getString(5));
                aa.setCycleId(cursor.getString(6));
                aa.setSubscriptionDeliveryId(cursor.getString(7));
                aa.setNonPreferanceStatus(cursor.getString(8));
                aa.setNonPreferanceQuantity(cursor.getString(9));
                aa.setPreferanceQuantity(cursor.getString(10));
                aa.setTradeQuantity(cursor.getString(11));
                aa.setUnit(cursor.getString(12));


                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getVegetableOrderTableDetailsByCycle(String cycleId) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM vegetableOrderTable WHERE cycleId = '"+cycleId+"'";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setVegetableId(cursor.getString(1));
                aa.setVegetableName(cursor.getString(2));
                aa.setOrderedQuantity(cursor.getString(3));
                aa.setReceivedQuantity(cursor.getString(4));
                aa.setFarmId(cursor.getString(5));
                aa.setCycleId(cursor.getString(6));
                aa.setSubscriptionDeliveryId(cursor.getString(7));
                aa.setNonPreferanceStatus(cursor.getString(8));
                aa.setNonPreferanceQuantity(cursor.getString(9));
                aa.setPreferanceQuantity(cursor.getString(10));
                aa.setTradeQuantity(cursor.getString(11));
                aa.setUnit(cursor.getString(12));
                aa.setCustomQuantity(cursor.getString(13));

                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getVegetableOrderTableDetails(int[] IDs) {
        SQLiteDatabase db = this.getReadableDatabase();

        String inClause = Arrays.toString(IDs);
        inClause = inClause.replace("[", "(");
        inClause = inClause.replace("]", ")");

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM vegetableOrderTable WHERE vegetableId IN "+inClause;

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setVegetableId(cursor.getString(1));
                aa.setVegetableName(cursor.getString(2));
                aa.setOrderedQuantity(cursor.getString(3));
                aa.setReceivedQuantity(cursor.getString(4));
                aa.setFarmId(cursor.getString(5));
                aa.setCycleId(cursor.getString(6));
                aa.setSubscriptionDeliveryId(cursor.getString(7));
                aa.setNonPreferanceStatus(cursor.getString(8));
                aa.setNonPreferanceQuantity(cursor.getString(9));
                aa.setPreferanceQuantity(cursor.getString(10));
                aa.setTradeQuantity(cursor.getString(11));
                aa.setUnit(cursor.getString(12));
                aa.setCustomQuantity(cursor.getString(13));

                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getVegetableOrderTableDetails(int cycleid,String veg_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM vegetableOrderTable  WHERE  vegetableId = '"+veg_id
                +"' AND subscriptionDeliveryId = "+cycleid+" ORDER BY vegetableName COLLATE NOCASE ASC ";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setId(cursor.getString(0));
                aa.setVegetableId(cursor.getString(1));
                aa.setVegetableName(cursor.getString(2));
                aa.setOrderedQuantity(cursor.getString(3));
                aa.setReceivedQuantity(cursor.getString(4));
                aa.setFarmId(cursor.getString(5));
                aa.setCycleId(cursor.getString(6));
                aa.setSubscriptionDeliveryId(cursor.getString(7));
                aa.setNonPreferanceStatus(cursor.getString(8));
                aa.setNonPreferanceQuantity(cursor.getString(9));
                aa.setPreferanceQuantity(cursor.getString(10));
                aa.setTradeQuantity(cursor.getString(11));
                aa.setUnit(cursor.getString(12));
                aa.setCustomQuantity(cursor.getString(13));


                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getVegetableOrderTable(String veg_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM vegetableOrderTable  WHERE  id = '"+veg_id+"'";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setId(cursor.getString(0));
                aa.setVegetableId(cursor.getString(1));
                aa.setVegetableName(cursor.getString(2));
                aa.setOrderedQuantity(cursor.getString(3));
                aa.setReceivedQuantity(cursor.getString(4));
                aa.setFarmId(cursor.getString(5));
                aa.setCycleId(cursor.getString(6));
                aa.setSubscriptionDeliveryId(cursor.getString(7));
                aa.setNonPreferanceStatus(cursor.getString(8));
                aa.setNonPreferanceQuantity(cursor.getString(9));
                aa.setPreferanceQuantity(cursor.getString(10));
                aa.setTradeQuantity(cursor.getString(11));
                aa.setUnit(cursor.getString(12));
                aa.setCustomQuantity(cursor.getString(13));


                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getVegetableOrderTableDetails(String farm_id,int i,String cycleId) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM vegetableOrderTable  WHERE  farmId = '"+farm_id+"'" +
                " AND subscriptionDeliveryId = '"+cycleId+"'ORDER BY vegetableName COLLATE NOCASE ASC ";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setVegetableId(cursor.getString(1));
                aa.setVegetableName(cursor.getString(2));
                aa.setOrderedQuantity(cursor.getString(3));
                aa.setReceivedQuantity(cursor.getString(4));
                aa.setFarmId(cursor.getString(5));
                aa.setCycleId(cursor.getString(6));
                aa.setSubscriptionDeliveryId(cursor.getString(7));
                aa.setNonPreferanceStatus(cursor.getString(8));
                aa.setNonPreferanceQuantity(cursor.getString(9));
                aa.setPreferanceQuantity(cursor.getString(10));
                aa.setTradeQuantity(cursor.getString(11));
                aa.setUnit(cursor.getString(12));
                aa.setCustomQuantity(cursor.getString(13));


                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getVegetableOrderTableDetailsForFarms(String farm_id,int i,String cycleId,String vegetable_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM vegetableOrderTable  WHERE  farmId = '"+farm_id+"'" +
                " AND subscriptionDeliveryId = '"+cycleId+"' AND vegetableId = '"+vegetable_id+"' ORDER BY vegetableName COLLATE NOCASE ASC ";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setVegetableId(cursor.getString(1));
                aa.setVegetableName(cursor.getString(2));
                aa.setOrderedQuantity(cursor.getString(3));
                aa.setReceivedQuantity(cursor.getString(4));
                aa.setFarmId(cursor.getString(5));
                aa.setCycleId(cursor.getString(6));
                aa.setSubscriptionDeliveryId(cursor.getString(7));
                aa.setNonPreferanceStatus(cursor.getString(8));
                aa.setNonPreferanceQuantity(cursor.getString(9));
                aa.setPreferanceQuantity(cursor.getString(10));
                aa.setTradeQuantity(cursor.getString(11));
                aa.setUnit(cursor.getString(12));
                aa.setCustomQuantity(cursor.getString(13));


                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getVegetableOrderTableDetailsForSync(String cycle_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
       // String countQuery = "SELECT  * FROM vegetableOrderTable WHERE NOT farmId = ''";
        String countQuery = "SELECT  * FROM vegetableOrderTable WHERE subscriptionDeliveryId = '"+cycle_id+"'";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setVegetableId(cursor.getString(1));
                aa.setVegetableName(cursor.getString(2));
                aa.setOrderedQuantity(cursor.getString(3));
                aa.setReceivedQuantity(cursor.getString(4));
                aa.setFarmId(cursor.getString(5));
                aa.setCycleId(cursor.getString(6));
                aa.setSubscriptionDeliveryId(cursor.getString(7));
                aa.setNonPreferanceStatus(cursor.getString(8));
                aa.setNonPreferanceQuantity(cursor.getString(9));
                aa.setPreferanceQuantity(cursor.getString(10));
                aa.setTradeQuantity(cursor.getString(11));
                aa.setUnit(cursor.getString(12));
                aa.setCustomQuantity(cursor.getString(13));


                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getVegetableOrderTableDetailsForNotsetFarm(String vid) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM vegetableOrderTable WHERE vegetableId = '"
                +vid+"' AND farmId = '39'" ;

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setVegetableId(cursor.getString(1));
                aa.setVegetableName(cursor.getString(2));
                aa.setOrderedQuantity(cursor.getString(3));
                aa.setReceivedQuantity(cursor.getString(4));
                aa.setFarmId(cursor.getString(5));
                aa.setCycleId(cursor.getString(6));
                aa.setSubscriptionDeliveryId(cursor.getString(7));
                aa.setNonPreferanceStatus(cursor.getString(8));
                aa.setNonPreferanceQuantity(cursor.getString(9));
                aa.setPreferanceQuantity(cursor.getString(10));
                aa.setTradeQuantity(cursor.getString(11));
                aa.setUnit(cursor.getString(12));
                aa.setCustomQuantity(cursor.getString(13));


                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getVegetableOrderTableDetails(String vegname,String cycleId) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM vegetableOrderTable WHERE vegetableName LIKE '%"+vegname
                +"%' AND subscriptionDeliveryId = '"+cycleId+"'";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setVegetableId(cursor.getString(1));
                aa.setVegetableName(cursor.getString(2));
                aa.setOrderedQuantity(cursor.getString(3));
                aa.setReceivedQuantity(cursor.getString(4));
                aa.setFarmId(cursor.getString(5));
                aa.setCycleId(cursor.getString(6));
                aa.setSubscriptionDeliveryId(cursor.getString(7));
                aa.setNonPreferanceStatus(cursor.getString(8));
                aa.setNonPreferanceQuantity(cursor.getString(9));
                aa.setPreferanceQuantity(cursor.getString(10));
                aa.setTradeQuantity(cursor.getString(11));
                aa.setUnit(cursor.getString(12));
                aa.setCustomQuantity(cursor.getString(13));


                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public void updateNonPrefStatusToVegetableOrderTable(Items listItems, String vegId,String cycleId) {

        Log.e("v .......Id.........",vegId);
        Log.e("status........",listItems.getNonPreferanceStatus());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(nonPreferanceStatus, listItems.getNonPreferanceStatus());

        db.update(vegetableOrderTable, values, vegetableId + " = ?  AND "+subscriptionDeliveryId+" =?", new String[] { vegId,cycleId });

    }
    public void updateVegetableQuantityTableDetails(Items listItems, String vegId,String farmid) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Log.e("recievedQuantity......",listItems.getReceivedQuantity()+" Farm Id......"+farmid);

        values.put(recievedQuantity, listItems.getReceivedQuantity());

        db.update(vegetableOrderTable, values, vegetableId + " = ? AND "+farmId+" =?", new String[] { vegId,farmid });

    }
    public void updateFarmIdDetails(Items listItems, String id) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.e("farmId......",listItems.getFarmId());

        values.put(farmId, listItems.getFarmId());

        db.update(vegetableOrderTable, values, id + " = ? ", new String[] { id });

    }
    public void updateVegetableOrderTableDetails(Items listItems, String vegId) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(recievedQuantity, listItems.getReceivedQuantity());
        values.put(farmId, listItems.getFarmId());

        db.update(vegetableOrderTable, values, vegetableId + " = ? ", new String[] { vegId });

    }

    public List<Items> getVegetableStockTableDetails() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM vegetableStockTable ORDER BY vegetableName COLLATE NOCASE ASC";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setVegetableId(cursor.getString(1));
                aa.setVegetableName(cursor.getString(2));
                aa.setSubscriptionQuantity(cursor.getString(3));
                aa.setTradeQuantity(cursor.getString(4));
                aa.setOrderedQuantity(cursor.getString(5));
                aa.setReceivedQuantity(cursor.getString(6));
                aa.setExcessQuantity(cursor.getString(7));
                aa.setWastageQuantity(cursor.getString(8));
                aa.setVegRate(cursor.getString(9));
                aa.setFarmName(cursor.getString(10));
                aa.setFarmId(cursor.getString(11));
                aa.setVegetableQuality(cursor.getString(12));
                aa.setNonPreferanceStatus(cursor.getString(13));
                aa.setSubscriptionDeliveryId(cursor.getString(14));
                aa.setNonPreferanceQuantity(cursor.getString(15));
                aa.setPreferanceQuantity(cursor.getString(16));
                aa.setCustomQuantity(cursor.getString(17));


                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getVegetableStockTableDetails(int cycleId) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM vegetableStockTable WHERE subscriptionDeliveryId = "
                +cycleId+" ORDER BY vegetableName COLLATE NOCASE ASC";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setVegetableId(cursor.getString(1));
                aa.setVegetableName(cursor.getString(2));
                aa.setOrderId(cursor.getString(3));
                aa.setTradeQuantity(cursor.getString(4));
                aa.setOrderedQuantity(cursor.getString(5));
                aa.setReceivedQuantity(cursor.getString(6));
                aa.setExcessQuantity(cursor.getString(7));
                aa.setWastageQuantity(cursor.getString(8));
                aa.setVegRate(cursor.getString(9));
                aa.setFarmName(cursor.getString(10));
                aa.setFarmId(cursor.getString(11));
                aa.setVegetableQuality(cursor.getString(12));
                aa.setNonPreferanceStatus(cursor.getString(13));
                aa.setSubscriptionDeliveryId(cursor.getString(14));
                aa.setNonPreferanceQuantity(cursor.getString(15));
                aa.setPreferanceQuantity(cursor.getString(16));
                aa.setCustomQuantity(cursor.getString(17));

                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<Items> getVegetableStockTableDetails(int a,String farm_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM vegetableStockTable WHERE farmId = '"+farm_id+"'";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setVegetableId(cursor.getString(1));
                aa.setVegetableName(cursor.getString(2));
                aa.setOrderId(cursor.getString(3));
                aa.setTradeQuantity(cursor.getString(4));
                aa.setOrderedQuantity(cursor.getString(5));
                aa.setReceivedQuantity(cursor.getString(6));
                aa.setExcessQuantity(cursor.getString(7));
                aa.setWastageQuantity(cursor.getString(8));
                aa.setVegRate(cursor.getString(9));
                aa.setFarmName(cursor.getString(10));
                aa.setFarmId(cursor.getString(11));
                aa.setVegetableQuality(cursor.getString(12));
                aa.setNonPreferanceStatus(cursor.getString(13));
                aa.setSubscriptionDeliveryId(cursor.getString(14));
                aa.setNonPreferanceQuantity(cursor.getString(15));
                aa.setPreferanceQuantity(cursor.getString(16));
                aa.setCustomQuantity(cursor.getString(17));

                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<Items> getVegetableStockTableDetails(String vegname,int cycle_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM vegetableStockTable WHERE vegetableName LIKE '%"
                +vegname+"%' AND subscriptionDeliveryId = "+cycle_id;
        Log.e("querey...",countQuery);

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setVegetableId(cursor.getString(1));
                aa.setVegetableName(cursor.getString(2));
                aa.setOrderId(cursor.getString(3));
                aa.setTradeQuantity(cursor.getString(4));
                aa.setOrderedQuantity(cursor.getString(5));
                aa.setReceivedQuantity(cursor.getString(6));
                aa.setExcessQuantity(cursor.getString(7));
                aa.setWastageQuantity(cursor.getString(8));
                aa.setVegRate(cursor.getString(9));
                aa.setFarmName(cursor.getString(10));
                aa.setFarmId(cursor.getString(11));
                aa.setVegetableQuality(cursor.getString(12));
                aa.setNonPreferanceStatus(cursor.getString(13));
                aa.setSubscriptionDeliveryId(cursor.getString(14));
                aa.setNonPreferanceQuantity(cursor.getString(15));
                aa.setPreferanceQuantity(cursor.getString(16));
                aa.setCustomQuantity(cursor.getString(17));
                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public void updateVegetableStockTable(Items listItems, String vegId,String cycleId) {

       // Log.e("vegId.........",vegId);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(excessQuantity, listItems.getExcessQuantity());
        values.put(wastageQuantity, listItems.getWastageQuantity());
        values.put(recievedQuantity, listItems.getReceivedQuantity());
        values.put(vegetableQuality, listItems.getVegetableQuality());
        values.put(vegRate, listItems.getVegRate());

        db.update(vegetableStockTable, values, vegetableId + " = ?  AND "+subscriptionDeliveryId+" =?", new String[] { vegId,cycleId });

    }
    public void updateNonPrefStatusToVegetableStockTable(Items listItems, String vegId,String cycleId) {

//        Log.e("v .......Id.........",vegId);
//        Log.e("status........",listItems.getNonPreferanceStatus());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(nonPreferanceStatus, listItems.getNonPreferanceStatus());

        db.update(vegetableStockTable, values, vegetableId + " = ?  AND "+subscriptionDeliveryId+" =?", new String[] { vegId,cycleId });

    }

    public List<Items> getCustomerDetailsTable(String fsid,String cycle_Id) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM customerDetailsTable where fsid = '"
                +fsid+"' AND subscriptionDeliveryId = '"+cycle_Id+"'";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setCustomerId(cursor.getInt(1));
                aa.setFsid(cursor.getString(2));
                aa.setCustomerName(cursor.getString(3));
                aa.setCustomerZone(cursor.getString(4));
                aa.setStatus(cursor.getString(5));
                aa.setFilledStatus(cursor.getString(6));
                aa.setPreferanceStatus(cursor.getString(7));
                aa.setPackQuantity(cursor.getString(8));
                aa.setDistributedQuantity(cursor.getString(9));
                aa.setOrderedQuantity(cursor.getString(10));
                aa.setComplaintCount(cursor.getString(11));
                aa.setPriorityStatus(cursor.getString(12));
                aa.setPackingStatus(cursor.getString(13));
                aa.setZoneId(cursor.getString(14));
                aa.setCustomerNote(cursor.getString(15));
                aa.setSubscriptionDeliveryId(cursor.getString(16));
                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<Items> getCustomerDetailsTable(String fsid,int cycle_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM customerDetailsTable WHERE customerName LIKE '%"+
                fsid+"%' AND subscriptionDeliveryId = "+cycle_id;
        Log.e("querey...",countQuery);

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setCustomerId(cursor.getInt(1));
                aa.setFsid(cursor.getString(2));
                aa.setCustomerName(cursor.getString(3));
                aa.setCustomerZone(cursor.getString(4));
                aa.setStatus(cursor.getString(5));
                aa.setFilledStatus(cursor.getString(6));
                aa.setPreferanceStatus(cursor.getString(7));
                aa.setPackQuantity(cursor.getString(8));
                aa.setDistributedQuantity(cursor.getString(9));
                aa.setOrderedQuantity(cursor.getString(10));
                aa.setComplaintCount(cursor.getString(11));
                aa.setPriorityStatus(cursor.getString(12));
                aa.setPackingStatus(cursor.getString(13));
                aa.setZoneId(cursor.getString(14));
                aa.setCustomerNote(cursor.getString(15));
                aa.setSubscriptionDeliveryId(cursor.getString(16));
                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getCustomerDetailsTable(String prefStatus,String filledStatus,String status,String zone) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM customerDetailsTable where preferanceStatus = "+prefStatus+" and " +
                "filledStatus = "+filledStatus+" and status = "+status+" and customerZone = '"+zone+"'" ;

        Log.e("querey ...",countQuery);

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setCustomerId(cursor.getInt(1));
                aa.setFsid(cursor.getString(2));
                aa.setCustomerName(cursor.getString(3));
                aa.setCustomerZone(cursor.getString(4));
                aa.setStatus(cursor.getString(5));
                aa.setFilledStatus(cursor.getString(6));
                aa.setPreferanceStatus(cursor.getString(7));
                aa.setPackQuantity(cursor.getString(8));
                aa.setDistributedQuantity(cursor.getString(9));
                aa.setOrderedQuantity(cursor.getString(10));
                aa.setComplaintCount(cursor.getString(11));
                aa.setPriorityStatus(cursor.getString(12));
                aa.setPackingStatus(cursor.getString(13));
                aa.setZoneId(cursor.getString(14));
                aa.setCustomerNote(cursor.getString(15));
                aa.setSubscriptionDeliveryId(cursor.getString(16));
                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }


    public List<Items> getVegetableDetailsTable() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM vegetableDetailsTable ";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setCustomerId(cursor.getInt(1));
                aa.setFsid(cursor.getString(2));
                aa.setVegetableId(cursor.getString(3));
                aa.setVegetableName(cursor.getString(4));
                aa.setVegetableQuantity(cursor.getString(5));
                aa.setDistributedStatus(cursor.getString(6));
                aa.setIncrementQuantity(cursor.getString(7));
                aa.setPreferanceValue(cursor.getString(8));
                aa.setFarmId(cursor.getString(9));
                aa.setIsVerified(cursor.getString(10));
                aa.setVegetableRank(cursor.getInt(11));
                aa.setSubscriptionDeliveryId(cursor.getString(12));

                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<Items> getVegetableDetailsTable(String fsid) {
        SQLiteDatabase db = this.getReadableDatabase();

        Log.e("fsid..", fsid);

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM vegetableDetailsTable WHERE fsid = '"+fsid+"' AND " +
                "preferanceValue = 'YES'";
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setCustomerId(cursor.getInt(1));
                aa.setFsid(cursor.getString(2));
                aa.setVegetableId(cursor.getString(3));
                aa.setVegetableName(cursor.getString(4));
                aa.setVegetableQuantity(cursor.getString(5));
                aa.setDistributedStatus(cursor.getString(6));
                aa.setIncrementQuantity(cursor.getString(7));
                aa.setPreferanceValue(cursor.getString(8));
                aa.setFarmId(cursor.getString(9));
                aa.setIsVerified(cursor.getString(10));
                aa.setVegetableRank(cursor.getInt(11));
                aa.setSubscriptionDeliveryId(cursor.getString(12));

                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getVegetableDetailsTable(String fsid,int distributedstatus) {
        SQLiteDatabase db = this.getReadableDatabase();

        Log.e("fsid....",fsid);
        List<Items> contactList = new ArrayList<Items>();
        /*String countQuery = "SELECT  * FROM vegetableDetailsTable where fsid = '"+fsid
                +"' AND (preferanceValue = 'YES' OR preferanceValue = 'MAYBE') ORDER BY vegetableRank ASC ";*/
        String countQuery = "SELECT  * FROM vegetableDetailsTable where fsid = '"+fsid
                +"' ORDER BY vegetableRank ASC ";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setCustomerId(cursor.getInt(1));
                aa.setFsid(cursor.getString(2));
                aa.setVegetableId(cursor.getString(3));
                aa.setVegetableName(cursor.getString(4));
                aa.setVegetableQuantity(cursor.getString(5));
                aa.setDistributedStatus(cursor.getString(6));
                aa.setIncrementQuantity(cursor.getString(7));
                aa.setPreferanceValue(cursor.getString(8));
                aa.setFarmId(cursor.getString(9));
                aa.setIsVerified(cursor.getString(10));
                aa.setVegetableRank(cursor.getInt(11));
                aa.setSubscriptionDeliveryId(cursor.getString(12));

                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getVegetableDetailsTableList(String fsid,String distributedstatus,String cycle_Id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Log.e("fsid....",fsid);
        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM vegetableDetailsTable where fsid = '"+fsid
                +"' AND preferanceValue = '"+distributedstatus+"' AND subscriptionDeliveryId = '"+
                cycle_Id+"' ORDER BY vegetableRank DESC ";
        Log.e("countQuery...",countQuery);
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();

                Log.e("rank...",""+cursor.getInt(11));

                aa.setCustomerId(cursor.getInt(1));
                aa.setFsid(cursor.getString(2));
                aa.setVegetableId(cursor.getString(3));
                aa.setVegetableName(cursor.getString(4));
                aa.setVegetableQuantity(cursor.getString(5));
                aa.setDistributedStatus(cursor.getString(6));
                aa.setIncrementQuantity(cursor.getString(7));
                aa.setPreferanceValue(cursor.getString(8));
                aa.setFarmId(cursor.getString(9));
                aa.setIsVerified(cursor.getString(10));
                aa.setVegetableRank(cursor.getInt(11));
                aa.setSubscriptionDeliveryId(cursor.getString(12));


                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getYesPrefVegetableDetailsTable(String fsid) {
        SQLiteDatabase db = this.getReadableDatabase();

        Log.e("fsid....",fsid);
        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM vegetableDetailsTable where fsid = '"+fsid
                +"' AND preferanceValue = 'YES'";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setCustomerId(cursor.getInt(1));
                aa.setFsid(cursor.getString(2));
                aa.setVegetableId(cursor.getString(3));
                aa.setVegetableName(cursor.getString(4));
                aa.setVegetableQuantity(cursor.getString(5));
                aa.setDistributedStatus(cursor.getString(6));
                aa.setIncrementQuantity(cursor.getString(7));
                aa.setPreferanceValue(cursor.getString(8));
                aa.setFarmId(cursor.getString(9));
                aa.setIsVerified(cursor.getString(10));
                aa.setVegetableRank(cursor.getInt(11));
                aa.setSubscriptionDeliveryId(cursor.getString(12));

                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getMayBePrefVegetableDetailsTable(String fsid) {
        SQLiteDatabase db = this.getReadableDatabase();

        Log.e("fsid....",fsid);
        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM vegetableDetailsTable where fsid = '"+fsid
                +"' AND preferanceValue = 'MAYBE'";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setCustomerId(cursor.getInt(1));
                aa.setFsid(cursor.getString(2));
                aa.setVegetableId(cursor.getString(3));
                aa.setVegetableName(cursor.getString(4));
                aa.setVegetableQuantity(cursor.getString(5));
                aa.setDistributedStatus(cursor.getString(6));
                aa.setIncrementQuantity(cursor.getString(7));
                aa.setPreferanceValue(cursor.getString(8));
                aa.setFarmId(cursor.getString(9));
                aa.setIsVerified(cursor.getString(10));
                aa.setVegetableRank(cursor.getInt(11));
                aa.setSubscriptionDeliveryId(cursor.getString(12));

                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getVegetableDetailsTableForFilter(String fsid,String filterText,String cycle_Id) {
        SQLiteDatabase db = this.getReadableDatabase();


        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM vegetableDetailsTable where fsid = '"+fsid
                +"' and vegetableName LIKE '%"+filterText+"%' AND subscriptionDeliveryId = '"+cycle_Id+"'";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setCustomerId(cursor.getInt(1));
                aa.setFsid(cursor.getString(2));
                aa.setVegetableId(cursor.getString(3));
                aa.setVegetableName(cursor.getString(4));
                aa.setVegetableQuantity(cursor.getString(5));
                aa.setDistributedStatus(cursor.getString(6));
                aa.setIncrementQuantity(cursor.getString(7));
                aa.setPreferanceValue(cursor.getString(8));
                aa.setFarmId(cursor.getString(9));
                aa.setIsVerified(cursor.getString(10));
                aa.setVegetableRank(cursor.getInt(11));
                aa.setSubscriptionDeliveryId(cursor.getString(12));

                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getVegetableDetailsTable(String fsid,String distributedstatus,String cycle_Id) {
        SQLiteDatabase db = this.getReadableDatabase();


        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM vegetableDetailsTable where fsid = '"+fsid+"' " +
                "and distributedStatus = '"+distributedstatus+"' AND subscriptionDeliveryId = '"+
                cycle_Id+"' ORDER BY vegetableRank DESC ";

        Log.e("fsid......",fsid);

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setCustomerId(cursor.getInt(1));
                aa.setFsid(cursor.getString(2));
                aa.setVegetableId(cursor.getString(3));
                aa.setVegetableName(cursor.getString(4));
                aa.setVegetableQuantity(cursor.getString(5));
                aa.setDistributedStatus(cursor.getString(6));
                aa.setIncrementQuantity(cursor.getString(7));
                aa.setPreferanceValue(cursor.getString(8));
                aa.setFarmId(cursor.getString(9));
                aa.setIsVerified(cursor.getString(10));
                aa.setVegetableRank(cursor.getInt(11));
                aa.setSubscriptionDeliveryId(cursor.getString(12));

                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }


    public List<Items> getVegetableDetailsTable(int cycle_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM vegetableDetailsTable where distributedStatus = 'true'" +
                " AND subscriptionDeliveryId = '"+cycle_id+"'";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setCustomerId(cursor.getInt(1));
                aa.setFsid(cursor.getString(2));
                aa.setVegetableId(cursor.getString(3));
                aa.setVegetableName(cursor.getString(4));
                aa.setVegetableQuantity(cursor.getString(5));
                aa.setDistributedStatus(cursor.getString(6));
                aa.setIncrementQuantity(cursor.getString(7));
                aa.setPreferanceValue(cursor.getString(8));
                aa.setFarmId(cursor.getString(9));
                aa.setIsVerified(cursor.getString(10));
                aa.setVegetableRank(cursor.getInt(11));
                aa.setSubscriptionDeliveryId(cursor.getString(12));
                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public void updateCustomerDetailsTable(Items listItems, String fsiD) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(packingStatus, listItems.getPackingStatus());

        db.update(customerDetailsTable, values, fsid + " = ? ", new String[] { fsiD });

    }
    public void updateIsverifiedStatusVegetableDetailsTable(Items listItems, String vegetableid,String fsId,String cycle_Id) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

       // Log.e("Status.....",listItems.getIsVerified()+" vegetableid......."+ vegetableid);
        values.put(isVerified, listItems.getIsVerified());

        db.update(vegetableDetailsTable, values, vegetableId + " = ? and "+
                fsid+" =? and "+subscriptionDeliveryId+" =?", new String[] { vegetableid,fsId,cycle_Id});

    }
    public void updateDistributedStatusVegetableDetailsTable(Items listItems, String vegetableid,
                                                             String fsId,String cycle_Id) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //Log.e("distributedStatus.....",listItems.getDistributedStatus());
        //Log.e("vegetableQuantity.....",listItems.getVegetableQuantity());

        values.put(distributedStatus, listItems.getDistributedStatus());
        values.put(vegetableQuantity, listItems.getVegetableQuantity());

        db.update(vegetableDetailsTable, values, vegetableId + " = ? and "+
                fsid+" =?and "+subscriptionDeliveryId+" =?", new String[] { vegetableid,fsId,cycle_Id});

    }
    public void updateStatusVegetableDetailsTable(Items listItems, String vegetableid,String fsId,String cycle_Id) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(isVerified, listItems.getDistributedStatus());

        db.update(vegetableDetailsTable, values, vegetableId + " = ? and "+
                fsid+" =? and "+subscriptionDeliveryId+" =?", new String[] { vegetableid,fsId,cycle_Id});

    }
    public void updateQuantityToVegetableDetailsTable(Items listItems, String vegetableid,
                                                      String fsId,String cycle_Id) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(vegetableQuantity, listItems.getVegetableQuantity());
        Log.e("quantity.....",listItems.getVegetableQuantity());

        db.update(vegetableDetailsTable, values, vegetableId + " = ? and "+
                fsid+" =? and "+subscriptionDeliveryId+" =?", new String[] { vegetableid,fsId,cycle_Id});

    }


    public List<Items> getComplaintDetailsTable(String cycle_Id) {
        SQLiteDatabase db = this.getReadableDatabase();
// WHERE complaintStatus IS NOT 'NO'
        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM complaintDetailsTable  WHERE NOT complaintStatus = 'NO'" +
                " AND subscriptionDeliveryId = '"+cycle_Id+"'";
       // String countQuery = "SELECT  * FROM complaintDetailsTable WHERE subscriptionDeliveryId = '"+cycle_Id+"'";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {


                Items aa = new Items();
                aa.setCustomerId(cursor.getInt(1));
                aa.setFsid(cursor.getString(2));
                aa.setComplaintNotes(cursor.getString(3));
                aa.setComplaintStatus(cursor.getString(4));
                aa.setSubscriptionDeliveryId(cursor.getString(5));

                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getComplaintDetailsTable(String  fsid, String cycle_Id) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM complaintDetailsTable  where fsid = '"
                +fsid+"' AND subscriptionDeliveryId = '"+cycle_Id+"'";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {


                Items aa = new Items();

                aa.setCompId(cursor.getInt(0));
                aa.setCustomerId(cursor.getInt(1));
                aa.setFsid(cursor.getString(2));
                aa.setComplaintNotes(cursor.getString(3));
                aa.setComplaintStatus(cursor.getString(4));
                aa.setSubscriptionDeliveryId(cursor.getString(5));

                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }


    public void updateComplaintComplaintDetailsTable(Items listItems, String fsId,String cycle_Id) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(complaintStatus, listItems.getComplaintStatus());
        Log.e("vccccvcvcvc......",listItems.getComplaintStatus());

        db.update(complaintDetailsTable, values, fsid + " = ? and "+subscriptionDeliveryId+" =? ", new String[] { fsId,cycle_Id });

    }
    public void updateNotesComplaintDetailsTable(Items listItems, String fsId,String cycle_Id) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(complaintNotes, listItems.getComplaintNotes());
        Log.e("complaintNotes......",listItems.getComplaintNotes());

        db.update(complaintDetailsTable, values, fsid + " = ? and "+subscriptionDeliveryId+" =? ", new String[] { fsId,cycle_Id });

    }

    public List<Items> getCustomVegetableDetailsTable() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM customVegetableDetailsTable ";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setId(cursor.getString(0));
                aa.setCustomerId(cursor.getInt(1));
                aa.setFsid(cursor.getString(2));
                aa.setVegetableId(cursor.getString(3));
                aa.setVegetableName(cursor.getString(4));
                aa.setVegetableQuantity(cursor.getString(5));
                aa.setStatus(cursor.getString(6));
                aa.setVegRate(cursor.getString(7));
                aa.setCustomId(cursor.getString(8));
                aa.setNumberOfDays(cursor.getString(9));
                aa.setSubscriptionDeliveryId(cursor.getString(10));

                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<Items> getCustomVegetableDetailsTable(int cycle_id) {

        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM customVegetableDetailsTable where NOT status = 'ORDERED'" +
                " AND subscriptionDeliveryId = "+cycle_id;

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setId(cursor.getString(0));
                aa.setCustomerId(cursor.getInt(1));
                aa.setFsid(cursor.getString(2));
                aa.setVegetableId(cursor.getString(3));
                aa.setVegetableName(cursor.getString(4));
                aa.setVegetableQuantity(cursor.getString(5));
                aa.setStatus(cursor.getString(6));
                aa.setVegRate(cursor.getString(7));
                aa.setCustomId(cursor.getString(8));
                aa.setNumberOfDays(cursor.getString(9));
                aa.setSubscriptionDeliveryId(cursor.getString(10));


                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<Items> getCustomVegetableDetailsTable(int a,String fsid,String cycle_Id) {

        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM customVegetableDetailsTable where fsid = '"
                +fsid+"' AND subscriptionDeliveryId = '"+cycle_Id+"'";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setId(cursor.getString(0));
                aa.setCustomerId(cursor.getInt(1));
                aa.setFsid(cursor.getString(2));
                aa.setVegetableId(cursor.getString(3));
                aa.setVegetableName(cursor.getString(4));
                aa.setVegetableQuantity(cursor.getString(5));
                aa.setStatus(cursor.getString(6));
                aa.setVegRate(cursor.getString(7));
                aa.setCustomId(cursor.getString(8));
                aa.setNumberOfDays(cursor.getString(9));
                aa.setSubscriptionDeliveryId(cursor.getString(10));

                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getCustomVegetableDetailsTable(String fsid,int cycle_id) {

        SQLiteDatabase db = this.getReadableDatabase();

        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM customVegetableDetailsTable where fsid = '" +fsid+
                "' AND status = 'ORDERED' AND subscriptionDeliveryId = "+cycle_id;

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setId(cursor.getString(0));
                aa.setCustomerId(cursor.getInt(1));
                aa.setFsid(cursor.getString(2));
                aa.setVegetableId(cursor.getString(3));
                aa.setVegetableName(cursor.getString(4));
                aa.setVegetableQuantity(cursor.getString(5));
                aa.setStatus(cursor.getString(6));
                aa.setVegRate(cursor.getString(7));
                aa.setCustomId(cursor.getString(8));
                aa.setNumberOfDays(cursor.getString(9));
                aa.setSubscriptionDeliveryId(cursor.getString(10));

                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public List<Items> getCustomVegetableDetailsTable(String fsid,String status,String cycle_Id) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Items> contactList = new ArrayList<Items>();
        String countQuery = "SELECT  * FROM customVegetableDetailsTable where fsid = '" +fsid+"' " +
                "and status = '"+status+"' AND subscriptionDeliveryId = '"+cycle_Id+"'";

        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (!cursor.isAfterLast()) {
            do {

                Items aa = new Items();
                aa.setId(cursor.getString(0));
                aa.setCustomerId(cursor.getInt(1));
                aa.setFsid(cursor.getString(2));
                aa.setVegetableId(cursor.getString(3));
                aa.setVegetableName(cursor.getString(4));
                aa.setVegetableQuantity(cursor.getString(5));
                aa.setStatus(cursor.getString(6));
                aa.setVegRate(cursor.getString(7));
                aa.setCustomId(cursor.getString(8));
                aa.setNumberOfDays(cursor.getString(9));
                aa.setSubscriptionDeliveryId(cursor.getString(10));

                contactList.add(aa);

            } while (cursor.moveToNext());
        }
        return contactList;
    }
    public void updateStatusToCustomVegetableDetailsTable(Items listItems, String vegetableid,String cycle_Id) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(status, listItems.getStatus());
        Log.e("sttttttt....",listItems.getStatus());

        db.update(customVegetableDetailsTable, values, id + " = ? and "
                +subscriptionDeliveryId+" =? ", new String[] { vegetableid,cycle_Id });

    }

    public void deleteCustomerDetailsTable() {

        SQLiteDatabase db = this.getWritableDatabase();

        String querey = "DELETE FROM customerDetailsTable";

        db.execSQL(querey);


    }
    public void deleteVegetableDetailsTable() {

        SQLiteDatabase db = this.getWritableDatabase();

        String querey = "DELETE FROM vegetableDetailsTable";

        db.execSQL(querey);


    }
    public void deleteCustomVegetableDetailsTable() {

        SQLiteDatabase db = this.getWritableDatabase();

        String querey = "DELETE FROM customVegetableDetailsTable";

        db.execSQL(querey);


    }
    public void deleteComplaintDetailsTable() {

        SQLiteDatabase db = this.getWritableDatabase();

        String querey = "DELETE FROM complaintDetailsTable";

        db.execSQL(querey);


    }
    public void deleteComplaintDetailsTable(int a) {

        SQLiteDatabase db = this.getWritableDatabase();

        Log.e("sadsa","asdfads");
        String querey = "DELETE FROM complaintDetailsTable where id = 13 ";

        db.execSQL(querey);


    }
    public void deleteCustomerZoneDetailsTable() {

        SQLiteDatabase db = this.getWritableDatabase();

        String querey = "DELETE FROM customerZoneDetailsTable";

        db.execSQL(querey);


    }
    public void deleteVegetableOrderDetailsTable() {

        SQLiteDatabase db = this.getWritableDatabase();

        String querey = "DELETE FROM vegetableOrderTable";

        db.execSQL(querey);


    }
    public void deleteVegetableOrderDetailsTable(String vegid,String farmid) {

        SQLiteDatabase db = this.getWritableDatabase();

        String querey = "DELETE FROM vegetableOrderTable WHERE vegetableId = '"
                +vegid+"' AND farmId = '"+farmid+"'";

        db.execSQL(querey);


    }
    public void deleteFarmDetailsTable() {

        SQLiteDatabase db = this.getWritableDatabase();

        String querey = "DELETE FROM farmDetailsTable";

        db.execSQL(querey);


    }
    public void deleteOrderedVegetableTable() {

        SQLiteDatabase db = this.getWritableDatabase();

        String querey = "DELETE FROM orderedVegetableTable";

        db.execSQL(querey);


    }
    public void deleteVegetableStockTable() {

        SQLiteDatabase db = this.getWritableDatabase();

        String querey = "DELETE FROM vegetableStockTable";

        db.execSQL(querey);

    }
    public void deleteTradeCustomerDetailsTable() {

        SQLiteDatabase db = this.getWritableDatabase();

        String querey = "DELETE FROM tradeCustomerDetailsTable";

        db.execSQL(querey);

    }
    public void deleteTradeOrederTable() {

        SQLiteDatabase db = this.getWritableDatabase();

        String querey = "DELETE FROM tradeOrederTable";

        db.execSQL(querey);

    }
    public void deleteDeliveryDetailsTable() {

        SQLiteDatabase db = this.getWritableDatabase();
        String querey = "DELETE FROM deliveryCycleTable";

        db.execSQL(querey);

    }
    public void deleteStockVegetableTableTable() {

        SQLiteDatabase db = this.getWritableDatabase();
        String querey = "DELETE FROM stockVegetableTable";

        db.execSQL(querey);

    }
}

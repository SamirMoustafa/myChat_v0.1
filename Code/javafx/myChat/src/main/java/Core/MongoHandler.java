package Core;

import ChatException.AccountNotExistsException;
import ChatException.AlreadyLogInException;
import ChatException.ImageNotExistsException;
import com.mongodb.*;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.mongodb.util.JSON;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samir Moustafa on 5/3/2017.
 */
public class MongoHandler implements CONSTANTS {

    protected static ServerAddress server;
    protected static MongoClient client;
    protected static DB database;
    protected static DBCollection collectionMessages;
    protected static DBCollection collectionAccounts;

    public MongoHandler() throws MongoClientException {
        //server = new ServerAddress(HOST, PORT);
        //MongoClientURI uri = new MongoClientURI(HOST);
        client = new MongoClient(HOST);
        database = client.getDB(DATA_BASE_NAME);
        collectionAccounts = database.getCollection(ACCOUNTS_COLLECTION_NAME);
    }

    public static UserAccount getUserAccountForLogin(String eMail, String password) {
        BasicDBObject andQuery = new BasicDBObject();
        List<BasicDBObject> obj = new ArrayList<>();
        obj.add(new BasicDBObject("email", eMail));
        obj.add(new BasicDBObject("password", password));
        andQuery.put("$and", obj);
        DBCursor cursor = collectionAccounts.find(andQuery);
        DBObject currentAccountData;
        try {
            currentAccountData = cursor.toArray().get(0);
        } catch (IndexOutOfBoundsException ex) {
            throw new AccountNotExistsException();
        }
        if (currentAccountData.get("isActive").toString().compareTo("true") == 0)
            throw new AlreadyLogInException();
        UserAccount loginAccount = new UserAccount(
                currentAccountData.get("name").toString(),
                Double.parseDouble(currentAccountData.get("age").toString()),
                currentAccountData.get("_id").toString(),
                currentAccountData.get("phone").toString(),
                currentAccountData.get("email").toString(),
                currentAccountData.get("password").toString(),
                currentAccountData.get("picture").toString()
        );
        return loginAccount;
    }


    public static byte[] getImageArrayByte(String imageNameByEmail) throws IOException {
        GridFS gridFS = new GridFS(database);
        DBObject imageObject = (DBObject) JSON.parse("{'filename':'" + imageNameByEmail + "'}");
        GridFSDBFile queryFile;
        try {
            queryFile = gridFS.find(imageObject).get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new ImageNotExistsException();
        }
        return getImageArrayByte(queryFile);
    }

    private static byte[] getImageArrayByte(GridFSDBFile queryFile) throws IOException {
        byte[] file = null;
        if (queryFile != null) {
            ByteArrayOutputStream bao = null;
            bao = new ByteArrayOutputStream();
            queryFile.writeTo(bao);
            file = bao.toByteArray();
        }
        return file;
    }

    public static boolean newRegisteredAccount(UserAccount UserAccount) {
        try {
            try {
                collectionAccounts.find(new BasicDBObject("email", UserAccount.getEMail())).toArray().get(0);
            } catch (Exception e) {
                BasicDBObject newDoc = new BasicDBObject().
                        append("isActive", false).
                        append("picture", UserAccount.getEMail()).
                        append("age", UserAccount.getAge()).
                        append("name", UserAccount.getName()).
                        append("email", UserAccount.getEMail()).
                        append("password", UserAccount.getPass()).
                        append("phone", UserAccount.getPhone()).
                        append("friends", new BasicDBObject());
                collectionAccounts.insert(newDoc);
                String newFileName = UserAccount.getEMail();
                File imageFile = new File(UserAccount.getPhoto());
                GridFS gfsPhoto = new GridFS(database, "fs");
                GridFSInputFile gfsFile = gfsPhoto.createFile(imageFile);
                gfsFile.setFilename(newFileName);
                gfsFile.save();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage() + "\n" + e.getCause());
            return false;
        }
        return false;
    }

    public static void setAccountloggedIn(UserAccount targetToUpdate) {
        try {
            BasicDBObject newDoc = new BasicDBObject().
                    append("isActive", true).
                    append("picture", targetToUpdate.getEMail()).
                    append("age", targetToUpdate.getAge()).
                    append("name", targetToUpdate.getName()).
                    append("email", targetToUpdate.getEMail()).
                    append("password", targetToUpdate.getPass()).
                    append("phone", targetToUpdate.getPhone()).
                    append("friends", new BasicDBObject());
            BasicDBObject searchQuery = new BasicDBObject().append("email", targetToUpdate.getEMail());
            collectionAccounts.update(searchQuery, newDoc);
            String newFileName = targetToUpdate.getEMail();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage() + "\n" + e.getCause());
        }
    }

    public static void setAccountloggedOut(UserAccount targetToUpdate) {
        try {
            BasicDBObject newDoc = new BasicDBObject().
                    append("isActive", false).
                    append("picture", targetToUpdate.getEMail()).
                    append("age", targetToUpdate.getAge()).
                    append("name", targetToUpdate.getName()).
                    append("email", targetToUpdate.getEMail()).
                    append("password", targetToUpdate.getPass()).
                    append("phone", targetToUpdate.getPhone()).
                    append("friends", new BasicDBObject());
            BasicDBObject searchQuery = new BasicDBObject().append("email", targetToUpdate.getEMail());
            collectionAccounts.update(searchQuery, newDoc);
            String newFileName = targetToUpdate.getEMail();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage() + "\n" + e.getCause());
        }
    }

    public static boolean updateAccount(UserAccount targetToUpdate) {
        try {
            BasicDBObject newDoc = new BasicDBObject().
                    append("isActive", false).
                    append("picture", targetToUpdate.getEMail()).
                    append("age", targetToUpdate.getAge()).
                    append("name", targetToUpdate.getName()).
                    append("email", targetToUpdate.getEMail()).
                    append("password", targetToUpdate.getPass()).
                    append("phone", targetToUpdate.getPhone()).
                    append("friends", new BasicDBObject());
            BasicDBObject searchQuery = new BasicDBObject().append("email", targetToUpdate.getEMail());
            collectionAccounts.update(searchQuery, newDoc);
            String newFileName = targetToUpdate.getEMail();
            File imageFile = new File(targetToUpdate.getPhoto());
            GridFS gfsPhoto = new GridFS(database, "fs");
            gfsPhoto.remove(targetToUpdate.getEMail());
            GridFSInputFile gfsFile = gfsPhoto.createFile(imageFile);
            gfsFile.setFilename(newFileName);
            gfsFile.save();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage() + "\n" + e.getCause());
            return false;
        }
    }


    public static List<DBObject> getAccountsList(String searchNameTarget) {
        collectionAccounts = database.getCollection(ACCOUNTS_COLLECTION_NAME);
        DBCursor cursor;
        if (searchNameTarget.compareTo("") == 0) {
            cursor = collectionAccounts.find();
            return cursor.toArray();
        }
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("name", searchNameTarget);
        cursor = collectionAccounts.find(whereQuery);
        return cursor.toArray();

    }

    public static List<DBObject> getChatBetweenTwoAccounts(String accountOneEmail, String accountTwoEmail) {
        collectionMessages = database.getCollection(CONSTANTS.CHAT_COLLECTION_NAME);

        DBObject case00 = new BasicDBObject("sender", accountOneEmail);
        DBObject case01 = new BasicDBObject("receiver", accountOneEmail);
        BasicDBList case0 = new BasicDBList();
        case0.add(case00);
        case0.add(case01);
        DBObject firstOr = new BasicDBObject("$or", case0);

        DBObject case10 = new BasicDBObject("sender", accountTwoEmail);
        DBObject case11 = new BasicDBObject("receiver", accountTwoEmail);
        BasicDBList case1 = new BasicDBList();
        case1.add(case10);
        case1.add(case11);
        DBObject secondOr = new BasicDBObject("$or", case1);

        BasicDBList andValues = new BasicDBList();
        andValues.add(firstOr);
        andValues.add(secondOr);
        DBObject query = new BasicDBObject("$and", andValues);

        DBCursor cursor = collectionMessages.find(query);
        return cursor.toArray();
    }

    public static void setChatBetweenTwoAccounts(String sender, String receiver, String senderName, String receiverName, String Message) {
        collectionMessages = database.getCollection(CONSTANTS.CHAT_COLLECTION_NAME);
        BasicDBObject newMessage = new BasicDBObject().
                append("sender", sender).
                append("receiver", receiver).
                append("senderName", senderName).
                append("receiverName", receiverName).
                append("text", Message);
        collectionMessages.insert(newMessage);
    }

    public static void closeConnection() {
        client.close();
    }
}

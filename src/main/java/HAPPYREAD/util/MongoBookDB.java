package HAPPYREAD.util;

import HAPPYREAD.Module.Book;
import HAPPYREAD.Module.Tip;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.Binary;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Feng on 2016/7/17.
 */
public class MongoBookDB {
    private static final MongoClient mongoClient =new MongoClient("boteteam.com",27017);
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> mongoCollection=null;


    public MongoBookDB() {
        mongoDatabase = mongoClient.getDatabase("happyread");
    }

    public List<Book> getBooks(){

        List<Book> books = new ArrayList<>();
        Book book;
        mongoCollection= mongoDatabase.getCollection("books");
        FindIterable<Document> iterator=mongoCollection.find();
        MongoCursor mongoCursor=iterator.iterator();
        Gson gson=new GsonBuilder().create();
        while (mongoCursor.hasNext())
        {
            String json;
            Document doc=(Document) mongoCursor.next();
            json=doc.toJson();
            book=gson.fromJson(json,Book.class);
            books.add(book);
        }
        //   Log.d("myapp",books.toString());
        return books;


    }

    public boolean addBook(Book book){
        Gson gson=new GsonBuilder().create();
        String json=gson.toJson(book);
        Document doc=Document.parse(json);
        mongoCollection= mongoDatabase.getCollection("books");
        mongoCollection.insertOne(doc);
        return true;
    }

    public boolean addTip(Tip tip){
        mongoCollection= mongoDatabase.getCollection("tips");
        Gson gson=new GsonBuilder().create();
        String json=gson.toJson(tip);
        Document doc=Document.parse(json);
        mongoCollection.insertOne(doc);
        return  true;
    }

    public boolean addBookforDownLoad(String title,String txtFileName) {
        mongoCollection= mongoDatabase.getCollection("bookdown");

        File file = new File(txtFileName);

        if (file.isFile() && file.exists()) {

            try {

                FileChannel fc=new RandomAccessFile(file,"r").getChannel();
                MappedByteBuffer byteBuffer=fc.map(FileChannel.MapMode.READ_ONLY,0,fc.size()).load();
                byte[] result=new byte[(int) fc.size()];
                 if(byteBuffer.remaining()>0)
                 {
                     byteBuffer.get(result,0,byteBuffer.remaining());
                 }
                Document doc=new Document();
                doc.put("title",title);
                doc.put("txtFile",result);
                mongoCollection.insertOne(doc);
                System.out.println(result.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;

        } else {
            return false;

        }
    }

    public byte[] GetBookTxt(String title){
        mongoCollection= mongoDatabase.getCollection("bookdown");
        Document filter = new Document();
        filter.put("title",title);
        Document document=mongoCollection.find(filter).first();

        Binary bs=(Binary) document.get("txtFile");

        return bs.getData();
    }

    public static File putFileFromBytes(byte[] b, String outputFile) {
        File ret = null;
        BufferedOutputStream stream = null;
        try {
            ret = new File(outputFile);
            FileOutputStream fstream = new FileOutputStream(ret);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            // log.error("helper:get file from byte process error!");
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    // log.error("helper:get file from byte process error!");
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }

    public static File putFile(byte[] bfile, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {// �ж��ļ�Ŀ¼�Ƿ����
                dir.mkdirs();
            }
            file = new File(filePath + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }

}

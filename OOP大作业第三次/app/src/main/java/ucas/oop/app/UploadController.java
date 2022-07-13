package ucas.oop.app;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadController {
    private static String ENDPOINT="oss-cn-beijing.aliyuncs.com";
    private static String ACCESSKEYID="LTAI5tP4Qr3uAV8QWiPzvqhd";
    private static String ACCESSKEYSECRET="BJdFyJJQahgWNaJ4ppzhC6TUbLRWxZ";
    private static String BUCKETNAME="object-oriented-program-ucas";
    private static String SUFFER_URL="http://"+BUCKETNAME+"."+"oss-cn-beijing.aliyuncs.com/";
    private static SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
    private Stage dialogStage;

    @FXML
    private TextField DownloadAddressField;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void setUrl(String url){
        DownloadAddressField.setText(url);
    }
    public static String readFile(File file) {
        StringBuilder resultStr = new StringBuilder();
        try {
            BufferedReader bReader = new BufferedReader(new FileReader(file));
            String line = bReader.readLine();
            while (line != null) {
                resultStr.append(line + "\n");
                line = bReader.readLine();
            }
            bReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultStr.toString();
    }
    public OSSClient getOssClient(){
        OSSClient ossClient=new OSSClient(ENDPOINT, ACCESSKEYID,ACCESSKEYSECRET);

        if(ossClient.doesBucketExist(BUCKETNAME)){
            System.out.println("bucket exists");
        }else{

            System.out.println("bucket doesn't exist, create bucket:" + BUCKETNAME);
            CreateBucketRequest bucketRequest=new CreateBucketRequest(null);
            bucketRequest.setBucketName(BUCKETNAME);
            bucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
            ossClient.createBucket(bucketRequest);
            //ossClient.shutdown();
        }

        return ossClient;
    }
    public String uploadDocument(NoteBook noteBook){
        File target = noteBook.getFile();
        String content = FileUtil.readFile(target);
        String noteBookName = noteBook.getName().getValue();
        OSSClient ossClient=this.getOssClient();
        String date=sdf.format(new Date());

        String url=null;
        String filename = date + "/" + noteBookName;
        try {
            ossClient.putObject(BUCKETNAME, filename, new ByteArrayInputStream(content.getBytes()));
            url=SUFFER_URL+filename;
            System.out.println("----->url: "+url);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            ossClient.shutdown();
        }
        return url;
    }

}

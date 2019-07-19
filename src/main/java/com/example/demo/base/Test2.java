package com.example.demo.base;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @Filename: Test2
 * @Description:
 * @Version: 1.0
 * @Author: wangchao
 * @Email: wangchao@hellogeek.com
 * @date: 2019/7/11 ;
 */
public class Test2 {

    private static List<Integer> a=new ArrayList<>(3);


    //阿里云API的内或外网域名
    private static String ENDPOINT ="oss-cn-shanghai.aliyuncs.com";
    //阿里云API的密钥Access Key ID
    private static String ACCESS_KEY_ID ="LTAINETaZIjt3oRX";
    //阿里云API的密钥Access Key Secret
    private static String ACCESS_KEY_SECRET ="N8ZeIB19lraZ2ctngkfBeNW8iXU9H6";
    //阿里云API的bucket名称
    private static String BACKET_NAME="lwsp-test";
    //阿里云API的文件夹名称
    private static String FOLDER ="/";

    //阿里云API的文件夹名称
    private static String httpTitle ="http://";

    public static List<Integer> getA() {
        a.remove(0);
        return  a;
    }

    public static void setA(Integer b) {
        a.add(b);
    }


    public static OSSClient getOSSClient(){
        return new OSSClient(ENDPOINT,ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }

    public static void deleteFile(OSSClient ossClient, String bucketName, String folder, String key){
        ossClient.deleteObject(bucketName,  key);
//        logger.info("删除" + bucketName + "下的文件" + folder + key + "成功");
    }


    public static void main(String[] args) {
        OSSClient ossClient = getOSSClient();

//

// 判断文件是否存在。doesObjectExist还有一个参数isOnlyInOSS，如果为true则忽略302重定向或镜像；如果
        //为false，则考虑302重定向或镜像。
//        boolean found = ossClient.doesObjectExist(BACKET_NAME, "repairData/video/13003.mp4");
//        System.out.println(found);
        List<String> keys = new ArrayList<String>();
        keys.add("repairData/avatar/13091.jpg");
        keys.add("repairData/video/13091.mp4");
        keys.add("repairData/cover/13091.jpg");
        try {
//            deleteFile(ossClient,BACKET_NAME,FOLDER,"repairData/video/13003.mp4");
            DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(BACKET_NAME).withKeys(keys));
            List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
            System.out.println(deletedObjects.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

//        boolean found2 = ossClient.doesObjectExist(BACKET_NAME, "repairData/video/13003.mp4");
//        System.out.println(found2);



        // 删除文件。




// 关闭OSSClient。
        ossClient.shutdown();
    }
}


import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

/**
 * This class is used for generating logs.
 *
 * Created by liuqianyu on 17/3/3.
 */
public class LogSimulator {
        public static void main(String[] args) {
//        新增用户   用户信息存在hbase中
//        日活跃用户  DAU   对于当天的日志根据用户唯一标识对日志进行去重  mr+hive
//        月活跃用户  MAU   一个月内 所有日活用户加起来 再根据唯一标识进行去重 mr+hive
//        周活跃用户  WAU   一周内 所有日活用户
//        次日留存率  下载app后第二天还在用app的人数/下载当天总下载人数  次日活跃用户数中的那天注册的/那天的总下载量
//        周留存率    下载后一周还在用app的人数/下载当天总下载人数
//        病毒系数    平均每位老用户会带来几位新用户  //registration table
//        行为留存率  使用了一个功能的用户 一段时间内还使用该功能的用户
//        用户分布    用户注册地点分布
//        渠道分析    直接从数据库查   //hbase download table
//        用户年龄分布      // hbase registration table
//        用户性别分布      //hbase  registration table
//        用户系统分布      //hbase  registration table
//        活跃时间分布      //统计四天的日志
//        日活用户分布


        File file = new File("/Users/liuqianyu/Desktop/log1.txt");
        FileOutputStream fos;
        LocationSimulator ls;
        try {
            fos = new FileOutputStream(file,true);
            ls = new LocationSimulator();
        String[] systems = {"android","ios"};
        String[] app_versions = {"1.0","2.0","3.0","4.0"};
        String[] sdk_versions = {"1.0","2.0","3.0"};
        String[] distribution_channels = {"app_store","baidu","360","wandoujia","tencent"};
        String[] recommands = {"0","1"};
        String[] events = {"share with wechat","share with weibo","check in"};

        String nginx_time;
        String province;
        String city;
        String user_id;
        String system;
        String app_version;
        String sdk_version;
        String distribution_channel;
        String recommand;
        String event;
        String date;
        String time;


        //1-10 0000 beijing
        //10 0001 - 20 0000 shanghai
        //20 0001 - 30 0000 guangzhou
        //30 0001 - 50 0000 quanguo
        //1000000/4= 25 0000
        int[] generateTimes = {1,2,3,4};
        for (int i = 1 ; i <= 400000; i++) {
        String[] city_and_province = ls.getLocation();

                //  nginx_time = String.valueOf(System.currentTimeMillis());


            // user_id = String.valueOf(i);
            province = city_and_province[0];
            city = city_and_province[1];
            system = systems[new Random().nextInt(systems.length)];
            app_version = app_versions[new Random().nextInt(systems.length)];
            sdk_version = sdk_versions[new Random().nextInt(sdk_versions.length)];
            distribution_channel = distribution_channels[new Random().nextInt(distribution_channels.length)];
            recommand = recommands[new Random().nextInt(2)];
            date = "2017-03-01";

         //    System.out.println(log);

            for (int j = 0; j < generateTimes[i%4]; j++) {

               //user_id = String.valueOf(new Random().nextInt(400000)+1);
                user_id = String.valueOf(i);
                time = timeGenerator();
                event = events[new Random().nextInt(events.length)];

        String log =
                "{" +
                "\"header\":" +
                    "{" +
                    "\"nginx_time\":" +"\""+time+"\""+
                    ",\"city\":" +"\""+city+"\""+
                    ",\"province\":"+"\""+province+"\""+
                    ",\"user_id\":"+"\"" +user_id+"\""+
                    ",\"system\":"+"\""+system+"\""+
                    ",\"app_version\":"+"\""+app_version+"\""+
                    ",\"sdk_version\":"+"\""+sdk_version+"\""+
                    ",\"distribution_channel\":"+"\""+distribution_channel+"\""+
                    ",\"recommand\":"+"\""+recommand+"\""+
                    ",\"date\":"+"\""+date+"\""+
                        ",\"time\":"+"\""+time+"\""+
                    "}"+
                        ",\"events\":"+
                      //  "{"+
                        "\""+event+"\""+
                      //  "}"+
                        "}";

            System.out.println(i+"--> finished");
            fos.write(log.getBytes());
            fos.write("\n".getBytes());
            }


        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static String timeGenerator(){
        String result ;

        int hour = new Random().nextInt(24);
        int minute = new Random().nextInt(60);
        int second = new Random().nextInt(60);

        String hourStr = String.valueOf(hour);
        String minStr = String.valueOf(minute);
        String secStr = String.valueOf(second);

        if (hour<10){
            hourStr = "0"+hour;
        }
        if (minute<10){
            minStr = "0"+minStr;
        }
        if(second<10){
            secStr = "0"+secStr;
        }

        result = "18"+":"+minStr+":"+secStr;
        return  result;
    }

}

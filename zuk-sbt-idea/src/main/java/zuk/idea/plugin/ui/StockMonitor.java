package zuk.idea.plugin.ui;

import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalTime;
import java.util.List;
import java.util.Timer;
import java.util.*;

public class StockMonitor {
    private JFrame frame;
//    private JButton startButton;
    private JLabel timeLabel;
    private JLabel priceLabel;
    private JTextField comparePriceField;
    private Timer timer;
    private Random random;

    private static String URL;
    private static Float PRICE = 0.0f;
    private static Boolean VISIBLE = false;

    public static void main(String[] args) {
        System.out.println("-help");
        if(args!=null && args.length>0){
            try {
                Arrays.stream(args).map(arg->arg+" ").forEach(System.out::println);
                for (int i = 0; i < args.length; i++) {
                    String arg = args[i].toLowerCase();
                    if(arg.equals("-url")){
                        URL = args[i+1];
                        List<String> list = FileUtils.readLines(new File(URL), "utf-8");
                        URL = list.get(0);
                    }
                    if(arg.equals("-price")){
                        PRICE = Float.parseFloat(args[i+1]);
                    }
                    if(arg.equals("-visible")){
                        VISIBLE = Boolean.parseBoolean(args[i+1]);
                    }
                    if (arg.equals("-help")){
                        System.out.println(
                                "-url\n //http://xxx.com" +
                                "-price\n  //0.0" +
                                "-visible //false");
                    }

                }
            }catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        if (VISIBLE) {
            SwingUtilities.invokeLater(StockMonitor::new);
        }
        else {
            printData();
        }

    }

    public static void printData() {
        try {
            while (true) {
                LocalTime now = LocalTime.now();
                int hour = now.getHour();
                if(hour>=15){
                    System.exit(1);
                }
                ResponseDto responseDto = getHttp(URL);
                while (responseDto!=null && responseDto.getData()==null) {
                    responseDto = getHttp(URL);
                }
                DataDto dataDto = responseDto.getData().get(responseDto.getData().size()-1);
                System.out.println(dataDto.getTime() + "  " + dataDto.getPrice());
                if (Float.parseFloat(dataDto.getPrice())>=PRICE) {
                    Frame frame=new Frame();
                    frame.setAlwaysOnTop(true);
                    JOptionPane.showMessageDialog(frame,"OK","提示",2);
                }
                Thread.sleep(5000);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public StockMonitor() {
        frame = new JFrame("Monitor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setLayout(new GridLayout(5, 2));

        //stockCodeField = new JTextField();
//        startButton = new JButton("Start");
        timeLabel = new JLabel("Time: ");
        priceLabel = new JLabel("Price: ");
        comparePriceField = new JTextField();
        comparePriceField.setText("0");
        random = new Random();

        frame.add(timeLabel);
        frame.add(priceLabel);
        frame.add(comparePriceField);
//        frame.add(startButton);

//        startButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                startMonitoring();
//            }
//        });

        frame.setVisible(true);
//        startMonitoring();

    }

//    private void startMonitoring() {
//        if (timer != null) {
//            timer.cancel();
//        }
//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                updateStockPrice();
//            }
//        }, 0, 10000);
//    }

//    private void updateStockPrice() {
//        SwingUtilities.invokeLater(() -> {
//
//            try {
//                LocalTime now = LocalTime.now();
//                int hour = now.getHour();   // 获取小时（24小时制）
//                int minute = now.getMinute(); // 获取分钟
//                System.out.println(hour + ":" + minute);
//                if((hour>9 && hour<12) || (hour>13 && minute<15)){
//
//                    ResponseDto responseDto = getHttp();
//                    while (responseDto!=null) {
//                        responseDto = getHttp();
//                    }
//                    String time = null;
//                    Float price = null;
//                    if(responseDto.data!=null && responseDto.getData().size()>0){
//                        DataDto dataDto = responseDto.getData().get(responseDto.getData().size()-1);
//                        time = dataDto.getTime();
//                        price = Float.parseFloat(dataDto.getPrice());
//                    }
//
//                    if(time==null || price==null){
//                        timeLabel.setText("Time: " + "null");
//                        priceLabel.setText("Price: " + "null");
//                    }
//                    else {
//                        timeLabel.setText("Time: " + time);
//                        priceLabel.setText("Price: " + price);
//
//                        float comparePrice = Float.parseFloat(comparePriceField.getText());
//                        if(price >= comparePrice){
//                            JOptionPane.showMessageDialog(frame, " OK ", "SUCCESS", JOptionPane.WARNING_MESSAGE);
//                        }
//                    }
//
//                }
//
//
//            }catch (Exception exception) {
//                exception.printStackTrace();
//            }
//
//        });
//    }

    public static ResponseDto getHttp(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0"); // 设置请求头（可选）

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 200 表示成功
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                reader.close();
                //System.out.println("Response: " + stringBuilder);
                ResponseDto responseDto = JSONObject.parseObject(stringBuilder.toString(), ResponseDto.class);
                //System.out.println("Response.json: " + JSONObject.toJSONString(responseDto, JSONWriter.Feature.LargeObject));
                return responseDto;
            } else {
                //System.out.println("GET 请求失败，响应码：" + responseCode);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static class DataDto{
        private String time;
        private String price;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
    public static class ResponseDto {

        private Integer code;
        private String msg;
        private List<DataDto> data;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public List<DataDto> getData() {
            return data;
        }

        public void setData(List<DataDto> data) {
            this.data = data;
        }
    }
}

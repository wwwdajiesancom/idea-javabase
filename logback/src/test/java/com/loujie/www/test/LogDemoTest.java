package com.loujie.www.test;

import ch.qos.logback.core.util.FileUtil;
import com.loujie.www.utils.ArgsUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @name loujie
 * @date 2018/11/21
 */
public class LogDemoTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Test
    public void soutTest() throws InterruptedException {
        int i = 0;
        while (i++ != 3) {
            logger.debug(i + "-----------soutTest----------");
            logger.info(i + "-----------soutTest----------");
            logger.warn(i + "-----------soutTest----------");
            logger.error(i + "-----------soutTest----------");
            this.abc(i);
        }
    }

    public void abc(int i) {
        logger.debug(i + "------abc------debug");
        logger.info(i + "------abc------info");
        logger.warn(i + "------abc------warn");
        logger.error(i + "------abc------error");
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void createFile() throws IOException {
        Date endDate = ArgsUtils.parseDate("2018-12-10", "yyyy-MM-dd");
        int i = 30;
        do {
            File file = new File("D://logs//logback//template." + ArgsUtils.formatDate(endDate, "yyyy-MM-dd") + ".log");
            if (!file.exists()) file.createNewFile();
            endDate = ArgsUtils.getDate(endDate, -1);
            i--;
        } while (i != 0);

        System.out.println("ok");
    }

    @Test
    public void setDate() {
        setDatetime("2018-12-28", null);
    }

    public static String setDatetime(String date, String time) {
        String osName = System.getProperty("os.name");
        String dateTimeMessage = date + " " + time;
        try {
            if (osName.matches("^(?i)Windows.*$")) { // Window 系统
                String cmd;

                cmd = " cmd /c date " + date; // 格式：yyyy-MM-dd
                Process p = Runtime.getRuntime().exec(cmd);
                InputStream is = p.getErrorStream();
                if (is != null && is.available() != -1 && is.available() != 0) {
                    System.out.println("--is--" + is.available());
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    System.out.println(new String(b));
                }
                InputStream is2 = p.getInputStream();
                if (is2 != null && is2.available() != -1 && is2.available() != 0) {
                    System.out.println("--is2--" + is2.available());
                    byte[] b = new byte[is2.available()];
                    is.read(b);
                    System.out.println(new String(b));
                }
                //cmd = " cmd /c time " + time; // 格式 HH:mm:ss
                // Runtime.getRuntime().exec(cmd);
            } else if (osName.matches("^(?i)Linux.*$")) {// Linux 系统
                String command = "date -s " + "\"" + date + " " + time + "\"";// 格式：yyyy-MM-dd HH:mm:ss
                Runtime.getRuntime().exec(command);
            } else {

            }
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return dateTimeMessage;
    }

}

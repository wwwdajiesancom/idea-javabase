import www.loujie.com.excel.ExcelUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @name loujie
 * @date 2019/2/20
 */
public class ExcelTest {
    public static void main(String[] args) throws FileNotFoundException {
        InputStream is = new FileInputStream(new File("C:\\Users\\loujie\\Desktop\\需要的模版\\abc.et"));
        ExcelUtils.readExcelToList(is, null, 10000);

    }
}

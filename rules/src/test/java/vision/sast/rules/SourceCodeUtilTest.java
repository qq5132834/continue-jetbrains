package vision.sast.rules;

import vision.sast.rules.dto.IssueDto;
import vision.sast.rules.utils.SourceCodeUtil;

import java.util.ArrayList;

/***
 * 解析 ANSI 编码格式、UTF-8 编码格式的 文件
 */
public class SourceCodeUtilTest {
    public static void main(String[] args) throws Exception {
        RulesApplication.loadProperties();
        String str = SourceCodeUtil.show("src/test/resources/ANSI_test.c", new ArrayList<IssueDto>());
        System.out.println(str);
    }
}

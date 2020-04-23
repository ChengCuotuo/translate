package baidu.translate;

// 这个 jar 包，依赖与 org.apache.commons.lang3.jar 这个 jar 包
import org.apache.commons.text.StringEscapeUtils;

import java.util.HashMap;
import java.util.Map;

public class TransApi {
    private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";

    private String appid;
    private String securityKey;

    public TransApi(String appid, String securityKey) {
        this.appid = appid;
        this.securityKey = securityKey;
    }

    public String getTransResult(String query, String from, String to) {
        Map<String, String> params = buildParams(query, from, to);
        String result =  HttpGet.get(TRANS_API_HOST, params);
        int startIndex = result.lastIndexOf("\"dst\"") + 7;
        int endIndex = result.lastIndexOf("}]") - 1;
        result = result.substring(startIndex, endIndex);
        // 调用第三方 jar 包，处理转义字符
        // 也就是 \\u4e00 种的转义字符，无法使用 replace 进行去除
        // 使用这个方法，就可以忽略转义字符
        // 将 Unicode 编码的字符直接输出 \u4e00 ，也就是输出了汉字
        result= StringEscapeUtils.unescapeJava(result);

        return result;
    }

    private Map<String, String> buildParams(String query, String from, String to) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);

        params.put("appid", appid);

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);

        // 签名
        String src = appid + query + salt + securityKey; // 加密前的原文
        params.put("sign", MD5.md5(src));

        return params;
    }

}

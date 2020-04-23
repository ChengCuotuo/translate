package baidu.translate;

import java.util.LinkedHashMap;

/**
 * Created by lei02 on 2019/4/11.
 */
public class BaiduTranslate {
    // 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
    private static final String APP_ID = "20190410000286537";
    private static final String SECURITY_KEY = "zyHXb8RJR_dp8Cnh6hFz";

    public LinkedHashMap<String, String> getLanguages(){
        LinkedHashMap<String, String> languages = new LinkedHashMap<>();
        languages.put("自动检测", "auto");
        languages.put("中文", "zh");
        languages.put("英语", "en");
        languages.put("粤语", "yue");
        languages.put("文言文", "wyw");
        languages.put("日语", "jp");
        languages.put("韩语", "kor");
        languages.put("法语", "fra");
        languages.put("西班牙语", "spa");
        languages.put("泰语", "th");
        languages.put("阿拉伯语", "ara");
        return languages;
    }

    public String translate(String text, String from, String to) {
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);
        return api.getTransResult(text, from, to);
    }

    public static void main(String[] args) {
        BaiduTranslate t = new BaiduTranslate();
        System.out.println(t.translate("hello, world!", "en", "zh"));
    }
}

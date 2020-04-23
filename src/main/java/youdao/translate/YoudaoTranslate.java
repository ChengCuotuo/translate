package youdao.translate;

/**
 * Created by lei02 on 2019/4/11.
 */

import java.util.LinkedHashMap;

public class YoudaoTranslate {
    private static String APP_KEY = "7c74fcd10a1ad581";
    private static String APP_SECRET = "NtjX0rhz2cqrJHSb8KCGnGTcBrFtulZc";

    public LinkedHashMap<String, String> getLanguages(){
        LinkedHashMap<String, String> languages = new LinkedHashMap<>();
        languages.put("自动识别", "auto");
        languages.put("中文", "zh-CHS");
        languages.put("英文", "en");
        languages.put("日文", "ja");
        languages.put("韩文", "ko");
        languages.put("法文", "fr");
        languages.put("西班牙文", "es");
        languages.put("俄文", "ru");
        languages.put("越南文", "vi");
        languages.put("德语", "de");
        languages.put("阿拉伯问", "ar");
        languages.put("印尼文", "id");

        return languages;
    }

    public String translate(String from, String to, String text) {
        GetInfo get = new GetInfo(APP_KEY, APP_SECRET);
        return get.getTransResult(from, to, text);
    }

    public static void main(String[] args) {
        YoudaoTranslate t = new YoudaoTranslate();
        System.out.println(t.translate("zh-CHS", "en", "你好，世界！"));
    }
}

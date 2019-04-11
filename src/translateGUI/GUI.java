package translateGUI;

/**
 * Created by lei02 on 2019/4/10.
 */
import baidu.translate.BaiduTranslate;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import youdao.translate.YoudaoTranslate;

import java.util.LinkedHashMap;

public class GUI extends Application{
    private YoudaoTranslate youdao= new YoudaoTranslate();
    private BaiduTranslate baidu = new BaiduTranslate();

    private HBox hb_control = new HBox(20);                         //控制按钮存放的面板
    private VBox vb_body = new VBox(10);                            //总面板
    private Button bt_translate = new Button("翻译");               //提交按钮
    private ComboBox<String> cb_interface = new ComboBox<>();       //选择
    private ComboBox<String> cb_language_in = new ComboBox<>();     //选择输入的语言
    private ComboBox<String> cb_language_out = new ComboBox<>();    //选择输出的语言
    private TextArea ta_input = new TextArea();                     //用户输入
    private TextArea ta_output = new TextArea();                    //翻译结果

    private LinkedHashMap<String, String> languages; //存储输入法
    private boolean translating = false;

    @Override
    public void start(Stage primaryStage){
        vb_body.setPadding(new Insets(10, 10, 10, 10));
        vb_body.setStyle("-fx-background-color: aqua");
        ScrollPane sp_ta_in = new ScrollPane(ta_input);
        sp_ta_in.setStyle("-fx-background-color: brown");
        ScrollPane sp_ta_out = new ScrollPane(ta_output);
        sp_ta_out.setStyle("-fx-background-color: black");

        String[] inter = {"百度", "有道"};
        cb_interface.setItems(FXCollections.observableArrayList(inter));
        cb_interface.setValue("百度");

        languages= baidu.getLanguages();
        chooseLanguage(languages);

        ta_input.setPrefColumnCount(55);
        ta_input.setPrefRowCount(10);
        ta_input.setFont(new Font("米开花季情书", 16));
        ta_input.setStyle("-fx-text-fill: brown");
        ta_output.setPrefColumnCount(55);
        ta_output.setPrefRowCount(10);
        ta_output.setFont(new Font("米开花季情书", 16));
        ta_output.setStyle("-fx-text-fill: black");
        ta_output.setEditable(false);

        HBox hb_in = new HBox(5);
        hb_in.getChildren().addAll(new Label("输入语言:"), cb_language_in);
        HBox hb_out = new HBox(5);
        hb_out.getChildren().addAll(new Label("输出语言:"), cb_language_out);

        hb_control.setPadding(new Insets(10, 0, 0, 20));
        hb_control.getChildren().addAll(cb_interface,hb_in, hb_out, bt_translate);
        vb_body.getChildren().addAll(hb_control, sp_ta_in, sp_ta_out);

        chooseInterfance();
        translate();

        Scene scene = new Scene(vb_body);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("翻译软件");
        primaryStage.show();
    }

    public void chooseInterfance(){
        cb_interface.setOnAction(e -> {
            String inter = cb_interface.getValue();
            if (inter == "百度") {
                languages= baidu.getLanguages();
                chooseLanguage(languages);
            } else if (inter == "有道") {
                languages= youdao.getLanguages();
                chooseLanguage(languages);
            }

        });
    }

    public void translate() {
        ta_output.setText("注意选择翻译的语言");
        bt_translate.setOnAction(e-> {
            if (!translating) {
                String text = ta_input.getText().trim();
                translating = true;
                if (!text.equals("") && text != null) {
                    String in = languages.get(cb_language_in.getValue());
                    String out = languages.get(cb_language_out.getValue());
                    String inter = cb_interface.getValue();
                    if (inter == "百度") {
                        languages= baidu.getLanguages();
                        chooseLanguage(languages);
                        ta_output.setText(baidu.translate(text, in, out));
                    } else if (inter == "有道") {
                        languages= youdao.getLanguages();
                        chooseLanguage(languages);
                        ta_output.setText(youdao.translate(in, out, text));
                    }
                } else {
                    ta_output.setText("请输入文本");
                }
                translating = false;
            }
        });
    }

    public void chooseLanguage(LinkedHashMap languages) {
        cb_language_out.setItems(null);
        cb_language_out.setItems(null);
        cb_language_in.setItems(FXCollections.observableArrayList(languages.keySet()));
        cb_language_in.setValue("自动检测");
        cb_language_out.setItems(FXCollections.observableArrayList(languages.keySet()));
        cb_language_out.setValue("英语");
    }

    public static void main(String[] args){
        Application.launch();
    }
}

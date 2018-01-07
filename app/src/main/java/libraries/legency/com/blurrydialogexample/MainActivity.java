package libraries.legency.com.blurrydialogexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import libraries.legency.com.mkblurrydialog.BlurryDialog;

public class MainActivity extends AppCompatActivity {

    Button clickMeBtn;
    RelativeLayout rootView;
    BlurryDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootView = findViewById(R.id.rootView);
        clickMeBtn = findViewById(R.id.clicMeBtn);
        clickMeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBlurryDialog();
            }
        });
    }

    private void showBlurryDialog() {
//        if(dialog == null){
//            initBlurry();
//        }
//
//        if(dialog.isShowing()) {
//            dialog.dismiss();
//        }else{
//            dialog.show();
//        }

        initBlurry();
    }


    private void initBlurry(){

        dialog = new BlurryDialog(this);
        dialog.setCancelableOutSide(true)
                .setLoop(2)
                .setProportion(3)
                .setAnimated(true)
                .setRadius(15f)
                .setBodyView(R.layout.other_body);


        TextView title = (TextView) dialog.getBodyView().findViewById(R.id.custom_layout_title);
        TextView message = (TextView) dialog.getBodyView().findViewById(R.id.custom_layout_message);

        title.setText("My custom layout Title");
        message.setText("My custom layout Message");

        Button button = dialog.getBodyView().findViewById(R.id.closeBtn2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your custom button click

                //example
                dialog.dismiss();
            }
        });

        dialog.getBackgroundImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // what happens when you click out side the body layout

            }
        });

        dialog.show();

    }

    @Override
    public void onBackPressed() {
        if (BlurryDialog.checkIsOpen(this)){
            BlurryDialog.removeAllOpenedDialogs(this, true);
        }else {
            super.onBackPressed();
        }
    }
}

package libraries.legency.com.blurrydialogexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

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
        if(dialog == null){
            initBlurry();
        }

        if(dialog.isShowing()) {
            dialog.dismiss();
        }else{
            dialog.show();
        }
    }


    private void initBlurry(){
        if(dialog != null){
            return;
        }

        dialog = new BlurryDialog(this);
        dialog.setCancelableOutSide(true)
                .setLoop(5)
                .setProportion(4)
                .setAnimated(true)
                .setRadius(20f)
                .setTitle("your title")
                .setMessage("Hello World");

//        View view = dialog.getBodyView();
//
//        Button button = view.findViewById(R.id.closeBtn2);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

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

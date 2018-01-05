package libraries.legency.com.mkblurrydialog;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.Visibility;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

/**
 * Created by Madrit on 12/7/2017.
 */

public class BlurryDialog {

    public static final String DIALOG_KEY_STRING_BODY_FULL = "MKBlurryDialog_Legency_Android_Library_full_body";
    public static final int DIALOG_KEY_MAP_KEY_BODY_FULL = 1357986420;
    public static final int DIALOG_KEY_MAP_FULL_OBJECT = 147258963;
    private Activity mActivity;

    private int mLoop = 3;
    private float mRadius = 10f;
    private int mProportion = 3;
    private boolean showing = false;

    private String mTitle = "";
    private String mMessage = "";
    private String mButtonTitle = "OK";
    private boolean mAnimated = true;
    private boolean mCancelableOutSide = true;
    //Views
    private View fullBodyView;
    private View bodyContent;
    private ImageView backgroundImage;
    private RelativeLayout bodyContainer;
    private Bitmap originalScreenShoot;
    private LayoutInflater mInflator;

    public BlurryDialog(Activity activity){
        this.mActivity = activity;
        mInflator = LayoutInflater.from(mActivity);
        fullBodyView = inflateFullBody();
        fullBodyView.setAlpha(1);
    }

    private View inflateFullBody(){
        View fullView = mInflator.inflate(R.layout.dialog_body, null);
        fullView.setTag(DIALOG_KEY_MAP_KEY_BODY_FULL, DIALOG_KEY_STRING_BODY_FULL);
        fullView.setTag(DIALOG_KEY_MAP_FULL_OBJECT, this);
        backgroundImage = fullView.findViewById(R.id.backgroundImageBlurred);
        bodyContainer = fullView.findViewById(R.id.mainContainer);

        backgroundImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCancelableOutSide()) {
                    dismiss();
                }
            }
        });

        return fullView;
    }

    private void setBody(){

        if(fullBodyView == null){
            fullBodyView = inflateFullBody();
        }

        if (bodyContent == null) {
            // adding default body content
            bodyContent = mInflator.inflate(R.layout.default_body, null);
            TextView title =  bodyContent.findViewById(R.id.title);
            TextView message = bodyContent.findViewById(R.id.messageBody);
            Button closeBtn = bodyContent.findViewById(R.id.closeBtn);

            title.setText(mTitle);
            message.setText(mMessage);
            closeBtn.setText(mButtonTitle);
            closeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

        }
        RelativeLayout.LayoutParams mParams = new RelativeLayout.
                LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bodyContainer.removeAllViews();
        bodyContainer.addView(bodyContent, mParams);

    }

    public BlurryDialog setBodyView(int id){
        if(bodyContent != null){
            return this;
        }

        this.bodyContent = null;
        this.bodyContent = mInflator.inflate(id, null);
        setBody();
        return this;
    }

    public BlurryDialog setBodyView(View view){

        if(view == null){
            return this;
        }

        this.bodyContent = null;
        this.bodyContent = view;
        setBody();

        return this;
    }

    public BlurryDialog setTitle(String title) {
        this.mTitle = title;
        return this;
    }

    public BlurryDialog setMessage(String message) {
        this.mMessage = message;
        return this;
    }

    public BlurryDialog setBtnTitle(String buttonTitle){
        this.mButtonTitle = buttonTitle;
        return this;
    }


    public BlurryDialog setLoop(int loop) {
        this.mLoop = loop;
        return this;
    }

    public BlurryDialog setRadius(float radius) {
        if (radius == 0){
            this.mRadius = 3f;
        }else if (radius > 25f){
            this.mRadius = 25f;
        }else {
            this.mRadius = radius;
        }
        return this;
    }

    public BlurryDialog setProportion(int proportion) {
        if (proportion < 1){
            this.mProportion = 1;
        }else {
            this.mProportion = proportion;
        }
        return this;
    }

    public BlurryDialog setAnimated(boolean animated) {
        this.mAnimated = animated;
        return this;
    }


    public BlurryDialog setCancelableOutSide(boolean cancelableOutSide) {
        this.mCancelableOutSide = cancelableOutSide;
        return this;
    }

    public void show(){
        final View  rootView = mActivity.getWindow().getDecorView().getRootView();
        rootView.post( new Runnable() {
            @Override
            public void run() {
                showWhenDrawn(rootView);
                showing = true;
            }
        });

    }

    private void showWhenDrawn(View rootView){

        if(fullBodyView == null){
            fullBodyView = inflateFullBody();
        }

        fullBodyView.setVisibility(View.VISIBLE);
        fullBodyView.animate().alpha(1).setDuration(300).setListener(null);
        backgroundImage.setAlpha(0f);

        if (bodyContent == null){
            setBody();
        }

        Bitmap originalScreenShoot = Utils.screenShot(rootView);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int height = displayMetrics.heightPixels / mProportion;
        int width = displayMetrics.widthPixels / mProportion;
        originalScreenShoot = Utils.getResizedBitmap(originalScreenShoot, height, width);

        //adding blur to the frames
        RenderScript rs = RenderScript.create(mActivity);

        for (int i = 0; i < mLoop;  i++) {
            final Allocation input = Allocation.createFromBitmap(rs, originalScreenShoot); //use this constructor for best performance, because it uses USAGE_SHARED mode which reuses memory
            final Allocation output = Allocation.createTyped(rs, input.getType());
            final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            script.setRadius(mRadius);
            script.setInput(input);
            script.forEach(output);
            output.copyTo(originalScreenShoot);
        }
        backgroundImage.setImageBitmap(originalScreenShoot);
        //showing finally the dialog
        ViewGroup.LayoutParams mParams = new ViewGroup.
                LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if(fullBodyView.getParent() == null) {
            ((ViewGroup) rootView).addView(fullBodyView, mParams);
        }

        if(mAnimated) {
            backgroundImage.animate().alpha(1).setDuration(300);
        }else{
            backgroundImage.setAlpha(1f);
        }

        boolean fullBodyVisibility = fullBodyView.getVisibility() == View.VISIBLE;
        String debug = "";
    }

    public void dismiss(){
        if (fullBodyView == null){
            return;
        }

        if (mAnimated) {
            fullBodyView.animate().setDuration(300).alpha(0).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (fullBodyView.getParent() == null) {
                        return;
                    }

                    ((ViewManager) fullBodyView.getParent()).removeView(fullBodyView);
                    destroy();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

        }else{
            if (fullBodyView.getParent() == null) {
                return;
            }
            ((ViewManager) fullBodyView.getParent()).removeView(fullBodyView);
            destroy();
        }
    }

    private void destroy(){
        bodyContent = null;
        bodyContainer.removeAllViews();
        bodyContainer = null;
        fullBodyView.setTag(DIALOG_KEY_MAP_FULL_OBJECT, null);
        fullBodyView = null;
        showing = false;
    }

    public BlurryDialog hide(boolean animated){

        if(animated){
            fullBodyView.animate().alpha(0).setDuration(300).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    fullBodyView.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }else{
            fullBodyView.setVisibility(View.GONE);
        }
        showing = false;
        return this;
    }

    public BlurryDialog hide(){

        return this.hide(true);
    }

    //getters ----------------------------------------------------------------------------
    public View getBodyView(){
        if (bodyContent == null){
            setBody();
        }
        return bodyContent;
    }

    public View getFullBodyView(){

        return fullBodyView;
    }

    public ViewGroup getBodyContainer(){

        return bodyContainer;
    }

    public int getLoop() {
        return mLoop;
    }

    public float getRadius() {
        return mRadius;
    }

    public int getProportion() {
        return mProportion;
    }

    public boolean isCancelableOutSide() {
        return mCancelableOutSide;
    }

    public boolean isAnimated() {
        return mAnimated;
    }

    public boolean isShowing() {
        return showing;
    }

    //end getters ------------------------------------------------------------------------

    public static boolean checkIsOpen(Activity activity){

        if (activity == null || activity.isDestroyed() || activity.isFinishing()){
            return false;
        }

        ViewGroup  rootView = null;
        try{
            rootView = (ViewGroup) (activity.getWindow().getDecorView().getRootView());
        }catch (Exception e){
            return false;
        }

        if (rootView == null){
            return false;
        }

        for (int i = 0; i < rootView.getChildCount(); i++){
            final View childView = rootView.getChildAt(i);

            if (childView.getTag(DIALOG_KEY_MAP_KEY_BODY_FULL) != null
                    && childView.getTag(DIALOG_KEY_MAP_KEY_BODY_FULL)
                    .equals(DIALOG_KEY_STRING_BODY_FULL)){

                return true;
            }
        }

        return false;
    }

    public static int getOpenedDialogsCount(Activity activity){
        int opened = 0;

        if (activity == null || activity.isDestroyed() || activity.isFinishing()){
            return opened;
        }

        ViewGroup rootView = null;
        try{
            rootView = (ViewGroup) (activity.getWindow().getDecorView().getRootView());
        }catch (Exception e){
            return opened;
        }

        if (rootView == null){
            return opened;
        }

        for (int i = 0; i < rootView.getChildCount(); i++){
            final View childView = rootView.getChildAt(i);

            if (childView.getTag(DIALOG_KEY_MAP_KEY_BODY_FULL) != null
                    && childView.getTag(DIALOG_KEY_MAP_KEY_BODY_FULL)
                    .equals(DIALOG_KEY_STRING_BODY_FULL)){
                opened += 1;
            }
        }

        return opened;
    }

    public static void removeAllOpenedDialogs(Activity activity, boolean animated){
        if(BlurryDialog.checkIsOpen(activity)){
            ViewGroup  rootView = null;
            try{
                rootView = (ViewGroup) (activity.getWindow().getDecorView().getRootView());
                for (int i = 0; i < rootView.getChildCount(); i++){
                    final View childView = rootView.getChildAt(i);
                    if (childView.getTag(DIALOG_KEY_MAP_KEY_BODY_FULL) != null
                            && childView.getTag(DIALOG_KEY_MAP_KEY_BODY_FULL)
                            .equals(DIALOG_KEY_STRING_BODY_FULL)){

                        final ViewGroup finalRootView = rootView;
                        final BlurryDialog dialog = (BlurryDialog) childView.getTag(DIALOG_KEY_MAP_FULL_OBJECT);
                        if(animated) {
                            childView.animate().setDuration(400).alpha(0)
                                    .setListener(new Animator.AnimatorListener() {
                                        @Override
                                        public void onAnimationStart(Animator animation) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            finalRootView.removeViewInLayout(childView);
                                            dialog.destroy();
                                        }

                                        @Override
                                        public void onAnimationCancel(Animator animation) {

                                        }

                                        @Override
                                        public void onAnimationRepeat(Animator animation) {

                                        }
                                    });
                        }else{
                            finalRootView.removeViewInLayout(childView);
                            dialog.destroy();
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void removeAllOpenedDialogs(Activity activity){
        removeAllOpenedDialogs(activity, false);
    }
}

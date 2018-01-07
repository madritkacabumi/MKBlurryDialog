# MKBlurryDialog

A blurry dialog iOS like effect. Show your popup/dialog with a blurry effect background

## Getting Started

MKBlurryDialog is an Android library for Dialog/AlertBuilder. it uses iOS like effect for blurry background. It is very simple to use contet-customisable.

### Prerequisites

What things you need to install the software and how to install them

```
Min SDK : Api 21 Android Lolipop 5.0
```

### Installing

First, add thouse lines into gradle :

 Step 1. In build.gradle (Project Level) Add the JitPack repository to your build file :

```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

 Step 2. (VERY IMPORTANT) In build.gradle (app Level) Add the renderscript support in defaultConfig :

```
android {
    compileSdkVersion **
    buildToolsVersion "**.**.*"
    defaultConfig {
    	...
        renderscriptSupportModeEnabled true
    }
    ...
}
```

Step 2. Add the dependency in build.gradle (app level) :

```
compile 'com.github.devMadrit:MKBlurryDialog:1.2'
```


## Using

1) Create Dialog object
```

 BlurryDialog dialog = new BlurryDialog(this);
        dialog.setCancelableOutSide(true)
                .setLoop(5)
                .setProportion(4)
                .setAnimated(true)
                .setRadius(20f)
                .setTitle("your title")
                .setMessage("Hello World").show();

```

 *** Explanation ***
   The logic is simple , this dialog takes the root of the app and blurry it as an image. and than adds a ViewGroup container center horizontal , center vertical to your root with this blurried image.

2) BlurryDialog setLoop(int loop).

```
dialog.setLoop(3);

// sets the number of times , the background will be blurried. more is the value , more it will be blurried.

```

3) BlurryDialog setProportion(int proportion).

```
dialog.setProportion(4);

// the background image size will be height/proportion  width/proportion , (screenshoot of your root ViewGroup).
//  less the value higher resolution will be the screenshoot , slower wil appear the dialog , larger the valuew , more blurried and faster bitmap proccesing will be.
// its recomended min of 3 becouse was tested in older devices.

```

4) BlurryDialog setRadius(float radius).

```
dialog.setRadius(15f);

// sets the blurried radius for yor background. max valuew should be 25f.

```


5) Default body

```
// the dialog/alert dialog body comes with a default layout that has a title , message, and an 'ok' 
// button. You can customise the title and message , and the ok button will dismiss the dialog
	.setTitle("your title")
	.setMessage("Hello World");

```

### Screen

<img src="https://raw.githubusercontent.com/devMadrit/MKBlurryDialog/master/gitimages/image1.png" width="360" height="640"> ---- <img src="https://raw.githubusercontent.com/devMadrit/MKBlurryDialog/master/gitimages/image2.png" width="360" height="640">

6) Custom Body

```
//  you can add a custom view as the body of your dialog and reference its elements

       Dialog dialog = new BlurryDialog(this);
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

        dialog.getBacgroundImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // what happens when you click out side the body layout

            }
        });

        dialog.show();

```
### Screen

<img src="https://raw.githubusercontent.com/devMadrit/MKBlurryDialog/master/gitimages/image1.png" width="360" height="640"> ---- <img src="https://raw.githubusercontent.com/devMadrit/MKBlurryDialog/master/gitimages/image3.png" width="360" height="640">



### Utils methods

Use other moethods 

```
  //getters

  dialog.getBodyView(); (returns View) // get the body content view

  dialog.getFullBodyView(); (returns View) // get the all attached view of BlurryDialog, including the background image that is blurried

  dialog.getBodyContainer(); (returns ViewGroup) // get the body content container (instance of RelativeLayout) that contains the default layout or your custom layout

  dialog.getBackgroundImage(); (returns ImageView) // gets the blurried background image

  dialog.getLoop();

  dialog.getRadius();

  dialog.getProportion();

  dialog.isCancelableOutSide();

  dialog.isAnimated();

  dialog.isShowing();





  //static methods 

// 1- BlurryDialog.checkIsOpen(Activity activity); (returns boolean) // checking if this activity has any BlurryDialog opened 

// 2- BlurryDialog.getOpenedDialogsCount(Activity activity); (returns int) // get the number of opened BlurryDialog

// 3- BlurryDialog.removeAllOpenedDialogs(Activity activity, boolean animated); (void) // remove all dialogs that could be opened and attached to the view

```

## Versioning

Version 1.2 // first release

## Authors

* **Madrit Kacabumi**

## License

This project is licensed under the MIT License
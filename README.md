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
compile 'com.github.devMadrit:MKBlurryDialog:1.0'
```

End with an example of getting some data out of the system or using it for a little demo

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
// the dialog/alert dialog body comes with a default layout that has a title , message, and an 'ok' button.

```

### Screen

<img src="https://raw.githubusercontent.com/devMadrit/MKBlurryDialog/master/gitimages/image1.png" width="360" height="640">




### Atributes or Customisation

It is a good idea that width and height are equal , but it is not fully required 
Here you have a list of attributes to add to xml. The same attributes avaible in setters and getters through java code

```
    <declare-styleable name="MKAnalogClockView">
        <attr name="clockType" format="enum">
            <enum name="analog" value="0"/>
            <enum name="digital" value="1"/>
        </attr>

        <!--move hands always based to time , for example move seconds smoothly each milisecond-->
        <attr name="alwaysMovingHands" format="boolean"/>



        <!--analog clock circle customisation -->
        <attr name="circleEnabled" format="boolean"/> 	<!--if you want the circle around the clock -->
        <attr name="circleColor" format="color"/>  		<!--Color of circle  -->
        <attr name="circleStroke" format="integer"/>	<!--Stroke size  -->
        <attr name="circlePaintStyle" format="enum">	<!--Type of paint  -->
            <enum name="FILL" value="0"/>
            <enum name="STROKE" value="1"/>
            <enum name="FILL_AND_STROKE" value="2"/>
        </attr>

        <!--center of clock-->
        <attr name="centerEnabled" format="boolean"/>
        <attr name="centerColor" format="color"/>
        <attr name="centerSize" format="integer"/>
        <attr name="centerPaintStyle" format="enum">
            <enum name="FILL" value="0"/>
            <enum name="STROKE" value="1"/>
            <enum name="FILL_AND_STROKE" value="2"/>
        </attr>

        <!-- numbers around the clock, -->
        <attr name="numeralsEnabled" format="boolean"/>
        <attr name="numeralsFontSize" format="dimension"/>
        <attr name="numeralsColor" format="color"/>
        <attr name="numeralsSpacingToCircle" format="dimension"/> <!-- distance from the circle for numbers-->

        <!--Seconds line decorations, adding lines around the clock circle (no matter if the circle is enabled) to decorate seconds-->
        <attr name="secLinesDecorationEnabled" format="boolean"/>
        <attr name="secLinesDecorationHeight" format="dimension"/>
        <attr name="secLineDecorationColor" format="color"/>
        <attr name="secLineDecorationPaintStyle" format="enum">
            <enum name="FILL" value="0"/>
            <enum name="STROKE" value="1"/>
            <enum name="FILL_AND_STROKE" value="2"/>
        </attr>
        <attr name="secLineStroke" format="integer"/>

        <!--------------------HANDS----------------------->
        <!-- Hour Hand-->
        <attr name="handHourPadding" format="dimension"/> <!--Padding or distance from clock cirle (no matter if the circle is enabled) to the hand-->
        <attr name="handHourColor" format="color"/>
        <attr name="handHourPaintStyle" format="enum">
            <enum name="FILL" value="0"/>
            <enum name="STROKE" value="1"/>
            <enum name="FILL_AND_STROKE" value="2"/>
        </attr>
        <attr name="handHourStroke" format="integer"/>

        <!-- Minutes Hand-->
        <attr name="handMinutesPadding" format="dimension"/>
        <attr name="handMinutesColor" format="color"/>
        <attr name="handMinutesPaintStyle" format="enum">
            <enum name="FILL" value="0"/>
            <enum name="STROKE" value="1"/>
            <enum name="FILL_AND_STROKE" value="2"/>
        </attr>
        <attr name="handMinutesStroke" format="integer"/>

        <!-- Seconds Hand-->
        <attr name="secondsEnabled" format="boolean"/>
        <attr name="handSecondsPadding" format="dimension"/>
        <attr name="handSecondsColor" format="color"/>
        <attr name="handSecondsPaintStyle" format="enum">
            <enum name="FILL" value="0"/>
            <enum name="STROKE" value="1"/>
            <enum name="FILL_AND_STROKE" value="2"/>
        </attr>
        <attr name="handSecondsStroke" format="integer"/>

    </declare-styleable>
```

## Versioning

Version 0.1.0 // first release

## Authors

* **Madrit Kacabumi**

## License

This project is licensed under the MIT License

## Notes

This view uses redraw often , so it is a little bit resource hungry , but it doesnt seem to affect the activity after all.
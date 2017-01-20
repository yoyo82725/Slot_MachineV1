import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Slot_MachineV1 extends PApplet {

//777Comble\u52a0\u6210
//\u97f3\u6548\u5ba3\u544a

Minim minim;
AudioPlayer SEpullDown2, SEpullDown3, SEcoinHole, SEerror, SEsevenStop, SEgetCoin, SEloseCoin, SErhythm1, SErhythm2, SErhythm3, SEbackMusic, SEbigReward,SEyesNo;
boolean SEplay2 = false, SEplay3 = false;//\u97f3\u6548\u4e0d\u9023\u7e8c\u64a5\u653e
//\u5716\u7247\u5ba3\u544a
PImage imgMachine, imgBright, imgDestroy, imgCenterBar, imgCenterMask1, imgCenterMask2, imgCenterMask3, imgStop1, imgStop2, imgStop3, imgCoinHole, imgPullDown_bot, imgMoneyArea, imgPullDown, imgCoin,imgBankruptBg,imgBankrupt1,imgBankrupt2,imgYes,imgNo,imgStopShadow,imgBarShadow,imgPullDownShadow,imgCoinHoleShadow;
PImage[] imgSeven = new PImage[7];

//\u62c9\u687f\u76f8\u95dc\u5ba3\u544a
PVector pullDownPos = new PVector(581, 226);//\u62c9\u687f\u5713\u5708\u5b9a\u4f4d
PVector coinHolePos = new PVector(562, 92);//\u6295\u5e63\u5b54\u5b9a\u4f4d
float d, d2;//\u6ed1\u9f20\u548c\u9ede\u7684\u8ddd\u96e2,d2\u7528\u4f5c777stop Y\u8ef8
boolean canPullDown = false;
//\u6295\u5e63\u5b54\u76f8\u95dc\u5ba3\u544a
float tmpX;//\u6309\u4e0b\u53bb\u6642\u7684\u5ea7\u6a19
boolean SEcoinSecess = false;//\u6295\u5e63\u6210\u529f\u6642\u7684\u97f3\u6548
boolean canPushCoin = true;//\u53ef\u5426\u6295\u5e63
//\u521d\u59cb\u91d1\u9322\u3001\u6295\u5165\u5e63\u6578\u3001\u589e\u6e1b\u91d1\u9322
int money = 300, nowCoin = 0,maxMoney = 300;
String money2 = "-0";
//777\u76f8\u95dc
boolean sevenIni = true, sevenStart = false, sevenStop1 = false, sevenStop2 = false, sevenStop3 = false, sevenReward = false, sevenReward2 = false,getSeven = false;//\u521d\u59cb\u986f\u793a\u3001\u958b\u59cb\u3001\u505c\u6b62 \u53ef\u5426\u8f49\u52d5 \u662f\u5426\u6309\u505c\u6b62 \u662f\u5426\u9818\u734e \u4e0d\u91cd\u8907\u7b97\u9322 \u9ab0\u5230777
boolean playSe = true, playSe2 = true, playSe3 = true, getCoinSE = true, sevenRhythm1 = true, sevenRhythm2 = true, sevenRhythm3 = true;//\u4e0d\u91cd\u8907\u64a5\u653e\u932f\u8aa4\u97f3\u6548
PVector stopBtnPos1 = new PVector(140, 546);//\u6309\u9215\u5b9a\u4f4d STOP1
PVector stopBtnPos2 = new PVector(260, 546);//\u6309\u9215\u5b9a\u4f4d STOP2
PVector stopBtnPos3 = new PVector(380, 549);//\u6309\u9215\u5b9a\u4f4d STOP3
int imgHeight = 60, imgDownSpd=10;//\u5716\u7247\u9ad860\uff0c\u5716\u7247\u4e00\u6b21\u79fb\u52d51/4
int imgDown=imgHeight / imgDownSpd, imgChange1=0, imgChange2=0, imgChange3=0;//\u5716\u7247\u5411\u4e0b\u79fb\u52d5\u3001\u63a7\u5236\u66f4\u65b0\u5716\u7247
int chooseImg1=6, chooseImg2=0, chooseImg3=1;//\u63a7\u5236\u6efe\u8ef8\u5716\u7247\u7d22\u5f15
int rewardMoney = 0,sevenComble = 0,sevenCombleMax = 0;//\u9818\u734e\u589e\u6e1b\u91d1\u9322\u7e3d\u984d,777\u9023\u7e8c\u4e2d\u734e\u6578
int[] Llite = new int[5];//\u63a7\u5236\u5de6\u908a\u4e94\u9846\u71c8\u4eae
int[] Rlite = new int[5];//\u63a7\u5236\u53f3\u908a\u4e94\u9846\u71c8\u4eae
int speedLv = 3;//1-60 2-30 3-20 4-15 5-12 6-10 7-6 8-5 9-4 10-3 11-2 12-1
int seeBonus = 100;
float bonus = 1.0f;
//\u7834\u7522\u76f8\u95dc
boolean isBankrupt = false,bankruptSE = true,yesNoSE1 = true,yesNoSE2 = true;

public void setup() {
  //\u8996\u7a97 
  size(655, 585);
  //\u97f3\u6548
  minim = new Minim(this);
  SEbackMusic = minim.loadFile("sound/marcus_kellis_theme.mp3");
  SEpullDown2 = minim.loadFile("sound/pullDown.mp3");
  SEpullDown3 = minim.loadFile("sound/start.mp3");
  SEcoinHole = minim.loadFile("sound/coin.mp3");
  SEerror = minim.loadFile("sound/buzzer1.mp3");
  SEsevenStop = minim.loadFile("sound/sevenStop.mp3");
  SEgetCoin = minim.loadFile("sound/item3.mp3");
  SEloseCoin = minim.loadFile("sound/fail.mp3");
  SErhythm1 = minim.loadFile("sound/Fx08100_T602.mp3");
  SErhythm2 = minim.loadFile("sound/Fx08101_T603.mp3");
  SErhythm3 = minim.loadFile("sound/Fx08102_T604.mp3");
  SEbigReward = minim.loadFile("sound/PIANOLOOP_03.mp3");
  SEyesNo = minim.loadFile("sound/yesNo.wav");
  SEbackMusic.loop();
  //\u5716\u7247
  imgMachine = loadImage("img/machine.png");//\u6a5f\u53f0\u80cc\u666f
  //imgTopBar = loadImage("img/topBar.png");//\u4e0a\u9762\u90a3\u689d
  imgBright = loadImage("img/bright.png");//\u4eae\u5149
  imgDestroy = loadImage("img/destroy.png");//\u6ec5\u5149
  imgCenterBar = loadImage("img/centerBar.png");//\u4e2d\u9593\u4e09\u689d\u4e2d\u7684\u4e00\u689d
  imgCenterMask1 = loadImage("img/centerBar1.png");//\u4e2d\u9593\u4e09\u689d\u4e2d\u7684\u906e\u7f69\u5de6
  imgCenterMask2 = loadImage("img/centerBar2.png");//\u4e2d\u9593\u4e09\u689d\u4e2d\u7684\u906e\u7f69\u4e2d
  imgCenterMask3 = loadImage("img/centerBar3.png");//\u4e2d\u9593\u4e09\u689d\u4e2d\u7684\u906e\u7f69\u53f3
  imgSeven[0] = loadImage("img/seven1.png");//777
  imgSeven[1] = loadImage("img/seven2.png");//777
  imgSeven[2] = loadImage("img/seven3.png");//777
  imgSeven[3] = loadImage("img/seven4.png");//777
  imgSeven[4] = loadImage("img/seven5.png");//777
  imgSeven[5] = loadImage("img/seven6.png");//777
  imgSeven[6] = loadImage("img/seven7.png");//777
  imgStop1 = loadImage("img/stopBtn.png");//stop\u6309\u9215
  imgStop2 = loadImage("img/stopBtn.png");//stop\u6309\u9215
  imgStop3 = loadImage("img/stopBtn.png");//stop\u6309\u9215
  imgCoinHole = loadImage("img/vCoinHole.png");//\u6295\u5e63\u5b54
  imgMoneyArea = loadImage("img/moneyArea.png");//\u5269\u9918\u91d1\u9322\u80cc\u666f
  imgPullDown = loadImage("img/pullDown_1.png");//\u62c9\u687f
  imgCoin = loadImage("img/coin.png");//\u9322\u5e63
  imgBankruptBg = loadImage("img/bankrupt3.png");//\u7834\u7522\u80cc\u666f
  imgBankrupt1 = loadImage("img/bankrupt1.png");//\u7834\u7522\u8a0a\u606f
  imgBankrupt2 = loadImage("img/bankrupt2.png");//\u7834\u7522\u9078\u64c7
  imgYes = loadImage("img/yesGreen.png");//\u7834\u7522Yes
  imgNo = loadImage("img/noGreen.png");//\u7834\u7522No
  imgStopShadow = loadImage("img/stopShadow.png");//stop\u9670\u5f71
  imgBarShadow = loadImage("img/barShadow.png");//777\u9670\u5f71
  imgPullDownShadow = loadImage("img/pullDownShadow1.png");//\u62c9\u687f\u9670\u5f71
  imgCoinHoleShadow = loadImage("img/vCoinHoleShadow.png");//\u6295\u5e63\u5b54\u9670\u5f71
  //\u6587\u5b57
  PFont font;
  font = createFont("Georgia", 32);
  textFont(font);
  textSize(36);
  //\u4e0a\u7a97
}

public void draw() {
  //\u6a5f\u53f0\u80cc\u666f
  image(imgMachine, 0, 0);
  //************************
  //\u4e0a\u9762\u90a3\u689d
  //************************
  //image(imgTopBar, 12, 27);
  //\u5f71\u5b50
  stroke(0);
  strokeWeight(0);
  fill(128);
  rect(19,35,488,133);
  //\u6a5f\u5b50
  stroke(255);
  strokeWeight(5);
  fill(0);
  rect(12,27,488,133);
  strokeWeight(3);
  for(int i = 0; i < SEbackMusic.bufferSize() - 1; i++)//\u97f3\u7dda
  {
    float x1 = map( i, 25, SEbackMusic.bufferSize(), 25, 500 );
    float x2 = map( i+1, 25, SEbackMusic.bufferSize(), 25, 500 );
    line( x1, 75 + SEbackMusic.left.get(i)*50, x2, 75 + SEbackMusic.left.get(i+1)*50 );
    //line( x1, 150 + SEbackMusic.right.get(i)*50, x2, 150 + SEbackMusic.right.get(i+1)*50 );
  }
  //\u6587\u5b57
  fill(255);
  textSize(16);
  text("777 Comble:"+sevenComble,20,127);
  text("777 Max Comble:"+sevenCombleMax,20,147);
  text("Max money:"+maxMoney,210,127);
  text("SpeedLv:"+speedLv,210,147);
  text("Bonus:%"+seeBonus,385,147);
  //**************\u4e0a\u9762\u90a3\u689d\u7d50\u675f***************
  //\u6295\u5e63\u71c8
  if (!sevenReward) {
    //\u5de6\u908a\u4e94\u9846\u71c8
    if (nowCoin == 0) {
      Llite[0] = 0;
      Llite[1] = 0;
      Llite[2] = 0;
      Llite[3] = 0;
      Llite[4] = 0;
    } else if (nowCoin == 1) {
      Llite[0] = 0;
      Llite[1] = 0;
      Llite[2] = 1;
      Llite[3] = 0;
      Llite[4] = 0;
    } else if (nowCoin == 2) {
      Llite[0] = 0;
      Llite[1] = 1;
      Llite[2] = 1;
      Llite[3] = 1;
      Llite[4] = 0;
    } else {
      Llite[0] = 1;
      Llite[1] = 1;
      Llite[2] = 1;
      Llite[3] = 1;
      Llite[4] = 1;
    }
    //\u5370\u51fa\u5728\u9818\u734e\u4e0b\u9762
    //\u53f3\u908a\u4e94\u9846\u71c8
    if (nowCoin == 0) {
      Rlite[0] = 0;
      Rlite[1] = 0;
      Rlite[2] = 0;
      Rlite[3] = 0;
      Rlite[4] = 0;
    } else if (nowCoin == 1) {
      Rlite[0] = 0;
      Rlite[1] = 0;
      Rlite[2] = 1;
      Rlite[3] = 0;
      Rlite[4] = 0;
    } else if (nowCoin == 2) {
      Rlite[0] = 0;
      Rlite[1] = 1;
      Rlite[2] = 1;
      Rlite[3] = 1;
      Rlite[4] = 0;
    } else {
      Rlite[0] = 1;
      Rlite[1] = 1;
      Rlite[2] = 1;
      Rlite[3] = 1;
      Rlite[4] = 1;
    }
  }
  //\u5370\u51fa\u5728\u9818\u734e\u4e0b\u9762
  //\u4e2d\u9593\u4e09\u689d
  for (int i=99;i<350;i+=120) {
    image(imgCenterBar, i, 240);
  }
  //Stop
  image(imgStopShadow,102,520);
  image(imgStopShadow,222,520);
  image(imgStopShadow,342,520);
  image(imgStop1, 97, 515);
  image(imgStop2, 217, 515);
  image(imgStop3, 337, 515);
  //\u6295\u5e63\u5b54
  image(imgCoinHoleShadow, 528, 34);
  image(imgCoinHole, 525, 33);
  //\u62c9\u687f
  image(imgPullDownShadow, 532, 201);
  image(imgPullDown, 527, 196);
  //\u5269\u9918\u91d1\u9322\u80cc\u666f
  image(imgMoneyArea, 435, 513);
  //\u5269\u9918\u91d1\u9322
  textSize(36);
  fill(255, 144, 25);
  text(money, 549, 550);
  //\u589e\u6e1b\u91d1\u9322
  text(money2, 535, 505);
  //**************************
  //\u52d5\u4f5c
  //**************************
  try {
    //*******************
    //777 Start&Stop
    //*******************
    //\u901f\u5ea6\u8abf\u7bc0
    if(money < 100)
      speedLv = 1;
    else if(money < 200)
      speedLv = 2;
    else if(money < 400)
      speedLv = 3;
    else if(money < 800)
      speedLv = 4;
    else if(money < 2000)
      speedLv = 5;
    else if(money < 3500)
      speedLv = 6;
    else if(money < 6000)
      speedLv = 7;
    else if(money < 10000)
      speedLv = 8;
    else if(money < 14000)
      speedLv = 9;
    else if(money < 18000)
      speedLv = 10;
    else if(money < 22000)
      speedLv = 11;
    else
      speedLv = 12;
    switch(speedLv){
      case 1:
        imgDownSpd = 60;
        break;
      case 2:
        imgDownSpd = 30;
        break;
      case 3:
        imgDownSpd = 20;
        break;
      case 4:
        imgDownSpd = 15;
        break;
      case 5:
        imgDownSpd = 12;
        break;
      case 6:
        imgDownSpd = 10;
        break;
      case 7:
        imgDownSpd = 6;
        break;
      case 8:
        imgDownSpd = 5;
        break;
      case 9:
        imgDownSpd = 4;
        break;
      case 10:
        imgDownSpd = 3;
        break;
      case 11:
        imgDownSpd = 2;
        break;
      case 12:
        imgDownSpd = 1;
        break;
      default:
        imgDownSpd = 60;
    }
    imgDown=imgHeight / imgDownSpd;
    if (sevenIni) {//\u525b\u958b\u59cb
      //\u5de6\u689d
      image(imgSeven[(chooseImg1+3)%imgSeven.length], 99, 200+imgChange1*imgDown);//\u4e0a\u906e
      image(imgSeven[(chooseImg1+2)%imgSeven.length], 99, (200+imgHeight)+imgChange1*imgDown);//\u4e0a
      image(imgSeven[(chooseImg1+1)%imgSeven.length], 99, (200+imgHeight*2)+imgChange1*imgDown);//\u4e2d
      image(imgSeven[chooseImg1%imgSeven.length], 99, (200+imgHeight*3)+imgChange1*imgDown);//\u4e0b
      //\u4e2d\u689d
      image(imgSeven[(chooseImg2+3)%imgSeven.length], 219, 200+imgChange2*imgDown);//\u4e0a\u906e
      image(imgSeven[(chooseImg2+2)%imgSeven.length], 219, (200+imgHeight)+imgChange2*imgDown);//\u4e0a
      image(imgSeven[(chooseImg2+1)%imgSeven.length], 219, (200+imgHeight*2)+imgChange2*imgDown);//\u4e2d
      image(imgSeven[chooseImg2%imgSeven.length], 219, (200+imgHeight*3)+imgChange2*imgDown);//\u4e0b
      //\u53f3\u689d
      image(imgSeven[(chooseImg3+3)%imgSeven.length], 339, 200+imgChange3*imgDown);//\u4e0a\u906e
      image(imgSeven[(chooseImg3+2)%imgSeven.length], 339, (200+imgHeight)+imgChange3*imgDown);//\u4e0a
      image(imgSeven[(chooseImg3+1)%imgSeven.length], 339, (200+imgHeight*2)+imgChange3*imgDown);//\u4e2d
      image(imgSeven[chooseImg3%imgSeven.length], 339, (200+imgHeight*3)+imgChange3*imgDown);//\u4e0b
    } else if (sevenStart) {
      //\u5de6\u689d
      if (sevenStop1) {//\u505c\u6b62
        if (imgChange1 > 0) {//\u8f49\u5230\u6b63\u78ba\u4f4d\u7f6e
          imgChange1 = (imgChange1+1)%imgDownSpd; //01234
          if (imgChange1 == 0) { //\u63db\u5716\u7247
            chooseImg1++;
          }
          //\u7531\u4e0b\u81f3\u4e0a
          image(imgSeven[(chooseImg1+3)%imgSeven.length], 99, 200+imgChange1*imgDown);//\u4e0a\u906e
          image(imgSeven[(chooseImg1+2)%imgSeven.length], 99, (200+imgHeight)+imgChange1*imgDown);//\u4e0a
          image(imgSeven[(chooseImg1+1)%imgSeven.length], 99, (200+imgHeight*2)+imgChange1*imgDown);//\u4e2d
          image(imgSeven[chooseImg1%imgSeven.length], 99, (200+imgHeight*3)+imgChange1*imgDown);//\u4e0b
        } else {//\u505c\u6b62
          image(imgSeven[(chooseImg1+3)%imgSeven.length], 99, 200+imgChange1*imgDown);//\u4e0a\u906e
          image(imgSeven[(chooseImg1+2)%imgSeven.length], 99, (200+imgHeight)+imgChange1*imgDown);//\u4e0a
          image(imgSeven[(chooseImg1+1)%imgSeven.length], 99, (200+imgHeight*2)+imgChange1*imgDown);//\u4e2d
          image(imgSeven[chooseImg1%imgSeven.length], 99, (200+imgHeight*3)+imgChange1*imgDown);//\u4e0b
        }
      } else {//\u904b\u8f49
        //\u7bc0\u594f\u97f3\u6a02
        if (chooseImg1%imgSeven.length==5) {
          sevenRhythm1 = true;
          if (sevenRhythm1) {
            SErhythm1.rewind();
            SErhythm1.play();
            sevenRhythm1 = false;
          }
        }
        imgChange1 = (imgChange1+1)%imgDownSpd; //01234
        if (imgChange1 == 0) { //\u63db\u5716\u7247
          chooseImg1++;
        }
        //\u7531\u4e0b\u81f3\u4e0a
        image(imgSeven[(chooseImg1+3)%imgSeven.length], 99, 200+imgChange1*imgDown);//\u4e0a\u906e
        image(imgSeven[(chooseImg1+2)%imgSeven.length], 99, (200+imgHeight)+imgChange1*imgDown);//\u4e0a
        image(imgSeven[(chooseImg1+1)%imgSeven.length], 99, (200+imgHeight*2)+imgChange1*imgDown);//\u4e2d
        image(imgSeven[chooseImg1%imgSeven.length], 99, (200+imgHeight*3)+imgChange1*imgDown);//\u4e0b
      }
      //\u4e2d\u689d
      if (sevenStop2) {//\u505c\u6b62
        if (imgChange2 > 0) {//\u8f49\u5230\u6b63\u78ba\u4f4d\u7f6e
          imgChange2 = (imgChange2+1)%imgDownSpd; //01234
          if (imgChange2 == 0) { //\u63db\u5716\u7247
            chooseImg2++;
          }
          //\u7531\u4e0b\u81f3\u4e0a
          image(imgSeven[(chooseImg2+3)%imgSeven.length], 219, 200+imgChange2*imgDown);//\u4e0a\u906e
          image(imgSeven[(chooseImg2+2)%imgSeven.length], 219, (200+imgHeight)+imgChange2*imgDown);//\u4e0a
          image(imgSeven[(chooseImg2+1)%imgSeven.length], 219, (200+imgHeight*2)+imgChange2*imgDown);//\u4e2d
          image(imgSeven[chooseImg2%imgSeven.length], 219, (200+imgHeight*3)+imgChange2*imgDown);//\u4e0b
        } else {//\u505c\u6b62
          image(imgSeven[(chooseImg2+3)%imgSeven.length], 219, 200+imgChange2*imgDown);//\u4e0a\u906e
          image(imgSeven[(chooseImg2+2)%imgSeven.length], 219, (200+imgHeight)+imgChange2*imgDown);//\u4e0a
          image(imgSeven[(chooseImg2+1)%imgSeven.length], 219, (200+imgHeight*2)+imgChange2*imgDown);//\u4e2d
          image(imgSeven[chooseImg2%imgSeven.length], 219, (200+imgHeight*3)+imgChange2*imgDown);//\u4e0b
        }
      } else {//\u904b\u8f49
        //\u7bc0\u594f\u97f3\u6a02
        if (chooseImg2%imgSeven.length==5) {
          sevenRhythm2 = true;
          if (sevenRhythm2) {
            SErhythm2.rewind();
            SErhythm2.play();
            sevenRhythm2 = false;
          }
        }
        imgChange2 = (imgChange2+1)%imgDownSpd; //01234
        if (imgChange2 == 0) { //\u63db\u5716\u7247
          chooseImg2++;
        }
        //\u7531\u4e0b\u81f3\u4e0a
        image(imgSeven[(chooseImg2+3)%imgSeven.length], 219, 200+imgChange2*imgDown);//\u4e0a\u906e
        image(imgSeven[(chooseImg2+2)%imgSeven.length], 219, (200+imgHeight)+imgChange2*imgDown);//\u4e0a
        image(imgSeven[(chooseImg2+1)%imgSeven.length], 219, (200+imgHeight*2)+imgChange2*imgDown);//\u4e2d
        image(imgSeven[chooseImg2%imgSeven.length], 219, (200+imgHeight*3)+imgChange2*imgDown);//\u4e0b
        
      }
      //\u53f3\u689d
      if (sevenStop3) {//\u505c\u6b62
        if (imgChange3 > 0) {//\u8f49\u5230\u6b63\u78ba\u4f4d\u7f6e
          imgChange3 = (imgChange3+1)%imgDownSpd; //01234
          if (imgChange3 == 0) { //\u63db\u5716\u7247
            chooseImg3++;
          }
          //\u7531\u4e0b\u81f3\u4e0a
          image(imgSeven[(chooseImg3+3)%imgSeven.length], 339, 200+imgChange3*imgDown);//\u4e0a\u906e
          image(imgSeven[(chooseImg3+2)%imgSeven.length], 339, (200+imgHeight)+imgChange3*imgDown);//\u4e0a
          image(imgSeven[(chooseImg3+1)%imgSeven.length], 339, (200+imgHeight*2)+imgChange3*imgDown);//\u4e2d
          image(imgSeven[chooseImg3%imgSeven.length], 339, (200+imgHeight*3)+imgChange3*imgDown);//\u4e0b
        } else {//\u505c\u6b62
          image(imgSeven[(chooseImg3+3)%imgSeven.length], 339, 200+imgChange3*imgDown);//\u4e0a\u906e
          image(imgSeven[(chooseImg3+2)%imgSeven.length], 339, (200+imgHeight)+imgChange3*imgDown);//\u4e0a
          image(imgSeven[(chooseImg3+1)%imgSeven.length], 339, (200+imgHeight*2)+imgChange3*imgDown);//\u4e2d
          image(imgSeven[chooseImg3%imgSeven.length], 339, (200+imgHeight*3)+imgChange3*imgDown);//\u4e0b
        }
      } else {//\u904b\u8f49
        //\u7bc0\u594f\u97f3\u6a02
        if (chooseImg3%imgSeven.length==5) {
          sevenRhythm3 = true;
          if (sevenRhythm3) {
            SErhythm3.rewind();
            SErhythm3.play();
            sevenRhythm3 = false;
          }
        }
        imgChange3 = (imgChange3+1)%imgDownSpd; //01234
        if (imgChange3 == 0) { //\u63db\u5716\u7247
          chooseImg3++;
        }
        //\u7531\u4e0b\u81f3\u4e0a
        image(imgSeven[(chooseImg3+3)%imgSeven.length], 339, 200+imgChange3*imgDown);//\u4e0a\u906e
        image(imgSeven[(chooseImg3+2)%imgSeven.length], 339, (200+imgHeight)+imgChange3*imgDown);//\u4e0a
        image(imgSeven[(chooseImg3+1)%imgSeven.length], 339, (200+imgHeight*2)+imgChange3*imgDown);//\u4e2d
        image(imgSeven[chooseImg3%imgSeven.length], 339, (200+imgHeight*3)+imgChange3*imgDown);//\u4e0b
      }
    } else {//\u5df2\u505c
      //\u5de6\u689d
      image(imgSeven[(chooseImg1+3)%imgSeven.length], 99, 200+imgChange1*imgDown);//\u4e0a\u906e
      image(imgSeven[(chooseImg1+2)%imgSeven.length], 99, (200+imgHeight)+imgChange1*imgDown);//\u4e0a
      image(imgSeven[(chooseImg1+1)%imgSeven.length], 99, (200+imgHeight*2)+imgChange1*imgDown);//\u4e2d
      image(imgSeven[chooseImg1%imgSeven.length], 99, (200+imgHeight*3)+imgChange1*imgDown);//\u4e0b
      //\u4e2d\u689d
      image(imgSeven[(chooseImg2+3)%imgSeven.length], 219, 200+imgChange2*imgDown);//\u4e0a\u906e
      image(imgSeven[(chooseImg2+2)%imgSeven.length], 219, (200+imgHeight)+imgChange2*imgDown);//\u4e0a
      image(imgSeven[(chooseImg2+1)%imgSeven.length], 219, (200+imgHeight*2)+imgChange2*imgDown);//\u4e2d
      image(imgSeven[chooseImg2%imgSeven.length], 219, (200+imgHeight*3)+imgChange2*imgDown);//\u4e0b
      //\u53f3\u689d
      image(imgSeven[(chooseImg3+3)%imgSeven.length], 339, 200+imgChange3*imgDown);//\u4e0a\u906e
      image(imgSeven[(chooseImg3+2)%imgSeven.length], 339, (200+imgHeight)+imgChange3*imgDown);//\u4e0a
      image(imgSeven[(chooseImg3+1)%imgSeven.length], 339, (200+imgHeight*2)+imgChange3*imgDown);//\u4e2d
      image(imgSeven[chooseImg3%imgSeven.length], 339, (200+imgHeight*3)+imgChange3*imgDown);//\u4e0b
    }
    //\u906e\u7f69+\u5f71\u5b50
    image(imgCenterMask1, 99, 190);//\u906e\u7f69
    image(imgCenterMask2, 219, 190);
    image(imgCenterMask3, 339, 190);
    image(imgBarShadow, 109, 243);//\u5f71\u5b50
    image(imgBarShadow, 229, 243);//\u5f71\u5b50
    image(imgBarShadow, 349, 243);//\u5f71\u5b50
    //*******************
    //777 \u9818\u734e
    //*******************
    //777\u505c\u6b62
    if (sevenStart && sevenStop1 && sevenStop2 && sevenStop3 && imgChange3==0 && imgChange2==0 && imgChange1==0) {//\u5168\u90e8\u505c\u6b62
      sevenStop1 = false;
      sevenStop2 = false;
      sevenStop3 = false;
      sevenStart = false;
      sevenReward = true;//\u9818\u734e\u71c8
      sevenReward2 = true;//\u9818\u734e
    }
    if (sevenReward2) {//\u5224\u65b7\u662f\u5426\u5f97\u734e
      //\u6c34\u5e73\u5224\u65b7
      if (chooseImg1%imgSeven.length == chooseImg2%imgSeven.length && chooseImg1%imgSeven.length == chooseImg3%imgSeven.length) {
        //0\u7334\u5b501\u4e03\u4e032\u70b8\u5f483\u661f\u661f4\u62f3\u982d5\u91d1\u584a6\u860b\u679c
        //\u4e2d\u9593
        switch((chooseImg1+1)%imgSeven.length) {
          case 0:
            rewardMoney += -400;
            break;
          case 1:
            rewardMoney += 1000;
            getSeven = true;
            break;
          case 2:
            rewardMoney += -500;
            break;
          case 3:
            rewardMoney += 200;
            break;
          case 4:
            rewardMoney += -300;
            break;
          case 5:
            rewardMoney += 700;
            break;
          case 6:
            rewardMoney += 100;
        }
        if(getSeven){
          bonus += 0.2f;
          seeBonus += 20;
        }else{
          bonus += 0.1f;
          seeBonus += 10;
        }
        Llite[2]=1;
        Rlite[2]=1;
        //\u4e2d\u4e0a\u4e2d\u4e0b
        if (nowCoin >= 2) {
          switch(chooseImg1%imgSeven.length) {
            case 0:
              rewardMoney += -400;
              break;
            case 1:
              rewardMoney += 1000;
              getSeven = true;
              break;
            case 2:
              rewardMoney += -500;
              break;
            case 3:
              rewardMoney += 200;
              break;
            case 4:
              rewardMoney += -300;
              break;
            case 5:
              rewardMoney += 700;
              break;
            case 6:
              rewardMoney += 100;
          }
          switch((chooseImg1+2)%imgSeven.length) {
            case 0:
              rewardMoney += -400;
              break;
            case 1:
              rewardMoney += 1000;
              getSeven = true;
              break;
            case 2:
              rewardMoney += -500;
              break;
            case 3:
              rewardMoney += 200;
              break;
            case 4:
              rewardMoney += -300;
              break;
            case 5:
              rewardMoney += 700;
              break;
            case 6:
              rewardMoney += 100;
          }
          Llite[1]=1;
          Rlite[1]=1;
          Llite[3]=1;
          Rlite[3]=1;
          Llite[0]=0;
          Rlite[0]=0;
          Llite[4]=0;
          Rlite[4]=0;
          bonus += 0.2f;
          seeBonus += 20;
        }
      }
      //\u6a6b\u5224\u65b7
      if (nowCoin == 3) {
        //\u5de6\u4e0b\u81f3\u53f3\u4e0a
        if (chooseImg1%imgSeven.length == (chooseImg2+1)%imgSeven.length && chooseImg1%imgSeven.length == (chooseImg3+2)%imgSeven.length) {
          switch(chooseImg1%imgSeven.length) {
            case 0:
              rewardMoney += -400;
              break;
            case 1:
              rewardMoney += 1000;
              getSeven = true;
              break;
            case 2:
              rewardMoney += -500;
              break;
            case 3:
              rewardMoney += 200;
              break;
            case 4:
              rewardMoney += -300;
              break;
            case 5:
              rewardMoney += 700;
              break;
            case 6:
              rewardMoney += 100;
          }
          Llite[1]=0;
          Rlite[1]=0;
          Llite[2]=0;
          Rlite[2]=0;
          Llite[3]=0;
          Rlite[3]=0;
          Llite[0]=0;
          Rlite[4]=0;
          Llite[4]=1;
          Rlite[0]=1;
          bonus += 0.1f;
          seeBonus += 10;
        }
        //\u5de6\u4e0a\u81f3\u53f3\u4e0b
        if ((chooseImg1+2)%imgSeven.length == (chooseImg2+1)%imgSeven.length && (chooseImg1+2)%imgSeven.length == chooseImg3%imgSeven.length) {
          switch((chooseImg1+2)%imgSeven.length) {
            case 0:
              rewardMoney += -400;
              break;
            case 1:
              rewardMoney += 1000;
              getSeven = true;
              break;
            case 2:
              rewardMoney += -500;
              break;
            case 3:
              rewardMoney += 200;
              break;
            case 4:
              rewardMoney += -300;
              break;
            case 5:
              rewardMoney += 700;
              break;
            case 6:
              rewardMoney += 100;
          }
          Llite[1]=0;
          Rlite[1]=0;
          Llite[2]=0;
          Rlite[2]=0;
          Llite[3]=0;
          Rlite[3]=0;
          Llite[4]=0;
          Rlite[0]=0;
          Llite[0]=1;
          Rlite[4]=1;
          bonus += 0.1f;
          seeBonus += 10;
        }
      }
      //\u5224\u65b7\u7d50\u675f
      rewardMoney *= bonus;
      money += rewardMoney;
      maxMoney = max(money,maxMoney);
      if (getSeven) {
        if (getCoinSE) {
          SEbigReward.rewind();
          SEbigReward.play();
          sevenComble++;
          sevenCombleMax = max(sevenComble,sevenCombleMax);
        }
        money2 = "+"+rewardMoney;
        getCoinSE = false;
      } else{
        sevenComble = 0;
        if (rewardMoney > 0) {
          if (getCoinSE) {
            SEgetCoin.rewind();
            SEgetCoin.play();
          }
          money2 = "+"+rewardMoney;
          getCoinSE = false;
        } else if (rewardMoney < 0) {
          if (getCoinSE) {
            SEloseCoin.rewind();
            SEloseCoin.play();
          }
          money2 = ""+rewardMoney;
          getCoinSE = false;
        }else{
           bonus = 1.0f;
           seeBonus = 100;
        }
      }
      rewardMoney = 0;
      nowCoin = 0;
      sevenReward2 = false;//\u7d50\u675f\u9818\u734e
      getSeven = false;//\u662f\u5426\u9ab0\u52307
      //\u5224\u65b7\u7834\u7522
      if(money<5){
        isBankrupt = true;
        bankruptSE = true;
      }
    }
    //\u5370\u51fa\u71c8
    for (int i=168,j=0;i<450;i+=70,j++) {
      if (Llite[j] == 0)//\u6ec5\u71c8
        image(imgDestroy, 3, i);
      else//\u4eae\u71c8
      image(imgBright, 3, i);
    }
    for (int i=168,j=0;i<450;i+=70,j++) {
      if (Rlite[j] == 0)//\u6ec5\u71c8
        image(imgDestroy, 427, i);
      else//\u4eae\u71c8
      image(imgBright, 427, i);
    }
    if (mousePressed) {
      //**************************
      //\u62c9\u687f\u52d5\u4f5c
      //**************************
      if (mouseButton == LEFT) {
        d = dist(mouseX, mouseY, pullDownPos.x, pullDownPos.y);//\u6ed1\u9f20\u548c\u9ede\u7684\u8ddd\u96e2
        if (d<30) {//\u6709\u6309\u5230\u62c9\u687f
          canPullDown = true;
        }
        //***********************
        //\u62c9\u687f
        //***********************
        if (canPullDown) {
          if (mouseY>=390) {//\u62c9\u5230\u5e95
            //\u9818\u734e\u71c8\u6ec5&\u97f3\u6548\u958b
            sevenReward = false;
            getCoinSE = true;
            //\u63db\u5716\u7247
            imgPullDownShadow = loadImage("img/pullDownShadow3.png");
            imgPullDown = loadImage("img/pullDown_3.png");
            //\u97f3\u6548
            if (SEplay3) {
              if (nowCoin == 0 || sevenStart) {
                SEerror.rewind();
                SEerror.play();
              } else {
                sevenIni = false;
                sevenStart = true;
                SEpullDown3.rewind();
                SEpullDown3.play();
              }
              money2 = "-0";
              SEplay3 = false;
              SEplay2 = true;
            }
          } else if (mouseY>=300) {//\u62c9\u5230\u4e2d\u9593
            //\u63db\u5716\u7247
            imgPullDownShadow = loadImage("img/pullDownShadow2.png");
            imgPullDown = loadImage("img/pullDown_2.png");
            //\u97f3\u6548
            if (SEplay2) {
              SEpullDown2.rewind();
              SEpullDown2.play();
              SEplay2 = false;
              SEplay3 = true;
            }
          } else {//\u62c9\u5230\u4e0a\u9762
            //\u63db\u5716\u7247
            imgPullDownShadow = loadImage("img/pullDownShadow1.png");
            imgPullDown = loadImage("img/pullDown_1.png");
            //\u958b\u555f\u97f3\u6548\u958b\u95dc
            if (!SEplay2) {
              SEpullDown2.rewind();
              SEpullDown2.play();
              SEplay2 = true;
            }
          }
        }
        //**************************
        //\u6295\u5e63
        //**************************
        d = dist(mouseX, mouseY, coinHolePos.x, coinHolePos.y);//\u6ed1\u9f20\u548c\u9ede\u7684\u8ddd\u96e2
        if (d<60) {
          //\u5716\u7247
          if (canPushCoin) {
            if (mouseX-tmpX > 25) {//\u6295\u5e63\u6210\u529f
              //\u9818\u734e\u71c8\u6ec5&\u97f3\u6548\u958b
              sevenReward = false;
              getCoinSE = true;
              //\u97f3\u6548
              imgCoinHole = loadImage("img/vCoinHole.png");
              if (SEcoinSecess) {
                if (sevenStart || nowCoin >= 3 || money<=0) {//777\u8f49\u52d5\u4e2d\u6216\u6295\u6eff\u6216\u6c92\u9322
                  SEerror.rewind();
                  SEerror.play();
                } else {//\u91d1\u5e63\u672a\u6295\u4e09\u500b
                  SEcoinHole.rewind();
                  SEcoinHole.play();
                  money -= 5;
                  money2 = "-5";
                  nowCoin++;
                }
                SEcoinSecess = false;
                canPushCoin = false;
              }
            } else if (mouseX-tmpX > 20) 
              imgCoinHole = loadImage("img/pushCoin_4.png"); else if (mouseX-tmpX > 14)
              imgCoinHole = loadImage("img/pushCoin_3.png"); else if (mouseX-tmpX > 7)
              imgCoinHole = loadImage("img/pushCoin_2.png");
            else
              imgCoinHole = loadImage("img/pushCoin_1.png");
          }
        }
        //**************************
        //777+STOP
        //**************************
        //stop
        //stop1
        d = dist(mouseX, 0, stopBtnPos1.x, 0);//X
        d2 = dist(0, mouseY, 0, stopBtnPos1.y);//Y
        if (d<42 && d2<28) {//\u6ed1\u9f20\u5728\u4e0a\u9762
          imgStop1 = loadImage("img/stopBtnClk.png");
          if (!sevenStart || sevenIni || sevenStop1) {//\u672a\u958b\u59cb\u6216\u525b\u958b\u59cb\u6216\u5df2\u505c\u6b62
            if (playSe) {
              SEerror.rewind();
              SEerror.play();
              playSe = false;
            }
          } else {//\u505c\u6b62
            sevenStop1 = true;
            if (playSe) {
              SEsevenStop.rewind();
              SEsevenStop.play();
              playSe = false;
            }
          }
        } else {
          imgStop1 = loadImage("img/stopBtn.png");
        }
        //stop2
        d = dist(mouseX, 0, stopBtnPos2.x, 0);//X
        d2 = dist(0, mouseY, 0, stopBtnPos2.y);//Y
        if (d<42 && d2<28) {//\u6ed1\u9f20\u5728\u4e0a\u9762
          imgStop2 = loadImage("img/stopBtnClk.png");
          if (!sevenStart || sevenIni || sevenStop2) {//\u672a\u958b\u59cb\u6216\u525b\u958b\u59cb\u6216\u5df2\u505c\u6b62
            if (playSe2) {
              SEerror.rewind();
              SEerror.play();
              playSe2 = false;
            }
          } else {//\u505c\u6b62
            sevenStop2 = true;
            if (playSe2) {
              SEsevenStop.rewind();
              SEsevenStop.play();
              playSe2 = false;
            }
          }
        } else {
          imgStop2 = loadImage("img/stopBtn.png");
        }
        //stop3
        d = dist(mouseX, 0, stopBtnPos3.x, 0);//X
        d2 = dist(0, mouseY, 0, stopBtnPos3.y);//Y
        if (d<42 && d2<28) {//\u6ed1\u9f20\u5728\u4e0a\u9762
          imgStop3 = loadImage("img/stopBtnClk.png");
          if (!sevenStart || sevenIni || sevenStop3) {//\u672a\u958b\u59cb\u6216\u525b\u958b\u59cb\u6216\u5df2\u505c\u6b62
            if (playSe3) {
              SEerror.rewind();
              SEerror.play();
              playSe3 = false;
            }
          } else {//\u505c\u6b62
            sevenStop3 = true;
            if (playSe3) {
              SEsevenStop.rewind();
              SEsevenStop.play();
              playSe3 = false;
            }
          }
        } else {
          imgStop3 = loadImage("img/stopBtn.png");
        }
        //if (mouseButton == LEFT) END
      }
      //if (mousePressed) END
    } else {//MouseMove
      //**************************
      //\u6295\u5e63  \u9322\u5e63\u8ddf\u8457\u6ed1\u9f20\u8d70
      //**************************
      d = dist(mouseX, mouseY, coinHolePos.x, coinHolePos.y);//\u6ed1\u9f20\u548c\u9ede\u7684\u8ddd\u96e2
      if (d<60) {
        //\u9322\u5e63\u8ddf\u8457\u6ed1\u9f20\u8d70
        image(imgCoin, mouseX-25, mouseY-29);
      }
      //*************************
      //777 STOP\u6309\u9215\u8b8a\u8272
      //*************************
      //stop1
      d = dist(mouseX, 0, stopBtnPos1.x, 0);//X
      d2 = dist(0, mouseY, 0, stopBtnPos1.y);//Y
      if (d<42 && d2<28) {//\u6ed1\u9f20\u5728\u4e0a\u9762
        imgStop1 = loadImage("img/stopBtnOn.png");
      } else {
        imgStop1 = loadImage("img/stopBtn.png");
      }
      //stop2
      d = dist(mouseX, 0, stopBtnPos2.x, 0);//X
      d2 = dist(0, mouseY, 0, stopBtnPos2.y);//Y
      if (d<42 && d2<28) {//\u6ed1\u9f20\u5728\u4e0a\u9762
        imgStop2 = loadImage("img/stopBtnOn.png");
      } else {
        imgStop2 = loadImage("img/stopBtn.png");
      }
      //stop3
      d = dist(mouseX, 0, stopBtnPos3.x, 0);//X
      d2 = dist(0, mouseY, 0, stopBtnPos3.y);//Y
      if (d<42 && d2<28) {//\u6ed1\u9f20\u5728\u4e0a\u9762
        imgStop3 = loadImage("img/stopBtnOn.png");
      } else {
        imgStop3 = loadImage("img/stopBtn.png");
      }
    }
    //*****************
    //\u7834\u7522
    //*****************
    if(isBankrupt){
      //\u80cc\u666f
      image(imgBankruptBg, 0, 0);
      image(imgBankrupt1, 175, 63);
      image(imgBankrupt2, 86, 240);
      image(imgYes,207,389);
      image(imgNo,379,386);
      //\u97f3\u6a02
      if (bankruptSE) {
        SEloseCoin.rewind();
        SEloseCoin.play();
        bankruptSE = false;
      }
      //\u6309\u9215\u529f\u80fd
      d = dist(mouseX, mouseY, 246, 416);//\u6ed1\u9f20\u548c\u9ede\u7684\u8ddd\u96e2
      if(d < 40){
        println(d);
        if(yesNoSE1){
          SEyesNo.rewind();
          SEyesNo.play();
          yesNoSE1 = false;
        }
        if(mousePressed){//\u9ede\u4e0byes
          SEpullDown3.rewind();
          SEpullDown3.play();
          money = 300;
          bonus = 1.0f;
          seeBonus =100;
          isBankrupt = false;
          sevenReward = false;//\u9818\u734e\u71c8\u6ec5
        }
        imgYes = loadImage("img/yesYello.png");
      }else{
        yesNoSE1 = true;
        imgYes = loadImage("img/yesGreen.png");
      }
      d = dist(mouseX, mouseY, 407, 414);//\u6ed1\u9f20\u548c\u9ede\u7684\u8ddd\u96e2
      if(d < 40){
        if(yesNoSE2){
          SEyesNo.rewind();
          SEyesNo.play();
          yesNoSE2 = false;
        }
        if(mousePressed){//\u9ede\u4e0bno
          exit();
        }
        imgNo = loadImage("img/noYello.png");
      }else{
        yesNoSE2 = true;
        imgNo = loadImage("img/noGreen.png");
      }
    }
    //try END
  }
  catch(Exception e) {
    println(e);
  }
}
public void mousePressed() {//\u53ea\u57f7\u884c\u4e00\u6b21\u7684\u6309\u6ed1\u9f20
  //*****************
  //\u6295\u5e63   \u4f9d\u62d6\u66f3\u7a0b\u5ea6\u6295\u5e63
  //*****************
  tmpX = mouseX;//\u53d6\u5f97\u6309\u4e0b\u53bb\u7684\u5ea7\u6a19
  canPushCoin = true;
  SEcoinSecess = true;
  println(mouseX+","+mouseY);
}
public void mouseReleased() {//\u6ed1\u9f20\u653e\u958b
  //\u62c9\u687f\u9084\u539f
  SEplay2 = false;
  canPullDown = false;
  imgPullDownShadow = loadImage("img/pullDownShadow1.png");//\u9084\u539f\u62c9\u687f
  imgPullDown = loadImage("img/pullDown_1.png");//\u9084\u539f\u62c9\u687f
  //\u6295\u5e63\u9084\u539f
  imgCoinHole = loadImage("img/vCoinHole.png");//\u9084\u539f\u5716\u7247
  //777
  playSe = true;//stop\u97f3\u6548\u5de6
  playSe2 = true;//\u4e2d
  playSe3 = true;//\u53f3
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Slot_MachineV1" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}

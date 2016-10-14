//777Comble加成
//音效宣告
import ddf.minim.*;
Minim minim;
AudioPlayer SEpullDown2, SEpullDown3, SEcoinHole, SEerror, SEsevenStop, SEgetCoin, SEloseCoin, SErhythm1, SErhythm2, SErhythm3, SEbackMusic, SEbigReward,SEyesNo;
boolean SEplay2 = false, SEplay3 = false;//音效不連續撥放
//圖片宣告
PImage imgMachine, imgBright, imgDestroy, imgCenterBar, imgCenterMask1, imgCenterMask2, imgCenterMask3, imgStop1, imgStop2, imgStop3, imgCoinHole, imgPullDown_bot, imgMoneyArea, imgPullDown, imgCoin,imgBankruptBg,imgBankrupt1,imgBankrupt2,imgYes,imgNo,imgStopShadow,imgBarShadow,imgPullDownShadow,imgCoinHoleShadow;
PImage[] imgSeven = new PImage[7];

//拉桿相關宣告
PVector pullDownPos = new PVector(581, 226);//拉桿圓圈定位
PVector coinHolePos = new PVector(562, 92);//投幣孔定位
float d, d2;//滑鼠和點的距離,d2用作777stop Y軸
boolean canPullDown = false;
//投幣孔相關宣告
float tmpX;//按下去時的座標
boolean SEcoinSecess = false;//投幣成功時的音效
boolean canPushCoin = true;//可否投幣
//初始金錢、投入幣數、增減金錢
int money = 300, nowCoin = 0,maxMoney = 300;
String money2 = "-0";
//777相關
boolean sevenIni = true, sevenStart = false, sevenStop1 = false, sevenStop2 = false, sevenStop3 = false, sevenReward = false, sevenReward2 = false,getSeven = false;//初始顯示、開始、停止 可否轉動 是否按停止 是否領獎 不重複算錢 骰到777
boolean playSe = true, playSe2 = true, playSe3 = true, getCoinSE = true, sevenRhythm1 = true, sevenRhythm2 = true, sevenRhythm3 = true;//不重複撥放錯誤音效
PVector stopBtnPos1 = new PVector(140, 546);//按鈕定位 STOP1
PVector stopBtnPos2 = new PVector(260, 546);//按鈕定位 STOP2
PVector stopBtnPos3 = new PVector(380, 549);//按鈕定位 STOP3
int imgHeight = 60, imgDownSpd=10;//圖片高60，圖片一次移動1/4
int imgDown=imgHeight / imgDownSpd, imgChange1=0, imgChange2=0, imgChange3=0;//圖片向下移動、控制更新圖片
int chooseImg1=6, chooseImg2=0, chooseImg3=1;//控制滾軸圖片索引
int rewardMoney = 0,sevenComble = 0,sevenCombleMax = 0;//領獎增減金錢總額,777連續中獎數
int[] Llite = new int[5];//控制左邊五顆燈亮
int[] Rlite = new int[5];//控制右邊五顆燈亮
int speedLv = 3;//1-60 2-30 3-20 4-15 5-12 6-10 7-6 8-5 9-4 10-3 11-2 12-1
int seeBonus = 100;
float bonus = 1.0;
//破產相關
boolean isBankrupt = false,bankruptSE = true,yesNoSE1 = true,yesNoSE2 = true;

void setup() {
  //視窗 
  size(655, 585);
  //音效
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
  //圖片
  imgMachine = loadImage("img/machine.png");//機台背景
  //imgTopBar = loadImage("img/topBar.png");//上面那條
  imgBright = loadImage("img/bright.png");//亮光
  imgDestroy = loadImage("img/destroy.png");//滅光
  imgCenterBar = loadImage("img/centerBar.png");//中間三條中的一條
  imgCenterMask1 = loadImage("img/centerBar1.png");//中間三條中的遮罩左
  imgCenterMask2 = loadImage("img/centerBar2.png");//中間三條中的遮罩中
  imgCenterMask3 = loadImage("img/centerBar3.png");//中間三條中的遮罩右
  imgSeven[0] = loadImage("img/seven1.png");//777
  imgSeven[1] = loadImage("img/seven2.png");//777
  imgSeven[2] = loadImage("img/seven3.png");//777
  imgSeven[3] = loadImage("img/seven4.png");//777
  imgSeven[4] = loadImage("img/seven5.png");//777
  imgSeven[5] = loadImage("img/seven6.png");//777
  imgSeven[6] = loadImage("img/seven7.png");//777
  imgStop1 = loadImage("img/stopBtn.png");//stop按鈕
  imgStop2 = loadImage("img/stopBtn.png");//stop按鈕
  imgStop3 = loadImage("img/stopBtn.png");//stop按鈕
  imgCoinHole = loadImage("img/vCoinHole.png");//投幣孔
  imgMoneyArea = loadImage("img/moneyArea.png");//剩餘金錢背景
  imgPullDown = loadImage("img/pullDown_1.png");//拉桿
  imgCoin = loadImage("img/coin.png");//錢幣
  imgBankruptBg = loadImage("img/bankrupt3.png");//破產背景
  imgBankrupt1 = loadImage("img/bankrupt1.png");//破產訊息
  imgBankrupt2 = loadImage("img/bankrupt2.png");//破產選擇
  imgYes = loadImage("img/yesGreen.png");//破產Yes
  imgNo = loadImage("img/noGreen.png");//破產No
  imgStopShadow = loadImage("img/stopShadow.png");//stop陰影
  imgBarShadow = loadImage("img/barShadow.png");//777陰影
  imgPullDownShadow = loadImage("img/pullDownShadow1.png");//拉桿陰影
  imgCoinHoleShadow = loadImage("img/vCoinHoleShadow.png");//投幣孔陰影
  //文字
  PFont font;
  font = createFont("Georgia", 32);
  textFont(font);
  textSize(36);
  //上窗
}

void draw() {
  //機台背景
  image(imgMachine, 0, 0);
  //************************
  //上面那條
  //************************
  //image(imgTopBar, 12, 27);
  //影子
  stroke(0);
  strokeWeight(0);
  fill(128);
  rect(19,35,488,133);
  //機子
  stroke(255);
  strokeWeight(5);
  fill(0);
  rect(12,27,488,133);
  strokeWeight(3);
  for(int i = 0; i < SEbackMusic.bufferSize() - 1; i++)//音線
  {
    float x1 = map( i, 25, SEbackMusic.bufferSize(), 25, 500 );
    float x2 = map( i+1, 25, SEbackMusic.bufferSize(), 25, 500 );
    line( x1, 75 + SEbackMusic.left.get(i)*50, x2, 75 + SEbackMusic.left.get(i+1)*50 );
    //line( x1, 150 + SEbackMusic.right.get(i)*50, x2, 150 + SEbackMusic.right.get(i+1)*50 );
  }
  //文字
  fill(255);
  textSize(16);
  text("777 Comble:"+sevenComble,20,127);
  text("777 Max Comble:"+sevenCombleMax,20,147);
  text("Max money:"+maxMoney,210,127);
  text("SpeedLv:"+speedLv,210,147);
  text("Bonus:%"+seeBonus,385,147);
  //**************上面那條結束***************
  //投幣燈
  if (!sevenReward) {
    //左邊五顆燈
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
    //印出在領獎下面
    //右邊五顆燈
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
  //印出在領獎下面
  //中間三條
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
  //投幣孔
  image(imgCoinHoleShadow, 528, 34);
  image(imgCoinHole, 525, 33);
  //拉桿
  image(imgPullDownShadow, 532, 201);
  image(imgPullDown, 527, 196);
  //剩餘金錢背景
  image(imgMoneyArea, 435, 513);
  //剩餘金錢
  textSize(36);
  fill(255, 144, 25);
  text(money, 549, 550);
  //增減金錢
  text(money2, 535, 505);
  //**************************
  //動作
  //**************************
  try {
    //*******************
    //777 Start&Stop
    //*******************
    //速度調節
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
    if (sevenIni) {//剛開始
      //左條
      image(imgSeven[(chooseImg1+3)%imgSeven.length], 99, 200+imgChange1*imgDown);//上遮
      image(imgSeven[(chooseImg1+2)%imgSeven.length], 99, (200+imgHeight)+imgChange1*imgDown);//上
      image(imgSeven[(chooseImg1+1)%imgSeven.length], 99, (200+imgHeight*2)+imgChange1*imgDown);//中
      image(imgSeven[chooseImg1%imgSeven.length], 99, (200+imgHeight*3)+imgChange1*imgDown);//下
      //中條
      image(imgSeven[(chooseImg2+3)%imgSeven.length], 219, 200+imgChange2*imgDown);//上遮
      image(imgSeven[(chooseImg2+2)%imgSeven.length], 219, (200+imgHeight)+imgChange2*imgDown);//上
      image(imgSeven[(chooseImg2+1)%imgSeven.length], 219, (200+imgHeight*2)+imgChange2*imgDown);//中
      image(imgSeven[chooseImg2%imgSeven.length], 219, (200+imgHeight*3)+imgChange2*imgDown);//下
      //右條
      image(imgSeven[(chooseImg3+3)%imgSeven.length], 339, 200+imgChange3*imgDown);//上遮
      image(imgSeven[(chooseImg3+2)%imgSeven.length], 339, (200+imgHeight)+imgChange3*imgDown);//上
      image(imgSeven[(chooseImg3+1)%imgSeven.length], 339, (200+imgHeight*2)+imgChange3*imgDown);//中
      image(imgSeven[chooseImg3%imgSeven.length], 339, (200+imgHeight*3)+imgChange3*imgDown);//下
    } else if (sevenStart) {
      //左條
      if (sevenStop1) {//停止
        if (imgChange1 > 0) {//轉到正確位置
          imgChange1 = (imgChange1+1)%imgDownSpd; //01234
          if (imgChange1 == 0) { //換圖片
            chooseImg1++;
          }
          //由下至上
          image(imgSeven[(chooseImg1+3)%imgSeven.length], 99, 200+imgChange1*imgDown);//上遮
          image(imgSeven[(chooseImg1+2)%imgSeven.length], 99, (200+imgHeight)+imgChange1*imgDown);//上
          image(imgSeven[(chooseImg1+1)%imgSeven.length], 99, (200+imgHeight*2)+imgChange1*imgDown);//中
          image(imgSeven[chooseImg1%imgSeven.length], 99, (200+imgHeight*3)+imgChange1*imgDown);//下
        } else {//停止
          image(imgSeven[(chooseImg1+3)%imgSeven.length], 99, 200+imgChange1*imgDown);//上遮
          image(imgSeven[(chooseImg1+2)%imgSeven.length], 99, (200+imgHeight)+imgChange1*imgDown);//上
          image(imgSeven[(chooseImg1+1)%imgSeven.length], 99, (200+imgHeight*2)+imgChange1*imgDown);//中
          image(imgSeven[chooseImg1%imgSeven.length], 99, (200+imgHeight*3)+imgChange1*imgDown);//下
        }
      } else {//運轉
        //節奏音樂
        if (chooseImg1%imgSeven.length==5) {
          sevenRhythm1 = true;
          if (sevenRhythm1) {
            SErhythm1.rewind();
            SErhythm1.play();
            sevenRhythm1 = false;
          }
        }
        imgChange1 = (imgChange1+1)%imgDownSpd; //01234
        if (imgChange1 == 0) { //換圖片
          chooseImg1++;
        }
        //由下至上
        image(imgSeven[(chooseImg1+3)%imgSeven.length], 99, 200+imgChange1*imgDown);//上遮
        image(imgSeven[(chooseImg1+2)%imgSeven.length], 99, (200+imgHeight)+imgChange1*imgDown);//上
        image(imgSeven[(chooseImg1+1)%imgSeven.length], 99, (200+imgHeight*2)+imgChange1*imgDown);//中
        image(imgSeven[chooseImg1%imgSeven.length], 99, (200+imgHeight*3)+imgChange1*imgDown);//下
      }
      //中條
      if (sevenStop2) {//停止
        if (imgChange2 > 0) {//轉到正確位置
          imgChange2 = (imgChange2+1)%imgDownSpd; //01234
          if (imgChange2 == 0) { //換圖片
            chooseImg2++;
          }
          //由下至上
          image(imgSeven[(chooseImg2+3)%imgSeven.length], 219, 200+imgChange2*imgDown);//上遮
          image(imgSeven[(chooseImg2+2)%imgSeven.length], 219, (200+imgHeight)+imgChange2*imgDown);//上
          image(imgSeven[(chooseImg2+1)%imgSeven.length], 219, (200+imgHeight*2)+imgChange2*imgDown);//中
          image(imgSeven[chooseImg2%imgSeven.length], 219, (200+imgHeight*3)+imgChange2*imgDown);//下
        } else {//停止
          image(imgSeven[(chooseImg2+3)%imgSeven.length], 219, 200+imgChange2*imgDown);//上遮
          image(imgSeven[(chooseImg2+2)%imgSeven.length], 219, (200+imgHeight)+imgChange2*imgDown);//上
          image(imgSeven[(chooseImg2+1)%imgSeven.length], 219, (200+imgHeight*2)+imgChange2*imgDown);//中
          image(imgSeven[chooseImg2%imgSeven.length], 219, (200+imgHeight*3)+imgChange2*imgDown);//下
        }
      } else {//運轉
        //節奏音樂
        if (chooseImg2%imgSeven.length==5) {
          sevenRhythm2 = true;
          if (sevenRhythm2) {
            SErhythm2.rewind();
            SErhythm2.play();
            sevenRhythm2 = false;
          }
        }
        imgChange2 = (imgChange2+1)%imgDownSpd; //01234
        if (imgChange2 == 0) { //換圖片
          chooseImg2++;
        }
        //由下至上
        image(imgSeven[(chooseImg2+3)%imgSeven.length], 219, 200+imgChange2*imgDown);//上遮
        image(imgSeven[(chooseImg2+2)%imgSeven.length], 219, (200+imgHeight)+imgChange2*imgDown);//上
        image(imgSeven[(chooseImg2+1)%imgSeven.length], 219, (200+imgHeight*2)+imgChange2*imgDown);//中
        image(imgSeven[chooseImg2%imgSeven.length], 219, (200+imgHeight*3)+imgChange2*imgDown);//下
        
      }
      //右條
      if (sevenStop3) {//停止
        if (imgChange3 > 0) {//轉到正確位置
          imgChange3 = (imgChange3+1)%imgDownSpd; //01234
          if (imgChange3 == 0) { //換圖片
            chooseImg3++;
          }
          //由下至上
          image(imgSeven[(chooseImg3+3)%imgSeven.length], 339, 200+imgChange3*imgDown);//上遮
          image(imgSeven[(chooseImg3+2)%imgSeven.length], 339, (200+imgHeight)+imgChange3*imgDown);//上
          image(imgSeven[(chooseImg3+1)%imgSeven.length], 339, (200+imgHeight*2)+imgChange3*imgDown);//中
          image(imgSeven[chooseImg3%imgSeven.length], 339, (200+imgHeight*3)+imgChange3*imgDown);//下
        } else {//停止
          image(imgSeven[(chooseImg3+3)%imgSeven.length], 339, 200+imgChange3*imgDown);//上遮
          image(imgSeven[(chooseImg3+2)%imgSeven.length], 339, (200+imgHeight)+imgChange3*imgDown);//上
          image(imgSeven[(chooseImg3+1)%imgSeven.length], 339, (200+imgHeight*2)+imgChange3*imgDown);//中
          image(imgSeven[chooseImg3%imgSeven.length], 339, (200+imgHeight*3)+imgChange3*imgDown);//下
        }
      } else {//運轉
        //節奏音樂
        if (chooseImg3%imgSeven.length==5) {
          sevenRhythm3 = true;
          if (sevenRhythm3) {
            SErhythm3.rewind();
            SErhythm3.play();
            sevenRhythm3 = false;
          }
        }
        imgChange3 = (imgChange3+1)%imgDownSpd; //01234
        if (imgChange3 == 0) { //換圖片
          chooseImg3++;
        }
        //由下至上
        image(imgSeven[(chooseImg3+3)%imgSeven.length], 339, 200+imgChange3*imgDown);//上遮
        image(imgSeven[(chooseImg3+2)%imgSeven.length], 339, (200+imgHeight)+imgChange3*imgDown);//上
        image(imgSeven[(chooseImg3+1)%imgSeven.length], 339, (200+imgHeight*2)+imgChange3*imgDown);//中
        image(imgSeven[chooseImg3%imgSeven.length], 339, (200+imgHeight*3)+imgChange3*imgDown);//下
      }
    } else {//已停
      //左條
      image(imgSeven[(chooseImg1+3)%imgSeven.length], 99, 200+imgChange1*imgDown);//上遮
      image(imgSeven[(chooseImg1+2)%imgSeven.length], 99, (200+imgHeight)+imgChange1*imgDown);//上
      image(imgSeven[(chooseImg1+1)%imgSeven.length], 99, (200+imgHeight*2)+imgChange1*imgDown);//中
      image(imgSeven[chooseImg1%imgSeven.length], 99, (200+imgHeight*3)+imgChange1*imgDown);//下
      //中條
      image(imgSeven[(chooseImg2+3)%imgSeven.length], 219, 200+imgChange2*imgDown);//上遮
      image(imgSeven[(chooseImg2+2)%imgSeven.length], 219, (200+imgHeight)+imgChange2*imgDown);//上
      image(imgSeven[(chooseImg2+1)%imgSeven.length], 219, (200+imgHeight*2)+imgChange2*imgDown);//中
      image(imgSeven[chooseImg2%imgSeven.length], 219, (200+imgHeight*3)+imgChange2*imgDown);//下
      //右條
      image(imgSeven[(chooseImg3+3)%imgSeven.length], 339, 200+imgChange3*imgDown);//上遮
      image(imgSeven[(chooseImg3+2)%imgSeven.length], 339, (200+imgHeight)+imgChange3*imgDown);//上
      image(imgSeven[(chooseImg3+1)%imgSeven.length], 339, (200+imgHeight*2)+imgChange3*imgDown);//中
      image(imgSeven[chooseImg3%imgSeven.length], 339, (200+imgHeight*3)+imgChange3*imgDown);//下
    }
    //遮罩+影子
    image(imgCenterMask1, 99, 190);//遮罩
    image(imgCenterMask2, 219, 190);
    image(imgCenterMask3, 339, 190);
    image(imgBarShadow, 109, 243);//影子
    image(imgBarShadow, 229, 243);//影子
    image(imgBarShadow, 349, 243);//影子
    //*******************
    //777 領獎
    //*******************
    //777停止
    if (sevenStart && sevenStop1 && sevenStop2 && sevenStop3 && imgChange3==0 && imgChange2==0 && imgChange1==0) {//全部停止
      sevenStop1 = false;
      sevenStop2 = false;
      sevenStop3 = false;
      sevenStart = false;
      sevenReward = true;//領獎燈
      sevenReward2 = true;//領獎
    }
    if (sevenReward2) {//判斷是否得獎
      //水平判斷
      if (chooseImg1%imgSeven.length == chooseImg2%imgSeven.length && chooseImg1%imgSeven.length == chooseImg3%imgSeven.length) {
        //0猴子1七七2炸彈3星星4拳頭5金塊6蘋果
        //中間
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
          bonus += 0.2;
          seeBonus += 20;
        }else{
          bonus += 0.1;
          seeBonus += 10;
        }
        Llite[2]=1;
        Rlite[2]=1;
        //中上中下
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
          bonus += 0.2;
          seeBonus += 20;
        }
      }
      //橫判斷
      if (nowCoin == 3) {
        //左下至右上
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
          bonus += 0.1;
          seeBonus += 10;
        }
        //左上至右下
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
          bonus += 0.1;
          seeBonus += 10;
        }
      }
      //判斷結束
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
           bonus = 1.0;
           seeBonus = 100;
        }
      }
      rewardMoney = 0;
      nowCoin = 0;
      sevenReward2 = false;//結束領獎
      getSeven = false;//是否骰到7
      //判斷破產
      if(money<5){
        isBankrupt = true;
        bankruptSE = true;
      }
    }
    //印出燈
    for (int i=168,j=0;i<450;i+=70,j++) {
      if (Llite[j] == 0)//滅燈
        image(imgDestroy, 3, i);
      else//亮燈
      image(imgBright, 3, i);
    }
    for (int i=168,j=0;i<450;i+=70,j++) {
      if (Rlite[j] == 0)//滅燈
        image(imgDestroy, 427, i);
      else//亮燈
      image(imgBright, 427, i);
    }
    if (mousePressed) {
      //**************************
      //拉桿動作
      //**************************
      if (mouseButton == LEFT) {
        d = dist(mouseX, mouseY, pullDownPos.x, pullDownPos.y);//滑鼠和點的距離
        if (d<30) {//有按到拉桿
          canPullDown = true;
        }
        //***********************
        //拉桿
        //***********************
        if (canPullDown) {
          if (mouseY>=390) {//拉到底
            //領獎燈滅&音效開
            sevenReward = false;
            getCoinSE = true;
            //換圖片
            imgPullDownShadow = loadImage("img/pullDownShadow3.png");
            imgPullDown = loadImage("img/pullDown_3.png");
            //音效
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
          } else if (mouseY>=300) {//拉到中間
            //換圖片
            imgPullDownShadow = loadImage("img/pullDownShadow2.png");
            imgPullDown = loadImage("img/pullDown_2.png");
            //音效
            if (SEplay2) {
              SEpullDown2.rewind();
              SEpullDown2.play();
              SEplay2 = false;
              SEplay3 = true;
            }
          } else {//拉到上面
            //換圖片
            imgPullDownShadow = loadImage("img/pullDownShadow1.png");
            imgPullDown = loadImage("img/pullDown_1.png");
            //開啟音效開關
            if (!SEplay2) {
              SEpullDown2.rewind();
              SEpullDown2.play();
              SEplay2 = true;
            }
          }
        }
        //**************************
        //投幣
        //**************************
        d = dist(mouseX, mouseY, coinHolePos.x, coinHolePos.y);//滑鼠和點的距離
        if (d<60) {
          //圖片
          if (canPushCoin) {
            if (mouseX-tmpX > 25) {//投幣成功
              //領獎燈滅&音效開
              sevenReward = false;
              getCoinSE = true;
              //音效
              imgCoinHole = loadImage("img/vCoinHole.png");
              if (SEcoinSecess) {
                if (sevenStart || nowCoin >= 3 || money<=0) {//777轉動中或投滿或沒錢
                  SEerror.rewind();
                  SEerror.play();
                } else {//金幣未投三個
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
        if (d<42 && d2<28) {//滑鼠在上面
          imgStop1 = loadImage("img/stopBtnClk.png");
          if (!sevenStart || sevenIni || sevenStop1) {//未開始或剛開始或已停止
            if (playSe) {
              SEerror.rewind();
              SEerror.play();
              playSe = false;
            }
          } else {//停止
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
        if (d<42 && d2<28) {//滑鼠在上面
          imgStop2 = loadImage("img/stopBtnClk.png");
          if (!sevenStart || sevenIni || sevenStop2) {//未開始或剛開始或已停止
            if (playSe2) {
              SEerror.rewind();
              SEerror.play();
              playSe2 = false;
            }
          } else {//停止
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
        if (d<42 && d2<28) {//滑鼠在上面
          imgStop3 = loadImage("img/stopBtnClk.png");
          if (!sevenStart || sevenIni || sevenStop3) {//未開始或剛開始或已停止
            if (playSe3) {
              SEerror.rewind();
              SEerror.play();
              playSe3 = false;
            }
          } else {//停止
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
      //投幣  錢幣跟著滑鼠走
      //**************************
      d = dist(mouseX, mouseY, coinHolePos.x, coinHolePos.y);//滑鼠和點的距離
      if (d<60) {
        //錢幣跟著滑鼠走
        image(imgCoin, mouseX-25, mouseY-29);
      }
      //*************************
      //777 STOP按鈕變色
      //*************************
      //stop1
      d = dist(mouseX, 0, stopBtnPos1.x, 0);//X
      d2 = dist(0, mouseY, 0, stopBtnPos1.y);//Y
      if (d<42 && d2<28) {//滑鼠在上面
        imgStop1 = loadImage("img/stopBtnOn.png");
      } else {
        imgStop1 = loadImage("img/stopBtn.png");
      }
      //stop2
      d = dist(mouseX, 0, stopBtnPos2.x, 0);//X
      d2 = dist(0, mouseY, 0, stopBtnPos2.y);//Y
      if (d<42 && d2<28) {//滑鼠在上面
        imgStop2 = loadImage("img/stopBtnOn.png");
      } else {
        imgStop2 = loadImage("img/stopBtn.png");
      }
      //stop3
      d = dist(mouseX, 0, stopBtnPos3.x, 0);//X
      d2 = dist(0, mouseY, 0, stopBtnPos3.y);//Y
      if (d<42 && d2<28) {//滑鼠在上面
        imgStop3 = loadImage("img/stopBtnOn.png");
      } else {
        imgStop3 = loadImage("img/stopBtn.png");
      }
    }
    //*****************
    //破產
    //*****************
    if(isBankrupt){
      //背景
      image(imgBankruptBg, 0, 0);
      image(imgBankrupt1, 175, 63);
      image(imgBankrupt2, 86, 240);
      image(imgYes,207,389);
      image(imgNo,379,386);
      //音樂
      if (bankruptSE) {
        SEloseCoin.rewind();
        SEloseCoin.play();
        bankruptSE = false;
      }
      //按鈕功能
      d = dist(mouseX, mouseY, 246, 416);//滑鼠和點的距離
      if(d < 40){
        println(d);
        if(yesNoSE1){
          SEyesNo.rewind();
          SEyesNo.play();
          yesNoSE1 = false;
        }
        if(mousePressed){//點下yes
          SEpullDown3.rewind();
          SEpullDown3.play();
          money = 300;
          bonus = 1.0;
          seeBonus =100;
          isBankrupt = false;
          sevenReward = false;//領獎燈滅
        }
        imgYes = loadImage("img/yesYello.png");
      }else{
        yesNoSE1 = true;
        imgYes = loadImage("img/yesGreen.png");
      }
      d = dist(mouseX, mouseY, 407, 414);//滑鼠和點的距離
      if(d < 40){
        if(yesNoSE2){
          SEyesNo.rewind();
          SEyesNo.play();
          yesNoSE2 = false;
        }
        if(mousePressed){//點下no
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
void mousePressed() {//只執行一次的按滑鼠
  //*****************
  //投幣   依拖曳程度投幣
  //*****************
  tmpX = mouseX;//取得按下去的座標
  canPushCoin = true;
  SEcoinSecess = true;
  println(mouseX+","+mouseY);
}
void mouseReleased() {//滑鼠放開
  //拉桿還原
  SEplay2 = false;
  canPullDown = false;
  imgPullDownShadow = loadImage("img/pullDownShadow1.png");//還原拉桿
  imgPullDown = loadImage("img/pullDown_1.png");//還原拉桿
  //投幣還原
  imgCoinHole = loadImage("img/vCoinHole.png");//還原圖片
  //777
  playSe = true;//stop音效左
  playSe2 = true;//中
  playSe3 = true;//右
}

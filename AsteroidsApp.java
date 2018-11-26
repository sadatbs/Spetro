package Asteroid_Game;

import javafx.animation.AnimationTimer;


import javafx.application.Application;

import javafx.event.EventHandler;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;

import javafx.stage.Stage;
import javafx.scene.text.Font;



import java.util.ArrayList;
import java.util.List;

import static javafx.geometry.Pos.CENTER;




public class AsteroidsApp extends Application {

    static int score=0;
    static int score2=3;
    static  double PlayerSpeed=1;
    static  double BulletSpeed=8;
    Scene scene1,scene2,scene3;
    boolean start=true;
    boolean items=true;
   // boolean bulletspawm=false;
    ImageView presskey;
    




    static int stop=0;
    Label label2,label3;
    private Pane root;
    MediaPlayer bgmediap ;
    StackPane layout4=new StackPane();
    VBox layout1= new VBox(80);
    StackPane layout3=new StackPane();
    Image icon ;
    ImageView bg;
    AnimationTimer timer;





    private List<GameObject> bullets = new ArrayList<>();
    private List<GameObject> enemies = new ArrayList<>();


    private GameObject player,item;


    private Parent createContent() {

        root = new Pane();
        root.setPrefSize(1530,800);
        Media background=new Media(getClass().getResource("bgvid2sd.mp4").toExternalForm());
        MediaPlayer play=new MediaPlayer(background);
        MediaView mv=new MediaView();
        mv.setMediaPlayer(play);
        play.play();
        play.setCycleCount(MediaPlayer.INDEFINITE);
        icon= new Image(getClass().getResourceAsStream("pressArow.png"));
        bg=new ImageView(icon);
        bg.setImage(icon);
        bg.setEffect(new Reflection());
        bg.setTranslateX(400);
        bg.setLayoutY(350);

        mv.setFitHeight(1530);
        mv.setFitWidth(1530);
        label2 = new Label();
        label2.setAlignment(Pos.CENTER_RIGHT);
        label2.setText(Integer.toString(score));
       /* label2.setPadding(new Insets(25, 30, 50, 25));*/
        label2.setPrefSize(200,200);
        label2.setTextFill(Color.web("#33FF54"));
        label2.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("Score.png"),100,100,true,true)));
        Font font = new Font(40);
        label2.setFont(font);
        label3 = new Label();
        label3.setAlignment(Pos.CENTER_RIGHT);
        label3.setTranslateX(80);
        label3.setText(Integer.toString(score2));
        label3.setPrefSize(500,500);
        label3.setTextFill(Color.web("#33FF54"));
        label3.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("Score.png"),100,100,true,true)));
        Font font1=new Font(30);
        label3.setFont(font1);



        root.getChildren().addAll(label2,mv,bg);
        root.getChildren().remove(label2);
        root.getChildren().add(label2);




        player = new Player();
        player.setVelocity(new Point2D(0, 0));
        addGameObject(player, 100, 300);


        timer = new AnimationTimer() {

            @Override
            public void handle(long now) {

                    label2.setText(Integer.toString(score));
                    score2=score;

                if(score>0&&score%5==0)
                {
                    item.getView().setTranslateX(20);
                    item.getView().setTranslateY(20);
                }

                    onUpdate();
                label3.setText( Integer.toString(score2));



            }
        };


            item = new Item();





        timer.start();








        return root;
    }

    private void addBullet(GameObject bullet, double x, double y) {
        bullets.add(bullet);
        addGameObject(bullet, x, y);
    }

    private void addEnemy(GameObject enemy, double x, double y) {
        enemies.add(enemy);
        addGameObject(enemy, x, y);

    }

    private void addGameObject(GameObject object, double x, double y) {
        object.getView().setTranslateX(x);
        object.getView().setTranslateY(y);
        root.getChildren().add(object.getView());
    }



    private void onUpdate() throws NullPointerException{

            if (player.getlocationx() > root.getPrefWidth() - 80 || player.getlocationx() < 0) {
                player.setAlive(false);
                player.getView().setEffect(new ImageInput(new Image(getClass().getResourceAsStream("Explosion.gif"), 60, 60, true, true)));
                if(start) {
                    presskey = new ImageView(new Image(getClass().getResourceAsStream("Restart.png")));
                    layout4.getChildren().add(presskey);
                }
            }

            if (player.getlocationy() > root.getPrefHeight() - 80 || player.getlocationy() < 0) {
                player.setAlive(false);
                player.getView().setEffect(new ImageInput(new Image(getClass().getResourceAsStream("Explosion.gif"), 60, 60, true, true)));
                if(start) {
                    presskey = new ImageView(new Image(getClass().getResourceAsStream("Restart.png")));
                    layout4.getChildren().add(presskey);
                }
            }
            for (GameObject enemy : enemies) {
                if (enemy.getlocationx() > root.getPrefWidth() || enemy.getlocationx() < 0) {
                    enemy.setAlive(false);
                    root.getChildren().remove(enemy.getView());
                }
                if (enemy.getlocationy() > root.getPrefHeight() || enemy.getlocationy() < 0) {
                    enemy.setAlive(false);
                    root.getChildren().remove(enemy.getView());
                }
                if (enemy.isColliding(player)) {
                    player.setAlive(false);
                    player.getView().setEffect(new ImageInput(new Image(getClass().getResourceAsStream("Explosion.gif"), 60, 60, true, true)));
                    if(start) {
                        presskey = new ImageView(new Image(getClass().getResourceAsStream("Restart.png")));
                        layout4.getChildren().add(presskey);
                    }
                    start = false;

                    //Media plyr_die = new Media("file:////F:/gamesounds/update/PlayerDie.mp3");
                    // MediaPlayer Plyr_die = new MediaPlayer(plyr_die);
                    //Plyr_die.play();


                    root.getChildren().remove(enemy.getView());
                }
                for (GameObject bullet : bullets) {
                    if (bullet.getlocationx() > root.getPrefWidth() - 80 || bullet.getlocationx() < 0) {
                        bullet.setAlive(false);
                        root.getChildren().remove(bullet.getView());
                    }
                    if (bullet.getlocationy() > root.getPrefHeight() - 80 || bullet.getlocationy() < 0) {
                        bullet.setAlive(false);
                        root.getChildren().remove(bullet.getView());
                    }
                    if (bullet.isColliding(enemy)) {
                        bullet.setAlive(false);
                        enemy.setAlive(false);
                        //enemy.diesound();

                        score += 1;
                        score2 =+1;
                        System.out.println(score);

                        root.getChildren().removeAll(bullet.getView(), enemy.getView());
                    }

                }
            }
            if (player.isColliding(item)) {
                Bullet bullet = new Bullet();

                addBullet(bullet, player.getView().getTranslateX(), player.getView().getTranslateY());
                bullet.getView().setEffect(new ImageInput(new Image(getClass().getResourceAsStream("Rasengan.png"), 40, 40, true, true)));
                //Media itemgain = new Media("file:////F:/gamesounds/update/3.ItemGain2.mp3");
                //MediaPlayer Itemgain = new MediaPlayer(itemgain);
                // Itemgain.play();
                //bullet.setVelocity(player.getVelocity().normalize().multiply(6));
                item.setAlive(false);
                item.isDead();
            }

            if (player.isDead()) {

                start = false;

            }

            bullets.removeIf(GameObject::isDead);
            enemies.removeIf(GameObject::isDead);


            bullets.forEach(GameObject::update);
            enemies.forEach(GameObject::update);

            if (start) {
                player.update();
                item.update();
            }


            if (Math.random() < 0.013 && Math.random() > 0) {

                if (start) {
                    /*addWarning(getBullet(),Math.random() + (player.getlocationx()+(Math.random()+200)),Math.random() + (player.getlocationy()+Math.random()+100));*/
                    addEnemy(new Enemy(), Math.random() + player.getlocationx() + (Randomextra() * 200), Math.random() + player.getlocationy() + (Randomextra() * 100));
                    //System.out.println(score);


                    if (items) {
                        addGameObject(item, 500, 200);
                        item.setVelocity(new Point2D(2, 1));
                        items = false;
                    }


                }
            }



    }
    public double Randomextra()
    {
        double y=0;
        if(Math.random()>0&&Math.random()<5)

        if(Math.random()>3)
        {
            y=1;
        }
        else if(Math.random()<3)
        {
           y=-1;
        }
        return y;


    }
    private static class Item extends GameObject{
        Item(){super(getItem());}
    }
    private static ImageView getItem()
    {
        Image iitem=new Image(Item.class.getResourceAsStream("Item1.gif"),80,81,true,true);
        ImageView Iitem=new ImageView(iitem);
        return Iitem;
}





    private static class Player extends GameObject {



        Player() {
            super(getshape());
        }


    }
    private static ImageView getshape() {

        Image imae=new Image(Player.class.getResourceAsStream("Player1.gif"),130,70,true,true);

        ImageView image = new ImageView(imae);
        image.setLayoutX(0);
        image.setLayoutY(0);
        return image;
    }

    private static class Enemy extends GameObject {
        Enemy() {
            super(getCircle());
        }
    }

    private static class Bullet extends GameObject {
        Bullet() {
            super(getBullet());
        }
    }
    private static ImageView getCircle()
    {
        Image circle=new Image(Enemy.class.getResourceAsStream("Enemy1.gif"),120,70,true,true);
        ImageView Circle=new ImageView(circle);
        //Circle.setFitWidth(100);
        //Circle.setFitHeight(60);
        Circle.setLayoutX(0);
        Circle.setLayoutY(0);
        return  Circle;

    }
    private static ImageView getBullet()
    {
        Image bullet=new Image(Enemy.class.getResourceAsStream("Bullet1.png"),55,40,true,true);
        ImageView Bullet=new ImageView(bullet);
        //Bullet.setEffect(new DropShadow() );
        Bullet.setLayoutX(0);
        Bullet.setLayoutY(0);
       // Bullet.setFitWidth(60);
        //Bullet.setFitHeight(44);
        return  Bullet;
    }


    @Override
    public void start(Stage stage) throws Exception {


        Media bgmedia = new Media(getClass().getResource("2.mp3").toExternalForm());
        bgmediap = new MediaPlayer(bgmedia);
        bgmediap.setCycleCount(MediaPlayer.INDEFINITE);
        bgmediap.setVolume(0.7);
        Media st_btn = new Media(getClass().getResource("1.FirstSceneStartButton.mp3").toExternalForm());
        MediaPlayer st_btn_play = new MediaPlayer(st_btn);
        //bgmediap.setAutoPlay(true);

       /* stage.setTitle("Asteroid Game");
        Image icon = new Image(getClass().getResourceAsStream("shuntu.png"));
        stage.getIcons().add(icon);
        Image image1=new Image(getClass().getResourceAsStream("bgvid2.gif"),1500,1200,true,true);
        ImageView Startscene=new ImageView(image1);*/

        //Startscene.setFitHeight(800);
        //Startscene.setFitWidth(800);
        Media background=new Media(getClass().getResource("xz6.mp4").toExternalForm());
        MediaPlayer play=new MediaPlayer(background);
        MediaView mv=new MediaView();
        mv.setTranslateX(50);
        mv.setTranslateY(50);
        mv.setMediaPlayer(play);
        //mv.getBoundsInParent();
        //mv.setFitHeight(1200) ;
        //mv.setFitWidth(1500);
        play.play();
        play.setCycleCount(MediaPlayer.INDEFINITE);

        Button button1 = new Button( );
        //button1.setGraphic(getshape());
        button1.setEffect(new ImageInput(new Image(getClass().getResourceAsStream("Launch.gif"),190,200,true,true)));

        //button1.setEffect(new Reflection());
        Button button2 = new Button();
        button2.setEffect(new ImageInput(new Image(getClass().getResourceAsStream("Exit2.gif"),190,150,true,true)));
        //button2.setEffect(new Reflection());
        Button button4 = new Button();
        button4.setEffect(new ImageInput(new Image(getClass().getResourceAsStream("Instructions.gif"),190,150,true,true)));
        button4.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Media instructions=new Media(getClass().getResource("Instructions.mp4").toExternalForm());
                MediaPlayer instructionsp=new MediaPlayer(instructions);
                MediaView imv=new MediaView(instructionsp);
                imv.setMediaPlayer(instructionsp);
                instructionsp.play();
                Button ins_button=new Button();
                ins_button.setEffect(new ImageInput(new Image(getClass().getResourceAsStream("BACK.gif"))));
                ins_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        layout4.getChildren().removeAll(imv,ins_button);
                        layout4.getChildren().addAll(mv,layout1);
                    }
                });
                layout4.getChildren().removeAll(mv,layout1);
                layout4.getChildren().addAll(imv,ins_button);
            }
        });
        //button2.setEffect(new Reflection());
        button1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                st_btn_play.play();
                bgmediap.play();
                layout4.getChildren().removeAll(mv,layout1);
                layout4.getChildren().add(createContent());
            }
        });
        button2.setOnMouseClicked(e-> stage.close());


        layout1.setAlignment(CENTER);
       // layout1.setTranslateX(700);
        //layout1.setTranslateY(350);
        layout1.getChildren().addAll(button1,button2);
        layout1.getChildren().removeAll(button1,button2);
        //layout1.getChildren().addAll(bg1);
        layout1.getChildren().addAll(button1,button2,button4);

        layout4.setPrefSize(1530,800);
        layout4.getBoundsInParent();
        //mv.setFitWidth(1200);
        //mv.setFitHeight(1200);
        layout4.getChildren().addAll(mv,layout1);
        scene1=new Scene(layout4);
        scene2=new Scene(createContent());
        //Image endscene=new Image(getClass().getResourceAsStream("end scene.png"));
        //ImageView Endscene=new ImageView(endscene);
        //Endscene.getBoundsInParent();
        //Endscene.setFitHeight(800);
        //Endscene.setPreserveRatio(true);
        Media endbackground=new Media(getClass().getResource("endscene.mp4").toExternalForm());
        MediaPlayer endscene=new MediaPlayer(endbackground);
        MediaView Endscene=new MediaView();
        Endscene.getBoundsInParent();
        Endscene.setMediaPlayer(endscene);
        endscene.play();
        endscene.setCycleCount(MediaPlayer.INDEFINITE);
        Button button3 = new Button("Exit");
        button3.setEffect(new ImageInput(new Image(getClass().getResourceAsStream("Exit2.gif"),190,150,true,true)));
        button3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                //endscene.stop();
                layout4.getChildren().remove(layout3);
                stage.close();
            }
        });
        VBox layout2=new VBox(50);
        layout2.setAlignment(CENTER);
        layout2.setTranslateY(-40);
        layout2.getChildren().addAll(button3);
        layout3.getChildren().addAll(Endscene,layout2);



        stage.setScene(scene1);
        stage.getScene().setOnKeyReleased(e -> {
            if(start) {

                if (e.getCode() == KeyCode.LEFT) {
                    player.rotateLeft(PlayerSpeed);
                    if(start)
                    {
                        root.getChildren().remove(bg);
                    }
                } else if (e.getCode() == KeyCode.RIGHT) {
                    player.rotateRight(PlayerSpeed);
                    if(start)
                    {
                        root.getChildren().remove(bg);
                    }
                } else if (e.getCode() == KeyCode.SPACE) {
                    if (start) {
                        Bullet bullet = new Bullet();
                        bullet.bulletsound();
                        bullet.setVelocity(player.getVelocity().normalize().multiply(BulletSpeed));
                        addBullet(bullet, player.getView().getTranslateX(), player.getView().getTranslateY());

                    }

                }
                else if(e.getCode()==KeyCode.UP)
                {
                    PlayerSpeed=PlayerSpeed+0.1;
                    player.setspeed(PlayerSpeed);
                    if(start)
                    {
                        root.getChildren().remove(bg);
                    }

                }
                else if(e.getCode()==KeyCode.DOWN)
                {
                    if(PlayerSpeed>0) {
                        PlayerSpeed = PlayerSpeed - 0.1;
                        player.setspeed(PlayerSpeed);
                    }
                    if(start)
                    {
                        root.getChildren().remove(bg);
                    }
                }
                else if(e.getCode()==KeyCode.R)
                {
                    PlayerSpeed=-PlayerSpeed;
                    player.setspeed(PlayerSpeed);
                }
                else if(e.getCode()==KeyCode.E)
                {
                    BulletSpeed=-BulletSpeed;
                }
            }
            else
            {
                timer.stop();
                if(e.getCode()== KeyCode.ESCAPE) {
                    layout4.getChildren().removeAll(root,bg,presskey);
                   // layout4.getChildren().remove(presskey);
                    layout4.getChildren().add(layout3);
                    bgmediap.stop();
                    //start=true;
                    //layout4.getChildren().addAll(createContent());

                }
                else if(e.getCode()== KeyCode.ENTER)
                {
                    layout4.getChildren().removeAll(root,bg,presskey);
                    start=true;
                    player.setAlive(true);
                    layout4.getChildren().addAll(createContent());
                    PlayerSpeed=1;
                    items=true;
                    score=0;
                }
            }

        });


        //scene3=new Scene(layout3,layout4.getPrefWidth(),layout4.getPrefHeight());

        stage.show();
        //stage.setScene(scene1);

    }

    public static void main(String[] args) {
        launch(args);


    }
 }

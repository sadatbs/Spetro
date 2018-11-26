package Asteroid_Game;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;

public class GameObject{




        private Node view;
        private Point2D velocity = new Point2D(Math.random(), Math.random());

        private boolean alive = true;

    public GameObject(Node view) {
            this.view = view;
        }

        public void update () {
            view.setTranslateX(view.getTranslateX() + velocity.getX());
            view.setTranslateY(view.getTranslateY() + velocity.getY());
        }

        public double getlocationx ()
        {
            return (view.getTranslateX() + velocity.getX());
        }
        public double getlocationy ()
        {
            return (view.getTranslateY() + velocity.getY());
        }


        public void setVelocity (Point2D velocity){
            this.velocity = velocity;
        }

        public Point2D getVelocity () {
            return velocity;
        }

        public Node getView () {
            return view;
        }


        public boolean isAlive () {
            return alive;
        }

        public boolean isDead () {
            return !alive;
        }

        public void setAlive ( boolean alive){
            this.alive = alive;
        }

        public double getRotate () {
            return view.getRotate();
        }

        public void rotateRight ( double x){
            view.setRotate(view.getRotate() + 30);
            setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))).normalize().multiply(x));
        }

        public void rotateLeft ( double x){
            view.setRotate(view.getRotate() - 30);
            setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))).normalize().multiply(x));
        }
        public void setspeed ( double x){
            view.setRotate(view.getRotate() + 0);
            setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))).normalize().multiply(x));
        }


        public boolean isColliding (GameObject other)throws NullPointerException {
            return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
        }

        public void bulletsound () {
            Media bulleteffect = new Media(getClass().getResource("shoot.mp3").toExternalForm());
            MediaPlayer audio = new MediaPlayer(bulleteffect);
            audio.play();
        }

        public void diesound () {
            Media diesound = new Media(getClass().getResource("shoot.mp3").toExternalForm());
            MediaPlayer audio = new MediaPlayer(diesound);
            audio.play();
        }

}

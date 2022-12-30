/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.engine;

import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.character.Monster;
import fr.ubx.poo.ubomb.go.character.Player;
import fr.ubx.poo.ubomb.go.decor.Decor;
import fr.ubx.poo.ubomb.go.decor.Princess;
import fr.ubx.poo.ubomb.view.*;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;


public final class GameEngine {

    private static AnimationTimer gameLoop;
    private final Game game;
    private final Player player;
    private final List<Sprite> sprites = new LinkedList<>();
    private final Set<Sprite> cleanUpSprites = new HashSet<>();

    private final List<Monster> monsterList = new LinkedList<>();
    private final Stage stage;
    private StatusBar statusBar;
    private Pane layer;
    private Input input;
    private long total = 0;
    private final Random random;

    public GameEngine(Game game, final Stage stage) {
        this.stage = stage;
        this.game = game;
        this.player = game.player();
        initialize();
        buildAndSetGameLoop();
        this.random = new Random();
    }

    private void initialize() {
        Group root = new Group();
        layer = new Pane();

        int height = game.grid().height();
        int width = game.grid().width();
        int sceneWidth = width * ImageResource.size;
        int sceneHeight = height * ImageResource.size;
        Scene scene = new Scene(root, sceneWidth, sceneHeight + StatusBar.height);
        scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());

        stage.setScene(scene);
        stage.setResizable(true);
        stage.sizeToScene();
        stage.hide();
        stage.show();

        input = new Input(scene);
        root.getChildren().add(layer);
        statusBar = new StatusBar(root, sceneWidth, sceneHeight, game);

        // Create sprites
        for (var decor : game.grid().values()) {
            sprites.add(SpriteFactory.create(layer, decor));
            decor.setModified(true);
        }
        for(Monster monster : game.getLevel().getMonsters().values()){
            sprites.add(SpriteFactory.create(layer, monster));
            monsterList.add(monster);
            monster.setModified(true);
        }
        sprites.add(new SpritePlayer(layer, player));
    }

    void buildAndSetGameLoop() {
        gameLoop = new AnimationTimer() {
            public void handle(long now) {

                // Check keyboard actions
                processInput(now);

                // Do actions
                update(now);
                updateMonsters();
                createNewBombs(now);
                checkCollision(now);
                checkExplosions();

                // Graphic update
                cleanupSprites();
                checkLevelChange();
                checkSpriteChanges();
                render();
                statusBar.update(game);
            }
        };
    }

    private void updateMonsters() {
        System.out.println("Upadte monsters");
        this.monsterList.forEach(monster -> {
            System.out.println(monster.toString());
            if(this.random.nextFloat() < 0.05){
                int dir;
                Direction direction;
                do{
                    dir = (int) (this.random.nextFloat()*4 % 4);
                    direction = switch (dir){
                        case 0-> Direction.RIGHT;
                        case 1 -> Direction.UP;
                        case 2-> Direction.LEFT;
                        case 3-> Direction.DOWN;
                        default -> throw new RuntimeException("Dir not between 0 and 4");
                    };
                } while(!monster.canMove(direction));
                monster.move(direction);

            }
        });
    }

    private void checkSpriteChanges() {
        sprites.forEach(sprite -> {
            if (sprite.getGameObject().spriteChanged()) {
                game.grid().remove(sprite.getPosition());
                cleanUpSprites.add(sprite);
            }
        });
        cleanUpSprites.forEach(sprite -> {
            if(sprite.getGameObject() instanceof Decor decor){
                sprites.add(SpriteFactory.create(layer, decor));
                decor.setModified(true);
                decor.changeSprite(false);
                game.grid().set(decor.getPosition(), decor);
            }
        });
        cleanUpSprites.forEach(Sprite::remove);
        sprites.removeAll(cleanUpSprites);
        cleanUpSprites.clear();
    }

    private void checkLevelChange() {
        if(this.game.checkLevelChange()){
            sprites.forEach(Sprite::remove);
            sprites.clear();
            this.game.getLevel().getElements().values().forEach(decor -> {
                sprites.add(SpriteFactory.create(layer, decor));
                decor.setModified(true);
            });
            this.game.getLevel().getMonsters().values().forEach(moster -> {
                sprites.add(SpriteFactory.create(layer, moster));
                moster.setModified(true);
            });
            this.monsterList.clear();
            sprites.add(new SpritePlayer(layer, player));
            this.game.getLevel().getMonsters().values().forEach(monster ->{
                monsterList.add(monster);
            });
        }
    }

    private void checkExplosions() {
        // Check explosions of bombs
    }

    private void animateExplosion(Position src, Position dst) {
        ImageView explosion = new ImageView(ImageResource.EXPLOSION.getImage());
        TranslateTransition tt = new TranslateTransition(Duration.millis(200), explosion);
        tt.setFromX(src.x() * Sprite.size);
        tt.setFromY(src.y() * Sprite.size);
        tt.setToX(dst.x() * Sprite.size);
        tt.setToY(dst.y() * Sprite.size);
        tt.setOnFinished(e -> {
            layer.getChildren().remove(explosion);
        });
        layer.getChildren().add(explosion);
        tt.play();
    }

    private void createNewBombs(long now) {
        // Create a new Bomb is needed
    }

    private void checkCollision(long now) {
        if(game.getLevel().getMonster(player.getPosition()) != null && now - game.player().getTime() > game.configuration().playerInvisibilityTime()*100000){
            System.out.println("now : " + now + " player: " + player.getTime() + " dif: " + (now -player.getTime()));
            player.damage();
            player.setTime(now);
        }
    }

    private void processInput(long now) {
        if (input.isExit()) {
            gameLoop.stop();
            Platform.exit();
            System.exit(0);
        } else if (input.isMoveDown()) {
            player.requestMove(Direction.DOWN);
        } else if (input.isMoveLeft()) {
            player.requestMove(Direction.LEFT);
        } else if (input.isMoveRight()) {
            player.requestMove(Direction.RIGHT);
        } else if (input.isMoveUp()) {
            player.requestMove(Direction.UP);
        } else if(input.isKey()){
            player.checkKey();
            System.out.println("ENTER pressed");
        }
        input.clear();
    }

    private void showMessage(String msg, Color color) {
        Text waitingForKey = new Text(msg);
        waitingForKey.setTextAlignment(TextAlignment.CENTER);
        waitingForKey.setFont(new Font(60));
        waitingForKey.setFill(color);
        StackPane root = new StackPane();
        root.getChildren().add(waitingForKey);
        Scene scene = new Scene(root, 400, 200, Color.WHITE);
        stage.setScene(scene);
        input = new Input(scene);
        stage.show();
        new AnimationTimer() {
            public void handle(long now) {
                processInput(now);
            }
        }.start();
    }


    private void update(long now) {
        player.update(now);

        if (player.getLives() == 0) {
            gameLoop.stop();
            showMessage("Perdu!", Color.RED);
        }
    }

    public void cleanupSprites() {
        sprites.forEach(sprite -> {
            if (sprite.getGameObject().isDeleted()) {
                game.grid().remove(sprite.getPosition());
                cleanUpSprites.add(sprite);
            }
        });
        cleanUpSprites.forEach(Sprite::remove);
        sprites.removeAll(cleanUpSprites);
        cleanUpSprites.clear();
    }

    private void render() {
        sprites.forEach(Sprite::render);
    }

    public void start() {
        gameLoop.start();
    }

}
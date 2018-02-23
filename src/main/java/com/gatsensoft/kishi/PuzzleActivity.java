package com.gatsensoft.kishi;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class PuzzleActivity extends Activity {

    int [][] routes = {{1,3}, {0,2,4}, {1,5}, {0,4,6}, {1,3,5,7}, {2,4,8}, {3,7,9}, {4,6,8,10}, {5,7,11}, {6,10}, {7,9,11}, {8,10}};
    ArrayList<Tile> tiles = new ArrayList();
    MediaPlayer swap_sound, solved_sound;
    Chronometer chronometer;
    boolean gameStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        for(int i = 0; i < 12; i++) {

            tiles.add(new Tile(i, i, routes[i], "tile"+(i+1), ContextCompat.getDrawable(this, getResources().getIdentifier("tile"+(i+1), "drawable", this.getPackageName())), (i == 11)? true : false));
        }

        swap_sound = MediaPlayer.create(this, R.raw.tile_switch);
        solved_sound = MediaPlayer.create(this, R.raw.ta_da);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
    }

    public  void tileClicked(View view) {

        if (gameStarted) {

            String sourceTileId = getResources().getResourceEntryName(view.getId());
            Tile sourceTile = getSourceTile(sourceTileId);
            Tile targetTile = getTargetTile();
            ArrayList sourceTileRoutes = new ArrayList();
            Spinner spnType = (Spinner) findViewById(R.id.spnType);

            int[] tempRoutes = sourceTile.getRoutes();
            for (int i = 0; i < tempRoutes.length; i++) {
                sourceTileRoutes.add(tempRoutes[i]);
            }

            if (spnType.getSelectedItem().toString().equals("Slide")) {
                if (sourceTileRoutes.contains(targetTile.getPosition())) {
                    swapTiles(sourceTile, targetTile);
                }
            } else {
                swapTiles(sourceTile, targetTile);
            }

            if (isSolved()) {
                Toast.makeText(this, "Congratulations you solved the puzzle in " + chronometer.getText().toString(), Toast.LENGTH_LONG).show();
                solved_sound.start();
                chronometer.stop();
                gameStarted = false;
            }
        }
    }

    public void shuffleTiles(View view) {

        Spinner spnDifficulty = (Spinner) findViewById(R.id.spnDifficulty);
        int difficulty = 2;
        chronometer.setBase(SystemClock.elapsedRealtime());

        switch (spnDifficulty.getSelectedItem().toString()) {

            case "Easy":
                difficulty = 6;
                break;
            case "Average":
                difficulty = 12;
                break;
            case "Hard":
                difficulty = 18;
                break;
        }

        int i = 1;
        Random rand = new Random();
        while (i <= difficulty) {

            int  j = rand.nextInt(tiles.size()) + 1;
            int  k = rand.nextInt(tiles.size()) + 1;

            if(j == k) {
                --i;
                continue;
            }

            swapTiles(tiles.get(j-1), tiles.get(k-1));
            i++;
        }
        chronometer.start();
        gameStarted = true;
    }

    public Tile getSourceTile(String sourceTileId) {

        Tile sourceTile = null;
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).getHost().equals(sourceTileId)) {
                sourceTile = tiles.get(i);
                break;
            }
        }
        return sourceTile;
    }

    public Tile getTargetTile() {

        Tile targetTile = null;
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).isTarget()) {
                targetTile = tiles.get(i);
                break;
            }
        }
        return targetTile;
    }

    public boolean isSolved() {

        boolean allHome = true;
        for (int i = 0; i < tiles.size(); i++) {
            if (!tiles.get(i).isHome()) {
                allHome = false;
                break;
            }
        }
        return allHome;
    }

    public void swapTiles(Tile sourceTile, Tile targetTile) {

        Tile tile;
        // switch host button backgrounds
        Button jButton = (Button) findViewById(getResources().getIdentifier(sourceTile.getHost(), "id", this.getPackageName()));
        Button kButton = (Button) findViewById(getResources().getIdentifier(targetTile.getHost(), "id", this.getPackageName()));
        Drawable background = sourceTile.getBackground();
        jButton.setBackground(targetTile.getBackground());
        kButton.setBackground(background);

        // update tile info
        tile = new Tile(sourceTile);
        sourceTile.setPosition(targetTile.getPosition());
        sourceTile.setHost(targetTile.getHost());
        sourceTile.setRoutes(targetTile.getRoutes());
        targetTile.setPosition(tile.getPosition());
        targetTile.setHost(tile.getHost());
        targetTile.setRoutes(tile.getRoutes());
        // switch tiles
        tile = new Tile(targetTile);
        tiles.set(sourceTile.getPosition(), sourceTile);
        tiles.set(tile.getPosition(), tile);
        // play swap sound
        swap_sound.start();
    }

    public class Tile {

        private int origin;
        private int position;
        private int [] routes;
        private String host;
        private Drawable background;
        private boolean isTarget;

        public Tile(int origin, int position, int[] routes, String host, Drawable background, boolean isTarget) {
            this.origin = origin;
            this.position = position;
            this.routes = routes;
            this.host = host;
            this.background = background;
            this.isTarget = isTarget;
        }

        public Tile(Tile tile) {
            this.origin = tile.origin;
            this.position = tile.position;
            this.routes = tile.routes;
            this.host = tile.host;
            this.background = tile.background;
            this.isTarget = tile.isTarget;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int[] getRoutes() {
            return routes;
        }

        public void setRoutes(int[] routes) {
            this.routes = routes;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public Drawable getBackground() {
            return background;
        }

        public boolean isHome() {
            return this.origin == this.position;
        }

        public boolean isTarget() {
            return this.isTarget;
        }
    }
}

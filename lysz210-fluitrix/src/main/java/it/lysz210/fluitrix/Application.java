package it.lysz210.fluitrix;

import processing.core.PApplet;

public class Application extends PApplet {
    @Override
    public void settings(){
        size(200, 200);
    }

    @Override
    public void draw(){
        background(0);
        ellipse(mouseX, mouseY, 20, 20);
    }

    public static void main(String... args){
        PApplet.main("proc.sketches.First");
    }
}

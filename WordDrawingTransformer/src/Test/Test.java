/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;

import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author edkjd
 */
public class Test {
    public static void main(String[] args) throws Exception {
        System.out.println(WordDrawingProcessor.
                WordDrawingProcessor.getOneChar(ImageIO.read(new File("a.png")), 'a')[0]+"/a");
        System.out.println(WordDrawingProcessor.
                WordDrawingProcessor.getOneChar(ImageIO.read(new File("b.png")), 'b')[0]+"/b");
        System.out.println(WordDrawingProcessor.
                WordDrawingProcessor.getOneChar(ImageIO.read(new File("c.png")), 'c')[0]+"/c");
        System.out.println(WordDrawingProcessor.
                WordDrawingProcessor.getOneChar(ImageIO.read(new File("e.png")), 'e')[0]+"/e");
        System.out.println(WordDrawingProcessor.
                WordDrawingProcessor.getOneChar(ImageIO.read(new File("f.png")), 'f')[0]+"/f");
        System.out.println(WordDrawingProcessor.
                WordDrawingProcessor.getOneChar(ImageIO.read(new File("g.png")), 'g')[0]+"/g");
        System.out.println(WordDrawingProcessor.
                WordDrawingProcessor.getOneChar(ImageIO.read(new File("s.png")), 's')[0]+"/s");
        System.out.println(WordDrawingProcessor.
                WordDrawingProcessor.getOneChar(ImageIO.read(new File("x.png")), 'x')[0]+"/x");
        System.out.println(WordDrawingProcessor.
                WordDrawingProcessor.getOneChar(ImageIO.read(new File("v.png")), 'v')[0]+"/v");
        System.out.println(WordDrawingProcessor.
                WordDrawingProcessor.getOneChar(ImageIO.read(new File("z.png")), 'z')[0]+"/z");
        System.out.println(WordDrawingProcessor.
                WordDrawingProcessor.getOneChar(ImageIO.read(new File("i.png")), 'i')[0]+"/i");
        System.out.println(WordDrawingProcessor.
                WordDrawingProcessor.getOneChar(ImageIO.read(new File("u.png")), 'u')[0]+"/u");
        System.out.println(WordDrawingProcessor.
                WordDrawingProcessor.getOneChar(ImageIO.read(new File("t.png")), 't')[0]+"/t");
        System.out.println(WordDrawingProcessor.
                WordDrawingProcessor.getOneChar(ImageIO.read(new File("h.png")), 'h')[0]+"/h");
        System.out.println(WordDrawingProcessor.
                WordDrawingProcessor.getOneChar(ImageIO.read(new File("m.png")), 'm')[0]+"/m");
        System.out.println(WordDrawingProcessor.
                WordDrawingProcessor.getOneChar(ImageIO.read(new File("w.png")), 'w')[0]+"/w");
        System.out.println(WordDrawingProcessor.
                WordDrawingProcessor.getOneChar(ImageIO.read(new File("o.png")), 'o')[0]+"/o");
        
    }
}

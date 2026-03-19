/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package WordDrawingProcessor;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 *
 * @author edkjd
 */
public class TextProcessr {

//    static final float basisRate = 10, minRate = 0.07f;
    static final int error = 1;

    public static String getText(BufferedImage image1) {
        BufferedImage image = new BufferedImage(image1.getWidth() * 5, image1.getHeight() * 5,
                BufferedImage.TYPE_3BYTE_BGR);

        image.getGraphics().drawImage(image1, 0, 0, image.getWidth(), image.getHeight(), null);

        TextData textData = getTextData(image);
        StringBuilder sb = new StringBuilder();
        for (int a = 0; a < textData.rects.size(); a++) {
            if (a > 1 && textData.rects.get(a - 1)[0]
                    > textData.rects.get(a)[0]) {
                sb.append("\r\n");
            } else if (a > 1) {
                int w = textData.rects.get(a)[0] - textData.rects.get(a - 1)[2];
                int h = textData.rects.get(a)[3] - textData.rects.get(a)[1];
                if (h / 5 <= w) {
                    sb.append(' ');
                }
            }
            sb.append(WordDrawingProcessor.getOneChar(textData, a)[0]);
        }

        return sb.toString();
    }

    public static TextData getTextData(BufferedImage image) {
        byte[][] drawing = new byte[image.getWidth()][image.getHeight()];
        for (int x = 0; x < drawing.length; x++) {
            for (int y = 0; y < drawing[x].length; y++) {
                Color cl = new Color(image.getRGB(x, y));
                int num = (cl.getRed() + cl.getGreen() + cl.getBlue()) / 3;
                drawing[x][y] = (byte) (num * 2 / 256);
            }
        }
        HashMap<Byte, Integer> colorCounts = new HashMap<Byte, Integer>();
        for (int x = 0; x < drawing.length; x++) {
            for (int y = 0; y < drawing[x].length; y++) {
                if (colorCounts.get(drawing[x][y]) == null) {
                    colorCounts.put(drawing[x][y], 0);
                }
                colorCounts.put(drawing[x][y], colorCounts.get(drawing[x][y]) + 1);
            }
        }
        int max = 0;
        byte firstColor = 0, secondColor = 0;
        for (byte color : colorCounts.keySet()) {
            if (colorCounts.get(color) > max) {
                max = colorCounts.get(color);
                firstColor = color;
            }
        }
        max = 0;
        colorCounts.remove((Byte) firstColor);
        for (byte color : colorCounts.keySet()) {
            if (colorCounts.get(color) > max) {
                max = colorCounts.get(color);
                secondColor = color;
            }
        }

        for (int x = 0; x < drawing.length; x++) {
            for (int y = 0; y < drawing[x].length; y++) {
                if (drawing[x][y] != secondColor) {
                    drawing[x][y] = 1;
                } else {
                    drawing[x][y] = 0;
                }
            }
        }
        TextData td = new TextData();
        td.image = drawing;

        int[] startPoint = WordDrawingProcessor.getStartPoint(drawing);
        startPoint[0]--;
        startPoint[1]--;
        int[] endPoint = WordDrawingProcessor.getEndPoint(drawing, startPoint);
        endPoint[0]++;
        endPoint[1]++;

        byte[][] drawing1 = new byte[drawing.length][];
        for (int x = 0; x < drawing1.length; x++) {
            drawing1[x] = drawing[x].clone();
        }
        int[] y = new int[2];
        while (getOneWord(drawing1, startPoint, endPoint, td, y)) {

        }

        return td;
    }

    public static boolean getOneWord(byte[][] drawing, int[] startPoint1,
            int[] endPoint1, TextData td, int[] yPos) {
        int[] startPoint = WordDrawingProcessor.getStartPoint(drawing);
        if (startPoint[0] == 0
                && startPoint[1] == 0) {
            return false;
        }
        int[] endPoint = WordDrawingProcessor.getEndPoint(drawing, startPoint);
        startPoint[0]--;
        startPoint[1]--;
        endPoint[0]++;
        endPoint[1]++;
        boolean test = false;
        if (yPos[1] != 0) {
            if (Math.abs(yPos[0] - startPoint[1]) < (yPos[1] - yPos[0]) * 2 / 3) {
                test = true;
            }
        }

        int sx = -1, sy = -1, ex = -1, ey = -1;
        if (test == false) {
            for (int y = startPoint[1]; y < endPoint[1]; y++) {
                float select = 0;
                for (int x = startPoint[0]; x < endPoint[0]; x++) {
                    if (drawing[x][y] == 0) {
                        select++;
                    }
                }
                if (select < error) {

                    sy = y;
                    break;
                }
            }

            if (sy == -1) {
                sy = startPoint[1];
            }

            for (int y = sy + 5; y < endPoint[1]; y++) {
                float select = 0;
                for (int x = startPoint[0]; x < endPoint[0]; x++) {
                    if (drawing[x][y] == 0) {
                        select++;
                    }
                }
                if (select < error) {
                    ey = y;
                    break;
                }
            }

            if (ey == -1) {
                ey = endPoint[1];
            }
            if (yPos[0] == 0 && yPos[1] == 0) {
                yPos[0] = sy;
                yPos[1] = ey;
            }
        } else {
            sy = yPos[0];
            ey = yPos[1];
        }
        for (int x = startPoint[0]; x < endPoint[0]; x++) {
            float select = 0;
            for (int y = sy; y < ey; y++) {
                if (drawing[x][y] == 0) {
                    select++;
                }
            }
            float select1 = 0;
            for (int y = sy; y < ey; y++) {
                if (x + 1 < drawing.length && drawing[x + 1][y] == 0) {
                    select1++;
                }
            }
            if (select < error && select1 >= error) {
                sx = x;
                break;
            }
        }

        if (sx == -1) {
            sx = startPoint[0];
        }

        for (int x = sx + 1; x < endPoint[0]; x++) {
            float select = 0;
            for (int y = sy; y < ey; y++) {
                if (drawing[x][y] == 0) {
                    select++;
                }
            }
            if (select < error) {
                ex = x;
                break;
            }
        }

        if (ex == -1) {
            ex = endPoint[0];
        }

        int select = 0;
        for (int x = startPoint1[0]; x < endPoint1[0]; x++) {
            for (int y = startPoint1[1]; y < endPoint1[1]; y++) {
                if (drawing[x][y] == 0) {
                    select++;
                }
            }
        }
        if (select == 0) {
            return false;
        }
        td.rects.add(new int[]{sx, sy, ex, ey});
        for (int x = 0; x <= ex + 5; x++) {
            for (int y = 0; y <= ey + 5; y++) {
                if (drawing.length <= x
                        || drawing[x].length <= y) {
                    continue;
                }
                drawing[x][y] = 1;
            }
        }
        return true;
    }
}

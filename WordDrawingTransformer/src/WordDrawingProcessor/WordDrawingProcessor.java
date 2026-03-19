/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package WordDrawingProcessor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

/**
 *
 * @author edkjd
 */
public class WordDrawingProcessor {

    public static byte[][][] alphabetDrawing, numberDrawing, bigAlphabetDrawing;//, koreanDrawing;
//    public static final int basisSize = 5;

    static {
        alphabetDrawing = getAlphabetDrawing();
        bigAlphabetDrawing = getBigAlphabetDrawing();
        numberDrawing = getNumberDrawing();
//        koreanDrawing = getKoreanDrawing();
    }

    public static int[] getStartPoint(byte[][] drawing) {
        int x1 = 0, y1 = 0;
        loop:
        for (int y = 0; y < drawing[0].length; y++) {
            for (int x = 0; x < drawing.length; x++) {
                if (drawing[x][y] == 0) {
                    y1 = y;
                    break loop;
                }
            }
        }
        loop1:
        for (int x = 0; x < drawing.length; x++) {
            for (int y = 0; y < drawing[0].length; y++) {
                if (drawing[x][y] == 0) {
                    x1 = x;
                    break loop1;
                }
            }
        }
        return new int[]{x1, y1};
    }

    public static int[] getEndPoint(byte[][] drawing, int[] startPoint) {
        int x1 = 0, y1 = 0;
        loop:
        for (int y = drawing[0].length - 1; y >= 0; y--) {
            for (int x = 0; x < drawing.length; x++) {
                if (drawing[x][y] == 0) {
                    y1 = y;
                    break loop;
                }
            }
        }
        loop1:
        for (int x = drawing.length - 1; x >= 0; x--) {
            for (int y = 0; y < drawing[0].length; y++) {
                if (drawing[x][y] == 0) {
                    x1 = x;
                    break loop1;
                }
            }
        }
        return new int[]{x1, y1};
    }

//    public static int[] getEndPoint(byte[][] drawing, int[] startPoint) {
//        int count = 0;
//        for (int a = 4;; a++) {
//            if (startPoint[1] + a >= drawing[0].length - 1) {
//                return new int[]{startPoint[0] + a, startPoint[1] + a};
//            }
//            for (int y = startPoint[1]; y < startPoint[1] + a; y++) {
//                if (drawing[startPoint[0] + a][y] == 1) {
//                    count++;
//                }
//            }
//            if (startPoint[0] + a >= drawing.length - 1) {
//                return new int[]{startPoint[0] + a, startPoint[1] + a};
//            }
//            for (int x = startPoint[0]; x < startPoint[0] + a; x++) {
//                if (drawing[x][startPoint[1] + a] == 1) {
//                    count++;
//                }
//            }
//            if (count >= a + a) {
//                return new int[]{startPoint[0] + a, startPoint[1] + a};
//            }
//            count = 0;
//        }
//    }
//    public static int[] getEndPoint(byte[][] drawing, int[] startPoint) {
//        float aft = 0, bef = -Float.MAX_VALUE;
//        for (int a = 4;; a++) {
//            for (int y = startPoint[1]; y < startPoint[1] + a; y++) {
//                if (startPoint[1] + a > drawing[0].length) {
//                    return null;
//                }
//                for (int x = startPoint[0]; x < startPoint[0] + a; x++) {
//                    if (startPoint[0] + a > drawing.length) {
//                        return null;
//                    }
//                    if (drawing[x][y] == 1) {
//                        aft++;
//                    }
//                }
//            }
//            aft /= (a * a);
//            if (bef > aft) {
//                return new int[]{startPoint[0] + a, startPoint[1] + a};
//            }
//            bef = aft;
//            aft = 0;
//        }
//    }
    public static byte[][] getDrawing(BufferedImage image) {
        byte[][] drawing = new byte[image.getWidth()][image.getHeight()];
        for (int x = 0; x < drawing.length; x++) {
            for (int y = 0; y < drawing[x].length; y++) {
                Color cl = new Color(image.getRGB(x, y));
                drawing[x][y] = cl.getRed() + cl.getGreen() + cl.getBlue() >= 255 * 3 ? (byte) 1 : (byte) 0;
            }
        }
        return drawing;
    }

    public static byte[][][] getBigAlphabetDrawing() {
        byte[][][] alphabetDrawing = new byte['Z' - 'A' + 1][][];
        for (int a = 'A'; a <= 'Z'; a++) {
            alphabetDrawing[a - 'A'] = getCharDrawing((char) a);
            int[] startPoint = getStartPoint(alphabetDrawing[a - 'A']);
            int[] endPoint = getEndPoint(alphabetDrawing[a - 'A'], startPoint);
            alphabetDrawing[a - 'A'] = getPart(alphabetDrawing[a - 'A'], startPoint, endPoint);
        }
        return alphabetDrawing;
    }

    public static byte[][][] getAlphabetDrawing() {
        byte[][][] alphabetDrawing = new byte['z' - 'a' + 1][][];
        for (int a = 'a'; a <= 'z'; a++) {
            alphabetDrawing[a - 'a'] = getCharDrawing((char) a);
            int[] startPoint = getStartPoint(alphabetDrawing[a - 'a']);
            int[] endPoint = getEndPoint(alphabetDrawing[a - 'a'], startPoint);
            alphabetDrawing[a - 'a'] = getPart(alphabetDrawing[a - 'a'], startPoint, endPoint);
        }
        return alphabetDrawing;
    }

    public static byte[][][] getNumberDrawing() {
        byte[][][] numberDrawing = new byte['9' - '0' + 1][][];
        for (int a = '0'; a <= '9'; a++) {
            numberDrawing[a - '0'] = getCharDrawing((char) a);
            int[] startPoint = getStartPoint(numberDrawing[a - '0']);
            int[] endPoint = getEndPoint(numberDrawing[a - '0'], startPoint);
            numberDrawing[a - '0'] = getPart(numberDrawing[a - '0'], startPoint, endPoint);
        }
        return numberDrawing;
    }

    public static byte[][][] getKoreanDrawing() {
        byte[][][] koreanDrawing = new byte['힣' - '가' + 1][][];
        for (int a = '가'; a <= '힣'; a++) {
            koreanDrawing[a - '가'] = getCharDrawing((char) (a));
            int[] startPoint = getStartPoint(koreanDrawing[a - '가']);
            int[] endPoint = getEndPoint(koreanDrawing[a - '가'], startPoint);
            koreanDrawing[a - '가'] = getPart(koreanDrawing[a - '가'], startPoint, endPoint);
        }
        return koreanDrawing;
    }

    public static byte[][] getCharDrawing(char ch) {
        BufferedImage bim = new BufferedImage(50, 50, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = (Graphics2D) bim.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 50, 50);
        g.setColor(Color.BLACK);
        g.setFont(new Font("", Font.BOLD, 40));
        Rectangle2D bounds = g.getFontMetrics().getStringBounds("" + ch, g);
        g.drawString("" + ch, 0, (int) (50 - bounds.getMaxY()));
        return getDrawing(bim);
    }

    public static void print(byte[][] bytes) {
        for (int y = 0; y < bytes[0].length; y++) {
            for (int x = 0; x < bytes.length; x++) {
                System.out.print(bytes[x][y] + " ");
            }
            System.out.println();
        }
    }

    public static float score(byte[][] line1, byte[][] line2, int n1, int n2) {
//        if(line1[0][n1]!=line2[0][n2]){
//            return 0;
//        }
        int count1 = 0;
        boolean test1 = true;
        for (int a = 0; a < line1.length; a++) {
            if (line1[a][n1] == 1) {
                test1 = true;
            }
            if (test1 == false) {
                continue;
            }
            if (line1[a][n1] == 0) {
                count1++;
                test1 = false;
            }
        }
        int count2 = 0;
        boolean test2 = true;
        for (int a = 0; a < line2.length; a++) {
            if (line2[a][n2] == 1) {
                test2 = true;
            }
            if (test2 == false) {
                continue;
            }
            if (line2[a][n2] == 0) {
                count2++;
                test2 = false;
            }
        }
        int score = Math.abs(count1 - count2);
        if (score < 1) {
            return 1;
        }
        return 0f;
    }

    public static float getScore3(byte[][] left, byte[][] right, int size, boolean compress) {
        left = reverse(left);
        right = reverse(right);
        left = getFitDrawing(left, size);
        right = getFitDrawing(right, size);
        if (compress) {
            left = getCompressedDrawing(left);
            right = getCompressedDrawing(right);
        }
//        print(left);
//        System.out.println();
//        print(right);
//        System.out.println();
        int num = 0;
        float score = 0, temp;
        for (int a = 0; a < left[0].length; a++) {
            for (int b = num; b < right[0].length; b++) {
//                if (num >= right[0].length) {
//                    break;
//                }
                if ((temp = score(left, right, a, b)) > 0) {
                    num = b + 1;
                    score += temp;
                    break;
                }
            }
        }
        float score1 = score / left[0].length;
        num = 0;
        score = 0;
        for (int a = 0; a < right[0].length; a++) {
            for (int b = num; b < left[0].length; b++) {
                if ((temp = score(right, left, a, b)) > 0) {
                    num = b + 1;
                    score += temp;
                    break;
                }
            }
        }
        float score2 = score / right[0].length;
        return (score1 + score2) / 2;
//        return score2 > score1 ? score2 : score1;
    }

    public static float getScore2(byte[][] left, byte[][] right, int size, boolean compress) {
        left = getFitDrawing(left, size);
        right = getFitDrawing(right, size);
        if (compress) {
            left = getCompressedDrawing(left);
            right = getCompressedDrawing(right);
        }
//        print(left);
//        System.out.println();
//        print(right);
//        System.out.println();
        int num = 0;
        float score = 0, temp;
        for (int a = 0; a < left[0].length; a++) {
            for (int b = num; b < right[0].length; b++) {
//                if (num >= right[0].length) {
//                    break;
//                }
                if ((temp = score(left, right, a, b)) > 0) {
                    num = b + 1;
                    score += temp;
                    break;
                }
            }
        }
        float score1 = score / left[0].length;
        num = 0;
        score = 0;
        for (int a = 0; a < right[0].length; a++) {
            for (int b = num; b < left[0].length; b++) {
                if ((temp = score(right, left, a, b)) > 0) {
                    num = b + 1;
                    score += temp;
                    break;
                }
            }
        }
        float score2 = score / right[0].length;
        return (score1 + score2) / 2;
//        return score2 > score1 ? score2 : score1;
    }

    public static float getScore(byte[][] basis, byte[][] sample, int rate) {
        float score4 = getScore2(basis, sample, 5 * rate, true);
        float score5 = getScore3(basis, sample, 5 * rate, true);
//        if (score4 > 0.7 && score5 > 0.7) {
        float score2 = getScore2(basis, sample, 5 * rate, false);
        float score3 = getScore3(basis, sample, 5 * rate, false);
        float score1 = getScore4(basis, sample, 5 * rate);
        return (score2 + score3 + score1 * 3 + score4 + score5) * 100 / 7;
//        }
//        return 0;
    }

    public static float getScore4(byte[][] basis, byte[][] sample, int size) {
        basis = getFitDrawing(basis, size);
        sample = getFitDrawing(sample, size);
        float score = 0;
        float total = 0;
        for (int x = 0; x < basis.length
                && x < sample.length; x++) {
            for (int y = 0; y < basis[x].length
                    && y < sample[x].length; y++) {
                if (basis[x][y] == sample[x][y]) {
                    score++;
                }
                total++;
            }
        }
        return score / total;
    }

    public static float getScore1(byte[][] basis, byte[][] sample) {
        basis = getFitDrawing(basis, 15);
        sample = getFitDrawing(sample, 15);
        float score = 0;
        float total = 0;
        for (int x = 0; x < basis.length
                && x < sample.length; x++) {
            for (int y = 0; y < basis[x].length
                    && y < sample[x].length; y++) {
                if (basis[x][y] == 1
                        && 0 == sample[x][y]) {
                } else {
                    score++;
                }
                total++;
            }
        }
        return score / total;
    }

    public static BufferedImage getImage(byte[][] drawing) {
        BufferedImage bim = new BufferedImage(drawing.length, drawing[0].length, BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = bim.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, bim.getWidth(), bim.getHeight());
        g.setColor(Color.BLACK);
        for (int x = 0; x < drawing.length; x++) {
            for (int y = 0; y < drawing[x].length; y++) {
                if (drawing[x][y] == 0) {
                    g.fillRect(x, y, 1, 1);
                }
            }
        }
        return bim;
    }

    public static byte[][] getFitDrawing(byte[][] drawing, int basisSize) {
//        BufferedImage bim = new BufferedImage(drawing.length, drawing[0].length, BufferedImage.TYPE_3BYTE_BGR);
//        Graphics g = bim.getGraphics();
//        g.setColor(Color.WHITE);
//        g.fillRect(0, 0, bim.getWidth(), bim.getHeight());
//        g.setColor(Color.BLACK);
//        for (int x = 0; x < drawing.length; x++) {
//            for (int y = 0; y < drawing[x].length; y++) {
//                if (drawing[x][y] == 0) {
//                    g.fillRect(x, y, 1, 1);
//                }
//            }
//        }

        byte[][] fitDrawing = new byte[basisSize][basisSize];
        float xRate = drawing.length / (float) basisSize;
        float yRate = drawing[0].length / (float) basisSize;
        for (int x = 0; x < basisSize; x++) {
            for (int y = 0; y < basisSize; y++) {
                fitDrawing[x][y] = 1;
                loop:
                for (int xx = 0; xx < xRate; xx++) {
                    for (int yy = 0; yy < yRate; yy++) {
                        if (drawing[(int) (x * xRate + xx)][(int) (y * yRate + yy)] == 0) {
                            fitDrawing[x][y] = 0;
                            break loop;
                        }
                    }
                }
            }
        }

        return fitDrawing;
    }

    public static byte[][] getPart(byte[][] image, int[] start, int[] end) {
        byte[][] part = new byte[end[0] - start[0]][end[1] - start[1]];
        for (int x = 0; x < part.length; x++) {
            for (int y = 0; y < part[x].length; y++) {
                part[x][y] = image[x + start[0]][y + start[1]];
            }
        }
        return part;
    }

//    public static BufferedImage getSmallImage(BufferedImage bim1) {
//        BufferedImage bim = new BufferedImage(basisSize, basisSize, BufferedImage.TYPE_3BYTE_BGR);
//        Graphics g = bim.getGraphics();
//        g.drawImage(bim1, 0, 0, basisSize, basisSize, null);
//        return bim;
//    }
    public static byte[][] getFitDrawing1(byte[][] drawing, int basisSize) {
        BufferedImage bim = new BufferedImage(drawing.length, drawing[0].length, BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = bim.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, bim.getWidth(), bim.getHeight());
        g.setColor(Color.BLACK);
        for (int x = 0; x < drawing.length; x++) {
            for (int y = 0; y < drawing[x].length; y++) {
                if (drawing[x][y] == 0) {
                    g.fillRect(x, y, 1, 1);
                }
            }
        }
        bim = getSmallImage(bim, basisSize);
        return getDrawing(bim);
    }

    public static BufferedImage getSmallImage(BufferedImage bim1, int basisSize) {
        int w = basisSize;
        int h = basisSize;
        BufferedImage bim = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = bim.getGraphics();
        g.drawImage(bim1, 0, 0, w, h, null);
        return bim;
    }

    public static byte[][] getCompressedDrawing(byte[][] drawing) {
        drawing = reverse(drawing);
//        drawing = getRightDeletedDrawing(drawing);
//        drawing = getLeftDeletedDrawing(drawing);
        drawing = getXCompressedDrawing(drawing);
        drawing = reverse(drawing);
        return drawing;
    }

    public static byte[][] reverse(byte[][] drawing) {
        byte[][] revDrawing = new byte[drawing[0].length][drawing.length];
        for (int x = 0; x < drawing.length; x++) {
            for (int y = 0; y < drawing[x].length; y++) {
                revDrawing[y][x] = drawing[x][y];
            }
        }
        return revDrawing;
    }

    public static byte[][] getRightDeletedDrawing(byte[][] drawing) {
        for (int x = drawing.length - 1; x >= 0; x--) {
            boolean test = false;
            for (int y = 0; y < drawing[x].length; y++) {
                if (drawing[x][y] == 0) {
                    test = true;
                    break;
                }
            }
            if (test == false) {
                for (int x1 = drawing.length - 1; x1 >= x; x1--) {
                    if (x1 - 1 <= 0
                            || drawing[x1 - 2] == null) {
                        drawing[x1 - 1] = null;
                        break;
                    }
                    drawing[x1] = drawing[x1 - 1];
                }
                x++;
            } else {
                break;
            }
        }
        int count = 0;
        for (int x = drawing.length - 1; x >= 0; x--) {
            if (drawing[x] != null) {
                count++;
            } else {
                break;
            }
        }
        byte[][] emptyDeletedDrawing = new byte[count][];
        for (int a = emptyDeletedDrawing.length - 1; a >= 0; a--) {
            emptyDeletedDrawing[a] = drawing[a];
        }
        return emptyDeletedDrawing;
    }

    public static byte[][] getLeftDeletedDrawing(byte[][] drawing) {
        for (int x = 0; x < drawing.length; x++) {
            boolean test = false;
            for (int y = 0; y < drawing[x].length; y++) {
                if (drawing[x][y] == 0) {
                    test = true;
                    break;
                }
            }
            if (test == false) {
                for (int x1 = x; x1 < drawing.length - 1; x1++) {
                    if (x1 + 1 >= drawing.length - 1
                            || drawing[x1 + 2] == null) {
                        drawing[x1 + 1] = null;
                        break;
                    }
                    drawing[x1] = drawing[x1 + 1];
                }
                x--;
            } else {
                break;
            }
        }
        int count = 0;
        for (int x = 0; x < drawing.length; x++) {
            if (drawing[x] != null) {
                count++;
            } else {
                break;
            }
        }
        byte[][] emptyDeletedDrawing = new byte[count][];
        for (int a = 0; a < emptyDeletedDrawing.length; a++) {
            emptyDeletedDrawing[a] = drawing[a];
        }
        return emptyDeletedDrawing;
    }

    public static float test(byte[] line1, byte[] line2) {
//        if(line1[0][n1]!=line2[0][n2]){
//            return 0;
//        }
        int count1 = 0;
        boolean test1 = true;
        for (int a = 0; a < line1.length; a++) {
            if (line1[a] == 1) {
                test1 = true;
            }
            if (test1 == false) {
                continue;
            }
            if (line1[a] == 0) {
                count1++;
                test1 = false;
            }
        }
        int count2 = 0;
        boolean test2 = true;
        for (int a = 0; a < line2.length; a++) {
            if (line2[a] == 1) {
                test2 = true;
            }
            if (test2 == false) {
                continue;
            }
            if (line2[a] == 0) {
                count2++;
                test2 = false;
            }
        }
        int score = Math.abs(count1 - count2);
        if (score < 1) {
            return 1;
        }
        return 0f;
    }

    public static byte[][] getXCompressedDrawing(byte[][] drawing) {
        for (int x = 0; x < drawing.length - 1; x++) {
//            int count = 0, count1 = 0;
//            int start, start1;
            if (drawing[x + 1] != null) {
//                start = drawing[x][0];
//                for (int y = 0; y < drawing[0].length - 1; y++) {
//                    if (drawing[x][y] != drawing[x][y + 1]) {
//                        count++;
//                    }
//                }
//                start1 = drawing[x + 1][0];
//                for (int y = 0; y < drawing[0].length - 1; y++) {
//                    if (drawing[x + 1][y] != drawing[x + 1][y + 1]) {
//                        count1++;
//                    }
//                }

                if (test(drawing[x], drawing[x + 1]) > 0) {
                    for (int x1 = x; x1 < drawing.length - 1; x1++) {
                        if (x1 + 1 >= drawing.length - 1
                                || drawing[x1 + 2] == null) {
                            drawing[x1 + 1] = null;
                            break;
                        }
                        drawing[x1] = drawing[x1 + 1];
                    }
                    x--;
                }
            } else {
                break;
            }
        }

        int count = 0;
        for (int x = 0; x < drawing.length; x++) {
            if (drawing[x] != null) {
                count++;
            } else {
                break;
            }
        }
        byte[][] xCompressedDrawing = new byte[count][];
        for (int a = 0; a < xCompressedDrawing.length; a++) {
            xCompressedDrawing[a] = drawing[a];
        }
        return xCompressedDrawing;
    }

    public static byte[][] getXCompressedDrawing1(byte[][] drawing) {
        for (int x = 0; x < drawing.length - 1; x++) {
            boolean test = false;
            if (drawing[x + 1] != null) {
                for (int y = 0; y < drawing[x].length; y++) {
                    if (drawing[x][y] != drawing[x + 1][y]) {
                        test = true;
                        break;
                    }
                }
                if (test == false) {
                    for (int x1 = x; x1 < drawing.length - 1; x1++) {
                        if (x1 + 1 >= drawing.length - 1
                                || drawing[x1 + 2] == null) {
                            drawing[x1 + 1] = null;
                            break;
                        }
                        drawing[x1] = drawing[x1 + 1];
                    }
                    x--;
                }
            } else {
                break;
            }
        }
        int count = 0;
        for (int x = 0; x < drawing.length; x++) {
            if (drawing[x] != null) {
                count++;
            } else {
                break;
            }
        }
        byte[][] xCompressedDrawing = new byte[count][];
        for (int a = 0; a < xCompressedDrawing.length; a++) {
            xCompressedDrawing[a] = drawing[a];
        }
        return xCompressedDrawing;
    }

    public static Object[] getOneChar(BufferedImage bim, char ch1) {
        byte[][] smallDrawing = getDrawing(bim);
        int[] startPoint = getStartPoint(smallDrawing);
        int[] endPoint = getEndPoint(smallDrawing, startPoint);
        smallDrawing = getPart(smallDrawing, startPoint, endPoint);

        float score = 0, maxScore = 0;
        char ch = ' ';
        for (int a = 0; a < alphabetDrawing.length; a++) {

            score = getScore(alphabetDrawing[a], smallDrawing, 3);
            if (maxScore <= score) {
                maxScore = score;
                ch = (char) ('a' + a);
            }
        }
        for (int a = 0; a < numberDrawing.length; a++) {

            score = getScore(numberDrawing[a], smallDrawing, 5);
            if (maxScore <= score) {
                maxScore = score;
                ch = (char) ('0' + a);
            }
        }
        for (int a = 0; a < bigAlphabetDrawing.length; a++) {

            score = getScore(bigAlphabetDrawing[a], smallDrawing, 5);
            if (maxScore <= score) {
                maxScore = score;
                ch = (char) ('A' + a);
            }
        }
//        for (int a = 0; a < koreanDrawing.length; a++) {
//
//            score = getScore(koreanDrawing[a], smallDrawing, 3);
//            System.out.println((char) ( '가' +  a)+"  "+score+" "+a/((float)koreanDrawing.length));
//            if (maxScore <= score) {
//                maxScore = score;
//                ch = (char) ( '가' +  a);
//            }
//        }
        return new Object[]{ch, maxScore};
    }

    public static Object[] getOneChar(TextData td, int num) {
        int w, h;
        w = td.rects.get(num)[2] - td.rects.get(num)[0];
        h = td.rects.get(num)[3] - td.rects.get(num)[1];
        if(w==0 || h==0){
            return new Object[]{' ', 0};
        }
        byte[][] smallDrawing = new byte[w][h];
        for (int x = td.rects.get(num)[0]; x < td.rects.get(num)[2]; x++) {
            for (int y = td.rects.get(num)[1]; y < td.rects.get(num)[3]; y++) {
                smallDrawing[x - td.rects.get(num)[0]][y - td.rects.get(num)[1]] = td.image[x][y];
            }
        }

        int[] startPoint = getStartPoint(smallDrawing);
        int[] endPoint = getEndPoint(smallDrawing, startPoint);
        if(startPoint[0]-endPoint[0]==0 || startPoint[1]-endPoint[1]==0){
            return new Object[]{' ', 0};
        }
        smallDrawing = getPart(smallDrawing, startPoint, endPoint);

        float score = 0, maxScore = 0;
        char ch = ' ';
        for (int a = 0; a < alphabetDrawing.length; a++) {

            score = getScore(alphabetDrawing[a], smallDrawing, 3);
            if (maxScore <= score) {
                maxScore = score;
                ch = (char) ('a' + a);
            }
        }
        for (int a = 0; a < numberDrawing.length; a++) {

            score = getScore(numberDrawing[a], smallDrawing, 5);
            if (maxScore <= score) {
                maxScore = score;
                ch = (char) ('0' + a);
            }
        }
        for (int a = 0; a < bigAlphabetDrawing.length; a++) {

            score = getScore(bigAlphabetDrawing[a], smallDrawing, 5);
            if (maxScore <= score) {
                maxScore = score;
                ch = (char) ('A' + a);
            }
        }

        return new Object[]{ch, maxScore};
    }
}

package com.example.macroproject.commands.opencv;

import com.example.macroproject.commands.Command;
import com.example.macroproject.commands.CommandFunction;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.*;
import java.awt.image.*;
import java.io.File;

import static org.opencv.core.CvType.CV_32FC1;

public class OpenCVHelper {
    public static Mat matify(BufferedImage sourceImg) {

        DataBuffer dataBuffer = sourceImg.getRaster().getDataBuffer();
        byte[] imgPixels = null;
        Mat imgMat = null;

        int width = sourceImg.getWidth();
        int height = sourceImg.getHeight();

        if(dataBuffer instanceof DataBufferByte) {
            imgPixels = ((DataBufferByte)dataBuffer).getData();
        }

        if(dataBuffer instanceof DataBufferInt) {

            int byteSize = width * height;
            imgPixels = new byte[byteSize*3];

            int[] imgIntegerPixels = ((DataBufferInt)dataBuffer).getData();

            for(int p = 0; p < byteSize; p++) {
                imgPixels[p*3 + 2] = (byte) ((imgIntegerPixels[p] & 0x00FF0000) >> 16);
                imgPixels[p*3 + 1] = (byte) ((imgIntegerPixels[p] & 0x0000FF00) >> 8);
                imgPixels[p*3 + 0] = (byte) (imgIntegerPixels[p] & 0x000000FF);
            }
        }

        if(imgPixels != null) {
            imgMat = new Mat(height, width, CvType.CV_8UC3);
            imgMat.put(0, 0, imgPixels);
        }

        return imgMat;
    }

    public static double[] templateMatch(Mat background, Mat template, double threshhold, boolean showImage) {
        boolean hasFoundImage = false;
        int match_method = Imgproc.TM_SQDIFF_NORMED;

        int result_cols = background.cols() - template.cols() + 1;
        int result_rows = background.rows() - template.rows() + 1;
        Mat result = new Mat();
        result.create(result_rows, result_cols, CV_32FC1);

        Imgproc.matchTemplate(background, template, result, match_method);
//            Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1, new Mat());

        Core.MinMaxLocResult mmr = Core.minMaxLoc(result);
        org.opencv.core.Point matchLoc;
        if (match_method == Imgproc.TM_SQDIFF
                || match_method == Imgproc.TM_SQDIFF_NORMED) {
            matchLoc = mmr.minLoc;
        } else {
            matchLoc = mmr.maxLoc;
        }

        if (1 - mmr.minVal > threshhold) {
            hasFoundImage = true;
            if (showImage) {
                Imgproc.rectangle(background, matchLoc, new Point(matchLoc.x + template.cols(),
                        matchLoc.y + template.rows()), new Scalar(0, 255, 0));
            }
        }

        if (showImage) {

            HighGui.imshow("Template Match", background);
            HighGui.waitKey(1);
        }

        if (hasFoundImage) {
            return new double[]{matchLoc.x, matchLoc.y, matchLoc.x + template.cols(), matchLoc.y + template.rows()};
        } else {
            return null;
        }

    }

    public static Rectangle getFullScreenRectangle() {
        return new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
    }

    public static Mat openImage(String imagePath) {
        File f = new File(imagePath);
        if (!f.exists()) {
            throw new IllegalArgumentException("Could not find image");
        }
        return Imgcodecs.imread(imagePath);
    }

    public static void writeImage(String imageName, Mat image) {
        Imgcodecs.imwrite(imageName, image);
    }
}

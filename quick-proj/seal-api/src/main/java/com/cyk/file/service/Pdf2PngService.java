package com.cyk.file.service;

import com.itextpdf.text.pdf.PdfReader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


@Service
public class Pdf2PngService {

    private Logger logger = LoggerFactory.getLogger(Pdf2PngService.class);

    /**
     * 全部转换： 适用于页数小于10页的
     */
    public List<byte[]> convertPdfToPngByPdfbox(String pdfPath) {
        File file = new File(pdfPath);
        PDDocument pdDocument;
        try {
            pdDocument = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(pdDocument);
            /* dpi越大转换后越清晰，相对转换速度越慢 */
            PdfReader reader = new PdfReader(pdfPath);
            int pages = reader.getNumberOfPages();
            List<byte[]> imgList = new ArrayList<>();
            for (int i = 0; i < pages; i++) {
                try (
                        OutputStream out = new ByteArrayOutputStream();
                ) {
//                    BufferedImage image = renderer.renderImageWithDPI(i, dpi);
                    BufferedImage image = renderer.renderImage(i);
                    ImageIO.write(image, "png", out);
                    byte[] ecByts = ((ByteArrayOutputStream) out).toByteArray();
                    imgList.add(ecByts);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            return imgList;
        } catch (IOException e) {
            logger.error("转换出错", e);
        }
        return null;
    }

    /**
     * 根据指定页码转换
     */
    public BufferedImage convertPdfToPngNum2Num(String pdfPath, int pageNo) {
        File file = new File(pdfPath);
        PDDocument pdDocument;
        try (
                OutputStream out = new ByteArrayOutputStream();
        ) {
            pdDocument = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(pdDocument);
            /* dpi越大转换后越清晰，相对转换速度越慢 */
            PdfReader reader = new PdfReader(pdfPath);
            int pages = reader.getNumberOfPages();
            if (pages < pageNo || pageNo < 1) {
                throw new IllegalArgumentException("起始页码超过最出文档范围");
            }
            return renderer.renderImage(pageNo - 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

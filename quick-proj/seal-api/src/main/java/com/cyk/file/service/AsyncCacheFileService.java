package com.cyk.file.service;


import com.cyk.common.ex.OptFailException;
import com.cyk.common.global.LocalCacheRepo;
import com.cyk.file.rr.CacheImg;
import com.itextpdf.text.pdf.PdfReader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;


/**
 * 异步处理文件缓存的服务
 * @author WangChengyu
 * 2020/6/5 10:52
 */
@Service
public class AsyncCacheFileService {

    Logger logger = LoggerFactory.getLogger(AsyncCacheFileService.class);

    @Async
    public void cachePdfimgToLocalCache(String uuid, String fp){
        logger.info("文件[{}:{}],开始转换图片", uuid, fp);
        File file = new File(fp);
        PDDocument pdDocument = null;
        try {
            PdfReader pdfReader = new PdfReader(fp);
            int pages = pdfReader.getNumberOfPages();
            pdDocument = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(pdDocument);
            /* dpi越大转换后越清晰，相对转换速度越慢 */
            for (int i = 0; i < pages; i++) {
                int pageNo = i + 1;
                try (
                        OutputStream out = new ByteArrayOutputStream();
                ) {
//                    BufferedImage image = renderer.renderImageWithDPI(i, dpi);
                    BufferedImage image = renderer.renderImage(i);
                    int width = image.getWidth();
                    int height = image.getHeight();
                    ImageIO.write(image, "png", out);
                    byte[] ecByts = ((ByteArrayOutputStream) out).toByteArray();
                    String cacheKey = uuid + "-no." + pageNo;
                    CacheImg eaImg = new CacheImg(cacheKey, pageNo, width, height, ecByts);
                    if(null == LocalCacheRepo.getCache(cacheKey)){
                        LocalCacheRepo.putCache(cacheKey, eaImg, 10*60);
                        logger.info(String.format("文件[%s]: 缓存图片参数{no=%s, w=%s, h=%s}", uuid, pageNo, width, height ));
                    }else{
                        logger.info(String.format("文件[%s]: 缓存图片参数{no=%s, w=%s, h=%s} >> had done and skip", uuid, pageNo, width, height ));
                    }

                } catch (Exception e) {
                    throw new OptFailException("第"+pageNo+"页转换失败");
                }
            }
        } catch (Exception e) {
            logger.error("转换出错", e);
        }finally {
            if(null != pdDocument){
                try {
                    pdDocument.close();
                }catch (Exception e){}
            }
        }
        logger.info("文件[{}:{}],转换图片完毕", uuid, fp);
    }



}

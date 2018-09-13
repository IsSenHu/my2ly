package com.husen.controller;

import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.husen.base.CommonResponse;
import com.husen.dao.po.IconPo;
import com.husen.dao.vo.DatatablesView;
import com.husen.exception.ParamException;
import com.husen.service.IconService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by HuSen on 2018/8/16 14:53.
 */
@RestController
public class IconController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(IconController.class);
    private final IconService iconService;
    private final FastFileStorageClient fastFileStorageClient;

    @Autowired
    public IconController(IconService iconService, FastFileStorageClient fastFileStorageClient) {
        this.iconService = iconService;
        this.fastFileStorageClient = fastFileStorageClient;
    }

    @GetMapping("/icons")
    private ModelAndView icons() {
        return new ModelAndView("pages/tables/icons");
    }

    @PostMapping("/saveIcon")
    private CommonResponse<IconPo> saveIcon(@Valid IconPo iconPo, BindingResult result, MultipartFile imgFile) throws ParamException {
        log.info("上传的图标为:[{}]", iconPo);
        if(result.hasErrors()) {
            throw new ParamException(result.getFieldErrors());
        }
        if(iconService.ifExisted(iconPo.getName())) {
            return commonResponse(iconPo, Constant.EXISTED);
        }
        try {
            String originalFilename = imgFile.getOriginalFilename();
            log.info("上传文件[{}]大小为:[{}]", originalFilename, imgFile.getSize());
            String path = fastFileStorageClient
                    .uploadFile(null, imgFile.getInputStream(), imgFile.getSize(), originalFilename.substring(originalFilename.lastIndexOf("."))).getFullPath();
            log.info("图标:[{}]上传成功，路径为:[{}]", iconPo.getName(), path);
            iconPo.setPath(path);
            iconService.saveIcon(iconPo);
        } catch (Exception e) {
            log.error("上传图标失败:[{}]", e.getMessage());
            e.printStackTrace();
        }
        return commonResponse(iconPo, Constant.SUCCESS);
    }

    @PostMapping("/pageIcon")
    private DatatablesView<IconPo> pageIcon(HttpServletRequest request, IconPo iconPo) {
        Map<String, String[]> map = request.getParameterMap();
        return iconService.pageIcon(map, iconPo);
    }

    @PostMapping("/deleteIconById")
    private CommonResponse<Integer> deleteIconById(Integer iconId) {
        return iconService.deleteIconById(iconId);
    }

    @GetMapping("/getAllIcons")
    private List<IconPo> getAllIcons() {
        return iconService.getAllIcons();
    }
}

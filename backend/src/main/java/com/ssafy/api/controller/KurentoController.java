package com.ssafy.api.controller;

import com.ssafy.api.response.KurentoChromaGetRes;
import com.ssafy.api.response.KurentoChromaPostRes;
import com.ssafy.api.service.KurentoService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Kurento;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "Kurento API", tags = {"Kurento"})
@RestController
@RequestMapping("/api/v1/")
public class KurentoController {

    @Autowired
    KurentoService kurentoService;

    @Autowired
    UserService userService;

    @PostMapping("kurentos")
    @ApiOperation(value = "사진 등록", notes = "Kurento 크로마 배경 사진을 등록한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 404, message = "이미지나 사용자가 없음"),
    })
    public ResponseEntity<? extends KurentoChromaPostRes> registImage(
            @RequestParam("image") MultipartFile image, @RequestParam("userId") String userId) {
        Kurento chromaImage = new Kurento();
        try {
            String[] imageInfo = saveImage(image).split(",");
            String imageUrl = imageInfo[0];
            String imageName = imageInfo[1];
            chromaImage = kurentoService.registImage(imageUrl, imageName, userId);
            return ResponseEntity.status(201).body(KurentoChromaPostRes.of(201, "success.", chromaImage));
        } catch (IOException e) {
            return ResponseEntity.status(404).body(KurentoChromaPostRes.of(404, "이미지나 사용자가 없음", chromaImage));
        }
    }

    @GetMapping("kurentos")
    @ApiOperation(value = "사진 가져오기", notes = "해당 user에 등록 된 Kurento 크로마 배경 사진을 가져온다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "사용자가 없음"),
    })
    public ResponseEntity<? extends KurentoChromaGetRes> getImage(
            @RequestParam("userId") String userId) {
        List<Kurento> chromaImage = new ArrayList<>();
        try {
            chromaImage = kurentoService.getImages(userId);
            return ResponseEntity.status(200).body(KurentoChromaGetRes.of(201, "success.", chromaImage));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(KurentoChromaGetRes.of(404, "이미지나 사용자가 없음", chromaImage));
        }
    }

    @DeleteMapping("kurentos")
    @ApiOperation(value = "사진 삭제하기", notes = "해당 user에 등록 된 Kurento 크로마 배경 사진을 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "사용자가 없음"),
    })
    public ResponseEntity<? extends BaseResponseBody> deleteImage(
            @RequestParam("userId") String userId, @RequestParam("imageName") String imageName) {
        try {
            kurentoService.deleteImage(userId, imageName);
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success."));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(BaseResponseBody.of(404, "이미지나 사용자가 없음"));
        }
    }


    private String saveImage(MultipartFile chroma) throws IOException {
        String path = "kurento/";
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();

        String url = "";
        String imageName = "";
        if (chroma != null) {
            imageName = chroma.getOriginalFilename();
            String originalFileExtension = imageName.substring(chroma.getOriginalFilename().lastIndexOf("."));
            String new_file_name = Long.toString(System.nanoTime()) + originalFileExtension;

            url = "kurento" + File.separator + new_file_name;
            Path pathabs = Paths.get(url).toAbsolutePath();
            chroma.transferTo(pathabs.toFile());
        }
        return url + "," + imageName;
    }
}

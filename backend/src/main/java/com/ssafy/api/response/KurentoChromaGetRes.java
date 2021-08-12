package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Kurento;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Chroma 등록 API ([Post] /kurentos) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("KurentoChromaGetRes")
public class KurentoChromaGetRes extends BaseResponseBody {
    @ApiModelProperty(name = "Chroma Image")
    List<Kurento> kurento = new ArrayList<>();

    public static KurentoChromaGetRes of(Integer statusCode, String message, List<Kurento> kurento) {
        KurentoChromaGetRes res = new KurentoChromaGetRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setKurento(kurento);
        return res;
    }
}

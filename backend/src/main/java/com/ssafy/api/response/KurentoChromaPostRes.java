package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Kurento;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Chroma 등록 API ([Post] /kurentos) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("KurentoChromaPostRes")
public class KurentoChromaPostRes extends BaseResponseBody {
    @ApiModelProperty(name = "Chroma Image")
    Kurento kurento = new Kurento();

    public static KurentoChromaPostRes of(Integer statusCode, String message, Kurento kurento) {
        KurentoChromaPostRes res = new KurentoChromaPostRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setKurento(kurento);
        return res;
    }
}

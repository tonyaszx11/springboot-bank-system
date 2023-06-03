package com.bank.bank_system.common;

import lombok.Data;

/* Result類會封裝回應，然後回傳給瀏覽器
    屬性:
        code:根據業務層處理的結果，如果成功會設定為Code.SUCCESS(1)，失敗會設定為Code.ERROR(0)
        data:業務層處理後的結果資料
        msg:提示訊息

*/
@Data
public class Result {
    private Integer code;
    private Object data;
    private String msg;
    public Result() {
    }

    public Result(Integer code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }
}

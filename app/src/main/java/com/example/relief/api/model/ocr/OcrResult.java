package com.example.relief.api.model.ocr;

import androidx.annotation.NonNull;
import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;
import java.util.List;

public class OcrResult {

    @JSONField(name = "log_id")
    private BigInteger logId;

    @JSONField(name = "words_result")
    private List<WordResult> wordsResult;

    @JSONField(name = "words_result_num")
    private BigInteger wordsResultNum;

    public OcrResult() {
    }

    public BigInteger getLogId() {
        return logId;
    }

    public void setLogId(BigInteger logId) {
        this.logId = logId;
    }

    public void setWordsResult(List<WordResult> wordsResult) {
        this.wordsResult = wordsResult;
    }

    public List<WordResult> getWordsResult() {
        return this.wordsResult;
    }

    public BigInteger getWordsResultNum() {
        return this.wordsResultNum;
    }

    public void setWordsResultNum(BigInteger wordsResultNum) {
        this.wordsResultNum = wordsResultNum;
    }

    @NonNull

    @Override
    public String toString() {
        return "OcrResult{" + "logId=" + logId + ", wordsResult=" + wordsResult
                + ", wordsResultNum=" + wordsResultNum + '}';
    }
}

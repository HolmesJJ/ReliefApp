package com.example.relief.api.model.ocr;

import androidx.annotation.NonNull;

import java.math.BigInteger;
import java.util.List;

public class OcrResult {

    private BigInteger logLd;
    private List<WordResult> wordsResult;
    private BigInteger wordsResultNum;

    public OcrResult() {
    }

    public BigInteger getLogLd() {
        return logLd;
    }

    public void setLogLd(BigInteger logLd) {
        this.logLd = logLd;
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
        return "OcrResult{" + "logLd=" + logLd + ", wordsResult=" + wordsResult
                + ", wordsResultNum=" + wordsResultNum + '}';
    }
}

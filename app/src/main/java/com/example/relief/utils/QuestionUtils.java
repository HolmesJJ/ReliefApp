package com.example.relief.utils;

import android.content.Context;
import android.content.res.AssetManager;
import com.example.relief.model.Question;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public final class QuestionUtils {

    private QuestionUtils() {
    }

    public static List<Question> getDefaultMenus() {
        return new Gson().fromJson(getDefaultMenuJson(ContextUtils.getContext()), new TypeToken<List<Question>>() {
        }.getType());
    }

    public static String getDefaultMenuJson(Context context) {
        return getFileJson("phq.json", context);
    }

    public static String getFileJson(String fileName, Context context) {
        // 将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            // 获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            // 通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}

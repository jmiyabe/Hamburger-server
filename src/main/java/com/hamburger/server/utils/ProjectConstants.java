package com.hamburger.server.utils;

import java.nio.file.FileSystems;

public class ProjectConstants {

    public static final String path  = "/src/main/java/com/hamburger/server/";
    public static final String utils = "utils/";
    public static final String jsonIngredients = "Ingredients.json";
    public static final String fullPathToFile = getAbsolutePath() + path + utils + jsonIngredients;

    public static final  String getAbsolutePath(){
       return FileSystems.getDefault().getPath("").toAbsolutePath().toString();
    }

}

package com.sparkleside.preferences;

import android.content.Context;
import androidx.appcompat.app.AppCompatDelegate;
import com.sparkleside.BuildConfig;

/*
 * Basic class to manage preferences.
 * TODO: use DataStore instead SharedPreferences.
 *
 * @author Aquiles Trindade (trindadedev).
 * @author Syntaxspin (SyntaxSpins).
 */

public class Preferences {
  /*
   * Preferences related with theme
   */
  public static class Theme {
    private static final String THEME_PREFERENCE = BuildConfig.APPLICATION_ID + ".theme_prefs";
    private static final String THEME_MODE_KEY = "THEME_MODE";
    private static final String THEME_MONET_KEY = "THEME_MONET";
    private static final String THEME_AMOLED_KEY = "THEME_AMOLED";

    public static int getThemeMode(Context ctx) {
      var sp = ctx.getSharedPreferences(THEME_PREFERENCE, Context.MODE_PRIVATE);
      return sp.getInt(THEME_MODE_KEY, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    public static boolean isMonetEnable(Context ctx) {
      var sp = ctx.getSharedPreferences(THEME_PREFERENCE, Context.MODE_PRIVATE);
      return sp.getBoolean(THEME_MONET_KEY, false);
    }

    public static void setThemeMode(Context ctx, int themeMode) {
      AppCompatDelegate.setDefaultNightMode(themeMode);
      var sp = ctx.getSharedPreferences(THEME_PREFERENCE, Context.MODE_PRIVATE);
      var spEditor = sp.edit();
      spEditor.putInt(THEME_MODE_KEY, themeMode);
      spEditor.apply();
    }

    public static void setMonetEnable(Context ctx, boolean isMonetEnable) {
      var sp = ctx.getSharedPreferences(THEME_PREFERENCE, Context.MODE_PRIVATE);
      var spEditor = sp.edit();
      spEditor.putBoolean(THEME_MONET_KEY, isMonetEnable);
      spEditor.apply();
    }

    /* Amoled Related Settings*/
    public static boolean isAmoledEnable(Context ctx) {
      var sp = ctx.getSharedPreferences(THEME_PREFERENCE, Context.MODE_PRIVATE);
      return sp.getBoolean(THEME_AMOLED_KEY, false);
    }

    public static void setAmoledEnable(Context ctx, boolean isAmoledEnable) {
      var sp = ctx.getSharedPreferences(THEME_PREFERENCE, Context.MODE_PRIVATE);
      var spEditor = sp.edit();
      spEditor.putBoolean(THEME_AMOLED_KEY, isAmoledEnable);
      spEditor.apply();
    }
  }

  public static class Editor {
    private static final String THEME_PREFERENCE = BuildConfig.APPLICATION_ID + ".editor_prefs";
    private static final String EDITOR_WORD_WRAP_KEY = "EDITOR_WORD_WRAP";
    private static final String EDITOR_SHOW_FIRST_LINE = "EDITOR_SHOW_FIRST_LINE";
    private static final String EDITOR_SHOW_LINE = "EDITOR_SHOW_LINE";
    private static final String EDITOR_USE_OVERSCROLL = "EDITOR_USE_OVERSCROLL";
    private static final String EDITOR_SHOW_TOOLBAR = "EDITOR_SHOW_TOOLBAR";
    private static final String EDITOR_THEME = "EDITOR_THEME";

    public static void setThemeMode(Context ctx, int editorThemeMode) {
      var sp = ctx.getSharedPreferences(THEME_PREFERENCE, Context.MODE_PRIVATE);
      var spEditor = sp.edit();
      spEditor.putInt(EDITOR_THEME, editorThemeMode);
      spEditor.apply();
    }

    public static int getEditorThemeMode(Context ctx) {
      var sp = ctx.getSharedPreferences(THEME_PREFERENCE, Context.MODE_PRIVATE);
      return sp.getInt(EDITOR_THEME, 0);
    }

    public static void setWordWrapEnable(Context ctx, boolean isWordWrapEnable) {
      var sp = ctx.getSharedPreferences(THEME_PREFERENCE, Context.MODE_PRIVATE);
      var spEditor = sp.edit();
      spEditor.putBoolean(EDITOR_WORD_WRAP_KEY, isWordWrapEnable);
      spEditor.apply();
    }

    public static boolean isWordWrapEnabled(Context ctx) {
      var sp = ctx.getSharedPreferences(THEME_PREFERENCE, Context.MODE_PRIVATE);
      return sp.getBoolean(EDITOR_WORD_WRAP_KEY, false);
    }

    public static void setShowFirstLineEnable(Context ctx, boolean isWordWrapEnable) {
      var sp = ctx.getSharedPreferences(THEME_PREFERENCE, Context.MODE_PRIVATE);
      var spEditor = sp.edit();
      spEditor.putBoolean(EDITOR_SHOW_FIRST_LINE, isWordWrapEnable);
      spEditor.apply();
    }

    public static void setShowLineEnable(Context ctx, boolean isWordWrapEnable) {
      var sp = ctx.getSharedPreferences(THEME_PREFERENCE, Context.MODE_PRIVATE);
      var spEditor = sp.edit();
      spEditor.putBoolean(EDITOR_SHOW_LINE, isWordWrapEnable);
      spEditor.apply();
    }

    public static boolean isShowFirstLineEnable(Context ctx) {
      var sp = ctx.getSharedPreferences(THEME_PREFERENCE, Context.MODE_PRIVATE);
      return sp.getBoolean(EDITOR_SHOW_FIRST_LINE, false);
    }

    public static boolean isShowLineEnable(Context ctx) {
      var sp = ctx.getSharedPreferences(THEME_PREFERENCE, Context.MODE_PRIVATE);
      return sp.getBoolean(EDITOR_SHOW_LINE, false);
    }

    public static void setUseOverscrollEnable(Context ctx, boolean isWordWrapEnable) {
      var sp = ctx.getSharedPreferences(THEME_PREFERENCE, Context.MODE_PRIVATE);
      var spEditor = sp.edit();
      spEditor.putBoolean(EDITOR_USE_OVERSCROLL, isWordWrapEnable);
      spEditor.apply();
    }

    public static boolean isUseOverscrollEnabled(Context ctx) {
      var sp = ctx.getSharedPreferences(THEME_PREFERENCE, Context.MODE_PRIVATE);
      return sp.getBoolean(EDITOR_USE_OVERSCROLL, false);
    }

    public static void setShowToolbarEnable(Context ctx, boolean isWordWrapEnable) {
      var sp = ctx.getSharedPreferences(THEME_PREFERENCE, Context.MODE_PRIVATE);
      var spEditor = sp.edit();
      spEditor.putBoolean(EDITOR_SHOW_TOOLBAR, isWordWrapEnable);
      spEditor.apply();
    }

    public static boolean isShowToolbarEnabled(Context ctx) {
      var sp = ctx.getSharedPreferences(THEME_PREFERENCE, Context.MODE_PRIVATE);
      return sp.getBoolean(EDITOR_SHOW_TOOLBAR, false);
    }
  }
}

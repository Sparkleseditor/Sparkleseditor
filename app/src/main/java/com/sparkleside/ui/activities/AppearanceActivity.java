package com.sparkleside.ui.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDelegate;
import com.google.android.material.snackbar.Snackbar;
import com.sparkleside.R;
import com.sparkleside.databinding.ActivityAppearanceBinding;
import com.sparkleside.preferences.Preferences;
import com.sparkleside.ui.base.BaseActivity;
import dev.trindadedev.fastui.preferences.withicon.PreferenceSwitch;

/*
 * Appearance Activity of Sparkles.
 * @author Syntaxspin (SyntaxSpins)
 */

public class AppearanceActivity extends BaseActivity {

  private ActivityAppearanceBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    binding = ActivityAppearanceBinding.inflate(getLayoutInflater());
    configureTransitions(R.id.coordinator);
    super.onCreate(savedInstanceState);

    setContentView(binding.getRoot());
    int theme = AppCompatDelegate.getDefaultNightMode();

    switch (theme) {
      case AppCompatDelegate.MODE_NIGHT_YES -> binding.linear5.check(R.id.materialbutton2);
      case AppCompatDelegate.MODE_NIGHT_NO -> binding.linear5.check(R.id.materialbutton1);
      case AppCompatDelegate.MODE_NIGHT_UNSPECIFIED -> binding.linear5.check(R.id.materialbutton3);
      case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM ->
          binding.linear5.check(R.id.materialbutton3);
    }

    setSupportActionBar(binding.toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
    binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

    binding.materialbutton1.setOnClickListener(
        v -> {
          Preferences.Theme.setThemeMode(this, AppCompatDelegate.MODE_NIGHT_NO);
          binding.linear5.check(R.id.materialbutton1);
        });

    binding.materialbutton2.setOnClickListener(
        v -> {
          Preferences.Theme.setThemeMode(this, AppCompatDelegate.MODE_NIGHT_YES);
          binding.linear5.check(R.id.materialbutton2);
        });

    binding.materialbutton3.setOnClickListener(
        v -> {
          Preferences.Theme.setThemeMode(this, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
          binding.linear5.check(R.id.materialbutton3);
        });

    binding.linear1.addView(getMonetPreference());
    binding.linear1.addView(getAmoledPreference());
  }

  @Override
  public void onResume() {
    super.onResume();
    configureTransitions(R.id.coordinator);
  }

  private PreferenceSwitch getMonetPreference() {
    PreferenceSwitch pref = new PreferenceSwitch(this);
    pref.setIcon(R.drawable.ic_pallete);
    pref.setTitle(getString(R.string.monet_title));
    pref.setDescription(getString(R.string.monet_desc));
    pref.setValue(Preferences.Theme.isMonetEnable(this));
    pref.setBackgroundType(0);
    pref.setEnabled(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S);
    pref.setSwitchChangedListener(
        (c, isChecked) -> {
          Preferences.Theme.setMonetEnable(this, isChecked);
          askForRestart();
        });
    return pref;
  }

  private PreferenceSwitch getAmoledPreference() {
    PreferenceSwitch pref = new PreferenceSwitch(this);
    pref.setIcon(R.drawable.ic_pallete);
    pref.setTitle(getString(R.string.amoled_title));
    pref.setDescription(getString(R.string.amoled_desc));
    pref.setValue(Preferences.Theme.isAmoledEnable(this));
    pref.setBackgroundType(2);
    pref.setSwitchChangedListener(
        (c, isChecked) -> {
          Preferences.Theme.setAmoledEnable(this, isChecked);
          askForRestart();
        });
    return pref;
  }

  private void askForRestart() {
    Snackbar.make(
            binding.linear1,
            "To Apply Changes Restart the app",
            com.google.android.material.snackbar.Snackbar.LENGTH_SHORT)
        .setAction(
            "Restart",
            v -> {
              Intent intent = new Intent(this, MainActivity.class);
              intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
              startActivity(intent);
              System.exit(0);
            })
        .show();
  }
}

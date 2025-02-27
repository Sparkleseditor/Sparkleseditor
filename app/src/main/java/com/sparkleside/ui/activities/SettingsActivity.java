package com.sparkleside.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.sparkleside.R;
import com.sparkleside.databinding.ActivitySettingsBinding;
import com.sparkleside.ui.base.BaseActivity;

public class SettingsActivity extends BaseActivity {

  private ActivitySettingsBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    binding = ActivitySettingsBinding.inflate(getLayoutInflater());
    configureTransitions(R.id.coordinator);
    super.onCreate(savedInstanceState);
    setContentView(binding.getRoot());

    setSupportActionBar(binding.toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);

    binding.toolbar.setNavigationOnClickListener(v -> super.onBackPressed());

    binding.about.setOnClickListener(
        v -> {
          Intent intent = new Intent(SettingsActivity.this, AboutActivity.class);
          android.app.ActivityOptions optionsCompat =
              android.app.ActivityOptions.makeSceneTransitionAnimation(SettingsActivity.this);
          startActivity(intent, optionsCompat.toBundle());
        });

    binding.main.setOnClickListener(
        v -> {
          Intent intent = new Intent(SettingsActivity.this, AppearanceActivity.class);
          android.app.ActivityOptions optionsCompat =
              android.app.ActivityOptions.makeSceneTransitionAnimation(SettingsActivity.this);
          startActivity(intent, optionsCompat.toBundle());
        });
    binding.editor.setOnClickListener(
        v -> {
          Intent intent = new Intent(SettingsActivity.this, CodeEditorSettingsActivity.class);
          android.app.ActivityOptions optionsCompat =
              android.app.ActivityOptions.makeSceneTransitionAnimation(SettingsActivity.this);
          startActivity(intent, optionsCompat.toBundle());
        });
    binding.lib.setOnClickListener(
        v -> {
          LibsBuilder libe = new LibsBuilder();
          libe.start(this);
        });
  }

  @Override
  public void onResume() {
    super.onResume();
    configureTransitions(R.id.coordinator);
  }
}

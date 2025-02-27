package com.sparkleside.ui.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.transition.platform.MaterialContainerTransform;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;
import com.sparkleside.R;
import com.sparkleside.databinding.ActivityHtmlViewerBinding;

public class HtmlViewerActivity extends AppCompatActivity {
  private ActivityHtmlViewerBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    binding = ActivityHtmlViewerBinding.inflate(getLayoutInflater());
    binding.coordinator.setTransitionName("html");
    setEnterSharedElementCallback(new MaterialContainerTransformSharedElementCallback());
    getWindow()
        .setSharedElementEnterTransition(
            new MaterialContainerTransform().addTarget(R.id.coordinator).setDuration(400));
    getWindow()
        .setSharedElementReturnTransition(
            new MaterialContainerTransform().addTarget(R.id.coordinator).setDuration(350));
    super.onCreate(savedInstanceState);
    setContentView(binding.getRoot());
    setSupportActionBar(binding.toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
    binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
    binding.webview.loadUrl("data:text/html,".concat(getIntent().getStringExtra("html")));
  }
}

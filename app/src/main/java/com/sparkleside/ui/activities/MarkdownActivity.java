package com.sparkleside.ui.activities;

import android.os.Bundle;
import com.google.android.material.transition.platform.MaterialContainerTransform;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;
import com.sparkleside.R;
import com.sparkleside.databinding.ActivityMarkdownBinding;
import com.sparkleside.ui.base.BaseActivity;
import io.noties.markwon.Markwon;

public class MarkdownActivity extends BaseActivity {
  private ActivityMarkdownBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    binding = ActivityMarkdownBinding.inflate(getLayoutInflater());
    binding.coordinator.setTransitionName("mark");
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
    final Markwon markwon = Markwon.create(MarkdownActivity.this);
    markwon.configuration();
    markwon.setMarkdown(binding.markdownView, getIntent().getStringExtra("mark"));
  }
}

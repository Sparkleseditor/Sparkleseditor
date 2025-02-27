package com.sparkleside.ui.base;

import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.transition.platform.MaterialSharedAxis;

public class BaseActivity extends AppCompatActivity {

  private static final int DEFAULT_TOAST_TIME = 4000;
  private static final long DEFAULT_TRANSITION_DURATION = 400L;

  @Override
  protected void onCreate(@Nullable final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
  }

  protected void configureTransitions(@IdRes final int root) {
    getWindow().setAllowEnterTransitionOverlap(false);
    try {
      configureEnterTransition(root);
      configureReturnTransition(root);
    } catch (final Exception e) {
      showMessage("Not possible to configure transitions, error: " + e.toString());
    }
  }

  protected void configureEnterTransition(@IdRes final int root) {
    final MaterialSharedAxis enterTransition = new MaterialSharedAxis(MaterialSharedAxis.X, true);
    enterTransition.addTarget(root);
    enterTransition.setDuration(DEFAULT_TRANSITION_DURATION);
    getWindow().setEnterTransition(enterTransition);
  }

  protected void configureReturnTransition(@IdRes final int root) {
    final MaterialSharedAxis returnTransition = new MaterialSharedAxis(MaterialSharedAxis.X, false);
    returnTransition.setDuration(DEFAULT_TRANSITION_DURATION);
    returnTransition.addTarget(root);
    getWindow().setReturnTransition(returnTransition);
  }

  protected void showMessage(final String message) {
    Toast.makeText(this, message, DEFAULT_TOAST_TIME);
  }
}

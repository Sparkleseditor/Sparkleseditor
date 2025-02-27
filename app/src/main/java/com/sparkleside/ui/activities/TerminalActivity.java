package com.sparkleside.ui.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.blankj.utilcode.util.SizeUtils;
import com.sparkleside.R;
import com.sparkleside.databinding.ActivityTerminalBinding;
import com.sparkleside.ui.base.BaseActivity;
import com.sparkleside.ui.components.TerminalBackEnd;
import com.sparkleside.ui.components.virtualkeys.VirtualKeysConstants;
import com.sparkleside.ui.components.virtualkeys.VirtualKeysInfo;
import com.sparkleside.ui.components.virtualkeys.VirtualKeysListener;
import com.termux.terminal.TerminalEmulator;
import com.termux.terminal.TerminalSession;
import com.termux.view.TerminalView;
import org.json.JSONException;

/**
 * A basic implementation of termux's terminal
 *
 * @author Rohit Kushvaha (RohitKushvaha01).
 */
public class TerminalActivity extends BaseActivity {
  public ActivityTerminalBinding binding;
  private TerminalBackEnd backEnd;
  private TerminalView terminal;

  @Override
  protected void onCreate(@Nullable Bundle saved) {
    binding = ActivityTerminalBinding.inflate(getLayoutInflater());
    configureTransitions(R.id.coordinator);
    super.onCreate(saved);
    backEnd = new TerminalBackEnd(this);
    setupTerminalView();
    setContentView(binding.getRoot());
    try {
      setupVirtualKeys();
    } catch (Exception e) {
      throw new RuntimeException("Invalid VIRTUAL_KEYS variable", e);
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    configureTransitions(R.id.coordinator);
  }

  // destroy the session to prevent memory leak
  @Override
  protected void onDestroy() {
    if (terminal != null && terminal.mTermSession != null) {
      terminal.mTermSession.finishIfRunning();
    }
    super.onDestroy();
  }

  private void setupVirtualKeys() throws JSONException {
    binding.extraKeys.setVirtualKeysViewClient(new VirtualKeysListener(terminal.mTermSession));
    binding.extraKeys.reload(
        new VirtualKeysInfo(VIRTUAL_KEYS, "", VirtualKeysConstants.CONTROL_CHARS_ALIASES));
  }

  private void setupTerminalView() {
    terminal = new TerminalView(this, null);
    backEnd.setTerminal(terminal);
    terminal.setTerminalViewClient(backEnd);
    TerminalSession session = createSession();
    terminal.attachSession(session);
    terminal.setBackgroundColor(Color.BLACK);
    terminal.setTextSize(SizeUtils.dp2px(14f));
    terminal.setKeepScreenOn(true);

    LinearLayout.LayoutParams params =
        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
    params.weight = 1f;
    binding.getRoot().addView(terminal, 0, params);
    terminal.requestFocus();
    terminal.setFocusableInTouchMode(true);
  }

  private TerminalSession createSession() {
    // environment variable that be will be available in terminal
    String[] env = {
      "HOME=" + getFilesDir().getAbsolutePath(),
      "PUBLIC_HOME=" + getExternalFilesDir(null).getAbsolutePath(),
      "COLORTERM=truecolor",
      "TERM=xterm-256color"
    };

    // using system shell
    String shell = "/system/bin/sh";

    // args for the shell
    String[] args = new String[] {};

    return new TerminalSession(
        shell,
        getFilesDir().getAbsolutePath(),
        args,
        env,
        TerminalEmulator.DEFAULT_TERMINAL_TRANSCRIPT_ROWS,
        backEnd);
  }

  private static final String VIRTUAL_KEYS =
      "[\n  ["
          + "\n    \"ESC\","
          + "\n    {\"key\": \"/\", \"popup\": \"\\\\\"},"
          + "\n    {\"key\": \"-\", \"popup\": \"|\"},"
          + "\n    \"HOME\","
          + "\n    \"UP\","
          + "\n    \"END\","
          + "\n    \"PGUP\""
          + "\n  ],"
          + "\n  ["
          + "\n    \"TAB\","
          + "\n    \"CTRL\","
          + "\n    \"ALT\","
          + "\n    \"LEFT\","
          + "\n    \"DOWN\","
          + "\n    \"RIGHT\","
          + "\n    \"PGDN\""
          + "\n  ]\n]";
}

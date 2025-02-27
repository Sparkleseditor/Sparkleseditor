package com.sparkleside.ui.components;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import com.bumptech.glide.Glide;
import com.sparkleside.R;
import com.sparkleside.databinding.LayoutTeamMemberViewBinding;

public class TeamMemberView extends RelativeLayout {

  private LayoutTeamMemberViewBinding binding;
  private String url;
  private Context context;
  private boolean hasDivider;

  public TeamMemberView(Context context) {
    super(context);
    init(context);
  }

  public TeamMemberView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public TeamMemberView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  private void init(Context context) {
    this.context = context;
    binding = LayoutTeamMemberViewBinding.inflate(LayoutInflater.from(context), this, true);
    getRoot()
        .setOnClickListener(
            v -> {
              try {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(getURL()));
                context.startActivity(i);
              } catch (Exception e) {
                // nah
              }
            });
  }

  public View getRoot() {
    return binding.getRoot();
  }

  public void setImageURL(String url) {
    Glide.with(context).load(url).into(binding.avatar);
  }

  public void setName(String name) {
    binding.name.setText(name);
  }

  public void setDescription(String description) {
    binding.description.setText(description);
  }

  public void setURL(String url) {
    this.url = url;
  }

  public String getURL() {
    return url;
  }

  public void setBackgroundType(final int type) {
    int backgroundResource;
    switch (type) {
      case 0:
        backgroundResource = R.drawable.shape_top;
        break;
      case 1:
        backgroundResource = R.drawable.shape_middle;
        break;
      case 2:
        backgroundResource = R.drawable.shape_bottom;
        break;
      case 3:
        backgroundResource = R.drawable.shape_alone;
        break;
      default:
        backgroundResource = Color.TRANSPARENT;
        break;
    }
    binding.bg.setBackgroundResource(backgroundResource);
  }

  public void setHasDivider(boolean hasDivider) {
    this.hasDivider = hasDivider;
    if (!hasDivider) {
      binding.divider.setVisibility(View.GONE);
      return;
    }
    binding.divider.setVisibility(View.VISIBLE);
  }
}

package com.sparkleside.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.StringRes;
import com.bumptech.glide.Glide;
import com.peekandpop.shalskar.peekandpop.PeekAndPop;
import com.sparkleside.R;
import com.sparkleside.databinding.ActivityAboutBinding;
import com.sparkleside.ui.base.BaseActivity;
import com.sparkleside.ui.components.TeamMemberView;
import dev.trindadedev.fastui.UI;

/*
 * A Screen with info about app
 * @author Aquiles Trindade (trindadedev).
 * @author SyntaxSpin (SyntaxSpin)
 */

public class AboutActivity extends BaseActivity {
  private ActivityAboutBinding binding;
  private Intent intent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    binding = ActivityAboutBinding.inflate(getLayoutInflater());
    configureTransitions(R.id.coordinator);
    super.onCreate(savedInstanceState);
    setContentView(binding.getRoot());
    configureToolbar();
    configureDevelopers();
    configureLinks();
    configureTeamMembers();
    UI.handleInsetts(binding.getRoot());
  }

  @Override
  public void onResume() {
    super.onResume();
    configureTransitions(R.id.coordinator);
  }

  private void configureToolbar() {
    setSupportActionBar(binding.toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);

    binding.toolbar.setNavigationOnClickListener(
        v -> {
          super.onBackPressed();
        });
  }

  private void configureDevelopers() {
    Glide.with(this).load("https://github.com/syntaxspin.png").into(binding.imgSyn);
    Glide.with(this).load("https://github.com/neoapps-dev.png").into(binding.imgNeo);
    Glide.with(this).load("https://github.com/trindadedev13.png").into(binding.imgTrindade);

    peekAndPop(
        "SyntaxSpin",
        "https://github.com/syntaxspin.png",
        getString(R.string.syntaxspin_phrase),
        binding.imgSyn);
    peekAndPop(
        "NEOAPPS",
        "https://github.com/neoapps-dev.png",
        getString(R.string.neoapps_phrase),
        binding.imgNeo);
    peekAndPop(
        "Aquiles Trindade",
        "https://github.com/trindadedev13.png",
        getString(R.string.trindadedev_phrase),
        binding.imgTrindade);
  }

  private void configureLinks() {
    binding.tg.setOnClickListener(
        v -> {
          openURL("https://www.telegram.me/sparkleseditor");
        });

    binding.commits.setOnClickListener(
            v -> {
              Intent intent = new Intent(AboutActivity.this, CommitsActivity.class);
              android.app.ActivityOptions optionsCompat =
                      android.app.ActivityOptions.makeSceneTransitionAnimation(
                              AboutActivity.this, binding.commits, "commits");
              startActivity(intent, optionsCompat.toBundle());
            });

    binding.github.setOnClickListener(
        v -> {
          openURL("https://github.com/sparkleddevs/sparkleseditor");
        });

    binding.hanzo.setOnClickListener(
        v -> {
          openURL("https://github.com/neoapps-dev");
        });

    binding.syn.setOnClickListener(
        v -> {
          openURL("https://github.com/syntaxspin");
        });

    binding.trindade.setOnClickListener(
        v -> {
          openURL("https://github.com/trindadedev13");
        });
  }

  private void configureTeamMembers() {
    TeamMember(
        "Vivek",
        Role.DEVELOPER,
        "https://github.com/itsvks19",
        getString(R.string.vivek_phrase),
        0,
        false);
    TeamMember(
        "Rohit Kushvaha",
        Role.DEVELOPER,
        "https://github.com/RohitKushvaha01",
        getString(R.string.rohit_kushvaha_phrase),
        1,
        false);

    TeamMember(
        "Thiarley Rocha",
        Role.DEVELOPER,
        "https://github.com/thdev-only",
        getString(R.string.thiarley_rocha_phrase),
        1,
        false);

    TeamMember(
        "YamenHer",
        Role.DEVELOPER,
        "https://github.com/yamenher",
        getString(R.string.yamenher_phrase),
        1,
        false);
    TeamMember(
        "ArtSphere",
        Role.DEVELOPER,
        "https://github.com/ArtSphereOfficial",
        getString(R.string.art_phrase),
        1,
        false);
    // lmao bro below did anything and is here lmfaoooo
    TeamMember(
        "Hanzo",
        Role.DEVELOPER,
        "https://github.com/HanzoDev1375",
        getString(R.string.hanzo_phrase),
        1,
        false);

    TeamMember(
        "Jeiel Lima Miranda",
        Role.DEVELOPER,
        "https://github.com/Jeiel0rbit",
        getString(R.string.jaiel_lima_phrase),
        1,
        false);
    TeamMember(
        "Ömer SÜSİN",
        Role.PROMOTER,
        "https://github.com/omersusin",
        getString(R.string.omer_phrase),
        1,
        false);
    TeamMember(
        "𝙊𝙋𝙏𝙄𝙈𝒊𝒛𝒆𝒓. 𝟚.𝟜.𝟛",
        Role.PROMOTER,
        "https://github.com/matrixguy007",
        getString(R.string.optim_phrase),
        1,
        false);
    TeamMember(
        "ZG089",
        Role.DESGINER,
        "https://github.com/zg089",
        getString(R.string.zg_phrase),
        1,
        false);

    TeamMember(
        "Alex",
        Role.TRANSLATOR,
        "https://github.com/paxsenix0",
        getString(R.string.alex_phrase),
        1,
        false);
    TeamMember(
        "Heinrich",
        Role.TRANSLATOR,
        "https://github.com/HeinrichTheGermanNOTOffizier20",
        getString(R.string.alex_phrase),
        1,
        false);    
    TeamMember(
        "BatoBatoB",
        Role.TRANSLATOR,
        "https://github.com/BatoBatoB",
        getString(R.string.bato_phrase),
        1,
        false);        
    TeamMember(
        "Fahim Abdullah",
        Role.TRANSLATOR,
        "https://github.com/nexavo999",
        getString(R.string.nex_phrase),
        2,
        false);
  }

  private void peekAndPop(String name, String imageUrl, String phrase, View v) {
    final PeekAndPop peekAndPop =
        new PeekAndPop.Builder(this)
            .peekLayout(R.layout.layout_about_preview)
            .longClickViews(v)
            .build();
    final ImageView peekChild = peekAndPop.getPeekView().findViewById(R.id.icon);
    Glide.with(this).load(imageUrl).into(peekChild);
    final TextView peekTextName = peekAndPop.getPeekView().findViewById(R.id.name);
    peekTextName.setText(name);
    final TextView peekTextPhrase = peekAndPop.getPeekView().findViewById(R.id.phrase);
    peekTextPhrase.setText(phrase);
  }

  private void TeamMember(
      String name, Role role, String url, String phrase, int bgType, boolean hasDivider) {
    final var c = new TeamMemberView(this);
    c.setName(name);
    c.setDescription(role.getName(this));
    c.setImageURL(url + ".png");
    c.setURL(url);
    c.setHasDivider(hasDivider);
    c.setBackgroundType(bgType);
    peekAndPop(name, url + ".png", phrase, c.getRoot());
    binding.team.addView(c);
  }

  private void openURL(String url) {
    final var i = new Intent(Intent.ACTION_VIEW);
    i.setData(Uri.parse(url));
    startActivity(i);
  }

  public enum Role {
    TRANSLATOR(R.string.about_tag_translator),
    DEVELOPER(R.string.about_tag_developer),
    PROMOTER(R.string.about_tag_promote),
    DESGINER(R.string.about_tag_designer);
    @StringRes private final int stringResId;

    Role(@StringRes int stringResId) {
      this.stringResId = stringResId;
    }

    public String getName(Context context) {
      return context.getString(stringResId);
    }
  }
}

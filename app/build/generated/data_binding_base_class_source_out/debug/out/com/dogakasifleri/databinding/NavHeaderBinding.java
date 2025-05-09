// Generated by view binder compiler. Do not edit!
package com.dogakasifleri.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.dogakasifleri.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class NavHeaderBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageView ivNavHeaderAvatar;

  @NonNull
  public final TextView tvNavHeaderEmail;

  @NonNull
  public final TextView tvNavHeaderUsername;

  private NavHeaderBinding(@NonNull LinearLayout rootView, @NonNull ImageView ivNavHeaderAvatar,
      @NonNull TextView tvNavHeaderEmail, @NonNull TextView tvNavHeaderUsername) {
    this.rootView = rootView;
    this.ivNavHeaderAvatar = ivNavHeaderAvatar;
    this.tvNavHeaderEmail = tvNavHeaderEmail;
    this.tvNavHeaderUsername = tvNavHeaderUsername;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static NavHeaderBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static NavHeaderBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.nav_header, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static NavHeaderBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.ivNavHeaderAvatar;
      ImageView ivNavHeaderAvatar = ViewBindings.findChildViewById(rootView, id);
      if (ivNavHeaderAvatar == null) {
        break missingId;
      }

      id = R.id.tvNavHeaderEmail;
      TextView tvNavHeaderEmail = ViewBindings.findChildViewById(rootView, id);
      if (tvNavHeaderEmail == null) {
        break missingId;
      }

      id = R.id.tvNavHeaderUsername;
      TextView tvNavHeaderUsername = ViewBindings.findChildViewById(rootView, id);
      if (tvNavHeaderUsername == null) {
        break missingId;
      }

      return new NavHeaderBinding((LinearLayout) rootView, ivNavHeaderAvatar, tvNavHeaderEmail,
          tvNavHeaderUsername);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}

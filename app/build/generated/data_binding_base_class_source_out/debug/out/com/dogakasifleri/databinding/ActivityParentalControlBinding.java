// Generated by view binder compiler. Do not edit!
package com.dogakasifleri.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.dogakasifleri.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityParentalControlBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnBack;

  @NonNull
  public final Button btnSaveSettings;

  @NonNull
  public final EditText etDailyTimeLimit;

  @NonNull
  public final EditText etParentPin;

  @NonNull
  public final Switch switchAllowQuiz;

  @NonNull
  public final Switch switchContentFilter;

  @NonNull
  public final Switch switchTimeLimit;

  @NonNull
  public final Toolbar toolbar;

  private ActivityParentalControlBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button btnBack, @NonNull Button btnSaveSettings, @NonNull EditText etDailyTimeLimit,
      @NonNull EditText etParentPin, @NonNull Switch switchAllowQuiz,
      @NonNull Switch switchContentFilter, @NonNull Switch switchTimeLimit,
      @NonNull Toolbar toolbar) {
    this.rootView = rootView;
    this.btnBack = btnBack;
    this.btnSaveSettings = btnSaveSettings;
    this.etDailyTimeLimit = etDailyTimeLimit;
    this.etParentPin = etParentPin;
    this.switchAllowQuiz = switchAllowQuiz;
    this.switchContentFilter = switchContentFilter;
    this.switchTimeLimit = switchTimeLimit;
    this.toolbar = toolbar;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityParentalControlBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityParentalControlBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_parental_control, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityParentalControlBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnBack;
      Button btnBack = ViewBindings.findChildViewById(rootView, id);
      if (btnBack == null) {
        break missingId;
      }

      id = R.id.btnSaveSettings;
      Button btnSaveSettings = ViewBindings.findChildViewById(rootView, id);
      if (btnSaveSettings == null) {
        break missingId;
      }

      id = R.id.etDailyTimeLimit;
      EditText etDailyTimeLimit = ViewBindings.findChildViewById(rootView, id);
      if (etDailyTimeLimit == null) {
        break missingId;
      }

      id = R.id.etParentPin;
      EditText etParentPin = ViewBindings.findChildViewById(rootView, id);
      if (etParentPin == null) {
        break missingId;
      }

      id = R.id.switchAllowQuiz;
      Switch switchAllowQuiz = ViewBindings.findChildViewById(rootView, id);
      if (switchAllowQuiz == null) {
        break missingId;
      }

      id = R.id.switchContentFilter;
      Switch switchContentFilter = ViewBindings.findChildViewById(rootView, id);
      if (switchContentFilter == null) {
        break missingId;
      }

      id = R.id.switchTimeLimit;
      Switch switchTimeLimit = ViewBindings.findChildViewById(rootView, id);
      if (switchTimeLimit == null) {
        break missingId;
      }

      id = R.id.toolbar;
      Toolbar toolbar = ViewBindings.findChildViewById(rootView, id);
      if (toolbar == null) {
        break missingId;
      }

      return new ActivityParentalControlBinding((ConstraintLayout) rootView, btnBack,
          btnSaveSettings, etDailyTimeLimit, etParentPin, switchAllowQuiz, switchContentFilter,
          switchTimeLimit, toolbar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}

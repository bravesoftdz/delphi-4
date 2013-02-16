package edu.mq.comp.delphi.model.presentation;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import edu.mq.comp.delphi.model.Source;
import edu.mq.comp.delphi.model.Tag;
import edu.mq.comp.delphi.model.UserPreference;

public class PresentationUserPreference implements Serializable {
  private static final long serialVersionUID = 1L;
  private final Set<PresentationTag> tags;
  private final Set<PresentationSource> sources;
  private final int frequency;
  private final int maxPerTag;
  private final boolean notificationEnabled;
  private final String mobileNumber;
  private final int notificationType;

  public PresentationUserPreference(UserPreference userPreference) {
	if (userPreference == null) {
	  throw new IllegalArgumentException("userPreference cannot be null");
	}

	frequency = userPreference.getFrequency();
	maxPerTag = userPreference.getMaxPerTag();
	notificationEnabled = userPreference.isNotificationEnabled();
	mobileNumber = userPreference.getMobileNumber();
	notificationType = userPreference.getNotificationType();

	tags = new LinkedHashSet<PresentationTag>();
	
	for (Tag tag : userPreference.getTags()) {
	  tags.add(new PresentationTag(tag));
	}
	
	sources = new LinkedHashSet<PresentationSource>();
	
	for (Source source : userPreference.getSources()) {
	  sources.add(new PresentationSource(source));
	}
  }

  public Set<PresentationTag> getTags() {
	return tags;
  }

  public Set<PresentationSource> getSources() {
	return sources;
  }

  public int getFrequency() {
	return frequency;
  }

  public int getMaxPerTag() {
	return maxPerTag;
  }

  public boolean isNotificationEnabled() {
	return notificationEnabled;
  }

  public String getMobileNumber() {
	return mobileNumber;
  }

  public int getNotificationType() {
	return notificationType;
  }
}

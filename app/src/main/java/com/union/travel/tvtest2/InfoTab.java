package com.union.travel.tvtest2;

import android.text.TextUtils;

import java.util.Locale;
import java.util.Map;

public class InfoTab {

    public String tabId;

    public Map<String, String> tabTitle;


    public String getOrigTabName() {
        return tabTitle.get(Locale.ENGLISH.getLanguage().toLowerCase());
    }

    public String getTabNameForLocale() {
        String current = Locale.getDefault().getLanguage().toLowerCase();
        String title = tabTitle.get(current);
        if (TextUtils.isEmpty(title)) {
            return tabTitle.get(Locale.ENGLISH.getLanguage().toLowerCase());
        }

        return title;
    }
}

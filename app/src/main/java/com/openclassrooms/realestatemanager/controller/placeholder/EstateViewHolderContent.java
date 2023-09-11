package com.openclassrooms.realestatemanager.controller.placeholder;

import com.openclassrooms.realestatemanager.model.Estate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class EstateViewHolderContent {
    public static final List<Estate> ESTATE_LIST = new ArrayList<>();
    public static final Map<String, Estate> ITEM_MAP = new HashMap<>();
    private static final int COUNT = 1;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createPlaceholderItem(i));
        }
    }

    private static void addItem(Estate estate) {
        ESTATE_LIST.add(estate);
        ITEM_MAP.put(estate.getAddress(), estate);
    }

    private static Estate createPlaceholderItem(int position) {
        return new Estate();
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A placeholder item representing a piece of content.
     */
    public static class EstateListViewHolder {
        public final String price;
        public final String content;
        public final String details;

        public EstateListViewHolder(String price, String content, String details) {
            this.price = price;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
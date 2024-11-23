package christmas.domain;

import java.util.Arrays;

public enum Badge {

    SANTA("산타",20_000),
    TREE("트리",10_000),
    STAR("별", 5_000),
    NONE("없음",0);

    private final String badgeName;
    private final int appliabeCriteriaMoney;

    Badge(final String badgeName, final int appliabeCriteriaMoney) {
        this.badgeName = badgeName;
        this.appliabeCriteriaMoney = appliabeCriteriaMoney;
    }

    public static Badge of(final int totalDiscountAmount){
       return Arrays.stream(values())
                .filter(badge -> badge.appliabeCriteriaMoney <= totalDiscountAmount)
                .findFirst()
                .orElse(NONE);
    }
}


package com.daviddai.retail.ui.component;

import java.util.Arrays;

import com.daviddai.retail.model.enums.Role;

public enum Screen {

    HOME("Trang chủ", "bar_chart_white", true),
    PRODUCTS("Sản phẩm", "package_box_white", true),
    SELL("Bán hàng", "shopping_cart_white", true),
    BILL("Hóa đơn", "receipt_white", true),
    PROMOTION("Khuyến mãi", "discount_coupon_white", false),
    STAFFS("Nhân viên", "user_group_white", false),
    CUSTOMER("Khách hàng", "cashier_white", true),
    REFUND("Trả hàng", "document_history_white", false),
    PROFILE("Tài khoản", "user_profile_white", true);

    private final String title;
    private final String iconName;

    private final boolean isStaffAccess;

    private Screen(String title, String iconName, boolean isStaffAccess) {
        this.title = title;
        this.iconName = iconName;
        this.isStaffAccess = isStaffAccess;
    }

    public String getTitle() {
        return title;
    }

    public String getIconName() {
        return iconName;
    }

    public boolean isStaffAccess() {
        return isStaffAccess;
    }

    public static Screen[] forRole(String role) {
        boolean isManager = Role.MANAGER.toString().equalsIgnoreCase(role);
        return Arrays.stream(values())
                .filter(s -> isManager || s.isStaffAccess())
                .toArray(Screen[]::new);
    }
}

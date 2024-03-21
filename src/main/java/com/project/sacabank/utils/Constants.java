package com.project.sacabank.utils;

public class Constants {
  public static final String API_USER_PATH = "/api/user";
  public static final String API_CATEGORY_PATH = "/api/category";
  public static final String API_ROLE_PATH = "/api/role";
  public static final String API_AUTH_PATH = "/api/auth";
  public static final String API_REGISTER_VENDOR_PATH = "/api/register_vendor";
  public static final String API_PRODUCT_PATH = "/api/product";
  public static final String API_ORDER_PATH = "/api/order";

  public static class AuthPermissionAll {
    public static final String AUTH_PATH = "/api/auth/**";
    public static final String ROLE_PATH = "/api/role/**";
    public static final String USER_VENDOR = "/api/user/vendor";
    public static final String PRODUCT_PUBLIC = "/api/product/public/**";

    public static final String API_REGISTER_VENDOR_PATH = "/api/register_vendor/register";
  }

  public static final Integer PAGE_SIZE = 20;
}

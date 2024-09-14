package com.project.sacabank.utils;

public class Constants {
  public static final String API_USER_PATH = "/api/user";
  public static final String API_CATEGORY_PATH = "/api/category";
  public static final String API_ROLE_PATH = "/api/role";
  public static final String API_AUTH_PATH = "/api/auth";
  public static final String API_REGISTER_VENDOR_PATH = "/api/register_vendor";
  public static final String API_PRODUCT_PATH = "/api/product";
  public static final String API_ORDER_PATH = "/api/order";
  public static final String API_MAIL_PATH = "/api/mail";
  public static final String API_LIST_PHOTO = "/api/list_photo";
  public static final String REPORT_API = "/api/report";
  public static final String PRODUCT_CATEGORY_API = "/api/product_category";
  public static final String BLOG_API = "/api/blog";
  public static final String PRODUCT_COMMENT_API = "/api/product_comment";

  public static final String BANNER_API = "/api/banner";

  public static class AuthPermissionAll {
    public static final String AUTH_PATH = "/api/auth/**";
    public static final String ROLE_PATH = "/api/role/**";
    public static final String USER_VENDOR = "/api/user/vendor";
    public static final String PRODUCT_PUBLIC = "/api/product/public/**";

    public static final String API_REGISTER_VENDOR_PATH = "/api/register_vendor/register";
  }

  public static final Integer PAGE_SIZE = 20;
}

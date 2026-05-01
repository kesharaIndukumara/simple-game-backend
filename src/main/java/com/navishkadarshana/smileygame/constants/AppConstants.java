package com.navishkadarshana.smileygame.constants;


import java.util.regex.Pattern;

/**
 * @author Navishka Darshana - navishkada@gmail.com
 * @project  simple-game-backend
 * @CreatedBy IntelliJ IDEA
 * @created 26/02/2024 - 23.16
 */

public class AppConstants {

    public static final class DetailConstants {
        public static final String HEADER_AUTH = "Authorization";

        public static final String INTERNAL_CLIENT_BASIC_KEY = "cmVnaXN0ZXJlZF9kb25vcjo=";

        public static final String ASYNC_EXECUTOR = "threadPoolTaskExecutor";
        public static final String FORBIDDEN_RESOURCE = "You are not authorized to access this resource!";
    }

    public static final class PatternConstants {
        public static final String DATE_TIME_RESPONSE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        public static final String DATE_PATTERN = "yyyy-MM-dd";
        public static final String TIME_PATTERN = "HH:mm:ss";
        public static final String TIME_ZONE = "GMT";
        public static final String REGEX = "[^A-Za-z0-9]";
        public static final Pattern ALLOWED_CHARACTERS_PATTERN = Pattern.compile("[A-Za-z0-9\\s" + Pattern.quote("\",-[]()&.@!<>/\\s\\\\ ") +  "]*");

    }



    public static final class Email {
        public static final String PLAYER_EMAIL_CONFORM_URL="{player_frontend_base_url}/register-complete?uid={token}";
        public static final String VERIFY_EMAIL="Verify your email";
    }

    public static final class EmailBody {

        public static final String VERIFY_EMAIL_STARTING_LINE="You’re almost done creating your Karuna.lk profile.";
    }

    public static final class NotFoundConstants {
        public static final String NO_USER_FOUND = "User not found.";
    }


    public static final class DuplicatedConstants {

        public static final String EMAIL_ALREADY_EXISTS = "Email already exists";
        public static final String USERNAME_ALREADY_EXISTS = "Username already exists";

    }


    public static final class ErrorConstants {

        public static final String INVALID_EMAIL = "Invalid email!";
        public static final String DONOR_ACCOUNT_NOT_COMPLETED_MSG = "There seems to be an issue with your account identification. Please fill out the following details so that we can confirm your identity.";
        public static final String INVALID_CLIENT_ID = "Invalid client id!!";
        public static final String INVALID_ACTION = "Invalid action!";
        public static final String INVALID_DATE = "Invalid date format!";
        public static final String INVALID_PASSWORD = "Invalid password!";
        public static final String INVALID_LOGIN_ATTEMPTS = "Invalid login credentials. You have <count> attempts remaining.";
        public static final String ACCOUNT_LOCKED = "Your account was locked after too many failed login attempts. Please click on forgot password to a set a new password and try again.";
        public static final String ACCOUNT_BLOCKED = "Your account is currently blocked by the administrator. Please contact them for further information.";
        public static final String ACCOUNT_NOT_VERIFY = "our account is not yet verified. Please verify your account first!";
        public static final String ADMIN_ACCOUNT_LOCKED = " Your account was locked after too many failed login attempts. Please contact the service provider in order to set a new password and try again.";
        public static final String MODERATOR_ACCOUNT_LOCKED = "Your account was locked after too many failed login attempts. Please contact an administrator in the platform in order to set a new password and try again.";
        public static final String FINANCE_REVIEWER_ACCOUNT_LOCKED = "Your account was locked after too many failed login attempts. Please contact an administrator in the platform in order to set a new password and try again.";
        public static final String INVALID_OLD_PASSWORD = "Old password is invalid!";
        public static final String INVALID_PASSWORD_CHARACTER_LENGTH = "Password must be at least 6 characters!";
        public static final String INVALID_RE_TYPE_PASSWORD = "The password confirmation doest not match.";
        public static final String OTP_SEND_ERROR_MESSAGE = "Unable to send OTP.";
        public static final String OTP_VALID_ALREADY_ERROR = "Your final OTP code is still valid. Please use it during the OTP authentication step.";
        public static final String EMAIL_RESEND_ERROR = "You can only request the verification link twice within a minute. Please try again after the mentioned time period.";
        public static final String OTP_HAS_EXPIRED = "OTP has expired!";
        public static final String LARGE_FILE_SIZE = "File is too large to be uploaded. Files larger than 15MB are not currently supported.";
        public static final String INVALID_FILE_TYPE = "Invalid file type!";
        public static final String ORGANIZATION_LOGO_INVALID_FORMAT = "Organization logo should be in JPG/JPEG or PNG format!";
        public static final String REQ_CATEGORY_LIST_EMPTY = "Please select categories!";
        public static final String ACCOUNT_ALREADY_VERIFIED ="Account Already verified.";

        public static final String PASSWORD_UPDATED ="Your Password has been updated.";
        public static final String TOKEN_HAS_EXPIRED = "Authentication Failed. Your token has expired or is no longer active.";
    }

    public static final class RequiredFieldConstant{
        public static final String CHARITY_LOGO_REQUIRED = "Organization logo is required!";
    }

    public static final class CacheConstant{
        public static final String HOME_CACHE = "HOME_CACHE";
    }
}

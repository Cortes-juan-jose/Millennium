package com.app.millennium.core.common

/**
 * Constantes
 */
object Constant {

    const val KO: Int = 876
    const val OK: Int = 877

    const val COLOR_RED_PRIMARY : Int = 243
    const val COLOR_RED_PRIMARY_DARK: Int = 189
    const val COLOR_GREEN_PRIMARY : Int = 135
    const val COLOR_GREEN_PRIMARY_DARK : Int = 88
    const val COLOR_BLUE_PRIMARY : Int = 135
    const val COLOR_BLUE_PRIMARY_DARK : Int = 90

    const val TAG_DEFAULT: String = "default"
    const val TAG_NOT_DEFAULT: String = "no default"

    const val RESULT_CODE_CV_IMG_POST_1: Int = 1
    const val RESULT_CODE_CV_IMG_POST_2: Int = 2
    const val RESULT_CODE_CV_IMG_POST_3: Int = 3
    const val RESULT_CODE_CV_IMG_POST_4: Int = 4
    const val RESULT_CODE_CV_IMG_POST_COVER: Int = 5
    const val RESULT_CODE_CV_IMG_POST_PROFILE: Int = 6

    const val PERMISSION_CAMERA: Int  = 111
    const val PERMISSION_GALLERY: Int  = 222

    const val PACKAGE_PROJECT: String = "com.app.millennium"
    const val NAME_APP: String = "Millennium"

    const val FORMAT_JPG: String = ".jpg"
    const val FORMAT_JPEG: String = ".jpeg"
    const val FORMAT_PNG: String = ".png"

    const val COLLECTION_USERS: String = "Users"
    const val COLLECTION_LIKES: String = "Likes"
    const val COLLECTION_PRODUCTS: String = "Products"
    const val COLLECTION_OPINIONS: String = "Opinions"
    const val COLLECTION_TOKENS: String = "Tokens"
    const val COLLECTION_CHATS: String = "Chats"

    const val STORAGE_IMAGES : String = "images"

    const val WIDTH_IMAGE_STORAGE: Int = 500
    const val HEIGHT_IMAGE_STORAGE: Int = 500

    const val BUNDLE_USER: String = "user"
    const val PROP_ID_USER: String = "id"
    const val PROP_USERNAME_USER: String = "name"
    const val PROP_EMAIL_USER: String = "email"
    const val PROP_UPLOADED_PRODUCTS_USER: String = "uploadedProducts"
    const val PROP_OPINIONS_USER: String = "opinions"
    const val PROP_TIMESTAMP_USER: String = "timestamp"
    const val PROP_IMG_COVER_USER: String = "imgCover"
    const val PROP_IMG_PROFILE_USER: String = "imgProfile"
    const val PROP_PHONE_USER: String = "phone"

    const val BUNDLE_PRODUCT: String = "product"
    const val PROP_ID_PRODUCT: String = "id"
    const val PROP_ID_USER_PRODUCT: String = "idUser"
    const val PROP_TITLE_PRODUCT: String = "title"
    const val PROP_DESCRIPTION_PRODUCT: String = "description"
    const val PROP_CATEGORY_PRODUCT: String = "category"
    const val PROP_PRICE_PRODUCT: String = "price"
    const val PROP_NEGOTIABLE_PRODUCT: String = "negotiable"
    const val PROP_PRODUCT_STATUS_PRODUCT: String = "productStatus"
    const val PROP_IMAGE1_PRODUCT: String = "image1"
    const val PROP_IMAGE2_PRODUCT: String = "image2"
    const val PROP_IMAGE3_PRODUCT: String = "image3"
    const val PROP_IMAGE4_PRODUCT: String = "image4"
    const val PROP_TIMESTAMP_PRODUCT: String = "timestamp"

    const val PROP_ID_LIKE: String = "id"
    const val PROP_ID_USER_TO_SESSION_LIKE: String = "idUserToSession"
    const val PROP_ID_USER_TO_POST_PRODUCT_LIKE: String = "idUserToPostProduct"
    const val PROP_ID_PRODUCT_LIKE: String = "idProduct"
    const val PROP_TIMESTAMP_LIKE: String = "timestamp"

    const val PROP_ID_OPINION: String = "id"
    const val PROP_ID_USER_CREATOR_OPINION: String = "idUserCreator"
    const val PROP_ID_USER_RECEPTOR_OPINION: String = "idUserReceptor"
    const val PROP_MSG_OPINION: String = "msg"
    const val PROP_ASSESSMENT_OPINION: String = "assessment"
    const val PROP_TIMESTAMP_OPINION: String = "timestamp"

    const val TITLE_NOTIFICATION: String = "title"
    const val BODY_NOTIFICATION: String = "body"
    const val CONTENT_TYPE_API_NOTIFICATION: String = "Content-Type:application/json"
    const val AUTHORIZATION_KEY_API_NOTIFICATION: String = "Authorization:key=AAAAVcKXk0c:APA91bE7B8iTblTgAL2Ne8v6QHpd7SkL2PnyZmwZdJWJ5H9kEzAHXfMv2iTGd1SntsYsIAJ6KTUmgZTCw7KILQItuKYmjc4zG3298gxc-FsnnDPTeBG3L-3po4w--ppTpYAIWBmCpMFq"
    const val URL_API_NOTIFICATION: String = "https://fcm.googleapis.com/"

    const val BUNDLE_CHAT: String = "chat"
    const val PROP_ID_USER_TO_SESSION_CHAT: String = "idUserToSession"
    const val PROP_ID_USER_TO_CHAT_CHAT: String = "idUserToChat"
    const val PROP_IS_WRITING_CHAT: String = "isWriting"
    const val PROP_TIMESTAMP_CHAT: String = "timestamp"
}

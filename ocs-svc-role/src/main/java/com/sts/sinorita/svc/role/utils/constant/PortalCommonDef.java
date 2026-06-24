package com.sts.sinorita.svc.role.utils.constant;

public final class PortalCommonDef {
    public enum PortalThreadLocalEnum {
        mac, srcId;
    }

    public static final String APP_CODE = EnvironmentUtil.getValue("app.appCode");

    public static final String PRODUCT_MODE = EnvironmentUtil.getValue("app.product.mode");

    public static final String AVAILABLE = "A";

    public static final String DISABLE = "X";

    public static final String YES = "Y";

    public static final String NO = "N";

    public static final String STATUS_DRAFT = "D";

    public static final String STATUS_EXPIRED = "E";

    public static final String STATUS_CANCEL = "C";

    public static final String STATUS_ACKNOWLEDGE = "AC";

    public static final String SYSTEM_TERMS = "S";

    public static final String UM_STAFF_TERMS = "O";

    public static final Long OPERATORS_SRC = Long.valueOf(2L);

    public static final Long DEALER_SRC = Long.valueOf(3L);

    public static final Long PARTNER_SRC = Long.valueOf(4L);

    public static final String UM_APP_CODE = "UM";

    public static final String TM_APP_CODE = "TM";

    public static final String OML_APP_CODE = "OML";

    public static final String MTML_APP_CODE = "MTML";

    public static final String DSCM_APP = "DSCMAPP";

    public static final String MTN_APP_CODE = "Rwanda";

    public static final String WOM_APP_CODE = "WOM";

    public static final String BSNL_APP_CODE = "BSNL";

    public static final String GAIA_APP_CODE = "GAIA";

    public static final String ESALES_WEB_APP_CODE = "esalesWeb";

    public static final String GAIA_LOGIN_TYPE_UC = "User";

    public static final String V8_PRODUCT_MODE = "V81E";

    public static final String STAFF_DEVICE_IDN_CODE = "STAFF_DEVICE_IDN_CODE";

    public static final Integer IMPORT_MAX_DEFAULT = Integer.valueOf(200);

    public static final String ALTERNATE_CHANNEL_USER_PASSWORD = "umobile018";

    public static final int AREA_LEVEL_COUNTRY = 1;

    public static final int AREA_LEVEL_ZONE = 2;

    public static final int AREA_LEVEL_CIRCLE = 3;

    public static final int AREA_LEVEL_BA = 4;

    public static final int AREA_LEVEL_SSA = 5;

    public static final int AREA_LEVEL_EXCHANGE = 6;

    public static final String CACHE_USERCODE_ID_MAP = "UserIdMap:UserCode ";

    public static final String CACHE_USERID_DTO_MAP = "UserDtoMap:UserId ";

    public static final String CHANNEL_PUSH_NOTIFICATION = "channel_001";

    public static final String CHANNEL_SEND_NOTIFICATION = "channel_002";
}

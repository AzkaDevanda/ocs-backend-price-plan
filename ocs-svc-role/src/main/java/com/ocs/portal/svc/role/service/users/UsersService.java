package com.ocs.portal.svc.role.service.users;

import com.google.gson.Gson;
import com.ocs.portal.dto.response.BaseResponseDto;
import com.ocs.portal.dto.response.CustomeResponse;
import com.ocs.portal.entity.*;
import com.ocs.portal.svc.role.dto.request.*;
import com.ocs.portal.svc.role.dto.request.roles.*;
import com.ocs.portal.svc.role.dto.response.*;
import com.ocs.portal.svc.role.projection.*;
import com.ocs.portal.svc.role.repository.*;
import com.ocs.portal.svc.role.utils.*;
import com.sts.sinorita.entity.*;
import com.ocs.portal.svc.role.auth.repository.UserRepository;
import com.ocs.portal.svc.role.auth.service.JWTBlacklist;
import com.ocs.portal.svc.role.auth.service.UserService;
import com.sts.sinorita.svc.role.dto.request.*;
import com.ocs.portal.svc.role.dto.request.common.PagingRequestDto;
import com.sts.sinorita.svc.role.dto.request.roles.*;
import com.sts.sinorita.svc.role.dto.response.*;
import com.ocs.portal.svc.role.mapper.BfmUserMapper;
import com.ocs.portal.svc.role.mapper.RoleMapper;
import com.sts.sinorita.svc.role.projection.*;
import com.sts.sinorita.svc.role.repository.*;
import com.ocs.portal.svc.role.repository.custom.BfmStaffCustomRepository;
import com.ocs.portal.svc.role.security.EmailSender;
import com.ocs.portal.svc.role.security.PasswordGeneratorRandom;
import com.ocs.portal.svc.role.service.roles.LogService;
import com.ocs.portal.svc.role.service.roles.RoleUserService;
import com.sts.sinorita.svc.role.utils.*;
import com.ocs.portal.svc.role.utils.constant.PortalCommonDef;
import jakarta.mail.MessagingException;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

import static com.ocs.portal.svc.role.utils.Const.FORCE_LOGIN;

@Transactional(rollbackFor = {Exception.class, RuntimeException.class, BeansException.class, MessagingException.class})
@Service
public class UsersService {


    @Autowired
    BfmUserPortalRepository bfmUserPortalRepository;

    @Autowired
    StafService stafService;

    @Autowired
    StaffHisService staffHisService;

    @Value("${app.appCode}")
    private String appCode;

    //    @Autowired
    BfmSecurityRuleUmRepository bfmSecurityRuleUmRepository;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    PasswordGeneratorRandom passwordGeneratorRandom;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    BfmStaffRepository bfmStaffRepository;

    @Autowired
    PageUtils pageUtils;

    @Autowired
    MessageUtil messageUtil;

    @Autowired
    BfmSecurityRuleRepository bfmSecurityRuleRepository;

    @Autowired
    BfmStaffRepository staffRepository;

    @Autowired
    BfmStaffCustomRepository bfmStaffCustomRepository;

    @Autowired
    private LogService logService;

    @Value("${app.product.mode}")
    String productMode;

    Logger logger = LoggerFactory.getLogger(UsersService.class);

    @Autowired
    private RoleUserService roleUserService;

    @Autowired
    BfmUserRepository bfmUserRepository;

    @Autowired
    SecurityRulesService securityRulesService;

    @Autowired
    BfmSecurityRuleRepository bfmSecurityRulesRepository;

    @Autowired
    BfmUserHisRepository bfmUserHisRepository;

    String subject = "Password Baru untuk Akun Anda";

    @Autowired
    BfmStaffHisRepository bfmStaffHisRepository;

    @Autowired
    BfmStaffAttrRepository bfmStaffAttrRepository;

    @Autowired
    BfmUserMapper userMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Autowired
    PortalRepository portalRepository;

    @Autowired
    Gson gson;

    @Autowired
    BfmUserPrivsRepository bfmUserPrivsRepository;

    public ResponseEntity<CustomeResponse> getRoleListByUserIdWithoutJob(Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", queryRoleListByUserIdWithoutJob(userId)));
    }

    private List<UserRoleExtDto> queryRoleListByUserIdWithoutJob(Long userId) {
        logger.info("queryRoleListByUserIdWithoutJob");
        List<UsersRoleProjection> list = userRoleRepository.selectRoleListByUserIdWithoutJob(userId, ThreadLocalMap.getSpId());
        return roleMapper.toListUserRoleExtDto(list);
    }

    public ResponseEntity<CustomeResponse> queryUserListRc(UserReqParam userDto, PagingRequestDto pagingRequest) {
        Pageable pageable = pageUtils.getPageAble(pagingRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", queryUserListPage(userDto, pageable)));
    }

    public Page<UserDto> queryUserListPage(UserReqParam user, Pageable pageable) {
        logger.info("queryUserListRc");
        logger.debug("queryUserListRc");

        Page<UserDto> pageList = queryUserList(user, pageable);
        logger.info("page size :" + pageList.getContent().size());
        List<UserDto> userList = pageList.getContent();
        String enableCircle = querySysParamValueByMask("BSNL_USERMANANGMENT_ZONE_CIRCLE_ENABLE");
        if ("true".equals(enableCircle))
            userList.forEach(userDto -> {
                Long userId = userDto.getUserId();
                Optional<StafDtoProjection> stafDtoProjection = bfmStaffRepository.selectStaff(null, userId);
                if (stafDtoProjection.isPresent()) {
                    StaffDto staff = roleMapper.toStaffDto(stafDtoProjection.get());
                    userDto.setCircle(staff.getExtStr07());
                }
            });
        return pageList;
    }

    private Page<UserDto> queryUserList(UserReqParam userDto, Pageable page) {
        logger.info("queryUserList");
        String username = null;
        String userCode = null;
        String state = null;
        String isLock = null;
        if (userDto.getUserName() != null) {
            username = userDto.getUserName();
        }
        if (userDto.getUserCode() != null) {
            userCode = userDto.getUserCode();
        }
        if (userDto.getState() != null) {
            state = userDto.getState();
        }
        if (userDto.getIsLock() != null) {
            isLock = userDto.getIsLock();
        }
        Page<UserDtoProjection> list = bfmUserRepository.selectUserList(username, userCode, state, isLock, page);
        logger.info("LIST :" + list.getContent().size());
        List<UserDto> listUser = roleMapper.toUserDtoFromUserDtoProjection(list.getContent());
        return new PageImpl<>(listUser, page, list.getTotalElements());
    }

    public String querySysParamValueByMask(String mask) {
        ParamsDto params = securityRulesService.querySysParamValueByMask(mask);
        return (params == null) ? "" : params.getCurrentValue();
    }

    public ResponseEntity<CustomeResponse> addUsers(UserDto user) {
        try {
            String enableSend = querySysParamValueByMask("CREATE_USER_POT_ENABLE_SMS");
            if ("true".equals(enableSend) && StringUtils.isEmpty(user.getPhone())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageUtil.getMessage("PROD_USER_PHONE_000003"));
            }
            String userName = user.getUserName();
            String replaceName = userName.replace(" ", "");
            user.setUserName(replaceName);
            String userCode = user.getUserCode();
            String replace = userCode.replace(" ", "");
            user.setUserCode(replace);
            UserDto userDto = addUser(user);

            addOrUpdateStaff(userDto);
            logService.addAuthLog(LogEvent.ADD_USER, "created a new user " + user.getUserCode(), 0);
            return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", userDto));

        } catch (Exception e) {
            logService.addAuthLog(LogEvent.ADD_USER, "created a new user " + user.getUserCode(), 1);
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new CustomeResponse(HttpStatus.NOT_MODIFIED.value(), HttpStatus.NOT_MODIFIED.getReasonPhrase(), "created a new user failed"));
        }
    }

    public UserDto addUser(UserDto user) throws MessagingException {
        String isDefaultPwd = querySysParamValueByMask("FORGOT_PWD_RESET_DEFAULT_PWD_ENABLE");
        String defaultPwd = querySysParamValueByMask("USER_DEFAULT_CURRENT_VALUE");
        String enableSend = querySysParamValueByMask("CREATE_USER_POT_ENABLE_SMS");
        String initialPwdExpireDays = querySysParamValueByMask("INITIAL_PWD_EXPIRE_DAYS");
        boolean fromDscm = StringUtils.isNotEmpty(user.getPwd());
//        boolean needSendMessage = (!fromDscm && "true".equals(enableSend));
//        if (needSendMessage) {
        StringSanitizer.sanitize(user);
        if ("false".equals(isDefaultPwd)) {
            //generate pasword.
            logger.warn("GENERATE DEF PWD PROCESS");
            defaultPwd = generatePwd(user.getUserCode(), LogEvent.RESET_PASSWORD);
            user.setPwd(defaultPwd);
            sendAddUserMsg(user, defaultPwd);
        }
//        }
        if (!fromDscm && !"-1".equals(initialPwdExpireDays) && StringUtils.isNumeric(initialPwdExpireDays)) {
            LocalDateTime nowDate = LocalDateTime.now();
//            LocalDateTime pwdExpDate = nowDate.plusDays(Long.parseLong(initialPwdExpireDays));
//            user.setPwdExpDate(pwdExpDate);
        }
        UserDto userDto = prodUserManagerAddUser(user);


        userDto.setPwd(null);
//        PLMThreadLocal.mixin("plm_add_user", user.getUserId());
        return userDto;
    }

    public void resetPassword(Long userId, ResetPwdRequest request) throws MessagingException {
        logger.info("resetPassword");
        updateResetPwd(userId, request);
        logService.addAuthLog(LogEvent.RESET_PASSWORD, "reset the password for " + request.getUserCode(), 0);
    }


    public void editPasswordByUser(Long userId, ResetPwdRequest request) throws MessagingException {
        // try {
        ParamsDto dto = securityRulesService.querySysParamValueByMask("SECURITY_LEVEL");
        Optional<SecurityRuleProjection> securityRuleProjection = bfmSecurityRuleRepository.querySecurityRule(dto.getCurrentValue());
        SecurityRuleDto securityConfig = new SecurityRuleDto();
        if (securityRuleProjection.isPresent()) {
            securityConfig = roleMapper.toSecurityRule(securityRuleProjection.get());
        }

        BfmUser bfmUser = bfmUserRepository.findBfmUserByUserId(userId, request.getUserName(), request.getUserCode())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "users not found"));

        String nakedCurrPass = bfmUser.getPwd().replace("{bcrypt}", "");

        logger.info("ini encryptOldPwd {} ini bfmPwd {} ini matches {}", request.getOldPwd(), nakedCurrPass, passwordEncoder.matches(request.getOldPwd(), nakedCurrPass));

        if(!passwordEncoder.matches(request.getOldPwd(), nakedCurrPass)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect current password. Please re-enter your current password to continue.");
        }
        String encryptPwd = "{bcrypt}".concat(decrypt(request.getNewPwd()));
        bfmUser.setPwd(encryptPwd);
        Long pwdExcDays = securityConfig.getPwdExcDays();
        LocalDateTime pwdExpDate = null;
        if (pwdExcDays != null && pwdExcDays.intValue() > 0) {
            LocalDateTime nowDate = LocalDateTime.now();
            pwdExpDate = nowDate.plusDays(pwdExcDays);
        }
        if (pwdExpDate != null) {
            bfmUser.setPwdExpDate(pwdExpDate);
        }
        bfmUser.setUpdateDate(LocalDateTime.now());
        bfmUser.setForceLogin("");
        bfmUserRepository.save(bfmUser);
        logger.info("edit password for bfmUser " + bfmUser.getUserName());
        UserDto userDto = roleMapper.userDto(bfmUser);
        UserHisDto userHisDto = roleMapper.toUserHisDto(userDto);
        addUserHis(userHisDto, "Modify Password", bfmUser.getUserId().longValue(), ThreadLocalMap.getLoginIp());
        // } catch (Exception e) {
        //     logger.error("editPasswordByUser" + e.getMessage());
        //     // TODO: handle exception
        // }

    }

    private void updateResetPwd(Long userId, ResetPwdRequest request) throws MessagingException {
        BfmUser user = bfmUserRepository.findBfmUserByUserId(userId, request.getUserName(), request.getUserCode())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "users not found"));
        String defaultPwd = generatePwd(user.getUserCode(), LogEvent.RESET_PASSWORD);
        String pwd = "{bcrypt}" + decrypt(defaultPwd);
        user.setPwd(pwd);
        user.setUpdateDate(LocalDateTime.now());
        user.setForceLogin(FORCE_LOGIN);
        bfmUserRepository.save(user);
        logger.info("reset Password success");
        UserDto userDto = roleMapper.userDto(user);
        sendAddUserMsg(userDto, defaultPwd);
    }

    public UserDto editUser(UserDto user) throws MessagingException {
        logger.info("editUser");
        String isDefaultPwd = querySysParamValueByMask("FORGOT_PWD_RESET_DEFAULT_PWD_ENABLE");
        String defaultPwd = querySysParamValueByMask("USER_DEFAULT_CURRENT_VALUE");
        String enableSend = querySysParamValueByMask("CREATE_USER_POT_ENABLE_SMS");
        String initialPwdExpireDays = querySysParamValueByMask("INITIAL_PWD_EXPIRE_DAYS");
        boolean fromDscm = StringUtils.isNotEmpty(user.getPwd());
        boolean needSendMessage = (!fromDscm && "true".equals(enableSend));
        StringSanitizer.sanitize(user);
        if (needSendMessage) {
            if ("false".equals(isDefaultPwd)) {
                defaultPwd = generatePwd(user.getUserCode(), LogEvent.RESET_PASSWORD);
                user.setPwd(defaultPwd);
                sendAddUserMsg(user, defaultPwd);
            }
        }
        if (!fromDscm && !"-1".equals(initialPwdExpireDays) && StringUtils.isNumeric(initialPwdExpireDays)) {
            LocalDateTime nowDate = LocalDateTime.now();
            LocalDateTime pwdExpDate = nowDate.plusDays(Long.parseLong(initialPwdExpireDays));
            user.setPwdExpDate(pwdExpDate);
        }
        UserDto userDto = prodUserManagerEditUser(user);
        userDto.setPwd(null);
        return userDto;
    }

    private void sendAddUserMsg(UserDto user, String defaultPwd) throws MessagingException {

        if (StringUtils.isNotEmpty(productMode) && productMode.indexOf("V8") == 0) {
            emailSender.send(user.getEmail(), subject, user.getUserName(), defaultPwd);
//            this.sendMessageServ.sendSmsPwdMessageByV8(user.getUserId(), defaultPwd, "POT_USER_PWD_SMS");
        } else {
            emailSender.send(user.getEmail(), subject, user.getUserName(), defaultPwd);
//            this.prodUserManager.sendUserPwdToCic(user.getUserId(), user.getSrcId(), defaultPwd, "SYS_USER_REGISTERE_SMS", 2);
        }
    }

    private UserDto prodUserManagerAddUser(UserDto user) {
        String userCode = user.getUserCode();
        String defPwd = user.getPwd();
        LocalDateTime pwdExpDate = null;
        Long pwdExpDays = null;
        Long userExpDays = null;
        boolean caseSensitive = false;

        try {
            ParamsDto dto = securityRulesService.querySysParamValueByMask("SECURITY_LEVEL");
            Optional<SecurityRuleProjection> securityRuleProjection = bfmSecurityRuleRepository.querySecurityRule(dto.getCurrentValue());
            SecurityRuleDto securityConfig = new SecurityRuleDto();
            if (securityRuleProjection.isPresent()) {
                securityConfig = roleMapper.toSecurityRule(securityRuleProjection.get());
            }
            pwdExpDays = securityConfig.getPwdExcDays();
            userExpDays = (long) securityConfig.getUserExcDays();
            String ignoreCase = securityConfig.getUserCodeComposition();
            if (ignoreCase != null && ("3".equals(ignoreCase) || "4".equals(ignoreCase))) {
                caseSensitive = true;
            }
            if (!caseSensitive) {
                userCode = userCode.toUpperCase();
            }
        } catch (Exception e) {
            logger.error("prodUserManagerAddUser " + e.getMessage());
        }
        checkUserCode(caseSensitive, userCode);
        Long srcId = user.getSrcId();
        checkEmailAndPhone(user);

        isSameValueByName(user);

        LocalDateTime nowDate = LocalDateTime.now();
        user.setPwd("{bcrypt}" + decrypt(defPwd));
        setEffDate(user, nowDate);
        user.setCreatedDate(nowDate);
        user.setState("A");
        user.setStateDate(nowDate);
        user.setIsLocked("N");
        user.setLoginFail(Long.valueOf(0L));
        BfmSecurityRule securityRuleUmDto = null;

        assert pwdExpDays != null;
        pwdExpDate = LocalDateTime.now().plusDays(pwdExpDays.intValue());
        user.setPwdExpDate(pwdExpDate);
        user.setUpdateDate(nowDate);

        BfmUser bfmUser = roleMapper.toBfmUser(user);
        bfmUser.setSpId(Math.toIntExact(ThreadLocalMap.getSpId()));
        bfmUser.setForceLogin(FORCE_LOGIN);
        bfmUserRepository.save(bfmUser);

        Long recUserId = bfmUser.getUserId().longValue(); //ThreadLocalMap.getUserId();
        String srcIp = ThreadLocalMap.getLoginIp();
        UserHisDto userHisDto = roleMapper.toUserHisDto(user);
        addUserHis(userHisDto, "Add User", recUserId, srcIp);
        return user;
    }

    private UserDto prodUserManagerEditUser(UserDto user) {
        String userCode = user.getUserCode();
        String defPwd = user.getPwd();
        LocalDateTime pwdExpDate = null;
        Long pwdExpDays = null;
        Long userExpDays = null;
        boolean caseSensitive = false;
        try {
            ParamsDto dto = securityRulesService.querySysParamValueByMask("SECURITY_LEVEL");
            Optional<SecurityRuleProjection> securityRuleProjection = bfmSecurityRuleRepository.querySecurityRule(dto.getCurrentValue());
            SecurityRuleDto securityConfig = new SecurityRuleDto();
            if (securityRuleProjection.isPresent()) {
                securityConfig = roleMapper.toSecurityRule(securityRuleProjection.get());
            }
            pwdExpDays = securityConfig.getPwdExcDays();
            userExpDays = (long) securityConfig.getUserExcDays();
            String ignoreCase = securityConfig.getUserCodeComposition();
            if (ignoreCase != null && ("3".equals(ignoreCase) || "4".equals(ignoreCase))) {
                caseSensitive = true;
            }

        } catch (Exception e) {
            logger.error("prodUserManagerEditUser " + e.getMessage());
        }

        LocalDateTime nowDate = LocalDateTime.now();
        user.setPwd(decrypt(defPwd));
        setEffDate(user, nowDate);
        user.setState("A");
        user.setUpdateDate(nowDate);
        user.setIsLocked("N");
        user.setLoginFail(Long.valueOf(0L));

        assert pwdExpDays != null;
        pwdExpDate = LocalDateTime.now().plusDays(pwdExpDays.intValue());
        user.setPwdExpDate(pwdExpDate);
        user.setUpdateDate(nowDate);
        logger.info("userId :"+ user.getUserId());
        BfmUser bfmUser = bfmUserRepository.findBfmUserById(user.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "user not found"));
        userMapper.updateEntityFromDto(user, bfmUser);
        bfmUser.setSpId(Math.toIntExact(ThreadLocalMap.getSpId()));
        bfmUserRepository.save(bfmUser);
        logger.info("edit user success");
        return user;
    }

    public void isSameValueByName(UserDto user) {
        if (bfmUserRepository.isSameValueByName(user.getUserName()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageUtil.getMessage("46430127"));
        }
    }

    public void checkEmailAndPhone(UserDto user) {

        String enableSamePhoneOrEmail = querySysParamValueByMask("STAFF_EMAIL_UNIQUE");
        if (!"false".equals(enableSamePhoneOrEmail)) {
            return;
        }
        if (bfmUserRepository.chekEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageUtil.getMessage("46430112"));
        } else if (bfmUserRepository.chekPhone(user.getPhone()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageUtil.getMessage("46430117"));
        }
    }

    public void checkUserCode(boolean caseSensitive, String userCode) {
        logger.info("checkUserCode");
        if (bfmUserRepository.isSameValue(userCode).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageUtil.getMessage("46430068"));
        }

    }

    public String decrypt(String pwd) {
        if (pwd == null || StringUtils.isEmpty(pwd)) {
            pwd = querySysParamValueByMask("USER_DEFAULT_PWD");
        } else {
            try {
                pwd = this.passwordEncoder.encode(pwd);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return pwd;
    }

    public void setEffDate(UserDto user, LocalDateTime nowDate) {
        String isEffectiveNow = user.getIsEffectiveNow();
        if (isEffectiveNow != null && "Y".equalsIgnoreCase(isEffectiveNow)) {
            user.setUserEffDate(nowDate);
        } else {
            LocalDateTime userEffDate = user.getUserEffDate();
            if (userEffDate == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageUtil.getMessage("46430113"));
            }
            user.setUserEffDate(userEffDate);
        }
    }


    public void addUserHis(UserHisDto user, String comments, Long recUserId, String srcIp) {
        user.setRecUserId(recUserId);
        user.setIpAddress(srcIp);
        user.setRecCreateDate(LocalDateTime.now().toLocalDate());
        user.setComments(comments);
        user.setUserId(recUserId);
        if ("Add User".equals(comments))
            user.setOperatorType("A");
        BfmUserHis bfmUserHis = roleMapper.toBfmUserHis(user);
        bfmUserHisRepository.save(bfmUserHis);
        logger.info("addUserHis");
    }


    public void addOrUpdateStaff(UserDto user) {
        StaffParamDto staffParamDto = new StaffParamDto();
        staffParamDto.setUserId(user.getUserId());
        Optional<StafDtoProjection> optStafDto = bfmStaffRepository.selectStaff(null, user.getUserId());
        if (optStafDto.isPresent()) {
            StaffDto staffDto = roleMapper.toStaffDto(optStafDto.get());
            copyFromUser(user, staffDto);
            bfmStaffCustomRepository.updateStaff(staffDto);
        } else {
            staffParamDto.setCreatedDate(user.getCreatedDate().toLocalDate());
            staffParamDto.setStaffCode(user.getUserCode());
            staffParamDto.setStaffName(user.getUserName());
            staffParamDto.setSpId(ThreadLocalMap.getSpId());
            copyFromUser(user, (StaffDto) staffParamDto);
            Long currentStaffId = ThreadLocalMap.getStaffId();
            if (currentStaffId != null)
                staffParamDto.setCreator(String.valueOf(currentStaffId));

            inserStaff((StaffDto) staffParamDto);
            String comments = "Add new staff and user";
            Long operateUserId = ThreadLocalMap.getUserId();
            String remoteIp = ThreadLocalMap.getLoginIp();
            staffHisService.addStaffHis((StaffDto) staffParamDto, operateUserId, remoteIp, comments);

        }
    }

    void inserStaff(StaffDto staffDto) {
        BfmStaff bfmStaff = roleMapper.toBfmStaff(staffDto);
        bfmStaffRepository.save(bfmStaff);
    }

    public void copyFromUser(UserDto user, StaffDto staff) {
        logger.info("copyFromUser");
        String enableCircle = querySysParamValueByMask("BSNL_USERMANANGMENT_ZONE_CIRCLE_ENABLE");
        if ("true".equals(enableCircle)) {
            AttrDto attrDto = new AttrDto();
            attrDto.setAttrId("CIRCLE");
            attrDto.setAttrValue(user.getCircle());
            List<AttrDto> attrDtoList = new ArrayList<>();
            attrDtoList.add(attrDto);
            updateAttrData(attrDtoList, "BFM_STAFF", "STAFF_ID", staff.getStaffId().toString(), (staff.getSpId() == null) ? ThreadLocalMap.getSpId() : staff.getSpId());
            staff.setExtStr07(user.getCircle());
        }
        staff.setPhone(user.getPhone());
        staff.setEmail(user.getEmail());
        staff.setAddress(user.getAddress());
        staff.setStateDate(user.getStateDate().toLocalDate());
        staff.setState(user.getState());
        staff.setUpdateDate(LocalDateTime.now());
        logger.info("copyFromUser end");
    }

    public void updateAttrData(List<AttrDto> attrDataList, String tableName, String keyName, String keyValue, Long spId) {
        if (attrDataList == null)
            return;
        String extCode = "";
        String extValue = "";
        AttrDefDto attrDefDto = null;
        for (int i = 0; i < attrDataList.size(); i++) {
            AttrDto dictTemp = attrDataList.get(i);
            extCode = dictTemp.getAttrId();
            extValue = dictTemp.getAttrValue();

            Optional<AttrDefDtoProjection> attrDto = bfmStaffRepository.qryAttrDefDto(tableName, extCode, spId.intValue());
            if (attrDto.isPresent()) {
                attrDefDto = roleMapper.toAttrDef(attrDto);
                logger.info("toAttrDef");
            }

            if (attrDefDto != null && attrDefDto.getColumnName() != null && !"".equals(attrDefDto.getColumnName().trim()))
                if (attrDefDto.getTableName().contains("ATTR")) {
                    Long lKeyValue = Long.valueOf(Long.parseLong(keyValue));
                    mergeAttrValue(attrDefDto.getTableName(), keyName, lKeyValue, attrDefDto.getExtCode(), attrDefDto.getDisplayName(), extValue);
                } else {
                    logger.info("updateColValue()");
                    updateColValue(tableName, keyName, keyValue, attrDefDto.getColumnName(), extValue, attrDefDto.getColumnType(), attrDefDto.getInputType());
                }
            if (attrDefDto != null && !StringUtils.isEmpty(attrDefDto.getIsSyncAttr()) && "Y".equals(attrDefDto.getIsSyncAttr()))
                if ("BFM_STAFF".equals(tableName)) {
                    Optional<BfmAttrDtoProjection> bfmAttrs = bfmStaffRepository.qryBfmAttrByCode(extCode, spId);
                    BfmAttrDto bfmAttr = null;
                    if (bfmAttrs.isPresent()) {
                        bfmAttr = roleMapper.toBfmAttr(bfmAttrs);
                    }
                    assert bfmAttr != null;
                    Long attrId = bfmAttr.getAttrId();
                    Long staffId = Long.parseLong(keyValue);
                    BfmStaffAttrDto staffAttr = new BfmStaffAttrDto(staffId, attrId, extValue, spId);
                    Optional<StaffAttrProjection> opt = bfmStaffRepository.qryBfmStaffAttr(staffId, attrId);
                    BfmStaffAttrDto oldStaffValue = roleMapper.toBfmStaff(opt);
                    if (oldStaffValue == null) {
                        BfmStaffAttr staffAttr1 = roleMapper.toBfmStaffFromBfmStaffAttrDto(staffAttr);
                        if (attrId != null) {
                            staffAttr1.setAttrCode(attrId.toString());
                        }
                        bfmStaffAttrRepository.save(staffAttr1);
                    } else {
                        bfmStaffAttrRepository.updateAttrValue(oldStaffValue.getAttrValue(), oldStaffValue.getStaffId(), oldStaffValue.getAttrCode());
                    }
                }
        }
    }

    private void mergeAttrValue(String tableName, String keyName, Long keyValue, String extCode, String displayName, String extValue) {
        Map<String, Object> currentValueInAttrTable = bfmStaffCustomRepository.getCurrentValueInAttrTable(tableName, keyName, keyValue.toString(), extCode);
        if (extValue == null && currentValueInAttrTable != null) {
            logger.info("deleteAttrExtValue");
            bfmStaffCustomRepository.deleteAttrExtValue(tableName, keyName, keyValue, extCode);
        } else if (extValue != null && currentValueInAttrTable == null) {
            logger.info("insertAttrExtValue");
            bfmStaffCustomRepository.insertAttrExtValue(tableName, keyName, keyValue, extCode, displayName, extValue);
        } else if (extValue != null) {
            logger.info("updateAttrExtValue");
            bfmStaffCustomRepository.updateAttrExtValue(tableName, keyName, keyValue, extCode, extValue);
        }
    }


    public int updateColValue(String tableName, String keyName, String keyValue, String colName, String colValue, String dataType, String inputType) {
        //sudah selesai
        logger.info("updateColValue");
        AttrDefDto attrDefDto = null;
        Optional<AttrDefDtoProjection> attrDto = bfmStaffRepository.qryAttrDefDto(tableName, colName, null);
        if (attrDto.isPresent()) {
            attrDefDto = roleMapper.toAttrDef(attrDto);
            logger.info("toAttrDef");
        }

        Long lKeyValue = Long.valueOf(Long.parseLong(keyValue));
        if ("d".equalsIgnoreCase(dataType)) {
            logger.info("updateColValue dataType : " + dataType);
            if (!StringUtils.isEmpty(colValue) || (attrDefDto != null && "Y".equalsIgnoreCase(attrDefDto.getNullAble()))) {
                bfmStaffCustomRepository.updateColValue(tableName, keyName, lKeyValue, colName, (colValue == null) ? null : String.valueOf(DateUtils.formatSQLDate(colValue)));
                logger.info("updateColValue colValue : " + colValue);
                return 1;
            }
        } else if ("i".equalsIgnoreCase(dataType)) {
            logger.info("updateColValue dataType : " + dataType);
            if (!StringUtils.isEmpty(colValue) || (attrDefDto != null && "Y".equalsIgnoreCase(attrDefDto.getNullAble()))) {
                logger.info("updateColValue colValue : " + colValue);
                if ("2".equals(inputType)) {
                    logger.info("updateColValue inputType : " + inputType);
                    bfmStaffCustomRepository.updateColValue(tableName, keyName, lKeyValue, colName, colValue);
                    return 1;
                } else {
                    logger.info("updateColValue updateColValue ");
                    bfmStaffCustomRepository.updateColValue(tableName, keyName, lKeyValue, colName, (colValue == null) ? null : Integer.valueOf(Integer.parseInt(colValue)));
                    return 1;
                }
            }
        } else if (!StringUtils.isEmpty(colValue) || (attrDefDto != null && "Y".equalsIgnoreCase(attrDefDto.getNullAble()))) {
            logger.info("updateColValue nullable = Y and colValue" + colValue);
            bfmStaffCustomRepository.updateColValue(tableName, keyName, lKeyValue, colName, colValue);
            return 1;
        }
        return 0;
    }


    public String generatePwd(String userCode, LogEvent logEvent) {
        String pwd;
        logger.info("begin to generation random password.");
        if (!"UM".equalsIgnoreCase(this.appCode) && !"TM".equals(this.appCode)) {
            String pwdRule = querySysParamValueByMask("RANDOM_NUMBER_RULE");
            String[] strArr = pwdRule.split("\\|");
            int ruleId = Integer.parseInt(strArr[1]);
            pwd = passwordGeneratorRandom.generatePassword(10);
        } else {
            SecurityRuleUm securityRuleUmDto = bfmSecurityRuleUmRepository.selectSecurityRuleByRuleId(Long.valueOf(3L))
                    .orElseThrow(() -> new RuntimeException("securityRuleUmDto not found"));
            pwd = passwordGeneratorRandom.generatePassword(10);
        }
        logService.addAuthLog(logEvent, "Generation random password for " + userCode, 0);
        logger.info("generation random password for " + userCode + " : ");
        return pwd;
    }

    public ResponseEntity<CustomeResponse> lockUser(String token, Integer userId, String opReason) {
        logger.info("begin to lock user.");
        UserDto userDto = queryUserByUserId(userId);
        String comments = "lock user " + userDto.getUserCode();
        if (StringUtils.isNotBlank(opReason)) {
            comments = comments.concat(" [" + opReason + "]");
        }
        modUserLock(userId.longValue(), "Y", comments);
        clearToken(token);
        logService.addAuthLog(LogEvent.LOCK_USER, comments, 1);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", null));
    }

    private void clearToken(String token) {

        // Add the token to the blacklist
        JWTBlacklist.addToBlacklist(token);

        // Optionally clear the authentication context (for stateless auth)
        SecurityContextHolder.clearContext();
        logger.info("clear token success : ");
    }

    public ResponseEntity<CustomeResponse> unLock(Integer userId, String opReason) {
        logger.info("begin to unLock user.");
        UserDto userDto = queryUserByUserId(userId);
        String comments = "unLock user " + userDto.getUserCode();
        if (StringUtils.isNotBlank(opReason)) {
            comments = comments.concat(" [" + opReason + "]");
        }
        modUserLock(userId.longValue(), "N", comments);
        logService.addAuthLog(LogEvent.LOCK_USER, comments, 1);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", null));
    }


    private UserDto queryUserByUserId(Integer userId) {
        BfmUser bfmUser = bfmUserRepository.selectUserByUserId(userId).orElseThrow(() -> new RuntimeException("user not found"));
        return roleMapper.userDto(bfmUser);
    }

    public void modUserLock(Long userId, String lock, String comments) {
        logger.info("begin to mod user lock.");
//        UserDto user = new UserDto();
        BfmUser bfmUser = bfmUserRepository.selectUserByUserId(userId.intValue())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "users not found"));

        UserDto user = roleMapper.userDto(bfmUser);
        user.setUserId(userId);
        user.setIsLocked(lock);
        user.setUnlockDate(LocalDateTime.now());
        user.setUpdateDate(LocalDateTime.now());
        userMapper.updateEntityFromDto(user, bfmUser);

        bfmUserRepository.save(bfmUser);
        logger.info("modify user " + user.getUserId() + " lock success : " + lock);

        UserHisDto userHisDto = roleMapper.toUserHisDto(user);
        logger.info("modify user " + user.getUserId() + " lock success : " + lock);
        addUserHis(userHisDto, comments, bfmUser.getUserId().longValue(), ThreadLocalMap.getLoginIp());
        logService.addAuthLog(LogEvent.LOCK_USER, comments, 1);

    }

    public ResponseEntity<CustomeResponse> disableUser(Integer userId, String opReason) {
        logger.info("disableUser");
        if (!disableUsers(userId.longValue(), opReason)) {
            throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, "failed disable user");
        }
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", null));
    }

    public ResponseEntity<CustomeResponse> enableUser(Integer userId, String opReason) {
        logger.info("enableUser");
        if (!enableUsers(userId.longValue(), opReason)) {
            throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, "failed enable user");
        }
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", null));
    }

    private Boolean enableUsers(Long userId, String opReason) {
        UserDto userDto = queryUserByUserId(userId.intValue());
        String comments = "enable user " + userDto.getUserCode();
        if (StringUtils.isNotBlank(opReason))
            comments = comments.concat(" [" + opReason + "]");
        try {
            StaffDto staffDto = null;
            StafDtoProjection st = bfmStaffRepository.selectStaff(null, userId).orElse(null);
            if (st != null) {
                staffDto = roleMapper.toStaffDto(st);
                Long staffId = staffDto.getStaffId();
                stafService.disableStaff(staffId, Boolean.valueOf(true));
            } else {
                this.modifyUserStatus(userId, "A");
            }
            logService.addAuthLog(LogEvent.ENABLE_USER, comments, 0);
            logger.info("enableUser success");
            return true;
        } catch (Exception e) {
            logService.addAuthLog(LogEvent.ENABLE_USER, comments, 1);
            return false;
        }
    }


    private Boolean disableUsers(Long userId, String opReason) {
        UserDto userDto = queryUserByUserId(userId.intValue());
        String comments = "disabled user " + userDto.getUserCode();
        if (StringUtils.isNotBlank(opReason))
            comments = comments.concat(" [" + opReason + "]");
        try {
            StaffDto staffDto = null;
            StafDtoProjection st = bfmStaffRepository.selectStaff(null, userId).orElse(null);
            if (st != null) {
                staffDto = roleMapper.toStaffDto(st);
                Long staffId = staffDto.getStaffId();
                stafService.disableStaff(staffId, Boolean.valueOf(true));
            } else {
                this.modifyUserStatus(userId, "X");
            }
            logService.addAuthLog(LogEvent.DISABLE_USER, comments, 0);
            logger.info("disableUsers success");
            return true;
        } catch (Exception e) {
            logService.addAuthLog(LogEvent.DISABLE_USER, comments, 1);
            return false;
        }
    }

    private void modifyUserStatus(Long userId, String status) {
//        if (!"A".equals(status)) {
        modUserStatus(userId, status);
//        }
    }

    public void modUserStatus(Long userId, String status) {
        logger.info("modUserStatus");
        UserDto user = queryUserByUserId(userId.intValue());
        user.setState(status);
        LocalDateTime nowDate = LocalDateTime.now();
        user.setStateDate(nowDate);
        user.setUpdateDate(nowDate);
        user.setUserExpDate(null);
        if ("X".equals(status)) {
            user.setUserExpDate(nowDate);
        } else if (!PortalCommonDef.OPERATORS_SRC.equals(user.getSrcId()) && !"UM".equalsIgnoreCase(this.appCode) && !"TM".equals(this.appCode)) {

            String currentValue = querySysParamValueByMask("SECURITY_LEVEL");
            ProdSecurityRuleDto securityConfig = null;
            Optional<SecurityRuleProjection> dt = bfmSecurityRuleRepository.querySecurityRule(currentValue);
            if (dt.isPresent()) {
                securityConfig = roleMapper.toSecurityProd(dt.get());
            }
            assert securityConfig != null;
            Long userExpDays = securityConfig.getUserExpireDays();

            if (null != userExpDays && userExpDays.intValue() > 0) {
                LocalDateTime expDateTime = nowDate.plusDays(userExpDays.longValue());
                user.setUserExpDate(expDateTime);
            }
        }
        BfmUser bfmUser = new BfmUser();
        userMapper.updateEntityFromDto(user, bfmUser);
        bfmUser.setUserId(user.getUserId());
        bfmUserRepository.save(bfmUser);

        UserHisDto userHisDto = roleMapper.toUserHisDto(user);
        Long recUserId = bfmUser.getUserId(); //ThreadLocalMap.getUserId();
        String srcIp = ThreadLocalMap.getLoginIp();
        addUserHis(userHisDto, "A".equals(status) ? "Enable User" : "Disable User", recUserId, srcIp);
        logger.info("modUserStatus success");
    }


    public ResponseEntity<CustomeResponse> removeUser(Long userId, String opReason) {
        StaffDto staffDto = stafService.queryStaffByUserId(userId);
        if (ObjectUtils.isNotEmpty(staffDto)) {
            stafService.removeStaff(staffDto.getStaffId());
        }
//        else {
        bfmUserRepository.deleteUserByUserId(userId.intValue());
//        }
        String comments = "delete user " + userId;
        if (StringUtils.isNotBlank(opReason))
            comments = comments.concat(" [" + opReason + "]");
        logService.addAuthLog(LogEvent.DELETE_USER, comments, 0);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", null));
    }

    public ResponseEntity<BaseResponseDto> queryUserPortalListByUserId(Long userId) {
        logger.info("queryUserPortalListByUserIds");
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseDto("success", 200, queryUserPortalListByUserIds(userId)));
    }


    private List<UserPortalDto> queryUserPortalListByUserIds(Long userId) {
        return roleMapper.toUserPortalListDto(portalRepository.selectUserPortalListByUserId(userId));
    }

    public ResponseEntity<BaseResponseDto> grantPortalToUsers(Long userId, Long defaultPortalId, List<Long> portalList) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("roleList", portalList);
        try {
            addPortalToUser(userId, defaultPortalId, portalList);
            logService.addAuthLog(LogEvent.GRANT_PORTAL_TO_USER, gson.toJson(map), 0);
        } catch (Exception e) {
            logService.addAuthLog(LogEvent.GRANT_PORTAL_TO_USER, gson.toJson(map), 1);
            throw e;
        }
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseDto("success", 200, null));
    }

    public ResponseEntity<BaseResponseDto> queryRolePortalListByUserId(Long userId) {
        List<RolePortalDto> list = queryRolePortalListByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseDto("success", 200, list));
    }

    private List<RolePortalDto> queryRolePortalListByUser(Long userId) {
        return roleMapper.toListRolePortalDto(portalRepository.queryRolePortalListByUser(userId));
    }

    private void addPortalToUser(Long userId, Long defaultPortalId, List<Long> portalList) {
        logger.info("addPortalToUser");
        addUserPortals(userId, defaultPortalId, portalList);
    }

    private void addUserPortals(Long userId, Long defaultPortalId, List<Long> portalList) {
        bfmUserPortalRepository.deleteUserPortalByUserId(userId);
        List<UserPortalDto> userPortalList = new ArrayList<>(portalList.size());
        for (int i = 0, size = portalList.size(); i < size; i++) {
            UserPortalDto userPortal = new UserPortalDto();
            userPortal.setUserId(userId);
            userPortal.setPortalId(portalList.get(i));
            userPortal.setState("A");
            userPortal.setStateDate(LocalDateTime.now());
            userPortalList.add(userPortal);
        }
        List<BfmUserPortal> list = roleMapper.toBfmUserPortalList(userPortalList);
        if (!list.isEmpty()) {
            bfmUserPortalRepository.saveAll(list);
        }
        if (null != defaultPortalId) {
            bfmUserPortalRepository.updatePortalIdByUserId(defaultPortalId, userId, LocalDateTime.now());
            logger.info("updatePortalIdByUserId success");
        }
        logger.info("addUserPortals success");
    }

    public ResponseEntity<BaseResponseDto> getPortalListByUserId(Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseDto("success", 200, getPortalListByUser(userId)));
    }

    private List<PortalDto> getPortalListByUser(Long userId) {
        return roleMapper.toListPortalDto(bfmUserPortalRepository.selectPortalListByUserIdAndSpId(userId, null));
    }

    public ResponseEntity<BaseResponseDto> qryUserOwnedPortletList(Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseDto("success", 200, qryUserOwnedPortletListQr(userId)));
    }

    private List<PortletsDto> qryUserOwnedPortletListQr(Long userId) {
        return roleMapper.toListPortletsDto(bfmUserPortalRepository.queryUserOwnedPortletList(userId));
    }

    public ResponseEntity<BaseResponseDto> queryPrivListByUserIds(Long userId) {
        return ResponseEntity.ok().body(new BaseResponseDto("success", 200, queryPrivListByUserId(userId)));
    }

    private List<UserPrivDto> queryPrivListByUserId(Long userId) {
        return roleMapper.toListUserPriv(bfmUserPortalRepository.selectPrivListByUserId(userId, "M"));
    }

    public ResponseEntity<BaseResponseDto> addPrivsToUserPrivNew(Long userId, List<ProdPrivOperDto> list) {
        logger.info("addPrivsToUserPrivNew begin ...");
        String comments = "";
        try {
            UserDto userDto = queryUserByUserId(userId.intValue());
            List<ProdUserPrivDto> userPrivDtoList = new ArrayList<>();
            StringBuffer sb = new StringBuffer("");
            String type = "privilege";
            for (ProdPrivOperDto privOperDto : list) {
                if (privOperDto.getPrivName() != null) {
                    sb.append(privOperDto.getPrivName()).append(",");
                } else {
                    sb.append(privOperDto.getPortletName()).append(",");
                    type = "portlets";
                }
                ProdUserPrivDto userPrivDto = new ProdUserPrivDto();
                userPrivDto.setPrivLevel(privOperDto.getPrivLevel());
                userPrivDto.setPrivId(privOperDto.getPrivId());
                userPrivDto.setUserId(userId);
                userPrivDtoList.add(userPrivDto);
            }
            comments = String.format("granted %s %s to user %s", new Object[]{type, sb.substring(0, sb.length() - 1), userDto
                    .getUserCode()});
            logService.addAuthLog(LogEvent.GRANT_PRIVILEGE_TO_USER, comments, 0);
            addUserPrivs(userId, userPrivDtoList);
        } catch (Exception e) {
            logService.addAuthLog(LogEvent.GRANT_PRIVILEGE_TO_USER, comments, 1);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.debug("addPrivsToUserPrivNew end ...");
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseDto("success", 200, null));
    }

    private void addUserPrivs(Long userId, List<ProdUserPrivDto> userPrivDtoList) {
        if (!userPrivDtoList.isEmpty()) {
            for (int i = 0; i < userPrivDtoList.size(); i++) {
                ProdUserPrivDto temp = userPrivDtoList.get(i);
                temp.setUserId(userId);
            }
            List<BfmUserPriv> bfmUserPriv = userMapper.toBfmUserPrivList(userPrivDtoList);
            bfmUserPrivsRepository.saveAll(bfmUserPriv);
            logger.info("addUserPrivs success");
        }
    }

    public ResponseEntity<BaseResponseDto> delPrivsFromUserPrivNew(Long userId, List<ProdPrivOperDto> list) {
        this.logger.debug("degrantPrivToRoleNew begin ...");
        String comments = "";
        try {
            List<UserPrivDto> privList = new ArrayList<>();
            UserDto userDto = queryUserByUserId(userId.intValue());
            StringBuffer sb = new StringBuffer("");
            String type = "privilege";
            for (PrivOperDto privOperDto : list) {
                UserPrivDto userPrivDto = new UserPrivDto();
                userPrivDto.setUserId(userId);
                userPrivDto.setPrivId(privOperDto.getPrivId());
                if (privOperDto.getPrivName() != null) {
                    sb.append(privOperDto.getPrivName()).append(",");
                } else {
                    sb.append(privOperDto.getPortletName()).append(",");
                    type = "portlets";
                }
                privList.add(userPrivDto);
            }
            comments = String.format("Cancel %s %s from user %s", new Object[]{type, sb.substring(0, sb.length() - 1), userDto
                    .getUserCode()});
            removePrivsFromUserPriv(userId, privList);
            logService.addAuthLog(LogEvent.UNGRANT_PRIVILEGE_TO_USER, comments, 0);
        } catch (Exception e) {
            logService.addAuthLog(LogEvent.UNGRANT_PRIVILEGE_TO_USER, comments, 1);
            throw e;
        }
        this.logger.debug("degrantPrivToRoleNew end ...");
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseDto("success", 200, null));
    }

    public int removePrivsFromUserPriv(Long userId, List<UserPrivDto> privIdList) {
        logger.info("delUserPrivsByUserId begin ...");
        if (!privIdList.isEmpty()) {
            for (int i = 0; i < privIdList.size(); i++) {
                UserPrivDto temp = privIdList.get(i);
                temp.setUserId(userId);
            }
            List<BfmUserPriv> bfmUserPriv = userMapper.toBfmUserPrivsList(privIdList);
            bfmUserPrivsRepository.deleteAll(bfmUserPriv);
            logger.info("delUserPrivsByUserId end ...");
        }
        return 0;
    }

    public ResponseEntity<CustomeResponse> queryUserHistory(UserHisFilterDto dto, PagingRequestDto pagingRequestDto) {
        Pageable pageable = pageUtils.getPageAble(pagingRequestDto);
        Page<UserHisDto> list = queryUserHistory(dto, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, "success", list, list.getTotalElements(), list.getTotalPages()));
    }

    public Page<UserHisDto> queryUserHistory(UserHisFilterDto dto, Pageable page) {
        Page<UserHisProjection> list1 = bfmUserHisRepository.selectUserHistoryList(dto.getStartDate(), dto.getEndDate(), null, dto.getUserName(), page);
        return new PageImpl<>(roleMapper.toUserListHisDto(list1), page, list1.getTotalElements());

    }

}


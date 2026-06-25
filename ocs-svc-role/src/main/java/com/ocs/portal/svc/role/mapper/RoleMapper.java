package com.ocs.portal.svc.role.mapper;

import com.ocs.portal.dto.response.login.Menu;
import com.ocs.portal.entity.*;
import com.ocs.portal.svc.role.dto.request.*;
import com.ocs.portal.svc.role.dto.request.roles.*;
import com.ocs.portal.svc.role.dto.response.*;
import com.ocs.portal.svc.role.dto.response.ProdRolesDto;
import com.ocs.portal.svc.role.projection.*;
import com.ocs.portal.entity.embeddable.BfmUserPortalId;
import com.ocs.portal.svc.role.dto.request.job.JobDto;
import com.ocs.portal.svc.role.dto.request.job.JobRoleDto;
import com.ocs.portal.svc.role.dto.request.job.JobRoleHisDto;
import com.ocs.portal.svc.role.dto.request.job.OrgAreaStaffUserDto;
import com.ocs.portal.svc.role.dto.request.log.LogDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    ProdRolesDto toProdRolesDto(RoleProjection role);

    BfmServLog toBfmServLog(LogDto role);

    List<BfmRolePriv> toBfmRolePriv(List<RolePrivProjection> role);

    List<RolePrivDto> toRolePrivDto(List<RolePrivProjection> role);

    UserRoleExtDto toRoleDto(UserRoleProjection role);

    BfmUserRoleHis toBfmRoleHis(UserRoleHisDto dto);

    List<PortalDto> toListPortalDto(List<PortalProjection> role);

    BfmRole toBfmRole(ProdRolesDto prodRolesDto);

    List<ProdRolePrivDto> doListProdRolePrivDto(List<RolePrivMenuProjection> role);

    List<PortalMenuDto> doListPortalMenuDto(List<PortalMenuDirProjection> portalMenuDirProjection);

    List<PortalMenuDto> doListPortalMenuDtoFromPriv(List<PortalPrivProjection> role);

    List<DirDto> toListDirDto(List<DirProjection> role);

    List<RolePrivDto> toListRolePrivDto(List<MenuProjection> role);

    List<UserDto> toPageUserDto(List<UsersRoleProjection> role);

    List<UserDto> toPageUserDtoFromUserInfo(List<UserInfoProjection> role);

    List<PortletsDto> toListPortletsDto(List<PortletProjection> role);

    List<PortletTypeDto> toListPortletTypeDto(List<PortletTypeProjection> role);

    List<JobDto> toListJobDto(List<JobProjection> role);

    JobRole toJobDto(JobRoleDto role);

    List<OrgAreaStaffUserDto> toListOrgStaf(List<StaffJobProjection> role);

    List<UserRoleExtDto> toUserRoleExtDto(List<UserRoleProjection> role);

    List<BfmUserRole> toUserRole(List<UserRoleExtDto> role);

    List<JobRoleHis> toRoleHis(List<JobRoleDto> role);

    List<JobRoleDto> toListJobRoleDto(List<JobRoleProjection> role);

    List<JobRoleHis> toJobRoleHis(List<JobRoleHisDto> jobRoleHisDtos);

    List<ComponentPrivDto> toListComponentPriv(List<ComponentPrivProjection> role);

    List<AppDto> toListAppDto(List<AppDtoProjection> role);

    List<UserRoleExtDto> toListUserRoleExtDto(List<UsersRoleProjection> role);

    List<UserRoleExtDto> toListUserRoleExtDtoFromUserRoleExt(List<UserRoleExtProjection> role);

    List<RolePortalDto> toListRolePortalDto(List<RolePortalProjection> list);

    List<ProdRolePrivDto> toListProdRolePrivDto(List<ProdRolePrivProjection> role);

    List<ProdUserPrivDto> toListUserPrivDto(List<ProdUserPrivProjection> role);

    List<ProdComponentPrivDto> toListProdComponentPrivDto(List<ProdComponentPrivProjection> role);

    ProdComponentPrivDto toProdComponentPrivDto(ProdComponentPrivProjection mapper);

    ParamsDto toParamDto(ParamsProjection dto);

    SecurityRuleDto toSecurityRule(SecurityRuleProjection role);

    ProdSecurityRuleDto toSecurityProd(SecurityRuleProjection role);

    List<UserDto> toUserDtoFromUserDtoProjection(List<UserDtoProjection> role);

    StaffDto toStaffDto(StafDtoProjection role);

    BfmUser toBfmUser(UserDto role);

    UserDto userDto(BfmUser bfmUser);

    BfmUserHis toBfmUserHis(UserHisDto role);

    UserHisDto toUserHisDto(UserDto role);

    AttrDefDto toAttrDef(Optional<AttrDefDtoProjection> attr);

    BfmAttrDto toBfmAttr(Optional<BfmAttrDtoProjection> role);

    BfmStaffAttrDto toBfmStaff(Optional<StaffAttrProjection> role);

    BfmStaff toBfmStaff(StaffDto staffDto);

    BfmStaffHis toStaffHis(StaffHistoryDto dto);

    BfmStaffAttr toBfmStaffFromBfmStaffAttrDto(BfmStaffAttrDto dto);

    StaffDto toStaffDtoEntity(BfmStaff staff);

    List<BfmUserRole> toBfmUserRoleList(List<UserRoleDto> roles);

    List<UserPortalDto> toUserPortalListDto(List<UserPortalProjection> role);

    List<UserPrivDto> toListUserPriv(List<UserPrivProjection> list);

    @Mapping(target = "id", source = ".", qualifiedByName = "toEmbeddedId")
    BfmUserPortal toBfmUserPortal(UserPortalDto dto);

    List<BfmUserPortal> toBfmUserPortalList(List<UserPortalDto> list);

    @Named("toEmbeddedId")
    default BfmUserPortalId toEmbeddedId(UserPortalDto dto) {
        BfmUserPortalId id = new BfmUserPortalId();
        id.setUserId(dto.getUserId());
        id.setPortalId(dto.getPortalId());
        return id;
    }

    List<Menu> toListMenu(List<ProdRolePrivDto> listMenu);

    List<UserHisDto> toUserListHisDto(Page<UserHisProjection> userHisDto);
}

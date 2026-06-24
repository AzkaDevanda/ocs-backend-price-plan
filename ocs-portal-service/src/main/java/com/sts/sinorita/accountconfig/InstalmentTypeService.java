package com.sts.sinorita.accountconfig;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.constant.HttpStatusConstant;
import com.sts.sinorita.constant.SortDirection;
import com.sts.sinorita.dto.request.accountConfig.AppliedAccountItemTypeRequestDto;
import com.sts.sinorita.dto.request.accountConfig.InstalmentItemRequestDto;
import com.sts.sinorita.dto.request.accountConfig.InstalmentRequestDto;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.dto.response.accountconfig.ListIntalmentItemResponseDto;
import com.sts.sinorita.entity.InstalmentItem;
import com.sts.sinorita.entity.InstalmentItemId;
import com.sts.sinorita.entity.InstalmentType;
import com.sts.sinorita.entity.InstalmentTypeItem;
import com.sts.sinorita.entity.InstalmentTypeItemId;
import com.sts.sinorita.mapper.accountConfig.InstalmentTypeMapper;
import com.sts.sinorita.projection.accountConfig.QryInstalmentItemByTypeIDProjection;
import com.sts.sinorita.repository.InstalmentItemRepository;
import com.sts.sinorita.repository.InstalmentTypeItemRepository;
import com.sts.sinorita.repository.InstalmentTypeRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class InstalmentTypeService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private MessageService messageService;

    // ==========> REPOSITORY <==========
    @Autowired
    private InstalmentTypeRepository instalmentTypeRepository;
    @Autowired
    private InstalmentItemRepository instalmentItemRepository;
    @Autowired
    private InstalmentTypeItemRepository instalmentTypeItemRepository;

    // ==========> MAPPER <==========
    @Autowired
    private InstalmentTypeMapper instalmentTypeMapper;

    public ResponseEntity<CustomeResponse> QryInstalmentType1(Character state, Integer spId, String isFixedFirstPay,
            int page, int size, String order_field, SortDirection sortDirection) {

        int paging = Math.max(page - 1, 0);
        Sort.Direction direction = Sort.Direction.valueOf(sortDirection.name());
        Sort sort = Sort.by(direction, order_field);
        Pageable pageable = PageRequest.of(paging, size, sort);

        var data = instalmentTypeRepository.QryInstalmentType1(state, spId, isFixedFirstPay, pageable);
        var result = data.getContent().stream().map(instalmentTypeMapper::toDtoListInstalmentType).toList();

        return ResponseEntity.status(HttpStatus.OK).body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE,
                result, data.getTotalElements(), data.getTotalPages()));
    }

    public ResponseEntity<CustomeResponse> GetInstalmentTypeDetail(Long instalmentTypeId) {
        var instalmentTypeProjection = instalmentTypeRepository
                .findByInstalmentTypeId(instalmentTypeId)
                .orElseThrow(() -> new RuntimeException("Instalment type not found"));

        var data = instalmentTypeMapper.toDto(instalmentTypeProjection);

        var itemsProjection = instalmentItemRepository.QryInstalmentItemByTypeID(instalmentTypeId);
        data.setInstalmentItems(instalmentTypeMapper.toItemDtoList(itemsProjection));

        var acctItemTypeProjection = instalmentTypeItemRepository.findByInstalmentTypeId(instalmentTypeId);
        data.setAppliedAccountItemType(instalmentTypeMapper

                .toItemDtoList2(acctItemTypeProjection));
        Integer totalPercent = 0;
        for (ListIntalmentItemResponseDto item : data.getInstalmentItems()) {
            totalPercent += item.getFeePercent();
        }
        data.setTotalPhase(totalPercent);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, data));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CustomeResponse> addInstalment(InstalmentRequestDto dto) {
        InstalmentType instalmentType = addInstalmentType(dto);
        operateInstalmentItem(instalmentType.getId(), dto);
        processAppliedAcctItemType(instalmentType.getId(), dto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CustomeResponse> modInstallment(Long instalmentTypeId, InstalmentRequestDto dto) {
        instalmentItemRepository.deleteByInstalmentTypeIdAndSeq(instalmentTypeId, null);
        operateInstalmentItem(instalmentTypeId, dto);
        modInstalmentType(instalmentTypeId, dto);
        processAppliedAcctItemType(instalmentTypeId, dto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CustomeResponse> delInstallment(Long instalmentTypeId) {
        instalmentItemRepository.deleteByInstalmentTypeIdAndSeq(instalmentTypeId, null);
        delInstalmentType(instalmentTypeId);
        instalmentTypeItemRepository.deleteByInstalmentTypeId(instalmentTypeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomeResponse(200, HttpStatusConstant.SUCCESS_MESSAGE, null));
    }

    private InstalmentType addInstalmentType(InstalmentRequestDto dto) {
        InstalmentType instalmentType = new InstalmentType();
        Long nextId = instalmentTypeRepository.getNextId();
        instalmentType.setId(nextId);
        instalmentType.setInstalmentTypeName(dto.getInstalmentTypeName());
        instalmentType.setFirstPay(dto.getFirstPay());
        instalmentType.setState('A');
        instalmentType.setStateDate(LocalDate.now());
        instalmentType.setComments(dto.getComments());
        instalmentType.setSpId(0);
        instalmentType.setFeePercent(dto.getFeePercent());
        instalmentTypeRepository.save(instalmentType);
        return instalmentType;
    }

    private void modInstalmentType(Long instalmentTypeId, InstalmentRequestDto dto) {
        InstalmentType instalmentType = instalmentTypeRepository.findById(instalmentTypeId).isPresent()
                ? instalmentTypeRepository.findById(instalmentTypeId).get()
                : null;
        if (instalmentType != null) {
            instalmentType.setInstalmentTypeName(dto.getInstalmentTypeName());
            instalmentType.setFirstPay(dto.getFirstPay());
            instalmentType.setComments(dto.getComments());
            instalmentType.setFeePercent(dto.getFeePercent());
            instalmentTypeRepository.save(instalmentType);
        }
    }

    private void delInstalmentType(Long instalmentTypeId) {
        if (instalmentTypeRepository.isReferencedInReCcInst(instalmentTypeId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00745"));
        }

        if (instalmentTypeRepository.isReferencedInInstalmentItem(instalmentTypeId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00746"));
        }

        if (instalmentTypeRepository.isReferencedInInstalmentTypeItem(instalmentTypeId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00747"));
        }

        if (instalmentTypeRepository.isReferencedInOverduePlan(instalmentTypeId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00748"));
        }

        // if
        // (instalmentTypeRepository.isReferencedInShoppingCartOffer(instalmentTypeId).isPresent())
        // {
        // throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
        // messageService.getMessage("S-ACT-00758"));
        // }

        if (instalmentTypeRepository.isReferencedInOrderItem(instalmentTypeId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("S-ACT-00759"));
        }

        if (instalmentTypeRepository.isInstalmentTypeReferencedByInstallment(instalmentTypeId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, messageService.getMessage("BL-S-ACT-00376"));
        }

        instalmentTypeRepository.deleteById(instalmentTypeId);
    }

    private void operateInstalmentItem(Long instalmentTypeId, InstalmentRequestDto dto) {
        if (dto.getInstalmentItems() == null || dto.getInstalmentItems().isEmpty()) {
            return;
        }

        for (InstalmentItemRequestDto item : dto.getInstalmentItems()) {
            InstalmentItem instalmentItem = new InstalmentItem();
            InstalmentItemId instalmentItemId = new InstalmentItemId();

            instalmentItemId.setInstalmentTypeId(instalmentTypeId);
            instalmentItemId.setSeq(item.getSeq());

            instalmentItem.setId(instalmentItemId);
            instalmentItem.setItemPercent(item.getItemPercent());
            instalmentItem.setRepeatTime(item.getRepeatTime());
            instalmentItem.setSpId(0);
            instalmentItem.setFeePercent(item.getFeePercent() != null ? item.getFeePercent().intValue() : 0);

            instalmentItemRepository.save(instalmentItem);
        }
    }

    @Transactional
    public void processAppliedAcctItemType(Long instalmentTypeId, InstalmentRequestDto dto) {
        if (dto.getAppliedAccountItemType() != null && !dto.getAppliedAccountItemType().isEmpty()) {
            for (AppliedAccountItemTypeRequestDto acctItemType : dto.getAppliedAccountItemType()) {

                // Hapus data
                if (acctItemType.getStatus().compareTo('D') == 0) {
                    instalmentTypeItemRepository.deleteByInstalmentTypeIdAndAcctItemTypeId(
                            instalmentTypeId, acctItemType.getAcctItemTypeId());
                    entityManager.flush();
                    entityManager.clear();
                    continue;
                }

                // Tambah data baru jika belum ada
                if (acctItemType.getStatus().compareTo('A') == 0 && instalmentTypeItemRepository
                        .existsByAcctItemTypeIdAndInstalmentTypeId(
                                acctItemType.getAcctItemTypeId(), instalmentTypeId) == 0) {
                    InstalmentTypeItem instalmentTypeItem = new InstalmentTypeItem();
                    InstalmentTypeItemId instalmentTypeItemId = new InstalmentTypeItemId();
                    instalmentTypeItemId.setInstalmentTypeId(instalmentTypeId);
                    instalmentTypeItemId.setAcctItemTypeId(acctItemType.getAcctItemTypeId());
                    instalmentTypeItem.setId(instalmentTypeItemId);
                    instalmentTypeItem.setSpId(0);
                    instalmentTypeItemRepository.save(instalmentTypeItem);
                }
            }
        }
    }

}

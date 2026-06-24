package com.sts.sinorita.orderentry;

import com.sts.sinorita.common.MessageService;
import com.sts.sinorita.constant.HttpStatusConstant;
import com.sts.sinorita.dto.request.orderentry.CertListReqDto;
import com.sts.sinorita.dto.response.BaseResponseDto;
import com.sts.sinorita.dto.response.CustomeResponse;
import com.sts.sinorita.projection.orderentry.CertProjection;
import com.sts.sinorita.projection.orderentry.CustomerProjection;
import com.sts.sinorita.repository.orderentry.CertRepository;
import com.sts.sinorita.repository.orderentry.CustomerRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderEntryService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CertRepository certRepository;

    @Autowired
    MessageService messageService;

    public BaseResponseDto getListCert(){
        BaseResponseDto baseResponseDto = new BaseResponseDto();
        baseResponseDto.setCode("200");
        baseResponseDto.setMessage("Success");
        List<CertProjection> list = certRepository.getListCert(0);
        if (!list.isEmpty()){
            baseResponseDto.setData(list);
        }else {
            Response response = new Response();
            response.setMessage(messageService.getMessage("S-ACT-00424"));
            baseResponseDto.setData(response);
        }

        return baseResponseDto;
    }

    public BaseResponseDto getListCustomer(CertListReqDto certListReqDto){
        BaseResponseDto baseResponseDto = new BaseResponseDto();
        baseResponseDto.setCode("200");
        baseResponseDto.setMessage("Success");
        List<CustomerProjection> list = customerRepository.searchCustomers((long)0,certListReqDto.getEmail(),
                certListReqDto.getIndustryId(),
                certListReqDto.getCustCode(),
                certListReqDto.getCustType(),
                certListReqDto.getCertTypeId(),
                certListReqDto.getCertNbr(),
                certListReqDto.getCustId());
        if (!list.isEmpty()){
            baseResponseDto.setData(list);
        }else {
            Response response = new Response();
            response.setMessage(messageService.getMessage("S-ACT-00424"));
            baseResponseDto.setData(response);
        }

        return baseResponseDto;
    }

    @Getter
    @Setter
    static class Response{
        String message;
    }
}

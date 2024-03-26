package com.accountmanagement.domain.service.create;

import com.accountmanagement.domain.service.dto.TransactionDTO;

import java.util.List;

public record TransactionQueryResponse(List<TransactionDTO> transactions) {
}

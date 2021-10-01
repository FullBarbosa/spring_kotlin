package com.mercadolivro.service.extension

import com.mercadolivro.model.CustomerModel
import com.mercadolivro.service.enums.CustomerStatus
import com.mercadolivro.service.request.PostCustomerRequest
import com.mercadolivro.service.request.PutCustomerRequest
import com.mercadolivro.service.response.CustomerResponse


    
    fun PostCustomerRequest.toCustomeModel(): CustomerModel {
        return CustomerModel(
            name = this.name,
            email = this.email,
            status = CustomerStatus.ATIVO
        )
    }
    
    fun PutCustomerRequest.toCustomeModel(previousValue: CustomerModel): CustomerModel {
        return CustomerModel(
            id = previousValue.id,
            name = this.name ?: previousValue.name,
            email = this.email ?: previousValue.email,
            status = previousValue.status
        )
    }
    
    fun CustomerModel.toResponse(): CustomerResponse {
        return CustomerResponse(
            id = this.id,
            name = this.name,
            email = this.email,
            status = this.status
        )
    }

package com.mercadolivro.service

import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.service.enums.BookStatus
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.reppsitory.BookRepository
import com.mercadolivro.reppsitory.CustomerRepository
import com.mercadolivro.service.enums.Erros
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BookService(
    val bookRepository: BookRepository,
    val customerRepository: CustomerRepository
) {
    fun create(book: BookModel) {
        bookRepository.save(book)
    }
    
    fun findAll(pageable: Pageable): Page<BookModel> {
        return bookRepository.findAll(pageable)
    }
    
    fun findActives(pageable: Pageable): Page<BookModel> {
        return bookRepository.findByStatus(BookStatus.ATIVO, pageable)
    }
    
    fun findById(id: Int): BookModel {
        return bookRepository.findById(id).orElseThrow {
            NotFoundException(Erros.ML101.message.format(id), Erros.ML101.code)
        }
    }
    
    fun delete(id: Int) {
        if (!bookRepository.existsById(id)) {
            NotFoundException(Erros.ML101.message.format(id), Erros.ML101.code)
        }
        val book = findById(id)
        book.status = BookStatus.CANCELADO
        update(book)
    }
    
    fun update(book: BookModel) {
        bookRepository.save(book)
    }
    
    fun deleteByCustomer(customer: CustomerModel) {
        val books = bookRepository.findByCustomer(customer)
        for (book in books) {
            book.status = BookStatus.DELETADO
        }
        bookRepository.saveAll(books)
    }
}
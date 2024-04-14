package com.example.demo.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.dtos.reponses.AuthorDTO;
import com.example.demo.dtos.reponses.BookDTO;
import com.example.demo.dtos.reponses.PatronDTO;
import com.example.demo.entities.Author;
import com.example.demo.entities.Book;
import com.example.demo.entities.Patron;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.secuirty.SecurityConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
public class SystemUtil {

    public static <T> T unwrapEntity(Optional<T> entity, Object identifier, String entityName) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(entityName, identifier);
    }

    public static String[] getNullPropertyNames(Object source) {
        Set<String> emptyNames = new HashSet<>();
        BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        for (PropertyDescriptor pd : pds) {
            if (src.getPropertyValue(pd.getName()) == null) {
                emptyNames.add(pd.getName());
            }
        }

        return emptyNames.toArray(new String[0]);
    }

    public static void copyNonNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    public static BookDTO convertToDetailedDTO(Book book) {

        //Authors
        Set<AuthorDTO> authorDTOs = new HashSet<>();
        for (Author A : book.getAuthors()) {
            AuthorDTO authorDTO = new AuthorDTO();
            BeanUtils.copyProperties(A, authorDTO);
            authorDTOs.add(authorDTO);
        }
        //Patrons
        Set<PatronDTO> patronDTOS = new HashSet<>();
        for (Patron P : book.getPatrons()) {
            PatronDTO patronDTO = new PatronDTO();
            BeanUtils.copyProperties(P, patronDTO);
            patronDTOS.add(patronDTO);
        }

        BookDTO bookDTO = new BookDTO();
        bookDTO.setAuthors(authorDTOs);
        bookDTO.setPatrons(patronDTOS);
        BeanUtils.copyProperties(book, bookDTO);
        return bookDTO;

    }

    public static PageRequest pagination(String sortField, String sortDirection, Integer pageNumber, Integer pageSize) {
        sortField = sortField == null ? "id" : sortField;
        sortDirection = sortDirection == null ? "asc" : sortDirection.toLowerCase();
        Sort.Direction direction = sortDirection.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        pageNumber = pageNumber == null ? 0 : pageNumber;
        pageSize = pageSize == null ? 10 : pageSize;
        return PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortField));
    }

    public static String writeObjectIntoString(Object object) {
        String result = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("An error occurred: {}", e.getMessage(), e);
        }
        return result;
    }

    public static void sendResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("msg", message);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jsonObject);
        response.getWriter().flush();
    }

    public static Cookie createCookie(String token, String tokenName, String path) {
        Cookie cookie = new Cookie(tokenName, token);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath(path);

        return cookie;

    }

    public static String createToken(String subject, Long expiry) {
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiry))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY));
    }

}



package com.example.demo.services;

import com.example.demo.dtos.reponses.ContactInformationDTO;
import com.example.demo.dtos.reponses.PatronDTO;
import com.example.demo.dtos.requests.ContactInformationRequest;
import com.example.demo.entities.ContactInformation;
import com.example.demo.entities.Patron;
import com.example.demo.repositories.ContactInformationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import static com.example.demo.utils.SystemUtil.*;
import static com.example.demo.utils.SystemUtil.unwrapEntity;

@Service
@AllArgsConstructor
public class ContactInformationService {

    private ContactInformationRepository contactInformationRepository;

    private PatronService patronService;


    @Transactional
    public ContactInformationDTO createContactInformation(ContactInformationRequest contactRequest) {


        ContactInformation contactInformation = new ContactInformation();
        BeanUtils.copyProperties(contactRequest, contactInformation);


        PatronDTO patronDTO = (PatronDTO) patronService.getUserById(contactRequest.getPatronId());
        Patron P = new Patron();
        BeanUtils.copyProperties(patronDTO, P);

        contactInformation.setPatron(P);
        contactInformation = contactInformationRepository.save(contactInformation);
        ContactInformationDTO contactDTO = new ContactInformationDTO();
        BeanUtils.copyProperties(contactInformation, contactDTO);
        return contactDTO;
    }

    public ContactInformationDTO getContactInformationById(Long id) {
        ContactInformation contactInformation = unwrapEntity(contactInformationRepository.findById(id), id, "Contact Information");

        //Patron

        PatronDTO P = new PatronDTO();
        BeanUtils.copyProperties(contactInformation.getPatron(), P);

        ContactInformationDTO contactInformationDTO = new ContactInformationDTO();
        contactInformationDTO.setPatron(P);

        BeanUtils.copyProperties(contactInformation, contactInformationDTO);
        return contactInformationDTO;
    }

    public Page<ContactInformationDTO> getAllContactInformation(String searchTerm, Integer pageSize, Integer pageNumber, String sortField, String sortDirection) {
        Pageable paginate = pagination(sortField, sortDirection, pageNumber, pageSize);
        searchTerm = searchTerm == null ? "" : searchTerm.toLowerCase();
        return contactInformationRepository.finaAllContactInformation(searchTerm, paginate).map(this::convertToDto);
    }

    public ContactInformationDTO updateContactInformation(Long id, ContactInformationRequest contactInformationRequest) {

        ContactInformation contactInformation = unwrapEntity(contactInformationRepository.findById(id), id, "Contact Information");
        copyNonNullProperties(contactInformationRequest, contactInformation);
        contactInformation = contactInformationRepository.save(contactInformation);
        ContactInformationDTO contactInformationDTO = new ContactInformationDTO();
        BeanUtils.copyProperties(contactInformation, contactInformationDTO);
        return contactInformationDTO;
    }

    public ContactInformationDTO convertToDto(ContactInformation contactInformation) {
        ContactInformationDTO response = new ContactInformationDTO();
        BeanUtils.copyProperties(contactInformation, response);
        return response;
    }

    public final void deleteContactInformation(Long id) {
        unwrapEntity(contactInformationRepository.findById(id), id, "book");
        contactInformationRepository.deleteById(id);
    }


}

package net.hassani.springwebservicesexample1.ws;

import net.hassani.soap.GetCompteRequest;
import net.hassani.soap.GetCompteResponse;
import net.hassani.soap.GetComptesResponse;
import net.hassani.soap.GetcomptesRequest;
import net.hassani.springwebservicesexample1.entities.Compte;
import net.hassani.springwebservicesexample1.repository.CompteRepository;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;

@Endpoint
public class BanqueSoapEndpoint {
    private CompteRepository compteRepository;
    private static final String NAMESPACE_URI = "http://www.hassani.net/soap";

    public BanqueSoapEndpoint(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }
    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCompteRequest")
    public GetCompteResponse getCompte(@RequestPayload GetCompteRequest request) throws DatatypeConfigurationException {
        GetCompteResponse response = new GetCompteResponse();
        Compte compteMetier = compteRepository.findById(request.getCode()).get();
        net.hassani.soap.Compte c = mapSoapCompte(compteMetier);
        response.setCompte(c);
        return response;
    }

    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getcomptesRequest")
    public GetComptesResponse getComptes(@RequestPayload GetcomptesRequest request) throws DatatypeConfigurationException {
        GetComptesResponse response = new GetComptesResponse();
        compteRepository.findAll().forEach(compteMetier -> {
            net.hassani.soap.Compte c = null;
            try {
                c = mapSoapCompte(compteMetier);
            } catch (DatatypeConfigurationException e) {
                throw new RuntimeException(e);
            }
            response.getComptes().add(c);
        });
        return response;
    }

    private net.hassani.soap.Compte mapSoapCompte(Compte compteMetier) throws DatatypeConfigurationException {
        net.hassani.soap.Compte c = new net.hassani.soap.Compte();
        c.setCode(compteMetier.getCode());
        c.setSolde(compteMetier.getSolde());
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(compteMetier.getDateCreation());
        c.setDateCreation(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
        c.setState(compteMetier.getState());
        return c;
    }

}

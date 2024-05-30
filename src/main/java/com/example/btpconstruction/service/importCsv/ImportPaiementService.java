package com.example.btpconstruction.service.importCsv;

import com.example.btpconstruction.model.Devis;
import com.example.btpconstruction.model.Paiement;
import com.example.btpconstruction.model.importData.ImportPaiement;
import com.example.btpconstruction.model.importData.ImportTravaux;
import com.example.btpconstruction.service.DevisService;
import com.example.btpconstruction.service.PaiementService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Service
public class importPaiementService {
    DevisService devisService;
    PaiementService paiementService;

    public importPaiementService(DevisService devisService, PaiementService paiementService) {
        this.devisService = devisService;
        this.paiementService = paiementService;
    }

    public List<String> verifier(ImportPaiement importPaiement)
    {
        List<String> errors=new ArrayList<>();

        //verfiier devis
        Devis devis=devisService.getDevisRepository().findByReference(importPaiement.getRefDevis());
        if(devis==null)
        {
            errors.add("Referemce devis invalide");
        }
        try {
            Paiement paiement=new Paiement();
            paiement.setDatePaiement(importPaiement.getDatePaiement());

        }catch (Exception e)
        {
            errors.add(e.getMessage());
        }

        try {
            Paiement paiement=new Paiement();
            paiement.setMontant(importPaiement.getMontant());
        }catch (Exception e)
        {
            errors.add(e.getMessage());
        }
        return errors;
    }

    public ImportPaiement save(List<String> strings)
    {
        ImportPaiement importPaiement=new ImportPaiement();
        importPaiement.setRefDevis(strings.get(0));
        importPaiement.setRefPaiement(strings.get(1));
        importPaiement.setDatePaiement(strings.get(2));
        importPaiement.setMontant(strings.get(3));
        return importPaiement;
    }

    public Paiement enregistrer(ImportPaiement importPaiement)
    {
        Paiement paiement=new Paiement();
        paiement.setDevis(devisService.getDevisRepository().findByReference(importPaiement.getRefDevis()));
        paiement.setDatePaiement(importPaiement.getDatePaiement());
        paiement.setMontant(importPaiement.getMontant());
        paiement.setReference(importPaiement.getRefPaiement());
        return paiementService.getPaiementRepository().save(paiement);
    }




    public List<String> enregisrerPaiements(List<List<String>> data )
    {
        List<String> errors=new ArrayList<>();
        List<ImportPaiement> paiementList=new ArrayList<>();
        //enlever l'entete
        data.remove(0);
        System.out.println("data"+data);
        int numero=1;
        for (List<String> list : data)
        {
            ImportPaiement importPaiement=null;
            importPaiement=save(list);
            paiementList.add(importPaiement);
            List<String> listError=verifier(importPaiement);
            for (String s : listError)
            {
                errors.add("ligne"+numero+"-details:"+s);
            }
            numero++;
        }
        if(errors.size()==0)
        {
            for (ImportPaiement importPaiement: paiementList)
            {
                enregistrer(importPaiement);
            }
        }
        return errors;
    }
}

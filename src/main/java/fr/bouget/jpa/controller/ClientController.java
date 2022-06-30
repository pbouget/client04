package fr.bouget.jpa.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.bouget.jpa.model.Adresse;
import fr.bouget.jpa.model.Client;
import fr.bouget.jpa.model.Telephone;
import fr.bouget.jpa.repository.AdresseRepository;
import fr.bouget.jpa.repository.ClientRepository;
import fr.bouget.jpa.repository.TelephoneRepository;

/**
 * @author Philippe
 *
 */
@CrossOrigin("*")
@RestController
public class ClientController {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private AdresseRepository adresseRepository;

	@Autowired
	private TelephoneRepository telephoneRepository;

	@GetMapping("/accueil")
	@ResponseBody
	public String home()
	{

		Adresse adresse1=new Adresse("5, rue du Renard","","75015","PARIS", "FRANCE");
		Client client1=new Client("MARTIN","Jean");
		client1=clientRepository.saveAndFlush(client1);

		Adresse adresse2=new Adresse("5, rue du Renard","","75015","PARIS","FRANCE");
		Client client2=new Client("DUPONT","sophie",adresse2);
		adresse2.setClient(client2);
		client2=clientRepository.saveAndFlush(client2);

		Adresse adresse3=new Adresse("20, boulevard Gambetta","","78300","POISSY","FRANCE");
		Client client3=new Client("DURAND","Pierre",adresse3);
		adresse3.setClient(client3);
		client3=clientRepository.saveAndFlush(client3);

		Adresse adresse4=new Adresse("29, boulevard Devaux","","78300","POISSY","FRANCE");
		Client client4=new Client("MADEC","Denis",adresse4);
		adresse4.setClient(client4);
		client4=clientRepository.saveAndFlush(client4);

		System.out.println("liste de tous les clients:");
		Collection<Client> liste=clientRepository.findAll();
		affiche(liste);


		System.out.println("MARTIN Jean habite desormais avec DUPONT Sophie");
		client1.setAdresse(adresse1);
		adresse1.setClient(client1);
		clientRepository.save(client1);

		//          System.out.println("MADEC Denis se desinscrit");
		//          clientRepository.delete(client4);


		System.out.println("Liste de toutes les adresses et les clients associ�s:");
		Collection<Adresse> listeAdresses = adresseRepository.findAll();
		for (Adresse adresse : listeAdresses)
		{
			System.out.print(adresse+"  client : ");
			if (adresse.getClient()!=null) System.out.println(adresse.getClient());

		}

		Telephone tel1=new Telephone("01 43 65 88 34");
		telephoneRepository.save(tel1);
		System.out.println("le numero "+tel1.getNumero()+" est ajoute au client "+client1);

		ajoutTelephone(client1.getId(),tel1);

		Telephone tel2=new Telephone("01 65 25 01 23");
		telephoneRepository.save(tel2);
		System.out.println("le numero "+tel2.getNumero()+" est ajoute au client "+client2);

		ajoutTelephone(client2.getId(),tel2);

		Telephone tel3=new Telephone("02 78 88 41 73");
		telephoneRepository.save(tel3);
		System.out.println("le numero "+tel3.getNumero()+" est ajoute au client "+client3);

		ajoutTelephone(client3.getId(),tel3);

		Telephone tel4=new Telephone("02 70 98 23 08");
		telephoneRepository.save(tel4);
		System.out.println("le numero "+tel4.getNumero()+" est ajoute au client "+client3);

		ajoutTelephone(client3.getId(),tel4);

		System.out.println("liste de tous les clients:");
		liste=clientRepository.findAll();
		affiche(liste);

		System.out.println("le numero "+tel2.getNumero()+" est supprimé du client "+client2);

		enleveTelephone(client2.getId(),tel2);

		System.out.println("Liste de tous les clients:");

		this.affiche(clientRepository.findAll());

		StringBuilder sb = new StringBuilder();
		sb.append("<h1>Regardez dans votre console et dans votre base de données MySQL <strong>JPA</strong></h1>");
		sb.append("<a href='http://localhost:8080/clients'>Voir la liste des clients enregistrés</a>");
		return  sb.toString();

	}

	@GetMapping(value = "/clients")
	public ResponseEntity<?> getAll(){
		List<Client> liste = null;
		try
		{
			liste = clientRepository.findAll();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

		return ResponseEntity.status(HttpStatus.OK).body(liste);
	}

	/**
	 * M�thode pour affichage dans la console
	 * @param liste
	 */
	private void affiche(Collection<Client> liste)
	{

		for (Client client : liste) {

			System.out.println(client);
		}



	}
	/**
	 * Méthode pour ajouter un Téléphone à un Client
	 * @param idClient
	 * @param numero
	 * @return
	 */
	private boolean ajoutTelephone(int idClient,Telephone numero){
		// on recherche le client
		Client cl=clientRepository.getOne(idClient);
		if(cl!=null)
		{
			// on ajoute le num�ro de t�l�phone
			cl.getTelephones().add(numero);
			clientRepository.saveAndFlush(cl);
			return true;
		}
		return false;
	}

	/**
	 * Méthode pour enlever un Téléphone à un Client
	 * @param idClient
	 * @param numero
	 * @return
	 */
	private boolean enleveTelephone(int idClient,Telephone numero){
		Client cl=clientRepository.getOne(idClient);
		if(cl!=null)
		{
			for(Telephone num : cl.getTelephones())
			{
				if(num.equals(numero))
				{
					cl.getTelephones().remove(num);
					telephoneRepository.delete(num);
					clientRepository.saveAndFlush(cl);
					return true;
				}
			}
		}
		return false;
	}
}

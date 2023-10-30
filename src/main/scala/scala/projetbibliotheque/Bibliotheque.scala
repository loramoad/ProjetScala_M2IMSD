package scala.projetbibliotheque

// Classe représentant une bibliothèque
class Bibliothèque {
  var livres: List[Livre] = List()

  def ajouterLivre(livre: Livre): Unit = {
    livres = livre :: livres
    println(s"${livre.titre} a été ajouté à la bibliothèque.")
  }

  def emprunterLivre(titre: String): Unit = {
    livres.find(livre => livre.titre == titre) match {
      case Some(l) => l.emprunter()
      case None => println(s"Le livre $titre n'est pas dans la bibliothèque.")
    }
  }

  def rendreLivre(titre: String): Unit = {
    livres.find(livre => livre.titre == titre) match {
      case Some(l) => l.rendre()
      case None => println(s"Le livre $titre n'est pas dans la bibliothèque.")
    }
  }

  def rechercherLivreParTitre(titre: String): Option[Livre] = {
    livres.find(livre => livre.titre == titre)
  }

  def rechercherLivresParAuteur(auteur: String): List[Livre] = {
    livres.filter(livre => livre.auteur == auteur)
  }
}
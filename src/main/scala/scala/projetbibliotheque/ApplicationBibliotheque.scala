package scala.projetbibliotheque

import scala.io.StdIn
import java.io.{File, PrintWriter}
import scala.io.Source

object ApplicationBibliotheque {
  val bibliothèque = new Bibliothèque

  def main(args: Array[String]): Unit = {
    println("Bienvenue dans votre bibliothèque!")

    // Charger l'état précédent de la bibliothèque (si disponible)
    chargerBibliothèque()

    var continuer = true
    while (continuer) {
      println("\nQue voulez-vous faire?")
      println("1. Ajouter un livre")
      println("2. Emprunter un livre")
      println("3. Rendre un livre")
      println("4. Rechercher un livre par titre")
      println("5. Rechercher des livres par auteur")
      println("6. Quitter")

      val choix = StdIn.readInt()
      choix match {
        case 1 => ajouterLivre()
        case 2 => emprunterLivre()
        case 3 => rendreLivre()
        case 4 => rechercherLivreParTitre()
        case 5 => rechercherLivresParAuteur()
        case 6 => continuer = false
        case _ => println("Choix invalide. Veuillez réessayer.")
      }
    }

    // Sauvegarder l'état actuel de la bibliothèque
    sauvegarderBibliothèque()
  }

  def ajouterLivre(): Unit = {
    println("Veuillez entrer les détails du livre:")
    print("Titre: ")
    val titre = StdIn.readLine()
    print("Auteur: ")
    val auteur = StdIn.readLine()
    print("Année de publication: ")
    val annee = StdIn.readInt()

    val livre = new Livre(titre, auteur, annee)
    bibliothèque.ajouterLivre(livre)
  }

  def emprunterLivre(): Unit = {
    println("Veuillez entrer le titre du livre que vous souhaitez emprunter:")
    val titre = StdIn.readLine()
    bibliothèque.emprunterLivre(titre)
  }

  def rendreLivre(): Unit = {
    println("Veuillez entrer le titre du livre que vous souhaitez rendre:")
    val titre = StdIn.readLine()
    bibliothèque.rendreLivre(titre)
  }

  def rechercherLivreParTitre(): Unit = {
    println("Veuillez entrer le titre du livre que vous recherchez:")
    val titre = StdIn.readLine()
    bibliothèque.rechercherLivreParTitre(titre) match {
      case Some(livre) => println(s"Livre trouvé : ${livre.titre} par ${livre.auteur}, année de publication : ${livre.anneeDePublication}")
      case None => println(s"Livre non trouvé.")
    }
  }

  def rechercherLivresParAuteur(): Unit = {
    println("Veuillez entrer le nom de l'auteur que vous recherchez:")
    val auteur = StdIn.readLine()
    val livres = bibliothèque.rechercherLivresParAuteur(auteur)
    if (livres.nonEmpty) {
      println(s"Livres de $auteur:")
      livres.foreach(livre => println(s"${livre.titre}, année de publication : ${livre.anneeDePublication}"))
    } else {
      println(s"Aucun livre de $auteur trouvé dans la bibliothèque.")
    }
  }

  def sauvegarderBibliothèque(): Unit = {
    val writer = new PrintWriter(new File("bibliotheque.txt"))
    bibliothèque.livres.foreach(livre => {
      writer.println(s"${livre.titre},${livre.auteur},${livre.anneeDePublication},${livre.estEmprunte}")
    })
    writer.close()
  }

  def chargerBibliothèque(): Unit = {
    try {
      val lines = Source.fromFile("bibliotheque.txt").getLines()
      lines.foreach(line => {
        val parts = line.split(",")
        val titre = parts(0)
        val auteur = parts(1)
        val annee = parts(2).toInt
        val estEmprunte = parts(3).toBoolean
        val livre = new Livre(titre, auteur, annee)
        if (estEmprunte) livre.emprunter()
        bibliothèque.ajouterLivre(livre)
      })
    } catch {
      case _: Throwable => println("Aucune sauvegarde de bibliothèque trouvée.")
    }
  }
}

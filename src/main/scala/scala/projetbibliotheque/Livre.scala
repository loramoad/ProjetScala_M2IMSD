package scala.projetbibliotheque

// Classe représentant un livre
class Livre (val titre: String, val auteur: String, val anneeDePublication: Int) {
  var estEmprunte: Boolean = false

  def emprunter(): Unit = {
    if (!estEmprunte) {
      estEmprunte = true
      println(s"$titre a été emprunté.")
    } else {
      println(s"$titre est déjà emprunté.")
    }
}
  def rendre(): Unit = {
    if (estEmprunte) {
      estEmprunte = false
      println(s"$titre a été rendu.")
    } else {
      println(s"$titre n'est pas emprunté actuellement.")
    }
  }
}